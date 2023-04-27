package com.gamedev.generator;

import com.gamedev.generator.model.BspLeaf;
import com.gamedev.generator.model.BspMap;
import com.gamedev.generator.service.BspService;
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

	void drawRectangles(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;


		BspService bspService = new BspService(new BspUtil());
		BspMap bspMap = bspService.createMap(100, 100);

		for(BspLeaf leaf : bspMap.getLeafs()){
//			g2d.drawRect(leaf.x * scale, leaf.y * scale, leaf.maxWidth * scale, leaf.maxHeight * scale);

			if(leaf.getRoom() != null){
				g2d.setColor(new Color(210, 210, 210));
				g2d.fillRect(leaf.getRoom().x * scale, leaf.getRoom().y * scale, leaf.getRoom().width * scale, leaf.getRoom().height * scale);

				g2d.setColor(new Color(0, 0, 0));
				g2d.drawRect(leaf.getRoom().x * scale, leaf.getRoom().y * scale, leaf.getRoom().width * scale, leaf.getRoom().height * scale);
			}

			if(leaf.getHalls() != null){
				g2d.setColor(new Color(232, 0, 0));
				for(Rectangle hall : leaf.getHalls()){
					g2d.fillRect(hall.x * scale, hall.y * scale, hall.width * scale, hall.height * scale);
				}
			}
		}
		// code to draw rectangles goes here...

	}

	public void paint(Graphics g) {
		super.paint(g);
		drawRectangles(g);
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

}
