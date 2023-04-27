package com.gamedev.generator.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapGraph {
    List<Node> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }
}
