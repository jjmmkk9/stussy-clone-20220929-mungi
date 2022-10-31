package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ShopPageController {
    @GetMapping("/collections/{category}") //최대한 모델 안쓸게요
    public String loadCollections(@PathVariable String category) {
        return "shop/collections";
    }


    @GetMapping("/products/{groupId}")
    public String loadProductDetail(@PathVariable String groupId) {
        return "shop/product";
    }
}
