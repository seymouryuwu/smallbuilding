package com.smallbuilding.smallbuilding.service;

import com.smallbuilding.smallbuilding.model.Building;
import com.smallbuilding.smallbuilding.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final Building applicationBuilding;
    @Autowired
    public RoomService(Building building) {
        this.applicationBuilding = building;
    }

    /**
     * Update status of heating enabled or cooling enabled for a room based on the comparison of building requested
     * temperature.
     * If the room temperature is below the requested building temperature, heating should be enabled.
     * If the room temperature is above the requested building temperature, cooling should be enabled.
     * @param room The room to be updated.
     */
    public void updateStatus(Room room) {
        room.setHeatingEnabled(room.getTemperature() < applicationBuilding.getRequestedTemperature());
        room.setCoolingEnabled(room.getTemperature() > applicationBuilding.getRequestedTemperature());
    }
}
