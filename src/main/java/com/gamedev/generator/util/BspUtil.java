package com.gamedev.generator.util;

import com.gamedev.generator.model.bsp.BspTree;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspUtil {

    public BspTree createMap(Integer width, Integer height){
        BspTree bspTree = new BspTree();
        bspTree.create(width, height);

        return bspTree;
    }


}