package com.physicsproject;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;


public class App extends Application {

    private Parent createContent() {
        return new StackPane(new Text("Hello World"));
    }


    public void start(Stage stage) {
        stage.setScene(new Scene(createContent(), 300, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}