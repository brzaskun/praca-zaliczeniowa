/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import msg.Msg;
import view.WpisView; import org.primefaces.PrimeFaces;
import viewfk.subroutines.ObslugaWiersza;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
public class DokFKVATBean {
    
    public static Double pobierzstawke(EVatwpisFK evatwpis) {
        String stawkavat = null;
        double kwotavat = 0.0;
        try {
            if (evatwpis.getDokfk().getRodzajedok().getRodzajtransakcji().equals(("sprzedaz"))) {
                stawkavat = evatwpis.getEwidencja().getNazwa().replaceAll("[^\\d]", "");
                kwotavat = Double.parseDouble(stawkavat) / 100;
            } else if (evatwpis.getDokfk().getRodzajedok().getStawkavat() != 0.0) {
                double st = evatwpis.getDokfk().getRodzajedok().getStawkavat();
                kwotavat = st / 100;
            }else {
                kwotavat = 0.23;
            }
        } catch (Exception e) {
            E.e(e);
            kwotavat = 0.23;
        }
        return kwotavat;
    }
    
    public static void ustawvat(EVatwpisFK evatwpis,  Dokfk selected) {
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        double przelicznik = selected.getTabelanbp().getWaluta().getPrzelicznik();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String rodzajdok = selected.getRodzajedok().getSkrot();
        double stawkavat = DokFKVATBean.pobierzstawke(evatwpis);
         if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto()/kurs*przelicznik);
            if (evatwpis.getNettowwalucie()!= obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto()*kurs/przelicznik));
            }
        }
        if (rodzajdok.contains("WDT") || rodzajdok.contains("UPTK") || rodzajdok.contains("RVCS") || rodzajdok.contains("EXP") || rodzajdok.contains("sprzedaż zw")) {
            evatwpis.setVat(0.0);
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
        if (!w.getSymbolwaluty().equals("PLN")) {
            //ten vat tu musi byc bo inaczej bylby onblur przy vat i cykliczne odswiezanie
            evatwpis.setVatwwalucie(Z.z(evatwpis.getVat()/kurs*przelicznik));
        }
    }
    
    public static void ustawvatodbrutto(EVatwpisFK evatwpis,  Dokfk selected) {
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        double przelicznik = selected.getTabelanbp().getWaluta().getPrzelicznik();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        if (!w.getSymbolwaluty().equals("PLN")) {
            evatwpis.setNettowwalucie(evatwpis.getNetto());
            evatwpis.setVatwwalucie(evatwpis.getVat());
            //ten vat tu musi byc bo inaczej bylby onblur przy vat i cykliczne odswiezanie
            evatwpis.setNetto(Z.z(evatwpis.getNettowwalucie()*kurs/przelicznik));
            evatwpis.setVat(Z.z(evatwpis.getVatwwalucie()*kurs/przelicznik));
            evatwpis.setBrutto(Z.z(evatwpis.getNetto()+evatwpis.getVat()));
        }
    }
    
    public static void ustawvat(EVatwpisFK evatwpis, Dokfk selected, double stawkavat) {
        String skrotRT = selected.getSeriadokfk();
        int lp = evatwpis.getLp();
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        double przelicznik = selected.getTabelanbp().getWaluta().getPrzelicznik();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String opis = evatwpis.getEwidencja().getNazwa();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto() / kurs * przelicznik);
            if (evatwpis.getNettowwalucie() != obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto() * kurs /przelicznik));
            }
        }
        if (opis.contains("WDT") || opis.contains("UPTK") || opis.contains("EXP") || opis.contains("RVCS") ) {
            evatwpis.setVat(0.0);
        } else if (selected.getRodzajedok().getProcentvat() != 0.0 && evatwpis.getEwidencja().getTypewidencji().equals("z")) {
            evatwpis.setVat(Z.z((evatwpis.getNetto() * 0.23) / 2));
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
    }
            
