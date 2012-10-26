/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import java.util.Properties;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
public class Mail {

    public static void nadajMail(String adres, String login) {

        final String username = "teleputa@wp.pl";
        final String password = "Teleputa";

        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.wp.pl");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teleputa@wp.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setSubject("Potwierdzenie rejestracji w serwisie Biura Rachunkowego Taxman");
            message.setText("Szanowny Kliencie,"
                    + "\n\nWłaśnie zarejestrowałeś się w naszym serwisie z loginem: \n    "+login
                    + "\nZe względów bezpieczeństwa Twoje konto wymaga jeszcze aktywacji dokonanej przez administratora."
                    + "\nMoże to potrwać do godziny. O udanej aktywacji zostaniesz poinformowany kolejną wiadomością."
                    + "\n\nZ poważaniem"
                    + "\n\nObsługa serwisu");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
      public static void nadanoUprawniednia(String adres, String login, String uprawnienia) {

        final String username = "teleputa@wp.pl";
        final String password = "Teleputa";

        Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.wp.pl");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teleputa@wp.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setSubject("Potwierdzenie nadania uprawnień w serwisie Biura Rachunkowego Taxman");
            message.setText("Szanowny Kliencie,"
                    + "\n\nAdministrator własnie nadał ci uprawnienia "+uprawnienia
                    + "\nw naszym serwisie z loginem: \n    "+login+"."
                    + "\nOd teraz możesz logować się do naszego serwisu http://213.136.236.104:8080"
                    + "\nużywając wybranego loginu: "+login+" i wybranego hasła."
                    + "\n\nZ poważaniem"
                    + "\n\nObsługa serwisu");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
