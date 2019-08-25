/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import entity.Dok;
import entity.Rozrachunek1;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ViewScoped
@ManagedBean
public class TransformacjaView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject 
    private EvewidencjaDAO evewidencjaDAO;
    public List<Dok> listaWszystkichDok;

    public TransformacjaView() {
        listaWszystkichDok = Collections.synchronizedList(new ArrayList<>());
    }
    
  
    @PostConstruct
    private void init() { //E.m(this);
    }
    
//    public void transformuj() {
//        for (int j = 0; j < 31700; j += 100) {
//            int dokad = (j + 99 > 31601 ? 31601 : j+99);
//            listaWszystkichDok.clear();
//            listaWszystkichDok.addAll(dokDAO.znajdzOdDo(j, dokad));
//            for (Dok p : listaWszystkichDok) {
//                DokPKPiR l = new DokPKPiR();
//                l.setIdDok(p.getIdDok());
//                l.setTypdokumentu(p.getTypdokumentu());
//                l.setNrWpkpir(p.getNrWpkpir());
//                l.setNrWlDk(p.getNrWlDk());
//                Klienci klient = p.getKontr();
//                if (klient != null) {
//                    try {
//                        klient = klienciDAO.findKlientByNazwa(klient.getNpelna());
//                    } catch (Exception e) { E.e(e); 
//                        try {
//                            klient = klienciDAO.findKlientByNip(klient.getNip());
//                        } catch (Exception ep) {
//                            klient = p.getKontr();
//                            klient.setNip("0000000000");
//                            if (klient.getNip().equals("0000000000")) {
//                                klienciDAO.dodaj(klient);
//                            }
//                        }
//                    }
//                }
//                l.setKontr(klient);
//                l.setPodatnik(p.getPodatnik());
//                l.setWprowadzil(p.getWprowadzil());
//                l.setDataK(p.getDataK());
//                l.setDataSprz(p.getDataSprz());
//                l.setRodzTrans(p.getRodzTrans());
//                l.setDataWyst(p.getDataWyst());
//                l.setOpis(p.getOpis());
//                //l.setListakwot(p.getListakwot());
//                l.setNetto(p.getNetto());
//                l.setBrutto(p.getBrutto());
//                l.setUwagi(p.getUwagi());
//                l.setPkpirM(p.getPkpirM());
//                l.setPkpirR(p.getPkpirR());
//                l.setVatM(p.getVatM());
//                l.setVatR(p.getVatR());
//                l.setStatus(p.getStatus());
//                //l.setEwidencjaVAT(p.getEwidencjaVAT());
//                l.setDokumentProsty(p.isDokumentProsty());
//                l.setDodatkowaKolumna(p.isDodatkowaKolumna());
//                l.setRozliczony(p.getRozliczony());
//                l.setTerminPlatnosci(p.getTerminPlatnosci());
//               // l.setRozrachunki(p.getRozrachunki());
//                l.setTermin30(p.getTermin30());
//                l.setTermin90(p.getTermin90());
//                l.setTermin150(p.getTermin150());
//                l.setStorno(p.getStorno());
//                l.setUsunpozornie(p.getUsunpozornie());
//                l.setSymbolinwestycji(p.getSymbolinwestycji());
//                try {
//                    dokPKPiRDAO.edit(l);
//                    Msg.msg("Dodalem "+j);
//                } catch (Exception e) { E.e(e); 
//                    Msg.msg("Gupi blad" + e.getCause().getMessage());
//                }
//            }
//        }
//    }
    
