package com.demo.controller.admin.api;

import com.demo.Exception.CustomInternalServerErrorException;
import com.demo.aop.annotation.ValidAspect;
import com.demo.dto.CMRespDto;
import com.demo.dto.admin.ProductAdditionReqDto;
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
        String productName = productAdditionReqDto.getName();
        for(int i = 0; i < 20; i++) {
            if(i % 4 == 0){
                productAdditionReqDto.setName(productName + "-" + (i + 1));
            }
            productService.addProduct(productAdditionReqDto);
        }

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
}
