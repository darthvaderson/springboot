package com.koscom.springboot.config.auth.login;


import com.koscom.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    // 판단기준이 되는 것
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // method의 파라미터 어노테이션이 @LoginUser
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // 클래스의 타입이 SessionUser 인지
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        // 둘다 true 인경우, resolveArgument 메소드를 실행
        return isLoginUserAnnotation && isUserClass;

    }

    // 기준 충족 시 액션
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SessionUser user = (SessionUser)httpSession.getAttribute("user");

        return user;
    }

}
