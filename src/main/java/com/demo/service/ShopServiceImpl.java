package com.demo.service;

import com.demo.domain.ProductDetail;
import com.demo.dto.shop.CollectionListRespDto;
import com.demo.dto.shop.ProductDetailRespDto;
import com.demo.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService{

    private final ShopRepository shopRepository;

    @Override
    public List<CollectionListRespDto> getCollections(String category, int page) throws Exception {
        List<CollectionListRespDto> responses = new ArrayList<CollectionListRespDto>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", category);
        map.put("index", (page - 1) * 16); //page 가 왜 0 ??????????????  xml에서 #{index}가 -16이 되는 문제

        shopRepository.getCollectionList(map).forEach(collection -> {
            responses.add(collection.toListRespDto());
        });
        return responses;
    }

    @Override
    public ProductDetailRespDto getProductDetails(int groupId) throws Exception {
        List<ProductDetail> productDetails = shopRepository.getProduct(groupId);
        List<String> imgNames = new ArrayList<String>();

        productDetails.get(0).getProductImgFiles().forEach(productFile -> {
            imgNames.add(productFile.getTemp_name());
        });

        //option 만들어주기
        Map<String, List<String>> options = new HashMap<String, List<String>>();
        //groupId로 묶인 제품 하나 증에서 제품 요소들꺼냄(가격, 색상, 사이즈 등)
        productDetails.forEach(productDetail -> {
            options.put(productDetail.getColor(), new ArrayList<>());
        });

        productDetails.forEach(productDetail -> {
            //흩어져있는 것들 color 동일하면 size 리스트에 add
            options.forEach((key, value) ->{
                if(key.equals(productDetail.getColor())){
                    value.add(productDetail.getSize());
                }
            });
        });

        return  ProductDetailRespDto.builder()
                .group_id(productDetails.get(0).getGroup_id())
                .name(productDetails.get(0).getName())
                .price(productDetails.get(0).getPrice())
                .infoSimple(productDetails.get(0).getInfo_simple())
                .infoDetail(productDetails.get(0).getInfo_detail())
                .infoOption(productDetails.get(0).getInfo_option())
                .infoManagement(productDetails.get(0).getInfo_management())
                .infoShipping(productDetails.get(0).getInfo_shipping())
                .options(options)
                .imgNames(imgNames)
                .build();

    }
}
