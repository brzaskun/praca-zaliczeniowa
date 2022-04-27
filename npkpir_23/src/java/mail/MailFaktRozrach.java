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
    
    
    public static void rozrachunek(String szukanyklient, String email, String udw, String telefon, List<FakturaPodatnikRozliczenie> faktury, WpisView wpisView, FakturaDAO fakturaDAO, double saldo, String stopka, SMTPSettings settings, SMTPSettings ogolne, String tekstwiadomosci) {
        tekstwiadomosci = tekstwiadomosci==null ? "": tekstwiadomosci;
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
                     + "<p>W poniższej tabeli wykaz niezapłaconych faktur na rzecz naszego biura (plik pdf). Prosimy o niezwłoczne uregulowanie zaległości.</p>"
                     + "<p>Przelewy księgowane są automatycznie przez nasz program po pobraniu wyciągów z banku. Jeśli robią państwo przelewy ekpresowe lub z nowego konta może dojśc do niewłaściwego przyporządkowania wpłaty.</p>"
                     + "<p>W takich przypadkach prosimy o kontakt w celu weryfikacji salda.</p>"
                     + "<p>Please find below a list of unsettled invoices issued by us . Please settle any existing arrears as soon as possible.</p>"
                     + "<p>Transfers are posted automatically by our program after downloading bank statements. If you make express transfers or transfers from a new account, the payment may be incorrectly allocated.</p>"
                     + "<p>In such cases, please contact us to verify the balance.</p>"
                     + "<p></p>"
                     + " <table align=\"left\" border=\"1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 550px;\"> <caption> faktury/invoices/rechnungen</caption> "
                    + "<thead> <tr> <th scope=\"col\"> lp</th> <th scope=\"col\"> data wyst.</th><th scope=\"col\"> nr faktury</th> <th scope=\"col\"> kwota do zap.</th> </tr> </thead> "
                    + "<tbody> "
                    + dodajwiersze(faktury)
                    + "</tbody> </table>"
                    + " <p> &nbsp;</p> <p> &nbsp;</p> <p> &nbsp;</p><br/> "
                     + "<p>Firma "+szukanyklient+"</p>"
                     + "<p>tel "+telefon+"</p>"
                     + "<p>Rozliczenia obejmują okres do "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu()+"</p>"
                     + "<p style='color:red;font-weight: bold;'>Zaległa kwota do zapłaty "+F.curr(saldo, f.getWalutafaktury())+"</p>"
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

    private static String dodajwiersze(List<FakturaPodatnikRozliczenie> faktury) {
        String zwrot = "";
        int lp = 1;
        for (FakturaPodatnikRozliczenie p : faktury) {
            zwrot += "<tr> <td style=\"text-align: center;\"> "+lp+"</td> <td> "+p.getData()+"</td><td> "+p.getNrDok()+"</td> <td style=\"text-align: right;\">"+F.curr(p.getKwota(),p.getWalutafaktury())+"</td> </tr> ";
            lp++;
        }
        return zwrot;
    }
}
