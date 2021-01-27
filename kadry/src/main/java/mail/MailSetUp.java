/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import entity.SMTPSettings;
import java.io.Serializable;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
/**
 *
 * @author Osito
 */

public class MailSetUp implements Serializable{

    
       
      public static Session otworzsesje(SMTPSettings settings, SMTPSettings ogolne) {
        Session session = null;
        if (settings == null) {
            final String username = ogolne.getUsername();
            final String password = ogolne.getPassword();
            Properties props = new Properties();
            props.put("mail.smtp.host", ogolne.getSmtphost());
            props.put("mail.smtp.port", ogolne.getSmtpport());
            if (ogolne.isSmtpauth()) {
                props.put("mail.smtp.auth", "true");
            }
            if (ogolne.isStarttlsenable()) {
                props.put("mail.smtp.starttls.enable", "true");
            } else if (ogolne.isSslenable()) {
                props.put("mail.smtp.ssl.enable", "true");
            }
            //"mail.smtp.ssl.enable"
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        } else {
            final String username = settings.getUsername();
            final String password = settings.getPassword();
            Properties props = new Properties();
            props.put("mail.smtp.host", settings.getSmtphost());
            props.put("mail.smtp.port", settings.getSmtpport());
            if (settings.isSmtpauth()) {
                props.put("mail.smtp.auth", "true");
            }
            if (settings.isStarttlsenable()) {
                props.put("mail.smtp.starttls.enable", "true");
            }
            session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
        }
        return session;
    }
     
//    public static Session otworzsesje() {
//        final String username = "info@e-taxman.pl";
//        final String password = "Pufikun7005*";
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "mailng.az.pl");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable","true");
//        return Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//    }
    
}
