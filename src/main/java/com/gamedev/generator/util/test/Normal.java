package com.gamedev.generator.util.test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Normal {
    int x, y;

    public Normal(Normal normal) {
        this.x = normal.getX();
        this.y = normal.getY();
    }
}
