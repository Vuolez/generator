package com.gamedev.generator.util.test;

import com.gamedev.generator.model.Node;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Edge implements Cloneable{
    Vertex v1, v2;
    Normal normal;

    public Edge(Vertex v1, Vertex v2) {
        this.v1 = v1;
        this.v2 = v2;
        calculateNormal();
        calculateRightDirection();
    }

    public Edge(Edge edge) {
        this.v1 = new Vertex(edge.getV1());
        this.v2 = new Vertex(edge.getV2());
        this.normal = new Normal(edge.getNormal());
        calculateRightDirection();
    }

    public Edge() {
        this.v1 = new Vertex(0,0);
        this.v2 = new Vertex(0,0);
        calculateNormal();
    }

    private void calculateNormal() {
        if (length() <= 0) {
            normal = new Normal(0,0);
            return;
        }

        int dx = v2.getX() - v1.getX();
        int dy = v2.getY() - v1.getY();
        normal = new Normal(-dy / length(), dx / length());
    }

    public int length() {
        return (int) Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2));
    }

    public Edge getCollinearIntersection(Edge other) {
        if (this == other) {
            return null;
        }

        double[] points;
        Edge intersectionEdge = new Edge();
        if (isCollinearY(other)) {
            points = EdgeUtil.findEdgeIntersection1D(v1.getX(), v2.getX(), other.getV1().getX(), other.getV2().getX());
            if (points != null) {
                intersectionEdge = new Edge(new Vertex((int) points[0], v1.getY()), new Vertex((int) points[1], v2.getY()));
            }
        } else if (isCollinearX(other)) {
            points = EdgeUtil.findEdgeIntersection1D(getV1().getY(), getV2().getY(), other.getV1().getY(), other.getV2().getY());
            if (points != null) {
                intersectionEdge = new Edge(new Vertex(v1.getX(), (int) points[0]), new Vertex(v2.getX(), (int) points[1]));
            }
        }

        return intersectionEdge.isZero() ? null : intersectionEdge;
    }

    public boolean isCollinearX(Edge other) {
        return Objects.equals(v1.getX(), v2.getX())
                && Objects.equals(other.getV1().getX(), other.getV2().getX())
                && Objects.equals(v1.getX(), other.v1.getX());
    }

    public boolean isCollinearY(Edge other) {
        return Objects.equals(v1.getY(), v2.getY())
                && Objects.equals(other.getV1().getY(), other.getV2().getY())
                && Objects.equals(v1.getY(), other.getV1().getY());
    }

    public boolean isHorizontal() {
        return v1.getY() == getV2().getY();
    }

    public boolean isVertical() {
        return getV1().getX() == getV2().getX();
    }

    public boolean isZero() {
        return getV1().getX() == 0 && getV2().getX() == 0 && v1.getY() == 0 && getV2().getY() == 0;
    }

    public void calculateRightDirection() {
        if(isHorizontal()){
            if(v1.getX() > v2.getX()){
                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }
        }
        else if (isVertical()){
            if(v1.getY() > v2.getY()){
                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }
        }
    }

    public boolean vertexesEquals(Edge otherEdge){
        return v1.equals(otherEdge.getV1()) && v2.equals(otherEdge.v2);
    }

    @Override
    public Edge clone() {
        try {
            Edge clone = (Edge) super.clone();
            clone.setV1(new Vertex(v1));
            clone.setV2(new Vertex(v2));
            clone.setNormal(new Normal(normal));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setV1(Vertex v1){
        this.v1 = v1;
        calculateRightDirection();
    }

    public void setV2(Vertex v2){
        this.v2 = v2;
        calculateRightDirection();
    }
}
