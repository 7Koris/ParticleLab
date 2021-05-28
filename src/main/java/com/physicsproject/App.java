package com.physicsproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class App extends Application {

    static Group root;
    static Scene scene;
    static Canvas canvas;
    static GraphicsContext graphicsContext;
    static Stage stage;
        
    @Override
    public void start(Stage stage) {
        App.stage = stage;
        root = new Group();
        scene = new Scene(root);
        stage.setScene(scene);
        canvas = new Canvas(960, 540);
        root.getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        stage.setTitle("Physics Sim");
        stage.show();

        PhysicsProcess.startProcessing();
    }

    public static void main(String[] args) {
        launch(args);
    }

}