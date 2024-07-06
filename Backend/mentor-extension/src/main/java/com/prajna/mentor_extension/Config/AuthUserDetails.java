package com.prajna.mentor_extension.Config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prajna.mentor_extension.Entity.Users;
import com.prajna.mentor_extension.Service.UserService;

@Service
public class AuthUserDetails implements UserDetailsService{
    @Autowired
    private UserService AuthUserService;

    @Override
    public UserDetails loadUserByUsername(String AuthUsername) throws UsernameNotFoundException {
        Optional<Users> AuthUser = Optional.ofNullable(AuthUserService.getUsersByEmail(AuthUsername));
        if (AuthUser.isEmpty()) {
            throw new UsernameNotFoundException("AuthUser details not found for the AuthUser : " + AuthUsername);
        }
        return User.builder()
                .username(AuthUser.get().getEmail())
                .password(AuthUser.get().getPassword())
                .disabled(!AuthUser.get().getActive())
                .build();
    }
}
