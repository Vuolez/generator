package com.gamedev.generator.util;

import com.gamedev.generator.model.Node;

import java.awt.*;
import java.util.List;

public class NodeUtil {
    public void connectOverlappingNodes(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = i + 1; j < nodes.size(); ++j) {
                if(nodes.get(i).isOverlaping(nodes.get(j))){
                    nodes.get(i).getConnected().add(nodes.get(j));
                }
            }
        }
    }


}
