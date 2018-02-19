/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import beansMail.SMTPBean;
import entity.Klienci;
import entity.SMTPSettings;
import error.E;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class MailSetUp implements Serializable{

    
    public static MimeMessage logintoMail(WpisView wpisView, SMTPSettings settings, SMTPSettings ogolne) {
       MimeMessage message = new MimeMessage(otworzsesje(settings, ogolne));
        try {
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
        } catch (MessagingException ex) {
            E.e(ex);
        } catch (UnsupportedEncodingException ex) {
            E.e(ex);
        }
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(wpisView.getPodatnikObiekt().getEmail()));
        } catch (MessagingException ex) {
            E.e(ex);
        }
        if (!wpisView.getWprowadzil().getUprawnienia().equals("Guest")) {
            try {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(wpisView.getWprowadzil().getEmail()));
            } catch (Exception e) {
                E.e(e);
            }
        }
        return message;
    }
    
    public static MimeMessage logintoMailFakt(Klienci klient, WpisView wpisView, SMTPSettings settings, SMTPSettings ogolne)  {
        MimeMessage message = new MimeMessage(otworzsesje(settings, ogolne));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(klient.getEmail()));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!wpisView.getWprowadzil().getUprawnienia().equals("Guest")){
        try {
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wpisView.getWprowadzil().getEmail()));
        } catch (Exception e){
            E.e(e);
            Msg.msg("e", "Nie masz ma wprowadzonego adresu mail. Wysyłka nieudana");
        }
        }
        return message;
    }
    
     public static MimeMessage logintoMailAdmin(String adreskontrahenta, SMTPSettings settings, SMTPSettings ogolne) {
        MimeMessage message = new MimeMessage(otworzsesje(settings, ogolne));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
     
     public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy, SMTPSettings settings, SMTPSettings ogolne) {
        MimeMessage message = new MimeMessage(otworzsesje(settings, ogolne));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
     
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
            }
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
