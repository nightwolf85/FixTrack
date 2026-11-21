package com.fixtrack.desktop.service;

import com.fixtrack.desktop.model.ParteAdminDto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Locale;

public class PdfAlbaranService {

    private static final double TARIFA_HORA = 35.00; // Tarifa estándar de mano de obra SAT

    public static void generarAlbaran(ParteAdminDto parte, File destinoPdf) throws Exception {
        Document document = new Document(PageSize.A4, 36, 36, 40, 40);
        PdfWriter.getInstance(document, new FileOutputStream(destinoPdf));
        document.open();

        // Fuentes
        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, new Color(33, 37, 41));
        Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(13, 110, 253));
        Font fontNegrita = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);
        Font fontHeaderTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, Color.WHITE);

        // Cabecera del Albarán
        Paragraph titulo = new Paragraph("FIXTRACK SAT - ALBARÁN DE INTERVENCIÓN", fontTitulo);
        titulo.setSpacingAfter(4);
        document.add(titulo);

        Paragraph sub = new Paragraph("Parte Nº: #" + parte.getIdParte() + "  |  Fecha: " +
                (parte.getFechaEmision() != null ? parte.getFechaEmision() : "N/D") +
                "  |  Estado: " + parte.getEstado(), fontSubtitulo);
        sub.setSpacingAfter(16);
        document.add(sub);

        // Bloque de Datos del Cliente y Técnico
        PdfPTable tablaInfo = new PdfPTable(2);
        tablaInfo.setWidthPercentage(100);
        tablaInfo.setSpacingAfter(16);

        PdfPCell celdaCliente = new PdfPCell();
        celdaCliente.setPadding(8);
        celdaCliente.addElement(new Paragraph("DATOS DEL CLIENTE", fontNegrita));
        celdaCliente.addElement(new Paragraph("Nombre: " + nvl(parte.getClienteNombre()), fontNormal));
        celdaCliente.addElement(new Paragraph("NIF/CIF: " + nvl(parte.getClienteNif()), fontNormal));
        celdaCliente.addElement(new Paragraph("Dirección: " + nvl(parte.getClienteDireccion()), fontNormal));

        PdfPCell celdaIntervencion = new PdfPCell();
        celdaIntervencion.setPadding(8);
        celdaIntervencion.addElement(new Paragraph("DETALLE DEL SERVICIO", fontNegrita));
        celdaIntervencion.addElement(new Paragraph("Técnico asignado: " + nvl(parte.getTecnicoAsignado()), fontNormal));
        celdaIntervencion.addElement(new Paragraph("Avería reportada: " + nvl(parte.getDescripcionAveria()), fontNormal));

        tablaInfo.addCell(celdaCliente);
        tablaInfo.addCell(celdaIntervencion);
        document.add(tablaInfo);

        // Tabla de Materiales y Mano de Obra
        PdfPTable tablaConceptos = new PdfPTable(new float[]{1.5f, 4f, 1.2f, 1.5f, 1.8f});
        tablaConceptos.setWidthPercentage(100);
        tablaConceptos.setSpacingAfter(14);

        String[] cabeceras = {"Código", "Concepto / Repuesto", "Cant./Hrs", "Precio Ud.", "Subtotal"};
        for (String h : cabeceras) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fontHeaderTabla));
            cell.setBackgroundColor(new Color(33, 37, 41));
            cell.setPadding(6);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaConceptos.addCell(cell);
        }

        double baseImponible = 0.0;

        if (parte.getLineas() != null && !parte.getLineas().isEmpty()) {
            for (ParteAdminDto.LineaAlbaranDto linea : parte.getLineas()) {
                // Línea de repuesto
                if (linea.getUnidades() > 0) {
                    double subMat = linea.getUnidades() * linea.getPrecioUnitario();
                    baseImponible += subMat;
                    agregarFila(tablaConceptos, fontNormal,
                            nvl(linea.getCodigoArticulo()),
                            nvl(linea.getDescripcionArticulo()),
                            String.valueOf(linea.getUnidades()),
                            String.format(Locale.US, "%.2f €", linea.getPrecioUnitario()),
                            String.format(Locale.US, "%.2f €", subMat));
                }
                // Línea de mano de obra asociada
                if (linea.getHorasEmpleadas() > 0) {
                    double subHoras = linea.getHorasEmpleadas() * TARIFA_HORA;
                    baseImponible += subHoras;
                    agregarFila(tablaConceptos, fontNormal,
                            "MO-TEC",
                            "Mano de obra intervención técnica",
                            String.format(Locale.US, "%.2f h", linea.getHorasEmpleadas()),
                            String.format(Locale.US, "%.2f €", TARIFA_HORA),
                            String.format(Locale.US, "%.2f €", subHoras));
                }
            }
        } else {
            PdfPCell vacio = new PdfPCell(new Phrase("Sin líneas de materiales o mano de obra imputadas.", fontNormal));
            vacio.setColspan(5);
            vacio.setPadding(8);
            tablaConceptos.addCell(vacio);
        }

        document.add(tablaConceptos);

        // Totales (Base Imponible + 21% IVA)
        double iva = baseImponible * 0.21;
        double total = baseImponible + iva;

        Paragraph pTotales = new Paragraph(
                String.format(Locale.US, "Base Imponible: %.2f €   |   IVA (21%%): %.2f €   |   TOTAL ALBARÁN: %.2f €",
                        baseImponible, iva, total),
                fontNegrita);
        pTotales.setAlignment(Element.ALIGN_RIGHT);
        pTotales.setSpacingAfter(24);
        document.add(pTotales);

        // Incrustación de la Firma Digital del Cliente (Base64)
        Paragraph pFirmaTitulo = new Paragraph("CONFORMIDAD Y FIRMA DEL CLIENTE:", fontNegrita);
        pFirmaTitulo.setSpacingAfter(8);
        document.add(pFirmaTitulo);

        if (parte.getFirmaCliente() != null && !parte.getFirmaCliente().isBlank()) {
            try {
                String base64Puro = parte.getFirmaCliente();
                if (base64Puro.contains(",")) {
                    base64Puro = base64Puro.split(",")[1];
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Puro.trim());
                Image firmaImg = Image.getInstance(imageBytes);
                firmaImg.scaleToFit(180, 90);
                firmaImg.setBorder(Rectangle.BOX);
                firmaImg.setBorderWidth(1f);
                firmaImg.setBorderColor(Color.LIGHT_GRAY);
                document.add(firmaImg);
            } catch (Exception ex) {
                document.add(new Paragraph("[Error al decodificar la imagen de firma]", fontNormal));
            }
        } else {
            document.add(new Paragraph("[Parte pendiente de firma por el cliente]", fontNormal));
        }

        document.close();
    }

    private static void agregarFila(PdfPTable tabla, Font font, String c1, String c2, String c3, String c4, String c5) {
        tabla.addCell(new Phrase(c1, font));
        tabla.addCell(new Phrase(c2, font));
        PdfPCell cellCant = new PdfPCell(new Phrase(c3, font));
        cellCant.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cellCant);

        PdfPCell cellPrecio = new PdfPCell(new Phrase(c4, font));
        cellPrecio.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cellPrecio);

        PdfPCell cellSub = new PdfPCell(new Phrase(c5, font));
        cellSub.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(cellSub);
    }

    private static String nvl(String val) {
        return val != null ? val : "-";
    }
}