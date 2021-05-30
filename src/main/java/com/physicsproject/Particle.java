package com.physicsproject;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

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
        this.mass = mass;

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

            
            //Particle collision algorithm was taken from https://www.youtube.com/watch?v=LPzyNOHY3A4.
            for (Particle particle : particles) {
                if (particle != this) {
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
            } else if (this.position[1] - radius <= 45.5) {
                this.position[1] = 45.5 - radius;
                this.velocityVector.components[1] = (this.velocityVector.components[1] < -this.velocityVector.components[1]) ? -this.velocityVector.components[1] : this.velocityVector.components[1];
            }
            
            circle.setCenterX((float)this.position[0]);
            circle.setCenterY((float)this.position[1]);
        }
    }
}