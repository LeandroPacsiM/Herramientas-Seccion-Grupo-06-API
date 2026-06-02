package com.llamatours.service;

import com.llamatours.enums.Role;
import com.llamatours.user.entity.User;
import com.llamatours.user.repository.UserRepository;
import com.llamatours.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByEmail_shouldWork() {
        var user = User.builder()
                .name("Service User")
                .email("service@example.com")
                .password("encoded")
                .role(Role.USER)
                .build();

        userService.save(user);

        var found = userService.findByEmail("service@example.com");
        assertNotNull(found);
        assertEquals("Service User", found.getName());
    }

    @Test
    void existsByEmail_shouldReturnTrueForExistingEmail() {
        var user = User.builder()
                .name("Exists User")
                .email("exists@example.com")
                .password("encoded")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        assertTrue(userService.existsByEmail("exists@example.com"));
        assertFalse(userService.existsByEmail("nonexistent@example.com"));
    }
}
