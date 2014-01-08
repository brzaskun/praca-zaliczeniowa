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
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfFaktura;
import view.FakturaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class MailOther extends MailSetUp implements Serializable{
    
    //bo musze odnotowac ze jest wyslana
    @Inject private FakturaDAO fakturaDAO;
    
    
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
                    + "\n\n"+wysylajacy);

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
                    + "\n\nPonieważ program jest jeszcze w fazie testowania, może się zdarzyć, że dokument będzie zawierał błędy."
                    + "\nProsimy o wyrozumiałość i o informację zwrotną w takim wypadku na adres: info@taxman.biz.pl."
                    + "\n\nZ poważaniem"
                    + "\n\n"+podpisfaktury
                    + "\n\n"+firmafaktury);

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
            System.out.println("Wyslano maila z fakturą do klienta");
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
                    + "\n\n"+wysylajacy);

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
            System.out.println("Wyslano maila z PIT5 do klienta");

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
                    + "\n\n"+wysylajacy);

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
            System.out.println("Wyslano maila z obrotami do klienta");

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
                    + "\n\n"+wysylajacy);

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
            System.out.println("Wyslano maila z obrotami do klienta");

              } catch (MessagingException e) {
                  throw new RuntimeException(e);
              }
}
     
      public void vat7(int index) {
       try {
            Message message = logintoMail();
            message.setSubject("Wydruk dekalracji VAT-7");
            // create and fill the first message part
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText("Szanowny "+klient
                    + "\n\n"+"W niniejszym mailu znajdziesz"
                    + "\ndeklarację VAT-7 złożoną w Twoim imieniu w ostatnim okresie rozliczeniowym."
                    + "\n\nZ poważaniem"
                    + "\n\n"+wysylajacy);

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
            RequestContext.getCurrentInstance().execute("schowajmailbutton();");
            File f  = new File("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vat7-13" + klientfile + ".pdf");
            f.delete();
            RequestContext.getCurrentInstance().execute("schowajmailbutton();");
            } else {
                Msg.msg("e", "Brak wygenerowanej wcześniej deklaracji VAT. Nie wysłano maila do klienta. Kliknij najpierw na przycisk Pdf właściwej deklaracji VAT.");
            }

              } catch (MessagingException e) {
                  throw new RuntimeException(e);
              }
}

    public String getKlientfile() {
        return klientfile;
    }

    public void setKlientfile(String klientfile) {
        this.klientfile = klientfile;
    }
      
      
}
