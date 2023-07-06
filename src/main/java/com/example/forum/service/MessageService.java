package com.example.forum.service;

import com.example.forum.model.Message;
import com.example.forum.model.User;
import com.example.forum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public List<Message> findAll () {
        return messageRepository.findAll();
    }
    public void save(Message message) {
        messageRepository.save(message);
    }
    public Message findById (Long id) {
        return messageRepository.findById(id).get();
    }

}
