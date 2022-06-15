package com.eazybank.services;

import com.eazybank.dtos.UserDto;
import com.eazybank.model.Roles;
import com.eazybank.model.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
//    Users saveUser(Users users);
//    Roles saveRole(Roles roles);
////    void addRoleToUser(String username, String roles);
//    Users getUser(String username);
//    List<Users> getAllUsers();

    ResponseEntity<UserDto> customerRegistration(UserDto userDto);
}
