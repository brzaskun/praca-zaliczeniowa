package timer;

import beansMail.SMTPBean;
import dao.SMTPSettingsDAO;
import dao.UberprufungDAO;
import data.Data;
import entity.SMTPSettings;
import entity.Uberprufung;
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
public class UberprufungReminder {

    @Inject
    private UberprufungDAO uberprufungDAO;
    
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;

    @Schedule(dayOfWeek = "Mon-Fri", hour = "22", minute = "22", persistent = false)
    public void alarmUberprufung() {
        List<Uberprufung> listaUberprufung = uberprufungDAO.findAll();
        String databiezaca = Data.aktualnaData();
        
        for (Uberprufung u : listaUberprufung) {
            if (u.getDatado() != null && !u.getDatado().equals("")) {
                String dataDo = u.getDatado();
                String data60dni = Data.obliczDateMinusDni(dataDo, 60);
                String data30dni = Data.obliczDateMinusDni(dataDo, 30);
                
                boolean czyMail1Niewyslany = u.getMailprzypom1() == null || u.getMailprzypom1().equals("");
                boolean czyMail2Niewyslany = u.getMailprzypom2() == null || u.getMailprzypom2().equals("");
                
                if (czyMail1Niewyslany && Data.czyjestpoTerminData(data60dni, databiezaca)) {
                    // Wyślij pierwszy mail i zapisz datę wysłania
                    wyslijMail(u, 1);
                    u.setMailprzypom1(databiezaca);
                    uberprufungDAO.edit(u);
                } else if (czyMail2Niewyslany && u.getMailprzypom1() != null && Data.czyjestpoTerminData(data30dni, databiezaca)) {
                    // Wyślij drugi mail i zapisz datę wysłania
                    wyslijMail(u, 2);
                    u.setMailprzypom2(databiezaca);
                    uberprufungDAO.edit(u);
                }
            }
        }
    }

    private void wyslijMail(Uberprufung uberprufung, int numerPrzypomnienia) {
        try {
            SMTPSettings ogolne = sMTPSettingsDAO.findSprawaByDef();
            String email = uberprufung.getPodatnik().getEmail();  // Zakładam, że podatnik ma pole `email`
            String mailBCC = "w.daniluk@taxman.biz.pl";
            if (uberprufung.getPodatnik().getKsiegowa().getEmail() != null) {
                mailBCC = uberprufung.getPodatnik().getKsiegowa().getEmail();
            }
            if (email != null && !email.isEmpty()) {
                MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(ogolne, ogolne));
                message.setSubject("Przypomnienie o zbliżającym się terminie wykonania sprawozdania Uberprufung", "UTF-8");
                message.setFrom(new InternetAddress(SMTPBean.adresFrom(ogolne, ogolne), SMTPBean.nazwaFirmyFrom(ogolne, ogolne)));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailBCC));

                // Tworzenie i wypełnianie treści wiadomości
                MimeBodyPart mbp1 = new MimeBodyPart();
                mbp1.setHeader("Content-Type", "text/html; charset=utf-8");

                String body = "<p>Zbliża się termin wykonania sprawozdania Uberprufung dla firmy</p>"
                        + "<p>" + uberprufung.getPodatnik().getPrintnazwa() + "</p>"
                        + "<p>Sprawozdanie musi zostać wykonane do dnia " + uberprufung.getDatado() + ".</p>";
                
                if (numerPrzypomnienia == 1) {
                    body += "<p style=\"color: green;\">To jest pierwsze przypomnienie na 60 dni przed terminem wykonania sprawozdania Uberprufung.</p>";
                } else if (numerPrzypomnienia == 2) {
                    body += "<p style=\"color: red;\">To jest drugie przypomnienie na 30 dni przed terminem wykonania sprawozdania Uberprufung.</p>";
                }
                body += "<p>Proszę skontaktować się ze swoją księgową " + mailBCC + " celem ewentualnego przygotowania dokumentów.</p>"
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
