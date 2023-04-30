package com.gamedev.generator.util;

import com.gamedev.generator.model.Node;
import com.gamedev.generator.model.bsp.BspLeaf;
import com.gamedev.generator.model.bsp.BspTree;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspUtil {

    public BspTree createMap(Integer width, Integer height){
        BspTree bspTree = new BspTree();
        bspTree.create(width, height);

        return bspTree;
    }

    public List<BspLeaf> getLastLeafs(BspLeaf rootLeaf){
        List<BspLeaf> lastLeafs = new ArrayList<>();
        getLastLeafListRecursive(rootLeaf, lastLeafs);

        return lastLeafs;
    }

    private void getLastLeafListRecursive(BspLeaf currenLeaf, List<BspLeaf> lastLeafs){
        if(currenLeaf.getLeftChild() == null && currenLeaf.getRightChild() == null){
            lastLeafs.add(currenLeaf);
            return;
        }

        if(currenLeaf.getLeftChild() != null){
            getLastLeafListRecursive(currenLeaf.getLeftChild(), lastLeafs);
        }
        if(currenLeaf.getRightChild() != null){
            getLastLeafListRecursive(currenLeaf.getRightChild(), lastLeafs);
        }
    }
}