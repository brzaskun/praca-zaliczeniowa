/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import data.Data;
import entity.Pismoadmin;
import entity.SMTPSettings;
import entity.Uz;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import view.WpisView;
/**
 *
 * @author Osito
 */

public class MailAdmin implements Serializable {

    public static void mailAdmin(String adres, String temat, String tresc, SMTPSettings ogolne, byte[] zalacznik, String nazwapliku)  {
        try {
            MailSetUp mailSetUp = new MailSetUp();
            MimeMessage message = mailSetUp.logintoMailAdmin(adres, null, ogolne);
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String tresczpodpisem = tresc.concat(Mail.stopka);
            mbp1.setContent(tresczpodpisem, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void dolaczplik(byte[] is, Multipart mimeMultipart, String nazwapliku) {
        if (is != null && is instanceof byte[]) {
            try {
                // create the second message part with the attachment from a OutputStrean
                MimeBodyPart attachment= new MimeBodyPart();
                ByteArrayDataSource ds = new ByteArrayDataSource(is, "application/pdf");
                attachment.setDataHandler(new DataHandler(ds));
                attachment.setFileName(nazwapliku);
                mimeMultipart.addBodyPart(attachment);
            } catch (Exception ex) {
                Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
    
    public static void usterkausunieta(Pismoadmin p, Uz uz, WpisView wpisView, SMTPSettings ogolne) {
        try {
             MailSetUp mailSetUp = new MailSetUp();
             MimeMessage message = mailSetUp.logintoMailAdmin(uz.getEmail(), null, ogolne);
             message.setSubject("Taxman - Informacja o rozwiązaniu problemu","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Witaj "+p.getNadawca()
                     + "<p>"+"Informujemy, że twoje zgłoszenie z dnia "+Data.data_ddMMMMyyyy(p.getDatawiadomosci())
                     + "o treści:</p>"
                     + "<p style=\"color: green;\">"+p.getTresc()+"</p>"
                     + "<p>zostało załatwione pozytywnie przed admina.</p>"
                     + "<p>Z poważaniem</p>"
                     + "<br/>"
                     + "<p>Administrator Programu</p>", "text/html; charset=utf-8");
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         } 
        
    }
    
     public static void zablokowanoIPinfoDlaadmina(String ip, SMTPSettings ogolne) {
        try {
             MailSetUp mailSetUp = new MailSetUp();
             MimeMessage message = mailSetUp.logintoMailAdmin("brzaskun@gmail.com", null, ogolne);
             message.setSubject("Zablokowano IP usera","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Zablokowano IP usera"
                     + "<p>"+"Informuję cię, drogi Adminie, że z dnia "+new Date()
                     + "zablokowano usera o IP:</p>"
                     + "<p style=\"color: green;\">"+ip+"</p>"
                     + "<p>Z poważaniem</p>"
                     + "<br/>"
                     + "<p>Serwer Programu</p>", "text/html; charset=utf-8");
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
        } 
        
    }
    
    
    
    public static void main (String[] args) throws MessagingException {
        MailAdmin.mailAdmin("brzaskun@o2.pl", "Test", "test \n test", null, null, null);
    }
}
