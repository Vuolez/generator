package com.gamedev.generator.model.bsp;

import com.gamedev.generator.model.Node;
import lombok.Getter;

import java.awt.*;
import java.util.Vector;

// Leaf class represents a node in the binary space partitioning tree
public class BspLeaf extends Node {

    // Minimum size for a leaf to be split
    private final int MIN_LEAF_SIZE = 15;
    // Position and size of this leaf
    public int y, x, minWidth, minHeight, width, height;

    // Left and right child leaves
    public BspLeaf leftChild;
    public BspLeaf rightChild;
    // Hallways to connect this leaf to other leaves
    @Getter
    private Vector<Rectangle> halls;
    // Room that is inside this leaf
    @Getter
    private Rectangle room;

    // Constructor for initializing a leaf with position and size
    public BspLeaf(int X, int Y, int width, int height, int minWidth, int minHeight) {
        x = X;
        y = Y;
        this.minWidth = minWidth;
        this.width = width;
        this.minHeight = minHeight;
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
            leftChild = new BspLeaf(x, y, width, split, minWidth, minHeight);
            rightChild = new BspLeaf(x, y + split, width, height - split, minWidth, minHeight);
        } else {
            leftChild = new BspLeaf(x, y, split, height, minWidth, minHeight);
            rightChild = new BspLeaf(x + split, y, width - split, height, minWidth, minHeight);
        }
        // Split successful
        return true;
    }

    public Rectangle findRoom() {
        // Проходит через все листья, чтобы найти комнату, если такая есть.
        if (room != null)
            return room;
        else {
            Rectangle lRoom = null;
            Rectangle rRoom = null;
            if (leftChild != null) {
                lRoom = leftChild.getRoom();
            }

            if (rightChild != null) {
                rRoom = rightChild.getRoom();
            }

            if (lRoom == null && rRoom == null)
                return null;
            else if (rRoom == null)
                return lRoom;
            else if (lRoom == null)
                return rRoom;
            else if (Math.random() > .5)
                return lRoom;
            else
                return rRoom;
        }
    }

//    public void createHall(BspLeaf l, BspLeaf r) {
//        // Теперь мы соединяем эти две комнаты коридорами.
//        // Это выглядит довольно сложно, но это просто попытка понять,
//        // где находится каждая точка, а затем нарисовать прямую линию или пару линий
//        // для создания прямого угла и соединения их.
//        // Вы можете добавить дополнительную логику,
//        // чтобы сделать ваши коридоры более изогнутыми или выполнить более продвинутые действия, если захотите.
//        halls = new Vector<Rectangle>();
//
//        Point point1 = new Point((int) l.getCenterX(), (int) l.getCenterY());
//        Point point2 = new Point((int) r.getCenterX(), (int) r.getCenterY());
//
//        int w = point2.x - point1.x;
//        int h = point2.y - point1.y;
//
//        int thikness = 5;
//        if (w < 0) {
//            if (h < 0) {
//                if (Math.random() < 0.5) {
//                    halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point2.x, point2.y, thikness, Math.abs(h)));
//                } else {
//                    halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point1.x, point2.y, thikness, Math.abs(h)));
//                }
//            } else if (h > 0) {
//                if (Math.random() < 0.5) {
//                    halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point2.x, point1.y, thikness, Math.abs(h)));
//                } else {
//                    halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point1.x, point1.y, thikness, Math.abs(h)));
//                }
//            } else // если (h == 0)
//            {
//                halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), thikness));
//            }
//        } else if (w > 0) {
//            if (h < 0) {
//                if (Math.random() < 0.5) {
//                    halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point1.x, point2.y, thikness, Math.abs(h)));
//                } else {
//                    halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point2.x, point2.y, thikness, Math.abs(h)));
//                }
//            } else if (h > 0) {
//                if (Math.random() < 0.5) {
//                    halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point2.x, point1.y, thikness, Math.abs(h)));
//                } else {
//                    halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), thikness));
//                    halls.add(new Rectangle(point1.x, point1.y, thikness, Math.abs(h)));
//                }
//            } else // если (h == 0)
//            {
//                halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), thikness));
//            }
//        } else // если (w == 0)
//        {
//            if (h < 0) {
//                halls.add(new Rectangle(point2.x, point2.y, thikness, Math.abs(h)));
//            } else if (h > 0) {
//                halls.add(new Rectangle(point1.x, point1.y, thikness, Math.abs(h)));
//            }
//        }
//    }

    public int getCenterX(){
        return x + (width /2);
    }


    public int getCenterY(){
        return y + (height /2);
    }
}