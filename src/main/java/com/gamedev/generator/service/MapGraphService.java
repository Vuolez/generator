package com.gamedev.generator.service;


import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Node;
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

        int offset = 20;
        for (int i = 0; i < nodes.size(); ++i) {
            Node node = nodes.get(i);

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(225, 225, 225, 255));
            g2d.drawRect((node.getBound().getX() + offset) * 5, (node.getBound().getY() + offset) * 5
                    , node.getBound().getWidth() * 5, node.getBound().getHeight() * 5);

            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawString(String.valueOf(i), (node.getBoundCenterX() + offset) * 5, (node.getBoundCenterY() + offset) * 5);
        }

        nodeUtil.connectNeighborhoodNodes(nodes);

        List<Node> playersNodes = findPlayersNodes(4, nodes, width, height);
        for (int i = 0; i < playersNodes.size(); ++i) {
            Node node = playersNodes.get(i);

            g2d.setStroke(new BasicStroke(10));
            g2d.setColor(new Color(255, 0, 234));
            g2d.drawRect((node.getBound().getX() + offset) * 5, (node.getBound().getY() + offset) * 5
                    , node.getBound().getWidth() * 5, node.getBound().getHeight() * 5);
        }


        List<Node> actualNodes = findPaths(playersNodes, nodes)
                .stream()
                .flatMap(List::stream)
                .toList();

        actualNodes = actualNodes.stream().peek(i -> i.getConnections().clear()).toList();
        nodeUtil.connectNeighborhoodNodes(actualNodes);

        map.setRooms(createRoomsByNodeList(actualNodes, g2d));

        map.setDebugNodes(actualNodes);

        return map;
    }

    private List<Node> findPlayersNodes(Integer playersCount, List<Node> allNodes, Integer width, Integer height) {
        List<Node> playersNodes = new ArrayList<>();
        if (playersCount < 2) {
            return playersNodes;
        }

        List<Node> edgeMap = NodeUtil.findEdgeMapNodes(allNodes, new Point(width, height));
        if (playersCount >= 2) {
            playersNodes.add(edgeMap.get(0));
            playersNodes.add(edgeMap.get(edgeMap.size() - 1));
        }

        if (playersCount == 4) {
            Node nodeThird = NodeUtil.findMiddleNode(playersNodes.get(0), playersNodes.get(1), edgeMap);
            playersNodes.add(nodeThird);

            List<Node> path = NodeUtil.findFurthestNodePath(nodeThird, edgeMap);
            playersNodes.add(path.get(path.size() - 1));
        }

        return playersNodes;
    }

    private List<List<Node>> findPaths(List<Node> playerNodes, List<Node> allNodes) {
        List<List<Node>> paths = new ArrayList<>();
        if (playerNodes.size() % 2 != 0) {
            return paths;
        }

        for (int i = 0; i + 1 < playerNodes.size(); i += 2) {
            paths.add(NodeUtil.findShortestPath(playerNodes.get(i), playerNodes.get(i + 1), allNodes));
        }

        return paths;
    }

    private List<Room> createRoomsByNodeList(List<Node> nodes, Graphics2D g2d) {
        List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < nodes.size(); ++i) {
            rooms.add(createRoomByLeaf(nodes.get(i), g2d));
        }

        return rooms;
    }

    private Room createRoomByLeaf(Node node, Graphics2D g2d) {
        Room room = new Room(node);

        room.setNeighbours(node.getNeighbours());
        room.setConnections(node.getConnections());
        room.calculateHalls(g2d);
//        room.cutHalls();

        return room;
    }
}
