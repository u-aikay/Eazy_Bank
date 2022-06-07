package com.eazybank.services;

import com.eazybank.model.Users;
import com.eazybank.repos.UserRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = Optional.of(userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email "+ username + " not found")));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        users.get().getRoles().forEach(roles -> authorities.add(new SimpleGrantedAuthority(roles.getRole())));
        return new org.springframework.security.core.userdetails.User(users.get().getUsername(),
                users.get().getPassword(), authorities);
    }
}
