package com.demo.controller.admin.api;

import com.demo.Exception.CustomInternalServerErrorException;
import com.demo.aop.annotation.LogAspect;
import com.demo.aop.annotation.ValidAspect;
import com.demo.dto.CMRespDto;
import com.demo.dto.admin.ProductAdditionReqDto;
import com.demo.dto.admin.ProductModificationReqDto;
import com.demo.dto.validation.ValidationSequence;
import com.demo.service.admin.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;

/*
request 받고 response 주는 restController
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    //validAspect 달아주셔야겟됴
    @ValidAspect
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@Validated(ValidationSequence.class) ProductAdditionReqDto productAdditionReqDto, BindingResult bindingResult) throws Exception {
//        String productName = productAdditionReqDto.getName();
//        for(int i = 0; i < 20; i++) {
//            if(i % 4 == 0){
//                productAdditionReqDto.setName(productName + "-" + (i + 1));
//            }
//            productService.addProduct(productAdditionReqDto);
//        }

//        return ResponseEntity
//                .created(null)
//                .body(new CMRespDto<>(1, "Successfully", null));
        return ResponseEntity
                .created(null)
                .body(new CMRespDto<>(1, "Successfully", productService.addProduct(productAdditionReqDto)));

    }
    @GetMapping("/products")
    public ResponseEntity<?> getProductList(@RequestParam int page,
                                            @RequestParam @Nullable String category,
                                            @RequestParam @Nullable String searchValue) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.getProductList(page, category, searchValue)));
    }


    /*
    이미지 변화 있을수도 있고 없을수도 있고  product 변화 있을수도 있고 없을수도 있고
    =기존 데이터랑 비교해서 변화 있는지 체크

    이미지는 deleteList 나 NewList의 내용이 있으면 동작하면 됨
    업데이트는 무조건 해주고 인서트 딜리트는 내용 확인해서 해주기
     */
    @LogAspect
    @ValidAspect
    @PostMapping("/product/modification")
    public ResponseEntity<?> updateProduct(@Validated ProductModificationReqDto productModificationReqDto, BindingResult bindingResult) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.updateProduct(productModificationReqDto)));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId) throws Exception {

        return ResponseEntity.ok(new CMRespDto<>(1, "Successfully", productService.deleteProduct(productId)));
    }
}
