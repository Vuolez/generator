package com.gamedev.generator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.awt.*;
import java.util.List;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room extends Node{
    Rectangle bound;
    Rectangle content;
    List<Room> connectedRooms;

    public Room(Rectangle bound, Rectangle content){
        this.bound = bound;
        this.content = content;
    }
}
