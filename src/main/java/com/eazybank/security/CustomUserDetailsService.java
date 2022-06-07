//package com.eazybank.security;
//
//import com.eazybank.model.Role;
//import com.eazybank.model.Users;
//import com.eazybank.repos.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private UserRepo userRepo;
//    private  Role role;
//    @Autowired
//    public CustomUserDetailsService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//        Users givenUser =  userRepo.findUsersByEmailOrUsername(usernameOrEmail, usernameOrEmail)
//                .orElseThrow(() -> new UsernameNotFoundException("username or email not found"));
//
//        return new User(givenUser.getUsername(), givenUser.getPassword(), givenUser.getRole());
//    }
//
//    private Collection<? extends GrantedAuthority> rolesToAuthorities(Role role){
//
//    }
//}
