package com.macnss.Core;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.io.IOException;
import java.util.Properties;

/**
 * The MailProvider class provides a centralized instance of the JavaMail Session
 * for sending email using SMTP with authentication.
 */
public class MailProvider {

    private static volatile Session mailSession = null;

    static {
        if (mailSession == null) {
            synchronized (MailProvider.class) {
                if (mailSession == null) {
                    try {
                        Properties emailProperties = loadEmailProperties();
                        mailSession = Session.getInstance(emailProperties, new Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(
                                        env.get("MAIL_USERNAME"),
                                        env.get("MAIL_PASSWORD")
                                );
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException("Error loading email properties.", e);
                    }
                }
            }
        }
    }

    /**
     * Retrieves the JavaMail Session for sending emails.
     *
     * @return The JavaMail Session instance.
     */
    public static Session getMailSession() {
        return mailSession;
    }

    private static Properties loadEmailProperties() throws IOException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", env.get("MAIL_SMTP_AUTH"));
        properties.put("mail.smtp.host", env.get("MAIL_HOST"));
        properties.put("mail.smtp.port", env.get("MAIL_PORT"));
        properties.put("mail.smtp.starttls.enable", env.get("MAIL_SMTP_STARTTLS_ENABLE"));

        return properties;
    }
}