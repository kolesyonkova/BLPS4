package itmo.blps.mommy.service;

import itmo.blps.mommy.config.EmailConfig;
import itmo.blps.mommy.dto.EmailDto;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    @JmsListener(destination = "email-queue")
    public void listen(EmailDto emailDto) {
        send(emailDto.getEmailTo(), emailDto.getSubject(), emailDto.getMessage());
    }

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailConfig.getUsername());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
