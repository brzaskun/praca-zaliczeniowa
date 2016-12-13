/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import beansMail.SMTPBean;
import dao.SMTPSettingsDAO;
import entity.Klienci;
import entity.SMTPSettings;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
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

    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    
    public static MimeMessage logintoMail(WpisView wpisView, SMTPSettings settings) {
       MimeMessage message = new MimeMessage(otworzsesje(settings));
        try {
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings), SMTPBean.nazwaFirmyFrom(settings)));
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
            System.out.println("Nie masz ma wprowadzonego adresu mail podatnika. Wysyłka nieudana");
        }
        if (!wpisView.getWprowadzil().getUprawnienia().equals("Guest")) {
            try {
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(wpisView.getWprowadzil().getEmail()));
            } catch (Exception e) {
                E.e(e);
                System.out.println("Nie masz ma wprowadzonego adresu mail BCC");
            }
        }
        return message;
    }
    
    public static MimeMessage logintoMailFakt(Klienci klient, WpisView wpisView, SMTPSettings settings)  {
        MimeMessage message = new MimeMessage(otworzsesje(settings));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings), SMTPBean.nazwaFirmyFrom(settings)));
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
    
     public static MimeMessage logintoMailAdmin(String adreskontrahenta, SMTPSettings settings) {
        MimeMessage message = new MimeMessage(otworzsesje(settings));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings), SMTPBean.nazwaFirmyFrom(settings)));
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
     
     public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy, SMTPSettings settings) {
        MimeMessage message = new MimeMessage(otworzsesje(settings));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings), SMTPBean.nazwaFirmyFrom(settings)));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
     
      public static Session otworzsesje(SMTPSettings settings) {
        Session session = null;
        if (settings == null) {
            final String username = "info@taxman.pl";
            final String password = "Taxman7005*";
            Properties props = new Properties();
            props.put("mail.smtp.host", "taxman.pl");
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
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
