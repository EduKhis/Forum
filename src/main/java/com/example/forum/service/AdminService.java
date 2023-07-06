package com.example.forum.service;

import com.example.forum.model.User;
import com.example.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll () {
        return userRepository.findAll();
    }
    public User findById (Long a) {
        return userRepository.findById(a).get();
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public User findById (String id) {
        return userRepository.findById(Long.valueOf(id)).get();
    }
}
