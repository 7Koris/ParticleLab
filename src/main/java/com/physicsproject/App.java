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

        //ArrayList<Particle> p = new ArrayList<Particle>();

        // for (int i = 0; i < 1000; i++) {
        //     Random s = new Random();
        //     double height = App.screenSize.getHeight() - 500;
        //     double width = App.screenSize.getWidth() - 1000;


            
            
        //     p.add(new Particle(1, 1, s.nextInt((int)width), s.nextInt((int)height)));
        //     p.get(p.size()-1).velocityVector.setComponents(new double[] {ThreadLocalRandom.current().nextInt(-60, 60), ThreadLocalRandom.current().nextInt(-60, 60)});
        // }

        // Particle p = new Particle(10, 1, 70, 50);
        // Particle b = new Particle(10, 1, 50, 50);
    



        PhysicsProcess.startProcessing();
    }

    public static void main(String[] args) {
        launch(args);
    }

}