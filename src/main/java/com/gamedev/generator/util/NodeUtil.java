package com.gamedev.generator.util;

import com.gamedev.generator.model.Edge;
import com.gamedev.generator.model.Node;

import java.awt.geom.Point2D;
import java.util.*;

public class NodeUtil {

    //Находит соседей для переданных нод
    public void connectNeighborhoodNodes(List<Node> nodes) {
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).getNeighbours().clear();

            for (int j = 0; j < nodes.size(); ++j) {
                if (nodes.get(i) != nodes.get(j)) {
                    Edge overlappingEdge = nodes.get(i).isOverlapingWithThreshold(nodes.get(j), 5);
                    if (overlappingEdge != null) {
                        nodes.get(i).getNeighbours().add(nodes.get(j));
                    }
                }
            }
        }
    }


    //Находит самый короткий путь от startNode до endNode
    public static List<Node> findShortestPath(Node startNode, Node endNode, List<Node> allNodes) {
        // Создаем пустой список для хранения пути
        List<Node> path = new ArrayList<>();

        // Создаем массив для хранения расстояний до каждого узла
        int[] distances = new int[allNodes.size()];
        Arrays.fill(distances, Integer.MAX_VALUE);

        // Создаем массив для хранения предыдущего узла на пути к каждому узлу
        Node[] previousNodes = new Node[allNodes.size()];
        Arrays.fill(previousNodes, null);

        // Создаем очередь с приоритетом для хранения узлов, которые нужно обработать
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> distances[allNodes.indexOf(n)]));

        // Устанавливаем начальное расстояние до startNode равным 0 и добавляем его в очередь
        distances[allNodes.indexOf(startNode)] = 0;
        queue.offer(startNode);

        // Пока очередь не пуста
        while (!queue.isEmpty()) {
            // Извлекаем узел с наименьшим расстоянием из очереди
            Node currentNode = queue.poll();

            // Если текущий узел равен endNode, то мы нашли кратчайший путь
            if (currentNode == endNode) {
                // Восстанавливаем путь и добавляем его в список path
                while (previousNodes[allNodes.indexOf(currentNode)] != null) {
                    path.add(0, currentNode);
                    currentNode = previousNodes[allNodes.indexOf(currentNode)];
                }
                path.add(0, startNode);
                break;
            }

            // Проходим по всем соседям текущего узла
            for (Node neighbour : currentNode.getNeighbours()) {
                int neighbourIndex = allNodes.indexOf(neighbour);
                if(neighbourIndex < 0){
                    continue;
                }

                // Вычисляем новое расстояние до соседа через текущий узел
                int newDistance = distances[allNodes.indexOf(currentNode)] + 1;

                // Если новое расстояние меньше, чем старое, то обновляем расстояние и предыдущий узел
                if (newDistance < distances[neighbourIndex]) {
                    distances[neighbourIndex] = newDistance;
                    previousNodes[neighbourIndex] = currentNode;

                    // Добавляем соседа в очередь
                    queue.offer(neighbour);
                }
            }
        }

        return path;
    }

    //Находит путь до самой дальней ноды от startNode
    public static List<Node> findFurthestNodePath(Node startNode, List<Node> allNodes) {
        List<Node> furthestNodePath = new ArrayList<>();

        for (Node node : allNodes) {
            if (node != startNode) {
                List<Node> path = findShortestPath(startNode, node, allNodes);
                if (path.size() > furthestNodePath.size()) {
                    furthestNodePath = path;
                }
            }
        }

        return furthestNodePath;
    }

    public static Node findMiddleNode(Node node1, Node node2, List<Node> allNodes) {
        // Находим кратчайший путь между node1 и node2
        List<Node> shortestPath = findShortestPath(node1, node2, allNodes);

        // Если путь не найден, возвращаем null
        if (shortestPath.isEmpty()) {
            return null;
        }

        // Находим середину пути
        int middleIndex = shortestPath.size() / 2;

        return shortestPath.get(middleIndex);
    }

    //Находит все ноды лежащие на краю карты
    public static List<Node> findEdgeMapNodes(List<Node> allNodes, Point2D mapSize){
        List<Node> edgeMapNodes = new ArrayList<>();

        for(Node node : allNodes){
            if(node.getBound().getX() <= 0
                    || node.getBound().getY() <= 0
                    || node.getBound().getX() + node.getBound().getWidth() == mapSize.getX()
                    || node.getBound().getY() + node.getBound().getHeight() == mapSize.getY()){
                edgeMapNodes.add(node);
            }
        }

        return edgeMapNodes;
    }

    //Удаляет связи с нодами которые не входят в переданный список нодов
    public static List<Node> deleteUnusedNodes(List<Node> nodes){
        List<Node> cleanedNodes = new ArrayList<>();

        for(Node node : nodes){
            if(nodes.indexOf(node) > 0){
                cleanedNodes.add(node);
            }
        }

        return cleanedNodes;
    }
}
