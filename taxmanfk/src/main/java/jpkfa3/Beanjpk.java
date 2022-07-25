/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkfa3;

import beansDok.ListaEwidencjiVat;
import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import error.E;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import view.WpisView;
import waluty.Z;
import xls.ImportBean;

/**
 *
 * @author Osito
 */
public class Beanjpk {
    
    public static Dok generujdok(Object p, String waldok, List<Evewidencja> evewidencje, TabelanbpDAO tabelanbpDAO, Tabelanbp tabeladomyslna, List<Klienci> klienci, boolean wybierzosobyfizyczne, 
            boolean deklaracjaniemiecka, KlienciDAO klDAO, Podatnik podatnik, DokDAO dokDAO, Rodzajedok rodzajedok, boolean pol0de1) {
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
            selDokument.setKontr(pobierzkontrahenta(faktura, pobierzNIPkontrahenta(faktura), klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO));
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
    
    public static Dokfk generujdokfk(Object p, String waldok, List<Evewidencja> evewidencje, TabelanbpDAO tabelanbpDAO, Tabelanbp tabeladomyslna, List<Klienci> klienci, boolean wybierzosobyfizyczne,
            boolean deklaracjaniemiecka, KlienciDAO klDAO, Podatnik podatnik, DokDAOfk dokDAOfk, Rodzajedok rodzajedok, boolean pol0de1,ListaEwidencjiVat listaEwidencjiVat,
            KliencifkDAO kliencifkDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO, int numerkolejny, Konto kontokasadlajpk) {
        jpkfa3.JPK.Faktura faktura = (jpkfa3.JPK.Faktura) p;
        Dokfk nd = null;
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            String datawystawienia = faktura.getP1().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            nd = new Dokfk(numerkolejny, rok);
            nd.setWprowadzil(principal.getName());
            nd.setKontr(pobierzkontrahenta(faktura, pobierzNIPkontrahenta(faktura), klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO));
            ImportBean.ustawnumerwlasny(nd, faktura.getP2A());
            nd.setOpisdokfk("przychód ze sprzedaży");
            nd.setPodatnikObj(podatnik);
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
            nd.setEwidencjaVAT(null);
            nd.setImportowany(true);
            Tabelanbp innatabela = beansDok.BeansJPK.pobierztabele(waldok, datawystawienia, tabelanbpDAO);
            if (waldok.equals("PLN")) {
                nd.setTabelanbp(tabeladomyslna);
                nd.setWalutadokumentu(tabeladomyslna.getWaluta());
            } else {
                nd.setTabelanbp(innatabela);
                nd.setWalutadokumentu(innatabela.getWaluta());
            }
            Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
            if (juzjest != null) {
                nd = null;
            } else {
                ustawwiersze(nd, rodzajedok, faktura, tabeladomyslna, tabelanbpDAO, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, kontokasadlajpk);
                if (nd.getListawierszy() != null && nd.getListawierszy().size() > 0) {
                    nd.setDatadokumentu(datawystawienia);
                    nd.setDataoperacji(datawystawienia);
                    nd.setDatawplywu(datawystawienia);
                    nd.setDatawystawienia(datawystawienia);
                    nd.setDataujecia(new Date());
                    nd.setMiesiac(miesiac);
                    nd.setRok(rok);
                    nd.setVatM(miesiac);
                    nd.setVatR(rok);
                    nd.setImportowany(true);
                    nd.setWprowadzil(principal.getName());
                    nd.przeliczKwotyWierszaDoSumyDokumentu();
                    ImportBean.podepnijEwidencjeVat(nd, faktura, listaEwidencjiVat);
                } else {
                    nd = null;
                }
            }
            } catch (Exception ex) {
                E.e(ex);
            }
        return nd;
    }
    
