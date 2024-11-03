package dev.thilanka.shorturl.security.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationToken){
        String subject = "Please verify your Email for SLT-Mobitel URL Shortner";
        String verificationUrl = "http://localhost:8080/auth/verify?token=" + verificationToken;
        String message = "Click the following link to verify your account. " + verificationUrl;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
