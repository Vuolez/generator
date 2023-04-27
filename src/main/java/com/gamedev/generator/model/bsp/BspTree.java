package com.gamedev.generator.model.bsp;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class BspTree {

    // Константа для максимального размера листа
    private final int MAX_LEAF_SIZE = 20;

    // Вектор для хранения всех листьев
    @Getter
    private Vector<BspLeaf> leafs = new Vector<BspLeaf>();

    // Вспомогательный лист
    private BspLeaf l;

    public void create(Integer maxWidth, Integer maxHeight) {
        // Создание корневого листа
        BspLeaf root = new BspLeaf(0, 0, maxWidth, maxHeight, 5, 5);
        leafs.add(root);

        // Переменная для проверки успешности разделения
        boolean did_split = true;

        // Цикл разделения листов до тех пор, пока это возможно
        while (did_split) {
            did_split = false;
            for (int i = 0; i < leafs.size(); i++) {
                l = leafs.get(i);
                // Если этот лист еще не разделен...
                if (l.leftChild == null && l.rightChild == null) {
                    // Если этот лист слишком большой или есть 75% шанс...
                    if (l.width > MAX_LEAF_SIZE || l.height > MAX_LEAF_SIZE || Math.random() > 0.25) {
                        // Разделить лист!
                        if (l.split()) {
                            // Если разделение прошло успешно, добавить дочерние листы в вектор
                            leafs.add(l.leftChild);
                            leafs.add(l.rightChild);
                            did_split = true;
                        }
                    }
                }
            }
        }
    }

    public BspLeaf getRootLeaf() {
        if(leafs.get(0) != null){
            return leafs.get(0);
        }

        return null;
    }
}
