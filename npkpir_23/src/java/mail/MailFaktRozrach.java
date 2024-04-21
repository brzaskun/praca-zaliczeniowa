/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import dao.FakturaDAO;
import embeddable.FakturaPodatnikRozliczenie;
import entity.SMTPSettings;
import format.F;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import msg.Msg;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class MailFaktRozrach implements Serializable{
    
    
    private static final String trescmaila = "<p> Szanowny Podatniku</p> <p> W niniejszym mailu znajdziesz naliczone kwoty zobowiązań z tytułu ZUS I PIT-4</p> "
            + "<p> dla firmy <span style=\"color:#008000;\">%s</span> NIP %s</p> "
            + "<p> do zapłaty/przelania w miesiącu <span style=\"color:#008000;\">%s/%s</span></p> "
           
            + "<p> Ważne! Przelew do ZUS od stycznia 2018 robimy jedną kwotą na JEDNO indywidualne konto wskazane przez ZUS.</p>"
            + "<p> Przypominamy o terminach płatności ZUS:</p>"
            + " <p> do <span style=\"color:#008000;\">15-go</span> - dla firm z osobowością prawną (sp. z o.o.)</p>"
            + " <p> do <span style=\"color:#008000;\">20-go</span> &nbsp;- dla pozostałych firm</p>"
            + " <p> Termin płatności podatku:</p>"
            + " <p> do <span style=\"color:#006400;\">20-go</span> - PIT-4/PIT-8 od wynagrodzeń pracownik&oacute;w</p>"
            + " <p> &nbsp;</p>";
    
    
    public static void rozrachunek(String szukanyklient, String email, String udw, String telefon, List<FakturaPodatnikRozliczenie> faktury, WpisView wpisView, 
            FakturaDAO fakturaDAO, double saldo, String stopka, SMTPSettings settings, SMTPSettings ogolne, String tekstwiadomosci, Map<String,Double> saldawaluty) {
        double saldopln = saldawaluty.get("PLN");
        double saldoeur = saldawaluty.get("EUR");
        tekstwiadomosci = tekstwiadomosci==null ? "": tekstwiadomosci;
        String podsumowaniezalegosci = "<p style='color:red;font-weight: bold;'>Zaległa kwota do zapłaty faktury wystawione w walucie PLN "+F.curr(saldawaluty.get("PLN"), "PLN")+"</p>";
        if (saldawaluty.get("EUR")>0.0) {
            podsumowaniezalegosci = podsumowaniezalegosci+ "<p style='color:red;font-weight: bold;'>Zaległa kwota do zapłaty za faktury wystawione w EUR "+F.curr(saldawaluty.get("EUR"), "EUR")+"</p>";
        }
        Msg.msg("Rozpoczynam wysylanie maila z rozrachunkami. Czekaj na wiadomość końcową");
        int i = 0;
        FakturaPodatnikRozliczenie f = faktury.get(0);
        try {
            MimeMessage message = MailSetUp.logintoMailFakt(email, udw, wpisView, settings, ogolne);
            message.setSubject("Przypomnienie o zaległych płatnościach na rzecz Biura Rachunkowego Taxman","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            mbp1.setContent("Witam"
                     + "<p>W poniższej tabeli wykaz niezapłaconych faktur na rzecz naszego biura (plik pdf). Prosimy o jego weryfikacje. W przypadku, gdy zestawienie jest poprawne, prosimy o niezwłoczne uregulowanie zaległości.</p>"
                     + "<p>Przelewy księgowane są automatycznie przez nasz program po pobraniu wyciągów z banku. <b>Jeśli robią państwo przelewy ekpresowe lub z nowego konta może dojśc do niewłaściwego przyporządkowania wpłaty.</b></p>"
                     + "<p>W takich przypadkach prosimy o kontakt w celu weryfikacji salda.</p>"
                    + "<p>Przypominamy, że od kilku lat mamy nowe konto bankowe - firmowe. Dane konta są każdorazowo umieszczane na fakturze. Niektórzy nasi długoletni klienci dalej wysyłają płatności na stare konto. Proszę zwrócić na to uwagę.</p>"
                    + "<p></p>"
                     + "<p>Please find below a list of unsettled invoices issued by us . Please settle any existing arrears as soon as possible.</p>"
                     + "<p>Transfers are posted automatically by our program after downloading bank statements. If you make express transfers or transfers from a new account, the payment may be incorrectly allocated.</p>"
                     + "<p>In such cases, please contact us to verify the balance.</p>"
                     + dodajtabele(faktury, saldawaluty)
                     + "<p>Firma "+szukanyklient+"</p>"
                     + "<p>tel "+telefon+"</p>"
                     + "<p>Rozliczenia obejmują okres do "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu()+"</p>"
                     + podsumowaniezalegosci
                     + "<p style='color:red'>"+tekstwiadomosci+"</p>"
                     + Mail.reklama
                     + stopka,  "text/html; charset=utf-8");
            // create the second message part
//            MimeBodyPart mbp2 = new MimeBodyPart();
//            mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            // attach the file to the message
//           File file = Plik.plik(nazwa+".pdf", true);
//           if (file.isFile() == false) {
//               Msg.msg("e", "Nie odnaleziono pliku załącznika");
//               return;
//           }
            //ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            //String realPath = ctx.getRealPath("/");
            //FileDataSource fds = new FileDataSource(realPath+"wydruki/"+ nazwa+".pdf");
//            mbp2.setDataHandler(new DataHandler(fds));
//            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            //mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);
            Transport.send(message);
            Msg.msg("i","Wysłano maila z rozrachunkami do klienta "+szukanyklient);
//            try {
//               file = Plik.plik(nazwa+".pdf", true);
//               file.delete();
//            } catch (Exception ef) {
//                Msg.msg("e", "Nieudane usunięcie pliku rozrachunku");
//            }
        } catch (MessagingException e) {
            Msg.msg("e","Bład wysyłki. Sprawdź ustawienia serwera, połączenie z internetem");
            throw new RuntimeException(e);
        }
        i++;
    }
    
    private static String dodajtabele(List<FakturaPodatnikRozliczenie> faktury, Map<String, Double> saldawaluty) {
        String zwrot = "<p></p>"
                + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 550px;\"> <caption> faktury/invoices/rechnungen PLN</caption> "
                + "<thead> <tr> <th scope=\"col\"> lp</th> <th scope=\"col\"> data wyst.</th><th scope=\"col\"> nr faktury</th> <th scope=\"col\"> kwota do zap.</th> </tr> </thead> "
                + "<tbody> "
                + dodajwiersze(faktury, "PLN")
                + "</tbody> </table>"
                + " <p> &nbsp;</p> <p> &nbsp;</p>";
        if (saldawaluty.get("EUR") > 0.0) {
            zwrot = zwrot
                + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 550px;\"> <caption> faktury/invoices/rechnungen EUR</caption> "
                + "<thead> <tr> <th scope=\"col\"> lp</th> <th scope=\"col\"> data wyst.</th><th scope=\"col\"> nr faktury</th> <th scope=\"col\"> kwota do zap.</th> </tr> </thead> "
                + "<tbody> "
                + dodajwiersze(faktury, "EUR")
                + "</tbody> </table>"
                + " <p> &nbsp;</p> <p> &nbsp;</p>";
        }
        zwrot = zwrot + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p><br/> ";
        return zwrot;
    }

    private static String dodajwiersze(List<FakturaPodatnikRozliczenie> faktury, String waluta) {
        String zwrot = "";
        int lp = 1;
        for (FakturaPodatnikRozliczenie p : faktury) {
            if (p.getWalutafaktury().equals(waluta)) {
                if (p.isFaktura0rozliczenie1()) {
                    zwrot += "<tr> <td style=\"text-align: center;\"> "+lp+"</td> <td> "+p.getData()+"</td><td> otrzymano wpłatę"+p.getNrDok()+"</td> <td style=\"text-align: right;\">"+F.curr(-p.getKwota(),p.getWalutafaktury())+"</td> </tr> ";
                    lp++;
                } else {
                    zwrot += "<tr> <td style=\"text-align: center;\"> "+lp+"</td> <td> "+p.getData()+"</td><td>  faktura "+p.getNrDok()+"</td> <td style=\"text-align: right;\">"+F.curr(p.getKwota(),p.getWalutafaktury())+"</td> </tr> ";
                    lp++;
                }
            }
        }
        return zwrot;
    }
}
