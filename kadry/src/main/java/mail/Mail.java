/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import entity.SMTPSettings;
import error.E;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Named

public class Mail {
    protected static final String stopka;
    protected static final String reklama;
    protected static final String fake;

   static {
       stopka = " <div>Z poważaniem</div>"
               + "<br/>"
               + "<div> Grzegorz Grzelczyk</div>"
               + "<div> doradca podatkowy</div>"
               + "<br/>"
               + "<div> ul. A.Madalińskiego 4</div>"
               + "<div> PL-70-101 Szczecin</div>"
               + "<div> mobil +48 603133396</div>"
               + "<br/>"
               + "<div> <a href=\"http://taxman.biz.pl\">http://taxman.biz.pl</a></div>"
               + "<div> info@taxman.biz.pl&nbsp;</div>"
               + "<br/>"
               + "<div> BRE BANK: 11402004 SWIFT: BREXPLPWMUL&nbsp;</div>"
               + "<div> Numer konta EBAN-nr: 57114020040000340209035790</div>";
       reklama = "<br/>"
               + "<div>Możesz zawsze samodzielnie pobierać wszelkie informacje na temat twoje firmy</div>"
               + "<div>Wystarczy zarejestrować się w naszym programie księgowym online </div>"
               + "<div>Tutaj jest adres http tego programu <a href= \"http://taxman.pl:8080\">http://taxman.pl:8080</a></div>"
               + "<br/>";
       fake = "<div style=\"color: green;\">Adres mailowy, z którego została wysłana ta wiadomość nie służy do normalnej korespondencji. Prosimy nie odpowiadać na niniejszą wiadomość.</div>";
   }
    

      
      
       public static void resetowaniehasla(String adres, String login, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
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
       
      
   
    public static String getStopka() {
        return stopka;
    }

    public static String getReklama() {
        return reklama;
    }

    public static String getFake() {
        return fake;
    }
    
    
}
