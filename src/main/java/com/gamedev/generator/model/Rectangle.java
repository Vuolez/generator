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
public class Rectangle {
    Integer x, y, width, height;

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        //левая
        edges.add(new Edge(x, y, x, y + height));
        //нижняя
        edges.add(new Edge(x, y + height, x + width, y + height));
        //правая
        edges.add(new Edge(x + width, y, x + width, y + height));
        //верхняя
        edges.add(new Edge(x, y, x + width, y));

        return edges;
    }
}
