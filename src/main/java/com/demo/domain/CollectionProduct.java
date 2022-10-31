package com.demo.domain;

import com.demo.dto.shop.CollectionListRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionProduct {

    //테이블이랑 일치시켜야되나
    private int group_id;
    private String category;
    private String name;
    private int price;
    private String temp_name;
    private int total_count;


    public CollectionListRespDto toListRespDto() {
        return CollectionListRespDto.builder()
                .groupId(group_id)
                .category(category)
                .name(name)
                .price(price)
                .imgName(temp_name)
                .totalCount(total_count)
                .build();
    }
    //img_name = temp_name 으로 upload 에서 가져올 것임

}
