/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;
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
    
    public static double[] podsumujwartosciVATRK(EVatwpisFK ewidencja) {
        double[] wartosciVAT = new double[4];
        wartosciVAT[0] += ewidencja.getNetto();
        wartosciVAT[1] += ewidencja.getVat();
        wartosciVAT[2] += ewidencja.getNettowwalucie();
        wartosciVAT[3] += ewidencja.getVatwwalucie();
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
                    if (kontovat != null) {
                        wierszdrugi.getStronaMa().setKonto(kontovat);
                    } else {
                        wierszdrugi.getStronaMa().setKonto(kontadlaewidencji.get("221-1"));
                    }
                    selected.getListawierszy().add(wierszdrugi);
               }
                pobierzkontaZpoprzedniegoDokumentu(poprzedniDokument, selected);
                int index = wierszvatdoc.getLp()-1 < 0 ? 0 : wierszvatdoc.getLp()-1;
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
    }
    
     public static Konto pobierzKontoRozrachunkowe(KliencifkDAO kliencifkDAO, Dokfk selected, WpisView wpisView, KontoDAOfk kontoDAOfk) {
        try {
            //to znajdujemy polaczenie konta z klientem nazwa tego polaczenia to Kliencifk
            Kliencifk symbolSlownikowyKonta = kliencifkDAO.znajdzkontofk(selected.getKontr().getNip(), wpisView.getPodatnikObiekt().getNip());
            List<Konto> listakont = kontoDAOfk.findKontaNazwaPodatnik(symbolSlownikowyKonta.getNip(), wpisView);
            if (listakont == null || listakont.size() == 0) {
                throw new Exception();
            }
            Konto kontoprzyporzadkowaneDoRodzajuDok = selected.getRodzajedok().getKontorozrachunkowe();
            Konto konto = null;
            for (Konto p : listakont) {
                if (kontoprzyporzadkowaneDoRodzajuDok.equals(p.getKontomacierzyste())) {
                    konto = p;
                    break;
                }
            }
            return konto;
        } catch (Exception e) {  
            Msg.msg("e", "Brak w konatch słownikowych danego kontrahenta. Zweryfikuj plan kont czy sa podpiete slowniki");
            return null;
        }
    }
     
     private static void pobierzkontaZpoprzedniegoDokumentu(Dokfk poprzedniDokument, Dokfk selected) {
        try {
            if (poprzedniDokument != null) {
                for (int i = 0; i < 3; i++) {
                    Wiersz wierszDokumentuPoprzedniego = poprzedniDokument.getListawierszy().get(i);
                    Wiersz wierszDokumentuBiezacego = selected.getListawierszy().get(i);
                    if (wierszDokumentuPoprzedniego != null && wierszDokumentuBiezacego != null) {
                        StronaWiersza wnDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaWn();
                        StronaWiersza wnDokumenuBiezacego = wierszDokumentuBiezacego.getStronaWn();
                        if (wnDokumenuBiezacego != null && !wnDokumenuBiezacego.getKonto().getZwyklerozrachszczegolne().equals("vat") && !selected.getRodzajedok().isTylkovatnalezny()) {
                            wnDokumenuBiezacego.setKonto(wnDokumentuPoprzedniego.getKonto());
                        }
                        StronaWiersza maDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaMa();
                        StronaWiersza maDokumenuBiezacego = wierszDokumentuBiezacego.getStronaMa();
                        if (maDokumenuBiezacego != null && !maDokumenuBiezacego.getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                            maDokumenuBiezacego.setKonto(maDokumentuPoprzedniego.getKonto());
                        }
                    }
                }
            }
        } catch (Exception e) { 
            E.e(e);
        }
     }
     
     // tu oblicza sie vaty i dodaje wiersze
    public static void rozliczVatKoszt(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, Map<String, Konto> kontadlaewidencji, WpisView wpisView, Dokfk poprzedniDokument, Konto kontoRozrachunkowe) {
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
                    }
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
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
            RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:netto");
            RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:brutto");
            RequestContext.getCurrentInstance().update("formwpisdokument:dataList");

        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
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
                    if (kontovat != null) {
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
                    if (kontovat != null) {
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
    
      public static void rozliczVatKosztEdycja(EVatwpisFK wierszvatdoc, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView) {
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
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+lp+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+lp+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            
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
    
    public static List<Wiersz> rozliczVatKosztRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk,Map<String, Konto> kontadlaewidencji) {
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        double nettoEwidVat = ewidencjaVatRK.getNetto();
        double vatEwidVat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = null;
        String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
        String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
        if (kontrnazwa == null) {
            kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
        }
        kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
        wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
        if (selected.getRodzajedok().getSkrotNazwyDok().equals("DEL")) {
            try {
                kontoRozrachunkowe = kontoDAOfk.findKontoNazwaPelnaPodatnik(selected.getNumerwlasnydokfk(), wpisView);
            } catch (Exception e) {
                
            }
        }
        if (kontoRozrachunkowe == null) {
            kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        }
            //nie chodzi ze jest pierwszy, tylko ze jest zainicjalizowany
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                wierszpierwszy.setOpisWiersza(wierszpierwszyopis);
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                wn.setKwota(nettoEwidVat);
                ma.setKwota(nettoEwidVat + vatEwidVat);
                if (kontoRozrachunkowe != null) {
                    wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                }
                Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                if (kontonetto != null) {
                    wierszpierwszy.getStronaWn().setKonto(kontonetto);
                }
                nowewiersze.add(wierszpierwszy);
            }
            if (vatEwidVat != 0.0) {
                Wiersz wierszdrugi = selected.nastepnyWiersz(wierszpierwszy);
                boolean jestjuzwierszvat = false;
                if (wierszdrugi != null) {
                    if (wierszdrugi.getTypWiersza() == 1) {
                        if (wierszdrugi.getStronaWn().getKonto().getZwyklerozrachszczegolne().equals("vat")) {
                            jestjuzwierszvat = true;
                        }
                    }
                }
                double vatodliczenie = Z.z(vatEwidVat / 2.0);
                double vatkoszt = Z.z(vatEwidVat - vatodliczenie);
                if (ewidencjaVatRK.isPaliwo()) {
                    ewidencjaVatRK.setVat(vatodliczenie);
                }
                if (jestjuzwierszvat == false) {
                    if (ewidencjaVatRK.isPaliwo()) {
                        wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatodliczenie, 1);
                        wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat podl. odlicz.");
                    } else {
                        wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatEwidVat, 1);
                        wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                    }
                } else {
                    if (ewidencjaVatRK.isPaliwo()) {
                        wierszdrugi.getStronaWn().setKwota(vatodliczenie);
                    } else {
                        wierszdrugi.getStronaWn().setKwota(vatEwidVat);
                    }
                }
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                Konto kontovat = selected.getRodzajedok().getKontovat();
                if (kontovat != null) {
                    wierszdrugi.getStronaWn().setKonto(kontovat);
                } else {
                    wierszdrugi.getStronaWn().setKonto(kontadlaewidencji.get("221-3"));
                }
                nowewiersze.add(wierszdrugi);
                if (ewidencjaVatRK.isPaliwo()) {
                    Wiersz wiersztrzeci = selected.nastepnyWiersz(wierszdrugi);
                    jestjuzwierszvat = false;
                    if (wiersztrzeci != null) {
                        if (wiersztrzeci.getTypWiersza() == 1) {
                            if (wierszdrugi.getStronaWn().getKonto().getZwyklerozrachszczegolne().contains("vat")) {
                                jestjuzwierszvat = true;
                            }
                        }
                    }
                    if (jestjuzwierszvat) {
                        wiersztrzeci.getStronaWn().setKwota(vatkoszt);
                    } else {
                        wiersztrzeci = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatkoszt, 1);
                    }
                    wiersztrzeci.setTabelanbp(selected.getTabelanbp());
                    wiersztrzeci.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                    wiersztrzeci.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat k.u.p.");
                    wiersztrzeci.getStronaWn().setKonto(kontadlaewidencji.get("404-2"));
                    nowewiersze.add(wiersztrzeci);
                }
            }
        return nowewiersze;
    }
    
     public static List<Wiersz> rozliczEdytujVatKosztRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, int wierszRKindex, Map<String, Konto> kontadlaewidencji) {
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        double nettoEwidVat = ewidencjaVatRK.getNetto();
        double vatEwidVat = ewidencjaVatRK.getVat();
        String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
        String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
        if (kontrnazwa == null) {
            kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
        }
        kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
        wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            //nie chodzi ze jest pierwszy, tylko ze jest zainicjalizowany
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                wn.setKwota(nettoEwidVat);
                ma.setKwota(nettoEwidVat + vatEwidVat);
                nowewiersze.add(wierszpierwszy);
                wierszpierwszy.setOpisWiersza(wierszpierwszyopis);
            }
             if (vatEwidVat != 0.0) {
                double vatodliczenie = Z.z(vatEwidVat / 2.0);
                double vatkoszt = Z.z(vatEwidVat - vatodliczenie);
                Wiersz wierszdrugi = selected.nastepnyWiersz(wierszpierwszy);
                if (wierszdrugi != null && wierszdrugi.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                     if (ewidencjaVatRK.isPaliwo()) {
                         ewidencjaVatRK.setVat(vatodliczenie);
                         ewidencjaVatRK.setBrutto(nettoEwidVat + vatodliczenie);
                                             }
                     if (ewidencjaVatRK.isPaliwo()) {
                         wierszdrugi.getStronaWn().setKwota(vatodliczenie);
                         wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat podl. odlicz.");
                     } else {
                         wierszdrugi.getStronaWn().setKwota(vatEwidVat);
                         wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                     }
                 }
                 wierszdrugi.setTabelanbp(selected.getTabelanbp());
                 wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                 nowewiersze.add(wierszdrugi);
                 if (ewidencjaVatRK.isPaliwo()) {
                     Wiersz wiersztrzeci = selected.nastepnyWiersz(wierszdrugi);
                     if (wiersztrzeci != null && wiersztrzeci.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                         wiersztrzeci.getStronaWn().setKwota(vatkoszt);
                     } else {
                        wiersztrzeci = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, vatkoszt, 1);
                        wiersztrzeci.getStronaWn().setKonto(kontadlaewidencji.get("404-2"));
                     }
                     wiersztrzeci.setTabelanbp(selected.getTabelanbp());
                     wiersztrzeci.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                     wiersztrzeci.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat k.u.p.");
                     nowewiersze.add(wiersztrzeci);
                 } else if (!ewidencjaVatRK.isPaliwo()) {
                     Wiersz wiersztrzeci = selected.nastepnyWiersz(wierszdrugi);
                     if (wiersztrzeci != null && wiersztrzeci.getLpmacierzystego() == wierszpierwszy.getIdporzadkowy()) {
                         selected.getListawierszy().remove(wiersztrzeci);
                     }
                 }
             }
         } else {
             Msg.msg("w", "Brak Zdefiniowanego konta rozrachunkowego. Nie można generować zapisów VAT.");
         }
        return nowewiersze;
    }
    
    
    
    public static List<Wiersz> rozliczVatPrzychodEdycja(EVatwpisFK ewidencjaVatRK, WartosciVAT wartosciVAT, Dokfk selected, WpisView wpisView) {
        List<Wiersz> nowewiersze = Collections.synchronizedList(new ArrayList<>());
        if (wartosciVAT.netto != 0 || wartosciVAT.nettowWalucie != 0) {
            String wierszpierwszyopis = ewidencjaVatRK.getNumerwlasnydokfk()+", "+ewidencjaVatRK.getOpisvat()+", ";
            String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
            if (kontrnazwa == null) {
                kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
            }
            kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
            wierszpierwszyopis = wierszpierwszyopis+kontrnazwa;
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
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
        return nowewiersze;
    }
    
    public static List<Wiersz> rozliczVatPrzychodRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk, Map<String, Konto> kontadlaewidencji){
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
                wierszdrugi = ObslugaWiersza.wygenerujiDodajWierszRK(selected, wierszRKindex, true, kwotavat, 2);
                wierszdrugi.setTabelanbp(selected.getTabelanbp());
                wierszdrugi.setDataWalutyWiersza(wierszpierwszy.getDataWalutyWiersza());
                wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                Konto kontovat = selected.getRodzajedok().getKontovat();
                if (kontovat != null) {
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
    
    public static List<Wiersz> rozliczEdytujVatPrzychodRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, int wierszRKindex){
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
