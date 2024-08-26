/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package timer;

import beansMail.SMTPBean;
import dao.FreistellungDAO;
import dao.SMTPSettingsDAO;
import data.Data;
import entity.Freistellung;
import entity.SMTPSettings;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mail.MailSetUp;

@Named
@Stateless
public class FreistellungReminder {

    @Inject
    private FreistellungDAO freistellungDAO;
    
     @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;

    @Schedule(dayOfWeek = "Mon-Fri", hour = "22", minute = "22", persistent = false)
    public void alarmFreistellung() {
        List<Freistellung> listaFreistellung = freistellungDAO.findAll();
        String databiezaca = Data.aktualnaData();
        
        for (Freistellung f : listaFreistellung) {
            if (f.getDatado() != null && !f.getDatado().equals("")) {
                String dataDo = f.getDatado();
                String data60dni = Data.obliczDateMinusDni(dataDo, 60);
                String data30dni = Data.obliczDateMinusDni(dataDo, 30);
                
                boolean czyMail1Niewyslany = f.getMailprzypom1() == null || f.getMailprzypom1().equals("");
                boolean czyMail2Niewyslany = f.getMailprzypom2() == null || f.getMailprzypom2().equals("");
                
                if (czyMail1Niewyslany && Data.czyjestpoTerminData(data60dni, databiezaca)) {
                    // Wyślij pierwszy mail i zapisz datę wysłania
                    wyslijMail(f, 1);
                    f.setMailprzypom1(databiezaca);
                    freistellungDAO.edit(f);
                } else if (czyMail2Niewyslany && f.getMailprzypom1() != null && Data.czyjestpoTerminData(data30dni, databiezaca)) {
                    // Wyślij drugi mail i zapisz datę wysłania
                    wyslijMail(f, 2);
                    f.setMailprzypom2(databiezaca);
                    freistellungDAO.edit(f);
                }
            }
        }
    }


    private void wyslijMail(Freistellung freistellung, int numerPrzypomnienia) {
        try {
            SMTPSettings ogolne = sMTPSettingsDAO.findSprawaByDef();
            String email = freistellung.getPodatnik().getEmail();  // Zakładam, że podatnik ma pole `email`
            String mailBCC="w.daniluk@taxman.biz.pl";
                if (freistellung.getPodatnik().getKsiegowa().getEmail()!=null) {
                    mailBCC= freistellung.getPodatnik().getKsiegowa().getEmail();
                }
            if (email != null && !email.isEmpty()) {
                MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(ogolne, ogolne));
                message.setSubject("Przypomnienie o wygasającej umowie Freistellung", "UTF-8");
                message.setFrom(new InternetAddress(SMTPBean.adresFrom(ogolne, ogolne), SMTPBean.nazwaFirmyFrom(ogolne, ogolne)));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBCC));

                // Tworzenie i wypełnianie treści wiadomości
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setHeader("Content-Type", "text/html; charset=utf-8");

                String body = "<p>Zbliża się termin zakończenia ważności Freistellung dla firmy</p>"
                        + "<p>" + freistellung.getPodatnik().getPrintnazwa() + "</p>"
                        + "<p>Freistellung wygasa dnia dnia " + freistellung.getDatado() + ".</p>";
                
                if (numerPrzypomnienia == 1) {
                    body += "<p style=\"color: green;\">To jest pierwsze przypomnienie na 60 dni przed wygaśnięciem Freistellung.</p>";
                } else if (numerPrzypomnienia == 2) {
                    body += "<p style=\"color: red;\">To jest drugie przypomnienie na 30 dni przed wygaśnięciem Freistellung.</p>";
                }
                body += "<p>Proszę skontaktować się ze swoją księgową "+mailBCC +" celem ewentualnego przedłużenia Freistellung.</p>"
                        + "<p>Z poważaniem,</p>"
                        + "<br/>"
                        + "<p>Biuro Rachunkowe Taxman</p>";

                mbp1.setContent(body, "text/html; charset=utf-8");

                // Tworzenie Multipart i dodawanie części
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);

                // Dodawanie Multipart do wiadomości
                message.setContent(mp);

                // Wysłanie wiadomości
                Transport.send(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
