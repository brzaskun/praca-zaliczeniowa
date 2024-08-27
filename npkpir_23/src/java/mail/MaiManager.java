/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import beansMail.SMTPBean;
import entity.SMTPSettings;
import error.E;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author Osito
 */

public class MaiManager implements Serializable {
    
   public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy, SMTPSettings settings, SMTPSettings ogolne) {
        MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
        try {
            message.setSentDate(new Date());
            message.addHeader("X-Priority", "1");
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
            message.setReplyTo(InternetAddress.parse(wysylajacy));
        } catch (MessagingException ex) {
            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
   //zamienilem bo przychodzily odpowiedzi od klientow do mnie 12.11.2023
//   public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy, SMTPSettings settings, SMTPSettings ogolne) {
//        MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
//        try {
//            message.setSentDate(new Date());
//            message.addHeader("X-Priority", "1");
//            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
//            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
//            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
//        } catch (MessagingException ex) {
//            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return message;
//    }

    public static void mailManagerZUSPIT(String adres, String temat, String tresc, String wiadomoscodksiegowej, String wysylajacy,  SMTPSettings settings, SMTPSettings ogolne) throws MessagingException, UnsupportedEncodingException {
        //MailSetUp mailSetUp = new MailSetUp();
        MimeMessage message = logintoMailZUS(adres, wysylajacy, settings, ogolne);
        try {
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            MimeBodyPart mbp1 = new MimeBodyPart();
            if (wiadomoscodksiegowej!=null) {
                tresc = tresc.concat(wiadomoscodksiegowej);
            }
            String tresczpodpisem = tresc.concat(Mail.stopka);
            mbp1.setContent(tresczpodpisem, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            E.m(e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            E.m(ex);
            // Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public static void mailManagerZUSPITZalaczniki(String adres, String temat, String tresc, String wiadomoscodksiegowej, String wysylajacy, SMTPSettings settings, 
        SMTPSettings ogolne, ByteArrayOutputStream ksiega, ByteArrayOutputStream deklaracjavat, ByteArrayOutputStream plikjpk) throws MessagingException, UnsupportedEncodingException {
    // Logowanie do maila
    MimeMessage message = logintoMailZUS(adres, wysylajacy, settings, ogolne);
    try {
        // Ustawienie tematu wiadomości
        message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));

        // Tworzenie pierwszej części wiadomości (treści)
        MimeBodyPart mbp1 = new MimeBodyPart();
        if (wiadomoscodksiegowej != null) {
            tresc = tresc.concat(wiadomoscodksiegowej);
        }
        String tresczpodpisem = tresc.concat(Mail.stopka);
        mbp1.setContent(tresczpodpisem, "text/html; charset=utf-8");
        mbp1.setHeader("Content-Type", "text/html; charset=utf-8");

        // Tworzenie drugiej części wiadomości (załącznik - ksiega)
        MimeBodyPart attachmentPart1 = new MimeBodyPart();
        if (ksiega != null) {
            DataSource dataSource1 = new ByteArrayDataSource(ksiega.toByteArray(), "application/pdf");
            attachmentPart1.setDataHandler(new DataHandler(dataSource1));
            attachmentPart1.setFileName("ksiega.pdf");
        }

        // Tworzenie trzeciej części wiadomości (załącznik - deklaracjavat)
        MimeBodyPart attachmentPart2 = new MimeBodyPart();
        if (deklaracjavat != null) {
            DataSource dataSource2 = new ByteArrayDataSource(deklaracjavat.toByteArray(), "application/pdf");
            attachmentPart2.setDataHandler(new DataHandler(dataSource2));
            attachmentPart2.setFileName("deklaracjavat.pdf");
        }

        // Tworzenie czwartej części wiadomości (załącznik - plikjpk)
        MimeBodyPart attachmentPart3 = new MimeBodyPart();
        if (plikjpk != null) {
            DataSource dataSource3 = new ByteArrayDataSource(plikjpk.toByteArray(), "application/pdf");
            attachmentPart3.setDataHandler(new DataHandler(dataSource3));
            attachmentPart3.setFileName("plikjpk.pdf");
        }

        // Tworzenie całej wiadomości z treścią i załącznikami
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mbp1);
        if (ksiega != null) {
            mp.addBodyPart(attachmentPart1);
        }
        if (deklaracjavat != null) {
            mp.addBodyPart(attachmentPart2);
        }
        if (plikjpk != null) {
            mp.addBodyPart(attachmentPart3);
        }

        // Ustawienie zawartości wiadomości
        message.setContent(mp);

        // Wysłanie wiadomości
        Transport.send(message);
    } catch (MessagingException e) {
        E.m(e);
    } catch (Exception ex) {
        E.m(ex);
        // Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    
    public static void main (String[] args) throws MessagingException {
        try {
            MaiManager.mailManagerZUSPIT("info@taxman.biz.pl", "Test", "test \n test", null, "brzaskun@wp.pl", null, null);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
