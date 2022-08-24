/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.UzDAO;
import data.Data;
import entity.Podatnik;
import entity.SMTPSettings;
import entity.Uz;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class MailPodatnik {
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private UzDAO uzDAO;
    
    
    public static void dodanonowegopodatnika(Podatnik podatnik, UzDAO uzDAO, SMTPSettings ogolne) {
        try {
             String data = Data.data_yyyyMMdd(podatnik.getDatawprowadzenia());
             String osoba = "osoba fizyczna";
             if (!podatnik.getGussymbol().equals("099")) {
                 osoba = "osoba prawna";
             }
             MailSetUp mailSetUp = new MailSetUp();
             MimeMessage message = mailSetUp.logintoUZAktywny(uzDAO, ogolne);
             if (podatnik.getKsiegowa()==null&podatnik.getKadrowa()==null) {
                try {
                    List<Uz> lista = uzDAO.findByUprawnieniaAktywny("Bookkeeper");
                    lista.addAll(uzDAO.findByUprawnieniaAktywny("BookkeeperFK"));
                    lista.addAll(uzDAO.findByUprawnieniaAktywny("HumanResources"));
                    String adresy = lista.stream().filter(p-> p.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                    //message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                    message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                } catch (MessagingException ex) {
                    // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                }
             } else if (podatnik.getKsiegowa()==null) {
                 try {
                    List<Uz> lista = uzDAO.findByUprawnieniaAktywny("Bookkeeper");
                    lista.addAll(uzDAO.findByUprawnieniaAktywny("BookkeeperFK"));
                    String adresy = lista.stream().filter(p-> p.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                    adresy = adresy+","+podatnik.getKadrowa().getEmail();
                    //message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                    message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                } catch (MessagingException ex) {
                    // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                }
             } else if (podatnik.getKadrowa()==null) {
                 try {
                    List<Uz> lista = uzDAO.findByUprawnieniaAktywny("HumanResources");
                    String adresy = lista.stream().filter(p-> p.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                    adresy = adresy+","+podatnik.getKsiegowa().getEmail();
                    //message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                    message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                } catch (MessagingException ex) {
                    // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                }
             } else {
                try {
                   String adresy = "";
                   adresy = adresy+","+podatnik.getKsiegowa().getEmail();
                   adresy = adresy+","+podatnik.getKadrowa().getEmail();
                   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                   message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
               } catch (MessagingException ex) {

               }
                   // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
            }
             message.setSubject("Taxman - informacja o nowym kliencie","UTF-8");
             // create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();
             mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
             mbp1.setContent("Dzień dobry "
                     + "<p>"+"Informuję, że dnia "+data+" przyjeliśmy nowego klienta: "+osoba+"</p>"
                     + "<p>o nazwie:</p>"
                     + "<p style=\"color: green;\">"+podatnik.getPrintnazwa()+" </p>"
                     + "<p>Należy zrobić dla niego dokumenty rejestracyjne i przyporządkować do osoby prowadzącej w księgowości i w kadrach.</p>"
                     + "<p>Obsługa nowego klienta jest możliwa przez profil Managera.</p>"
                     + "<br/>"
                     + "<p>Administrator Programu</p>", "text/html; charset=utf-8");
             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             
             // add the Multipart to the message
             message.setContent(mp);
             Transport.send(message);
             
         } catch (MessagingException e) {
             throw new RuntimeException(e);
         } 
        
    }
    
     @Schedule(dayOfWeek="1,3,5", hour = "10", persistent = false)
    public void sprawdznowych() {
        List<Podatnik> podatniki = podatnikDAO.findNowi();
        SMTPSettings ogolne = sMTPSettingsDAO.findSprawaByDef();
        if (podatniki!=null) {
            podatniki.stream().forEach(p -> {
                List<String> braki = zrobbraki(p);
                try {
                    String data = Data.data_yyyyMMdd(p.getDatawprowadzenia());
                    MailSetUp mailSetUp = new MailSetUp();
                    MimeMessage message = mailSetUp.logintoUZAktywny(uzDAO, ogolne);
                    if (p.getKsiegowa()==null&p.getKadrowa()==null) {
                        try {
                            List<Uz> lista = uzDAO.findByUprawnieniaAktywny("Bookkeeper");
                            lista.addAll(uzDAO.findByUprawnieniaAktywny("BookkeeperFK"));
                            lista.addAll(uzDAO.findByUprawnieniaAktywny("HumanResources"));
                            String adresy = lista.stream().filter(pa-> pa.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                            //message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                        } catch (MessagingException ex) {
                            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     } else if (p.getKsiegowa()==null) {
                         try {
                            List<Uz> lista = uzDAO.findByUprawnieniaAktywny("Bookkeeper");
                            lista.addAll(uzDAO.findByUprawnieniaAktywny("BookkeeperFK"));
                            String adresy = lista.stream().filter(pa-> pa.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                            adresy = adresy+","+p.getKadrowa().getEmail();
                            //message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                        } catch (MessagingException ex) {
                            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     } else if (p.getKadrowa()==null) {
                         try {
                            List<Uz> lista = uzDAO.findByUprawnieniaAktywny("HumanResources");
                            String adresy = lista.stream().filter(pa-> pa.getEmail()!=null).map(r->r.getEmail()).collect(Collectors.joining(","));
                            adresy = adresy+","+p.getKsiegowa().getEmail();
                            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                        } catch (MessagingException ex) {
                            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                     } else {
                         try {
                            String adresy = "";
                            adresy = adresy+","+p.getKsiegowa().getEmail();
                            adresy = adresy+","+p.getKadrowa().getEmail();
                            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("info@taxman.biz.pl"));
                            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(adresy));
                        } catch (MessagingException ex) {
                            
                        }
                            // Logger.getLogger(MailSetUp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    message.setSubject("Taxman - informacja o brakach u nowego klienta","UTF-8");
                    // create and fill the first message part
                    MimeBodyPart mbp1 = new MimeBodyPart();
                    mbp1.setHeader("Content-Type", "text/html; charset=utf-8");
                    mbp1.setContent("Dzień dobry "
                            + "<p>"+"Informuję, że dnia "+data+" przyjeliśmy nowego klienta </p>"
                            + "<p>o nazwie:</p>"
                            + "<p style=\"color: green;\">"+p.getPrintnazwa()+" </p>"
                            + "<p>Do tej pory nie załatwiono następujących spraw związanych z podatnikiem</p>"
                            + zamienbrakinaparagraf(braki)
                            + "<br/>"
                            + "<p>Administrator Programu</p>", "text/html; charset=utf-8");
                    // create the Multipart and add its parts to it
                    Multipart mp = new MimeMultipart();
                    mp.addBodyPart(mbp1);

                    // add the Multipart to the message
                    message.setContent(mp);
                    Transport.send(message);

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                } 
            });
        }
    }

    private static List<String> zrobbraki(Podatnik p) {
        List<String> braki = new ArrayList<>();
        int i = 1;
        if (p!=null) {
            if (p.getKsiegowa()==null) {
                braki.add("<span style=\"color: blue;\">nie przyporzadkowano osoby księgowej</span>");
            }
            if (p.getKadrowa()==null) {
                braki.add("<span style=\"color: blue;\">nie przyporzadkowano osoby kadrowej</span>");
            }
            if (p.isUmowalokal()==false) {
                braki.add(i+") umowa najmu na siedzibę/tytuł prawny");
                i++;
            }
            if (p.isVatr()==false) {
                braki.add(i+") VAT-R");
                i++;
            }
            if (p.isVatue()==false) {
                braki.add(i+") VAT-R UE");
                i++;
            }
            if (p.isOpisdzialalnosci()==false) {
                braki.add(i+") opis działalności");
                i++;
            }
            if (p.isKontobankowe()==false) {
                braki.add(i+") umowa rachunku bankowego");
                i++;
            }
            if (p.isPpo()==false) {
                braki.add(i+") pełnomocnictwo ogólne");
                i++;
            }
            if (p.isPel()==false) {
                braki.add(i+") pełnomocnictwo ZUS");
                i++;
            }
            if (p.isUmowa()==false) {
                braki.add(i+") umowa z biurem");
                i++;
            }
            if (p.isFaktura()==false) {
                braki.add(i+") faktura okresowa");
                i++;
            }
            if (p.isZua()==false) {
                braki.add(i+") zgłoszenie ZUA");
                i++;
            }
             if (p.isUpl()==false) {
                braki.add(i+") zgłoszenie UPL");
                i++;
            }
        }
        return braki;
    }

    private static String zamienbrakinaparagraf(List<String> braki) {
        String zwrot = "";
        zwrot = braki.stream().map(p->"<p>"+p+"</p>").collect(Collectors.joining());
        return zwrot;
    }
    
}
