/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

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
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author Osito
 */

public class MaiManager implements Serializable {
    
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

    public static void mailManagerZUS(String adres, String temat, String tresc, String wysylajacy) throws MessagingException, UnsupportedEncodingException {
        //MailSetUp mailSetUp = new MailSetUp();
        MimeMessage message = logintoMailZUS(adres, wysylajacy);
        try {
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String tresczpodpisem = tresc.concat(Mail.stopka);
            mbp1.setContent(tresczpodpisem, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            E.e(e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            E.e(ex);
            Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main (String[] args) throws MessagingException {
        try {
            MaiManager.mailManagerZUS("info@taxman.biz.pl", "Test", "test \n test", "brzaskun@wp.pl");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
