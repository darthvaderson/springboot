package com.koscom.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration  // spring 에서 설정 클래스임을 아렬주는 어노테이션
@EnableJpaAuditing  // JPA Auditing 활성화 옵션 - SpringBoot는 관례 중심 개발.
public class JpaConfig {
}
