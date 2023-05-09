package com.gamedev.generator.util.test;

public class VertexUtil {
    public static void changeVertexes(Vertex v1, Vertex v2){
        Vertex temp = v1;
        v1 = v2;
        v2 = temp;
    }
}
