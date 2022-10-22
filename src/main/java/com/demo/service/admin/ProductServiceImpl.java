package com.demo.service.admin;

import com.demo.Exception.CustomInternalServerErrorException;
import com.demo.Exception.CustomValidationException;
import com.demo.aop.annotation.LogAspect;
import com.demo.domain.Product;
import com.demo.domain.ProductImgFile;
import com.demo.dto.admin.ProductAdditionReqDto;
import com.demo.dto.admin.ProductListRespDto;
import com.demo.dto.admin.ProductModificationReqDto;
import com.demo.repository.admin.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            //원래 product에서 getId 해서 가져왔는데 수정할때 img insert 는 dto 것을 사용
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

        //받아 온 files 에서 file 하나씩
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
            //ProductImgFile builder 로 생성
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



    //수정하기 누르면 컨트롤러에 postMapping으로 dto 들어와서 여기서는 response 가 boolean 인데 시ㅏ ㄹ읾니러ㅏㅣ넒ㄴ어ㅏ램ㄴ오러ㅣㅇㄴ[랴ㅕㅠㄷ조
    @Override
    public boolean updateProduct(ProductModificationReqDto productModificationReqDto) throws Exception {
        boolean status = false;
                                    //dto to Entity 해서 Product 객체 update 쿼리로
        int result = productRepository.setProduct(productModificationReqDto.toProductEntity());
        //업데이트 건수가 0이 아니면
        if(result != 0){
            status = true;
            boolean insertStatus = true;
            boolean deleteStatus = true;
                                        //만약 업데이트한 파일이 있으면?
            if(productModificationReqDto.getFiles() != null){
                                                //dto의 files와 product_id 가져와
                insertStatus = insertProductImg(productModificationReqDto.getFiles(), productModificationReqDto.getId());
                //db에 insert 잘 됐으면 true
            }
            if(productModificationReqDto.getDeleteImgFiles() != null){
                                                //dto의 deleteFiles와 product_id 가져와
                deleteStatus = deleteProductImg(productModificationReqDto.getDeleteImgFiles(), productModificationReqDto.getId());
            }
            status = status && insertStatus && deleteStatus;

            if(status == false){
                log.info("insert status: " + insertStatus);
                log.info("delete status: " + deleteStatus);

                throw new CustomInternalServerErrorException("상품 수정 오류");
            }
        }
        return status;
    }

    //아래의 insertProductImg 와 deleteProductImg 는 addProduct 에서 dto에 해당 리스트들이 null 이 아니면 실행되는 함수라서 null exception 발생 불가
    private boolean insertProductImg(List<MultipartFile> files, int productId) throws Exception {
        boolean status = false;

        List<ProductImgFile> productImgFiles = getProductImgFiles(files,productId);

        return productRepository.saveImgFiles(productImgFiles) > 0; //insert된 개수가 0 보다 크면 true
    }

    private boolean deleteProductImg(List<String> deleteImgFiles, int productId) throws Exception{
        boolean status = false;

        Map<String , Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("deleteImgFiles", deleteImgFiles);

        int result = productRepository.deleteImgFiles(map);

        //delete 건수가 0이 아니면!
        if(result != 0){
            //파일 delete도 반복 돌아야한다
            deleteImgFiles.forEach(temp_name ->{
                // path -> 파일객체 하나하나의 이름까지 가져와서 그 경로에서 지워야 하기 때문에 forEach
                Path uploadPath = Paths.get(filePath + "/product/" + temp_name);

                File file = new File(uploadPath.toUri());
                if(!file.exists()){
                    file.mkdirs();
                    if(!file.delete()){
                        throw new RuntimeException();
                    }
                        //람다라서 이렇게 접근 불가하대
                        //deleteSuccess++;
                }else{
                    if(!file.delete()){
                        throw new RuntimeException();
                    }
                }
            });
            status = true;
        }
        return status;
    }
    @Override
    public boolean deleteProduct(int productId) throws Exception {
        List<ProductImgFile> productImgFiles = productRepository.getProductImgsList(productId);

        if(productRepository.deleteProduct(productId) > 0){
            productImgFiles.forEach(productImgFile -> {
                Path uploadPath = Paths.get(filePath + "/product/" + productImgFile.getTemp_name());

                File file = new File(uploadPath.toUri());
                if(file.exists()) {
                    file.delete();
                }
            });
            return true;
        }

        return false;
    }

}
