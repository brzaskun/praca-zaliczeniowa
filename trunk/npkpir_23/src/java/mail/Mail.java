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
    private static Session session;
    
    public static void nadajMail(String adres, String login) {

        logintoMail();

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@e-taxman.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setSubject("Potwierdzenie rejestracji w internetowym serwisie Biura Rachunkowego Taxman");
            message.setText("Szanowny Kliencie,"
                    + "\n\nWłaśnie zarejestrowałeś się w naszym serwisie z loginem: \n    "+login
                    + "\nZe względów bezpieczeństwa Twoje konto wymaga jeszcze aktywacji przez administratora."
                    + "\nMoże to potrwać do godziny. O udanej aktywacji zostaniesz poinformowany kolejną wiadomością mailową."
                    + "\n\nZ poważaniem"
                    + "\n\nObsługa serwisu"
                    + "\nBiuro Rachunkowe Taxman"
                    + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                    + "\ntel. 91 8120976");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
      public static void nadanoUprawniednia(String adres, String login, String uprawnienia) {
       
        logintoMail();
        
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@e-taxman.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setSubject("Potwierdzenie nadania uprawnień w serwisie Biura Rachunkowego Taxman");
            message.setText("Szanowny Kliencie,"
                    + "\n\nAdministrator własnie nadał ci uprawnienia "+uprawnienia
                    + "\nw naszym serwisie z loginem: \n    "+login+"."
                    + "\nOd teraz możesz logować się do naszego serwisu pod adresem http://213.136.236.104:8080"
                    + "\nużywając wybranego loginu: "+login+" i wybranego hasła."
                    + "\n\nW przypadku zagubienia hasła wybierz odpowiednią opcję na stronie serwisu."
                    + "\n\nZ poważaniem"
                     + "\n\nObsługa serwisu"
                    + "\nBiuro Rachunkowe Taxman"
                    + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                    + "\ntel. 91 8120976");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
      
       public static void resetowaniehasla(String adres, String login) {
       
        logintoMail();
        
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("info@e-taxman.pl"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setSubject("Potwierdzenie zresetowania zapomnianego hasła w serwisie Biura Rachunkowego Taxman");
            message.setText("Szanowny Kliencie,"
                    + "\n\nAdministrator własnie zresetował ci hasło"
                    + "\nw naszym serwisie"
                    + "\nNowe hasło brzmi po prostu - 'haslo'"
                    + "\nTeraz powinieneś zalogować się do naszego serwisu http://213.136.236.104:8080"
                    + "\nużywając swojego loginu: "+login+" i nowego hasła nadanego przez administratora"
                    + "\noraz zmienić je niezwłocznie(!!!) na swoje własne."
                    + "\n\nZ poważaniem"
                    + "\n\nObsługa serwisu"
                    + "\nBiuro Rachunkowe Taxman"
                    + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                    + "\ntel. 91 8120976");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
      
      
    private static void logintoMail(){
        final String username = "info@e-taxman.pl";
        final String password = "Pufikun7005*";

        Properties props = new Properties();
		props.put("mail.smtp.host", "az0066.srv.az.pl");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
        
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

    }
}
