package com.gamedev.generator.util;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomUtil {
    public static Room mergeRoomsWithDeleteOverlapEdges(Room room1, Room room2) {
        // Удаляем ребра между комнатами
        Room roomCopy1 = new Room(room1);
        List<Edge> edgesForRemove = new ArrayList<>();
        List<Edge> edgesForAdd = new ArrayList<>();
        for (Edge wall1 : room1.getWalls()) {
            for (Edge wall2 : room2.getWalls()) {
                List<Edge> subtractedEdges = EdgeUtil.findSubtractEdges(wall1, wall2);
                if (!subtractedEdges.isEmpty()) {
                    subtractedEdges = EdgeUtil.findSubtractEdges(wall1, wall2);

                    edgesForRemove.add(wall1);
                    edgesForAdd.addAll(subtractedEdges);
                }
            }
        }
        room1.getWalls().removeAll(edgesForRemove);
        room1.getWalls().addAll(edgesForAdd);


        edgesForRemove = new ArrayList<>();
        edgesForAdd = new ArrayList<>();
        for (Edge wall2 : room2.getWalls()) {
            for (Edge wall1 : roomCopy1.getWalls()) {
                List<Edge> subtractedEdges = EdgeUtil.findSubtractEdges(wall2, wall1);
                if (!subtractedEdges.isEmpty()) {
                    edgesForRemove.add(wall2);
                    edgesForAdd.addAll(subtractedEdges);
                }
            }
        }
        room2.getWalls().removeAll(edgesForRemove);
        room2.getWalls().addAll(edgesForAdd);

        return mergeRooms(room1, room2);
    }

    public static Room mergeRooms(Room room1, Room room2) {
        Room mergedRoom = new Room();
        mergedRoom.getWalls().addAll(room1.getWalls());
        mergedRoom.getWalls().addAll(room2.getWalls());
        return mergedRoom;
    }
}
