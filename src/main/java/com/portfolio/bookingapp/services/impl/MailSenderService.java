package com.portfolio.bookingapp.services.impl;

import com.portfolio.bookingapp.exeptions.IncorrectDataException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public void sendMail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            helper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IncorrectDataException(e.getMessage());
        }
    }

}
