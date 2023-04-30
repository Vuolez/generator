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

        List<BspLeaf> leafs = bspService.createMap(width, height);
        List<Node> nodes = leafs.stream()
                .map(Node.class::cast)
                .toList();

        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawRect(node.getBound().getX() * 5, node.getBound().getY() * 5
                    , node.getBound().getWidth() * 5, node.getBound().getHeight() * 5);

            g2d.setStroke(new BasicStroke(2));
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawString(String.valueOf(i), node.getBoundCenterX() * 5, node.getBoundCenterY() * 5);
        }

        nodeUtil.connectOverlappingNodes(nodes, g2d);

        map.setRooms(createRoomsByLeafList(leafs, g2d));

        return map;
    }

    private List<Room> createRoomsByLeafList(List<BspLeaf> leafs, Graphics2D g2d) {
        List<Room> rooms = new ArrayList<>();
        for (int i = 0 ; i < leafs.size(); ++i) {
            rooms.add(createRoomByLeaf(leafs.get(i), g2d));
        }

        return rooms;
    }

    private Room createRoomByLeaf(BspLeaf leaf, Graphics2D g2d) {

        // Создание комнаты в текущем листе
        Point roomSize;
        Point roomPos;

        // Размер комнаты может быть от 3x3 до размеров листа минус 2
        roomSize = new Point(MathUtil.getRandIntInRange((leaf.getBound().getWidth() - ROOM_WIDTH_RANGE), leaf.getBound().getWidth())
                , MathUtil.getRandIntInRange((leaf.getBound().getHeight() - ROOM_HEIGHT_RANGE), leaf.getBound().getHeight()));

        // Расположение комнаты внутри листа
        roomPos = new Point(MathUtil.getRandIntInRange(0, (leaf.getBound().getWidth() - roomSize.x))
                , MathUtil.getRandIntInRange(0, (leaf.getBound().getHeight() - roomSize.y)));
        Room room = new Room(new Rectangle(leaf.getBound().getX(), leaf.getBound().getY(), leaf.getBound().getWidth(), leaf.getBound().getHeight())
                , new Rectangle(leaf.getBound().getX() + roomPos.x, leaf.getBound().getY() + roomPos.y, roomSize.x, roomSize.y));

        room.setNeighbours(leaf.getNeighbours());
        room.setConnections(leaf.getConnections());
        room.calculateHalls(g2d);

        return room;
    }
}
