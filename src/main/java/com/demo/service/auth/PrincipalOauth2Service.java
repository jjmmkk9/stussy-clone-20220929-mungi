package com.demo.service.auth;

import com.demo.domain.User;
import com.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2Service extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    //loaduser 한번만 설명해달라 하기
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = userRequest.getClientRegistration().getClientName(); //구글인지 네이버인지 ClientName으로 가져온다
        OAuth2User oAuth2User = super.loadUser(userRequest); //사용자 정보가 다 들어있다.

        log.info("userRequest >>>> {}", userRequest);
        log.info("getClientRegistration >>>> {}", userRequest.getClientRegistration());
        log.info("getAttributes >>>> {}", oAuth2User.getAttributes());
        /*
        userRequest >>>> org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@6f7f817b
        getClientRegistration >>>> ClientRegistration{registrationId='google', clientId='33513345693-him0dn6uql2o81umdle4d8fhns5i4tjo.apps.googleusercontent.com', clientSecret='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', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@2764fbc9, clientName='Google'}
        getAttributes >>>> {sub=118060866167697989704, name=박박박, given_name=박박, family_name=박, picture=https://lh3.googleusercontent.com/a/ALm5wu2GeVd_x5tFUarpFG9MnWPCS2c_6_tZfjNEQc6F=s96-c, email=abcdqwerty0192@gmail.com, email_verified=true, locale=ko}
         */

        User user = getOauth2User(provider, oAuth2User.getAttributes());

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private User getOauth2User(String provider, Map<String, Object> attributes) {
        String oauth2_id = null;
        String id = null;
        String email = null;

        User user = null;

        Map<String, Object> response = null;

        if(provider.equalsIgnoreCase("google")) { //구글이면 response에 바로 attributes 넣어줌
            response = attributes;
            id = (String) response.get("sub");
        }else if(provider.equalsIgnoreCase("naver")) { //네이버면 response에 attributes 안에 있는 response를 집어넣어주삼
            response = (Map<String, Object>) attributes.get("response");
            id = (String) response.get("id");
        }

        oauth2_id = provider + "_" + id;   //google_어쩌고저쩌고 고유 id
        email = (String) response.get("email");

        user = accountRepository.findUserByEmail(email);
        if(user == null) { //이메일 중복검사에서 검사 결과가 없으면(신규)
            user = User.builder()
                    .username(email)
                    .oauth_username(oauth2_id)
                    .password(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString().replaceAll("-", "")))//password는 uuid로 생성해서 넣어줌
                    .name((String) response.get("name")) // response.get("name")의 value는 obj 라서 다운캐스팅 해줘요
                    .email(email)
                    .role_id(1) //일반 회원이니 1 go
                    .provider(provider)
                    .build();

            accountRepository.save(user);

        }else if(user.getOauth_username() == null){ //가입된 이메일이 있는데 Oauth_username이 없으묜 (기존 일반회원)
            user.setOauth_username(oauth2_id);
            user.setProvider(provider);
            accountRepository.updateUserOauth2(user); //update 해줘요
        }

        return user;
    }
}
