package com.gamedev.generator.service;

import com.gamedev.generator.model.MapGraph;
import com.gamedev.generator.model.Room;
import com.gamedev.generator.model.bsp.BspLeaf;
import com.gamedev.generator.model.bsp.BspTree;
import com.gamedev.generator.util.BspUtil;
import com.gamedev.generator.util.MathUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspService {
    BspUtil bspUtil;

    public BspTree createBspTree(Integer width, Integer height) {
        return bspUtil.createMap(width, height);
    }

    public MapGraph createMap(Integer width, Integer height){
        BspTree bspTree = createBspTree(width, height);

        MapGraph map = new MapGraph();
        map.setRooms(createRooms(bspTree.getRootLeaf()));

        return map;
    }
    private List<Room> createRooms(BspLeaf leaf) {
        List<Room> rooms = new ArrayList<>();
        createRoomsRecursive(leaf, rooms);

        return rooms;
    }

    private void createRoomsRecursive(BspLeaf leaf, List<Room> rooms){
        BspLeaf leftChild = leaf.leftChild;
        BspLeaf rightChild = leaf.rightChild;

        // Если лист разделен, перейти в дочерние листы
        if (leftChild != null || rightChild != null) {
            if (leftChild != null) {
                createRoomsRecursive(leftChild, rooms);
            }
            if (rightChild != null) {
                createRoomsRecursive(rightChild, rooms);
            }
        } else {
            // Создание комнаты в текущем листе
            Point roomSize;
            Point roomPos;

            // Размер комнаты может быть от 3x3 до размеров листа минус 2
            roomSize = new Point(MathUtil.getRandIntInRange(leaf.width - leaf.minWidth, leaf.width)
                    , MathUtil.getRandIntInRange(leaf.height - leaf.minHeight, leaf.height));

            // Расположение комнаты внутри листа
            roomPos = new Point(MathUtil.getRandIntInRange(0,leaf.width - roomSize.x)
                    , MathUtil.getRandIntInRange(0,leaf.height - roomSize.y));
            Room room = new Room(new Rectangle(leaf.x, leaf.y, leaf.width, leaf.height)
                    ,new Rectangle(leaf.x + roomPos.x, leaf. y + roomPos.y, roomSize.x, roomSize.y));

            rooms.add(room);
        }
    }

    public List<BspLeaf> getLastLeafs(BspLeaf currenLeaf){
        List<BspLeaf> lastLeafs = new ArrayList<>();
        getLastLeafListRecursive(currenLeaf, lastLeafs);

        return lastLeafs;
    }

    private void getLastLeafListRecursive(BspLeaf currenLeaf, List<BspLeaf> lastLeafs){
        if(currenLeaf.leftChild == null && currenLeaf.rightChild == null){
            lastLeafs.add(currenLeaf);
            return;
        }

        if(currenLeaf.leftChild != null){
            getLastLeafListRecursive(currenLeaf.leftChild, lastLeafs);
        }
        if(currenLeaf.rightChild != null){
            getLastLeafListRecursive(currenLeaf.rightChild, lastLeafs);
        }
    }
}
