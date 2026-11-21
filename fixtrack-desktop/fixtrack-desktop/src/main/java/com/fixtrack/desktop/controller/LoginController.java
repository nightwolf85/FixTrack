package com.fixtrack.desktop.controller;

import com.fixtrack.desktop.utils.DesktopValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblError;

    @FXML
    public void handleLogin() {
        boolean emailOk = DesktopValidator.isNotEmpty(tfEmail) && DesktopValidator.isValidEmail(tfEmail);
        boolean passOk = DesktopValidator.isNotEmpty(pfPassword);

        if (!emailOk || !passOk) {
            lblError.setText("Error: Introduce un correo válido y tu contraseña.");
            lblError.setStyle("-fx-text-fill: #d32f2f;");
            return;
        }

        lblError.setText("");
        // Aquí llamas a apiClient.post("/auth/login", ...) con las credenciales
    }
}