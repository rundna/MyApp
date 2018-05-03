package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.scheduler.EmailScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EmailScheduler emailScheduler;

    @Test
    public void shouldSendEmail(){
        AdminConfig adminConfig = new AdminConfig();
        Mail mail = new Mail(adminConfig.getAdminMail(),"Test","Some message!",null);

        SimpleMailMessage mailMassage = new SimpleMailMessage();
        mailMassage.setTo(mail.getMailTo());
        mailMassage.setSubject(mail.getSubject());
        mailMassage.setText(mail.getMessage());

        simpleEmailService.send(mail);
        //emailScheduler.sendInformationEmail();

        verify(javaMailSender,times(1)).send(mailMassage);
    }

}