/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies;

import daoFK.ViesDAO;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import entity.VatUe;
import error.E;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.xml.ws.Holder;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;
import viesapi.Vies2;
import viesapi.ViesVatRegistration;
import viesapi.ViesVatServiceException;

/**
 *
 * @author Osito
 */
public class VIESCheckBean {
    
    public static boolean sprawdz(List klienciWDTWNT, ViesDAO viesDAO, Podatnik podatnik, Uz wprowadzil)  {
        boolean zwrot = true;
        if (klienciWDTWNT != null) {
            List<Vies> viesy = Collections.synchronizedList(new ArrayList<>());
             for (Iterator it = klienciWDTWNT.iterator(); it.hasNext();) {
                 VatUe p = (VatUe) it.next();
                 if (p.getKontrahent() != null && p.getVies() == null) {
                     String kraj = p.getKontrahent().getKrajkod().trim();
                     String nip = p.getKontrahent().getNip().trim();
                     if (nip.equals("ESB65448870")) {
                         error.E.s("ESB65448870");
                     }
                     boolean jestprefix = sprawdznip(p.getKontrahent().getNip());
                     if (jestprefix) {
                         nip = p.getKontrahent().getNip().substring(2).trim();
                     }
                     Vies v = null;
                     try {
                         v = VIESCheckBean.pobierzAPI(kraj, nip, p.getKontrahent(), podatnik, wprowadzil);
                         p.setVies(v);
                         v.setVatue(p);
                     } catch (SocketTimeoutException se) {
                         zwrot = false;
                         E.e(se);
                         break;
                     } catch (Exception e) {
                         E.e(e);
                         zwrot = false;
                     }
                     if (v != null) {
                         viesy.add(v);
                     }
                 }
             }
        }
        return zwrot;
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        int ile = 2;
        String pr = nip.substring(0, 2);
        if (pr.equals("ES")|| pr.equals("AT")) {
            ile = 3;
        }
        String prefix = nip.substring(0, ile);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }
    
    private static Vies pobierzAPI(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        try {
            javax.xml.ws.Holder<java.lang.String> countryCode = new Holder<>(kraj);
            javax.xml.ws.Holder<java.lang.String> vatNumber = new Holder<>(nip);
            ViesVatRegistration table = Vies2.checkVat(countryCode, vatNumber);
            if (table != null) {
                zwrot.setPodatnik(podatnik);
                zwrot.setData(table.getRequestDate());
                zwrot.setWynik(true);
                zwrot.setKraj(kraj);
                zwrot.setNIP(nip);
                zwrot.setNazwafirmy(table.getName());
                zwrot.setAdresfirmy(table.getAddress());
                zwrot.setIdentyfikatorsprawdzenia(null);
                zwrot.setWprowadzil(wprowadzil);
                zwrot.setUwagi(null);
            } else {
                zwrot =  null;
            }
        } catch (ViesVatServiceException ex) {
            zwrot.setUwagi(ex.getErrorKey());
        }
        return zwrot;
    }
    
    private static Vies pobierz(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        if (kraj.equals("ES")) {
        }
        try {
            Connection.Response res = null;
            if (kraj.equals("ES")) {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                .data("memberStateCode", kraj, "number", nip, "traderName",k.getNpelna(),"traderCompanyType","","traderStreet",k.getUlica(),"traderPostalCode",k.getKodpocztowy(),"traderCity",k.getMiejscowosc())
                .method(Method.POST)
                .execute();
            } else {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", kraj, "number", nip)
                    .method(Method.POST)
                    .execute();
            }
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (table != null) {
                Elements tds = table.getElementsByTag("td");
                String data = tds.get(8).text() != null ? tds.get(8).text() : "";
                if (data.contains("/")) {
                    data = data.replace("/", "-");
                }
                if (tds != null && tds.size() < 26) {
                    if (tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    } else if (tds.get(0).text().contains("Member State service unavailable. Please re-submit your request later.")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(false);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi("sna");
                    }
                } else if (tds != null) {
                    if (tds != null && tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        Date datawystawienia = formatter.parse(data);
                        zwrot.setData(datawystawienia);
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        String adres = tds.get(16).text()+" "+tds.get(19).text()+" "+tds.get(22).text();
                        zwrot.setAdresfirmy(adres);
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(25).text());
                        zwrot.setWprowadzil(wprowadzil);
                        zwrot.setUwagi(null);
                    }
                }
            } else {
                zwrot =  null;
            }
        } catch (IOException ex) {
            zwrot = null;
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            zwrot = null;
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }
    
    public static void sprawdz() {
        try {
            Connection.Response res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", "PL", "number", "8511005008", "requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                    .method(Method.POST)
                    .execute();
            
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            Elements tds = table.getElementsByTag("td");
            boolean znalazl = false;
            //error.E.s(new Date(tds.get(8).text()).toString());
            for (Element link : tds) {
                String linkText = link.text();
                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
                    znalazl = true;
                    if (!linkText.equals("")) {
                    }
                } else {
                    break;
                }
            }
        } catch (IOException ex) {
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        try {
            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36";
            Connection.Response res = Jsoup.connect("https://translate.google.pl/") 
                    .data()
                    .method(Method.GET)
                    .userAgent(USER_AGENT)
                    .execute();
            
            FormElement loginForm = (FormElement)res.parse().select("#gt-form").first();

            // ## ... then "type" the username ...
            Element loginField = loginForm.select("#source").first();
            loginField.val("kotek");
            Element gtsl = loginForm.select("#gt-sl").first();
            gtsl.val("polski");
            Element gttl = loginForm.select("#gt-tl").first();
            gttl.val("angielski");
            
//
//            // ## ... and "type" the password
//            Element passwordField = loginForm.select("#password").first();
//            checkElement("Password Field", passwordField);
//            passwordField.val(PASSWORD);        
//
//
//            // # Now send the form for login
            Connection.Response loginActionResponse = loginForm.submit()
                    .data("source","kotek","gt-sl","polski","gt-tl","angielski")
                    .method(Method.POST)
                    .cookies(res.cookies())
                    .userAgent(USER_AGENT)  
                    .execute();


            error.E.s(loginActionResponse.parse().html());
            error.E.s("");
            Document doc = res.parse();
            
            List<Element> tab = doc.getAllElements();
            Element table = doc.getElementById("source");
            Elements tds = table.getElementsByTag("td");
//            boolean znalazl = false;
//            //error.E.s(new Date(tds.get(8).text()).toString());
//            for (Element link : tds) {
//                String linkText = link.text();
//                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
//                    znalazl = true;
//                    if (!linkText.equals("")) {
//                    }
//                } else {
//                    break;
//                }
//            }
        } catch (IOException ex) {
            // Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
