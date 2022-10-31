package com.demo.service;

import com.demo.dto.shop.CollectionListRespDto;
import com.demo.dto.shop.ProductDetailRespDto;

import java.util.List;

public interface ShopService {
    public List<CollectionListRespDto> getCollections(String category, int page)throws Exception;
    public ProductDetailRespDto getProductDetails(int groupId)throws Exception;
}
