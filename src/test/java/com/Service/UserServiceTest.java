package com.Service;
import com.Models.Role;
import com.Models.User;
import com.Repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest
{
    @Autowired UserService userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MailSender mailSender;
    @MockBean
    private User user;

    @Test
    void add_user()
    {
        User user=new User();
        boolean isUserCreated=userService.add_user(user);
        user.setEmail("some@mail.ru");
        Assertions.assertTrue(isUserCreated); //проверка на истиность
        Assertions.assertNotNull(user.getActivationCode());//проверка на наличие кода
        Assertions.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepository,Mockito.times(1)).save(user);
        //проверка на то,что userRepository сходил по save 1 раз
        Mockito.verify(mailSender,Mockito.times(1))
                .send(
                         ArgumentMatchers.eq(user.getEmail())
                        ,ArgumentMatchers.anyString()
                        ,ArgumentMatchers.anyString()
                     );
    }
    @Test
    public void addUserFailed()
    {
        User user=new User();
        user.setUsername("Arthas");
        Mockito.doReturn(new User()).when(userRepository).findAllByUsername("Arthas");
        // Находит Arthas.[New User(),т.к в методе по userFromDb условие,а все остальное по user ]
        boolean isUserCreated=userService.add_user(user);
        Assertions.assertFalse(isUserCreated);

        Mockito.verify(userRepository,Mockito.times(0)).save(ArgumentMatchers.anyObject());
        Mockito.verify(mailSender,Mockito.times(0))
                .send(
                        ArgumentMatchers.anyString()
                        ,ArgumentMatchers.anyString()
                        ,ArgumentMatchers.anyString()
                );
    }

    @Test
    void activateUserTrue()
    {
        User user=new User();
        Mockito.doReturn(user).when(userRepository).findByActivationCode("activate");
        //user,т.к условия по юзеру и используется только юзер
        boolean isActivateUser=userService.activateUser("activate");
        Assertions.assertTrue(isActivateUser);
        Assertions.assertNull(user.getActivationCode());
        Mockito.verify(userRepository,Mockito.times(1)).save(user);

    }
    @Test
    public void activateUserFalse()
    {
        boolean isActivateCode=userService.activateUser("activate");
        Assertions.assertFalse(isActivateCode);
        Mockito.verify(userRepository,Mockito.times(0)).save(ArgumentMatchers.anyObject());
    }
}