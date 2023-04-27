package com.gamedev.generator.service;

import com.gamedev.generator.model.BspMap;
import com.gamedev.generator.util.BspUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspService {
    BspUtil bspUtil;

    public BspMap createMap(Integer width, Integer height) {
        return bspUtil.createMap(width, height);
    }
}
