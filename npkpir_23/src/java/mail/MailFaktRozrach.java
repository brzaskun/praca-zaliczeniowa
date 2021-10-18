/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import dao.FakturaDAO;
import entity.Klienci;
import entity.SMTPSettings;
import format.F;
import java.io.File;
import java.io.Serializable;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import msg.Msg;
import plik.Plik;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class MailFaktRozrach implements Serializable{
    
    public static void rozrachunek(Klienci szukanyklient, WpisView wpisView, FakturaDAO fakturaDAO, double saldo, String stopka, SMTPSettings settings, SMTPSettings ogolne, String nazwa, String tekstwiadomosci) {
        tekstwiadomosci = tekstwiadomosci==null ? "": tekstwiadomosci;
        Msg.msg("Rozpoczynam wysylanie maila z rozrachunkami. Czekaj na wiadomość końcową");
        int i = 0;
        try {
            MimeMessage message = MailSetUp.logintoMailFakt(szukanyklient, wpisView, settings, ogolne);
            message.setSubject("Przypomnienie o zaległych płatnościach na rzecz Biura Rachunkowego Taxman","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            mbp1.setContent("Witam"
                     + "<p>W załączeniu bieżące rozliczenie faktur i  płatności na rzecz naszego biura (plik pdf). Prosimy o niezwłoczne uregulowanie zaległości.</p>"
                     + "<p>Przelewy księgowane są automatycznie przez nasz program po pobraniu wyciągów z banku. Jeśli robią państwo przelewy ekpresowe lub z nowego konta może dojśc do niewłaściwego przyporządkowania wpłaty.</p>"
                     + "<p>W takich przypadkach prosimy o kontakt w celu weryfikacji salda.</p>"
                     + "<p>Attached is the list of current invoices issued by us and payments to the bank account of our office (pdf file). Please settle any existing arrears as soon as possible.</p>"
                     + "<p>Transfers are posted automatically by our program after downloading bank statements. If you make express transfers or transfers from a new account, the payment may be incorrectly allocated.</p>"
                     + "<p>In such cases, please contact us to verify the balance.</p>"
                     + "<p></p>"
                     + "<p>Firma "+szukanyklient.getNpelna()+"</p>"
                     + "<p>Rozliczenia obejmują okres do "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu()+"</p>"
                     + "<p>Zaległa kwota do zapłaty w pln "+F.curr(saldo)+"</p>"
                     + "<p style='color:red'>"+tekstwiadomosci+"</p>"
                     + Mail.reklama
                     + stopka,  "text/html; charset=utf-8");
            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            // attach the file to the message
           File file = Plik.plik(nazwa+".pdf", true);
           if (file.isFile() == false) {
               Msg.msg("e", "Nie odnaleziono pliku załącznika");
               return;
           }
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            FileDataSource fds = new FileDataSource(realPath+"wydruki/"+ nazwa+".pdf");
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);
            Transport.send(message);
            Msg.msg("i","Wysłano maila z rozrachunkami do klienta "+szukanyklient.getNpelna());
            try {
               file = Plik.plik(nazwa+".pdf", true);
               file.delete();
            } catch (Exception ef) {
                Msg.msg("e", "Nieudane usunięcie pliku rozrachunku");
            }
        } catch (MessagingException e) {
            Msg.msg("e","Bład wysyłki. Sprawdź ustawienia serwera, połączenie z internetem");
            throw new RuntimeException(e);
        }
        i++;
    }
}
