/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import error.E;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named
@Stateless
public class Mail {
    protected static final String stopka;
    protected static final String reklama;
    protected static final String fake;

   static {
       stopka = " <div>Z poważaniem</div>"
               + "<div> &nbsp;</div>"
               + "<div> Grzegorz Grzelczyk</div>"
               + "<div> doradca podatkowy</div>"
               + "<div> &nbsp;</div>"
               + "<div style=\"color: green;\">Adres mailowy, z którego została wysłana ta wiadomość nie służy do normalnej korespondencji. Prosimy nie odpowiadać na niniejszą wiadomość.</div>"
               + "<div> &nbsp;</div>"
               + "<div> ul. J.H. Dąbrowskiego 38/40 p.313</div>"
               + "<div> PL-70-100 Szczecin</div>"
               + "<div> tel. +4891 8128287</div>"
               + "<div> fax. +4891 8120977</div>"
               + "<div> mobil +48 603133396</div>"
               + "<div> &nbsp;</div>"
               + "<div> <a href=\"http://taxman.biz.pl\">http://taxman.biz.pl</a></div>"
               + "<div> info@taxman.biz.pl&nbsp;</div>"
               + "<div> SKYPE: taxman777</div>"
               + "<div> &nbsp;</div>"
               + "<div> <a href=\"http://www.facebook.com/BiuroRachunkowe.Szczecin\">http://www.facebook.com/BiuroRachunkowe.Szczecin</a></div>"
               + "<div> &nbsp;</div>"
               + "<div> BRE BANK: 11402004 SWIFT: BREXPLPWMUL&nbsp;</div>"
               + "<div> Numer konta EBAN-nr: 57114020040000340209035790</div>";
       reklama = "<div> &nbsp;</div>"
               + "<div>Możesz zawsze samodzielnie pobierać wszelkie informacje na temat twoje firmy</div>"
               + "<div>Wystarczy zarejestrować się w naszym programie księgowym online </div>"
               + "<div>Tutaj jest adres http tego programu <a href= \"http://taxman.pl:8080\">http://taxman.pl:8080</a></div>"
               + "<div> &nbsp;</div>";
       fake = "<div style=\"color: green;\">Adres mailowy, z którego została wysłana ta wiadomość nie służy do normalnej korespondencji. Prosimy nie odpowiadać na niniejszą wiadomość.</div>";
   }
    

   
    
    
    public static void nadajMailRejestracjaNowegoUzera(String adres, String login) {
        try {
            MimeMessage message = new MimeMessage(logintoMail());
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brzaskun@gmail.com"));
            message.setSubject("Potwierdzenie rejestracji w internetowym serwisie Biura Rachunkowego Taxman");
            message.setContent("Szanowny Użytkowniku,"
                    + "<p>Właśnie zarejestrowałeś się w naszym serwisie z loginem: </p>"
                    + "<span style=\"color: green;\">"+login+"</span>"
                    + "<p>Ze względów bezpieczeństwa Twoje konto wymaga jeszcze aktywacji przez administratora.</p>"
                    + "<p>Może to potrwać do godziny. O udanej aktywacji zostaniesz poinformowany kolejną wiadomością mailową.</p>"
                    + stopka,  "text/html; charset=utf-8");
            Transport.send(message);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void udanazmianaHasla(String adres, String login) {
        try {
            MimeMessage message = new MimeMessage(logintoMail());
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brzaskun@gmail.com"));
            message.setSubject("Potwierdzenie zmiany hasła/adresu email w internetowym serwisie Biura Rachunkowego Taxman");
            message.setContent("Szanowny Użytkowniku,"
                    + "<p>Właśnie zmieniłeś hasło/email w naszym serwisie dla loginu: </p>"
                    + "<span style=\"color: green;\">"+login+"</span>"
                    + "<p>Jeżeli to nie ty zmieniłeś hasło/email, zawiadom administratora o tym zdarzeniu."
                    + stopka,  "text/html; charset=utf-8");
            Transport.send(message);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void nadanoUprawniednia(String adres, String login, String uprawnienia) {
        try {
            MimeMessage message = new MimeMessage(logintoMail());
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brzaskun@gmail.com"));
            message.setSubject("Potwierdzenie nadania uprawnień w serwisie Biura Rachunkowego Taxman");
            message.setContent("Szanowny Użytkowniku,"
                    + "<p>Administrator własnie nadał ci następujące uprawnienia: <strong>"+uprawnienia+"</strong><br/>"
                    + "w naszym serwisie powiązane z loginem: <br/>"+login+".</p>"
                    + "<p>Od teraz możesz logować się do naszego serwisu pod adresem <a href=\"http://taxman.pl:8080\">http://taxman.pl:8080</a><br/>"
                    + "używając wybranego loginu: "+login+" i wybranego podczas rejestracji hasła.</p>"
                    + "<p>W przypadku zagubienia hasła wybierz <a href=\"http://taxman.pl:8080/faces/zapomnialemhasla.xhtml?faces-redirect=true\">"
                    + "zapomnialem hasla</a> na stronie serwisu.</p>"
                    + stopka,  "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("wyslano mail nadano uprawnienia");
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
      
       public static void resetowaniehasla(String adres, String login) {
        try {
            MimeMessage message = new MimeMessage(logintoMail());
            message.setFrom(new InternetAddress("info@e-taxman.pl", "Biuro Rachunkowe Taxman"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brzaskun@gmail.com"));
            message.setSubject("Potwierdzenie zresetowania zapomnianego hasła w serwisie Biura Rachunkowego Taxman");
            message.setContent("Szanowny Użytkowniku,"
                    + "<p>Administrator własnie zresetował Ci hasło"
                    + "w naszym serwisie</p>"
                    + "<p>Nowe hasło brzmi po prostu - <strong>haslo</strong></p>"
                    + "<p>Teraz powinieneś zalogować się do naszego serwisu <a href=\"http://taxman.pl:8080\">http://taxman.pl:8080</a><br/>"
                    + "używając swojego loginu: "+login+" i nowego hasła nadanego przez administratora</p>"
                    + "<p><strong>oraz zmienić je niezwłocznie(!!!) na swoje własne.</strong></p>"
                    + stopka,  "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
      
      
    private static Session logintoMail(){
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
        return session;
    }
}
