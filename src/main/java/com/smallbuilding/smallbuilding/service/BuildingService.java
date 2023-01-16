package com.smallbuilding.smallbuilding.service;


import com.smallbuilding.smallbuilding.model.Building;
import com.smallbuilding.smallbuilding.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {
    private final Building applicationBuilding;
    @Autowired
    public BuildingService(Building building) {
        this.applicationBuilding = building;
    }

    public void setRequestedTemperature(double requestedTemperature) {
        applicationBuilding.setRequestedTemperature(requestedTemperature);
    }

    public double getRequestedTemperature() {
        return applicationBuilding.getRequestedTemperature();
    }

    /**
     * Add a room to building. If the room already exists, we simply update the room.
     * @param room The room to be added or updated.
     */
    public void addRoom(Room room) {
        applicationBuilding.addRoom(room);
    }

    /**
     * Remove a room by id from the building. If the room to be removed does not exist, it will do nothing.
     * @param roomId The id of the room to be removed.
     */
    public void removeRoom(int roomId) {
        applicationBuilding.removeRoom(roomId);
    }
}
