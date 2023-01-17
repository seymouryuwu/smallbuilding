package com.smallbuilding.smallbuilding.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A building has a requested temperature with initial value 20.0.
 * It has a Map field rooms containing all the Apartment(s) and/or CommonRoom(s). The key of the map is the room id.
 */
public class Building {
    private double requestedTemperature;

    private int recalculateRoomStatusPeriod;

    private Map<Integer, Room> rooms;

    public Building() {
        this.requestedTemperature = 20.0;
        this.recalculateRoomStatusPeriod = 0; // 0 means recalculation is disabled.
        rooms = new HashMap<>();
    }

    /**
     * Add a room to the building. The room can be an Apartment or a CommonRoom.
     * @param room The room to be added.
     * @return Returns null if no previous room with the same key exists, otherwise returns the room object.
     */
    public Room addRoom(Room room) {
        if (room == null) {
            return null;
        }

        return rooms.put(room.getId(), room);
    }

    /**
     * Remove a room by id from the building.
     * @param roomId The id of the room to be removed.
     * @return Returns null if the room to be removed does not exist, otherwise returns its object.
     */
    public Room removeRoom(int roomId) {
        return rooms.remove(roomId);
    }

    /**
     * Get the requested temperature of the building.
     * @return Returns the requested temperature.
     */
    public double getRequestedTemperature() {
        return requestedTemperature;
    }

    /**
     * Set the requested temperature of the building.
     * @param requestedTemperature The requested temperature to be set.
     */
    public void setRequestedTemperature(double requestedTemperature) {
        this.requestedTemperature = requestedTemperature;
    }

    public Map<Integer, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Map<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    public int getRecalculateRoomStatusPeriod() {
        return recalculateRoomStatusPeriod;
    }

    public void setRecalculateRoomStatusPeriod(int recalculateRoomStatusPeriod) {
        this.recalculateRoomStatusPeriod = recalculateRoomStatusPeriod;
    }
}
