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
import entity.VatSuper;
import entity.VatUe;
import error.E;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Osito
 */
public class VIESCheckBean {
    
    public static void sprawdz(List klienciWDTWNT, ViesDAO viesDAO, Podatnik podatnik, Uz wprowadzil)  {
         if (klienciWDTWNT != null) {
            List<Vies> viesy = new ArrayList<>();
             for (Iterator it = klienciWDTWNT.iterator(); it.hasNext();) {
                 VatUe p = (VatUe) it.next();
                 if (p.getKontrahent() != null && p.getVies() == null) {
                     String kraj = p.getKontrahent().getKrajkod();
                     String nip = p.getKontrahent().getNip();
                     boolean jestprefix = sprawdznip(p.getKontrahent().getNip());
                     if (jestprefix) {
                         nip = p.getKontrahent().getNip().substring(2);
                     }
                     Vies v = null;
                     try {
                         v = VIESCheckBean.pobierz(kraj, nip, p.getKontrahent(), podatnik, wprowadzil);
                         p.setVies(v);
                         v.setVatue(p);
                     } catch (SocketTimeoutException se) {
                         E.e(se);
                     }
                     if (v != null) {
                         viesy.add(v);
                     }
                 }
             }
        }
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }
    
    private static Vies pobierz(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) throws SocketTimeoutException {
        Vies zwrot = new Vies();
        if (kraj.equals("ES")) {
        }
        try {
            Connection.Response res = null;
            if (kraj.equals("ES")) {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                .data("memberStateCode", kraj, "number", nip, "traderName",k.getNpelna(),"traderCompanyType","","traderStreet",k.getUlica(),"traderPostalCode",k.getKodpocztowy(),"traderCity",k.getMiejscowosc(),"requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                .method(Method.POST)
                .execute();
            } else {
            res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                    .data("memberStateCode", kraj, "number", nip, "requesterMemberStateCode", "PL", "requesterNumber", podatnik.getNip())
                    .method(Method.POST)
                    .execute();
            }
            Document doc = res.parse();
            Element table = doc.getElementById("vatResponseFormTable");
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (table != null) {
                Elements tds = table.getElementsByTag("td");
                String data = tds.get(8).text() != null ? tds.get(8).text().substring(0,10) : "";
                if (data.contains("/")) {
                    data.replace("/", "-");
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
                        Date datawystawienia = formatter.parse(tds.get(8).text());
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
                        Date datawystawienia = formatter.parse(tds.get(8).text());
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
                zwrot =  new Vies();
            }
        } catch (IOException ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
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
            //System.out.println(new Date(tds.get(8).text()).toString());
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
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) {
//        sprawdz();
//    }
    

    
}
