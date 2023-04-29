package com.gamedev.generator.util;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.Node;

import java.awt.*;
import java.util.List;

public class NodeUtil {
    public void connectOverlappingNodes(List<Node> nodes, Graphics2D g2d) {
        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = 0; j < nodes.size(); ++j) {
                if (nodes.get(i) != nodes.get(j)) {
                    Edge overlappingEdge = nodes.get(i).isOverlapingWithThreshold(nodes.get(j), 5);

                    if (overlappingEdge != null) {

//                    g2d.setColor(new Color(MathUtil.getRandIntInRange(50,150),MathUtil.getRandIntInRange(50,150),MathUtil.getRandIntInRange(50,150)));
//                    g2d.setStroke(new BasicStroke(3));
//                    g2d.drawLine(overlappingEdge.getX1() * 5, overlappingEdge.getY1() * 5, overlappingEdge.getX2() * 5, overlappingEdge.getY2() * 5);

//                        Edge test = nodes.get(i).isOverlapingWithThreshold(nodes.get(j), 0);

                        nodes.get(i).getConnected().add(nodes.get(j));
                    }
                }
            }
        }
    }


}
