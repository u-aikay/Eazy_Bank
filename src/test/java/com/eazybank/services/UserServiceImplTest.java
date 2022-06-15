package com.eazybank.services;

import com.eazybank.dtos.UserDto;
import com.eazybank.model.User;
import com.eazybank.repos.UserRepo;
import com.eazybank.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private User user;
    @Mock
    private UserDto userDto;
    @Mock
    private ModelMapper modelMapper;

    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication auth = Mockito.mock(Authentication.class);

    @BeforeEach
    public void setUp() {
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        user = User.builder()
                .userId(1L)
                .fullName("Test Case")
                .username("Test")
                .email("testcase@mail.com")
                .password("1234")
                .Role("CUSTOMER")
                .build();
        userDto = UserDto.builder()
                .fullName("Test Case")
                .username("Test")
                .email("testcase@mail.com")
                .password("1234")
                .confirmPassword("1234")
                .build();
    }

    @Test
    @DisplayName("Test to register customer")
    void shouldRegisterCustomerWithAllRightConditions() {
        given(userRepo.existsByEmail(userDto.getEmail())).willReturn(false);
        given(passwordEncoder.encode(userDto.getPassword())).willReturn("1234");
        given(userRepo.save(any())).willReturn(user);

        ResponseEntity<UserDto> register = userService.customerRegistration(userDto);
        assertThat(register.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(register.getBody()).isEqualTo(userDto);
    }

    @Test
    @DisplayName("Test for wrong password")
    void shouldThrowAnExceptionWhenPasswordAndConfirmPassDiffers(){
        userDto.setConfirmPassword("4321");
        Throwable throwable = catchThrowable(() -> userService.customerRegistration(userDto));
        assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("password do not match");
    }

    @Test
    @DisplayName("Test for already existing email")
    void shouldThrowAnExceptionWhenGivenEmailAlreadyExist(){
        given(userRepo.existsByEmail(userDto.getEmail())).willReturn(true);
        Throwable throwable = catchThrowable(() -> userService.customerRegistration(userDto));
        assertThat(throwable).isInstanceOf(RuntimeException.class).hasMessage("User already exist");
    }
}