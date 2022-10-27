package com.demo.service;

import com.demo.dto.CollectionListRespDto;

import java.util.List;

public interface ShopService {
    public List<CollectionListRespDto> getCollections(String category, int page)throws Exception;
}
