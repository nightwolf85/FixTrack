package com.fixtrack.desktop.model;

import java.util.List;

public class ParteAdminDto {
    private Long idParte;
    private String fechaEmision;
    private String descripcionAveria;
    private String estado;
    private String clienteNombre;
    private String clienteNif;
    private String clienteDireccion;
    private String tecnicoAsignado;
    private String firmaCliente; // Base64 proveniente de la app móvil
    private List<LineaAlbaranDto> lineas;

    // Getters y Setters
    public Long getIdParte() { return idParte; }
    public void setIdParte(Long idParte) { this.idParte = idParte; }

    public String getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(String fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getDescripcionAveria() { return descripcionAveria; }
    public void setDescripcionAveria(String descripcionAveria) { this.descripcionAveria = descripcionAveria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteNif() { return clienteNif; }
    public void setClienteNif(String clienteNif) { this.clienteNif = clienteNif; }

    public String getClienteDireccion() { return clienteDireccion; }
    public void setClienteDireccion(String clienteDireccion) { this.clienteDireccion = clienteDireccion; }

    public String getTecnicoAsignado() { return tecnicoAsignado; }
    public void setTecnicoAsignado(String tecnicoAsignado) { this.tecnicoAsignado = tecnicoAsignado; }

    public String getFirmaCliente() { return firmaCliente; }
    public void setFirmaCliente(String firmaCliente) { this.firmaCliente = firmaCliente; }

    public List<LineaAlbaranDto> getLineas() { return lineas; }
    public void setLineas(List<LineaAlbaranDto> lineas) { this.lineas = lineas; }

    public static class LineaAlbaranDto {
        private String codigoArticulo;
        private String descripcionArticulo;
        private int unidades;
        private double precioUnitario;
        private double horasEmpleadas;

        public String getCodigoArticulo() { return codigoArticulo; }
        public void setCodigoArticulo(String codigoArticulo) { this.codigoArticulo = codigoArticulo; }

        public String getDescripcionArticulo() { return descripcionArticulo; }
        public void setDescripcionArticulo(String descripcionArticulo) { this.descripcionArticulo = descripcionArticulo; }

        public int getUnidades() { return unidades; }
        public void setUnidades(int unidades) { this.unidades = unidades; }

        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

        public double getHorasEmpleadas() { return horasEmpleadas; }
        public void setHorasEmpleadas(double horasEmpleadas) { this.horasEmpleadas = horasEmpleadas; }
    }
}