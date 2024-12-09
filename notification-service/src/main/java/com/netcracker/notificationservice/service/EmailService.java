package com.netcracker.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String receiverEmail,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("jayaramkrishna1308@gmail.com");
        message.setTo(receiverEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
