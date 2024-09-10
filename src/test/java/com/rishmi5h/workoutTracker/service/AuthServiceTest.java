package com.rishmi5h.workoutTracker.service;

import com.rishmi5h.workoutTracker.dto.AuthDTO;
import com.rishmi5h.workoutTracker.entity.User;
import com.rishmi5h.workoutTracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_Success() {
        AuthDTO request = new AuthDTO("testuser", "password");
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        String result = authService.register(request);

        assertNotNull(result);
        assertEquals("jwtToken", result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_UserAlreadyExists() {
        AuthDTO request = new AuthDTO("existinguser", "password");
        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

        assertThrows(RuntimeException.class, () -> authService.register(request));
    }


}
