package com.gamedev.generator.util;

import com.gamedev.generator.model.BspMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspUtil {

    public BspMap createMap(Integer width, Integer height){
        BspMap bspMap = new BspMap();
        bspMap.create(width, height);

        return bspMap;
    }


}