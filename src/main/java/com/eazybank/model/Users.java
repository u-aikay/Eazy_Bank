package com.eazybank.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 64)
    private String verificationCode;
    private boolean enabled;
    private String Role;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @Column(nullable = false)
//    private Collection<Roles> roles = new ArrayList<>();
}