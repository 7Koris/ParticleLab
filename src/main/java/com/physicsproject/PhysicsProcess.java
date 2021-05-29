package com.physicsproject;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;

interface Process {
    void start();
}

public class PhysicsProcess extends AnimationTimer {

    static ArrayList<Process> processes = new ArrayList<Process>();
    static PhysicsProcess mainProcess = new PhysicsProcess();
    static double deltaTime = System.nanoTime();
    static long pastTimeNanos = System.nanoTime();
    static final double g = 980;

    PhysicsProcess(Process process) {
        processes.add(process);
    }

    PhysicsProcess() {}

    public static void startProcessing() {
       mainProcess.start();
    }

    public static void stopProcessing() {
        mainProcess.stop();
    }

    @Override
    public void handle(long now) {

        deltaTime = (double) Math.pow(10, -9) * ((double)now - (double) pastTimeNanos);

        for (Process process : processes) {
            process.start();
        }

        pastTimeNanos = now;
    }
}