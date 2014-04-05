/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import entity.Pismoadmin;
import entity.Uz;
import java.io.Serializable;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Osito
 */
public class MailAdmin extends MailSetUp implements Serializable {

    public static void mailAdmin(String adres, String temat, String tresc) throws MessagingException {
        MailSetUp mailSetUp = new MailSetUp();
        Message message = mailSetUp.logintoMailAdmin(adres);
        try {
            message.setSubject(temat);
            MimeBodyPart mbp1 = new MimeBodyPart();
            String tresczpodpisem = tresc.concat(Mail.stopka);
            mbp1.setContent(tresczpodpisem, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void usterkausunieta(Pismoadmin p, Uz uz) {
        try {
             Message message = logintoMailS(uz.getEmail());
             message.setSubject("Wydruk deklaracji PIT za miesiąc");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Witaj "+p.getNadawca()
                     + "<p>"+"Informujemy, że twoje zgłoszenie z dnia "+p.getDatawiadomosci()
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
    
    public static void main (String[] args) throws MessagingException {
        MailAdmin.mailAdmin("brzaskun@o2.pl", "Test", "test \n test");
    }
}
