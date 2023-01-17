# smallbuilding
A small building controls program in Java

## Introduction
This web application allows users to view and control the temperatures of rooms in a building. 
Users can view the requested temperature of the building (default 25.0 degrees) and update it through the web page. 

When a room's temperature is below the requested building temperature, heating should be enabled. Otherwise, cooling would be enabled.
Room temperature is round to 0.1 decimal.

The status of heating or cooling enabled will be updated when the new building request temperature is set, which can be disabled in the application configuration file.

The status can also be recalculated periodically by the application. Users can set the period value (when the value is 0, the recalculation does not take effect, which is also the default).

When heating is enabled, the room’s temperature will increase slowly (0.1 degrees per second).
When cooling is enabled, the room’s temperature will decrease at the same speed.

This application also allows users to set a close enough limit for each room to compare its temperature to building requested temperature.
When the difference between the room's temperature and the building requested temperature is within the limits, neither heating nor cooling will be enabled when it recalculates the room status.

Users can view a list of all the rooms in the building, which displays the rooms' id, temperature, close enough limit, heating enabled, and cooling enabled.
Rooms can be categorized as apartments or common rooms.
For apartments, the list will also display its owner name, which can be empty.
For common rooms, the list will also display its type, which can be either Gym, Library, or Laundry.

Besides, users can also add or update rooms on the web page. A room id, temperature, and close enough limit are required. 
Types of common rooms can be selected from a dropdown box.
When a new room is added, its status of heating and cooling enabled will be updated, which can be disabled in the application configuration file.

## Framework and Dependency
This application uses Java 1.8 with Spring Boot framework 2.7.7

The project is managed by Maven 3.6.0

Dependencies include spring boot starter web (to run the web application), thymeleaf (to render templates on the server side), and test. (Please check out pom.xml)

## How to Run the Application
To run the application, please run the main method in the class SmallbuildingApplication. It will launch the web application on the embedded Tomcat server. Open a browser and access the URL http://localhost:8080/ to see the UI of the application.

## Architecture
The application has model classes Building and Room. Room is the parent class of Apartment and CommonRoom. CommonRoom has Enum CommonRoomType.

The service classes include BuildingService and RoomService.

The front-end part has one template index.html and its controller BuildingController.

The application is initialized in the class SmallbuildingApplication.



