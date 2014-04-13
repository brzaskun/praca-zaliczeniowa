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
public class MaiManager extends MailSetUp implements Serializable {

    public static void mailManagerZUS(String adres, String temat, String tresc) throws MessagingException {
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
    
    public static void main (String[] args) throws MessagingException {
        MaiManager.mailManagerZUS("brzaskun@o2.pl", "Test", "test \n test");
    }
}
