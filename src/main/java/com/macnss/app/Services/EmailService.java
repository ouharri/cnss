package com.macnss.app.Services;

import com.macnss.Core.MailProvider;
import com.macnss.Core.env;

import com.macnss.app.Exceptions.EmailException;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * The `EmailService` class provides a way to send email messages using JavaMail.
 * It allows you to create and send plain text or HTML email messages.
 */
public class EmailService implements Runnable {

    private final Message message;

    /**
     * Constructs an `EmailService` instance with the specified recipient and subject.
     *
     * @param recipient The email address of the recipient.
     * @param subject   The subject of the email.
     */
    public EmailService(String recipient, String subject) {
        message = new MimeMessage(MailProvider.getMailSession());
        try {
            message.setFrom(new InternetAddress(env.get("MAIL_SMTP_FROM")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException("Error creating email message.", e);
        }
    }

    /**
     * Sets the plain text content of the email.
     *
     * @param body The plain text content of the email.
     * @throws MessagingException If there is an error while setting the email content.
     */
    public void setText(String body) throws MessagingException {
        try {
            message.setText(body);
        } catch (Exception e) {
            throw new MessagingException("Error setting email content.", e);
        }
    }

    /**
     * Sets the HTML content of the email.
     *
     * @param body The HTML content of the email.
     * @throws MessagingException If there is an error while setting the email content.
     */
    public void setContent(String body) throws MessagingException {
        try {
            message.setContent(body, "text/html");
        } catch (Exception e) {
            throw new MessagingException("Error setting email content.", e);
        }
    }

    /**
     * Sends the email message.
     *
     * @throws EmailException If there is an error while sending the email.
     */
    public void send() throws EmailException {
        synchronized (EmailService.class) {
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