package com.eazybank.dtos;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "Field cannot be empty")
    private String fullName;
    @NotNull(message = "Field cannot be empty")
    private String username;
    @Email(message = "Email is not valid", regexp = "[A-Za-z0-9][A-Za-z0-9!.#$%&'*+=?^_`{|}~-]{1,50}@[A_Za-z]+[.][A-Z.a-z]{2,6}")
    private String email;
    @NotNull(message = "Field cannot be empty")
    private String password;
    @NotNull(message = "Field cannot be empty")
    private String confirmPassword;
}
