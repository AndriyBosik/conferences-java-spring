package com.conferences.service;

import com.conferences.entity.User;
import com.conferences.entity.projection.IUserPublicData;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserPublicData;
import com.conferences.model.UserUpdateData;
import com.conferences.repository.IUserRepository;
import com.conferences.service.abstraction.ISecurityService;
import com.conferences.service.implementation.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class UserServiceTest {

    private IUserRepository userRepository;
    private IMapper<User, UserPublicData> mapper;
    private ISecurityService securityService;
    private PasswordEncoder passwordEncoder;

    @Test
    public void shouldReturnUserByLogin() {
        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLogin(any(String.class))).thenReturn(new User());

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);

        assertNotNull(service.getUserByLogin("login"));
    }

    @Test
    public void shouldReturnNullForNotExistingUser() {
        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLogin(any(String.class))).thenReturn(null);

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);

        assertNull(service.getUserByLogin("null"));
    }

    @Test
    public void shouldReturnUsersByRole() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setLogin("login" + i);
            users.add(user);
        }

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findAllByRole(any(String.class))).thenReturn(new LinkedHashSet<>(users));

        mapper = (IMapper<User, UserPublicData>) Mockito.mock(IMapper.class);
        Mockito.doAnswer(invocationOnMock -> {
            UserPublicData data = new UserPublicData();
            data.setLogin(((User) invocationOnMock.getArgument(0)).getLogin());
            return data;
        }).when(mapper).map(any(User.class));

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);

        List<UserPublicData> dataList = service.getUsersByRole("speaker");

        assertEquals(users.size(), dataList.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getLogin(), dataList.get(i).getLogin());
        }
    }

    @Test
    public void shouldReturnAvailableUsersByTopic() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setLogin("login" + i);
            users.add(user);
        }

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findAvailableSpeakersByTopic(any(Integer.class))).thenReturn(new LinkedHashSet<>(users));

        mapper = (IMapper<User, UserPublicData>) Mockito.mock(IMapper.class);
        Mockito.doAnswer(invocationOnMock -> {
            UserPublicData data = new UserPublicData();
            data.setLogin(((User) invocationOnMock.getArgument(0)).getLogin());
            return data;
        }).when(mapper).map(any(User.class));

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);

        List<UserPublicData> dataList = service.getAvailableSpeakersByTopic(5);

        assertEquals(users.size(), dataList.size());
        for (int i = 0; i < users.size(); i++) {
            assertEquals(users.get(i).getLogin(), dataList.get(i).getLogin());
        }
    }

    @Test
    public void shouldReturnProposedSpeakersForTopic() {
        List<IUserPublicData> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            dataList.add(new IUserPublicData() {
                @Override
                public Integer getId() {
                    return index;
                }

                @Override
                public String getName() {
                    return "name" + index;
                }

                @Override
                public String getSurname() {
                    return "surname" + index;
                }

                @Override
                public String getImagePath() {
                    return "image_path" + index;
                }
            });
        }

        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findProposedSpeakersForTopic(any(Integer.class))).thenReturn(new LinkedHashSet<>(dataList));

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);
        List<IUserPublicData> data = service.getProposedSpeakersForTopic(6);

        assertEquals(dataList.size(), data.size());
        for (int i = 0; i < dataList.size(); i++) {
            assertEquals(dataList.get(i).getId(), data.get(i).getId());
            assertEquals(dataList.get(i).getName(), data.get(i).getName());
            assertEquals(dataList.get(i).getSurname(), data.get(i).getSurname());
            assertEquals(dataList.get(i).getImagePath(), data.get(i).getImagePath());
        }
    }

    @Test
    public void shouldUpdateUserDataWithoutPassword() {
        final User[] user = configureMocksToUpdateUser("returnToken");

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);

        UserUpdateData data = new UserUpdateData();
        data.setLogin("newLogin");
        data.setName("newName");
        data.setSurname("newSurname");
        data.setEmail("new.email@gmail.com");
        data.setOldPassword("");
        data.setNewPassword("");
        data.setConfirmPassword("");
        String result = service.updateUser(data);

        assertEquals("returnToken", result);
        assertEquals("newLogin", user[0].getLogin());
        assertEquals("newName", user[0].getName());
        assertEquals("newSurname", user[0].getSurname());
        assertEquals("new.email@gmail.com", user[0].getEmail());
    }

    @Test
    public void shouldUpdateWithPassword() {
        final User[] user = configureMocksToUpdateUser("token");
        user[0].setPassword("old-password");

        UserUpdateData data = new UserUpdateData();
        data.setLogin("newLogin");
        data.setName("newName");
        data.setSurname("newSurname");
        data.setEmail("new.email@gmail.com");
        data.setOldPassword("old-password");
        data.setNewPassword("new-password");
        data.setConfirmPassword("new-password");

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);
        String result = service.updateUser(data);

        assertEquals("token", result);
        assertEquals("newLogin", user[0].getLogin());
        assertEquals("newName", user[0].getName());
        assertEquals("newSurname", user[0].getSurname());
        assertEquals("new.email@gmail.com", user[0].getEmail());
        assertEquals("new-password", user[0].getPassword());
    }

    @Test
    public void shouldNotUpdateUserWithIncorrectOldPassword() {
        final User[] user = configureMocksToUpdateUser("token");
        user[0].setPassword("old-password");

        UserUpdateData data = new UserUpdateData();
        data.setLogin("newLogin");
        data.setName("newName");
        data.setSurname("newSurname");
        data.setEmail("new.email@gmail.com");
        data.setOldPassword("old-password-");
        data.setNewPassword("new-password");
        data.setConfirmPassword("new-password");

        UserService service = new UserService(userRepository, mapper, securityService, passwordEncoder);
        String result = service.updateUser(data);

        assertEquals("", result);
        assertEquals("old-password", user[0].getPassword());
    }

    private User[] configureMocksToUpdateUser(String token) {
        final User[] user = {new User()};
        configurePasswordEncoder();
        configureSecurityService(token);
        userRepository = Mockito.mock(IUserRepository.class);
        Mockito.when(userRepository.findByLogin(any(String.class))).thenReturn(user[0]);
        Mockito.doAnswer(invocationOnMock -> {
            User parameterUser = invocationOnMock.getArgument(0);
            user[0].setLogin(parameterUser.getLogin());
            user[0].setName(parameterUser.getName());
            user[0].setSurname(parameterUser.getSurname());
            user[0].setEmail(parameterUser.getEmail());
            user[0].setPassword(parameterUser.getPassword());
            user[0].setImagePath(parameterUser.getImagePath());
            return null;
        }).when(userRepository).save(any(User.class));
        return user;
    }

    private void configurePasswordEncoder() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.doAnswer(invocationOnMock -> invocationOnMock.getArgument(0)).when(passwordEncoder).encode(any(CharSequence.class));
        Mockito
            .doAnswer(invocationOnMock -> {
                String first = invocationOnMock.getArgument(0);
                String second = invocationOnMock.getArgument(1);
                return first != null && first.equals(second);
            }).when(passwordEncoder).matches(any(String.class), any(String.class));
    }

    private void configureSecurityService(String token) {
        securityService = Mockito.mock(ISecurityService.class);
        Mockito.when(securityService.getUserLogin()).thenReturn("login");
        Mockito.when(securityService.reAuthenticateUser(any(User.class))).thenReturn(token);
    }
}
