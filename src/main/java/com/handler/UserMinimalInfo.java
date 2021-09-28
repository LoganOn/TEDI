package com.handler;

import com.model.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class UserMinimalInfo {

    private long userId;

    private String email;

    private String role;

    public UserMinimalInfo(Users user){
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
