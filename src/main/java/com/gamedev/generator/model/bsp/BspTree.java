package com.gamedev.generator.model.bsp;

import com.gamedev.generator.model.Node;
import com.gamedev.generator.model.Rectangle;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BspTree {

    // Константа для максимального размера листа
    private final int MAX_LEAF_SIZE = 20;

    // Вектор для хранения всех листьев
    @Getter
    private List<Node> leafs = new ArrayList<>();

    // Вспомогательный лист
    private BspLeaf l;

    public void create(Integer maxWidth, Integer maxHeight) {
        // Создание корневого листа
        BspLeaf root = new BspLeaf(new Rectangle(0, 0, maxWidth, maxHeight));
        leafs.add(root);

        // Переменная для проверки успешности разделения
        boolean did_split = true;

        // Цикл разделения листов до тех пор, пока это возможно
        while (did_split) {
            did_split = false;
            for (int i = 0; i < leafs.size(); i++) {
                l = (BspLeaf) leafs.get(i);
                // Если этот лист еще не разделен...
                if (l.getLeftChild() == null && l.getRightChild() == null) {
                    // Если этот лист слишком большой или есть 75% шанс...
                    if (l.getBound().getWidth() > MAX_LEAF_SIZE || l.getBound().getHeight() > MAX_LEAF_SIZE || Math.random() > 0.25) {
                        // Разделить лист!
                        if (l.split()) {
                            // Если разделение прошло успешно, добавить дочерние листы в вектор
                            leafs.add(l.getLeftChild());
                            leafs.add(l.getRightChild());
                            did_split = true;
                        }
                    }
                }
            }
        }
    }

    public BspLeaf getRootLeaf() {
        if (leafs.get(0) != null) {
            return (BspLeaf) leafs.get(0);
        }

        return null;
    }
}
