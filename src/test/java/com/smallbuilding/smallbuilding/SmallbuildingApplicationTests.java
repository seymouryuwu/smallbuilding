package com.smallbuilding.smallbuilding;

import com.smallbuilding.smallbuilding.model.*;
import com.smallbuilding.smallbuilding.service.BuildingService;
import com.smallbuilding.smallbuilding.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmallbuildingApplicationTests {
	@Autowired
	private Building building;

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private RoomService roomService;

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

	/**
	 * Test if setting building requested temperature works.
	 */
	@Test
	void testSettingRequestedTemperature() {
		System.out.println(buildingService.getRequestedTemperature());
		buildingService.setRequestedTemperature(100.0);
		System.out.println(buildingService.getRequestedTemperature());
		assertEquals(100, buildingService.getRequestedTemperature(),
				"Getting building requested temperature failed.");
	}

	/**
	 * Test if adding and removing rooms for application building works.
	 */
	@Test
	void testAddingAndRemovingRoomsForApplicationBuilding() {
		Apartment newApartment = new Apartment();
		newApartment.setId(103);

		buildingService.addRoom(newApartment);
		assertTrue(building.getRooms().get(103) instanceof Apartment, "Adding new apartment failed.");

		CommonRoom newCommonRoom = new CommonRoom();
		newCommonRoom.setId(3);

		buildingService.addRoom(newCommonRoom);
		assertTrue(building.getRooms().get(3) instanceof CommonRoom, "Adding new common room failed.");

		buildingService.removeRoom(103);
		buildingService.removeRoom(3);
		assertFalse(building.getRooms().containsKey(103), "Removing room failed");
		assertFalse(building.getRooms().containsKey(3), "Removing room failed");
	}

	/**
	 * Test if the status of all the rooms in the building can be updated correctly.
	 */
	@Test
	void testUpdatingRoomsStatus() {
		buildingService.updateRoomsStatus();

		for (Map.Entry<Integer, Room> entry : building.getRooms().entrySet()) {
			Room room = entry.getValue();
			assertEquals(room.getTemperature() > building.getRequestedTemperature(), room.isCoolingEnabled(),
					"Not all the room status is updated correctly. The coolingEnabled of room " + room.getId() +
							" is incorrect.");

			assertEquals(room.getTemperature() < building.getRequestedTemperature(), room.isHeatingEnabled(),
					"Not all the room status is updated correctly. The heatingEnabled of room " + room.getId() +
							" is incorrect.");
		}
	}

	/**
	 * Test if the status of a single room can be updated correctly.
	 */
	@Test
	void testUpdatingRoomStatus() {
		building.setRequestedTemperature(26.0);

		Apartment room1 = new Apartment();
		room1.setTemperature(20.0);

		roomService.updateStatus(room1);
		assertTrue(room1.isHeatingEnabled());
		assertFalse(room1.isCoolingEnabled());

		Apartment room2 = new Apartment();
		room2.setTemperature(30.0);

		roomService.updateStatus(room2);
		assertTrue(room2.isCoolingEnabled());
		assertFalse(room2.isHeatingEnabled());
	}
}
