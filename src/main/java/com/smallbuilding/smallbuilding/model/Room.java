package com.smallbuilding.smallbuilding.model;

import java.util.Random;

/**
 * Room is the parent class of Apartment and CommonRoom.
 * The initial temperature of a room is a random double value between 10.0 (inclusive) and 40.0 (exclusive).
 */
public abstract class Room {
    private int id;
    private double temperature;
    private boolean heatingEnabled;
    private boolean coolingEnabled;

    public Room() {
        Random rand = new Random();
        double temperatureMin = 10.0;
        double temperatureMax = 40.0;

        this.temperature = temperatureMin + rand.nextDouble() * (temperatureMax - temperatureMin);
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
