package com.service;

import org.springframework.stereotype.Service;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailSender {

  public void sendEmail(String to, String subject, String content) {
    Email email = EmailBuilder.startingBlank()
            .from(name, emailAddress)
            .to(to)
            .withSubject(subject)
            .withPlainText(content)
            .buildEmail();
    MailerBuilder.withSMTPServer(host, port, emailAddress, password)
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .buildMailer()
            .sendMail(email);
  }

  private final String name = "TEDI";

  @Value("${spring.mail.username}")
  private String emailAddress;

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.password}")
  private String password;
}

