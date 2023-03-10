package com.smallbuilding.smallbuilding.controller;

import com.smallbuilding.smallbuilding.model.Apartment;
import com.smallbuilding.smallbuilding.model.CommonRoom;
import com.smallbuilding.smallbuilding.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.smallbuilding.smallbuilding.model.Building;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        model.addAttribute("buildingRecalculateRoomStatusPeriod", buildingService.getRecalculateRoomStatusPeriod());

        model.addAttribute("apartmentList", buildingService.getApartmentList());
        model.addAttribute("commonRoomList", buildingService.getCommonRoomList());


        model.addAttribute("inputBuilding", new Building());

        model.addAttribute("inputApartment", new Apartment());

        model.addAttribute("inputCommonRoom", new CommonRoom());

        return "index";
    }

    @RequestMapping(value = "/setRequestedTemperature", method = RequestMethod.POST)
    public String setRequestedTemperature(@ModelAttribute("inputBuilding") Building inputBuilding) {

        buildingService.setRequestedTemperature(inputBuilding.getRequestedTemperature());

        return "redirect:/";
    }

    @RequestMapping(value = "/addApartment", method = RequestMethod.POST)
    public String addApartment(@ModelAttribute("inputApartment") Apartment inputApartment) {
        buildingService.addRoom(inputApartment);

        return "redirect:/";
    }

    @RequestMapping(value = "/addCommonRoom", method = RequestMethod.POST)
    public String addCommonRoom(@ModelAttribute("inputCommonRoom") CommonRoom inputCommonRoom) {
        buildingService.addRoom(inputCommonRoom);

        return "redirect:/";
    }

    @RequestMapping(value = "/setRecalculationPeriod", method = RequestMethod.POST)
    public String setRecalculationPeriod(@ModelAttribute("inputBuilding") Building inputBuilding) {
        buildingService.setApplicationBuildingRecalculateRoomStatusPeriod(inputBuilding.getRecalculateRoomStatusPeriod());

        return "redirect:/";
    }
}
