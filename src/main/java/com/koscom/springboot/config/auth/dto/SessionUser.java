package com.koscom.springboot.config.auth.dto;


import com.koscom.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

// Session에 담기 위해서는 직렬화를 해야 함.
// Session에는 너무 많은 데이터를 담으면 Out of Memory 일어날 수 있음.
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

}
