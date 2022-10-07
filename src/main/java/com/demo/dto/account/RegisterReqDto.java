package com.demo.dto.account;

import com.demo.domain.User;
import com.demo.dto.validation.ValidationGroups;
import lombok.Data;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterReqDto {
    @NotBlank(message = "이름은 비워둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 1, max = 3, message = "이름은 한글자에서 세글자 사이여야 합니다", groups = ValidationGroups.SizeCheckGroup.class)
    @Pattern(regexp = "^[가-힇]*$",
            message = "이름은 한글만 입력 가능합니다",
            groups = ValidationGroups.PatternCheckGroup.class
    )private String lastName;

    @NotBlank(message = "성은 비워둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 1, max = 2, message = "성은 한글자에서 두글자 사이여야 합니다", groups = ValidationGroups.SizeCheckGroup.class)
    @Pattern(regexp = "^[가-힇]*$",
            message = "성은 한글만 입력 가능합니다",
            groups = ValidationGroups.PatternCheckGroup.class
    )private String firstName;


    @Email//개좋아
    @NotBlank(message="이메일은 비워둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class) //notblank를 밑으로 내려야 errorMap에 들어가는 메세지를 덮어버려서 비웠을때는 blank 메세지를 띄울 수 있음
    private String email;

    @NotBlank(message = "비밀번호는 비워둘 수 없습니다.", groups = ValidationGroups.NotBlankGroup.class)
    @Size(min = 8, max =16, message = "비밀번호는 8자에서 16자 사이여야 합니다", groups = ValidationGroups.SizeCheckGroup.class)
                //하나의 글자마다 ()를 검사 영문인지, 숫자인지, 정해진특수문자인지 []의 전체조건은 전체로 입력한 값의 조건 {}는 전체 글자 수 조건
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*_])[a-zA-Z\\d-~!@#$%^&*_]*$",
            message = "숫자, 영문, 특수기호(~,!,@,#,$,%,^,&,*,_)를 하나 이상 포함하여 작성해야 합니다",
            groups = ValidationGroups.PatternCheckGroup.class
    )private String password;

    public User toUserEntity(){
        return User.builder()
                .username(email)
                .password(new BCryptPasswordEncoder().encode(password))//password 암호화
                .name(firstName + lastName)
                .email(email)
                .role_id(1) //회원 가입이니까 일반 유저 (1)
                .build();
    }
}
