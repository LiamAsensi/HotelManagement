package edu.carlosliam.hotelmanagementfx.model.data;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("numero")
    private final int number;

    public Room(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Room number: " + number;
    }
}
