package com.gamedev.generator.service;


import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Node;
import com.gamedev.generator.model.Rectangle;
import com.gamedev.generator.model.Room;
import com.gamedev.generator.model.bsp.BspLeaf;
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

    public MapGraph createBspMap(Integer width, Integer height, Graphics2D g2d) {
        MapGraph map = new MapGraph();

        List<Node> leafs = bspService.createMap(width, height);
        for (int i = 0 ; i < leafs.size(); ++i) {
            Node leaf = leafs.get(i);

            g2d.setColor(new Color(0,0,0));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(leaf.getBound().getX() * 5, leaf.getBound().getY() * 5, leaf.getBound().getWidth() * 5, leaf.getBound().getHeight() * 5);

            g2d.setColor(new Color(255,0,0));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawString(String.valueOf(i), leaf.getCenterX() * 5, leaf.getCenterY() * 5);
        }

        nodeUtil.connectOverlappingNodes(leafs, g2d);

        map.setRooms(leafs);

        return map;
}

    private List<Room> createRooms(BspLeaf leaf) {
        List<Room> rooms = new ArrayList<>();
        createRoomsRecursive(leaf, rooms);

        return rooms;
    }

    private void createRoomsRecursive(BspLeaf leaf, List<Room> rooms) {
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
            Room room = new Room(new Rectangle(leaf.getBound().getX(), leaf.getBound().getY(), leaf.getBound().getWidth(), leaf.getBound().getHeight())
                    , new Rectangle(leaf.getBound().getX() + roomPos.x, leaf.getBound().getY() + roomPos.y, roomSize.x, roomSize.y));

            rooms.add(room);
        }
    }
}
