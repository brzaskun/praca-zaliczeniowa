/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 *
 * @author Osito
 */

public class MaiManager implements Serializable {

    public static void mailManagerZUS(String adres, String temat, String tresc, String wysylajacy) throws MessagingException, UnsupportedEncodingException {
        MailSetUp mailSetUp = new MailSetUp();
        MimeMessage message = mailSetUp.logintoMailZUS(adres, wysylajacy);
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
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main (String[] args) throws MessagingException {
        try {
            MaiManager.mailManagerZUS("brzaskun@wp.pl", "Test", "test \n test", "brzaskun@wp.pl");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MaiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
