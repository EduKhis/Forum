package com.example.forum.service;

import com.example.forum.model.Role;
import com.example.forum.model.User;
import com.example.forum.repository.UserRepository;
import com.example.forum.util.UtilMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
    public void sendMessage (User user) {
        mailSenderService.sendMail(user.getEmail(), "Activation link", UtilMessage.textMessage(user.getUsername(), user.getActivationCode()));
    }
    public boolean existsByEmail(String email){
        if (!userRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }

    public boolean saveUser(String username, String password, String email){
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        else {
            User user = new User(username,email);
            user.setPassword(passwordEncoder.encode(password));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setActive(true);
            user.setList(Collections.singletonList(Role.USER));
            User u = userRepository.save(user);
            System.out.println(u);
            if (!user.getEmail().isEmpty() && user.getEmail()!=null) {
                sendMessage(user);
            }
            return true;
        }
    }
    public boolean activeUser (String code) {
        User user = userRepository.findByActivationCode(code);
        if (user==null) {
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public void updateUser(String email, String pswd, User user) {
        String mailOld = user.getEmail();
        boolean tr = mailOld!=null && !email.equals(mailOld) || email!=null && !mailOld.equals(email);
        if (tr){
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if (!StringUtils.isEmpty(pswd)) {
            user.setPassword(pswd);
        }
        userRepository.save(user);
        if (tr) {
            sendMessage(user);
        }

    }
}
