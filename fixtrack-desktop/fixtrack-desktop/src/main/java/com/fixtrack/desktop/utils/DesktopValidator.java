package com.fixtrack.desktop.utils;

import javafx.scene.control.Control;
import javafx.scene.control.TextInputControl;

import java.util.regex.Pattern;

public class DesktopValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern NIF_CIF_PATTERN = Pattern.compile("^([0-9]{8}[A-Z]|[A-Z][0-9]{7}[0-9A-Z])$");

    private static void setFieldErrorStyle(Control control, boolean hasError) {
        if (hasError) {
            control.setStyle("-fx-border-color: #d32f2f; -fx-border-width: 1.5px;");
        } else {
            control.setStyle("");
        }
    }

    public static boolean isNotEmpty(TextInputControl field) {
        boolean valid = field.getText() != null && !field.getText().trim().isEmpty();
        setFieldErrorStyle(field, !valid);
        return valid;
    }

    public static boolean isValidNif(TextInputControl field) {
        String text = field.getText() != null ? field.getText().trim().toUpperCase() : "";
        boolean valid = NIF_CIF_PATTERN.matcher(text).matches();
        setFieldErrorStyle(field, !valid);
        return valid;
    }

    public static boolean isValidEmail(TextInputControl field) {
        String text = field.getText() != null ? field.getText().trim() : "";
        boolean valid = text.isEmpty() || EMAIL_PATTERN.matcher(text).matches();
        setFieldErrorStyle(field, !valid);
        return valid;
    }

    public static boolean isPositiveNumber(TextInputControl field, boolean allowZero) {
        try {
            double val = Double.parseDouble(field.getText().trim().replace(",", "."));
            boolean valid = allowZero ? val >= 0 : val > 0;
            setFieldErrorStyle(field, !valid);
            return valid;
        } catch (Exception e) {
            setFieldErrorStyle(field, true);
            return false;
        }
    }
}