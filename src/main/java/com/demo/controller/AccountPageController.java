package com.demo.controller;


import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class AccountPageController {

<<<<<<< HEAD

//authentication failureHandler 에서 로그인 실패하면 위에 문구띄워주려고
///model 주입
@GetMapping("/login")           //요청 날릴때 null 가능한 요청 파라미터 error
public String login(Model model, @RequestParam @Nullable String error){
    if(error != null) {
        model.addAttribute("error", error.equals("auth") ? "이메일 또는 비밀번호가 잘못되었습니다." : "");
    }
    return "account/login";
=======
    @GetMapping("/login")
    public String login(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "account/login";
>>>>>>> af23d26 (AccountRepository, mappers, Service,)
}
@GetMapping("/register")
public String register(){
    return "account/register";
}
}
