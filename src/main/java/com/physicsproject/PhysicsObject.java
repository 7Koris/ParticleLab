package com.physicsproject;

public abstract class PhysicsObject implements Process {
    Vector velocityVector = new Vector(new double[] {0, 0});
    Vector accelerationVector = new Vector(new double[] {0, 0});
    double[] position = {0, 0};
    double mass = 0;
}