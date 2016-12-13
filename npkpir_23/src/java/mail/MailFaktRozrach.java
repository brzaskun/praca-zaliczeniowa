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
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import msg.Msg;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class MailFaktRozrach implements Serializable{
    
    public static void rozrachunek(Klienci szukanyklient, WpisView wpisView, FakturaDAO fakturaDAO, double saldo, String stopka, SMTPSettings settings) {
        Msg.msg("Rozpoczynam wysylanie maila z rozrachunkami. Czekaj na wiadomość końcową");
        int i = 0;
        try {
            MimeMessage message = MailSetUp.logintoMailFakt(szukanyklient, wpisView, settings);
            message.setSubject("Przypomnienie o zaległych płatnościach na rzecz Biura Rachunkowego Taxman","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            mbp1.setContent("Witam"
                     + "<p>W załączeniu bieżące rozliczenie płatności na rzecz naszego biura (plik pdf). Prosimy o niezwłoczne uregulowanie płatności.</p>"
                     + "<p>Firma "+szukanyklient.getNpelna()+"</p>"
                     + "<p>za okres "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu()+"</p>"
                     + "<p>zaległa kwota do zapłaty "+F.c(saldo)+"</p>"
                     + Mail.reklama
                     + stopka,  "text/html; charset=utf-8");
            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            // attach the file to the message
           String nazwa = wpisView.getPodatnikObiekt().getNip()+"faktrozrach";
           File file = Plik.plik(nazwa+".pdf", true);
           if (file.isFile() == false) {
               System.out.println("Nie odnaleziono pliku załącznika");
               Msg.msg("e", "Nie odnaleziono pliku załącznika");
               return;
           }
            FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/"+ nazwa+".pdf");
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
            throw new RuntimeException(e);
        }
        i++;
    }
}
