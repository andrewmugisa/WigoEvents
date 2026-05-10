package org.wigo.myday.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfiguration {

    // Gmail SMTP server settings
    @Value("${spring.mail.host}")
    private String mailSenderServer;

    @Value("${spring.mail.port:587}") // default to 587 if not set
    private int mailSenderPort;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:true}") // default true if not set
    private boolean mailUseTls;

    // Support email credentials
    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderServer);
        mailSender.setPort(mailSenderPort);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", String.valueOf(mailUseTls));
        props.put("mail.debug", "true"); // enable debug for development

        return mailSender;
    }
}