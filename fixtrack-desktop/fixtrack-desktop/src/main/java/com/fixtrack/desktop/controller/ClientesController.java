package com.fixtrack.desktop.controller;

import com.fixtrack.desktop.client.ApiClient;
import com.fixtrack.desktop.utils.DesktopValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientesController {

    @FXML private TableView<?> tableClientes;
    @FXML private TableColumn<?, ?> colNombre;
    @FXML private TableColumn<?, ?> colNif;
    @FXML private TableColumn<?, ?> colTelefono;
    @FXML private TableColumn<?, ?> colDireccion;

    @FXML private TextField txtNombre;
    @FXML private TextField txtNif;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;

    // Si tienes un Label de errores en la vista, puedes inyectarlo aquí:
    // @FXML private Label lblMensajeError;

    private final ApiClient apiClient = new ApiClient();

    @FXML
    public void guardarCliente() {
        // Ejecutamos las validaciones de los controles de texto gráficos
        boolean nombreOk = DesktopValidator.isNotEmpty(txtNombre);
        boolean nifOk = DesktopValidator.isValidNif(txtNif);
        boolean telefonoOk = DesktopValidator.isNotEmpty(txtTelefono);
        boolean direccionOk = DesktopValidator.isNotEmpty(txtDireccion);

        if (!nombreOk || !nifOk || !telefonoOk || !direccionOk) {
            System.out.println("Validación fallida: Revisa los campos marcados en rojo.");
            // if (lblMensajeError != null) lblMensajeError.setText("Por favor, corrige los campos erróneos.");
            return;
        }

        // Si pasa las validaciones, procedemos con el envío
        String nombre = txtNombre.getText().trim();
        String nif = txtNif.getText().trim().toUpperCase();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        String jsonBody = String.format(
                "{\"nombre\":\"%s\", \"nif\":\"%s\", \"telefono\":\"%s\", \"direccion\":\"%s\"}",
                nombre, nif, telefono, direccion
        );

        String respuesta = apiClient.post("/clientes", jsonBody);
        if (respuesta != null) {
            txtNombre.clear();
            txtNif.clear();
            txtTelefono.clear();
            txtDireccion.clear();
            System.out.println("Cliente guardado con éxito.");
        } else {
            System.out.println("Error al guardar el cliente en el servidor.");
        }
    }
}