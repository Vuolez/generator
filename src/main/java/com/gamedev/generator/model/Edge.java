package com.gamedev.generator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Edge {
    int x1, y1, x2, y2;

    public Edge(Point p1, Point p2) {
        this.x1 = p1.x;
        this.y1 = p1.y;
        this.x2 = p2.x;
        this.y2 = p2.y;
    }

    public Edge(Edge other) {
        this.x1 = other.x1;
        this.x2 = other.x2;
        this.y1 = other.y1;
        this.y2 = other.y2;
    }

    public static double[] findLineSegmentsIntersection1D(double a1, double a2, double b1, double b2) {
        double[] intersectionPoints = new double[2];
        double min1 = Math.min(a1, a2);
        double max1 = Math.max(a1, a2);
        double min2 = Math.min(b1, b2);
        double max2 = Math.max(b1, b2);
        if (max1 < min2 || max2 < min1 || max1 == min2 || max2 == min1) {
            // No intersection
            return null;
        } else {
            intersectionPoints[0] = Math.max(min1, min2);
            intersectionPoints[1] = Math.min(max1, max2);
            return intersectionPoints;
        }
    }

    public Edge getCollinearityIntersection(Edge other, Integer threshold) {
        if (this == other) {
            return null;
        }

        double[] points;
        Edge intersectionEdge = new Edge();
        if (isCollinearityY(other)) {
            points = findLineSegmentsIntersection1D(x1, x2, other.x1, other.x2);
            if (points != null) {
                intersectionEdge = new Edge((int) points[0], y1, (int) points[1], y2);
            }
        } else if (isCollinearityX(other)) {
            points = findLineSegmentsIntersection1D(y1, y2, other.y1, other.y2);
            if (points != null) {
                intersectionEdge = new Edge(x1, (int) points[0], x2, (int) points[1]);
            }
        }

        return (intersectionEdge.length() > 0 && intersectionEdge.length() >= threshold) ? intersectionEdge : null;
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

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public boolean isVertical() {
        return x1 == x2;
    }
}
