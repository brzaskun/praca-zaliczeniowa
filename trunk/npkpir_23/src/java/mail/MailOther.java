/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import dao.FakturaDAO;
import entity.Faktura;
import entity.Klienci;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.FakturaView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class MailOther extends MailSetUp implements Serializable{
    public static String nazwaewidencji;
    
    //bo musze odnotowac ze jest wyslana
    @Inject private FakturaDAO fakturaDAO;
    
    
     private String wiadomoscdodatkowa;
     
     public void pkpir() {
         try {
             Message message = logintoMail();
             message.setSubject("Wydruk podatkowej księgi przychodów i rozchodów za miesiąc");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setText("Szanowny "+klient
                     + "\n\n"+"W niniejszym mailu znajdziesz"
                     + "\nzamówiony przez Ciebie wydruk podatkowej księgi przychodów i rozchodów"
                     + "\n\nZ poważaniem"
                     + "\n\n"+wysylajacy
                     + "\nBiuro Rachunkowe Taxman"
                     + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                     + "\ntel. 91 8120976");
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pkpir" + klientfile + ".pdf");
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
             
         } catch (MessagingException e) {
             Msg.msg("e", "Klient nie ma wprowadzonego adresu mail. Wysyłka nieudana");
         }
     }
     public void faktura() {
         try {
             pdfFaktura.drukujmail();
         } catch (Exception el){}
         List<Faktura> fakturydomaila = FakturaView.getGosciwybralS();
         int i = 0;
         for (Faktura faktura : fakturydomaila){
             try {
                 
                 Klienci klientf = faktura.getKontrahent();
                 Message message = logintoMail(faktura.getKontrahent().getEmail());
                 message.setSubject("Wydruk faktury VAT - Biuro Rachunkowe Taxman");
                 // create and fill the first message part
                 MimeBodyPart mbp1 = new MimeBodyPart();
                 mbp1.setText("Witam"
                         + "\n\n"+"W załączeniu bieżąca faktura automatycznie wygenerowana przez nasz program księgowy."
                         + "\nAdres mailowy, z którego została wysłana nie służy do normalnej korespondencji."
                         + "\n\n" + wiadomoscdodatkowa
                         + "\n\nZ poważaniem"
                         + "\n\n"+podpisfaktury
                         + "\nBiuro Rachunkowe Taxman"
                         + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                         + "\ntel. 91 8120976");
                 
                 // create the second message part
                 MimeBodyPart mbp2 = new MimeBodyPart();
                 // attach the file to the message
                 FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura"+String.valueOf(i) + klientfile + ".pdf");
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
                 fakturaDAO.edit(faktura);
                 RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
             } catch (MessagingException e) {
                 throw new RuntimeException(e);
             }
             i++;
         }
     }
     
     public void oznaczonejakowyslane() {
         List<Faktura> fakturydomaila = FakturaView.getGosciwybralS();
         for (Faktura faktura : fakturydomaila){
             Klienci klientf = faktura.getKontrahent();
             Msg.msg("i","Oznaczono fakturę jako wysłaną do klienta "+klientf.getNpelna());
             faktura.setWyslana(true);
             fakturaDAO.edit(faktura);
         }
     }
     
     public void oznaczonejakozaksiegowane() {
         List<Faktura> fakturydomaila = FakturaView.getGosciwybralS();
         for (Faktura faktura : fakturydomaila){
             Klienci klientf = faktura.getKontrahent();
             Msg.msg("i","Oznaczono fakturę jako zaksięgowaną "+klientf.getNpelna());
             faktura.setZaksiegowana(true);
             fakturaDAO.edit(faktura);
         }
     }
     
     
     
     public void pit5() {     
         try {
             Message message = logintoMail();
             message.setSubject("Wydruk deklaracji PIT za miesiąc");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setText("Szanowny "+klient
                     + "\n\n"+"W niniejszym mailu znajdziesz"
                     + "\nzamówiony przez Ciebie wydruk deklaracji podatkowej w podatku dochodowym PIT5"
                     + "\n\nZ poważaniem"
                     + "\n\n"+wysylajacy
                     + "\nBiuro Rachunkowe Taxman"
                     + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                     + "\ntel. 91 8120976");
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pit5" + klientfile + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
    
     public void obroty() {
         try {
             Message message = logintoMail();
             message.setSubject("Wydruk obrotów z kontrahentem");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setText("Szanowna/y "+klient
                     + "\n\n"+"W niniejszym mailu znajdziesz"
                     + "\nzamówione przez Ciebie zestawienie obrotów z kontrahentem"
                     + "\n\nZ poważaniem"
                     + "\n\n"+wysylajacy
                     + "\nBiuro Rachunkowe Taxman"
                     + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                     + "\ntel. 91 8120976");
             
             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();
             // attach the file to the message
             FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/obroty" + klientfile + ".pdf");
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         }
     }
     
      public void ewidencjaSTR() {
          try {
              Message message = logintoMail();
              message.setSubject("Wydruk ewidencji środków trwałych");
              // create and fill the first message part
              MimeBodyPart mbp1 = new MimeBodyPart();
              mbp1.setText("Szanowny "+klient
                      + "\n\n"+"W niniejszym mailu znajdziesz"
                      + "\nzamówiony przez Ciebie wydruk aktualnej ewidencji środków trwałych"
                      + "\n\nZ poważaniem"
                      + "\n\n"+wysylajacy
                      + "\nBiuro Rachunkowe Taxman"
                      + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                      + "\ntel. 91 8120976");
              
              // create the second message part
              MimeBodyPart mbp2 = new MimeBodyPart();
              // attach the file to the message
              FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/srodki" + klientfile + ".pdf");
              mbp2.setDataHandler(new DataHandler(fds));
              mbp2.setFileName(fds.getName());
              
              // create the Multipart and add its parts to it
              Multipart mp = new MimeMultipart();
              mp.addBodyPart(mbp1);
              mp.addBodyPart(mbp2);
              
              // add the Multipart to the message
              message.setContent(mp);
              Transport.send(message);
              
          } catch (MessagingException e) {
              throw new RuntimeException(e);
          }
      }
      
      
      
      
    
    public void vat7(int row) {
        try {
            Message message = logintoMail();
            message.setSubject("Wydruk dekalracji VAT-7");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText("Szanowny "+klient
                    + "\n\n"+"W niniejszym mailu znajdziesz"
                    + "\ndeklarację VAT-7 złożoną w Twoim imieniu w ostatnim okresie rozliczeniowym."
                    + "\n\nZ poważaniem"
                    + "\n\n"+wysylajacy
                    + "\nBiuro Rachunkowe Taxman"
                    + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                    + "\ntel. 91 8120976");

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            if (new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + klientfile + ".pdf").isFile()) {
                FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + klientfile + ".pdf");
                mbp2.setDataHandler(new DataHandler(fds));
                mbp2.setFileName(fds.getName());
                
                // create the Multipart and add its parts to it
                Multipart mp = new MimeMultipart();
                mp.addBodyPart(mbp1);
                mp.addBodyPart(mbp2);
                
                // add the Multipart to the message
                message.setContent(mp);
                Transport.send(message);
                Msg.msg("i", "Wyslano maila z deklaracją VAT-7 do klienta "+klient);
                File f  = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + klientfile + ".pdf");
                f.delete();
                RequestContext.getCurrentInstance().execute("schowajmailbutton("+row+");");
            } else {
                Msg.msg("e", "Brak wygenerowanej wcześniej deklaracji VAT. Nie wysłano maila do klienta. Kliknij najpierw na przycisk Pdf właściwej deklaracji VAT.");
            }
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void ustawNazwaewidencji(String nazwa) {
        String nowanazwa;
        if (nazwa.contains("sprzedaż")) {
            nowanazwa = nazwa.substring(0, nazwa.length() - 1);
        } else {
            nowanazwa = nazwa;
        }
        MailOther.nazwaewidencji = nowanazwa;
    }
    
    public void vatewidencja() {
        try {
            Message message = logintoMail();
            message.setSubject("Wydruk bieżącej ewidencji VAT  za miesiąc");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText("Szanowny "+klient
                    + "\n\n"+"W niniejszym mailu znajdziesz"
                    + "\nzamówiony przez Ciebie wydruk ewidencji VAT "+nazwaewidencji
                    + "\n\nZ poważaniem"
                    + "\n\n"+wysylajacy
                    + "\nBiuro Rachunkowe Taxman"
                    + "\nSzczecin, ul. Gen.Dąbrowskiego 38/40 l.313"
                    + "\ntel. 91 8120976");

            // create the second message part
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat-" + nazwaewidencji + "-" + wpisView.getPodatnikWpisu() + ".pdf");
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);
            Transport.send(message);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNazwaewidencji() {
        return nazwaewidencji;
    }

    public void setNazwaewidencji(String nazwaewidencji) {
        MailOther.nazwaewidencji = nazwaewidencji;
    }

    public String getKlientfile() {
        return klientfile;
    }

    public void setKlientfile(String klientfile) {
        this.klientfile = klientfile;
    }

    public String getWiadomoscdodatkowa() {
        return wiadomoscdodatkowa;
    }

    public void setWiadomoscdodatkowa(String wiadomoscdodatkowa) {
        this.wiadomoscdodatkowa = wiadomoscdodatkowa;
    }

  
    
      
      
}
