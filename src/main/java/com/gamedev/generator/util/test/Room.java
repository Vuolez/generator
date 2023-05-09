package com.gamedev.generator.util.test;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Room {
    static int ID_COUNTER = 0;
    List<Edge> edges = new ArrayList<>();
    List<Vertex> vertexes = new ArrayList<>();
    int id;

    public Room(List<Vertex> vertexes) {
        id = ID_COUNTER++;

        this.vertexes = vertexes;
        calculateEdges();
    }

    private void calculateEdges() {
        for (int i = 0; i + 1 < vertexes.size(); ++i){
            edges.add(new Edge(vertexes.get(i), vertexes.get(i + 1)));
        }
        edges.add(new Edge(vertexes.get(vertexes.size()-1), vertexes.get(0)));
    }


}
