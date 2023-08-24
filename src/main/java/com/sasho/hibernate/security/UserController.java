package com.sasho.hibernate.security;

import com.sasho.hibernate.security.dto.UserFormRequest;
import com.sasho.hibernate.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users/register")
    public String register(@RequestBody UserFormRequest request) {
        UserDetails userDetails = this.userService.registerUser(request);
        return userDetails.getUsername();
    }

    @PostMapping("/users/login")
    public String login(@RequestBody UserFormRequest request) {
        return userService.verifyLogin(request);
    }
}
