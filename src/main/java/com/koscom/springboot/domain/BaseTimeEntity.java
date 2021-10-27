package com.koscom.springboot.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Auditing: createAt / updateAt / createUser / updateUser 자동으로 관리해주는 기능
@Getter
@MappedSuperclass // 해당 클래스 상속받은 Posts, User 등의 클래스 테이블의 컬럼으로 JPA가 인식하게 하는 것
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate  // 등록시간
    private LocalDateTime createdDate;

    @LastModifiedDate  // 수정시간
    private LocalDateTime modifiedDate;
}
