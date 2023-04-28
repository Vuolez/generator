package com.gamedev.generator;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Node;
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

    public GeneratorApplication() {
        super("Rectangles Drawing Demo");

        getContentPane().setBackground(Color.WHITE);
        setSize(150 * scale, 150 * scale);
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

        MapGraph map = mapGraphService.createBspMap(100, 100);

        for (Node room : map.getRooms()) {
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawRect(room.getBound().getX() * scale, room.getBound().getY() * scale
                    , room.getBound().getWidth() * scale, room.getBound().getHeight() * scale);


            g2d.setColor(new Color(255, 0, 0));
            g2d.setStroke(new BasicStroke(3));
            for (Edge hall : room.getHalls()) {
                g2d.drawLine(hall.getX1() * scale, hall.getY1() * scale, hall.getX2() * scale, hall.getY2() * scale);
            }
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }

}
