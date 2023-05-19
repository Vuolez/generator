package com.gamedev.generator.util.test;

import com.gamedev.generator.util.MathUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.*;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Map {
    final static int GROW_STEPS = 10;
    final static int MIN_ROOM_SIZE = 2;
    final static int ROOM_COUNT = 2;
    boolean DEBUG = false;
    List<Room> rooms = new ArrayList<>();
    java.util.Map<Integer, List<Edge>> potentialGrowthEdges = new LinkedHashMap<>();

    //дебаг отрисовка
    Graphics2D g2d;
    int offset = 20;
    int scale = 5;

    public Map(Graphics2D g2d){
        this.g2d = g2d;
    }

    public void create(int roomCountjr) {

        List<Room> debugRooms = new ArrayList<>();

        List<Vertex> vertexes = new ArrayList<>();
        vertexes.add(new Vertex(-1, -1));
        vertexes.add(new Vertex(-1, 51));
        vertexes.add(new Vertex(51, 51));
        vertexes.add(new Vertex(51, -1));
        Room room = new Room(vertexes);
        rooms.add(room);
        debugRooms.add(new Room(room));

        for (int i = 0 ; i < ROOM_COUNT ; ++i){
            vertexes = new ArrayList<>();
            int x = MathUtil.getRandIntInRange(5, 45);
            int y = MathUtil.getRandIntInRange(5, 45);
            vertexes.add(new Vertex(x-MIN_ROOM_SIZE, y-MIN_ROOM_SIZE));
            vertexes.add(new Vertex(x-MIN_ROOM_SIZE, y+MIN_ROOM_SIZE));
            vertexes.add(new Vertex(x+MIN_ROOM_SIZE, y+MIN_ROOM_SIZE));
            vertexes.add(new Vertex(x+MIN_ROOM_SIZE, y-MIN_ROOM_SIZE));
            room = new Room(vertexes);
            rooms.add(room);

            debugRooms.add(new Room(room));
        }

//        vertexes = new ArrayList<>();
//        vertexes.add(new Vertex(0, 20));
//        vertexes.add(new Vertex(0, 30));
//        vertexes.add(new Vertex(10, 30));
//        vertexes.add(new Vertex(10, 20));
//        room = new Room(vertexes);
//        rooms.add(room);
//
//        vertexes = new ArrayList<>();
//        vertexes.add(new Vertex(11, 25));
//        vertexes.add(new Vertex(11, 35));
//        vertexes.add(new Vertex(21, 35));
//        vertexes.add(new Vertex(21, 25));
//        room = new Room(vertexes);
//        rooms.add(room);
//
//        vertexes = new ArrayList<>();
//        vertexes.add(new Vertex(31, 5));
//        vertexes.add(new Vertex(31, 15));
//        vertexes.add(new Vertex(41, 15));
//        vertexes.add(new Vertex(41, 5));
//        room = new Room(vertexes);
//        rooms.add(room);


        g2d.setStroke(new BasicStroke(3));
        int scale = 5;
        int offset = 20;
        for (int i = 1; i < rooms.size(); ++i) {
            g2d.setColor(new Color(255, 255, 255));
            g2d.fillRect(-100, -100, 1000, 1000);
            for (Room r : rooms) {
                for (Edge edge : r.getEdges()) {
                    if (edge.length() > 0) {
                        g2d.setColor(new Color(0, 0, 0));
                        g2d.drawLine((edge.getV1().getX() + offset) * scale, (edge.getV1().getY() + offset) * scale
                                , (edge.getV2().getX() + offset) * scale, (edge.getV2().getY() + offset) * scale);
                    }
                }
            }
        }


        prepareUncheckedEdges();

        while (potentialGrowthEdges.size() != 0) {
            for (int i = 1; i < rooms.size(); ++i) {
                growHighestRoomEdge(rooms.get(i));
                if(DEBUG){
                    break;
                }
            }
            if(DEBUG){
                break;
            }
        }

        if(DEBUG){
            rooms = debugRooms;
            prepareUncheckedEdges();

            while (potentialGrowthEdges.size() != 0) {
                for (int i = 1; i < rooms.size(); ++i) {
                    growHighestRoomEdge(rooms.get(i));
                }

            }
        }

    }


    private void drawMap(){
        g2d.setColor(new Color(255, 255, 255));
        g2d.fillRect(-100, -100, 1000, 1000);
        for (Room r : rooms) {
            for (Edge edge : r.getEdges()) {
                g2d.setColor(new Color(MathUtil.getRandIntInRange(50,150), MathUtil.getRandIntInRange(50,150), MathUtil.getRandIntInRange(50,150)));
                g2d.drawLine((edge.getV1().getX() + offset) * scale, (edge.getV1().getY() + offset) * scale
                        , (edge.getV2().getX() + offset) * scale, (edge.getV2().getY() + offset) * scale);
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

        //Если верктора нормали нету
        if(edge.getNormal().equals(new Normal(0,0))){
            //Высчитываем его нормаль
            EdgeUtil.calculateNormal(edge, room.getEdges());

            //если после пересчета нормали она осталась нуелвая то ее следует удалить из потенциальных
            if(edge.getNormal().equals(new Normal(0,0))){
                removePotentialGrowthEdge(room, edge);
                EdgeUtil.calculateNormal(edge, room.getEdges());

                return;
            }
        }

        for (int i = 0; i < GROW_STEPS; ++i) {

            if (canEdgeGrow(edge, room)) {
                growEdgeStep(edge, room);
                room.getEdges().forEach(Edge::calculateRightDirection);

            } else {
                removePotentialGrowthEdge(room, edge);
                return;
            }

            drawMap();
            System.out.println();
        }
    }

    private void removePotentialGrowthEdge(Room room, Edge edge) {
        if (!potentialGrowthEdges.get(room.getId()).contains(edge)) {
            return;
        }

        List<Edge> edges = potentialGrowthEdges.get(room.getId());
        edges.remove(edge);

        if (edges.size() == 0) {
            potentialGrowthEdges.remove(room.getId());
        }
    }

    private boolean canEdgeGrow(Edge edge, Room room) {
        edge.getV1().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));
        edge.getV2().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));

        for (Room otherRoom : rooms) {
            if (otherRoom != room) {
                for (Edge otherEdge : otherRoom.getEdges()) {
                    if (otherEdge.equals(edge)) continue;
                    if (edge.getCollinearIntersection(otherEdge) != null) {
                        List<Edge> subtractEdges = EdgeUtil.findSubtractEdges(edge, otherEdge);
                        if (subtractEdges != null) {
                            for (Edge subtractEdge : subtractEdges) {
                                subtractEdge.getV1().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
                                subtractEdge.getV2().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
                                subtractEdge.setNormal(edge.getNormal());
                            }
                            subtractEdges = connectVertexes(subtractEdges);
                            replaceOldEdge(room, edge, subtractEdges);
                        }
                        else{
                            edge.getV1().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
                            edge.getV2().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
                        }
                        return false;
                    }
                }
            }
        }

        edge.getV1().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
        edge.getV2().add(new Vertex(-edge.getNormal().getX(), -edge.getNormal().getY()));
        return true;
    }

    /*
    * Соединяет вертексы расположенные в одном и том же месте
     */
    private List<Edge> connectVertexes(List<Edge> edges) {

        //Собирает все вертексы
        List<Vertex> vertexes = new ArrayList<>();
        for (Edge edge : edges) {
            vertexes.add(edge.getV1());
            vertexes.add(edge.getV2());
        }

        List<Edge> newEdges = new ArrayList<>(edges);
        for (int i = 0; i < vertexes.size(); ++i) {
            for (int j = i + 1; j < vertexes.size(); j++) {

                Vertex v1 = vertexes.get(i);
                Vertex v2 = vertexes.get(j);

                //Если вертексы расположенны в одном месте
                if (v1.getX() == v2.getX()
                        && v1.getY() == v2.getY()) {
                    newEdges.add(new Edge(vertexes.get(i), vertexes.get(j)));
                }
            }
        }
        return newEdges;
    }

    private void replaceOldEdge(Room room, Edge edge, List<Edge> newEdges) {
        List<Edge> edges = potentialGrowthEdges.get(room.getId());
        edges.remove(edge);
        edges.addAll(newEdges);

        room.getEdges().remove(edge);
        room.getEdges().addAll(newEdges);
    }

    private void growEdgeStep(Edge edge, Room room) {
        edge.getV1().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));
        edge.getV2().add(new Vertex(edge.getNormal().getX(), edge.getNormal().getY()));
    }
}
