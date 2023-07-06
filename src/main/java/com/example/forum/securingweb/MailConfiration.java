package com.example.forum.securingweb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiration {
    @Value("${spring.mail.host}")
    private String springMailHost;
    @Value("${spring.mail.username}")
    private String springMailUserName;
    @Value("${spring.mail.password}")
    private String springMailPassword;
    @Value("${spring.mail.port}")
    private Integer springMailPort;
    @Value("${mail.debug}")
    private String mailDebug;
    @Value("${spring.mail.protocol}")
    private String springMailProtocol;
   // @Bean
    //public JavaMailSender getJavaMailSender() {
    //    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    //    mailSender.setHost(springMailHost);
    //    mailSender.setPort(springMailPort);
    //    mailSender.setUsername(springMailUserName);
    //    mailSender.setPassword(springMailPassword);
    //    //Properties props = mailSender.getJavaMailProperties();
    //    //props.setProperty("mail.transport.protocol", springMailProtocol);
    //    //props.setProperty("mail.debug", mailDebug);
    //    return mailSender;
    //}
}
