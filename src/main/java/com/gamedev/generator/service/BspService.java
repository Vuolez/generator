package com.gamedev.generator.service;

import com.gamedev.generator.model.Node;
import com.gamedev.generator.model.bsp.BspLeaf;
import com.gamedev.generator.util.BspUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspService {
    BspUtil bspUtil;

    public List<Node> createMap(Integer width, Integer height) {
        return bspUtil.getLastLeafs(bspUtil.createMap(width, height).getRootLeaf());
    }
}
