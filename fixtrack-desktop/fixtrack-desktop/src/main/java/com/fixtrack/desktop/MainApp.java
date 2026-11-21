package com.fixtrack.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/com/fixtrack/desktop/view/MainView.fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se encontró /com/fixtrack/desktop/view/MainView.fxml en resources.");
        }

        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root, 1024, 700);

        primaryStage.setTitle("FixTrack SAT - Panel de Gestión");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}