package com.loja.email_service.service;

import com.loja.email_service.enums.statusEmail;
import com.loja.email_service.model.EmailModel;
import com.loja.email_service.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailModel emailModel;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public EmailModel sendEmail(EmailModel emailModel){
        try{
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setText(emailModel.getText());
            message.setSubject(emailModel.getSubject());

            emailSender.send(message);

            emailModel.setStatusEmail(statusEmail.SENT);

        }catch (MailException ex){
            emailModel.setStatusEmail(statusEmail.ERROR);
        }finally {
            return emailRepository.save(emailModel);
        }
    }
}
