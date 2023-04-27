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

    public Node (Rectangle bound){
        this.bound = bound;
    }
    public Integer getCenterX() {
        return bound.x + (bound.width / 2);
    }

    public Integer getCenterY() {
        return bound.y + (bound.height / 2);
    }

    public boolean isOverlaping (Node other) {
        return bound.getX() - 1 < other.getBound().getX() + other.getBound().getWidth()
                && bound.getX() + bound.getWidth() + 1> other.getBound().getX()
                && bound.getY() - 1 < other.getBound().getY() + other.getBound().getHeight()
                && bound.getY() + bound.getHeight() + 1 > other.getBound().getY();
    }
}
