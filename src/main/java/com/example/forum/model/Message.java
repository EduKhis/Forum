package com.example.forum.model;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name="message_seq", initialValue = 3)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Teg teg;
    @NotBlank(message = "description should be not null")
    @Length(max = 2048,message = "description should be less")
    private String description;
    private Date date;

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Message(String filename) {
        this.filename = filename;
    }

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message(Long id, Teg teg, String description, Date date) {
        this.id = id;
        this.teg = teg;
        this.description = description;
        this.date = date;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teg getTeg() {
        return teg;
    }

    public void setTeg(Teg teg) {
        this.teg = teg;
    }

    public String getDescription() {
        return description;
    }
    public String getUsername(){
        return user.getUsername();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }
    public String getDateForOutput(){
        String newstring = new SimpleDateFormat("dd.MM.yyyy").format(date);
        return newstring; // 2011-01-18
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
