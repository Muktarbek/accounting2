package com.peaksoft.accounting.db.mapper;

import com.peaksoft.accounting.api.payload.LoginResponse;
import com.peaksoft.accounting.db.entity.RoleEntity;
import com.peaksoft.accounting.db.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginMapper {

    public LoginResponse toLoginView(String token, String message, UserEntity user) {

        var loginResponse = new LoginResponse();
        if(user != null){
            setAuthority(loginResponse, user.getRoles());
        }

        loginResponse.setJwt_token(token);
        loginResponse.setMessage(message);

        return loginResponse;
    }
    private void setAuthority(LoginResponse loginView, List<RoleEntity> roles){
        Set<String> authorities = new HashSet<>();
        for(RoleEntity role: roles){
            authorities.add(role.getName());
        }
        loginView.setAuthorities(authorities);
    }
}
