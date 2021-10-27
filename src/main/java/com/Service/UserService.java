package com.Service;
import com.Models.Role;
import com.Models.User;
import com.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findAllByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepository.findAllByUsername(username);
    }

    public boolean add_user(User user)
    {

        User userFromDb = userRepository.findAllByUsername(user.getUsername());
        if(userFromDb!=null)
        {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(!StringUtils.isEmpty(user.getEmail()))
        {
            String message=String.format(
                    "Hello, %s! \n" +
                            "Welcome!Follow this link to continue: http://localhost:8080/activate/%s",
                    user.getUsername(),user.getActivationCode()
            );
            mailSender.send(user.getEmail(),"Activation code",message);
        }
        userRepository.save(user);
        return true;
    }

    public boolean activateUser(String code)
    {
        User user=userRepository.findByActivationCode(code);
        if(user==null)
        {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public List<User> findAll()
    {

        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form)
    {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet())
        {
            if(roles.contains(key))
            {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateUser(User user, String email, String password)
    {
        if(!password.equals(user.getPassword()))
        {
            user.setPassword(passwordEncoder.encode(password));
        }
        if(!email.equals(user.getEmail()))
        {
            user.setEmail(email);
        }
        userRepository.save(user);
    }

    public void subscribe(User user, User currentUser)
    {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }
    public void unsubscribe(User user, User currentUser)
    {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}
