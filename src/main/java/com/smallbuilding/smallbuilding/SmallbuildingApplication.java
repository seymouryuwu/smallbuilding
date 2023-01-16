package com.smallbuilding.smallbuilding;

import com.smallbuilding.smallbuilding.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmallbuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallbuildingApplication.class, args);
	}

	/**
	 * Initialize the application building and inject it to IoC.
	 * All the information is from the task description.
	 * @return Returns the application Building object.
	 */
	@Bean
	public Building applicationBuilding() {
		Building building = new Building();

		building.setRequestedTemperature(25.0);

		initializeApartment(building, 101);
		initializeApartment(building, 102);

		// Because there is no common room id mentioned in the task, so I just assign it by myself.
		initializeCommonRoom(building, 1, CommonRoomType.Gym);
		initializeCommonRoom(building, 2, CommonRoomType.Library);

		return building;
	}

	private void initializeApartment(Building building, int apartmentId) {
		Apartment apartment = new Apartment();
		apartment.setId(apartmentId);
		initializeRoomTemperature(apartment);

		building.getRooms().put(apartmentId, apartment);
	}

	private void initializeCommonRoom(Building building, int commonRoomId, CommonRoomType type) {
		CommonRoom commonRoom = new CommonRoom();
		commonRoom.setId(commonRoomId);
		commonRoom.setCommonRoomType(type);
		initializeRoomTemperature(commonRoom);

		building.getRooms().put(commonRoomId, commonRoom);
	}

	private void initializeRoomTemperature(Room room) {
		room.setHeatingEnabled(room.getTemperature() < 25.0);
		room.setCoolingEnabled(room.getTemperature() > 25.0);
	}
}
