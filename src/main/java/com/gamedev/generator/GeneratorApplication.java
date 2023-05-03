package com.gamedev.generator;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Room;
import com.gamedev.generator.service.BspService;
import com.gamedev.generator.service.MapGraphService;
import com.gamedev.generator.util.BspUtil;
import com.gamedev.generator.util.NodeUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class GeneratorApplication extends JFrame {

    final static int scale = 5;
    final static int map_size = 150;

    final static int offsetX = 20;
    public GeneratorApplication() {
        super("Rectangles Drawing Demo");

        getContentPane().setBackground(Color.WHITE);
        setSize((map_size + 50) * scale, (map_size + 50) * scale);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GeneratorApplication().setVisible(true);
            }
        });

//		SpringApplication.run(GeneratorApplication.class, args);


    }

    void drawRectangles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        MapGraphService mapGraphService = new MapGraphService(new BspService(new BspUtil()), new NodeUtil());

        MapGraph map = mapGraphService.createBspMap(map_size, map_size, g2d);

        for (int i = 0; i < map.getRooms().size(); ++i) {

            Room room = map.getRooms().get(i);

//            g2d.setStroke(new BasicStroke(1));
//            g2d.setColor(new Color(0, 0, 0));
//            g2d.drawRect(room.getBound().getX() * scale, room.getBound().getY() * scale
//                    , room.getBound().getWidth() * scale, room.getBound().getHeight() * scale);

            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(new Color(0, 119, 255));
            for(Edge wall : room.getWalls()){
                g2d.drawLine((wall.getX1() + offsetX) * scale, (wall.getY1() + offsetX) * scale
                        , (wall.getX2() + offsetX)* scale, (wall.getY2() + offsetX) * scale);
            }

            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(new Color(255, 255, 255));
            for(Edge wall : room.getHalls()){
                g2d.drawLine((wall.getX1() + offsetX) * scale, (wall.getY1() + offsetX) * scale
                        , (wall.getX2() + offsetX)* scale, (wall.getY2() + offsetX) * scale);
            }

//            g2d.setStroke(new BasicStroke(2));
//            g2d.setColor(new Color(0, 0, 0));
//            g2d.drawString(String.valueOf(i), (room.getBoundCenterX() + offset) * 5, (room.getBoundCenterY() + offset) * 5);

//            g2d.setStroke(new BasicStroke(4));
//            g2d.setColor(new Color(107, 40, 40));
//            for(Edge wall : room.getHalls()){
//                g2d.drawLine(wall.getX1() * scale, wall.getY1() * scale
//                        , wall.getX2() * scale, wall.getY2() * scale);
//            }


//            g2d.setColor(new Color(255,0,0));
//            g2d.setStroke(new BasicStroke(5));
//            for (Edge hall : room.getHalls()) {
//                g2d.drawLine(hall.getX1() * scale, hall.getY1() * scale, hall.getX2() * scale, hall.getY2() * scale);
//            }

//            g2d.setColor(new Color(255,0,0));
//            g2d.setStroke(new BasicStroke(2));
//            for (Node con : room.getConnected()) {
//                g2d.drawLine(room.getCenterX() * scale, room.getCenterY() * scale, con.getCenterX() * scale, con.getCenterY() * scale);
//            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }

}
