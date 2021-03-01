/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansFK.TabelaNBPBean;
import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import data.Data;
import embeddable.PanstwaEUSymb;
import embeddablefk.InterpaperXLS;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Tabelanbp;
import error.E;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import org.joda.time.DateTime;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class GenerujDok {
    
    public int generowanieListaDok(List<InterpaperXLS> wiersze, List<Klienci> znalezieni, boolean zakup0sprzedaz, boolean towar0usluga1, boolean firmy0indycentalni1, WpisView wpisView, RodzajedokDAO  rodzajedokDAO, 
            TabelanbpDAO tabelanbpDAO, DokDAO dokDAO, KlienciDAO klienciDAO, EvewidencjaDAO evewidencjaDAO) {
        int zwrot = 0;
        List<Evewidencja> evewidencje = evewidencjaDAO.findAll();
        if (wiersze!=null&&wiersze.size()>0) {
            for (InterpaperXLS p : wiersze) {
                generowanieDokumentu(p, znalezieni, zakup0sprzedaz, towar0usluga1, firmy0indycentalni1, wpisView, rodzajedokDAO, evewidencje, tabelanbpDAO, dokDAO, klienciDAO);
            }
        } else {
            zwrot = 1;
        }
        return zwrot;
    }
    
     public int generowanieDokumentu(InterpaperXLS wiersz, List<Klienci> znalezieni, boolean zakup0sprzedaz, boolean towar0usluga1, boolean firmy0indycentalni1, WpisView wpisView, RodzajedokDAO  rodzajedokDAO, 
                List<Evewidencja> evewidencje, TabelanbpDAO tabelanbpDAO, DokDAO dokDAO, KlienciDAO klienciDAO) {
        int ile = 0;
        try {
            int polska0unia1zagranica2 = 0;
            if (wiersz.getKlient().getKrajnazwa()!=null && !wiersz.getKlient().getKrajkod().equals("PL")) {
                polska0unia1zagranica2 = 2;
                if (PanstwaEUSymb.getWykazPanstwUE().contains(wiersz.getKlient().getKrajkod())) {
                    if (wiersz.getVatPLN()!=0.0) {
                        polska0unia1zagranica2 = 0;
                    } else {
                        polska0unia1zagranica2 = 1;
                    }
                }
            }
            String rodzajdk = "ZZ";
            Dok dokument = null;
            if (zakup0sprzedaz) {
                if (towar0usluga1) {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
                } else {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "WDT" : "EXP";
                }
                dokument = generujdok(wiersz, wpisView, firmy0indycentalni1, rodzajdk, rodzajedokDAO, "przychody ze sprzedaży", evewidencje, tabelanbpDAO, dokDAO, klienciDAO, znalezieni);
            } else {
                if (towar0usluga1) {
                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "IU";
                    if (wiersz.getVatPLN()!=0.0 && !wiersz.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "RACH";
                    }
                } else {
                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "WNT";
                    if (wiersz.getVatPLN()!=0.0 && !wiersz.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "RACH";
                    }
                }
                dokument = generujdok(wiersz, wpisView, firmy0indycentalni1, rodzajdk, rodzajedokDAO, "zakup towarów/koszty", evewidencje, tabelanbpDAO, dokDAO, klienciDAO, znalezieni);
            }
            
            try {
                if (dokument!=null) {
                    dokDAO.create(dokument);
                    wiersz.setSymbolzaksiegowanego(dokument.getNrWlDk());
                    ile = 1;
                }
            } catch (Exception e) {
                ile = 0;
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdk);
            }
        } catch (Exception e) {
            ile = 0;
            E.e(e);
        }
        return ile;
    }
     
     private Dok generujdok(InterpaperXLS wiersz, WpisView wpisView, boolean firmy0indycentalni1, String rodzajdok, RodzajedokDAO  rodzajedokDAO, String opis, 
                List<Evewidencja> evewidencje, TabelanbpDAO tabelanbpDAO, DokDAO dokDAO, KlienciDAO klienciDAO, List<Klienci> znalezieni) {
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = Data.data_yyyyMMdd(wiersz.getDatawystawienia());
            String miesiac = Data.getMc(datawystawienia);
            String rok = Data.getRok(datawystawienia);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(datawystawienia);
            selDokument.setDataSprz(Data.data_yyyyMMdd(wiersz.getDatasprzedaży()));
            selDokument.setKontr(pobierzkontrahenta(wiersz, firmy0indycentalni1, klienciDAO, znalezieni));
            selDokument.setRodzajedok(pobierzrodzajrok(rodzajdok, rodzajedokDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt()));
            selDokument.setOpis(opis);
            selDokument.setNrWlDk(wiersz.getNrfaktury());
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(wiersz.getNettoPLN());
            tmpX.setVat(wiersz.getVatPLN());
            tmpX.setNettowaluta(wiersz.getNettowaluta());
            tmpX.setVatwaluta(wiersz.getVatwaluta());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(Z.z(wiersz.getNettoPLN()+wiersz.getVatPLN())));
            listaX.add(tmpX);
            String symbolwalt = wiersz.getWalutaplatnosci();
            Tabelanbp innatabela = pobierztabele(symbolwalt, selDokument.getDataWyst(), tabelanbpDAO);
            selDokument.setTabelanbp(innatabela);
            selDokument.setWalutadokumentu(innatabela.getWaluta());
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(selDokument.getRodzajedok(), wiersz.getNettoPLN(), wiersz.getVatPLN(),evewidencje), wiersz.getNettoPLN(), wiersz.getVatPLN(), "sprz.op", miesiac, rok);
            eVatwpis1.setDok(selDokument);
            ewidencjaTransformowana.add(eVatwpis1);
            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument, dokDAO)!=null) {
                selDokument = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return selDokument;
    }
     

     private Tabelanbp pobierztabele(String waldok, String dataWyst, TabelanbpDAO tabelanbpDAO) {
        DateTime dzienposzukiwany = new DateTime(dataWyst);
        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
    }
    
     private Klienci pobierzkontrahenta(InterpaperXLS wiersz, boolean firmy0indycentalni1, KlienciDAO klienciDAO, List<Klienci> znalezieni) {
        if (firmy0indycentalni1) {
           Klienci inc = new Klienci();
           inc.setNip(wiersz.getNip());
           inc.setNpelna(wiersz.getKlientnazwa()!=null ? wiersz.getKlientnazwa(): "brak nazwy indycentalnego");
           inc.setAdresincydentalny(wiersz.getAdres()!=null ? wiersz.getAdres(): "brak adresu indycentalnego");
           return inc;
        } else {
            String nip = wiersz.getNip();
            Klienci klientznaleziony = klienciDAO.findKlientByNipImport(nip);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nip);
                if (klientznaleziony!=null && klientznaleziony.getNpelna()!=null) {
                    boolean juzjest = false;
                    for (Klienci p : znalezieni) {
                        if (p.getNip()!=null && p.getNip().equals(klientznaleziony.getNip())) {
                            juzjest = true;
                            break;
                        }
                    }
                    if (juzjest==false) {
                        znalezieni.add(klientznaleziony);
                    }
                } else if (klientznaleziony!=null){
                     klientznaleziony.setNskrocona(klientznaleziony.getNpelna());
                     klientznaleziony.setNpelna("nie znaleziono firmy w bazie Regon");
                }
            } else if (klientznaleziony==null||klientznaleziony.getNpelna()==null) {
                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+wiersz.getNip());
            }
            return klientznaleziony;
        }
    }
    
    private Evewidencja pobierzewidencje(Rodzajedok rodzajedok, double netto, double vat, List<Evewidencja> evewidencje) {
        Evewidencja zwrot = null;
        double stawka = obliczstawke(netto, vat);
        for (Evewidencja p : evewidencje) {
            if (rodzajedok.getSkrotNazwyDok().equals("SZ") && p.getStawkavat()==stawka) {
                zwrot = p;
                break;
            } else if (rodzajedok.getRodzajtransakcji().equals(p.getTypewidencji())){ 
                zwrot = p;
            }        }
        return zwrot;
    }
    
    private double obliczstawke(double netto, double vat) {
        double stawka = 23;
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
    
     public Dok sprawdzCzyNieDuplikat(Dok selD, DokDAO dokDAO) {
        if (selD.getKontr().getNpelna().equals("OPTEGRA POLSKA sp. z o.o.")) {
            error.E.s("");
        }
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, selD.getPodatnik(), selD.getRodzajedok().getSkrot());
        return tmp;
    }

    private Rodzajedok pobierzrodzajrok(String rodzajdok, RodzajedokDAO rodzajedokDAO, Podatnik podatnik, String rok) {
        return rodzajedokDAO.find(rodzajdok, podatnik, rok);
    }
}
