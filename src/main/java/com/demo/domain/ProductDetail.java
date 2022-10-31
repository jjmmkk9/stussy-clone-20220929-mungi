package com.demo.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
//xml에서 getProductList해서 이 클래스를 List로 들고옴
public class ProductDetail {
    private int group_id;
    private String name;
    private int price;
    private String color;
    private String size;

    private String info_simple;
    private String info_detail;
    private String info_option;
    private String info_management;
    private String info_shipping;


    private List<ProductImgFile> productImgFiles;
}