    private static void ustawwiersze(Dokfk nd, Rodzajedok rodzajedok,jpkfa3.JPK.Faktura faktura, Tabelanbp tabelanbppl, TabelanbpDAO tabelanbpDAO, KliencifkDAO kliencifkDAO, WpisView wpisView, 
            KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO, Konto kontokasadlajpk) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int lpwiersza = 1;
        Konto kontown = kontokasadlajpk;
        if (kontown==null) {
            kontown = ImportBean.pobierzkontoWn(nd.getKontr(), kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
        }
        Konto kontoma = rodzajedok.getKontoRZiS();
        nd.getListawierszy().add(przygotujwierszNettoSprzedaz(lpwiersza, nd, faktura, kontown, kontoma, tabelanbppl, tabelanbpDAO));
        lpwiersza++;
        kontoma = rodzajedok.getKontovat();
        if (faktura.getVat()!=0.0) {
            nd.getListawierszy().add(przygotujwierszVATNalezny(lpwiersza, nd, faktura, kontown, kontoma, tabelanbppl, tabelanbpDAO));
        }
    }
     private static Wiersz przygotujwierszNettoSprzedaz(int lpwiersza,Dokfk nd, jpkfa3.JPK.Faktura faktura, Konto kontown, Konto kontoma, Tabelanbp tabelanbppl, TabelanbpDAO tabelanbpDAO) {
        Wiersz w = new Wiersz(lpwiersza, nd, 0);
        uzupelnijwiersz(w, nd, faktura, tabelanbppl, tabelanbpDAO, 0);
        w.setOpisWiersza("przychody ze sprzedaży");
        StronaWiersza strwn = new StronaWiersza(w, "Wn", faktura.getBrutto(), kontown);
        StronaWiersza strma = new StronaWiersza(w, "Ma", faktura.getNetto(), kontoma);
        strwn.setKwotaPLN(zrobpln(w,faktura.getBrutto()));
        strma.setKwotaPLN(zrobpln(w,faktura.getNetto()));
        if (kontown!=null&&kontown.getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            strwn.setNowatransakcja(true);
        }
        strwn.setTypStronaWiersza(1);
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
     private static Wiersz przygotujwierszVATNalezny(int lpwiersza,Dokfk nd, jpkfa3.JPK.Faktura faktura, Konto kontown, Konto kontoma, Tabelanbp tabelanbppl, TabelanbpDAO tabelanbpDAO) {
        Wiersz w = new Wiersz(lpwiersza, nd, 2);
        uzupelnijwiersz(w, nd, faktura, tabelanbppl, tabelanbpDAO, 1);
        w.setOpisWiersza("przychody ze sprzedaży - VAT");
        StronaWiersza strma = new StronaWiersza(w, "Ma", faktura.getVat(), kontoma);
        strma.setKwotaPLN(zrobpln(w,faktura.getVat()));
        w.setStronaMa(strma);
        return w;
    }
     
     private static void uzupelnijwiersz(Wiersz w, Dokfk nd, jpkfa3.JPK.Faktura faktura, Tabelanbp tabelanbppl, TabelanbpDAO tabelanbpDAO, int lpmacierzystego) {
        if (faktura.getKodWaluty().value().equals("PLN")) {
            w.setTabelanbp(tabelanbppl);
        } else {
            DateTime dzienposzukiwany = new DateTime(nd.getDatadokumentu());
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, faktura.getKodWaluty().value());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    w.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
        }
        nd.setWalutadokumentu(w.getTabelanbp().getWaluta());
        w.setDokfk(nd);
        w.setLpmacierzystego(lpmacierzystego);
        w.setDataksiegowania(nd.getDatawplywu());
    }
     
   
     
    private static double zrobpln(Wiersz w, double kwota) {
        double zwrot = kwota;
        if (!w.getWalutaWiersz().equals("PLN")) {
            zwrot = Z.z(kwota*w.getTabelanbp().getKurssredniPrzelicznik());
        }
        return zwrot;
    }

        
    private static Klienci pobierzkontrahenta(jpkfa3.JPK.Faktura faktura, String nrKontrahenta, List<Klienci> klienci, boolean wybierzosobyfizyczne, boolean deklaracjaniemiecka, KlienciDAO klDAO) {
        if (wybierzosobyfizyczne||deklaracjaniemiecka) {
           Klienci inc = new Klienci();
           inc.setNpelna(faktura.getP3A()!=null ? faktura.getP3A(): "brak nazwy indycentalnego");
           inc.setNip(faktura.getP5B()!=null ? faktura.getP5B(): "brak nip indycentalnego");
           inc.setAdresincydentalny(faktura.getP3B()!=null ? faktura.getP3B(): "brak adresu indycentalnego");
           return inc;
        } else {
            nrKontrahenta = pobierznip(nrKontrahenta);
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta);
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
    
     private static String pobierznip(String nip) {
        String zwrot = nip;
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[A-Z]");
        boolean isalfa = p.matcher(prefix).find();
        if (isalfa) {
            zwrot = nip.substring(2);
        }
        return zwrot;
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
