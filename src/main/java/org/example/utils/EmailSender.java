package org.example.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import lombok.Setter;

import java.util.Properties;

@Setter
public class EmailSender {

    private String receiverMail;
    private String message;
    private Session session;
    private String subject;

    public EmailSender() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.starttls.enable", "true");

         session = Session.getInstance(prop,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ajibolaphilip10@gmail.com", "wsql nogf dtxq jagx");
            }
        });


    }
    public void send() {
        try{
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ajibolaphilip10@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverMail));
            msg.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            msg.setContent(multipart);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
