package edu.carlosliam.hotelmanagementfx.model.data;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("number")
    private final int number;

    public Room(int number) {
        this.number = number;
    }
}
