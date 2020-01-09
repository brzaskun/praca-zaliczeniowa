/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkfa3;

import jpkfa3.*;
import beansFK.TabelaNBPBean;
import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.KlienciDAO;
import daoFK.TabelanbpDAO;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Tabelanbp;
import error.E;
import gus.GUSView;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class Beanjpk {
    
    public static Dok generujdok(Object p, String waldok, List<Evewidencja> evewidencje, TabelanbpDAO tabelanbpDAO, Tabelanbp tabeladomyslna, List<Klienci> klienci, boolean wybierzosobyfizyczne, 
            boolean deklaracjaniemiecka, KlienciDAO klDAO, GUSView gUSView, Podatnik podatnik, DokDAO dokDAO, Rodzajedok rodzajedok, boolean pol0de1) {
        jpkfa3.JPK.Faktura faktura = (jpkfa3.JPK.Faktura) p;
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = faktura.getP1().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(podatnik);
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(faktura.getP1().toString());
            selDokument.setDataSprz(faktura.getP6().toString());
            selDokument.setKontr(pobierzkontrahenta(faktura, pobierzNIPkontrahenta(faktura), klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, gUSView));
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(faktura.getP2A());
            Tabelanbp innatabela = beansDok.BeansJPK.pobierztabele(waldok, selDokument.getDataWyst(), tabelanbpDAO);
            if (waldok.equals("PLN")) {
                selDokument.setTabelanbp(tabeladomyslna);
                selDokument.setWalutadokumentu(tabeladomyslna.getWaluta());
            } else {
                selDokument.setTabelanbp(innatabela);
                selDokument.setWalutadokumentu(innatabela.getWaluta());
            }
            selDokument.setOpis("przychód ze sprzedaży");
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            if (pol0de1) {
                tmpX.setNettowaluta(faktura.getNettoDE());
                tmpX.setVatwaluta(faktura.getVatDE());
                tmpX.setVat(faktura.getVatDE());
                tmpX.setNetto(faktura.getNettoDE());
                tmpX.setNazwakolumny("przych. sprz");
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(Z.z(faktura.getVatDE()+faktura.getNettoDE())));
            } else {
                if (waldok.equals("PLN")) {
                    tmpX.setNetto(faktura.getNetto());
                    tmpX.setVat(faktura.getVat());
                    tmpX.setNazwakolumny("przych. sprz");
                    tmpX.setDok(selDokument);
                    tmpX.setBrutto(Z.z(Z.z(faktura.getNetto()+faktura.getVat())));
                } else {
                    tmpX.setNettowaluta(faktura.getNetto());
                    tmpX.setVatwaluta(faktura.getVat());
                    tmpX.setVat(beansDok.BeansJPK.przeliczpln(faktura.getVat(), innatabela));
                    tmpX.setNetto(beansDok.BeansJPK.przeliczpln(faktura.getNetto(), innatabela));
                    tmpX.setNazwakolumny("przych. sprz");
                    tmpX.setDok(selDokument);
                    tmpX.setBrutto(Z.z(Z.z(beansDok.BeansJPK.przeliczpln(faktura.getVat(), innatabela)+beansDok.BeansJPK.przeliczpln(faktura.getNetto(), innatabela))));
                }
            }
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(Z.z(tmpX.getNetto()));
            selDokument.setBrutto(Z.z(tmpX.getBrutto()));
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(faktura,evewidencje), beansDok.BeansJPK.przeliczpln(faktura.getNetto(), innatabela), beansDok.BeansJPK.przeliczpln(faktura.getVat(), innatabela), "sprz.op", miesiac, rok);
            eVatwpis1.setDok(selDokument);
            ewidencjaTransformowana.add(eVatwpis1);
            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
            if (selDokument.getKontr()!=null && beansDok.BeansJPK.sprawdzCzyNieDuplikat(selDokument, podatnik, dokDAO)!=null) {
                selDokument = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return selDokument;
    }
    
        
    private static Klienci pobierzkontrahenta(jpkfa3.JPK.Faktura faktura, String nrKontrahenta, List<Klienci> klienci, boolean wybierzosobyfizyczne, boolean deklaracjaniemiecka, KlienciDAO klDAO, GUSView gUSView) {
        if (wybierzosobyfizyczne||deklaracjaniemiecka) {
           Klienci inc = new Klienci();
           inc.setNpelna(faktura.getP3A()!=null ? faktura.getP3A(): "brak nazwy indycentalnego");
           inc.setAdresincydentalny(faktura.getP3B()!=null ? faktura.getP3B(): "brak adresu indycentalnego");
           return inc;
        } else {
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta, gUSView);
                if (klientznaleziony!=null && klientznaleziony.getNip()!=null) {
                    boolean juzjest = false;
                    for (Klienci p : klienci) {
                        if (p.getNip().equals(klientznaleziony.getNip())) {
                            juzjest = true;
                            break;
                        }
                    }
                    if (juzjest==false && !klientznaleziony.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                        klienci.add(klientznaleziony);
                    }
                }
            } else if (klientznaleziony.getNpelna()==null) {
                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+nrKontrahenta);
            }
            return klientznaleziony;
        }
    }
    
    private static String pobierzNIPkontrahenta(jpkfa3.JPK.Faktura faktura) {
        String nip = "";
        if (faktura.getP5A()!=null) {
            nip = nip+faktura.getP5A();
        }
        if (faktura.getP5B()!=null) {
            nip = nip+faktura.getP5B();
        }
        return nip;
    }
    
    private static Evewidencja pobierzewidencje(jpkfa3.JPK.Faktura faktura, List<Evewidencja> evewidencje) {
        Evewidencja zwrot = null;
        double stawka = obliczstawke(faktura);
        for (Evewidencja p : evewidencje) {
            if (p.getStawkavat()==stawka) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    private static double obliczstawke(jpkfa3.JPK.Faktura faktura) {
        double stawka = 23;
        double netto = faktura.getNetto();
        double vat = faktura.getVat();
        double procent = Z.z4(vat/netto);
        if (procent>0.18) {
            stawka = 23;
        } else if (procent>0.07) {
            stawka = 8;
        }  else if (procent>0.04) {
            stawka = 5;
        } else {
            stawka = 0;
        }
        return stawka;
    }
    
}
