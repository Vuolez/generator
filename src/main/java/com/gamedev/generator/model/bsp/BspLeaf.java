package com.gamedev.generator.model.bsp;

import com.gamedev.generator.model.Node;
import lombok.Getter;

import java.awt.*;

public class BspLeaf extends Node {

    private final Integer MIN_LEAF_SIZE = 15;

    @Getter
    private BspLeaf leftChild;
    @Getter
    private BspLeaf rightChild;

    public BspLeaf(Rectangle bound) {
        super(bound);
    }


    // Method for splitting the leaf into two children
    public boolean split() {
        // If the leaf already has children, return false
        if (leftChild != null || rightChild != null) {
            return false;
        }
        // Determine the direction of the split
        // if the width is >25% larger than height, we split vertically
        // if the height is >25% larger than the width, we split horizontally
        // otherwise we split randomly
        boolean splitH = Math.random() > 0.5;
        if (getBound().width > getBound().height && getBound().width / getBound().height >= 1.25) {
            splitH = false;
        } else if (getBound().height > getBound().width && getBound().height / getBound().width >= 1.25) {
            splitH = true;
        }

        // Determine the maximum height or width for splitting
        int max = (splitH ? getBound().height : getBound().width) - MIN_LEAF_SIZE;
        // If the area is too small to split any more, return false
        if (max <= MIN_LEAF_SIZE) {
            return false;
        }
        // Determine where to split
        int split = (int) (Math.random() * (max - MIN_LEAF_SIZE)) + MIN_LEAF_SIZE;
        // Create left and right child leaves based on the direction of the split
        if (splitH) {
            leftChild = new BspLeaf(new Rectangle(getBound().x, getBound().y, getBound().width, split));
            rightChild = new BspLeaf(new Rectangle(getBound().x, getBound().y + split, getBound().width, getBound().height - split));
        } else {
            leftChild = new BspLeaf(new Rectangle(getBound().x, getBound().y, split, getBound().height));
            rightChild = new BspLeaf(new Rectangle(getBound().x + split, getBound().y, getBound().width - split, getBound().height));
        }
        // Split successful
        return true;
    }

}