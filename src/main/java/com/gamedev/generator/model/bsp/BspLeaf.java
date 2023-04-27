package com.gamedev.generator.model.bsp;

import com.gamedev.generator.model.Node;

public class BspLeaf extends Node {

    private final Integer MIN_LEAF_SIZE = 15;
    public Integer y, x, width, height;

    public BspLeaf leftChild;
    public BspLeaf rightChild;

    public BspLeaf(int X, int Y, int width, int height) {
        x = X;
        y = Y;
        this.width = width;
        this.height = height;

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
        if (width > height && width / height >= 1.25) {
            splitH = false;
        } else if (height > width && height / width >= 1.25) {
            splitH = true;
        }

        // Determine the maximum height or width for splitting
        int max = (splitH ? height : width) - MIN_LEAF_SIZE;
        // If the area is too small to split any more, return false
        if (max <= MIN_LEAF_SIZE) {
            return false;
        }
        // Determine where to split
        int split = (int) (Math.random() * (max - MIN_LEAF_SIZE)) + MIN_LEAF_SIZE;
        // Create left and right child leaves based on the direction of the split
        if (splitH) {
            leftChild = new BspLeaf(x, y, width, split);
            rightChild = new BspLeaf(x, y + split, width, height - split);
        } else {
            leftChild = new BspLeaf(x, y, split, height);
            rightChild = new BspLeaf(x + split, y, width - split, height);
        }
        // Split successful
        return true;
    }

    public int getCenterX() {
        return x + (width / 2);
    }


    public int getCenterY() {
        return y + (height / 2);
    }
}