package com.gamedev.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    int x1, y1, x2, y2;

    public Edge(Edge other) {
        this.x1 = other.x1;
        this.x2 = other.x2;
        this.y1 = other.y1;
        this.y2 = other.y2;
    }

    public Edge isOverlaping(Edge other, Integer threshold) {
        if (this == other) {
            return null;
        }

        Edge edge = new Edge();

        if (equals(other)) {
            edge = new Edge(this);
        }

        //если линии лежат вертикально на одной прямой
        else if (isCollinearityX(other) && !(y1 < other.y1 && y2 <= other.y1) && !(y1 >= other.y2 && y2 > other.y2)) {
            if (y1 < other.y2 && y2 >= other.y2) {
                edge = new Edge(x1, y1, other.x2, other.y2);
            } else if (y1 < other.y1 && y2 > other.y1) {
                edge = new Edge(other.x1, other.y1, x2, y2);
            } else if (y1 < other.y1 && y2 > other.y2) {
                edge = new Edge(other.x1, other.y1, other.x2, other.y2);
            } else if (y1 < other.y2 && y2 < other.y2) {
                edge = new Edge(x1, y1, x2, y2);
            }
        }

        //если линии лежат горизонтально на одной прямой
        else if (isCollinearityY(other) && !(x1 < other.x1 && x2 <= other.x1) && !(x1 >= other.x2 && x2 > other.x2)) {
            if (x1 < other.x2 && x2 >= other.x2) {
                edge = new Edge(x1, y1, other.x2, other.y2);
            } else if (x1 < other.x1 && x2 > other.x1) {
                edge = new Edge(other.x1, other.y1, x2, y2);
            } else if (x1 < other.x1 && x2 > other.x2) {
                edge = new Edge(other.x1, other.y1, other.x2, other.y2);
            } else if (x1 < other.x2 && x2 < other.x2) {
                edge = new Edge(x1, y1, x2, y2);
            }
        }

        var x = edge.length();
        System.out.println(x);

        var y = edge.length();
        System.out.println(y);

        return (edge.length() > 0 && edge.length() >= threshold) ? edge : null;
    }

    public double length() {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public boolean isCollinearityX(Edge other) {
        return Objects.equals(x1, x2) && Objects.equals(other.x1, other.x2) && Objects.equals(x1, other.x1);
    }

    public boolean isCollinearityY(Edge other) {
        return Objects.equals(y1, y2) && Objects.equals(other.y1, other.y2) && Objects.equals(y1, other.y1);
    }
}
