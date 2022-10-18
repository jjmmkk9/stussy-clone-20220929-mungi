package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/*
이미지 파일 처리 - WebMvcConfigurer 구현 - addResourceHandlers 오버라이드
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.path")
    private String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/image/**") //이주소
                .addResourceLocations("file:///" + filePath)//여기 매핑해라
                .setCachePeriod(60*60)//유지
                .resourceChain(true)
                .addResolver(new PathResourceResolver(){
                    @Override                       //uri(resourcePath)를 utf-8로 디코딩 해주셔야
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);//파일명 한글로 가능~
                        return super.getResource(resourcePath, location);
                    }
                });
    }
}
