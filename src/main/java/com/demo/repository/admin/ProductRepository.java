package com.demo.repository.admin;

import com.demo.domain.Product;
import com.demo.domain.ProductImgFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ProductRepository {
    public int saveProduct(Product product) throws Exception;
    public int saveImgFiles(List<ProductImgFile> product_img_files) throws Exception;

    public List<Product> getProductList(Map<String, Object> map) throws Exception;

    public int setProduct(Product product) throws Exception;
    public int deleteImgFiles(Map<String, Object> map) throws Exception;

    public List<ProductImgFile> getProductImgsList(int productId) throws Exception;
    public int deleteProduct(int productId) throws Exception;
 }