//   public static double[] podsumujwartosciVAT(List<EVatwpisFK> ewidencja) {
//        double[] wartosciVAT = new double[8];
//        for (EVatwpisFK p : ewidencja) {
//            if (p.getDokfk().getRodzajedok().getProcentvat() != 0.0) {
//                wartosciVAT[0] += p.getNetto();
//                wartosciVAT[1] += p.getVat();
//                wartosciVAT[2] += p.getNettowwalucie();
//                wartosciVAT[3] += p.getVatwwalucie();
//                double vatplnprocent = Z.z(p.getVat()*p.getDokfk().getRodzajedok().getProcentvat()/100);
//                double vatplnreszta = Z.z(p.getVat() - vatplnprocent);
//                wartosciVAT[4] += Z.z(vatplnprocent);
//                wartosciVAT[5] += Z.z(vatplnreszta);
//                double vatprocent = Z.z(p.getVatwwalucie()*p.getDokfk().getRodzajedok().getProcentvat()/100);
//                double vatreszta = Z.z(p.getVatwwalucie()-vatprocent);
//                wartosciVAT[6] += Z.z(vatprocent);
//                wartosciVAT[7] += Z.z(vatreszta);
//            } else {
//                wartosciVAT[0] += p.getNetto();
//                wartosciVAT[1] += p.getVat();
//                wartosciVAT[2] += p.getNettowwalucie();
//                wartosciVAT[3] += p.getVatwwalucie();
//                double vatplnpolowa = Z.z(p.getVat()/2);
//                double vatplnreszta = Z.z(p.getVat()-vatplnpolowa);
//                wartosciVAT[4] += Z.z(vatplnpolowa);
//                wartosciVAT[5] += Z.z(vatplnreszta);
//                double vatpolowa = Z.z(p.getVatwwalucie()/2);
//                double vatreszta = Z.z(p.getVatwwalucie()-vatpolowa);
//                wartosciVAT[6] += Z.z(vatpolowa);
//                wartosciVAT[7] += Z.z(vatreszta);
//            }
//        }
//        return wartosciVAT;
//    }
    
    public static WartosciVAT podsumujwartosciVAT(List<EVatwpisFK> ewidencja) {
        WartosciVAT wartosciVAT = new WartosciVAT();
        for (EVatwpisFK p : ewidencja) {
            double vatplnprocent = Z.z(p.getVat()/2);
            double vatplnreszta = Z.z(p.getVat()-vatplnprocent);
            double vatwalutaprocent = Z.z(p.getVatwwalucie()/2);
            double vatwalutareszta = Z.z(p.getVatwwalucie()-vatwalutaprocent);
            if (p.getDokfk().getRodzajedok().getProcentvat() != 0.0) {
                vatplnprocent = Z.z(p.getVat()*p.getDokfk().getRodzajedok().getProcentvat()/100);
                vatplnreszta = Z.z(p.getVat() - vatplnprocent);
                vatwalutaprocent = Z.z(p.getVatwwalucie()*p.getDokfk().getRodzajedok().getProcentvat()/100);
                vatwalutareszta = Z.z(p.getVatwwalucie()-vatwalutaprocent);
            }
            wartosciVAT.netto += p.getNetto();
            wartosciVAT.vat += p.getVat();
            wartosciVAT.nettowWalucie += p.getNettowwalucie();
            wartosciVAT.vatwWalucie += p.getVatwwalucie();
            wartosciVAT.vatPlndodoliczenia += vatplnprocent;
            wartosciVAT.vatPlnkup += vatplnreszta;
            wartosciVAT.vatWalutadodoliczenia += vatwalutaprocent;
            wartosciVAT.vatWalutakup += vatwalutareszta;
        }
        return wartosciVAT;
    }
    
    
    public static void rozliczVatPrzychod(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, Map<String, Konto> kontadlaewidencji, WpisView wpisView, Dokfk poprzedniDokument, Konto kontoRozrachunkowe) {
        if (wartosciVAT.netto != 0 || wartosciVAT.nettowWalucie != 0) {
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
            if (kontoRozrachunkowe == null) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
                if (wierszpierwszy != null && wartosciVAT.netto!=0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.netto);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.netto+wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.nettowWalucie);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.nettowWalucie+wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                    if (kontonetto != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontonetto);
                    }
                    DFKWiersze.zaznaczNowaTrasakcja(wierszpierwszy, "Wn");
                } else if (wierszpierwszy != null && wartosciVAT.netto==0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontovat);
                    } else {
                        wierszpierwszy.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                }
               if (selected.getListawierszy().size()==1 && wartosciVAT.vat != 0 && wartosciVAT.netto != 0) {
                    Wiersz wierszdrugi;
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT.vat, 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT.vatwWalucie, 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    }
                    wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza()+" - podatek vat");
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null && kontovat.getRok()==selected.getRokInt()) {
                        wierszdrugi.getStronaMa().setKonto(kontovat);
                    } else {
                        wierszdrugi.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                    selected.getListawierszy().add(wierszdrugi);
               }
                pobierzkontaZpoprzedniegoDokumentu(poprzedniDokument, selected);
                int index = wierszvatdoc.getLp()-1 < 0 ? 0 : wierszvatdoc.getLp()-1;
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+index+":netto");
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+index+":brutto");
                PrimeFaces.current().ajax().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
    }
    
     public static Konto pobierzKontoRozrachunkowe(KliencifkDAO kliencifkDAO, Dokfk selected, WpisView wpisView, KontoDAOfk kontoDAOfk) {
        Konto konto = null;
        try {
            //to znajdujemy polaczenie konta z klientem nazwa tego polaczenia to Kliencifk
            if (!selected.getKontr().getNip().equals(wpisView.getPodatnikObiekt().getNip())) {
                Kliencifk symbolSlownikowyKonta = kliencifkDAO.znajdzkontofk(selected.getKontr().getNip(), wpisView.getPodatnikObiekt().getNip());
                List<Konto> listakont = kontoDAOfk.findKontaNazwaPodatnik(symbolSlownikowyKonta.getNip(), wpisView);
                if (listakont == null || listakont.size() == 0) {
                    throw new Exception();
                }
                Konto kontoprzyporzadkowaneDoRodzajuDok = selected.getRodzajedok().getKontorozrachunkowe();
                for (Konto p : listakont) {
                    if (kontoprzyporzadkowaneDoRodzajuDok.equals(p.getKontomacierzyste())) {
                        konto = p;
                        break;
                    }
                }
            }
        } catch (Exception e) {  
            Msg.msg("e", "Brak w kontach słownikowych danego kontrahenta. Zweryfikuj plan kont czy sa podpiete slowniki");
        } finally {
            return konto;
        }
    }
     
     public static void pobierzkontaZpoprzedniegoDokumentu(Dokfk poprzedniDokument, Dokfk selected) {
        try {
            if (poprzedniDokument != null && poprzedniDokument.getRok().equals(selected.getRok())) {
                int rozmiar = poprzedniDokument.getListawierszy().size() > 1 ? 3 : 2;
                for (int i = 0; i < rozmiar; i++) {
                    Wiersz wierszDokumentuPoprzedniego = poprzedniDokument.getListawierszy().get(i);
                    Wiersz wierszDokumentuBiezacego = selected.getListawierszy().get(i);
                    if (wierszDokumentuPoprzedniego != null && wierszDokumentuBiezacego != null) {
                        StronaWiersza wnDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaWn();
                        StronaWiersza wnDokumenuBiezacego = wierszDokumentuBiezacego.getStronaWn();
                        if (!wnDokumenuBiezacego.getKonto().getPelnynumer().startsWith("221")) {//nie ruszamy konta vat zakupowego
                            if (wnDokumentuPoprzedniego != null && !wnDokumentuPoprzedniego.getKonto().getZwyklerozrachszczegolne().equals("vat") && !selected.getRodzajedok().isTylkovatnalezny()) {
                                if (wnDokumentuPoprzedniego.getKonto().getRok()==selected.getRokInt()) {
                                    wnDokumenuBiezacego.setKonto(wnDokumentuPoprzedniego.getKonto());
                                }
                            }
                        }
                        StronaWiersza maDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaMa();
                        StronaWiersza maDokumenuBiezacego = wierszDokumentuBiezacego.getStronaMa();
                        if (maDokumentuPoprzedniego != null && !maDokumentuPoprzedniego.getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                            if (maDokumentuPoprzedniego.getKonto().getRok()==selected.getRokInt()) {
                                maDokumenuBiezacego.setKonto(maDokumentuPoprzedniego.getKonto());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) { 
            E.e(e);
        }
     }
     
     // tu oblicza sie vaty i dodaje wiersze
    public static void rozliczVatKoszt(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, Map<String, Konto> kontadlaewidencji, WpisView wpisView, Dokfk poprzedniDokument, Konto kontoRozrachunkowe, Cechazapisu nkup) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        try {
            if (kontoRozrachunkowe == null) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
            if (wierszpierwszy != null && wierszpierwszy.getStronaWn().getKwota() == 0.0) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                //gdy netto jest rowne 0, np korekta stawki vat
                if (wartosciVAT.netto == 0.0) {
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                    } else {
                        wierszpierwszy.getStronaMa().setKonto(kontadlaewidencji.get("149-3"));
                    }
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null && kontovat.getRok()==selected.getRokInt()) {
                        wierszpierwszy.getStronaWn().setKonto(kontovat);
                    } else {
                        wierszpierwszy.getStronaWn().setKonto(kontadlaewidencji.get("221-3"));
                    }
                } else {
                    //gdy netto jest wieksze od zera
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.netto);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.netto);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.netto + wartosciVAT.vat);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    } else {
                        wn.setKwota(wartosciVAT.nettowWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.nettowWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.nettowWalucie + wartosciVAT.vatwWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                    } else {
                        wierszpierwszy.getStronaMa().setKonto(kontadlaewidencji.get("149-3"));
                    }
                    Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                    if (kontonetto != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontonetto);
                    }
                }
                DFKWiersze.zaznaczNowaTrasakcja(wierszpierwszy, "Ma");
            }
            int lpnastepnego = 2;
            int limitwierszy = 1;
            if (selected.getRodzajedok().getProcentvat() != 0.0) {
                wartosciVAT.vat = wartosciVAT.vatPlndodoliczenia;
                wartosciVAT.vatwWalucie = wartosciVAT.vatWalutadodoliczenia;
            }
            if (selected.getRodzajedok().getProcentvat() != 0.0) {
                dolaczwiersz2_3Koszt(wartosciVAT, w, 2, 1, selected, kontadlaewidencji);
                lpnastepnego++;
                limitwierszy++;
            }
            if (wpisView.isParamCzworkiPiatki() && selected.getListawierszy().size() == limitwierszy && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                Wiersz wierszdrugi;
                wierszdrugi = ObslugaWiersza.utworzNowyWiersz5(selected, 2, wartosciVAT.netto, 1);
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.getStronaWn().setKwota(wartosciVAT.netto);
                wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                wierszdrugi.setCzworka(wierszpierwszy);
                wierszpierwszy.getPiatki().add(wierszdrugi);
                wierszdrugi.getStronaMa().setKonto(kontadlaewidencji.get("490"));
                selected.getListawierszy().add(wierszdrugi);
                dolaczwiersz2_3(wartosciVAT, w, lpnastepnego + 1, 0, selected, kontadlaewidencji);
            } else if (!wpisView.isParamCzworkiPiatki() && selected.getListawierszy().size() == limitwierszy && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                dolaczwiersz2_3(wartosciVAT, w, lpnastepnego, 0, selected, kontadlaewidencji);
            }
            pobierzkontaZpoprzedniegoDokumentu(poprzedniDokument, selected);
            if (selected.getRodzajedok().getProcentkup()!=0.0) {
                rozliczobcieciekosztow(selected, nkup);
            }
            PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:0:netto");
            PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:0:brutto");
            PrimeFaces.current().ajax().update("formwpisdokument:dataList");

        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    private static void rozliczobcieciekosztow(Dokfk selected, Cechazapisu nkup) {
        double procent = Z.z(selected.getRodzajedok().getProcentkup()/100);
        List<Wiersz> wierszenkup = new ArrayList<>();
        int liczbawierszy = selected.getStronyWierszy().size();
        for (StronaWiersza p : selected.getStronyWierszy()) {
            if (p.isWn() && p.getKonto().getPelnynumer().startsWith("4")) {
                double starawartosc = p.getKwota();
                double starawartoscWaluta = p.getKwotaWaluta();
                double starawartoscPLN = p.getKwotaPLN();
                double nowawartosc = Z.z(starawartosc * procent);
                double nowawartoscnkup = starawartosc-nowawartosc;
                double nowawartoscWaluta = Z.z(starawartoscWaluta * procent);
                double nowawartoscnkupWaluta = starawartoscWaluta-nowawartoscWaluta;
                double nowawartoscPLN = Z.z(starawartoscPLN * procent);
                double nowawartoscnkupPLN = starawartoscPLN-nowawartoscPLN;
                Wiersz wiersznkup = ObslugaWiersza.utworzNowyWierszWn(selected, liczbawierszy++, nowawartoscnkup, 1);
                wiersznkup.getStronaWn().setKwotaPLN(Z.z(nowawartoscnkupPLN));
                wiersznkup.getStronaWn().setKwotaWaluta(Z.z(nowawartoscnkupWaluta));
                wiersznkup.getStronaWn().setKonto(p.getKonto());
                wiersznkup.getStronaWn().getCechazapisuLista().add(nkup);
                String opis = p.getWiersz().getOpisWiersza()+" - nkup";
                wiersznkup.setOpisWiersza(opis);
                wierszenkup.add(wiersznkup);
                p.setKwota(Z.z(nowawartosc));
                p.setKwotaWaluta(Z.z(nowawartoscWaluta));
                p.setKwotaPLN(Z.z(nowawartoscPLN));
            }
        }
        selected.getListawierszy().addAll(wierszenkup);
        sprawdzsumyobciecie(selected.getListawierszy());
    }
    
    private static void sprawdzsumyobciecie(List<Wiersz> listawierszy) {
        try {
            double sumap = Z.z(listawierszy.get(0).getStronaMa().getKwota());
            double sumal = 0.0;
            for (Wiersz p : listawierszy) {
                sumal += p.getKwotaWn();
            }
            double roznica = Z.z(sumap-sumal);
            Wiersz ostatni = listawierszy.get(listawierszy.size()-1);
            ostatni.getStronaWn().setKwota(ostatni.getStronaWn().getKwota()+roznica);
        } catch (Exception e) {}
    }
    
    
    private static void dolaczwiersz2_3(WartosciVAT wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected, Map<String, Konto> kontadlaewidencji) {
         Wiersz wiersz2_3;
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT.vat, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT.vat, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT.vatwWalucie, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT.vatwWalucie, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                    }
                }
                wiersz2_3.setTabelanbp(selected.getTabelanbp());
                if (odliczenie0koszt1==0   && !selected.getRodzajedok().isTylkovatnalezny()) {
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null && kontovat.getRok()==selected.getRokInt()) {
                        wiersz2_3.getStronaWn().setKonto(kontovat);
                    } else {
                        wiersz2_3.getStronaWn().setKonto(kontadlaewidencji.get("221-3"));
                    }
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat");
                } else {
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat nie podl. odl.");
                    wiersz2_3.getStronaWn().setKonto(kontadlaewidencji.get("404-2"));
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")  && !selected.getRodzajedok().isTylkovatnalezny()) {
                        wiersz2_3.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                }
                selected.getListawierszy().add(wiersz2_3);
    }
    
    private static void dolaczwiersz2_3Koszt(WartosciVAT wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected,  Map<String, Konto> kontadlaewidencji) {
         Wiersz wiersz2_3;
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT.vatPlnkup, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vatPlnkup);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT.vatPlnkup, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT.vatWalutakup, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vatPlnkup);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT.vatWalutakup, 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                    }
                }
                wiersz2_3.setTabelanbp(selected.getTabelanbp());
                if (odliczenie0koszt1==0) {
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null && kontovat.getRok()==selected.getRokInt()) {
                        wiersz2_3.getStronaWn().setKonto(kontovat);
                    } else {
                        wiersz2_3.getStronaWn().setKonto(kontadlaewidencji.get("221-3"));
                    }
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat");
                } else {
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat nie podl. odl.");
                    wiersz2_3.getStronaWn().setKonto(kontadlaewidencji.get("404-2"));
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                }
                selected.getListawierszy().add(wiersz2_3);
    }
    
    public static void rozliczVatKosztNapraw(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView, Cechazapisu nkup, Konto kontoRozrachunkowe) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        StronaWiersza wn = wierszpierwszy.getStronaWn()!=null?wierszpierwszy.getStronaWn():new StronaWiersza(wierszpierwszy, "Wn");
        if (wierszpierwszy.getStronaWn()==null){
            wn.setKonto(selected.getRodzajedok().getKontoRZiS());
            wierszpierwszy.setStronaWn(wn);
        };
        StronaWiersza ma = wierszpierwszy.getStronaMa()!=null?wierszpierwszy.getStronaMa():new StronaWiersza(wierszpierwszy, "Ma");
        if (wierszpierwszy.getStronaMa()==null){
            ma.setKonto(kontoRozrachunkowe);
            ma.setNowatransakcja(true);
            ma.setTypStronaWiersza(1);
            wierszpierwszy.setStronaMa(ma);
        };
       try {
                wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                //gdy netto jest rowne 0, np korekta stawki vat
                if (wartosciVAT.netto == 0.0) {
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                } else {
                    //gdy netto jest wieksze od zera
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.netto);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.netto);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.netto + wartosciVAT.vat);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    } else {
                        wn.setKwota(wartosciVAT.nettowWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.nettowWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.nettowWalucie + wartosciVAT.vatwWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    }
                }
                int lpnastepnego = 2;
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                   wartosciVAT.vat = wartosciVAT.vatPlndodoliczenia;
                   wartosciVAT.vatwWalucie = wartosciVAT.vatWalutadodoliczenia;
                }
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                       dolaczwiersz2_3KosztEdit(wartosciVAT, w, 1, selected);
                       lpnastepnego++;
                }
                if (wpisView.isParamCzworkiPiatki() && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    wierszdrugi.getStronaWn().setKwota(wartosciVAT.netto);
                    dolaczwiersz2_3Edit(wartosciVAT, w, lpnastepnego+1, 0, selected);
                } else if (!wpisView.isParamCzworkiPiatki() && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                    if (selected.getRodzajedok().getProcentvat() != 0.0) {
                        dolaczwiersz2_3Edit(wartosciVAT, w, 2, 0, selected);
                    } else {
                        dolaczwiersz2_3Edit(wartosciVAT, w, 1, 0, selected);
                    }
                }
                int lp = wierszvatdoc.getLp();
                if (selected.getRodzajedok().getProcentkup()!=0.0 && nkup!=null) {
                    for (Iterator<StronaWiersza> it = selected.getStronyWierszy().iterator(); it.hasNext();) {
                        StronaWiersza p = it.next();
                        if (p.isWn()) {
                            if (p.getWiersz().getOpisWiersza().contains("- nkup") && p.getCechazapisuLista().contains(nkup)) {
                                selected.getListawierszy().remove(p.getWiersz());
                            }
                        }
                    }
                    rozliczobcieciekosztow(selected, nkup);
                }
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    public static void rozliczVatKosztNaprawRachunek(Dokfk selected, WpisView wpisView, Konto kontoRozrachunkowe) {
        try {
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            StronaWiersza wn = wierszpierwszy.getStronaWn()!=null?wierszpierwszy.getStronaWn():new StronaWiersza(wierszpierwszy, "Wn");
            if (wierszpierwszy.getStronaWn()==null){
                wn.setKonto(selected.getRodzajedok().getKontoRZiS());
                wierszpierwszy.setStronaWn(wn);
            };
            StronaWiersza ma = wierszpierwszy.getStronaMa()!=null?wierszpierwszy.getStronaMa():new StronaWiersza(wierszpierwszy, "Ma");
            if (wierszpierwszy.getStronaMa()==null){
                ma.setKonto(kontoRozrachunkowe);
                ma.setNowatransakcja(true);
                ma.setTypStronaWiersza(1);
                wierszpierwszy.setStronaMa(ma);
            };
            if (wn.getKwota()==0.0) {
                wn.setKwota(ma.getKwota());
                wn.setKwotaPLN(ma.getKwotaPLN());
            }
            if (ma.getKwota()==0.0) {
                ma.setKwota(wn.getKwota());
                ma.setKwotaPLN(wn.getKwotaPLN());
            }
            wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
            wierszpierwszy.setTabelanbp(selected.getTabelanbp());
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    public static void rozliczVatKosztNaprawWB(Wiersz wierszpierwszy,Dokfk selected) {
        try {
            Waluty w = selected.getWalutadokumentu();
            StronaWiersza wn = wierszpierwszy.getStronaWn()!=null?wierszpierwszy.getStronaWn():new StronaWiersza(wierszpierwszy, "Wn");
            if (wierszpierwszy.getTypWiersza()==0 && wierszpierwszy.getStronaWn()==null){
                wierszpierwszy.setStronaWn(wn);
            }
            StronaWiersza ma = wierszpierwszy.getStronaMa()!=null?wierszpierwszy.getStronaMa():new StronaWiersza(wierszpierwszy, "Ma");
            if (wierszpierwszy.getTypWiersza()==0 && wierszpierwszy.getStronaMa()==null){
                wierszpierwszy.setStronaMa(ma);
            };
            if (wierszpierwszy.getTypWiersza()!=0 && wierszpierwszy.getStronaWn()!=null && wierszpierwszy.getStronaWn().getKonto()==null){
                wierszpierwszy.setStronaWn(null);
            }
            if (wierszpierwszy.getTypWiersza()!=0 && wierszpierwszy.getStronaMa()!=null && wierszpierwszy.getStronaMa().getKonto()==null){
                wierszpierwszy.setStronaMa(null);
            }
            if (wierszpierwszy.getTypWiersza()==0 && wn.getKwota()==0.0) {
                wn.setKwota(ma.getKwota());
                wn.setKwotaPLN(ma.getKwotaPLN());
            }
            if (wierszpierwszy.getTypWiersza()==0 && ma.getKwota()==0.0) {
                ma.setKwota(wn.getKwota());
                ma.setKwotaPLN(wn.getKwotaPLN());
            }
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
      public static void rozliczVatKosztEdycja(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView, Cechazapisu nkup) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        try {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                //gdy netto jest rowne 0, np korekta stawki vat
                if (wartosciVAT.netto == 0.0) {
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                } else {
                    //gdy netto jest wieksze od zera
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT.netto);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.netto);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.netto + wartosciVAT.vat);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    } else {
                        wn.setKwota(wartosciVAT.nettowWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT.nettowWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto);
                        } else {
                            ma.setKwota(wartosciVAT.nettowWalucie + wartosciVAT.vatwWalucie);
                            ma.setKwotaPLN(wartosciVAT.netto + wartosciVAT.vat);
                        }
                    }
                }
                int lpnastepnego = 2;
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                   wartosciVAT.vat = wartosciVAT.vatPlndodoliczenia;
                   wartosciVAT.vatwWalucie = wartosciVAT.vatWalutadodoliczenia;
                }
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                       dolaczwiersz2_3KosztEdit(wartosciVAT, w, 1, selected);
                       lpnastepnego++;
                }
                if (wpisView.isParamCzworkiPiatki() && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    wierszdrugi.getStronaWn().setKwota(wartosciVAT.netto);
                    dolaczwiersz2_3Edit(wartosciVAT, w, lpnastepnego+1, 0, selected);
                } else if (!wpisView.isParamCzworkiPiatki() && wartosciVAT.vat != 0.0 && wartosciVAT.netto != 0.0) {
                    if (selected.getRodzajedok().getProcentvat() != 0.0) {
                        dolaczwiersz2_3Edit(wartosciVAT, w, 2, 0, selected);
                    } else {
                        dolaczwiersz2_3Edit(wartosciVAT, w, 1, 0, selected);
                    }
                }
                int lp = wierszvatdoc.getLp();
                if (selected.getRodzajedok().getProcentkup()!=0.0 && nkup!=null) {
                    for (Iterator<StronaWiersza> it = selected.getStronyWierszy().iterator(); it.hasNext();) {
                        StronaWiersza p = it.next();
                        if (p.isWn()) {
                            if (p.getWiersz().getOpisWiersza().contains("- nkup") && p.getCechazapisuLista().contains(nkup)) {
                                selected.getListawierszy().remove(p.getWiersz());
                            }
                        }
                    }
                    rozliczobcieciekosztow(selected, nkup);
                }
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+lp+":netto");
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+lp+":brutto");
                PrimeFaces.current().ajax().update("formwpisdokument:dataList");
            
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    
    
    private static void dolaczwiersz2_3Edit(WartosciVAT wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected) {
         Wiersz wiersz2_3 = selected.getListawierszy().get(lp);
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vat);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT.vat);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vat);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatwWalucie);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT.vatwWalucie);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatwWalucie);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vat);
                    }
                }
    }
    
    
    
    private static void dolaczwiersz2_3KosztEdit(WartosciVAT wartosciVAT, Waluty w, int lp, Dokfk selected) {
         Wiersz wiersz2_3 = selected.getListawierszy().get(lp);
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vatPlnkup);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatWalutakup);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT.vatWalutakup);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT.vatPlnkup);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT.vatWalutakup);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT.vatPlnkup);
                    }
                }
    }
    
    
    public static List<Wiersz> rozliczVatKosztRK(EVatwpisFK ewidencjaVatRK, Dokfk selected, WpisView wpisView, int wierszRKindex, Map<String, Konto> kontadlaewidencji, Cechazapisu nkup, Konto kontorozrach, Konto kontonetto, TabelanbpDAO tabelanbpDAO) {
        SumyRK sumyrk = new SumyRK(ewidencjaVatRK);
        List<Wiersz> wiersze = sprawdzczyistniejawiersze(ewidencjaVatRK.getWiersz());
        int rozmiar = wiersze.size();
        if (rozmiar==1) {
            if (ewidencjaVatRK.isPaliwo()) {
                zrob50(wiersze, ewidencjaVatRK, kontorozrach, wierszRKindex, kontadlaewidencji.get("221-3"), kontadlaewidencji.get("404-2"), kontonetto, sumyrk, nkup, tabelanbpDAO);;
            } else if (ewidencjaVatRK.isKoszty75()) {
                zrob75(wiersze, ewidencjaVatRK, kontorozrach, wierszRKindex, kontadlaewidencji.get("221-3"), kontadlaewidencji.get("404-2"), kontonetto, sumyrk, nkup, tabelanbpDAO);;
            } else {
                zrob100(wiersze, ewidencjaVatRK, kontorozrach, wierszRKindex, kontadlaewidencji.get("221-3"), kontonetto, sumyrk, tabelanbpDAO);
            }
        } 
        if (rozmiar==2) {
            edit100(wiersze, ewidencjaVatRK, kontorozrach, kontadlaewidencji.get("221-3"), kontonetto, sumyrk);
        }
        if (rozmiar==3) {
            edit50(wiersze, ewidencjaVatRK, kontorozrach, kontadlaewidencji.get("221-3"), kontadlaewidencji.get("404-2"), kontonetto, sumyrk, nkup);;
        }
        if (rozmiar==5) {
            edit75(wiersze, ewidencjaVatRK, kontorozrach, kontadlaewidencji.get("221-3"), kontadlaewidencji.get("404-2"), kontonetto, sumyrk, nkup);;
        }
        sprawdzsumyobciecie(wiersze);
        return wiersze;
    }
            
    private static List<Wiersz> sprawdzczyistniejawiersze(Wiersz pierwszy) {
        List<Wiersz> zwrot = new ArrayList<>();
        Wiersz drugi = pierwszy.getWiersznastepny();
        Wiersz trzeci = drugi != null ? drugi.getWiersznastepny():null;
        Wiersz czwarty = trzeci != null ? trzeci.getWiersznastepny():null;
        Wiersz piaty = czwarty != null ? czwarty.getWiersznastepny():null;
        if (czwarty!=null&&piaty!=null) {
            zwrot.add(pierwszy);
            zwrot.add(drugi);
            zwrot.add(trzeci);
            zwrot.add(czwarty);
            zwrot.add(piaty);
        } else if (trzeci!=null) {
            zwrot.add(pierwszy);
            zwrot.add(drugi);
            zwrot.add(trzeci);
        } else if (drugi!=null) {
            zwrot.add(pierwszy);
            zwrot.add(drugi);
        } else {
            zwrot.add(pierwszy);
        }
        return zwrot;
    }
    
    private static void zrob100(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontorozrach, int wierszRKindex, Konto kontovat, Konto kontonetto, SumyRK sumyrk, TabelanbpDAO tabelanbpDAO) {
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontorozrach, kontonetto, sumyrk.getNetto(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getVat(), 1, wiersze.get(0), tabelanbpDAO);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat(), opis);
            wiersze.add(wierszdrugi);
        }
    }
    
    private static void edit100(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontodelegacja, Konto kontovat, Konto kontonetto, SumyRK sumyrk) {
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontodelegacja, kontonetto, sumyrk.getNetto(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = wiersze.get(1);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat(), opis);
        }
    }
    
    private static void zrob50(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontorozrach, int wierszRKindex, Konto kontovat, Konto kontovatnkup, Konto kontonetto, SumyRK sumyrk,  Cechazapisu nkup, TabelanbpDAO tabelanbpDAO) {
        redukujewidencje(ewidencjaVatRK, sumyrk);
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontorozrach, kontonetto, sumyrk.getNetto(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getNetto75(), 1, wiersze.get(0), tabelanbpDAO);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat50(), opis);
            wiersze.add(wierszdrugi);
            Wiersz wiersztrzeci = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getVat75koszt() , 1, wiersze.get(0), tabelanbpDAO);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz.";
            zrobkolejnywiersz(wiersze, wiersztrzeci, kontovatnkup, sumyrk.getVat50(), opis);
            wiersze.add(wiersztrzeci);
            wiersztrzeci.getStronaWn().getCechazapisuLista().add(nkup);
        }
    }
    
    private static void edit50(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontorozrach, Konto kontovat, Konto kontovatnkup, Konto kontonetto, SumyRK sumyrk,  Cechazapisu nkup) {
        redukujewidencje(ewidencjaVatRK, sumyrk);
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontorozrach, kontonetto, sumyrk.getNetto(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = wiersze.get(1);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat50(), opis);
            Wiersz wiersztrzeci = wiersze.get(2);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz.";
            zrobkolejnywiersz(wiersze, wiersztrzeci, kontovatnkup, sumyrk.getVat50(), opis);
            if (wiersztrzeci.getStronaWn().getCechazapisuLista()!=null && !wiersztrzeci.getStronaWn().getCechazapisuLista().contains(nkup)) {
                wiersztrzeci.getStronaWn().getCechazapisuLista().add(nkup);
            }
        }
    }
    
    private static void zrob75(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontorozrach, int wierszRKindex, Konto kontovat, Konto kontovatnkup, Konto kontonetto, SumyRK sumyrk,  Cechazapisu nkup, TabelanbpDAO tabelanbpDAO) {
        redukujewidencje(ewidencjaVatRK, sumyrk);
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontorozrach, kontonetto, sumyrk.getNetto75(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getVat50(), 1, wiersze.get(0), tabelanbpDAO);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat50(), opis);
            wiersze.add(wierszdrugi);
            Wiersz wiersztrzeci = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getVat75koszt(), 1, wiersze.get(0), tabelanbpDAO);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz.";
            zrobkolejnywiersz(wiersze, wiersztrzeci, kontovatnkup, sumyrk.getVat75koszt(), opis);
            wiersze.add(wiersztrzeci);
            Wiersz wierszczwarty = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getNetto25nkup(), 1, wiersze.get(0), tabelanbpDAO);
            opis = wiersze.get(0).getOpisWiersza() + " - netto nkup";
            zrobkolejnywiersz(wiersze, wierszczwarty, kontonetto, sumyrk.getNetto25nkup(), opis);
            wierszczwarty.getStronaWn().getCechazapisuLista().add(nkup);
            wiersze.add(wierszczwarty);
            Wiersz wierszpiaty = ObslugaWiersza.wygenerujiDodajWierszRK(wiersze.get(0).getDokfk(), wierszRKindex, true, sumyrk.getVat25nkup(), 1, wiersze.get(0), tabelanbpDAO);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz. nkup";
            zrobkolejnywiersz(wiersze, wierszpiaty, kontovatnkup, sumyrk.getNetto25nkup(), opis);
            wierszpiaty.getStronaWn().getCechazapisuLista().add(nkup);
            wiersze.add(wierszpiaty);
        }
    }
    
    private static void edit75(List<Wiersz> wiersze, EVatwpisFK ewidencjaVatRK, Konto kontorozrach, Konto kontovat, Konto kontovatnkup, Konto kontonetto, SumyRK sumyrk,  Cechazapisu nkup) {
        redukujewidencje(ewidencjaVatRK, sumyrk);
        zrobpierwszywiersz(wiersze.get(0), ewidencjaVatRK, kontorozrach, kontonetto, sumyrk.getNetto75(), sumyrk.getBrutto());
        if (ewidencjaVatRK.getVat() != 0.0) {
            Wiersz wierszdrugi = wiersze.get(1);
            String opis = wiersze.get(0).getOpisWiersza() + " - pod. vat";
            zrobkolejnywiersz(wiersze, wierszdrugi, kontovat, sumyrk.getVat50(), opis);
            Wiersz wiersztrzeci = wiersze.get(2);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz.";
            zrobkolejnywiersz(wiersze, wiersztrzeci, kontovatnkup, sumyrk.getVat75koszt(), opis);
            Wiersz wierszczwarty = wiersze.get(3);
            opis = wiersze.get(0).getOpisWiersza() + " - netto nkup";
            zrobkolejnywiersz(wiersze, wierszczwarty, kontonetto, sumyrk.getNetto25nkup(), opis);
            if (wierszczwarty.getStronaWn().getCechazapisuLista()!=null && !wierszczwarty.getStronaWn().getCechazapisuLista().contains(nkup)) {
                wierszczwarty.getStronaWn().getCechazapisuLista().add(nkup);
            }
            Wiersz wierszpiaty = wiersze.get(4);
            opis = wiersze.get(0).getOpisWiersza() + " - pod. vat nie podl.odlicz. nkup";
            zrobkolejnywiersz(wiersze, wierszpiaty, kontovatnkup, sumyrk.getVat25nkup(), opis);
            if (wierszpiaty.getStronaWn().getCechazapisuLista()!=null && !wierszpiaty.getStronaWn().getCechazapisuLista().contains(nkup)) {
                wierszpiaty.getStronaWn().getCechazapisuLista().add(nkup);
            }
        }
    }

    
    private static void zrobpierwszywiersz(Wiersz wiersz,  EVatwpisFK ewidencjaVatRK, Konto kontorozrach, Konto kontonetto, double netto, double brutto) {
        StronaWiersza wn = wiersz.getStronaWn();
        StronaWiersza ma = wiersz.getStronaMa();
        String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
        String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
        if (kontrnazwa == null) {
            kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
        }
        kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
        wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
        wiersz.setOpisWiersza(wierszpierwszyopis);
        wiersz.setTabelanbp(wiersz.getDokfk().getTabelanbp());
        wn.setKwota(netto);
        ma.setKwota(brutto);
        wn.setKwotaPLN(netto);
        ma.setKwotaPLN(brutto);
        ma.setKonto(kontorozrach);
        wn.setKonto(kontonetto);
    }
    
    
    private static Wiersz zrobkolejnywiersz(List<Wiersz> wiersze,  Wiersz wierszdrugi, Konto kontovat, double kwota, String opis) {
        Wiersz wierszpierwszy = wiersze.get(0);
        wierszdrugi.getStronaWn().setKwota(kwota);
        wierszdrugi.getStronaWn().setKwotaPLN(kwota);
        wierszdrugi.setOpisWiersza(opis);
        wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
        Konto kontovatdok = wierszpierwszy.getDokfk().getRodzajedok().getKontovat();
        if (kontovat != null) {
            wierszdrugi.getStronaWn().setKonto(kontovat);
        } else {
            wierszdrugi.getStronaWn().setKonto(kontovatdok);
        }
        return wierszdrugi;
    }

    
    private static void redukujewidencje(EVatwpisFK ewidencjaVatRK, SumyRK sumyrk) {
        ewidencjaVatRK.setVat(sumyrk.getVat50());
    }
    
    
    
    static class SumyRK {
        double netto;
        double vat;
        double brutto;
        double vat50;
        double netto75;
        double netto25nkup;
        double vat75koszt;
        double vat25nkup;
        

        protected SumyRK(EVatwpisFK ewidencjaVatRK) {
            this.netto = Z.z(ewidencjaVatRK.getNetto());
            this.vat = Z.z(ewidencjaVatRK.getVat());
            this.brutto =  Z.z(netto+vat);
            this.vat50 = Z.z(this.vat/2);
            this.netto25nkup = Z.z(this.netto*1/4);
            this.netto75 = Z.z(this.netto-this.netto25nkup);
            this.vat75koszt = Z.z(this.vat50*3/4);
            this.vat25nkup = Z.z(this.vat50-this.vat75koszt);
        }


        public double getNetto() {
            return netto;
        }

        public void setNetto(double netto) {
            this.netto = netto;
        }

        public double getVat() {
            return vat;
        }

        public void setVat(double vat) {
            this.vat = vat;
        }

        public double getBrutto() {
            return brutto;
        }

        public void setBrutto(double brutto) {
            this.brutto = brutto;
        }

        public double getVat50() {
            return vat50;
        }

        public void setVat50(double vat50) {
            this.vat50 = vat50;
        }

        public double getNetto75() {
            return netto75;
        }

        public void setNetto75(double netto75) {
            this.netto75 = netto75;
        }

        public double getNetto25nkup() {
            return netto25nkup;
        }

        public void setNetto25nkup(double netto25nkup) {
            this.netto25nkup = netto25nkup;
        }

        public double getVat75koszt() {
            return vat75koszt;
        }

        public void setVat75koszt(double vat75koszt) {
            this.vat75koszt = vat75koszt;
        }

        public double getVat25nkup() {
            return vat25nkup;
        }

        public void setVat25nkup(double vat25nkup) {
            this.vat25nkup = vat25nkup;
        }
        
        

    }
    
    public static void rozliczVatPrzychodNapraw(EVatwpisFK ewidencjaVatRK, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView, Konto kontoRozrachunkowe) {
        if (wartosciVAT.netto != 0 || wartosciVAT.nettowWalucie != 0) {
            String kontrnazwa = "";
            if (ewidencjaVatRK.getKlient()!=null) {
                kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona()!=null?ewidencjaVatRK.getKlient().getNskrocona():ewidencjaVatRK.getKlient().getNpelna();
                kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
            }
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
                StronaWiersza wn = wierszpierwszy.getStronaWn()!=null?wierszpierwszy.getStronaWn():new StronaWiersza(wierszpierwszy, "Wn");
                if (wierszpierwszy.getStronaWn()==null){
                    wn.setKonto(kontoRozrachunkowe);
                    wn.setNowatransakcja(true);
                    wn.setTypStronaWiersza(1);
                    wierszpierwszy.setStronaWn(wn);
                };
                StronaWiersza ma = wierszpierwszy.getStronaMa()!=null?wierszpierwszy.getStronaMa():new StronaWiersza(wierszpierwszy, "Ma");
                if (wierszpierwszy.getStronaMa()==null){
                    ma.setKonto(selected.getRodzajedok().getKontoRZiS());
                    wierszpierwszy.setStronaMa(ma);
                };
                if (wierszpierwszy != null && wartosciVAT.netto!=0) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.netto);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.netto+wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.nettowWalucie);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.nettowWalucie+wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    }
                    
                } else if (wierszpierwszy != null && wartosciVAT.netto==0) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                  }
               if (selected.getListawierszy().size()==2 && wartosciVAT.vat != 0 && wartosciVAT.netto != 0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT.vat);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT.vat);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT.vatwWalucie);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT.vatwWalucie);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    }
               }
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
    }

    
    
    public static List<Wiersz> rozliczVatPrzychodEdycja(EVatwpisFK ewidencjaVatRK, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView) {
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        if (wartosciVAT.netto != 0 || wartosciVAT.nettowWalucie != 0) {
            String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
            String kontrnazwa = "";
            if (ewidencjaVatRK.getKlient()!=null) {
                kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona()!=null?ewidencjaVatRK.getKlient().getNskrocona():ewidencjaVatRK.getKlient().getNpelna();
                kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
            }
            if (ewidencjaVatRK.getNumerwlasnydokfk()!=null&&ewidencjaVatRK.getOpisvat()!=null) {
                wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
            } else {
                wierszpierwszyopis = selected.getListawierszy().get(0).getOpisWiersza();
            }
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
                nowewiersze.add(wierszpierwszy);
                if (wierszpierwszy != null && wartosciVAT.netto!=0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(wierszpierwszyopis);
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.netto);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.netto+wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.nettowWalucie);
                        ma.setKwotaPLN(wartosciVAT.netto);
                        wn.setKwota(wartosciVAT.nettowWalucie+wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.netto+wartosciVAT.vat);
                    }
                    
                } else if (wierszpierwszy != null && wartosciVAT.netto==0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT.vat);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vat);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    } else {
                        ma.setKwota(wartosciVAT.vatwWalucie);
                        ma.setKwotaPLN(wartosciVAT.vat);
                        wn.setKwota(wartosciVAT.vatwWalucie);
                        wn.setKwotaPLN(wartosciVAT.vat);
                    }
                  }
               if (selected.getListawierszy().size()==2 && wartosciVAT.vat != 0 && wartosciVAT.netto != 0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    nowewiersze.add(wierszdrugi);
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT.vat);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT.vat);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    } else {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT.vatwWalucie);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT.vatwWalucie);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT.vat);
                    }
               }
                int index = ewidencjaVatRK.getLp()-1 < 0 ? 0 : ewidencjaVatRK.getLp()-1;
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+index+":netto");
                PrimeFaces.current().ajax().update("formwpisdokument:tablicavat:"+index+":brutto");
                PrimeFaces.current().ajax().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
        return nowewiersze;
    }
    
    public static List<Wiersz> rozliczVatPrzychodRK(EVatwpisFK ewidencjaVatRK, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk, Map<String, Konto> kontadlaewidencji, TabelanbpDAO tabelanbpDAO){
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        double nettovat = ewidencjaVatRK.getNetto();
        double kwotavat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            if (wierszpierwszy != null) {
                String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
                String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
                if (kontrnazwa == null) {
                    kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
                }
                kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
                wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                if (wierszpierwszy.getOpisWiersza().equals("")) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                }
                wierszpierwszy.setOpisWiersza(wierszpierwszyopis);
                ma.setKwota(nettovat);
                wn.setKwota(nettovat+kwotavat);
                if (kontoRozrachunkowe != null) {
                    wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                }
                Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                if (kontonetto != null) {
                    wierszpierwszy.getStronaMa().setKonto(kontonetto);
                }
                nowewiersze.add(wierszpierwszy);
            }
            if (kwotavat != 0.0) {
                Wiersz wierszdrugi;
                boolean jestjuzwierszvat = false;
                for (Wiersz p : selected.getListawierszy()) {
                    if (p.getTypWiersza() == 1) {
                        if (p.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                            if (p.getStronaWn().getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                                jestjuzwierszvat = true;
                            }
                        }
                    }
                }
                wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, kwotavat, 2, wierszpierwszy, tabelanbpDAO);
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                Konto kontovat = selected.getRodzajedok().getKontovat();
                if (kontovat != null && kontovat.getRok()==selected.getRokInt()) {
                    wierszdrugi.getStronaMa().setKonto(kontovat);
                } else {
                    wierszdrugi.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                }
                nowewiersze.add(wierszdrugi);
            }
        } else {
            Msg.msg("w", "Brak Zdefiniowanego konta kasy. Nie można generować zapisów VAT.");
        }
        return nowewiersze;
    }
    
    public static List<Wiersz> rozliczEdytujVatPrzychodRK(EVatwpisFK ewidencjaVatRK, Dokfk selected, int wierszRKindex){
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        double nettovat = ewidencjaVatRK.getNetto();
        double kwotavat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                ma.setKwota(nettovat);
                wn.setKwota(nettovat+kwotavat);
                nowewiersze.add(wierszpierwszy);
            }
            if (kwotavat != 0.0) {
                Wiersz wierszdrugi = selected.nastepnyWiersz(wierszpierwszy);
                if (wierszdrugi != null && wierszdrugi.getLpmacierzystego()==wierszpierwszy.getIdporzadkowy()) {
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                wierszdrugi.getStronaMa().setKwota(kwotavat);
                nowewiersze.add(wierszdrugi);
            }
        } else {
            Msg.msg("w", "Brak Zdefiniowanego konta kasy. Nie można generować zapisów VAT.");
        }
    }
        return nowewiersze;
    }

   
    

    

    
    
}
