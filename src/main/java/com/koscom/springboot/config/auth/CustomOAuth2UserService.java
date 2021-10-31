package com.koscom.springboot.config.auth;


import com.koscom.springboot.config.auth.dto.OAuthAttributes;
import com.koscom.springboot.config.auth.dto.SessionUser;
import com.koscom.springboot.domain.user.User;
import com.koscom.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        // 로그인 성공 시 세션에 저장, 재로그인 할 필요 없는 상태로 구현

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }
    
    //가입 안한 사용자 : 가입 시킴
    // 기가입된 사용자 : 소셜정보에서 변경된 부분 있으면 DB에 반영
    // 기가입된 사용자 : 세션에서 인증정보만 저장하면 로그인 상태가 된다.
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(savedUser -> savedUser.update(attributes.getName(), attributes.getPicture())) // User 찾은 경우
                .orElse(attributes.toEntity());  // User 못 찾은 경우
        
        return userRepository.save(user); // jpa.save는 insert 또는 update 로 작용한다.
    }
}
