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





}
