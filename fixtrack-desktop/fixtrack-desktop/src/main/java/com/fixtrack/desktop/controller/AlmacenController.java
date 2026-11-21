package com.fixtrack.desktop.controller;

import com.fixtrack.desktop.client.ApiClient;
import com.fixtrack.desktop.model.ArticuloDto;
import com.fixtrack.desktop.utils.DesktopValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Locale;

public class AlmacenController {

    @FXML private TableView<ArticuloDto> tableArticulos;
    @FXML private TableColumn<ArticuloDto, String> colCodigo;
    @FXML private TableColumn<ArticuloDto, String> colDescripcion;
    @FXML private TableColumn<ArticuloDto, Double> colPrecio;
    @FXML private TableColumn<ArticuloDto, Integer> colStock;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private Label lblMensaje;

    private final ApiClient apiClient = new ApiClient();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockActual"));
    }

    @FXML
    public void guardarArticulo() {
        boolean codOk = DesktopValidator.isNotEmpty(txtCodigo);
        boolean descOk = DesktopValidator.isNotEmpty(txtDescripcion);
        boolean precioOk = DesktopValidator.isPositiveNumber(txtPrecio, false);
        boolean stockOk = DesktopValidator.isPositiveNumber(txtStock, true);

        if (!codOk || !descOk || !precioOk || !stockOk) {
            lblMensaje.setText("Error: Revisa los campos marcados en rojo.");
            lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }

        String codigo = txtCodigo.getText().trim().toUpperCase();
        String descripcion = txtDescripcion.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));
        int stock = Integer.parseInt(txtStock.getText().trim());

        String jsonBody = String.format(Locale.US,
                "{\"codigo\":\"%s\", \"descripcion\":\"%s\", \"precioUnitario\":%.2f, \"stockActual\":%d}",
                codigo, descripcion, precio, stock
        );

        String respuesta = apiClient.post("/articulos", jsonBody);
        if (respuesta != null) {
            txtCodigo.clear();
            txtDescripcion.clear();
            txtPrecio.clear();
            txtStock.clear();
            lblMensaje.setText("Artículo guardado correctamente.");
            lblMensaje.setStyle("-fx-text-fill: #2e7d32;");
        } else {
            lblMensaje.setText("Error al conectar con el servidor.");
            lblMensaje.setStyle("-fx-text-fill: #d32f2f;");
        }
    }
}