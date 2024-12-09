package com.example.biydaalt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use Parent instead of AnchorPane for more flexibility
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
        //ene code odoo hereggu
    public static void main(String[] args) {
        launch(args);
    }
}