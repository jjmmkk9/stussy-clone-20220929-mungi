package com.demo.service.admin;

import com.demo.Exception.CustomInternalServerErrorException;
import com.demo.Exception.CustomValidationException;
import com.demo.aop.annotation.LogAspect;
import com.demo.domain.Product;
import com.demo.domain.ProductImgFile;
import com.demo.dto.admin.ProductAdditionReqDto;
import com.demo.dto.admin.ProductListRespDto;
import com.demo.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    @Value("${file.path}")
    private String filePath;
    private final ProductRepository productRepository;



    @Override
    public boolean addProduct(ProductAdditionReqDto productAdditionReqDto) throws Exception {
        int resultCount = 0;
        List<MultipartFile> files = productAdditionReqDto.getFiles();
        List<ProductImgFile> productImgFiles = null;
        Product product = productAdditionReqDto.toProductEntity();
        resultCount = productRepository.saveProduct(product);
        if(files != null) {
            int productId = product.getId();
            productImgFiles = getProductImgFiles(files, productId);
            resultCount = productRepository.saveImgFiles(productImgFiles);
        }
        if(resultCount == 0){
            throw new CustomInternalServerErrorException("상품 등록 실패");
        }
        return true;
    }
    private List<ProductImgFile> getProductImgFiles(List<MultipartFile> files, int productId) throws Exception {
        List<ProductImgFile> productImgFiles = new ArrayList<ProductImgFile>();
        files.forEach(file -> {
            String originName = file.getOriginalFilename();
            String extension = originName.substring(originName.lastIndexOf("."));
            String temp_name = UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(filePath + "/product/" + temp_name);
            File f = new File(filePath + "/product");
            if(!f.exists()) {
                f.mkdirs();
            }
            try {
                Files.write(uploadPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProductImgFile productImgFile = ProductImgFile.builder()
                    .product_id(productId)
                    .origin_name(originName)
                    .temp_name(temp_name)
                    .build();
            productImgFiles.add(productImgFile);
        });
        return productImgFiles;
    }





    @LogAspect
    @Override                                   //이 정보들은 ajax에서 받았고  paramsMap에 넣어줘야 우리가 xml 가지고가서 정보 load 가능
    public List<ProductListRespDto> getProductList(int pageNumber, String category, String searchText) throws Exception {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("index", (pageNumber - 1) * 10);
        paramsMap.put("category", category);
        paramsMap.put("searchText", searchText);
        List<ProductListRespDto> list = new ArrayList<ProductListRespDto>();
        productRepository.getProductList(paramsMap).forEach(product -> {
            list.add(product.toListRespDto());
        });
        return list;
    }
}