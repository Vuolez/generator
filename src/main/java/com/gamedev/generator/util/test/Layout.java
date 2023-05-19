package com.gamedev.generator.util.test;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Layout extends JPanel {
    public Layout() {
        setBorder(new LineBorder(Color.RED, 5));
    }

    public void paintComponent(Graphics g) {
        //passes the graphics context off to the component's UI delegate,
        //which paints the panel's background.
        super.paintComponent(g);
        // Draw Text
        g.drawString("This is my custom Panel!", 10, 20);
        g.setColor(Color.RED);
        g.fillOval(10, 30, 30, 30);
        g.drawOval(10, 80, 30, 30);
        g.fillRect(60, 30, 30, 30);
        g.drawRect(60, 80, 30, 30);
    }
}
