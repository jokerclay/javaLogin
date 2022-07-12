package com.example.javalogoin.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor

public class EmailService implements EmailSender {

    // 发送邮件时 log
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject("确认你的邮件");
            helper.setFrom("clay@bishe.clayliu.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e ) {
            LOGGER.error("邮件发送失败", e);
            throw new IllegalStateException("邮件发送失败");
        }
    }
}
