package dev.thilanka.shorturl.security.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${shorturl.service.baseurl}")
    private String BASE_URL;

    @Autowired
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationToken) {
        String subject = "Verify your Email for URL Shortener";
        String verificationUrl = BASE_URL + "auth/verify?token=" + verificationToken;
        String message = "Click the following link to verify your account. " + verificationUrl + " .If you have not created an account, please ignore this email";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
