package com.gamedev.generator.controller;

import com.gamedev.generator.service.BspService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bsp")
//@Tag(name = "Генерация уровней алгоритмом bsp tree ")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BspController {
    BspService bspService;

    @GetMapping("/create")
    public void getStructCatalogRootList() {
        bspService.createMap(300, 300);
    }
}
