package com.example.demo.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    @Autowired
    private JavaMailSender mailSender;

    //Registration Email
    public void sendEmail(String toEmail,
                          String subject,
                          String body){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yubazaarassistant@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail Sent");

    }

    //Verification OTP Email sender
    public void sendOtpEmail(String toEmail, String otp) {
        String subject = "Your OTP for YU Bazaar Registration";
        String body = "Dear User,\n\n"
                + "Thank you for registering at YU Bazaar. Please use the following OTP to verify your account:\n\n"
                + otp + "\n\n"
                + "This OTP is valid for 10 minutes.\n\n"
                + "Regards,\n"
                + "YU Bazaar Team";

        sendEmail(toEmail, subject, body);
    }
}
