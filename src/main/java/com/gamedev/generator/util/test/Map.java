package com.gamedev.generator.util.test;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.*;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Map {
    final static int GROW_STEPS = 1;
    List<Room> rooms = new ArrayList<>();
    java.util.Map<Integer, List<Edge>> potentialGrowthEdges = new LinkedHashMap<>();

    public void create(int roomCount, Graphics2D g2d) {

        List<Vertex> vertexes = new ArrayList<>();
        vertexes.add(new Vertex(-1, -1));
        vertexes.add(new Vertex(-1, 51));
        vertexes.add(new Vertex(51, 51));
        vertexes.add(new Vertex(51, -1));
        Room room = new Room(vertexes);
        rooms.add(room);

        vertexes = new ArrayList<>();
        vertexes.add(new Vertex(0, 20));
        vertexes.add(new Vertex(0, 30));
        vertexes.add(new Vertex(10, 30));
        vertexes.add(new Vertex(10, 20));
        room = new Room(vertexes);
        rooms.add(room);

        vertexes = new ArrayList<>();
        vertexes.add(new Vertex(11 , 25));
        vertexes.add(new Vertex(11 , 35));
        vertexes.add(new Vertex(21 , 35));
        vertexes.add(new Vertex(21 , 25));
        room = new Room(vertexes);
        rooms.add(room);


        g2d.setStroke(new BasicStroke(1));
        int scale = 5;
        int offset = 20;
        for (int i = 1; i < rooms.size(); ++i) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.fillRect(-100, -100, 1000, 1000);
            for (Room r : rooms) {
                for (Edge edge : r.getEdges()) {
                    g2d.setColor(new Color(0, 0, 0));
                    g2d.drawLine((edge.getV1().getX() + offset) * scale, (edge.getV1().getY() + offset) * scale
                            , (edge.getV2().getX() + offset) * scale, (edge.getV2().getY() + offset) * scale);
                }
            }
        }



        prepareUncheckedEdges();
        while (potentialGrowthEdges.size() != 0) {
            for (int i = 1; i < rooms.size(); ++i) {
                growHighestRoomEdge(rooms.get(i));

                g2d.setColor(new Color(255, 255, 255));
                g2d.fillRect(-100, -100, 1000, 1000);
                for (Room r : rooms) {
                    for (Edge edge : r.getEdges()) {
                        g2d.setColor(new Color(0, 0, 0));
                        g2d.drawLine((edge.getV1().getX() + offset) * scale, (edge.getV1().getY() + offset) * scale
                                , (edge.getV2().getX() + offset) * scale, (edge.getV2().getY() + offset) * scale);
                    }
                }
            }
        }
    }

    private void prepareUncheckedEdges() {
        potentialGrowthEdges.clear();
        for (int i = 1; i < rooms.size(); ++i) {
            Room room = rooms.get(i);
            potentialGrowthEdges.put(room.getId(), new ArrayList<>(room.getEdges()));
        }
    }

    public void growHighestRoomEdge(Room room) {
        if (potentialGrowthEdges.get(room.getId()) == null) {
            return;
        }

        Edge edge = EdgeUtil.getHighestEdge(potentialGrowthEdges.get(room.getId()));

        for (int i = 0; i < GROW_STEPS; ++i) {
            if (canEdgeGrow(edge, room)) {
                growEdgeStep(edge);
            } else {
                removePotentialGrowthEdge(room, edge);
                return;
            }
        }
    }

    private void removePotentialGrowthEdge(Room room, Edge edge) {
        if(!potentialGrowthEdges.get(room.getId()).contains(edge)){
            return;
        }

        List<Edge> edges = potentialGrowthEdges.get(room.getId());
        edges.remove(edge);

        if (edges.size() == 0) {
            potentialGrowthEdges.remove(room.getId());
        }
    }

    private boolean canEdgeGrow(Edge edge, Room room) {
        Edge edgeCopy = new Edge(edge);
        edgeCopy.getV1().add(new Vertex(edgeCopy.getNormal().getX(), edgeCopy.getNormal().getY()));
        edgeCopy.getV2().add(new Vertex(edgeCopy.getNormal().getX(), edgeCopy.getNormal().getY()));

        for (Room otherRoom : rooms) {
            if (otherRoom != room) {
                for (Edge otherEdge : otherRoom.getEdges()) {
                    if (otherEdge.equals(edgeCopy)) continue;
                    if (edgeCopy.getCollinearIntersection(otherEdge) != null) {
                        List<Edge> subtractEdges = EdgeUtil.findSubtractEdges(edgeCopy, otherEdge);
                        if (subtractEdges != null) {
                            for(Edge subtractEdge : subtractEdges){
                                subtractEdge.getV1().add(new Vertex(-edgeCopy.getNormal().getX(), -edgeCopy.getNormal().getY()));
                                subtractEdge.getV2().add(new Vertex(-edgeCopy.getNormal().getX(), -edgeCopy.getNormal().getY()));
                                subtractEdge.setNormal(edge.getNormal());
                            }
                            replaceOldEdge(room, edge, subtractEdges);
                        }
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void replaceOldEdge(Room room, Edge edge, List<Edge> newEdges) {
        List<Edge> edges = potentialGrowthEdges.get(room.getId());
        edges.remove(edge);
        edges.addAll(newEdges);

        room.getEdges().remove(edge);
        room.getEdges().addAll(newEdges);
    }

    private void growEdgeStep(Edge edge) {
        edge.getV1().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));
        edge.getV2().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));
    }
}
