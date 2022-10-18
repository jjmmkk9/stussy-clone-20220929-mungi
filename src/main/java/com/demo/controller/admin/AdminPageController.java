package com.demo.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



/*
단순 페이지 여는 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminPageController {

    @GetMapping("/product/addition")
    public String loadProductAddition(){
        return "admin/product_add";
    }

    @GetMapping("/products")
    public String loadProductList() {
        return "admin/product_list";
    }
}


