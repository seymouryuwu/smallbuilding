package com.smallbuilding.smallbuilding.service;


import com.smallbuilding.smallbuilding.model.Building;
import com.smallbuilding.smallbuilding.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BuildingService {
    private final Building applicationBuilding;
    @Autowired
    public BuildingService(Building building) {
        this.applicationBuilding = building;
    }

    @Value("${automaticUpdateRoomStatus}")
    private boolean automaticUpdateRoomStatus;

    private RoomService roomService;
    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Set building requested Temperature.
     * If automatic update for room heating or cooling status is active, it will also recalculate the status for all
     * the rooms.
     * @param requestedTemperature The building requested temperature.
     */
    public void setRequestedTemperature(double requestedTemperature) {
        applicationBuilding.setRequestedTemperature(requestedTemperature);

        if (automaticUpdateRoomStatus) {
            updateRoomsStatus();
        }
    }

    public double getRequestedTemperature() {
        return applicationBuilding.getRequestedTemperature();
    }

    /**
     * Add a room to building. If the room already exists, we simply update the room.
     * If automatic update for room heating or cooling status is active, it will also recalculate the status for the
     * new room.
     * @param room The room to be added or updated.
     */
    public void addRoom(Room room) {
        applicationBuilding.addRoom(room);

        if (automaticUpdateRoomStatus) {
            roomService.updateStatus(room);
        }

    }

    /**
     * Remove a room by id from the building. If the room to be removed does not exist, it will do nothing.
     * @param roomId The id of the room to be removed.
     */
    public void removeRoom(int roomId) {
        applicationBuilding.removeRoom(roomId);
    }


    /**
     * Update the status of heating or cooling enabled for all the rooms in the building.
     */
    public void updateRoomsStatus() {
        for (Map.Entry<Integer, Room> entry : applicationBuilding.getRooms().entrySet()) {
            Room room = entry.getValue();
            roomService.updateStatus(room);
        }
    }
}
