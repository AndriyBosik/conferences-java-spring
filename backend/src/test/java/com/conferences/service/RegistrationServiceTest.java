package com.conferences.service;

import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserRegistrationData;
import com.conferences.repository.IRoleRepository;
import com.conferences.repository.IUserRepository;
import com.conferences.service.implementation.RegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class RegistrationServiceTest {

    private IUserRepository userRepository;
    private IRoleRepository roleRepository;
    private IMapper<UserRegistrationData, User> mapper;
    private PasswordEncoder encoder;

    @Test
    public void shouldRegisterUser() {
        User user = new User();
        List<User> users = new ArrayList<>();

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.doAnswer(invocationOnMock -> {
            users.add(invocationOnMock.getArgument(0));
            return null;
        }).when(userRepository).save(any(User.class));
        Mockito.when(userRepository.findByLoginOrEmail(any(String.class), any(String.class))).thenReturn(null);

        roleRepository = Mockito.mock(IRoleRepository.class);
        Mockito.when(roleRepository.findByTitle(any(String.class))).thenReturn(new Role(2, "speaker"));

        mapper = (IMapper<UserRegistrationData, User>) Mockito.mock(IMapper.class);
        Mockito.when(mapper.map(any(UserRegistrationData.class))).thenReturn(user);

        encoder = Mockito.mock(PasswordEncoder.class);
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(encoder).encode(any(String.class));

        RegistrationService registrationService = new RegistrationService(userRepository, roleRepository, mapper, encoder);
        registrationService.signUpUser(new UserRegistrationData());

        assertEquals(1, users.size());
        assertSame(users.get(0), user);
    }

    @Test
    public void shouldNotRegisterExistingUser() {
        List<User> users = new ArrayList<>();

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLoginOrEmail(any(String.class), any(String.class))).thenReturn(new User());
        Mockito.doAnswer(invocationOnMock -> {
            users.add(invocationOnMock.getArgument(0));
            return null;
        }).when(userRepository).save(any(User.class));

        RegistrationService service = new RegistrationService(userRepository, roleRepository, mapper, encoder);

        UserRegistrationData data = new UserRegistrationData();
        data.setLogin("login");
        data.setEmail("email@example.com");
        assertNull(service.signUpUser(data));
        assertEquals(0, users.size());
    }
}
