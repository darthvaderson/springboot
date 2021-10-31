package com.koscom.springboot.config.auth.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 어노테이션이 선언될 수 있는 위치. 여기에서는 메소드 파라미터로만 사용가능하게 설정.
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
