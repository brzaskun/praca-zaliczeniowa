/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import beansMail.SMTPBean;
import dao.FakturaDAO;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import entity.SMTPSettings;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;
import msg.Msg;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class MailOther implements Serializable{
 
    
     
    public static void pkpir(WpisView wpisView, SMTPSettings ogolne) {
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
             message.setSubject("Wydruk podatkowej księgi przychodów i rozchodów za miesiąc","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
             String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk podatkowej księgi przychodów i rozchodów.</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
             // attach the file to the message
             File file = Plik.plik("pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf", true);
             if (file.isFile()) {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = ctx.getRealPath("/");
                FileDataSource fds = new FileDataSource(realPath+"wydruki/pkpir" + wpisView.getPodatnikWpisu().trim() + ".pdf");
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());

                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);

                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                Msg.msg("i","Wyslano maila z pkpir na wskazany adres: "+wpisView.getPodatnikObiekt().getEmail());
                file.delete();
             } else {
                Msg.msg("e", "Wystąpił błąd - brak wydrukowanej pkpir do załączenia");
             }
         } catch (MessagingException e) {
             Msg.msg("e", "Błąd podczas wysyłki pkpir. Wysyłka nieudana");
         }
     }
    
     public static void faktura(List<Faktura> fakturydomaila, WpisView wpisView, FakturaDAO fakturaDAO, String wiadomoscdodatkowa, String stopka, SMTPSettings settings, SMTPSettings ogolne) {
         Msg.msg("Rozpoczynam wysylanie maila z fakturą. Czekaj na wiadomość końcową");
         int i = 0;
         for (Faktura faktura : fakturydomaila){
             try {
                 Klienci klientf = faktura.getKontrahent();
                 if (klientf.getEmail()!=null && !klientf.getEmail().equals("")) {
                    String wiadomoscdowstawienia = wiadomoscdodatkowa != null? wiadomoscdodatkowa : "";
                    MimeMessage message = MailSetUp.logintoMailFakt(klientf, wpisView, settings, ogolne);
                    String nazwa = wpisView.getPodatnikObiekt().getNazwadlafaktury() != null ? wpisView.getPodatnikObiekt().getNazwadlafaktury() : wpisView.getPodatnikWpisu();
                    message.setSubject("Wydruk faktury VAT - "+SMTPBean.nazwaFirmyFrom(settings, ogolne),"UTF-8");
                    // create and fill the first message part
                    MimeBodyPart mbp1 = new MimeBodyPart();
                    mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
                    mbp1.setContent("Witam"
                        + "<p>W załączeniu bieżąca faktura automatycznie wygenerowana przez nasz program księgowy.</p>"
                        + "<p style=\"color: green;font-weight: bold;\">dla firmy "+klientf.getNpelna()+"</p>"
                        + "<p>za okres "+faktura.getRok()+"/"+faktura.getMc()+"</p>"
                        + "<span>"+wiadomoscdowstawienia+"</span><br/>"
                        + Mail.reklama
                        + stopka,  "text/html; charset=utf-8");

                    // create the second message part
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
                    // attach the file to the message
                    ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    String realPath = ctx.getRealPath("/");
                    int row = i;
                    if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                        String[] numer = faktura.getNumerkolejny().split("/");
                        row = Integer.parseInt(numer[0]);
                    }
                    FileDataSource fds = new FileDataSource(realPath+"wydruki/fakturaNr" + String.valueOf(row) + "firma"+ wpisView.getPodatnikObiekt().getNip() + ".pdf");
                    mbp2.setDataHandler(new DataHandler(fds));
                    mbp2.setFileName(fds.getName());

                    // create the Multipart and add its parts to it
                    Multipart mp = new MimeMultipart();
                    mp.addBodyPart(mbp1);
                    mp.addBodyPart(mbp2);

                    // add the Multipart to the message
                    message.setContent(mp);
                    Transport.send(message);
                    Msg.msg("i","Wysłano maila do klienta "+klientf.getNpelna());
                    faktura.setWyslana(true);
                    faktura.setDatawysylki(new Date());
                    PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
                    try {
                       File file = Plik.plik("fakturaNr" + String.valueOf(i) + "firma"+ wpisView.getPodatnikObiekt().getNip() + ".pdf", true);
                       file.delete();
                    } catch (Exception ef) {
                        Msg.msg("e", "Nieudane usunięcie pliku faktury");
                    }
                 }
             } catch (AuthenticationFailedException e1) {
                 Msg.msg("e", "Błąd logowania do konta pocztowego. Sprawdź login i hasło!");
                 throw new RuntimeException(e1);
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
             i++;
         }
         if (fakturydomaila != null) {
             fakturaDAO.editList(fakturydomaila);
         }
     }
     
     public static void fakturaarchiwum(List<Faktura> fakturydomaila, WpisView wpisView, FakturaDAO fakturaDAO, String wiadomoscdodatkowa, SMTPSettings settings, SMTPSettings ogolne) {
         Msg.msg("Rozpoczynam wysylanie maila z fakturą. Czekaj na wiadomość końcową");
         int i = 0;
         for (Faktura faktura : fakturydomaila){
             try {
                 String wiadomoscdowstawienia = wiadomoscdodatkowa != null? wiadomoscdodatkowa : "";
                 Klienci klientf = faktura.getKontrahent();
                 MimeMessage message = MailSetUp.logintoMailFakt(klientf, wpisView, settings, ogolne);
                 message.setSubject("Wydruk faktury VAT - Biuro Rachunkowe Taxman","UTF-8");
                 // create and fill the first message part
                 MimeBodyPart mbp1 = new MimeBodyPart();
                 mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
                 Podatnik pod = wpisView.getPodatnikObiekt();
                 String klient = pod.getImie()+" "+pod.getNazwisko();
                 mbp1.setContent("Szanowna/y "+klient
                     + "<p>W załączeniu bieżąca faktura automatycznie wygenerowana przez nasz program księgowy.</p>"
                     + "<p>"+wiadomoscdowstawienia+"</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");
             
                 // create the second message part
                 MimeBodyPart mbp2 = new MimeBodyPart();
                 mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
                 // attach the file to the message
                 ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                int row = i;
                  if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                      String[] numer = faktura.getNumerkolejny().split("/");
                      row = Integer.parseInt(numer[0]);
                  }
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/faktura"+String.valueOf(row) + wpisView.getPodatnikObiekt().getNip() + ".pdf");
                 mbp2.setDataHandler(new DataHandler(fds));
                 mbp2.setFileName(fds.getName());
                 
                 // create the Multipart and add its parts to it
                 Multipart mp = new MimeMultipart();
                 mp.addBodyPart(mbp1);
                 mp.addBodyPart(mbp2);
                 
                 // add the Multipart to the message
                 message.setContent(mp);
                 Transport.send(message);
                 Msg.msg("i","Wysłano maila do klienta "+klientf.getNpelna());
                 faktura.setWyslana(true);
                 PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
                 try {
                    File file = Plik.plik("faktura"+String.valueOf(i) + wpisView.getPodatnikWpisu() + ".pdf", true);
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku faktury");
                 }
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
             i++;
         }
         if (fakturydomaila != null) {
             fakturaDAO.edit(fakturydomaila);
         }
         Msg.msg("Wysłano ponownie fakture mailem do kontrahenta");
     }
     
     
     
     
     public static void pit5(WpisView wpisView, SMTPSettings ogolne) {     
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
             message.setSubject("Wydruk deklaracji PIT za miesiąc","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>"+"W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk deklaracji podatkowej w podatku dochodowym PIT5</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
             // attach the file to the message
             ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            FileDataSource fds = new FileDataSource(realPath+"wydruki/pit5" + wpisView.getPodatnikWpisu() + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             try {
                    File file = Plik.plik("pit5" + wpisView.getPodatnikWpisu() + ".pdf", true);
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
    
     public static void obroty(WpisView wpisView, SMTPSettings ogolne) {
         try {
             MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
             message.setSubject("Wydruk obrotów z kontrahentem","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             Podatnik pod = wpisView.getPodatnikObiekt();
             String klient = pod.getImie()+" "+pod.getNazwisko();
             mbp1.setContent("Szanowna/y "+klient
                     + "<p>W niniejszym mailu znajdziesz zamówione przez Ciebie zestawienie obrotów z kontrahentem</p>"
                     + Mail.reklama
                     + Mail.stopka,  "text/html; charset=utf-8");             
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
              mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
             // attach the file to the message
             ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/obroty" + wpisView.getPodatnikWpisu() + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
              try {
                    File file = Plik.plik("obroty" + wpisView.getPodatnikWpisu() + ".pdf", true);
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
     
      public static void ewidencjaSTR(WpisView wpisView, SMTPSettings ogolne) {
          try {
              MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
              message.setSubject("Wydruk ewidencji środków trwałych","UTF-8");
              // create and fill the first message part
              MimeBodyPart mbp1 = new MimeBodyPart();
              mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
              Podatnik pod = wpisView.getPodatnikObiekt();
              String klient = pod.getImie()+" "+pod.getNazwisko();
              mbp1.setContent("Szanowna/y "+klient
                      + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk aktualnej ewidencji środków trwałych</p>"
                      + Mail.reklama
                      + Mail.stopka,  "text/html; charset=utf-8");  
              
              // create the second message part
              MimeBodyPart mbp2 = new MimeBodyPart();
               mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
              // attach the file to the message
              ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/srodki" + wpisView.getPodatnikWpisu() + ".pdf");
              mbp2.setDataHandler(new DataHandler(fds));
              mbp2.setFileName(fds.getName());
              
              // create the Multipart and add its parts to it
              Multipart mp = new MimeMultipart();
              mp.addBodyPart(mbp1);
              mp.addBodyPart(mbp2);
              
              // add the Multipart to the message
              message.setContent(mp);
              Transport.send(message);
               try {
                    File file = Plik.plik("srodki" + wpisView.getPodatnikWpisu() + ".pdf", true);
                    file.delete();
                 } catch (Exception ef) {
                     Msg.msg("e", "Nieudane usunięcie pliku");
                 }
          } catch (MessagingException e) {
              throw new RuntimeException(e);
          }
      }
      
      
      
      
    
    public static void vat7(int row, WpisView wpisView, int stara0nowa1, SMTPSettings ogolne) {
        try {
            MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
            message.setSubject("Wydruk dekalracji VAT-7","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
            mbp1.setContent("Szanowna/y "+klient
                    + "<p>W niniejszym mailu znajdziesz deklarację VAT-7 złożoną w Twoim imieniu w ostatnim okresie rozliczeniowym.</p>"
                    + Mail.reklama
                    + Mail.stopka,  "text/html; charset=utf-8");  

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
             mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            // attach the file to the message
            if (stara0nowa1 == 0 && Plik.plik("vat7-13" + wpisView.getPodatnikWpisu() + ".pdf", true).isFile()) {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/vat7-13" + wpisView.getPodatnikWpisu() + ".pdf");
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                 // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);
                
                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                Msg.msg("i", "Wyslano maila z deklaracją VAT-7 do klienta "+klient+". Na adres mail: "+pod.getEmail());
                File f  = Plik.plik("vat7-13" + wpisView.getPodatnikWpisu() + ".pdf", true);
                f.delete();
                PrimeFaces.current().executeScript("schowajmailbutton("+row+");");
            } else if (stara0nowa1 == 1 && Plik.plik(wpisView.getPodatnikObiekt().getNip()+"vat7" + ".pdf", true).isFile()) {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/" + wpisView.getPodatnikObiekt().getNip()+"vat7" + ".pdf");
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                  // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);
                
                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                Msg.msg("i", "Wyslano maila z deklaracją VAT-7 do klienta "+klient+". Na adres mail: "+pod.getEmail());
                File f  = Plik.plik("vat7" + wpisView.getPodatnikWpisu() + ".pdf", true);
                f.delete();
                PrimeFaces.current().executeScript("schowajmailbutton("+row+");");
            } else {
                Msg.msg("e", "Brak wygenerowanej wcześniej deklaracji VAT. Nie wysłano maila do klienta. Kliknij najpierw na przycisk Pdf właściwej deklaracji VAT.");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public static void vatewidencja(WpisView wpisView, String nazwaewidencji, SMTPSettings ogolne) {
        try {
            MimeMessage message = MailSetUp.logintoMail(wpisView, null, ogolne);
            message.setSubject("Wydruk bieżącej ewidencji VAT  za miesiąc","UTF-8");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
            Podatnik pod = wpisView.getPodatnikObiekt();
            String klient = pod.getImie()+" "+pod.getNazwisko();
            mbp1.setContent("Szanowna/y "+klient
                    + "<p>W niniejszym mailu znajdziesz zamówiony przez Ciebie wydruk ewidencji VAT "+nazwaewidencji+"</p>"
                    + Mail.reklama
                    + Mail.stopka,  "text/html; charset=utf-8");

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
             mbp2.setHeader("Content-Type", "application/pdf;charset=UTF-8");
            // attach the file to the message
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                 String realPath = ctx.getRealPath("/");
                 FileDataSource fds = new FileDataSource(realPath+"wydruki/vat-" + nazwaewidencji + "-" + wpisView.getPodatnikObiekt().getNip() + ".pdf");
            if (fds.getFile().exists()) {
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());

                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);

                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                  try {
                        File file = Plik.plik("vat-" + nazwaewidencji + "-" + wpisView.getPodatnikObiekt().getNip() + ".pdf", true);
                        file.delete();
                     } catch (Exception ef) {
                         Msg.msg("e", "Nieudane usunięcie pliku");
                     }
            } else {
                Msg.msg("e", "Nieudane pobranie ewidencji do wysyłki pliku");
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

        
}
