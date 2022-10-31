package com.demo.controller.api;

import com.demo.dto.CMRespDto;
import com.demo.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShopApi {

    private final ShopService shopService;

    @GetMapping("/collections/{category}")
    public ResponseEntity<?> getCollection(@PathVariable String category, int page) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Load Successfully", shopService.getCollections(category, page)));
    }


    @GetMapping("/products/{groupId}")//Get 요청때 groupId를 받고 이 id로 요청을 날려준다
    public ResponseEntity<?> getProduct(@PathVariable int groupId)throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", shopService.getProductDetails(groupId)));
    }
}
