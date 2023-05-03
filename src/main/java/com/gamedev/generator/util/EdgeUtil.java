package com.gamedev.generator.util;

import com.gamedev.generator.model.Edge;

import java.util.ArrayList;
import java.util.List;

import static com.gamedev.generator.model.Edge.cutLineSegment1D;

public class EdgeUtil {

    //Находит ребра образованные при вычитании edge1 из edge2
    //Если edge2 > edge1 вернет пустой список
    public static List<Edge> findSubtractEdges(Edge edge1, Edge edge2) {
        List<Edge> edges = new ArrayList<>();

        double[][] edgesPoints = null;
        if (edge1.isCollinearY(edge2)) {
            edgesPoints = cutLineSegment1D(new double[]{edge1.getX1(), edge1.getX2()}, new double[]{edge2.getX1(), edge2.getX2()});
            if (edgesPoints != null) {
                for (double[] edgesPoint : edgesPoints) {
                    edges.add(new Edge((int) edgesPoint[0], edge1.getY1(), (int) edgesPoint[1], edge1.getY2()));
                }
            }

        } else if (edge1.isCollinearX(edge2)) {
            edgesPoints = cutLineSegment1D(new double[]{edge1.getY1(), edge1.getY2()}, new double[]{edge2.getY1(), edge2.getY2()});
            if (edgesPoints != null) {
                for (double[] edgesPoint : edgesPoints) {
                    edges.add(new Edge(edge1.getX1(), (int) edgesPoint[0], edge1.getX2(), (int) edgesPoint[1]));
                }
            }
        }

        edges = edges.stream().filter(i -> !i.isZero()).toList();
        return edges;
    }
}
