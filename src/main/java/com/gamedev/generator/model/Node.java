package com.gamedev.generator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Node {
    Rectangle bound;
    List<Node> connected = new ArrayList<>();
    List<Edge> halls = new ArrayList<>();

    public Node(Rectangle bound) {
        this.bound = bound;
    }

    public Integer getCenterX() {
        return bound.getX() + (bound.getWidth() / 2);
    }

    public Integer getCenterY() {
        return bound.getY() + (bound.getHeight() / 2);
    }

    public boolean isOverlaping(Node other) {
        return bound.getX() - 1 < other.getBound().getX() + other.getBound().getWidth()
                && bound.getX() + bound.getWidth() + 1 > other.getBound().getX()
                && bound.getY() - 1 < other.getBound().getY() + other.getBound().getHeight()
                && bound.getY() + bound.getHeight() + 1 > other.getBound().getY();
    }

    public Edge isOverlapingWithThreshold(Node other, Integer threshold) {
        List<Edge> edges = getEdges();
        List<Edge> otherEdges = other.getEdges();
        for (int i = 0; i < edges.size(); ++i) {
            for(int j = 0; j < otherEdges.size(); ++j){
                Edge overlapingEdge = edges.get(i).isOverlaping(otherEdges.get(j), threshold);
                if(overlapingEdge != null){
                    Edge test = edges.get(i).isOverlaping(otherEdges.get(j), threshold);
                    halls.add(overlapingEdge);
                    return overlapingEdge;
                }
            }
        }

        return null;
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        //левая
        edges.add(new Edge(bound.getX(), bound.getY(), bound.getX(), bound.getY() + bound.getHeight()));
        //нижняя
        edges.add(new Edge(bound.getX(), bound.getY() + bound.getHeight(), bound.getX() + bound.getWidth(), bound.getY() + bound.getHeight()));
        //правая
        edges.add(new Edge(bound.getX() + bound.getWidth(), bound.getY(), bound.getX() + bound.getWidth(), bound.getY() + bound.getHeight()));
        //верхняя
        edges.add(new Edge(bound.getX(), bound.getY(), bound.getX() + bound.getWidth(), bound.getY()));

        return edges;
    }
}
