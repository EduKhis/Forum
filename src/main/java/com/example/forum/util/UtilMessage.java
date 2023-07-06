package com.example.forum.util;

public class UtilMessage {

    public static String textMessage(String name, String code) {
        return "Hello, "+ name + "! You are registered on our website. To confirm your registration, please click on the link " + "http://localhost:8080/active/" + code;
    }
}
