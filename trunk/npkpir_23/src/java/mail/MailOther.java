/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.Serializable;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class MailOther extends MailSetUp implements Serializable{
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
            System.out.println("Wyslano maila z pkpir do klienta");

              } catch (MessagingException e) {
                  throw new RuntimeException(e);
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
     
      public void vat7() {
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
            FileDataSource fds = new FileDataSource("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/vat/vat7-13" + klientfile + ".pdf");
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);
            Transport.send(message);
            System.out.println("Wyslano maila z dekalracją VAT-7 do klienta");

              } catch (MessagingException e) {
                  throw new RuntimeException(e);
              }
}
}
