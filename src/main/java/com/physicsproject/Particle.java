package com.physicsproject;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Particle extends PhysicsObject{
    static ArrayList<Particle> particles = new ArrayList<Particle>();
    private double radius;
    private PhysicsProcess physicsProcess;
    Circle circle;

    public Particle(double radius, double mass, double x, double y) {
        this.position[0] = x;
        this.position[1] = y;
        this.radius = radius;

        physicsProcess = new PhysicsProcess(this);
        circle = new Circle();

        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(radius);
        circle.setFill(Color.BLACK);

        App.root.getChildren().add(circle);

        particles.add(this);
        this.circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, eventHandler);
    }

    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() { 
        @Override 
        public void handle(MouseEvent e) { 
            position[0] = e.getX();
            position[1] = e.getY();
         } 
     };   

    @Override
    public void start() {
        if (!(PhysicsProcess.deltaTime > 1)) {

            this.velocityVector.components[0] += (this.accelerationVector.components[0] * PhysicsProcess.deltaTime);
            this.velocityVector.components[1] += (this.accelerationVector.components[1] * PhysicsProcess.deltaTime);
  
            this.position[0] += (this.velocityVector.components[0] * (double) PhysicsProcess.deltaTime);
            this.position[1] += (this.velocityVector.components[1] * (double) PhysicsProcess.deltaTime);

            
            //Particle Collisions
            for (Particle particle : particles) {
                if (particle != this) {
                    double radialDistance = Math.sqrt(Math.pow(this.position[0]-particle.position[0], 2) + Math.pow(this.position[1] - particle.position[1], 2));
                    double distance = 0.5*(radialDistance - (this.radius + particle.radius));
                    
                    if (distance <= 0) {
                        
                    }
                }
            }

            double height = App.screenSize.getHeight() - 500;
            double width = App.screenSize.getWidth() - 1000;

            //Wall Collisions
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
            } else if (this.position[1] - radius <= 0) {
                this.position[1] = radius;
                this.velocityVector.components[1] = (this.velocityVector.components[1] < -this.velocityVector.components[1]) ? -this.velocityVector.components[1] : this.velocityVector.components[1];
            }
            
            circle.setCenterX((float)this.position[0]);
            circle.setCenterY((float)this.position[1]);
        }
    }
}