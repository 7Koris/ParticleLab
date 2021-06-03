package com.physicsproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AppController {

    static boolean showID = false;
    static boolean paused = false;
    static boolean disableBound = false;
    static AppController mainController;

    static Particle editedParticleInstance;
    static ObservableList<Particle> trackedParticles = FXCollections.observableArrayList();

    Stage helpStage;
    Stage toolStage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Menu helpItem;


    @FXML MenuItem toolSettingsItem;

    @FXML
    private Menu helpItem1;

    @FXML
    private ChoiceBox<String> toolSelect;

    @FXML
    private CheckBox pausedCheckBox;

    @FXML
    private Label timeLabel;

    @FXML
    private Button resetTimeButton;

    @FXML
    private Button pUpdateButton;

    @FXML
    private TextField axField;

    @FXML
    private TextField ayField;

    @FXML
    private TextField vxField;

    @FXML
    private TextField vyField;

    @FXML
    public TextField pxField;

    @FXML
    public TextField pyField;

    @FXML
    private Button resetButton;

    @FXML
    private CheckBox boundaryCheckbox;

    @FXML
    public Label idLabel;

    @FXML
    private TableView<Particle> particleTable;

    @FXML
    private TableColumn<Particle, Integer> idColumn;

    @FXML
    private TableColumn<Particle, Double> massColumn;

    @FXML
    private TableColumn<Particle, Double> radiusColumn;

    @FXML
    private TableColumn<Particle, Double> axColumn;

    @FXML
    private TableColumn<Particle, Double> ayColumn;

    @FXML
    private TableColumn<Particle, Double> vxColumnrticle;

    @FXML
    private TableColumn<Particle, Double> vyColumn;

    @FXML
    private TableColumn<Particle, Double> xColumn;

    @FXML
    private TableColumn<Particle, Double> yColumn;

    @FXML
    private CheckBox idCheckBox;

    @FXML
    private Button closeButton;

    @FXML
    void closeApp(ActionEvent event) {

    }

    @FXML
    void resetAll(MouseEvent event) {
        Particle[] allParticles = new Particle[Particle.particles.size()];
        int i = 0;
        for (Particle particle : Particle.particles) {
            allParticles[i] = particle;
            i++;
        }
        for (Particle particle : allParticles) {
            Particle.deleteParticle(particle);
        }
    }

    @FXML
    void resetTime(MouseEvent event) {
        PhysicsProcess.time = 0;
        updateTime(PhysicsProcess.time);
    }

    @FXML
    void toggleBoundary(ActionEvent event) {
        CheckBox toggleBound = (CheckBox) event.getSource();
        disableBound = toggleBound.isSelected();
    }

    @FXML
    void togglePause(ActionEvent event) {
        CheckBox pausedCheckbox = (CheckBox) event.getSource();
        paused = pausedCheckbox.isSelected();
    }

    @FXML
    private Pane simPane;

    @FXML
    void addParticle(MouseEvent event) {
        if (getTool().equals("Add Particle")) {
            new Particle(ToolSettingsController.getRadius(), ToolSettingsController.getMass(), event.getX(), event.getY()+30, ToolSettingsController.getColor());
        }
    }

    @FXML
    void openToolSettings(ActionEvent event) {
        toolStage.show();
    }

    @FXML
    void openHelp(ActionEvent event) throws IOException {
        helpStage.show();
    }



    @FXML
    void updateParticle(MouseEvent event) {
        double ax = Double.parseDouble(axField.getText());
        double ay = Double.parseDouble(ayField.getText());
        double vx = Double.parseDouble(vxField.getText());
        double vy = Double.parseDouble(vyField.getText());
        double px = Double.parseDouble(pxField.getText());
        double py = Double.parseDouble(pyField.getText());
        editedParticleInstance.updateParticle(ax, ay, vx, vy, px, py);
    }

    static void updateParticleFields(double ax, double ay, double vx, double vy, double px, double py) {
        mainController.axField.setText(Double.toString(ax));
        mainController.ayField.setText(Double.toString(ay));
        mainController.vxField.setText(Double.toString(vx));
        mainController.vyField.setText(Double.toString(vy));
        mainController.pxField.setText(Double.toString(px));
        mainController.pyField.setText(Double.toString(py));
    }

    static void trackNewParticle(Particle particle) {
        if (particle.tracked == true) {
            trackedParticles.add(particle);
            mainController.particleTable.setItems(trackedParticles);
        } else {
            trackedParticles.remove(particle);
            mainController.particleTable.refresh();
        }
        
    }

    @FXML
    void showId(ActionEvent event) {
        CheckBox idCheckbox = (CheckBox) event.getSource();
        showID = idCheckbox.isSelected();
    }

    static public String getTool() {
        return mainController.toolSelect.getValue();
    }

    static public void refreshTable() {
        mainController.particleTable.refresh();
    }

    static void updateTime(double time) {
        time = (double)Math.round(1000*time)/1000;
        mainController.timeLabel.setText("Time: " + Double.toString(time));
    }

    @FXML
    void initialize() throws IOException {
        assert helpItem != null : "fx:id=\"helpItem\" was not injected: check your FXML file 'Untitled'.";
        assert toolSettingsItem != null : "fx:id=\"toolSettingsItem\" was not injected: check your FXML file 'Untitled'.";
        assert helpItem1 != null : "fx:id=\"helpItem1\" was not injected: check your FXML file 'Untitled'.";
        assert toolSelect != null : "fx:id=\"toolSelect\" was not injected: check your FXML file 'Untitled'.";
        assert pausedCheckBox != null : "fx:id=\"pausedCheckBox\" was not injected: check your FXML file 'Untitled'.";
        assert timeLabel != null : "fx:id=\"timeLabel\" was not injected: check your FXML file 'Untitled'.";
        assert resetTimeButton != null : "fx:id=\"resetTimeButton\" was not injected: check your FXML file 'Untitled'.";
        assert pUpdateButton != null : "fx:id=\"pUpdateButton\" was not injected: check your FXML file 'Untitled'.";
        assert axField != null : "fx:id=\"axField\" was not injected: check your FXML file 'Untitled'.";
        assert ayField != null : "fx:id=\"ayField\" was not injected: check your FXML file 'Untitled'.";
        assert vxField != null : "fx:id=\"vxField\" was not injected: check your FXML file 'Untitled'.";
        assert vyField != null : "fx:id=\"vyField\" was not injected: check your FXML file 'Untitled'.";
        assert pxField != null : "fx:id=\"pxField\" was not injected: check your FXML file 'Untitled'.";
        assert pyField != null : "fx:id=\"pyField\" was not injected: check your FXML file 'Untitled'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'Untitled'.";
        assert boundaryCheckbox != null : "fx:id=\"boundaryCheckbox\" was not injected: check your FXML file 'Untitled'.";
        assert idLabel != null : "fx:id=\"idLabel\" was not injected: check your FXML file 'Untitled'.";
        assert idCheckBox != null : "fx:id=\"idCheckBox\" was not injected: check your FXML file 'Untitled'.";
        assert particleTable != null : "fx:id=\"particleTable\" was not injected: check your FXML file 'Untitled'.";
        assert idColumn != null : "fx:id=\"idColumn\" was not injected: check your FXML file 'Untitled'.";
        assert massColumn != null : "fx:id=\"massColumn\" was not injected: check your FXML file 'Untitled'.";
        assert radiusColumn != null : "fx:id=\"radiusColumn\" was not injected: check your FXML file 'Untitled'.";
        assert axColumn != null : "fx:id=\"axColumn\" was not injected: check your FXML file 'Untitled'.";
        assert ayColumn != null : "fx:id=\"ayColumn\" was not injected: check your FXML file 'Untitled'.";
        assert vxColumnrticle != null : "fx:id=\"vxColumnrticle\" was not injected: check your FXML file 'Untitled'.";
        assert vyColumn != null : "fx:id=\"vyColumn\" was not injected: check your FXML file 'Untitled'.";
        assert xColumn != null : "fx:id=\"xColumn\" was not injected: check your FXML file 'Untitled'.";
        assert yColumn != null : "fx:id=\"yColumn\" was not injected: check your FXML file 'Untitled'.";
        assert simPane != null : "fx:id=\"simPane\" was not injected: check your FXML file 'Untitled'.";

        toolSelect.getItems().add("Edit Particle");
        toolSelect.getItems().add("Move Particle");
        toolSelect.getItems().add("Add Particle");
        toolSelect.getItems().add("Delete Particle");
        toolSelect.getItems().add("Add Force");
        toolSelect.getItems().add("Track Particle");

        idColumn.setCellValueFactory(new PropertyValueFactory<Particle, Integer>("simpleID"));
        massColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleMass"));
        radiusColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleRadius"));
        axColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleAX"));
        ayColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleAY"));
        vxColumnrticle.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleVX"));
        vyColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleVY"));
        xColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleX"));
        yColumn.setCellValueFactory(new PropertyValueFactory<Particle, Double>("simpleY"));

        toolSelect.setValue("Move Particle");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HelpWindow.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1)); 
        stage.setTitle("User Guide");
        helpStage = stage; 

        fxmlLoader = new FXMLLoader(getClass().getResource("toolSettingsWindow.fxml"));
        root1 = (Parent) fxmlLoader.load();
        stage = new Stage();
        stage.setScene(new Scene(root1)); 
        stage.setTitle("User Guide");
        toolStage = stage;

        

        mainController = this;
    }
}
