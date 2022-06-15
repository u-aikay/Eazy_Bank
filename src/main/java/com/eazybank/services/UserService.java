package com.eazybank.services;

import com.eazybank.dtos.UserDto;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<UserDto> customerRegistration(UserDto userDto);
}
