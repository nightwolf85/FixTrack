package com.fixtrack.desktop.controller;

import com.fixtrack.desktop.client.ApiClient;
import com.fixtrack.desktop.model.ParteAdminDto;
import com.fixtrack.desktop.utils.DesktopValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PartesController {

    @FXML private TableView<ParteAdminDto> tablePartes;
    @FXML private TableColumn<ParteAdminDto, Long> colId;
    @FXML private TableColumn<ParteAdminDto, String> colCliente;
    @FXML private TableColumn<ParteAdminDto, String> colDescripcion;
    @FXML private TableColumn<ParteAdminDto, String> colTecnico;
    @FXML private TableColumn<ParteAdminDto, String> colEstado;

    @FXML private TextField txtIdCliente;
    @FXML private TextField txtIdTecnico;
    @FXML private TextArea txtDescripcionAveria;
    @FXML private Label lblMensaje;

    private final ApiClient apiClient = new ApiClient();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idParte"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("clienteNombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcionAveria"));
        colTecnico.setCellValueFactory(new PropertyValueFactory<>("tecnicoAsignado"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML
    public void crearYAsignarParte() {
        boolean clienteOk = DesktopValidator.isPositiveNumber(txtIdCliente, false);
        boolean tecnicoOk = DesktopValidator.isPositiveNumber(txtIdTecnico, false);
        boolean descOk = DesktopValidator.isNotEmpty(txtDescripcionAveria);

        if (!clienteOk || !tecnicoOk || !descOk) {
            lblMensaje.setText("Error: Indica ID de cliente, ID de técnico y descripción de avería.");
            lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }

        long idCliente = Long.parseLong(txtIdCliente.getText().trim());
        long idTecnico = Long.parseLong(txtIdTecnico.getText().trim());
        String descripcion = txtDescripcionAveria.getText().trim().replace("\"", "\\\"");

        String jsonBody = String.format(
                "{\"idCliente\":%d, \"idUsuarioTecnico\":%d, \"descripcionAveria\":\"%s\", \"estado\":\"PENDIENTE\"}",
                idCliente, idTecnico, descripcion
        );

        String respuesta = apiClient.post("/partes", jsonBody);
        if (respuesta != null) {
            txtIdCliente.clear();
            txtIdTecnico.clear();
            txtDescripcionAveria.clear();
            lblMensaje.setText("Parte creado y asignado correctamente.");
            lblMensaje.setStyle("-fx-text-fill: #2e7d32;");
        } else {
            lblMensaje.setText("Error al registrar el parte en el servidor.");
            lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
        }
    }

    @FXML
    public void exportarAlbaranPdf() {
        ParteAdminDto seleccionado = tablePartes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Selecciona un parte de la tabla para generar su albarán.");
            lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }

        if (!"FINALIZADO".equalsIgnoreCase(seleccionado.getEstado())) {
            lblMensaje.setText("Aviso: El parte aún no está FINALIZADO (puede no incluir firma).");
            lblMensaje.setStyle("-fx-text-fill: #ed6c02;");
        }

        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Guardar Albarán en PDF");
        fileChooser.setInitialFileName("Albaran_Parte_" + seleccionado.getIdParte() + ".pdf");
        fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("Documentos PDF (*.pdf)", "*.pdf")
        );

        java.io.File archivoDestino = fileChooser.showSaveDialog(tablePartes.getScene().getWindow());
        if (archivoDestino != null) {
            try {
                com.fixtrack.desktop.service.PdfAlbaranService.generarAlbaran(seleccionado, archivoDestino);
                lblMensaje.setText("Albarán exportado correctamente en: " + archivoDestino.getName());
                lblMensaje.setStyle("-fx-text-fill: #2e7d32;");
            } catch (Exception e) {
                e.printStackTrace();
                lblMensaje.setText("Error al generar el PDF: " + e.getMessage());
                lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
            }
        }
    }
}