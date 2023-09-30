package com.EmailClient;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.Serializable;



public class SendEmail implements Serializable {
    private static final long serialVersionUID = 4L;
    public String recipient;
    String subject;
    String date;

    public SendEmail(String recipient_in, String subject_in) {
        recipient = recipient_in;
        subject = subject_in;

        LocalDate date_object = LocalDate.now();
        DateTimeFormatter date_formatted = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        date = date_object.format(date_formatted);

    }

    public void sendMessage(String mailBody) {

        final String username = "mail4javamail@gmail.com";
        final String password = "";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(this.recipient)
            );
            message.setSubject(this.subject);
            message.setText(mailBody);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
