package com.gamedev.generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    Integer x1, y1, x2, y2;

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

        if (equals(other)) {
            return new Edge(this);
        }

        //если линии лежат вертикально на одной прямой
        else if (isCollinearityX(other) && !(y1 < other.y1 && y2 <= other.y1) && !(y1 >= other.y2 && y2 > other.y2)) {
            if (y1 < other.y2 && y2 > other.y2) {
                return new Edge(x1, y1, other.x2, other.y2);
            } else if (y1 < other.y2 && y2 < other.y2) {
                return new Edge(x1, y1, x2, y2);
            } else if (y1 < other.y1 && y2 > other.y1) {
                return new Edge(other.x1, other.y1, x2, y2);
            } else if (y1 < other.y1 && y2 > other.y2) {
                return new Edge(other.x1, other.y1, other.x2, other.y2);
            }
        }

        //если линии лежат горизонтально на одной прямой
        else if (isCollinearityY(other) && !(x1 < other.x1 && x2 <= other.x1) && !(x1 >= other.x2 && x2 > other.x2)) {
            if (x1 < other.x2 && x2 > other.x2) {
                return new Edge(x1, y1, other.x2, other.y2);
            } else if (x1 < other.x2 && x2 < other.x2) {
                return new Edge(x1, y1, x2, y2);
            } else if (x1 < other.x1 && x2 > other.x1) {
                return new Edge(other.x1, other.y1, x2, y2);
            } else if (x1 < other.x1 && x2 > other.x2) {
                return new Edge(other.x1, other.y1, other.x2, other.y2);
            }
        }

        return null;
    }

    public boolean isCollinearityX(Edge other) {
        return Objects.equals(x1, x2) && Objects.equals(other.x1, other.x2) && Objects.equals(x1, other.x1);
    }

    public boolean isCollinearityY(Edge other) {
        return Objects.equals(y1, y2) && Objects.equals(other.y1, other.y2) && Objects.equals(y1, other.y1);
    }
}
