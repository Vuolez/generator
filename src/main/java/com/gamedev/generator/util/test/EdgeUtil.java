package com.gamedev.generator.util.test;

import com.gamedev.generator.util.MathUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gamedev.generator.model.Edge.getIntersectionLine;

public class EdgeUtil {
    public static double[] findEdgeIntersection1D(int a1, int a2, int b1, int b2) {
        double[] intersectionPoints = new double[2];
        double min1 = Math.min(a1, a2);
        double max1 = Math.max(a1, a2);
        double min2 = Math.min(b1, b2);
        double max2 = Math.max(b1, b2);
        if (max1 < min2 || max2 < min1 /*|| max1 == min2 || max2 == min1 */) {
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
        if (edge1.vertexesEquals(edge2)) {
            return null;
        }

        List<Edge> edges = new ArrayList<>();
        int[] edgesPoint;
        if (edge1.isCollinearY(edge2)) {
            if (edge2.getV1().getX() < edge1.getV1().getX() && edge2.getV2().getX() > edge1.getV2().getX()) {
                return null;
            }

            edgesPoint = getIntersectionLine(edge1.getV1().getX(), edge1.getV2().getX()
                    , edge2.getV1().getX(), edge2.getV2().getX());

            if (edgesPoint != null) {
                Vertex iV1 = new Vertex(edgesPoint[0], edge1.getV1().getY());
                Vertex iV2 = new Vertex(edgesPoint[1], edge1.getV1().getY());

                Edge iEdge = new Edge();
                iEdge.setV1(edge1.getV1());
                iEdge.setV2(edge1.getV2());
                iEdge.setNormal(edge1.getNormal());


                if (edge1.getV1().getX() < edgesPoint[0]) {
                    iV1.setX(iV1.getX() - 1);
                    Vertex v1 = edge1.getV1();
                    Vertex v2 = new Vertex(iV1.getX(), edge1.getV2().getY());
                    edges.add(new Edge(v1, v2, edge1.getNormal()));
                    iEdge.setV1(iV1);
                }
                if (edge1.getV2().getX() > edgesPoint[1]) {
                    iV2.setX(iV2.getX() + 1);
                    Vertex v1 = new Vertex(iV2.getX(), edge1.getV2().getY());
                    Vertex v2 = edge1.getV2();
                    edges.add(new Edge(v1, v2, edge1.getNormal()));
                    iEdge.setV2(iV2);
                }

                edges.add(iEdge);
            }

        } else if (edge1.isCollinearX(edge2)) {
            if (edge2.getV1().getY() < edge1.getV1().getY() && edge2.getV2().getY() > edge1.getV2().getY()) {
                return null;
            }

            edgesPoint = getIntersectionLine(edge1.getV1().getY(), edge1.getV2().getY()
                    , edge2.getV1().getY(), edge2.getV2().getY());

            if (edgesPoint != null) {
                Vertex iV1 = new Vertex(edge1.getV1().getX(), edgesPoint[0]);
                Vertex iV2 = new Vertex(edge1.getV1().getX(), edgesPoint[1]);

                Edge iEdge = new Edge();
                iEdge.setV1(edge1.getV1());
                iEdge.setV2(edge1.getV2());
                iEdge.setNormal(edge1.getNormal());

                if(!iEdge.isVertical() && !iEdge.isHorizontal()){
                    System.out.println("");
                    iEdge.setV1(edge1.getV1());
                    iEdge.setV2(edge1.getV2());
                    iEdge.setNormal(edge1.getNormal());
                }

                if (edge1.getV1().getY() < edgesPoint[0]) {
                    iV1.setY(iV1.getY() - 1);
                    Vertex v1 = edge1.getV1();
                    Vertex v2 = new Vertex(edge1.getV2().getX(), iV1.getY());
                    edges.add(new Edge(v1, v2, edge1.getNormal()));
                    iEdge.setV1(iV1);
                }
                if (edge1.getV2().getY() > edgesPoint[1]) {
                    iV2.setY(iV2.getY() + 1);
                    Vertex v1 = new Vertex(edge1.getV2().getX(), iV2.getY());
                    Vertex v2 = edge1.getV2();
                    edges.add(new Edge(v1, v2, edge1.getNormal()));
                    iEdge.setV2(iV2);
                }

                edges.add(iEdge);
            }
        }

        edges = edges.stream().filter(i -> !i.isZero()).toList();
        return edges;
    }


    /*
     * Находит в переданном списке ребро которое содержит переданный вертекс
     * @param vertex вертекс который должно содержать ребро
     * @param edges список ребер в которых следует найти вертекс
     * @return ребро содержащее переданный вертекс
     */
    public static Edge findEdgeByVertex(Vertex vertex, List<Edge> edges) {
        for (Edge edge : edges) {
            if (edge.getV1() == vertex || edge.getV2() == vertex) {
                return edge;
            }
        }

        return null;
    }

    /*
     * Находит в переданном списке ребра которые содержат переданный вертекс
     * @param vertex вертекс который должно содержать ребро
     * @param edges список ребер в которых следует найти вертекс
     * @return ребра содержащие переданный вертекс
     */
    public static List<Edge> findEdgesByVertex(Vertex vertex, List<Edge> edges) {
        List<Edge> output = new ArrayList<>();

        for (Edge edge : edges) {
            if (edge.getV1() == vertex || edge.getV2() == vertex) {
                output.add(edge);
            }
        }

        return output;
    }

    public static void calculateNormal(Edge edge, List<Edge> edges) {
        Normal normal = new Normal(0, 0);

        Edge e1 = findEdgeByVertex(edge.getV1(), edges);
        Edge e2 = findEdgeByVertex(edge.getV2(), edges);

        //Если линии вертикальны
        if (e1.isVertical() && e2.isVertical()) {
            //Если у обоих линий нормали направленны вправо
            if (e1.getNormal().getX() == 1 && e2.getNormal().getX() == 1) {
                //Здесь неважно какую точку мы сраниваем первую или вторую
                if (e1.getV1().getX() < e2.getV1().getX()) {
                    normal.setY(1);
                } else if (e1.getV1().getX() > e2.getV1().getX()) {
                    normal.setY(-1);
                }
            }
            //Если у обоих линий нормали направленны влево
            else if (e1.getNormal().getX() == -1 && e2.getNormal().getX() == -1) {
                //Здесь неважно какую точку мы сраниваем первую или вторую
                if (e1.getV1().getX() < e2.getV1().getX()) {
                    normal.setY(-1);
                } else if (e1.getV1().getX() > e2.getV1().getX()) {
                    normal.setY(1);
                }
            }
        }
        //Если линии горизонтальны
        else if (e1.isHorizontal() && e2.isHorizontal()) {
            //Если у обоих линий нормали направленны вверх
            if (e1.getNormal().getY() == 1 && e2.getNormal().getY() == 1) {
                //Здесь неважно какую точку мы сраниваем первую или вторую
                if (e1.getV1().getY() < e2.getV1().getY()) {
                    normal.setX(-1);
                } else if (e1.getV1().getY() > e2.getV1().getY()) {
                    normal.setX(1);
                }
            }
            //Если у обоих линий нормали направленны вниз
            else if (e1.getNormal().getY() == 1 && e2.getNormal().getY() == 1) {
                //Здесь неважно какую точку мы сраниваем первую или вторую
                if (e1.getV1().getY() < e2.getV1().getY()) {
                    normal.setX(1);
                } else if (e1.getV1().getY() > e2.getV1().getY()) {
                    normal.setX(-1);
                }
            }
        }

        edge.setNormal(normal);
    }
}
