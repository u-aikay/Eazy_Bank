package com.eazybank.services;

import com.eazybank.dtos.UserDto;
import com.eazybank.model.Users;
import com.eazybank.repos.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final Users user;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Users> users = Optional.of(userRepo.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User with email "+ username + " not found")));
//
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        users.get().getRoles().forEach(roles -> authorities.add(new SimpleGrantedAuthority(roles.getRole())));
//        return new org.springframework.security.core.userdetails.User(users.get().getUsername(),
//                users.get().getPassword(), authorities);
        return null;
    }


    @Override
    public ResponseEntity<UserDto> customerRegistration(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getConfirmPassword())){
            throw new RuntimeException("password do not match");
        }
        if(userRepo.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("User already exist");
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        Users newUser = Users.builder()
                .fullName(userDto.getFullName())
                .username(userDto.getUsername())
                .password(encodedPassword)
                .email(userDto.getEmail())
                .Role("CUSTOMER")
                .build();
        userRepo.save(newUser);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
}


