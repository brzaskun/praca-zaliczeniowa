/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.Serializable;
import java.util.Properties;
import javax.ejb.Singleton;
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
@Singleton
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

    public static MimeMessage logintoMail(WpisView wpisView) throws MessagingException {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";
        Properties props = new Properties();
        props.put("mail.smtp.host", "az0066.srv.az.pl");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@e-taxman.pl"));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(wpisView.getPodatnikObiekt().getEmail()));
        if (!wpisView.getWprowadzil().getUprawnienia().equals("Guest")){
        try {
            message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wpisView.getWprowadzil().getEmail()));
        } catch (Exception e){
            Msg.msg("e", "Nie masz ma wprowadzonego adresu mail. Wysyłka nieudana");
        }
        }
        return message;
    }
    
     public static MimeMessage logintoMailAdmin(String adreskontrahenta) throws MessagingException {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";

        Properties props = new Properties();
        props.put("mail.smtp.host", "az0066.srv.az.pl");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@e-taxman.pl"));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
        return message;
    }
     
     public static MimeMessage logintoMailZUS(String adreskontrahenta, String wysylajacy) throws MessagingException {
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";

        Properties props = new Properties();
        props.put("mail.smtp.host", "az0066.srv.az.pl");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("info@e-taxman.pl"));
        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(adreskontrahenta));
        message.setRecipients(Message.RecipientType.BCC,InternetAddress.parse(wysylajacy));
        return message;
    }
    
}
