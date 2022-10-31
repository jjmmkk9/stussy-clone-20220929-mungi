package com.demo.repository;

import com.demo.domain.CollectionProduct;
import com.demo.domain.ProductDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface ShopRepository {

    public List<CollectionProduct> getCollectionList(Map<String, Object> map) throws Exception;

    public List<ProductDetail> getProduct(int groupId) throws Exception;
}
