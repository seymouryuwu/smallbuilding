package com.smallbuilding.smallbuilding.service;

import com.smallbuilding.smallbuilding.model.Building;
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
}
