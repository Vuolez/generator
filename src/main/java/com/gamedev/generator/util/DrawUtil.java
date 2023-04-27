package com.gamedev.generator.util;

import com.gamedev.generator.model.DrawPanel;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class DrawUtil {

    // create the GUI explicitly on the Swing event thread
    public static void createAndShowGui() {
        DrawPanel mainPanel = new DrawPanel();

        JFrame frame = new JFrame("DrawRect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
