package edu.carlosliam.hotelmanagementfx.utils;

import edu.carlosliam.hotelmanagementfx.model.data.Employee;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class MailUtils {
    private final static String SENDER_EMAIL = "liam.alejoasensi@gmail.com";
    private final static String EMAIL_SERVER = "smtp.gmail.com";
    private final static String EMAIL_SERVER_PORT = "587";
    private final static String TIMEOUT = "5000";
    private final static String PASSWORD_PATH = "src/main/resources/pass.txt";
    private static String senderPassword = "";
    private final Properties props;

    public MailUtils() {
        try {
            senderPassword = Files.readAllLines(Paths.get(PASSWORD_PATH)).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        props = new Properties();
        props.put("mail.smtp.user", SENDER_EMAIL);
        props.put("mail.smtp.host", EMAIL_SERVER);
        props.put("mail.smtp.port", EMAIL_SERVER_PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", EMAIL_SERVER_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.connectiontimeout", TIMEOUT);
    }

    public void sendBasicEmail(Employee receiver, String subject, String text) {
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, senderPassword);
                }
            };

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(getHtml(receiver, text), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            msg.setContent(multipart);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver.getEmail()));

            Transport.send(msg);
            System.out.println("Email sent successfully");
        } catch (Exception ex) {
            System.err.println("Error occurred while sending: " + ex.getMessage());
        }
    }

    public void sendEmailWithAttachment(Employee receiver, String subject, String text, String attachmentPath) {
        try {
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, senderPassword);
                }
            };

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(attachmentPath));

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(getHtml(receiver, text), "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver.getEmail()));

            Transport.send(msg);
            System.out.println("Email sent successfully");
        } catch (Exception ex) {
            System.err.println("Error occurred while sending: " + ex.getMessage());
        }
    }

    private String getHtml(Employee employee, String message) {
        return """
                <header style="width: 100%; background-color: #fc4503; color: white; padding: 15px; margin-bottom: 50px;">
                <h1 style="text-align: center">Nesteja Hotel©</h1>
                <p>A bunch of legal nonsense</p>
                </header>
                <main>
                <h2>%name %surname, there is a new notification for you</h2>
                <p>%message</p>
                </main>
                <footer style="width: 100%; background-color: #b8b8b8; font-size: 10px; padding: 15px; margin-top: 50px;">
                <p>Please, do not reply to this email</p>
                </footer>
                """.replace("%name", employee.getName())
                .replace("%surname", employee.getSurnames())
                .replace("%message", message);
    }
}
