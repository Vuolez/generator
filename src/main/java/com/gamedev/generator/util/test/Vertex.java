package com.gamedev.generator.util.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Vertex {
    int x, y;

    public Vertex(Vertex v1) {
        this.x = v1.getX();
        this.y = v1.getY();
    }

    public void add(Vertex vertex) {
        this.x += vertex.getX();
        this.y += vertex.getY();
    }
}
