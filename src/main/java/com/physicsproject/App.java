package com.physicsproject;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    static Parent parent;
    static Group root;
    static Scene scene;
    static Canvas canvas;
    static GraphicsContext graphicsContext;
    static Stage stage;
    static Rectangle2D screenSize;
        
    @Override
    public void start(Stage stage) throws IOException {
        parent = FXMLLoader.load(getClass().getResource("AppWindow.fxml"));

        App.stage = stage;
        root = new Group();
        root.getChildren().add(parent);
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Particle Lab");
        stage.setResizable (false);
        stage.show();
        screenSize = Screen.getPrimary().getBounds();

        PhysicsProcess.startProcessing();
    }

    public static void main(String[] args) {
        launch(args);
    }

}