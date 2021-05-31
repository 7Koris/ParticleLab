package com.physicsproject;

import java.util.ArrayList;
import java.util.Hashtable;

import javafx.animation.AnimationTimer;

interface Process {
    void start();
}

public class PhysicsProcess extends AnimationTimer {

    static ArrayList<Process> processes = new ArrayList<Process>();
    static Hashtable<Integer, Process> processTable = new Hashtable<Integer, Process>();
    static PhysicsProcess mainProcess = new PhysicsProcess();
    static double deltaTime = System.nanoTime();
    static long pastTimeNanos = System.nanoTime();
    static final double g = 980;
    static double gravityMultiplier = 0;
    static double dragCoeff = 1;
    static double time = 0;
    static final double gravitationalConstant = 6.67408 * Math.pow(10, -11);

    PhysicsProcess(Process process, int id) {
        processTable.put(id, process);
        processes.add(process);
    }

    PhysicsProcess() {}

    public static void startProcessing() {
       mainProcess.start();
    }

    public static void stopProcessing() {
        mainProcess.stop();
    }

    public static void removeProcess(int id) {
        processes.remove(processTable.get(id));
        processTable.remove(id);

    }

    @Override
    public void handle(long now) {

        deltaTime = (double) Math.pow(10, -9) * ((double)now - (double) pastTimeNanos);

        if (!AppController.paused) {
            time += deltaTime;
            AppController.updateTime(time);
        }

        for (Process process : processes) {

            try {
                process.start();
            } catch (NullPointerException e) {
                System.out.println("stuck");
            }
        }

        AppController.refreshTable();

        pastTimeNanos = now;
    }
}