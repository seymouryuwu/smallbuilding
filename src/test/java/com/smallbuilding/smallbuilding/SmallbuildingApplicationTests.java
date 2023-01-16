package com.smallbuilding.smallbuilding;

import com.smallbuilding.smallbuilding.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmallbuildingApplicationTests {
	@Autowired
	Building building;

	@Test
	void contextLoads() {

	}

	/**
	 * Test if the random initial room temperature is within the range.
	 */
	@Test
	void testRoomRandomInitialTemperature() {
		for (int i = 0; i < 100; i++) {
			Room room = new Apartment();
			double initialTemperature = room.getTemperature();
			System.out.println(initialTemperature);
			assertTrue(initialTemperature >= 10.0,
					"Initial room temperature is " + initialTemperature + ", which is less than minimum 10.");

			assertTrue(initialTemperature <= 40.0,
					"Initial room temperature is " + initialTemperature + ", which is greater than maximum 40.");
		}
	}

	/**
	 * Test adding and removing rooms for model Building.
	 */
	@Test
	void testAddingAndRemovingRooms() {
		Building building = new Building();

		Apartment apartment = new Apartment();
		apartment.setId(1);

		CommonRoom commonRoom = new CommonRoom();
		commonRoom.setId(2);

		building.addRoom(apartment);
		building.addRoom(commonRoom);

		building.removeRoom(1);
		building.removeRoom(2);

		building.addRoom(null);
		building.removeRoom(0);
	}

	/**
	 * Test if the application building is initialized correctly.
	 * Initially the building should have 2 apartments, apartment 101 and 102, a Gym and a Library.
	 * The building should also have a requested temperature of 25.0 degrees.
	 * Rooms have heating or cooling enabled based on the following logic:
	 * If the room temperature is below the requested building temperature, heating should be enabled.
	 * If the room temperature is above the requested building temperature, cooling should be enabled.
	 */
	@Test
	void testInitializingApplicationBuilding() {
		assertEquals(building.getRequestedTemperature(), 25.0,
				"Initial building requested temperature is incorrect");

		assertTrue(building.getRooms().get(101) instanceof Apartment, "Room 101 is not an Apartment.");
		assertTrue(building.getRooms().get(102) instanceof Apartment, "Room 102 is not an Apartment.");
		assertTrue(building.getRooms().get(1) instanceof CommonRoom, "Room 1 is not an CommonRoom.");
		assertTrue(building.getRooms().get(2) instanceof CommonRoom, "Room 2 is not an CommonRoom.");

		assertSame(((CommonRoom) building.getRooms().get(1)).getCommonRoomType(), CommonRoomType.Gym,
				"CommonRoom 1 is not a Gym.");
		assertSame(((CommonRoom) building.getRooms().get(2)).getCommonRoomType(), CommonRoomType.Library,
				"CommonRoom 2 is not a Library.");

		for (Map.Entry<Integer, Room> entry: building.getRooms().entrySet()) {
			Room room = entry.getValue();
			if (room.getTemperature() < 25.0) {
				assertTrue(room.isHeatingEnabled(),
						"Room with id " + room.getId() + " has wrong status for heating.");
			} else if (room.getTemperature() > 25.0) {
				assertTrue(room.isCoolingEnabled(),
						"Room with id " + room.getId() + " has wrong status for cooling.");
			}
		}
	}

}
