package com.physicsproject;

import java.lang.Math;

public class Vector {
    public double[] components = {0, 0};

    public Vector(double[] components) {
        this.components = components;
    }

    public Vector(float magnitude, float angleRadians) {
        this.components[0] = magnitude * Math.cos(angleRadians);
        this.components[1] = magnitude * Math.sin(angleRadians);
    }

    public double[] getComponents() {
        return this.components;
    }

    public void setComponents(double[] components) {
        this.components = components;
    }

    public double getMagnitude() {
        return (Math.sqrt(Math.pow(components[0], 2) + Math.pow(components[1], 2)));
    }

    public double getAngleRadians() {
        return Math.atan2(components[0], components[1]);
    }

    public double getAngleDegrees() {
        return Math.toDegrees(Math.atan2(components[0], components[1]));
    }

    public static Vector multiplyVector(Vector vect, double multiplier) {
        return new Vector(new double[] {vect.components[0] * multiplier, vect.components[1] * multiplier});
    }

    public static Vector addVectors(Vector vect1, Vector vect2) {
        double[] vect1Components = vect1.getComponents();
        double[] vect2Components = vect2.getComponents();
        double[] addedComponents = {vect1Components[0] + vect2Components[0], vect1Components[1] + vect2Components[1]};
        return new Vector(addedComponents);
    }
}