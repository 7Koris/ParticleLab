package com.physicsproject;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Particle extends PhysicsObject{
    static ArrayList<Particle> particles = new ArrayList<Particle>();
    double radius;
    private PhysicsProcess physicsProcess;
    private Label idLabel = new Label();
    boolean tracked = false;
    double duration = 0;
    double fx = 0;
    double fy = 0;
    double fg = 0;
    double interfx = 0;
    double interfy = 0;
    boolean inter = false;

    SimpleIntegerProperty simpleID = new SimpleIntegerProperty();
    SimpleDoubleProperty simpleMass = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleRadius = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleAX = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleAY = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleVX = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleVY = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleX = new SimpleDoubleProperty();
    SimpleDoubleProperty simpleY = new SimpleDoubleProperty();

    Circle circle;
    String color = "Black";
    int id = 0;

    static int idCount = 0;
    Particle currentParticle; //Sketchy but it works

    public Particle(double radius, double mass, double x, double y, String color) {
        this.position[0] = x;
        this.position[1] = y;
        this.radius = radius;
        this.mass = mass;
        this.id = idCount;
        idCount++;
        this.color = color;

        

        physicsProcess = new PhysicsProcess(this, id);
        circle = new Circle();

        switch (this.color) {
            case "Black":
                circle.setFill(Color.BLACK);
                break;
            case "Lime":
                circle.setFill(Color.LIME);
                break;
            case "Cyan":
                circle.setFill(Color.CYAN);
                break;
            case "Red":
                circle.setFill(Color.RED);
                break;
            case "Yellow":
                circle.setFill(Color.YELLOW);
                break;
            case "Orange":
                circle.setFill(Color.ORANGE);
                break;
            case "Violet":
                circle.setFill(Color.VIOLET);
                break;
            case "Pink":
                circle.setFill(Color.PINK);
                break;
            default:
                circle.setFill(Color.BLACK);
                break;
        }

        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);

        App.root.getChildren().add(circle);
        App.root.getChildren().add(idLabel);

        this.idLabel.setText(Integer.toString(id));

        particles.add(this);
        this.circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, eventHandler);
        this.circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        currentParticle = this;
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            switch (AppController.getTool()) {
                case "Edit Particle":
                    AppController.editedParticleInstance = currentParticle;
                    AppController.mainController.idLabel.setText(id + "");
                    double ax = accelerationVector.components[0];
                    double ay = accelerationVector.components[1];
                    double vx = velocityVector.components[0];
                    double vy = velocityVector.components[1];
                    double px = position[0];
                    double py = position[1];
                    AppController.updateParticleFields(ax, ay, vx, vy, px, py);
                    circle.setCenterX((float)position[0]);
                    circle.setCenterY((float)position[1]);

                    idLabel.setTranslateX(position[0]-3);
                    idLabel.setTranslateY(position[1] - radius - 15);
                    break;
                case "Move Particle":
                    position[0] = e.getX();
                    position[1] = e.getY();

                    circle.setCenterX((float)position[0]);
                    circle.setCenterY((float)position[1]);

                    idLabel.setTranslateX(position[0]-3);
                    idLabel.setTranslateY(position[1] - radius - 15);

                    velocityVector.components[0] = 0;
                    velocityVector.components[1] = 0;
                    break;
                case "Add Particle":
                    break;
                case "Delete Particle":
                    deleteParticle(currentParticle);
                    break;
                case "Add Force":
                    duration = ToolSettingsController.getDuration();
                    double[] forces = ToolSettingsController.getForces();
                    fx = forces[0];
                    fy = forces[1];
                    break;
                case "Track Particle":
                    if (tracked == false) {
                        tracked = true;
                        AppController.trackNewParticle(currentParticle);
                    } else {
                        tracked = false;
                        AppController.trackNewParticle(currentParticle);
                    }
                    break;
                default:
                    System.out.println(e);
                    break;
            }  
         } 
    };   

    public static void deleteParticle (Particle currentParticle) {
        currentParticle.tracked = false;
        AppController.trackNewParticle(currentParticle);
        Particle.particles.remove(currentParticle);
        System.out.println(particles.indexOf(currentParticle));
        PhysicsProcess.removeProcess(currentParticle.id);
        App.root.getChildren().remove(currentParticle.circle);
        App.root.getChildren().remove(currentParticle.idLabel);
        currentParticle.radius = 0;
        currentParticle.accelerationVector = null;
        currentParticle.velocityVector = null;
        currentParticle.position = null;
        currentParticle.idLabel = null;
        currentParticle.circle = null;
        currentParticle.physicsProcess = null;
        currentParticle.mass = 0;
        currentParticle = null;
    }

    public Integer getSimpleID() {
        simpleID.set(id);
        return simpleID.get();
    }

    public Double getSimpleMass() {
        simpleMass.set(mass);
        return simpleMass.get();
    }

    public Double getSimpleRadius() {
        simpleRadius.set(radius);
        return simpleRadius.get();
    }

    public Double getSimpleAX() {
        return simpleAX.get();
    }

    public Double getSimpleAY() {
        return simpleAY.get();
    }

    public Double getSimpleVX() {
        return simpleVX.get();
    }

    public Double getSimpleVY() {
        return simpleVY.get();
    }
    
    public Double getSimpleX() {
        return simpleX.get();
    }

    public Double getSimpleY() {
        return simpleY.get();
    }

    

    public void updateParticle(double ax, double ay, double vx, double vy, double px, double py) {
        this.accelerationVector.components = new double[] {ax, ay};
        this.velocityVector.components = new double[] {vx, vy};
        this.position = new double[] {px, py};
        circle.setCenterX((float)this.position[0]);
        circle.setCenterY((float)this.position[1]);
        this.idLabel.setTranslateX(this.position[0]-3);
        this.idLabel.setTranslateY(this.position[1] - this.radius - 15);
    }


    @Override
    public void start() {

        this.idLabel.setVisible(AppController.showID);
        
        if (!AppController.paused) {

        if (!(PhysicsProcess.deltaTime > 1))         

            if (duration > 0) {
                duration -= PhysicsProcess.deltaTime;
                this.velocityVector.components[0] += 100 * (fx/mass) * PhysicsProcess.deltaTime;
                this.velocityVector.components[1] += 100 * (fy/mass) * PhysicsProcess.deltaTime;
            } else {
                fx = 0;
                fy = 0;
            }

            fg = PhysicsProcess.g * PhysicsProcess.gravityMultiplier;
            this.velocityVector.components[1] += fg * PhysicsProcess.deltaTime;

            this.velocityVector.components[0] += (100 * this.accelerationVector.components[0] * PhysicsProcess.deltaTime);
            this.velocityVector.components[1] += (100 * this.accelerationVector.components[1] * PhysicsProcess.deltaTime);

            if (AppController.dragEnabled) {
                this.velocityVector.components[0] += -1 * Double.parseDouble(AppController.mainController.dragCoeffLabel.getText()) * PhysicsProcess.deltaTime * mass * velocityVector.components[0];
                this.velocityVector.components[1] += -1 * Double.parseDouble(AppController.mainController.dragCoeffLabel.getText()) * PhysicsProcess.deltaTime * mass * velocityVector.components[1];
            }

            this.position[0] += (this.velocityVector.components[0] * (double) PhysicsProcess.deltaTime);
            this.position[1] += (this.velocityVector.components[1] * (double) PhysicsProcess.deltaTime);
            
            //Particle collision algorithm was taken from https://www.youtube.com/watch?v=LPzyNOHY3A4.
            for (Particle particle : particles) {
                if (particle != this && !AppController.paused && particle!= null && this != null) {
                    double radialDistance = Math.sqrt(Math.pow(this.position[0]-particle.position[0], 2) + Math.pow(this.position[1] - particle.position[1], 2));
                    double distance = 0.5*(radialDistance - (this.radius + particle.radius));
                    
                    if (distance <= 0) {
                        
                        this.position[0] -= (distance) * (this.position[0] - particle.position[0]) / (radialDistance);
                        this.position[1] -= (distance) * (this.position[1] - particle.position[1]) / (radialDistance);

                        particle.position[0] += (distance) * (this.position[0] - particle.position[0]) / (radialDistance);
                        particle.position[1] += (distance) * (this.position[1] - particle.position[1]) / (radialDistance);

                        radialDistance = Math.sqrt(Math.pow(this.position[0]-particle.position[0], 2) + Math.pow(this.position[1] - particle.position[1], 2));
                        
                        double normx = (particle.position[0] - this.position[0]) / radialDistance;
                        double normy = (particle.position[1] - this.position[1]) / radialDistance;

                        double tanx = -normy;
                        double tany = normx;

                        double dotptan1 = this.velocityVector.components[0] * tanx + this.velocityVector.components[1] * tany;
                        double dotptan2 = particle.velocityVector.components[0] * tanx + particle.velocityVector.components[1] * tany;

                        double dotpnorm1 = this.velocityVector.components[0] * normx + this.velocityVector.components[1] * normy;
                        double dotpnorm2 = particle.velocityVector.components[0] * normx + particle.velocityVector.components[1] * normy;

                        double m1 = (dotpnorm1 * (this.mass - particle.mass) + 2 * particle.mass*dotpnorm2) / (this.mass + particle.mass);
                        double m2 = (dotpnorm2 * (particle.mass - this.mass) + 2 * this.mass*dotpnorm1) / (this.mass + particle.mass);

                        this.velocityVector.components[0] = tanx * dotptan1 + normx * m1;
                        this.velocityVector.components[1] = tany * dotptan1 + normy * m1;

                        particle.velocityVector.components[0] = tanx * dotptan2 + normx * m2;
                        particle.velocityVector.components[1] = tany * dotptan2 + normy * m2;
                    }
                }
            }

            

            double height = 379;
            double width = 752;

            //Wall Collisions

            if (!AppController.disableBound) {
                if  (this.position[0] + radius >= width) {
                    this.position[0] = width-radius;
                    this.velocityVector.components[0] = (this.velocityVector.components[0] > -this.velocityVector.components[0]) ? -this.velocityVector.components[0] : this.velocityVector.components[0];
                } else if (this.position[0] - radius <= 0) {
                    this.position[0] = radius;
                    this.velocityVector.components[0] = (this.velocityVector.components[0] < -this.velocityVector.components[0]) ? -this.velocityVector.components[0] : this.velocityVector.components[0];
                }   

                if (this.position[1] + radius >= height) {
                    this.position[1] = height-radius;
                    this.velocityVector.components[1] = (this.velocityVector.components[1] > -this.velocityVector.components[1]) ? -this.velocityVector.components[1] : this.velocityVector.components[1];
                } else if (this.position[1] - radius <= 25) {
                    this.position[1] = 25 + radius;
                    this.velocityVector.components[1] = (this.velocityVector.components[1] < -this.velocityVector.components[1]) ? -this.velocityVector.components[1] : this.velocityVector.components[1];
                }
            }
            
            if (this == AppController.editedParticleInstance) {
                AppController.mainController.pxField.setText(Double.toString(position[0]));
                AppController.mainController.pyField.setText(Double.toString(position[1]));
            }
            
            circle.setCenterX((float)this.position[0]);
            circle.setCenterY((float)this.position[1]);
            this.idLabel.setTranslateX(this.position[0]-3);
            this.idLabel.setTranslateY(this.position[1] - this.radius - 15);

            
            
        }

        simpleAX.set(accelerationVector.components[0] + (fx/mass) + (interfx/mass));
            simpleAY.set(accelerationVector.components[1] + (fy/mass) + fg + (interfy/mass));
            simpleVX.set(velocityVector.components[0]);
            simpleVY.set(velocityVector.components[1]);
            simpleX.set(position[0]);
            simpleY.set(position[1]);
    }
    }
