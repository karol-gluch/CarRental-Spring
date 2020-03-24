package com.car.rental.project.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendNotificaitoin(Mail email) throws MailException, InterruptedException {


        System.out.println("Sending email...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo("mail_kontaktowy@gmail.com");
        mail.setFrom(email.getEmail());
        mail.setSubject(email.getTemat());
        mail.setText(email.getTresc());


        javaMailSender.send(mail);

        System.out.println("Email Sent!");
    }

}