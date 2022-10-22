package com.demo.service.admin;

import com.demo.dto.admin.ProductAdditionReqDto;
import com.demo.dto.admin.ProductListRespDto;
import com.demo.dto.admin.ProductModificationReqDto;

import javax.validation.constraints.DecimalMax;
import java.util.List;

public interface ProductService {

    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception;
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception;

    /*
    업데이트를 먼저 하고 이미지 처리 해줄것
    파일은 안지워져도 상관없음 그러나 업데이트 처리 이후에 처리되어야 한다.
    데이터가 중요하기 때문..(?)
     */
    public boolean updateProduct(ProductModificationReqDto productModificationReqDto)throws Exception;
    public boolean deleteProduct(int productId) throws Exception;
}
