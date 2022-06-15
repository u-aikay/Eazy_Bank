package com.eazybank.controllers;

import com.eazybank.dtos.UserDto;
import com.eazybank.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return userService.customerRegistration(userDto);
    }

}
