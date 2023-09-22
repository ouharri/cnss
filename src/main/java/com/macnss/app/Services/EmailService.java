package com.macnss.app.Services;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;

public class EmailService {

    private final Properties emailProperties;
    private final Session mailSession;

    public EmailService() throws IOException {
        // Charger les propriétés de configuration depuis un fichier
        emailProperties = loadEmailProperties();

        // Configurer la session SMTP
        mailSession = Session.getInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        emailProperties.getProperty("mail.smtp.username"),
                        emailProperties.getProperty("mail.smtp.password")
                );
            }
        });
    }

    public void sendEmail(String recipient, String subject, String body) {
        try {
            // Créer un message MIME
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(emailProperties.getProperty("mail.smtp.from")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Envoyer le message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Gérer les erreurs d'envoi d'e-mail ici
        }
    }

    private Properties loadEmailProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream("/com/macnss/config/mail.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IOException("Le fichier de propriétés n'a pas été trouvé.");
            }
        }
        return properties;
    }
}

