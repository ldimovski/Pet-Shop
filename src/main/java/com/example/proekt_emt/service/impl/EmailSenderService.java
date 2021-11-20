package com.example.proekt_emt.service.impl;

import com.example.proekt_emt.model.Mail;
import com.example.proekt_emt.model.User;
import com.example.proekt_emt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    public void sendEmail(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dimovski.laze2@gmail.com");
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        message.setTo(this.userService.findById(mail.getUsername()).getEmail());
        mailSender.send(message);
    }

    public void sendEmailAll(Mail mail){
        List<User> users = this.userService.findAll();

        for (User u :
                users) {
            if(u.getEmail() == null)
                continue;
            if(u.getEmail().equals(""))
                continue;
            else {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("dimovski.laze2@gmail.com");
                message.setSubject(mail.getSubject());
                message.setText(mail.getText());
                message.setTo(u.getEmail());
                mailSender.send(message);
            }
        }



    }
}
