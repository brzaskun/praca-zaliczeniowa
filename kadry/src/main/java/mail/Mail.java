/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author Osito
 */
import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.SMTPSettings;
import entity.Umowa;
import error.E;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import javax.activation.DataHandler;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

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
               + "<div> ul. 1 Maja 38 bud.A</div>"
               + "<div> PL-71-627 Szczecin</div>"
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
               + "<div>Tutaj jest adres http tego programu <a href= \"https://taxman.pl:8181\">https://taxman.pl:8181</a></div>"
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
                    + "<p>Pana/Pani pracodawca prosi o uzupełnienie niezbędnych danych osobowych"
                    + "w naszym serwisie kadrowo-płacowym</p>"
                    + "<p>Dane są niezbędne do rozliczeń kadrowych i płac.</p>"
                    + "<p>Prosze zalogować się teraz do naszego serwisu <a href=\"https://taxman.pl:8181//kadry\">https://taxman.pl:8181//kadry</a><br/>"
                    + "używając jako loginu swojego adresu mailowego: "+adres+" a jako hasła swojego numeru Pesel</p>"
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
                    InternetAddress.parse("m.piwonska@taxman.biz.pl"));
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
                    InternetAddress.parse("m.piwonska@taxman.biz.pl"));
            String temat = "Nowy pracownik w "+firma.getNazwa();
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            message.setContent("Dzień dobry"
                    + "<p>Firma "+firma.getNazwa()+" NIP "+firma.getNip()
                    + " dodała nowego pracownika</p>"
                    + "<p>Nazwisko i imię pracownika "+pracownik.getNazwiskoImie()+"</p>"
                    + stopka,  "text/html; charset=utf-8");
            message.setHeader("Content-Type", "text/html; charset=utf-8");
            Transport.send(message);
        } catch (Exception e) {
            E.e(e);
            throw new RuntimeException(e);
        }
    }
    
    
    public static void mailDRA(FirmaKadry firma, String rok, String mc, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            if (firma.getKsiegowa()!=null&&firma.getKsiegowa().equals("")==false) {
                adresBCC = adresBCC+","+firma.getKsiegowa();
            }
            if (firma.getKsiegowa()!=null&&firma.getKsiegowa().equals("")==false) {
                adresBCC = adresBCC+","+firma.getKsiegowa();
            }
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Zestawienie DRA "+firma.getNazwa()+" za "+rok+"/"+mc;
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu zestawienie DRA dla firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "za okres</p>"
                    + "<p> "+rok+"/"+mc+"</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mailUrlopy(FirmaKadry firma, String rok, String mc, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Zestawienie wykorzystanych urlpów "+firma.getNazwa()+" za "+rok+"/"+mc;
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu zestawienie wykorzystanych urlopów pracowników firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "za rok </p>"
                    + "<p> "+rok+"</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mailZaswiadczeniezarobki(FirmaKadry firma, String rok, String mc, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC, Pracownik pracownik)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Zaśwaidczenie o zartrudnieniu "+firma.getNazwa()+" rok "+rok+"/"+mc;
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu zaświadczenie o zatrudnieniu dla pracownika "+pracownik.getNazwiskoImie()
                    + " za ostatni okres</p>"
                    + "<p> "+rok+"/"+mc+"</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void mailPIT11Zbiorcze(FirmaKadry firma, String rok, String mc, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "PIT-11 firma "+firma.getNazwa()+" za "+rok+"/"+mc;
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu wydruki deklaracji PIT-11 dla pracowników firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "za okres</p>"
                    + "<p> "+rok+"/"+mc+"</p>"
                       + "<p>Proszę wydrukować i dać pracownikom do podpisania DWA egzemplarza.</p>"
                    + "<p>Jeden egzemplarz proszę odesłać do nas.</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void mailListaPlac(FirmaKadry firma, String rok, String mc, String adresemail, String adresemaillista, SMTPSettings settings,SMTPSettings ogolne, ByteArrayOutputStream listaplac, ByteArrayOutputStream rachunki, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adresemail));
            String adresybcc = adresBCC;
            if (adresemaillista!=null) {
                adresybcc = adresybcc+","+adresemaillista;
            }
            if (firma.getKsiegowa()!=null&&firma.getKsiegowa().equals("")==false) {
                adresBCC = adresybcc+","+firma.getKsiegowa();
            }
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresybcc));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Lista płac "+firma.getNazwa()+" za "+rok+"/"+mc;
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu lista płac dla firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "za okres</p>"
                    + "<p> "+rok+"/"+mc+"</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            byte[] zalacznikLista = listaplac.toByteArray();
            byte[] zalacznikRachunki = null;
            dolaczplik(zalacznikLista, mp, nazwapliku);
            if (rachunki!=null) {
                zalacznikRachunki = rachunki.toByteArray();
                dolaczplik(zalacznikRachunki, mp, nazwapliku);
            }
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mailAneksydoUmowy(FirmaKadry firma, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Aneksy do umowy "+firma.getNazwa()+" do podpisania ";
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu aneksy do umów z pracownikami dla firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "<p>Proszę wydrukować i dać pracownikom do podpisania.</p>"
                    + "<p>Jeden egzemplarz proszę odesłać do nas.</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mailUmowyZlecenia(FirmaKadry firma, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Umowy zlecenia "+firma.getNazwa()+" do podpisania ";
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu nowe umowy zlecenia/o dzieło ze zleceniobiorcami dla firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "<p>Proszę wydrukować i dać zleceniobiorcom do podpisania.</p>"
                    + "<p>Jeden egzemplarz proszę odesłać do nas.</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mailUmowyoPrace(FirmaKadry firma, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Umowy o pracę "+firma.getNazwa()+" do podpisania ";
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu nowe umowy o pracę z pracownikami dla firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "<p>Proszę wydrukować i dać pracownikom do podpisania.</p>"
                    + "<p>Jeden egzemplarz proszę odesłać do nas.</p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void mailRaportzImportu(FirmaKadry firma, String adres, SMTPSettings settings,SMTPSettings ogolne, byte[] zalacznik, String nazwapliku, String adresBCC)  {
        try {
            MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(settings, ogolne));
            message.setFrom(new InternetAddress(SMTPBean.adresFrom(settings, ogolne), SMTPBean.nazwaFirmyFrom(settings, ogolne)));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
            MimeBodyPart mbp1 = new MimeBodyPart();
            String temat = "Raport z importu pracowników firmy "+firma.getNazwa();
            message.setSubject(MimeUtility.encodeText(temat, "UTF-8", "Q"));
            String tresc = "Dzień dobry"
                    + "<p>W załączeniu plik z zapisem importu firmy firmy "+firma.getNazwa()+" NIP "+firma.getNip()
                    + "<p></p>"
                    + stopka;
            mbp1.setContent(tresc, "text/html; charset=utf-8");
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            dolaczplik(zalacznik, mp, nazwapliku);
            message.setContent(mp);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException ex) {
            // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void dolaczplik(byte[] is, Multipart mimeMultipart, String nazwapliku) {
        if (is != null && is instanceof byte[]) {
            try {
                // create the second message part with the attachment from a OutputStrean
                MimeBodyPart attachment= new MimeBodyPart();
                ByteArrayDataSource ds = new ByteArrayDataSource(is, "application/pdf");
                attachment.setDataHandler(new DataHandler(ds));
                attachment.setFileName(nazwapliku);
                mimeMultipart.addBodyPart(attachment);
            } catch (Exception ex) {
                // Logger.getLogger(MailAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }
    
    public static void mailPrzypomnienieoUmowie(String adres, SMTPSettings settings,SMTPSettings ogolne, String adresBCC, Umowa umowa) {
        try {
             MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(ogolne, ogolne));
             message.setSubject("Przypomnienie o wygasających umowach o pracę","UTF-8");
             message.setFrom(new InternetAddress(SMTPBean.adresFrom(ogolne, ogolne), SMTPBean.nazwaFirmyFrom(ogolne, ogolne)));
             message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Zbliża się termin zakończenia umowy z pracownikiem"
                     + "<p>"+umowa.getImieNazwisko()+ "</p>"
                     + "Umowa zawarta do dnia "+umowa.getDatado()+"</p>"
                     + "<p style=\"color: green;\">Proszę skontaktować się z naszym działem kadr celem jej ewentualnego przedłużenia</p>"
                     + "<p>Z poważaniem</p>"
                     + "<br/>"
                     + "<p>Biuro Rachunkowe Taxman</p>", "text/html; charset=utf-8");
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (Exception e) {
             throw new RuntimeException(e);
        }
    }
    
    public static void mailPrzypomnienieoA1(String adres, SMTPSettings settings,SMTPSettings ogolne, String adresBCC, Angaz angaz) {
        try {
             MimeMessage message = new MimeMessage(MailSetUp.otworzsesje(ogolne, ogolne));
             message.setSubject("Przypomnienie o wygasających umowach o pracę","UTF-8");
             message.setFrom(new InternetAddress(SMTPBean.adresFrom(ogolne, ogolne), SMTPBean.nazwaFirmyFrom(ogolne, ogolne)));
             message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adres));
            message.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(adresBCC));
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Zbliża się termin zakończenia ważności zaświadczenia A1 dla pracownika"
                     + "<p>"+angaz.getNazwiskoiImie()+ "</p>"
                     + "A1 ważne do dnia "+angaz.getDataa1()+"</p>"
                     + "<p style=\"color: green;\">Proszę skontaktować się z naszym działem kadr celem jej ewentualnego przedłużenia</p>"
                     + "<p>Z poważaniem</p>"
                     + "<br/>"
                     + "<p>Biuro Rachunkowe Taxman</p>", "text/html; charset=utf-8");
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (Exception e) {
             throw new RuntimeException(e);
        }
    }
    
}
