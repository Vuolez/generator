package com.gamedev.generator;

import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Room;
import com.gamedev.generator.service.BspService;
import com.gamedev.generator.service.MapGraphService;
import com.gamedev.generator.util.BspUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class GeneratorApplication extends JFrame {

    final static int scale = 10;

    public GeneratorApplication() {
        super("Rectangles Drawing Demo");

        getContentPane().setBackground(Color.WHITE);
        setSize(100 * scale, 100 * scale);
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


        MapGraphService mapGraphService = new MapGraphService(new BspService(new BspUtil()));
//		BspMap bspMap = bspService.createBspMap(100, 100);
//
//		for(BspLeaf leaf : bspMap.getLeafs()){
//			g2d.drawRect(leaf.x * scale, leaf.y * scale, leaf.maxWidth * scale, leaf.maxHeight * scale);
//
//			if(leaf.getRoom() != null){
//				g2d.setColor(new Color(210, 210, 210));
//				g2d.fillRect(leaf.getRoom().x * scale, leaf.getRoom().y * scale, leaf.getRoom().width * scale, leaf.getRoom().height * scale);
//
//				g2d.setColor(new Color(0, 0, 0));
//				g2d.drawRect(leaf.getRoom().x * scale, leaf.getRoom().y * scale, leaf.getRoom().width * scale, leaf.getRoom().height * scale);
//			}
//
//			if(leaf.getHalls() != null){
//				g2d.setColor(new Color(232, 0, 0));
//				for(Rectangle hall : leaf.getHalls()){
//					g2d.fillRect(hall.x * scale, hall.y * scale, hall.width * scale, hall.height * scale);
//				}
//			}
//		}

        MapGraph map = mapGraphService.createBspMap(100, 100);

        for (Room room : map.getRooms()) {
            g2d.setColor(new Color(0, 0, 0));
            g2d.drawRect(room.getBound().x * scale, room.getBound().y * scale
                    , room.getBound().width * scale, room.getBound().height * scale);

            g2d.setColor(new Color(255, 0, 0));
            g2d.drawRect(room.getContent().x * scale, room.getContent().y * scale
					, room.getContent().width * scale, room.getContent().height * scale);
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }

}
