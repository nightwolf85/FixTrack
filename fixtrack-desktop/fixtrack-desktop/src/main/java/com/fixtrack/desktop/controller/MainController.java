package com.fixtrack.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void cargarDashboard(ActionEvent event) {
        cargarVista("/com/fixtrack/desktop/view/DashboardView.fxml");
    }

    @FXML
    public void cargarClientes(ActionEvent event) {
        cargarVista("/com/fixtrack/desktop/view/ClientesView.fxml");
    }

    @FXML
    public void cargarAlmacen(ActionEvent event) {
        cargarVista("/com/fixtrack/desktop/view/AlmacenView.fxml");
    }

    @FXML
    public void cargarPartes(ActionEvent event) {
        cargarVista("/com/fixtrack/desktop/view/PartesView.fxml");
    }

    private void cargarVista(String fxmlPath) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
