package com.macnss.app.Services;

import com.macnss.Core.MailProvider;
import com.macnss.Core.env;

import com.macnss.app.Exceptions.EmailException;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService  implements Runnable {
    private String recipient;
    private String subject;
    private String body;

    Message message = null;

    public EmailService(String recipient, String subject) throws MessagingException {
        message = new MimeMessage(MailProvider.getMailSession());
        try {
            message.setFrom(new InternetAddress(env.get("MAIL_SMTP_FROM")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException("Error creating email message.", e);
        }
    }

    public void setText(String body) throws MessagingException {
        message.setText(body);
    }

    public void setContent(String body) throws MessagingException {
        message.setContent(body, "text/html");
    }

    public void send() throws EmailException {
        synchronized (MailProvider.class){
            try {
                Transport.send(message);
            } catch (MessagingException e) {
                throw new EmailException("Error sending email.", e);
            }
        }
    }

    @Override
    public void run() {
        try {
            send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
