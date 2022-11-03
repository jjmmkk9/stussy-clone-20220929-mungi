package com.demo.controller;

import com.demo.aop.annotation.LogAspect;
import com.demo.dto.order.OrderReqDto;
import com.demo.service.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {

    @LogAspect
    @GetMapping("")
    public String loadOrder(Model model, OrderReqDto orderReqDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        model.addAttribute("order", orderReqDto);
        model.addAttribute("principalUser",principalDetails.getUser());
        return "order/order";
    }
    //지금은 폼로그인으로 세션개념으로 서버가 정보를 가지고 있다고 함 -> 서버가 무거워지지 않게 jwt 를 사용하는 방식으로 변경해야한다.

}
