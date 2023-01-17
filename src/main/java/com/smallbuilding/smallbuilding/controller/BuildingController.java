package com.smallbuilding.smallbuilding.controller;

import com.smallbuilding.smallbuilding.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuildingController {

    private BuildingService buildingService;
    @Autowired
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("buildingRequestedTemperature", buildingService.getRequestedTemperature());

        model.addAttribute("apartmentList", buildingService.getApartmentList());
        model.addAttribute("commonRoomList", buildingService.getCommonRoomList());

        return "index";
    }
}
