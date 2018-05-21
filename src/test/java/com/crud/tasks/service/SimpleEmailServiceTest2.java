package com.crud.tasks.service;

import com.crud.tasks.scheduler.EmailScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleEmailServiceTest2 {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailScheduler emailScheduler;

    @Test
    public void shouldSendEmailWithTasksList(){
        SimpleEmailService simpleEmailService = new SimpleEmailService();
        EmailScheduler emailScheduler = new EmailScheduler();
       // Mail mail = emailScheduler.sendInformationEmail();
        emailScheduler.sendInformationEmail();
        //simpleEmailService.sendTasksListEmail();
        //javaMailSender.send((MimeMessagePreparator) mail);

    }


}