package com.smallbuilding.smallbuilding;

import com.smallbuilding.smallbuilding.model.Apartment;
import com.smallbuilding.smallbuilding.model.Building;
import com.smallbuilding.smallbuilding.model.CommonRoom;
import com.smallbuilding.smallbuilding.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SmallbuildingApplicationTests {

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
}
