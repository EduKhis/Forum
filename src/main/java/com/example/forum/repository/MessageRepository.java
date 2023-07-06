package com.example.forum.repository;

import com.example.forum.model.Message;
import com.example.forum.model.Teg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByTeg(Teg teg);

    List<Message> findByDateBefore(Date date);
    List<Message> findByDateAfter(Date date);
    List<Message> findByDateBetween(Date first, Date last);

    List<Message> findByTegAndDateBetween(Teg teg, Date first, Date last);
    List<Message> findByTegAndDateBefore(Teg teg, Date last);
    List<Message> findByTegAndDateAfter(Teg teg, Date first);


}
