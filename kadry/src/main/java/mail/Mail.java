/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import entity.FirmaKadry;
import entity.Pracownik;
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
    

      
      
       public static void ankieta(String adres, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("brzaskun@gmail.com"));
            message.setSubject("Wprowadzenie/aktualizacja danych pracownika");
            message.setContent("Dzień dobry,"
                    + "<p>Twój pracodawca prosi o uzupełnienie niezbędnych danych"
                    + "w naszym serwisie kadrowo-płacowym</p>"
                    + "<p>Dane są niezbędne do rozliczeń kadrowych i płac.</p>"
                    + "<p>Teraz powinieneś zalogować się do naszego serwisu <a href=\"https://taxman.pl:8181//kadry\">https://taxman.pl:8181//kadry</a><br/>"
                    + "używając jako loginu: "+adres+" a jako hasła swojego numeru Pesel</p>"
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

  
    public static void updateemailpracownik(FirmaKadry firma, String adres, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("a.barczyk@taxman.biz.pl"));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("k.koszarek@taxman.biz.pl"));
            message.setSubject("Aktualizacja danych w firmie "+firma.getNazwa());
            message.setContent("Dzień dobry"
                    + "<p>Firma "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "zaktualizowała właśnie dane swoich pracowników</p>"
                    + stopka,  "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
    
    public static void updateemailnowypracownik(FirmaKadry firma, Pracownik pracownik, String adres, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("a.barczyk@taxman.biz.pl"));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("k.koszarek@taxman.biz.pl"));
            message.setSubject("Nowy pracownik w "+firma.getNazwa());
            message.setContent("Dzień dobry"
                    + "<p>Firma "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "dodała nowego pracownika</p>"
                    + "<p>dodała nowego pracownika "+pracownik.getNazwiskoImie()+"</p>"
                    + stopka,  "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
    
    
}
