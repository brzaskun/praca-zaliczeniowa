/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import beansMail.SMTPBean;
import entity.FakturaWaloryzacja;
import entity.SMTPSettings;
import entity.UPO;
import entity.Uz;
import error.E;
import java.util.List;
import java.util.Locale;
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
    protected static final String dawnoniezmieniona;
    protected static final String zmianaopodatkowania;
    protected static final String wiekszailosc;
    protected static final String minimum;
    protected static final String tabela;
    protected static final String odpowiedz;

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
       fake = "<div style=\"color: green;\">Mail wysłany przez nasz system księgowy. Prosimy nie odpowiadać na niniejszą wiadomość.</div>";
       dawnoniezmieniona = "<p>Chcielibyśmy ponadto podkreślić, że opłata była w państwa przypadku dawno nie zmieniana. Stąd nowa kwota w takiej wysokości.</p>";
       zmianaopodatkowania = "<p>Chcielibyśmy ponadto podkreślić, że wysokośc nowej kwoty jest w szczególności podyktowana zmianą sposobu rozliczeń księgowych państwa firmy od nowego roku.</p>";
       wiekszailosc = "<p>Chcielibyśmy ponadto podkreślić, że wysokośc nowej kwoty wynika w szczególności ze znacznego zwiększenia w ciągu roku ilości dokumentów jakie mamy do analizy i księgowania.</p>";
       minimum = "<p>Chcielibyśmy ponadto podkreślić, że wysokośc nowej kwoty wynika z konieczności dopasowania opłaty miesięcznej do minimalnej kwoty jaką płacą inni klienci w podobnej sytuacji.</p>";
       tabela =  "<table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 500px;\"> <caption> zestawienie zmian - kwoty netto</caption> <thead> <tr> "
            + "<th scope=\"col\"> lp</th> <th scope=\"col\"> składnik faktury</th> <th scope=\"col\"> 2021</th><th scope=\"col\"> 2022</th> </tr> </thead>"
            + "<tbody> <tr> <td style=\"text-align: center;\"> 1</td> <td> księgowość</td> <td style=\"text-align: right;\"> %.2f</td><td style=\"text-align: right;\"> %.2f</td> </tr>"
            + "<tr> <td style=\"text-align: center;\"> 2</td> <td> umowa o pracę</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td><td style=\"text-align: right;\"> %.2f</td> </tr>" 
            + "<tr> <td style=\"text-align: center;\"> 3</td> <td> umowa zlecenie</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td><td style=\"text-align: right;\"> %.2f</td> </tr>"
            + "<tr> <td style=\"text-align: center;\"> 4</td> <td> oddelegowanie</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> <td style=\"text-align: right;\"> %.2f</td></tr>"
            + " <tr> <td style=\"text-align: center;\"> 5</td><td>sprawozdanie roczne</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td> <td style=\"text-align: right;\"> %.2f</td></tr>"
            + " <tr> <td style=\"text-align: center;\"> 6</td> <td>obsługa Niemcy</td> <td style=\"text-align: right;\"> <span style=\"text-align: right;\">%.2f</span></td><td style=\"text-align: right;\"> %.2f</td> </tr>"
            + "</tbody> </table><br/>";
       odpowiedz = "<p style=\"color: blue\">Prosimy o potwierdzenie otrzymania maila przez wciśnięcie przycisku 'Odpowiedz' i wyslanie maila zwrotnego</p>";
   }
    

     public static void nadajMailWaloryzacjaFaktury(String adres, String firma, String nip, FakturaWaloryzacja p, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(null, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(null, ogolne), SMTPBean.nazwaFirmyFrom(null, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
            message.setSubject("Taxman - informacja o nowych stawkach w roku 2022 - NIP "+nip);
            message.setContent("Dzień dobry,"
                    + " dot. firmy: "+firma
                    + "<p>Niniejszy mail dotyczny stawek naszego wynagrodzenia na rok 2022. Z góry przepraszam niekrówych klientów, którzy uważają, że o takich sprawach rozmawia się bezpośrednio. Proszę zrozumieć, że nie jestem w stanie zaprezentować poniższej treści "
                    + " w rozmowach telefonicznych z ponad dwustoma klientami, których obecnie obsługujemy. Z drugiej strony tylko niektórzy klienci będą zainteresowani taką rozmową. Tych klientów oczywiście zapraszam do kontaktu.</p>"
                    + "<p>Kończący się rok należy do jednych z bardziej burzliwych, jeśli chodzi o wydarzenia dotyczące przedsiębiorców</p>"
                    + "<span>Rewolucja w dziedzinie podatków nakłada na przedsiębiorców nowe obowiązki.</span>"
                    + "<span>Te obowiązki cedujecie państwo na nas, a my dbamy o ich prawidłową i terminowa realizację.</span>"
                    + "<span>Niestety podwyżki podatków przekładają się na koszty usług i w wielu wypadkach wymagają optymalizacji podatkowej.</span>"
                    + "<p>Innym czynnikiem wpływającym na działalność firm jest b. wysoka inflacja, króea wynosi (rok-do-roku) ok.8%.</p>"
                    + "<p>Nie do pominięcia są oczekiwania płacowe pracowników naszego biura w związku z galopującą inflacją i nowymi obowiązkami. My ze swej strony bardzo dbamy o to by osoby pracujące w naszym biurze"
                    + " miały, oprócz stale aktualizowanej wiedzy podatkowej i kadrowej, odpowiednie podejście do spraw każdego klienta.</p>"
                    + "<span>Jako przedsiębiorcy zdają sobie państwo doskonale sprawę, jak trudno jest w obecnych czasach znaleźć takie osoby na płytkim obecnie rynku pracy</span>"
                    + "<span>Musimy więc dbać o to, aby nie doszło do niepożądanej rotacji pracowniczej w naszym biurze.</span>"
                    + "<p style=\"color: green;\">Rok 2022 to rok przygotowań do raportowania podatkowych ksiąg przychodów i rozchodów oraz ksiąg rachunkowych do urządu skarbowego.</p>"
                    + "<span style=\"color: green;\">Oznacza to, że będziemy musieli, we współpracy z państwem, na bieżąco analizowac sytuację w firmie. Wszystkie ewentualne korekty, wycofywanie środków trwałych, zmiany w kosztach będą widoczne i analizowane przez urząd skarbowy.</span>"
                    + "<span style=\"color: green;\">Prawdę mówiąc bedziemy pracować w klimacie ciągłej kontroli.</span>"
                    + "<span style=\"color: green;\">Kolejną rewolucją będą faktury elektroniczne rejestrowane centralnie przez ministerstwo finansów. Nie będzie można wystawiać faktur ręcznie, bądź w Excelu. Trzeba będzie używac aplikacji (np. naszej) i wysyłać fakturę po wystawieniu do ministerstwa.</span>"
                    + "<span style=\"color: green;\">Wycofywanie faktur będzię więc praktycznie niemożliwe. Trzeba będzie częściej stosowac proformy.</span>"
                    + "<p style=\"color: green;\">Ostatnio okazało się podczas rozmów na temat rozliczeń, że niektorzy nasi klienci nie wiedzą, że potrafimy pomagać w zatrudnieniu i rozliczaniu obcokrajowców. Robimy to od wielu lat załatwiając sprawy w urzedzie powiatowym i wojewódzkim</p>"
                    + "<p>Chyba po raz pierwszy w historii naszego biura stopa procentowa waloryzacji jest dwucyfrowa. Proszę jednak pamiętać, że 3/4 z tego jest wymuszona inflacją i niekorzystnymi zmianami nowego ładu. Pod uwagę braliśmy jakości dotychczasowej wpółracy. Terminowość, regularność i formę dostarcznia do nas dokumentów, regularne opłacanie naszych faktur itp.</p>"
                    + "<span>My ze swej strony staramy się to skompensować nowymi usługami jakie będziemy świadczyć po przeprowadzce (wyszukiwaniem dotacji i dofinansowań) oraz uruchomieniem nowego programu kadrowego, znacznie ułatwiającego obsługę kadrowo-płacową. </span>"
                    + "<span>Dodatkowo staramy się jak najszybciej wdrożyć w ten obszerne jak nigdy zmiany podatkowe od 2022, aby móc jak najlepiej zoptymalizować państwa firmy.</span>"
                    + "<p>Tak jak pisaliśmy wcześniej jedna ze zmian dotyczy rozbicia opłaty na część księgową i kadrową u niektórych firm. Firmy z najdłuższym u nas stażem płaciły do tej pory za usługi jedną kwotę. Zmieniamy to od stycznia 2022 dzieląc opłatę na księgowość oraz kadry i płace. Kwota więc będzie się zmieniać w zależności od ilosci pracowników/zleceniobiorców. Taki system obowiązuje wszystkich naszych nowych klientów.</p>"
                    + pobiezzmienna(p.isDawnoniezmieniona(), dawnoniezmieniona)
                    + pobiezzmienna(p.isZmianaopodatkowania(), zmianaopodatkowania)
                    + pobiezzmienna(p.isWiekszailosc(), wiekszailosc)
                    + pobiezzmienna(p.isMinimum(), minimum)
                    + "<p style=\"color: green;\">Niniejszy mail został wygenerowany automatycznie. Jest naszą propozycją na rok 2022. Jesteśmy oczywiście gotowi na rozmowy z klientami, którzy uważają, że z jakiegośc powodu zaproponowane nowe kwoty sa nieadekwatne.</p>"
                    + String.format(new Locale("pl"),tabela, p.getKwotabiezacanetto(), p.getKwotabiezacanettoN(), p.getUmowaoprace(), p.getUmowaopraceN(), p.getUmowazlecenie(), 
                            p.getUmowazlecenieN(), p.getOddelegowanie(), p.getOddelegowanieN(), p.getSprawozdanieroczne(), p.getSprawozdanieroczneN(), p.getObsluganiemcy(), p.getObsluganiemcyN())
                    + " <p> &nbsp;</p><p> &nbsp;</p><p> &nbsp;</p><p> &nbsp;</p><p> &nbsp;</p>"
                    + odpowiedz
                    + stopka,  "text/html; charset=utf-8");
            Transport.send(message);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
            E.e(e);
        }
    }
     
    private static String pobiezzmienna(boolean warunek,String zmienna) {
        String zwrot = "";
        if (warunek) {
            zwrot = zmienna;
        }
        return zwrot;
    }
    
    
    public static void nadajMailRejestracjaNowegoUzera(String adres, String login, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
            message.setSubject("Potwierdzenie rejestracji w internetowym serwisie Biura Rachunkowego Taxman");
            message.setContent("Szanowny Użytkowniku,"
                    + "<p>Właśnie zarejestrowałeś się w naszym serwisie z loginem: </p>"
                    + "<span style=\"color: green;\">"+login+"</span>"
                    + "<p>Ze względów bezpieczeństwa Twoje konto wymaga jeszcze aktywacji przez administratora.</p>"
                    + "<p>Może to potrwać do 48h. O udanej aktywacji zostaniesz poinformowany kolejną wiadomością mailową.</p>"
                    + stopka,  "text/html; charset=utf-8");
            Transport.send(message);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void udanazmianaHasla(String adres, String login, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
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
    
    public static void nadanoUprawniednia(String adres, String login, String uprawnienia, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
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
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
      
       public static void resetowaniehasla(String adres, String login, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
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
       
       public static void brakiwJPK(Uz wprowadzil, String opis, List<UPO> upolista, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            if (upolista.size()>0) {
                String upolstring = "";
                int znalazlem = 0;
                for (UPO p : upolista) {
                    if (p.getWprowadzil().equals(wprowadzil)) {
                        upolstring = upolstring+"<p>"+p.getPodatnik().getPrintnazwa()+" "+p.getRok()+"/"+p.getMiesiac()+"</p>";
                        znalazlem++;
                    }
                }
                if (znalazlem > 0) {
                    MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
                    message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(wprowadzil.getEmail()));
                    message.setRecipients(Message.RecipientType.BCC,
                            InternetAddress.parse("brzaskun@o2.pl"));
                    message.setSubject("Informacja o plikach JPK");
                    message.setContent("Szanowny Użytkowniku,"
                            + "<p>"+opis+"</p>"
                            + "<br/>"
                            + upolstring
                            + "<br/>"
                            + stopka,  "text/html; charset=utf-8");
                    message.setHeader("Content-Type", "text/html; charset=utf-8");
                    Transport.send(message);
                }
            }
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
       
    public static void nadajMailWystapilBlad(String blad, SMTPSettings settings, SMTPSettings ogolne) {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("info@taxman.pl"));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse("info@taxman.biz.pl"));
            message.setSubject("Błąd w programie");
            message.setContent("Szanowny Użytkowniku,"
                    + "<span style=\"color: green;\">"+blad+"</span>"
                    + stopka,  "text/html; charset=utf-8");
            Transport.send(message);
            message.setHeader("Content-Type", "text/html; charset=utf-8");
        } catch (Exception e) {
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
