package com.physicsproject;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Particle extends PhysicsObject{
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
    }

    @Override
    public void start() {
        if (!(PhysicsProcess.deltaTime > 1)) {
            this.velocityVector.components[0] += (this.accelerationVector.components[0] * PhysicsProcess.deltaTime);
            this.velocityVector.components[1] += (this.accelerationVector.components[1] * PhysicsProcess.deltaTime);
  
            this.position[0] += (this.velocityVector.components[0] * (double) PhysicsProcess.deltaTime);
            this.position[1] += (this.velocityVector.components[1] * (double) PhysicsProcess.deltaTime);

            circle.setCenterX((float)this.position[0]);
            circle.setCenterY((float)this.position[1]);
        }
    }
}