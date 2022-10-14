package com.demo.service.admin;

import com.demo.dto.admin.ProductAdditionReqDto;
import com.demo.dto.admin.ProductListRespDto;

import javax.validation.constraints.DecimalMax;
import java.util.List;

public interface ProductService {

    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception;
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception;
}
