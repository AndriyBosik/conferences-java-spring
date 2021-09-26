package com.conferences.service;

import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.model.Account;
import com.conferences.repository.IUserRepository;
import com.conferences.service.implementation.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class AccountServiceTest {

    private AccountService service;
    private IUserRepository userRepository;

    @Test
    public void shouldReturnAccountForExistingUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setRole(new Role(1, "moderator"));

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLogin("login")).thenReturn(user);
        service = new AccountService(userRepository);

        UserDetails account = service.loadUserByUsername("login");

        assertEquals("login", account.getUsername());
        assertEquals("password", account.getPassword());
        assertEquals(1, account.getAuthorities().size());
        assertEquals("moderator", new ArrayList<>(account.getAuthorities()).get(0).getAuthority().toLowerCase());
    }

    @Test
    public void shouldThrowExceptionWhenUserNotFound() {
        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLogin(any(String.class))).thenReturn(null);

        service = new AccountService(userRepository);

        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername("login");
        });
    }
}
