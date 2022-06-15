package com.eazybank.services.impl;

import com.eazybank.dtos.UserDto;
import com.eazybank.model.User;
import com.eazybank.repos.UserRepo;
import com.eazybank.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

@Data
@AllArgsConstructor
@Transactional
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<UserDto> customerRegistration(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getConfirmPassword())){
            throw new RuntimeException("password do not match");
        }
        if(userRepo.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("User already exist");
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User newUser = User.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(encodedPassword)
                .email(userDto.getEmail())
                .Role("CUSTOMER")
                .build();
        userRepo.save(newUser);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}


