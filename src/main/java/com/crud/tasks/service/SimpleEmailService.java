package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        LOGGER.info("Starting mail process...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            LOGGER.info("Email sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to send: ", e.getMessage(), e);
        }
    }

    public void sendTasksListEmail(final Mail mail) {
        LOGGER.info("Starting mail process...");
        try {
            javaMailSender.send(createMimeMessageWithTasksList(mail));
            LOGGER.info("Email sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to send: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
//        if(mail.getToCC()!=null){
//            mailMessage.setCc(mail.getToCC());
//        }
        //ofNullable(mailMessage).ifPresent(noCc->noCc.setCc(mail.getToCC()));
        //ofNullable(mail.getToCC()).ifPresent(mailMessage::setCc);
        return mailMessage;
    }


    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createMimeMessageWithTasksList(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.sendTasksListEmail(mail.getMessage()), true);
        };
    }

    }