package com.gamedev.generator.model;

import com.gamedev.generator.util.EdgeUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room extends Node {

    static final int HALL_SIZE = 5;
    List<Edge> walls = new ArrayList<>();
    List<Edge> halls = new ArrayList<>();

    public Room(Rectangle bound, Rectangle walls) {
        super(bound);

        this.walls = walls.getEdges();
        this.halls = new ArrayList<>();
    }

    public Room(Rectangle rectangle){
        this(rectangle, rectangle);
    }

    public Room(Node node){
        this(node.getBound(), node.getBound());
    }

    public Room(Room room){
        setBound(room.getBound());
        setNeighbours(getNeighbours());
        setConnections(getConnections());
        room.getWalls().stream().forEach(i -> walls.add(new Edge(i)));
        room.getHalls().stream().forEach(i -> halls.add(new Edge(i)));
    }

    public void calculateHalls(Graphics2D g2d) {
        for (Edge connection : getConnections()) {
            Edge edge = new Edge();
            if (connection.isHorizontal()) {
                double centerX = ((double) (connection.getX2() - connection.getX1()) / 2) + connection.getX1();
                edge = new Edge((int) (centerX - ((double) HALL_SIZE / 2)), connection.getY1()
                        , (int) (centerX + ((double) HALL_SIZE / 2)), connection.getY2());
            } else if (connection.isVertical()) {
                double centerY = ((double) (connection.getY2() - connection.getY1()) / 2) + connection.getY1();
                edge = new Edge(connection.getX1(), (int) (centerY - ((double) HALL_SIZE / 2))
                        , connection.getX2(), (int) (centerY + ((double) HALL_SIZE / 2)));
            }
            halls.add(edge);

//            g2d.setStroke(new BasicStroke(2));
//            g2d.setColor(new Color(255, 0, 0));
//            g2d.drawLine(connection.getX1() * 5, connection.getY1() * 5
//                    , connection.getX2() * 5, connection.getY2() * 5);
//
//            g2d.setColor(new Color(0, 139, 255));
//            g2d.drawLine(edge.getX1() * 5, edge.getY1() * 5
//                    , edge.getX2() * 5, edge.getY2() * 5);
//
//            System.out.println(connection);
        }

    }

//    public void cutHalls() {
//        List<Edge> newWalls = new ArrayList<>();
//        for (Edge wall : walls) {
//            boolean haveCollinearHall = false;
//            for (Edge hall : getHalls()) {
//                if (wall.isCollinear(hall)) {
//                    newWalls.addAll(EdgeUtil.findSubtractEdges(wall, hall));
//                    haveCollinearHall = true;
//                }
//            }
//            if (!haveCollinearHall) {
//                newWalls.add(wall);
//            }
//        }
//
//        walls = newWalls;
//    }

    public void addWall(Edge edge1) {
        walls.add(edge1);
    }
}
