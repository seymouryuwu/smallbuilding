package com.smallbuilding.smallbuilding.model;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Room is the parent class of Apartment and CommonRoom.
 * The initial temperature of a room is a random double value between 10.0 (inclusive) and 40.0 (exclusive).
 */
public abstract class Room {
    private int id;
    private double temperature; // Round to 1 decimal for letting temperature change based on room status.
    private boolean heatingEnabled;
    private boolean coolingEnabled;

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public Room() {
        Random rand = new Random();
        double temperatureMin = 10.0;
        double temperatureMax = 40.0;

        this.temperature = Double.parseDouble(df.format(temperatureMin + rand.nextDouble() * (temperatureMax - temperatureMin)));


        // Let room temperature changes slowly based on status.
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //System.out.println(id + "room heating:" + heatingEnabled);
                //System.out.println(id + "room cooling:" + coolingEnabled);
                if (heatingEnabled) {
                    temperature = Double.parseDouble(df.format(temperature + 0.1));
                } else if (coolingEnabled) {
                    temperature = Double.parseDouble(df.format(temperature - 0.1));
                }
            }
        };

        timer.schedule(timerTask, 0, 1000L);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isHeatingEnabled() {
        return heatingEnabled;
    }

    public void setHeatingEnabled(boolean heatingEnabled) {
        this.heatingEnabled = heatingEnabled;
    }

    public boolean isCoolingEnabled() {
        return coolingEnabled;
    }

    public void setCoolingEnabled(boolean coolingEnabled) {
        this.coolingEnabled = coolingEnabled;
    }
}
