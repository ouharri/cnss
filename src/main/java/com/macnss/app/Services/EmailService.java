package com.macnss.app.Services;

import com.macnss.Core.MailProvider;
import com.macnss.Core.env;

import com.macnss.app.Exceptions.EmailException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
    public void sendEmail(String recipient, String subject, String body) throws EmailException {
        try {
            Message message = new MimeMessage(MailProvider.getMailSession());
            message.setFrom(new InternetAddress(env.get("MAIL_SMTP_FROM")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new EmailException("Error sending email.", e);
        }
    }
}
