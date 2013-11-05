/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

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
            mbp1.setText(tresc);
            mbp1.setHeader("Content-Type", "text/html");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            message.setContent(mp);
            Transport.send(message);
            System.out.println("Done sending Adminmail");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main (String[] args) throws MessagingException {
        MailAdmin mailAdmin = new MailAdmin();
        mailAdmin.mailAdmin("brzaskun@o2.pl", "Test", "test \n test");
    }
}
