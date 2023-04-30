package com.gamedev.generator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {
    Rectangle bound;
    List<Node> neighbours = new ArrayList<>();
    List<Edge> connections = new ArrayList<>();

    public Node(Rectangle bound) {
        this.bound = bound;
    }

    public Integer getBoundCenterX() {
        return bound.getX() + (bound.getWidth() / 2);
    }

    public Integer getBoundCenterY() {
        return bound.getY() + (bound.getHeight() / 2);
    }

    public boolean isOverlaping(Node other) {
        return bound.getX() - 1 < other.getBound().getX() + other.getBound().getWidth()
                && bound.getX() + bound.getWidth() + 1 > other.getBound().getX()
                && bound.getY() - 1 < other.getBound().getY() + other.getBound().getHeight()
                && bound.getY() + bound.getHeight() + 1 > other.getBound().getY();
    }

    public Edge isOverlapingWithThreshold(Node other, Integer threshold) {
        List<Edge> edges = bound.getEdges();
        List<Edge> otherEdges = other.getBound().getEdges();
        for (int i = 0; i < edges.size(); ++i) {
            for(int j = 0; j < otherEdges.size(); ++j){
                Edge overlapingEdge = edges.get(i).getCollinearityIntersection(otherEdges.get(j), threshold);
                if(overlapingEdge != null){
                    Edge test = edges.get(i).getCollinearityIntersection(otherEdges.get(j), threshold);
                    connections.add(overlapingEdge);
                    return overlapingEdge;
                }
            }
        }

        return null;
    }
}
