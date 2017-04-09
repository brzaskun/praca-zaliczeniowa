/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vies;

import daoFK.ViesDAO;
import embeddable.VatUe;
import entity.Klienci;
import entity.Podatnik;
import entity.Uz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public static void sprawdz(List<VatUe> klienciWDTWNT, ViesDAO viesDAO, Podatnik podatnik, Uz wprowadzil) {
         if (klienciWDTWNT != null) {
            List<Vies> viesy = new ArrayList<>();
            for (VatUe p : klienciWDTWNT) {
                if (p.getKontrahent() != null && p.getVies() == null) {
                    String kraj = p.getKontrahent().getNip().substring(0,2);
                    String nip = p.getKontrahent().getNip().substring(2);
                    Vies v = VIESCheckBean.pobierz(kraj, nip, p.getKontrahent(), podatnik, wprowadzil);
                    p.setVies(v);
                    if (v != null) {
                        viesy.add(v);
                    }
                }
            }
            if (viesy.size() > 0) {
                viesDAO.editList(viesy);
            }
        }
    }
    
    private static Vies pobierz(String kraj, String nip, Klienci k, Podatnik podatnik, Uz wprowadzil) {
        Vies zwrot = new Vies();
        if (kraj.equals("ES")) {
            System.out.println("");
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
            if (table != null) {
                Elements tds = table.getElementsByTag("td");
                if (tds != null && tds.size() < 26) {
                    if (tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        zwrot.setData(new Date(tds.get(8).text()));
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        zwrot.setAdresfirmy(tds.get(12).text());
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(14).text());
                        zwrot.setWprowadzil(wprowadzil);
                    }
                } else if (tds != null) {
                    if (tds != null && tds.get(0).text().contains("Yes, valid VAT number")) {
                        zwrot.setPodatnik(podatnik);
                        zwrot.setData(new Date(tds.get(8).text()));
                        zwrot.setWynik(true);
                        zwrot.setKraj(kraj);
                        zwrot.setNIP(nip);
                        zwrot.setNazwafirmy(tds.get(10).text());
                        String adres = tds.get(16).text()+" "+tds.get(19).text()+" "+tds.get(22).text();
                        zwrot.setAdresfirmy(adres);
                        zwrot.setIdentyfikatorsprawdzenia(tds.get(25).text());
                        zwrot.setWprowadzil(wprowadzil);
                    }
                }
            } else {
                zwrot = null;
            }
        } catch (IOException ex) {
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
            System.out.println(new Date(tds.get(8).text()).toString());
            for (Element link : tds) {
                String linkText = link.text();
                if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
                    znalazl = true;
                    if (!linkText.equals("")) {
                        System.out.println(linkText);
                    }
                } else {
                    System.out.println("Nie znalazl");
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(VIESCheckBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        sprawdz();
    }
    
}
