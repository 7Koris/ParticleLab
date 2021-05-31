package com.physicsproject;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ToolSettingsController {

    static ToolSettingsController mainController;

    @FXML
    private TextField radiusField;

    @FXML
    private TextField massField;

    @FXML
    private TextField fxField;

    @FXML
    private TextField fyField;

    @FXML
    private ChoiceBox<String> colorChoice;

    @FXML
    private Slider gravitySlider;

    @FXML
    private Label gLabel;

    @FXML
    private TextField durationField;

    @FXML
    void updateGravMultiplier(MouseEvent event) {
        Slider gravitySlider = (Slider) event.getSource();
        PhysicsProcess.gravityMultiplier = gravitySlider.getValue();
        gLabel.setText(((double)Math.round(1000*PhysicsProcess.gravityMultiplier)/1000)+"g");
    }

    public static double getRadius() {
        return Double.parseDouble(mainController.radiusField.getText());
    }

    public static double getMass() {
        return Double.parseDouble(mainController.massField.getText());
    }

    public static String getColor() {
        System.out.println(mainController.colorChoice.getValue());
        return mainController.colorChoice.getValue();
    }

    public static double[] getForces() {
        return new double[] {Double.parseDouble(mainController.fxField.getText()), Double.parseDouble(mainController.fyField.getText())};
    }

    public static double getDuration() {
        return Double.parseDouble(mainController.durationField.getText());
    }

    @FXML
    void initialize() {
        assert radiusField != null : "fx:id=\"radiusField\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert massField != null : "fx:id=\"massField\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert fxField != null : "fx:id=\"fxField\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert fyField != null : "fx:id=\"fyField\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert colorChoice != null : "fx:id=\"colorChoice\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert gravitySlider != null : "fx:id=\"gravitySlider\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";
        assert gLabel != null : "fx:id=\"gLabel\" was not injected: check your FXML file 'toolSettingsWindow.fxml'.";

        mainController = this;


        colorChoice.getItems().add("Lime");
        colorChoice.getItems().add("Cyan");
        colorChoice.getItems().add("Red");
        colorChoice.getItems().add("Yellow");
        colorChoice.getItems().add("Orange");
        colorChoice.getItems().add("Violet");
        colorChoice.getItems().add("Pink");
        colorChoice.getItems().add("Black");
        colorChoice.setValue("Black");

        fxField.setText("10");
        fyField.setText("10");
        durationField.setText("0.5");

        radiusField.setText("10");
        massField.setText("10");
    }
}
