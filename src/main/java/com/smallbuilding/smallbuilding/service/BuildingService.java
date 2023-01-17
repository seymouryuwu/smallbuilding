package com.smallbuilding.smallbuilding.service;


import com.smallbuilding.smallbuilding.model.Apartment;
import com.smallbuilding.smallbuilding.model.Building;
import com.smallbuilding.smallbuilding.model.CommonRoom;
import com.smallbuilding.smallbuilding.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private Timer timer = new Timer("recalculateRoomStatus");

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

    public List<Room> getRoomList() {
        List<Room> roomList = new ArrayList<>();
        for (Map.Entry<Integer, Room> entry : applicationBuilding.getRooms().entrySet()) {
            Room room = entry.getValue();
            roomList.add(room);
        }

        return roomList;
    }

    public List<Apartment> getApartmentList() {
        List<Apartment> apartmentList = new ArrayList<>();
        for (Map.Entry<Integer, Room> entry : applicationBuilding.getRooms().entrySet()) {
            Room room = entry.getValue();

            if (room instanceof Apartment) {
                apartmentList.add((Apartment) room);
            }
        }

        return apartmentList;
    }

    public List<CommonRoom> getCommonRoomList() {
        List<CommonRoom> commonRoomList = new ArrayList<>();
        for (Map.Entry<Integer, Room> entry : applicationBuilding.getRooms().entrySet()) {
            Room room = entry.getValue();

            if (room instanceof CommonRoom) {
                commonRoomList.add((CommonRoom) room);
            }
        }

        return commonRoomList;
    }

    /**
     * Enable recalculation of room status on a timer. This will only take effect when the application building has a
     * period greater than 0. Otherwise, it will cancel the timer.
     */
    public void enableRecalculateRoomStatusOnTimer() {
        timer.cancel();

        if (applicationBuilding.getRecalculateRoomStatusPeriod() <=  0) {
            return;
        }

        timer = new Timer("recalculateRoomStatus");
        TimerTask recalculationTask = new TimerTask() {
            @Override
            public void run() {
                updateRoomsStatus();
            }
        };

        timer.scheduleAtFixedRate(recalculationTask, 0, applicationBuilding.getRecalculateRoomStatusPeriod() * 1000L);
    }

    /**
     * Set recalculate room status period for the application building, then enable it on a timer or cancel the timer
     * when the period is not greater than 0.
     * @param recalculateRoomStatusPeriod The period to be set.
     */
    public void setApplicationBuildingRecalculateRoomStatusPeriod(int recalculateRoomStatusPeriod) {
        applicationBuilding.setRecalculateRoomStatusPeriod(recalculateRoomStatusPeriod);
        enableRecalculateRoomStatusOnTimer();
    }
}