//    public void transformujInaczej() {
//        for (int j = 0; j < 32300; j += 200) {
//            int dokad = (j + 99 > 32299 ? 32299 : j+199);
//            listaWszystkichDok.clear();
//            listaWszystkichDok.addAll(dokDAO.znajdzKontr1NullOdDo());
//            //listaWszystkichDok.add(dokDAO.findDokByNr("fvp/2013/13185/m"));
//            for (Dok p : listaWszystkichDok) {
//                Klienci klient = p.getKontr();
//                if (klient != null) {
//                    try {
//                        klient = klienciDAO.findKlientByNazwa(klient.getNpelna());
//                    } catch (Exception e) { E.e(e); 
//                        try {
//                            klient = klienciDAO.findKlientByNip(klient.getNip());
//                        } catch (Exception ep) {
//                            klient = p.getKontr();
//                            if (klient.getNip() == null) {
//                                klient.setNip("0000000000");
//                            }
//                             klienciDAO.dodaj(klient);
//                        }
//                    }
//                } else {
//                    System.out.println("lolo");
//                }
//                p.setKontr1(klient);
//                try {
//                    dokPKPiRDAO.edit(p);
//                    Msg.msg("Dodalem "+j);
//                } catch (Exception e) { E.e(e); 
//                    Msg.msg("Gupi blad" + e.getCause().getMessage());
//                }
//            }
//        }
//    }

    public void przeniesEwidencjeVat() {
//        for (int j = 0; j < 32299; j += 200) {
//            int dokad = (j + 199 > 32299 ? 32299 : j + 199);
//            List<Dok> listaWszystkichDok = Collections.synchronizedList(new ArrayList<>());
//            listaWszystkichDok.addAll(dokDAO.znajdzOdDo(j, dokad));
//            //listaWszystkichDok.add(dokDAO.findDokByNr("fvp/2013/13185/m"));
//            for (Dok p : listaWszystkichDok) {
//                List<EVatwpis> eVatwpis = p.getEwidencjaVAT();
//                List<EVatwpis> nowyEVatwpis = new ArrayList<EVatwpis>();
//                if (eVatwpis != null) {
//                    for (EVatwpis t : eVatwpis) {
//                        EVatwpis nowywpis = new EVatwpis();
//                        nowywpis.setDok(p);
//                        nowywpis.setEwidencja(t.getEwidencja());
//                        nowywpis.setEstawka(t.getEstawka());
//                        nowywpis.setNetto(t.getNetto());
//                        nowywpis.setVat(t.getVat());
//                        nowyEVatwpis.add(nowywpis);
//                    }
//                    p.setEwidencjaVAT1(nowyEVatwpis);
//                    try {
//                        dokPKPiRDAO.edit(p);
//                        Msg.msg("Dodalem " + j);
//                    } catch (Exception e) { E.e(e); 
//                        Msg.msg("Gupi blad" + e.getCause().getMessage());
//                    }
//                }
//            }
//        }

    }
    
     public void przeniesRozrachunkiVat() {
        for (int j = 0; j < 32299; j += 200) {
            int dokad = (j + 199 > 32299 ? 32299 : j + 199);
            List<Dok> listaWszystkichDok = Collections.synchronizedList(new ArrayList<>());
            listaWszystkichDok.addAll(dokDAO.znajdzOdDo(j, dokad));
            //listaWszystkichDok.add(dokDAO.findDokByNr("fvp/2013/13185/m"));
            for (Dok p : listaWszystkichDok) {
               // List<Rozrachunek> eVatwpis = p.getRozrachunki();
                List<Rozrachunek1> nowyEVatwpis = Collections.synchronizedList(new ArrayList<>());
//                if (eVatwpis != null) {
//                    for (Rozrachunek t : eVatwpis) {
//                        Rozrachunek1 nowywpis = new Rozrachunek1();
//                        nowywpis.setDok(p);
//                        nowywpis.setDataplatnosci(t.getDataplatnosci());
//                        nowywpis.setDatawprowadzenia(t.getDatawprowadzenia());
//                        nowywpis.setDorozliczenia(t.getDorozliczenia());
//                        nowywpis.setKwotawplacona(t.getKwotawplacona());
//                        nowywpis.setUjetowstorno(t.isUjetowstorno());
//                        nowywpis.setWprowadzil(t.getWprowadzil());
//                        nowyEVatwpis.add(nowywpis);
//                    }
//                    p.setRozrachunki1(nowyEVatwpis);
//                    try {
//                        dokPKPiRDAO.edit(p);
//                        Msg.msg("Dodalem " + j);
//                    } catch (Exception e) { E.e(e); 
//                        Msg.msg("Gupi blad" + e.getCause().getMessage());
//                    }
//                }
            }
        }

    }
     
      public void przeniesKwotaKolumna() {
//        for (int j = 0; j < 32299; j += 200) {
//            int dokad = (j + 199 > 32299 ? 32299 : j + 199);
//            List<Dok> listaWszystkichDok = Collections.synchronizedList(new ArrayList<>());
//            listaWszystkichDok.addAll(dokDAO.znajdzOdDo(j, dokad));
//            //listaWszystkichDok.add(dokDAO.findDokByNr("fvp/2013/13185/m"));
//            for (Dok p : listaWszystkichDok) {
//                List<KwotaKolumna> eVatwpis = p.getListakwot();
//                ArrayList<KwotaKolumna1> nowyEVatwpis = Collections.synchronizedList(new ArrayList<>());
//                if (eVatwpis != null) {
//                    for (KwotaKolumna t : eVatwpis) {
//                        KwotaKolumna1 nowywpis = new KwotaKolumna1();
//                        nowywpis.setDok(p);
//                        nowywpis.setBrutto(t.getBrutto());
//                        nowywpis.setDowykorzystania(t.getDowykorzystania());
//                        nowywpis.setNazwakolumny(t.getNazwakolumny());
//                        nowywpis.setNetto(t.getNetto());
//                        nowywpis.setVat(t.getVat());
//                        nowyEVatwpis.add(nowywpis);
//                    }
//                    p.setListakwot1(nowyEVatwpis);
//                    try {
//                        dokPKPiRDAO.edit(p);
//                        Msg.msg("Dodalem " + j);
//                    } catch (Exception e) { E.e(e); 
//                        Msg.msg("Gupi blad" + e.getCause().getMessage());
//                    }
//                }
//            }
//        }

    }
      
