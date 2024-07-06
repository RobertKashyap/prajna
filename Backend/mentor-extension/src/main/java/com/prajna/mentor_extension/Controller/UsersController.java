package com.prajna.mentor_extension.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.prajna.mentor_extension.Config.AuthUserDetails;
import com.prajna.mentor_extension.DTO.UserDTO;
import com.prajna.mentor_extension.Entity.Users;
import com.prajna.mentor_extension.Service.UserService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UsersController {

    @Autowired
    private UserService UsersService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserDetails authUserDetails;

    @SuppressWarnings("rawtypes")
    @PostMapping("register")
    public ResponseEntity saveUserss(@RequestBody Users entity) {

        try {
            Users createdUsers = UsersService.createUsers(entity);
            if (createdUsers == null)
                throw new RuntimeException("Users is alrady present whth email : " + entity.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("users")
    public ResponseEntity<List<Users>> getAllUserss() {

        return ResponseEntity.status(HttpStatus.OK).body(UsersService.getAllUserss());
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDTO user) {
        try {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
                    user.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);

            if (authentication.isAuthenticated()) {
                Object authenticatedUser =  authentication.getPrincipal();
                // Map<String, Object> responseBody = new HashMap<>();
                // responseBody.put("message", "Login successful");
                // return ResponseEntity.ok(responseBody);
               // return authenticatedUser;

               return ResponseEntity.status(HttpStatus.CREATED).body(authenticatedUser);
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return null;
    }

    @GetMapping("/authuser")
	public ResponseEntity<Users> getUserDetailsAfterLogin() {

        return ResponseEntity.status(HttpStatus.OK).body(authUserDetails.getCurrentUser());
		
	}

}
