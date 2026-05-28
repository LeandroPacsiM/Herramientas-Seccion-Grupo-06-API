package com.llamatours.auth;

import com.llamatours.auth.dto.AuthResponse;
import com.llamatours.auth.dto.LoginRequest;
import com.llamatours.auth.dto.RegisterRequest;
import com.llamatours.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void register_shouldCreateUserAndReturnToken() {
        var request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Test User", response.getName());
    }

    @Test
    void register_shouldThrowWhenEmailAlreadyExists() {
        var request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("duplicate@example.com");
        request.setPassword("password123");

        authService.register(request);

        var duplicate = new RegisterRequest();
        duplicate.setName("Another");
        duplicate.setEmail("duplicate@example.com");
        duplicate.setPassword("password456");

        assertThrows(RuntimeException.class, () -> authService.register(duplicate));
    }

    @Test
    void login_shouldReturnTokenWithValidCredentials() {
        var registerReq = new RegisterRequest();
        registerReq.setName("Login User");
        registerReq.setEmail("login@example.com");
        registerReq.setPassword("password123");
        authService.register(registerReq);

        var loginReq = new LoginRequest();
        loginReq.setEmail("login@example.com");
        loginReq.setPassword("password123");

        AuthResponse response = authService.login(loginReq);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("login@example.com", response.getEmail());
    }
}
