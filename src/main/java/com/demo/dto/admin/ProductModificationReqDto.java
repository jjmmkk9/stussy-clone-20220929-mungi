package com.demo.dto.admin;

import com.demo.domain.Product;
import com.demo.dto.validation.ValidationGroups;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductModificationReqDto {
    @Min(value = 0, message = "상품 코드는 음수일 수 없습니다..")
    //상품 코드 : key 값 xml에서 사용
    private int id;
    @Max(value = 1000000, message = "최대 금액은 100만원 까지만 설정 가능합니다.")
    @Min(value = 100, message = "최소 금액은 100원입니다.")
    private int price;
    @NotBlank(message = "빈 값일 수 없습니다.", groups = ValidationGroups.NotBlankGroup.class)
    private String color;
    @NotBlank(message = "빈 값일 수 없습니다.", groups = ValidationGroups.NotBlankGroup.class)
    private String size;

    private String infoSimple;
    private String infoDetail;
    private String infoOption;
    private String infoManagement;
    private String infoShipping;

    private List<String> deleteImgFiles; //지운 파일<이름>
    private List<MultipartFile> files;  //추가한 파일리스트<파일>

    public Product toProductEntity(){
        return Product.builder()
                .id(id)
                .price(price)
                .color(color)
                .size(size)
                .info_simple(infoSimple)
                .info_detail(infoDetail)
                .info_option(infoOption)
                .info_management(infoManagement)
                .info_shipping(infoShipping)
                .build();
    }
}
