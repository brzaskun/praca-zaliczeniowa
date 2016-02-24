/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
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
//
//    private String wysylajacy;
//    private String klient;
//    private String klientfile;
//    private String podpisfaktury;
//    private String firmafaktury;
//    private String adres;
           
//    private static void init(WpisView wpisView){
//        Podatnik pod = wpisView.getPodatnikObiekt();
//        adres = pod.getEmail();
//        Uz uz = wpisView.getWprowadzil();
//        wysylajacy = uz.getImie()+" "+uz.getNazw();
//        klient = pod.getImie()+" "+pod.getNazwisko();
//        klientfile = wpisView.getPodatnikWpisu();
//        podpisfaktury = wpisView.getPodatnikObiekt().getImie()+" "+wpisView.getPodatnikObiekt().getNazwisko();
//        firmafaktury = wpisView.getPodatnikObiekt().getNazwadlafaktury();
//    }

    public static MimeMessage logintoMail(WpisView wpisView) {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";
        Properties props = new Properties();
        props.put("mail.smtp.host", "mailng.az.pl");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
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
    
    public static MimeMessage logintoMailFakt(Klienci klient, WpisView wpisView)  {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";
        Properties props = new Properties();
        props.put("mail.smtp.host", "mailng.az.pl");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress("info@e-taxman.pl", wpisView.getPodatnikWpisu()));
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
    
     public static MimeMessage logintoMailAdmin(String adreskontrahenta) {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";

        Properties props = new Properties();
        props.put("mail.smtp.host", "mailng.az.pl");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
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
     
     public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy) {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";

        Properties props = new Properties();
        props.put("mail.smtp.host", "mailng.az.pl");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
        } catch (MessagingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
    
}