//        public void ewidencjewDekalracji() {
//            List<Deklaracjevat> listaWszystkichDok = Collections.synchronizedList(new ArrayList<>());
//            listaWszystkichDok.addAll(deklaracjevatDAO.findAll());
//            //listaWszystkichDok.add(dokDAO.findDokByNr("fvp/2013/13185/m"));
//            for (Deklaracjevat p : listaWszystkichDok) {
//                HashMap<String,List<EVatwpisSuper>> stareEwidencje = p.getEwidencje();
//                List<EwidencjevatDeklaracja> nowyEVatwpis = Collections.synchronizedList(new ArrayList<>());
//                if (stareEwidencje != null) {
//                    List<String> klucze = Collections.synchronizedList(new ArrayList<>());
//                    klucze.addAll(stareEwidencje.keySet());
//                    for (String t : klucze) {
//                        EwidencjevatDeklaracja nowywpis = new EwidencjevatDeklaracja();
//                        List<EVatwpisSuper> pobranepola = stareEwidencje.get(t);
//                        EVatwpisSuma eVatwpisSuma = p.getPodsumowanieewidencji().get(t);
//                        nowywpis.setDeklaracja(p);
//                        Evewidencja evewidencja = new Evewidencja();
//                        try {
//                            evewidencja = evewidencjaDAO.znajdzponazwie(pobranepola.get(0).getNazwaewidencji());
//                        } catch (Exception e) { E.e(e); }
//                        EVatwpisSuper wiersz = pobranepola.get(0);
//                        nowywpis.setEwidencja(evewidencja);
//                        nowywpis.setMiesiac(wiersz.getDataSprz().substring(5,7));
//                        nowywpis.setPodatnik(p.getPodatnik());
//                        nowywpis.setRok(p.getRok());
//                        nowywpis.setSumaewidencji(eVatwpisSuma);
//                        nowywpis.setWierszeEwidencji(pobranepola);
//                        nowyEVatwpis.add(nowywpis);
//                    }
//                    p.setEwidencjedeklaracja(nowyEVatwpis);
//                    try {
//                        deklaracjevatDAO.edit(p);
//                        Msg.msg("Dodalem ");
//                    } catch (Exception e) { E.e(e); 
//                        Msg.msg("Gupi blad" + e.getCause().getMessage());
//                    }
//                }
//        }
//
//    }
        
    public static void main(String[] args) {
        String data = "2019-05-01";
        String mc = data.substring(5,7);
        assert mc.equals("05");
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

   
    public List<Dok> getListaWszystkichDok() {
        return listaWszystkichDok;
    }

    public void setListaWszystkichDok(List<Dok> listaWszystkichDok) {
        this.listaWszystkichDok = listaWszystkichDok;
    }
    
    
    
}
