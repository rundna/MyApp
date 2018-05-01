package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail(){
        Mail mail = new Mail("test@gmail.com","Dzawa","Dzawa is inda haus?!",null);

        SimpleMailMessage mailMassage = new SimpleMailMessage();
        mailMassage.setTo(mail.getMailTo());
        mailMassage.setSubject(mail.getSubject());
        mailMassage.setText(mail.getMessage());

        simpleEmailService.send(mail);

        verify(javaMailSender,times(1)).send(mailMassage);
    }

}