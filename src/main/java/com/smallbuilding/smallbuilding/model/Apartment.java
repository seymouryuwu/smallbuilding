package com.smallbuilding.smallbuilding.model;

/**
 * Apartment inherits the fields and methods from Room.
 * Besides, Apartment has an additional field for the owners name.
 */
public class Apartment extends Room {
    private String ownerName;

    public Apartment() {
        super();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
