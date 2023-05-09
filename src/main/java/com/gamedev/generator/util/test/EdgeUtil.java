package com.gamedev.generator.util.test;

import com.gamedev.generator.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gamedev.generator.model.Edge.cutLineSegment1D;
import static com.gamedev.generator.model.Edge.getIntersectionLine;

public class EdgeUtil {
    public static double[] findEdgeIntersection1D(int a1, int a2, int b1, int b2) {
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

    public static Edge getHighestEdge(List<Edge> edges) {
        Edge highestEdge = edges.stream().max(Comparator.comparingInt(Edge::length)).get();
        List<Edge> highestEdges = edges.stream().filter(i -> i.length() == highestEdge.length()).toList();

        return highestEdges.get(MathUtil.getRandIntInRange(0, highestEdges.size() - 1));
    }

    //Находит ребра образованные при вычитании edge2 из edge1
    //Если edge2 > edge1 вернет пустой список
    //Если edge2 == edge1 вернет пустой список
    public static List<Edge> findSubtractEdges(Edge edge1, Edge edge2) {
        if(edge1.vertexesEquals(edge2)){
            return null;
        }

        List<Edge> edges = new ArrayList<>();
        int[] edgesPoint;
        if (edge1.isCollinearY(edge2)) {
            if(edge2.getV1().getX() < edge1.getV1().getX() && edge2.getV2().getX() > edge1.getV2().getX()){
                return null;
            }

            edgesPoint = getIntersectionLine(edge1.getV1().getX(), edge1.getV2().getX()
                    , edge2.getV1().getX(), edge2.getV2().getX());

            if (edgesPoint != null) {
                if (edge1.getV1().getX() < edgesPoint[0]) {
                    edges.add(new Edge(new Vertex(edge1.getV1().getX(), edge1.getV1().getY())
                            , new Vertex(edgesPoint[0], edge1.getV2().getY())));
                }
                if (edge1.getV2().getX() > edgesPoint[1]) {
                    edges.add(new Edge(new Vertex(edgesPoint[1], edge1.getV2().getY())
                            , new Vertex(edge1.getV2().getX(), edge1.getV2().getY())));
                }

                edges.add(new Edge(new Vertex(edgesPoint[0], edge1.getV1().getY())
                        , new Vertex(edgesPoint[1], edge1.getV1().getY())));
            }

        } else if (edge1.isCollinearX(edge2)) {
            if(edge2.getV1().getY() < edge1.getV1().getY() && edge2.getV2().getY() > edge1.getV2().getY()){
                return null;
            }

            edgesPoint = getIntersectionLine(edge1.getV1().getY(), edge1.getV2().getY()
                    , edge2.getV1().getY(), edge2.getV2().getY());

            if (edgesPoint != null) {
                if(edge1.getV1().getY() < edgesPoint[0]){
                    edges.add(new Edge( new Vertex(edge1.getV1().getX(), edge1.getV1().getY())
                            , new Vertex(edge1.getV2().getX(), edgesPoint[0])));
                }
                if(edge1.getV2().getY() > edgesPoint[1]){
                    edges.add(new Edge( new Vertex(edge1.getV2().getX(), edgesPoint[1])
                            , new Vertex(edge1.getV2().getX(), edge1.getV2().getY())));
                }

                edges.add(new Edge( new Vertex(edge1.getV1().getX(), edgesPoint[0])
                        ,new Vertex(edge1.getV1().getX(), edgesPoint[1])));
            }
        }


        edges = edges.stream().filter(i -> !i.isZero()).toList();
        return edges;
    }

}
