package com.gamedev.generator.model;


import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {

    protected void paintRectangles(Graphics g) {
        super.paintComponent(g); // do your superclass's painting routine first, and then paint on top of it.
        g.setColor(Color.RED);
        g.fillRect(20,20,100,100);
    }
}
