package com.gamedev.generator.service;


import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Node;
import com.gamedev.generator.model.Rectangle;
import com.gamedev.generator.model.Room;
import com.gamedev.generator.model.bsp.BspLeaf;
import com.gamedev.generator.model.bsp.BspTree;
import com.gamedev.generator.util.MathUtil;
import com.gamedev.generator.util.NodeUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MapGraphService {
    private static Integer ROOM_WIDTH_RANGE = 5;
    private static Integer ROOM_HEIGHT_RANGE = 5;
    BspService bspService;
    NodeUtil nodeUtil;
    public MapGraph createBspMap(Integer width, Integer height){
        MapGraph map = new MapGraph();

        List<Node> leafs = bspService.createMap(width, height);
        nodeUtil.connectOverlappingNodes(leafs);

        map.setRooms(leafs);

        return map;
    }

    private List<Room> createRooms(BspLeaf leaf) {
        List<Room> rooms = new ArrayList<>();
        createRoomsRecursive(leaf, rooms);

        return rooms;
    }

    private void createRoomsRecursive(BspLeaf leaf, List<Room> rooms){
        BspLeaf leftChild = leaf.getLeftChild();
        BspLeaf rightChild = leaf.getRightChild();

        // Если лист разделен, перейти в дочерние листы
        if (leftChild != null || rightChild != null) {
            if (leftChild != null) {
                createRoomsRecursive(leftChild, rooms);
            }
            if (rightChild != null) {
                createRoomsRecursive(rightChild, rooms);
            }
        } else {
            // Создание комнаты в текущем листе
            Point roomSize;
            Point roomPos;

            // Размер комнаты может быть от 3x3 до размеров листа минус 2
            roomSize = new Point(MathUtil.getRandIntInRange((int) (leaf.getBound().getWidth() - ROOM_WIDTH_RANGE), (int) leaf.getBound().getWidth())
                    , MathUtil.getRandIntInRange((int) (leaf.getBound().getHeight() - ROOM_HEIGHT_RANGE), (int) leaf.getBound().getHeight()));

            // Расположение комнаты внутри листа
            roomPos = new Point(MathUtil.getRandIntInRange(0, (int) (leaf.getBound().getWidth() - roomSize.x))
                    , MathUtil.getRandIntInRange(0, (int) (leaf.getBound().getHeight() - roomSize.y)));
            Room room = new Room(new Rectangle(leaf.getBound().getX(),leaf.getBound().getY(), leaf.getBound().getWidth(), leaf.getBound().getHeight())
                    ,new Rectangle(leaf.getBound().getX() + roomPos.x, leaf.getBound().getY() + roomPos.y, roomSize.x, roomSize.y));

            rooms.add(room);
        }
    }
}
