/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import entity.Wpis;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;
import viewfk.subroutines.ObslugaWiersza;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Stateless
public class DokFKVATBean {
    
    public static Double pobierzstawke(EVatwpisFK evatwpis) {
        String stawkavat = null;
        try {
            stawkavat = evatwpis.getEwidencja().getNazwa().replaceAll("[^\\d]", "");
            return Double.parseDouble(stawkavat) / 100;
        } catch (Exception e) {
            E.e(e);
            stawkavat = "23";
            return Double.parseDouble(stawkavat) / 100;
        }
        
    }
    
    public static void ustawvat(EVatwpisFK evatwpis,  Dokfk selected) {
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String rodzajdok = selected.getRodzajedok().getSkrot();
        double stawkavat = DokFKVATBean.pobierzstawke(evatwpis);
         if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto()/kurs);
            if (evatwpis.getNettowwalucie()!= obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto()*kurs));
            }
        }
        if (rodzajdok.contains("WDT") || rodzajdok.contains("UPTK") || rodzajdok.contains("EXP")) {
            evatwpis.setVat(0.0);
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
        if (!w.getSymbolwaluty().equals("PLN")) {
            //ten vat tu musi byc bo inaczej bylby onblur przy vat i cykliczne odswiezanie
            evatwpis.setVatwwalucie(Z.z(evatwpis.getVat()/kurs));
        }
    }
    public static void ustawvat(EVatwpisFK evatwpis, Dokfk selected, double stawkavat) {
        String skrotRT = selected.getDokfkPK().getSeriadokfk();
        int lp = evatwpis.getLp();
        Waluty w = selected.getWalutadokumentu();
        double kurs = selected.getTabelanbp().getKurssredni();
        //obliczamy VAT/NETTO w PLN i zachowujemy NETTO w walucie
        String opis = evatwpis.getEwidencja().getNazwa();
        if (!w.getSymbolwaluty().equals("PLN")) {
            double obliczonenettowpln = Z.z(evatwpis.getNetto() / kurs);
            if (evatwpis.getNettowwalucie() != obliczonenettowpln || evatwpis.getNettowwalucie() == 0) {
                evatwpis.setNettowwalucie(evatwpis.getNetto());
                evatwpis.setNetto(Z.z(evatwpis.getNetto() * kurs));
            }
        }
        if (opis.contains("WDT") || opis.contains("UPTK") || opis.contains("EXP")) {
            evatwpis.setVat(0.0);
        } else if (selected.getRodzajedok().getProcentvat() != 0.0 && evatwpis.getEwidencja().getTypewidencji().equals("z")) {
            evatwpis.setVat(Z.z((evatwpis.getNetto() * 0.23) / 2));
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
    }
            
    public static double[] podsumujwartosciVAT(List<EVatwpisFK> ewidencja) {
        double[] wartosciVAT = new double[8];
        for (EVatwpisFK p : ewidencja) {
            if (p.getDokfk().getRodzajedok().getProcentvat() != 0.0) {
                wartosciVAT[0] += p.getNetto();
                wartosciVAT[1] += p.getVat();
                wartosciVAT[2] += p.getNettowwalucie();
                wartosciVAT[3] += p.getVatwwalucie();
                double vatplnpolowa = Z.z(p.getVat()*p.getDokfk().getRodzajedok().getProcentvat()/100);
                double vatplnreszta = p.getVat() - vatplnpolowa;
                wartosciVAT[4] += vatplnpolowa;
                wartosciVAT[5] += vatplnreszta;
                double vatpolowa = Z.z(p.getVatwwalucie()*p.getDokfk().getRodzajedok().getProcentvat()/100);
                double vatreszta = Z.z(p.getVatwwalucie()-vatpolowa);
                wartosciVAT[6] += vatpolowa;
                wartosciVAT[7] += vatreszta;
            } else {
                wartosciVAT[0] += p.getNetto();
                wartosciVAT[1] += p.getVat();
                wartosciVAT[2] += p.getNettowwalucie();
                wartosciVAT[3] += p.getVatwwalucie();
                double vatplnpolowa = Z.z(p.getVat()/2);
                double vatplnreszta = Z.z(p.getVat()-vatplnpolowa);
                wartosciVAT[4] += vatplnpolowa;
                wartosciVAT[5] += vatplnreszta;
                double vatpolowa = Z.z(p.getVatwwalucie()/2);
                double vatreszta = Z.z(p.getVatwwalucie()-vatpolowa);
                wartosciVAT[6] += vatpolowa;
                wartosciVAT[7] += vatreszta;
            }
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
    
    public static void rozliczVatPrzychod(EVatwpisFK wierszvatdoc, double[] wartosciVAT, Dokfk selected, KliencifkDAO kliencifkDAO, KontoDAOfk  kontoDAOfk, WpisView wpisView, DokDAOfk dAOfk) {
        if (wartosciVAT[0] != 0 || wartosciVAT[2] != 0) {
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
            Konto kontoRozrachunkowe = pobierzKontoRozrachunkowe(kliencifkDAO, selected, wpisView, kontoDAOfk);
            if (kontoRozrachunkowe == null) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
                if (wierszpierwszy != null && wartosciVAT[0]!=0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[0]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[0]+wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[2]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[2]+wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontonetto = selected.getRodzajedok().getKontoRZiS();
                    if (kontonetto != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontonetto);
                    }
                    DFKWiersze.zaznaczNowaTrasakcja(wierszpierwszy, "Wn");
                } else if (wierszpierwszy != null && wartosciVAT[0]==0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[1]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[3]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    }
                    if (kontoRozrachunkowe != null) {
                        wierszpierwszy.getStronaWn().setKonto(kontoRozrachunkowe);
                    }
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszpierwszy.getStronaMa().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wierszpierwszy.getStronaMa().setKonto(k);
                    }
                }
               if (selected.getListawierszy().size()==1 && wartosciVAT[1] != 0 && wartosciVAT[0] != 0) {
                    Wiersz wierszdrugi;
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT[1], 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wierszdrugi = ObslugaWiersza.utworzNowyWierszMa(selected, 2, wartosciVAT[3], 1);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    }
                    wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza()+" - podatek vat");
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wierszdrugi.getStronaMa().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wierszdrugi.getStronaMa().setKonto(k);
                    }
                    selected.getListawierszy().add(wierszdrugi);
               }
                pobierzkontaZpoprzedniegoDokumentu(dAOfk, wpisView, selected);
                int index = wierszvatdoc.getLp()-1 < 0 ? 0 : wierszvatdoc.getLp()-1;
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
    }
    
     private static Konto pobierzKontoRozrachunkowe(KliencifkDAO kliencifkDAO, Dokfk selected, WpisView wpisView, KontoDAOfk kontoDAOfk) {
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
                if (kontoprzyporzadkowaneDoRodzajuDok.getPelnynumer().equals(p.getMacierzyste())) {
                    konto = p;
                    break;
                }
            }
            return konto;
        } catch (Exception e) {  
            System.out.println("Brak w konatch słownikowych danego kontrahenta. Zweryfikuj plan kont czy sa podpiete slowniki "+e.getStackTrace()[0].toString()+" "+e.toString());
            Msg.msg("e", "Brak w konatch słownikowych danego kontrahenta. Zweryfikuj plan kont czy sa podpiete slowniki");
            return null;
        }
    }
     
     private static void pobierzkontaZpoprzedniegoDokumentu(DokDAOfk dokDAOfk, WpisView wpisView, Dokfk selected) {
        try {
            Dokfk poprzedniDokument = dokDAOfk.findDokfkLastofaTypeKontrahent(wpisView.getPodatnikObiekt().getNip(), selected.getRodzajedok().getSkrot(), selected.getKontr(), wpisView.getRokWpisuSt());
            if (poprzedniDokument != null) {
                for (int i = 0; i < 3; i++) {
                    Wiersz wierszDokumentuPoprzedniego = poprzedniDokument.getListawierszy().get(i);
                    Wiersz wierszDokumentuBiezacego = selected.getListawierszy().get(i);
                    if (wierszDokumentuPoprzedniego != null && wierszDokumentuBiezacego != null) {
                        StronaWiersza wnDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaWn();
                        StronaWiersza wnDokumenuBiezacego = wierszDokumentuBiezacego.getStronaWn();
                        if (wnDokumenuBiezacego != null) {
                            wnDokumenuBiezacego.setKonto(wnDokumentuPoprzedniego.getKonto());
                        }
                        StronaWiersza maDokumentuPoprzedniego = wierszDokumentuPoprzedniego.getStronaMa();
                        StronaWiersza maDokumenuBiezacego = wierszDokumentuBiezacego.getStronaMa();
                        if (maDokumenuBiezacego != null) {
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
    public static void rozliczVatKoszt(EVatwpisFK wierszvatdoc, double[] wartosciVAT, Dokfk selected, KliencifkDAO kliencifkDAO, KontoDAOfk  kontoDAOfk, WpisView wpisView, DokDAOfk dAOfk) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        try {
            Konto kontoRozrachunkowe = pobierzKontoRozrachunkowe(kliencifkDAO, selected, wpisView, kontoDAOfk);
            if (kontoRozrachunkowe == null) {
                System.out.println("Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu.");
            }
                if (wierszpierwszy != null && wierszpierwszy.getStronaWn().getKwota() == 0.0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                    //gdy netto jest rowne 0, np korekta stawki vat
                    if (wartosciVAT[0] == 0.0) {
                        if (w.getSymbolwaluty().equals("PLN")) {
                            wn.setKwota(wartosciVAT[1]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                            ma.setKwota(wartosciVAT[1]);
                            ma.setKwotaPLN(wartosciVAT[1]);
                        } else {
                            wn.setKwota(wartosciVAT[3]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                            ma.setKwota(wartosciVAT[3]);
                            wn.setKwotaPLN(wartosciVAT[1]);
                        }
                        if (kontoRozrachunkowe != null) {
                            wierszpierwszy.getStronaMa().setKonto(kontoRozrachunkowe);
                        }
                        Konto kontovat = selected.getRodzajedok().getKontovat();
                        if (kontovat != null) {
                            wierszpierwszy.getStronaWn().setKonto(kontovat);
                        } else {
                            Konto k = kontoDAOfk.findKonto("221", wpisView);
                            wierszpierwszy.getStronaWn().setKonto(k);
                        }
                    } else {
                        //gdy netto jest wieksze od zera
                        if (w.getSymbolwaluty().equals("PLN")) {
                            wn.setKwota(wartosciVAT[0]);
                            wn.setKwotaPLN(wartosciVAT[0]);
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                                ma.setKwota(wartosciVAT[0]);
                                ma.setKwotaPLN(wartosciVAT[0]);
                            } else {
                                ma.setKwota(wartosciVAT[0] + wartosciVAT[1]);
                                ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                            }
                        } else {
                            wn.setKwota(wartosciVAT[2]);
                            wn.setKwotaPLN(wartosciVAT[0]);
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                                ma.setKwota(wartosciVAT[2]);
                                ma.setKwotaPLN(wartosciVAT[0]);
                            } else {
                                ma.setKwota(wartosciVAT[2] + wartosciVAT[3]);
                                ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
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
                   wartosciVAT[1] = wartosciVAT[4];
                   wartosciVAT[3] = wartosciVAT[6];
                }
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                       dolaczwiersz2_3Koszt(wartosciVAT, w, 2, 1, selected, kontoDAOfk, wpisView);
                       lpnastepnego++;
                       limitwierszy++;
                }
                if (wpisView.isFKpiatki() && selected.getListawierszy().size() == limitwierszy && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
                    Wiersz wierszdrugi;
                    wierszdrugi = ObslugaWiersza.utworzNowyWiersz5(selected, 2, wartosciVAT[0], 1);
                    wierszdrugi.setTabelanbp(selected.getTabelanbp());
                    wierszdrugi.getStronaWn().setKwota(wartosciVAT[0]);
                    wierszdrugi.setOpisWiersza(wierszpierwszy.getOpisWiersza() + " - pod. vat");
                    wierszdrugi.setCzworka(wierszpierwszy);
                    wierszpierwszy.getPiatki().add(wierszdrugi);
                    Konto k = kontoDAOfk.findKonto("490", wpisView);
                    wierszdrugi.getStronaMa().setKonto(k);
                    selected.getListawierszy().add(wierszdrugi);
                    dolaczwiersz2_3(wartosciVAT, w, lpnastepnego+1, 0, selected, kontoDAOfk, wpisView);
                } else if (!wpisView.isFKpiatki() && selected.getListawierszy().size() == limitwierszy && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
                    dolaczwiersz2_3(wartosciVAT, w, lpnastepnego, 0, selected, kontoDAOfk, wpisView);
                }
                pobierzkontaZpoprzedniegoDokumentu(dAOfk, wpisView, selected);
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:0:brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            
        } catch (Exception e1) {
            Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
        }
    }
    
    private static void dolaczwiersz2_3(double[] wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         Wiersz wiersz2_3;
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[3], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[3], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                }
                wiersz2_3.setTabelanbp(selected.getTabelanbp());
                if (odliczenie0koszt1==0) {
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wiersz2_3.getStronaWn().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wiersz2_3.getStronaWn().setKonto(k);
                    }
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat");
                } else {
                    Konto k = kontoDAOfk.findKonto("404-2", wpisView);
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat nie podl. odl.");
                    wiersz2_3.getStronaWn().setKonto(k);
                }
                selected.getListawierszy().add(wiersz2_3);
    }
    
    private static void dolaczwiersz2_3Koszt(double[] wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         Wiersz wiersz2_3;
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[5], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[5]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[5], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[7], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[5]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[7], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                    }
                }
                wiersz2_3.setTabelanbp(selected.getTabelanbp());
                if (odliczenie0koszt1==0) {
                    Konto kontovat = selected.getRodzajedok().getKontovat();
                    if (kontovat != null) {
                        wiersz2_3.getStronaWn().setKonto(kontovat);
                    } else {
                        Konto k = kontoDAOfk.findKonto("221", wpisView);
                        wiersz2_3.getStronaWn().setKonto(k);
                    }
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat");
                } else {
                    Konto k = kontoDAOfk.findKonto("404-2", wpisView);
                    wiersz2_3.setOpisWiersza(selected.getOpisdokfk() + " - podatek vat nie podl. odl.");
                    wiersz2_3.getStronaWn().setKonto(k);
                }
                selected.getListawierszy().add(wiersz2_3);
    }
    
      public static void rozliczVatKosztEdycja(EVatwpisFK wierszvatdoc, double[] wartosciVAT, Dokfk selected, WpisView wpisView) {
        Wiersz wierszpierwszy = selected.getListawierszy().get(0);
        Waluty w = selected.getWalutadokumentu();
        try {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                wierszpierwszy.setTabelanbp(selected.getTabelanbp());
                //gdy netto jest rowne 0, np korekta stawki vat
                if (wartosciVAT[0] == 0.0) {
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                        ma.setKwota(wartosciVAT[1]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wn.setKwota(wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                        ma.setKwota(wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    }
                } else {
                    //gdy netto jest wieksze od zera
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wn.setKwota(wartosciVAT[0]);
                        wn.setKwotaPLN(wartosciVAT[0]);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT[0]);
                            ma.setKwotaPLN(wartosciVAT[0]);
                        } else {
                            ma.setKwota(wartosciVAT[0] + wartosciVAT[1]);
                            ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                        }
                    } else {
                        wn.setKwota(wartosciVAT[2]);
                        wn.setKwotaPLN(wartosciVAT[0]);
                        if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                            ma.setKwota(wartosciVAT[2]);
                            ma.setKwotaPLN(wartosciVAT[0]);
                        } else {
                            ma.setKwota(wartosciVAT[2] + wartosciVAT[3]);
                            ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                        }
                    }
                }
                int lpnastepnego = 2;
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                   wartosciVAT[1] = wartosciVAT[4];
                   wartosciVAT[3] = wartosciVAT[6];
                }
                if (selected.getRodzajedok().getProcentvat() != 0.0) {
                       dolaczwiersz2_3KosztEdit(wartosciVAT, w, 1, 1, selected);
                       lpnastepnego++;
                }
                if (wpisView.isFKpiatki() && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    wierszdrugi.getStronaWn().setKwota(wartosciVAT[0]);
                    dolaczwiersz2_3Edit(wartosciVAT, w, lpnastepnego+1, 0, selected);
                } else if (!wpisView.isFKpiatki() && wartosciVAT[1] != 0.0 && wartosciVAT[0] != 0.0) {
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
    
    
    
    private static void dolaczwiersz2_3Edit(double[] wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected) {
         Wiersz wiersz2_3 = selected.getListawierszy().get(lp);
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT[1]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[1]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[3]);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT[3]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[3]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                }
    }
    
    
    
    private static void dolaczwiersz2_3KosztEdit(double[] wartosciVAT, Waluty w, int lp, int odliczenie0koszt1, Dokfk selected) {
         Wiersz wiersz2_3 = selected.getListawierszy().get(lp);
                if (w.getSymbolwaluty().equals("PLN")) {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT[5]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[5]);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[5]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().contains("import usług") || selected.getRodzajedok().getRodzajtransakcji().equals("odwrotne obciążenie")) {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[7]);
                        wiersz2_3.getStronaMa().setKwota(wartosciVAT[7]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[5]);
                    } else {
                        wiersz2_3.getStronaWn().setKwota(wartosciVAT[7]);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                    }
                }
    }
    
    public static List<Wiersz> rozliczVatKosztRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk) {
        List<Wiersz> nowewiersze = new ArrayList<>();
        double nettoEwidVat = ewidencjaVatRK.getNetto();
        double vatEwidVat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            //nie chodzi ze jest pierwszy, tylko ze jest zainicjalizowany
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                if (wierszpierwszy.getOpisWiersza().equals("")) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                }
                String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
                if (kontrnazwa == null) {
                    kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
                }
                kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
                wierszpierwszy.setOpisWiersza(wierszpierwszy.getOpisWiersza()+" "+kontrnazwa);
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
                    Konto k = kontoDAOfk.findKonto("221", wpisView);
                    wierszdrugi.getStronaWn().setKonto(k);
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
                    Konto k = kontoDAOfk.findKonto("404-2", wpisView);
                    wiersztrzeci.getStronaWn().setKonto(k);
                    nowewiersze.add(wiersztrzeci);
                }
            }
        } else {
            Msg.msg("w", "Brak Zdefiniowanego konta rozrachunkowego. Nie można generować zapisów VAT.");
        }
        return nowewiersze;
    }
    
     public static List<Wiersz> rozliczEdytujVatKosztRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk) {
        List<Wiersz> nowewiersze = new ArrayList<>();
        double nettoEwidVat = ewidencjaVatRK.getNetto();
        double vatEwidVat = ewidencjaVatRK.getVat();
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
                     } else {
                         wierszdrugi.getStronaWn().setKwota(vatEwidVat);
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
                        Konto k = kontoDAOfk.findKonto("404-2", wpisView);
                        wiersztrzeci.getStronaWn().setKonto(k);
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
    
    
    
    public static List<Wiersz> rozliczVatPrzychodEdycja(EVatwpisFK wierszvatdoc, double[] wartosciVAT, Dokfk selected, WpisView wpisView) {
        List<Wiersz> nowewiersze = new ArrayList<>();
        if (wartosciVAT[0] != 0 || wartosciVAT[2] != 0) {
            Wiersz wierszpierwszy = selected.getListawierszy().get(0);
            Waluty w = selected.getWalutadokumentu();
            try {
                nowewiersze.add(wierszpierwszy);
                if (wierszpierwszy != null && wartosciVAT[0]!=0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[0]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[0]+wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[2]);
                        ma.setKwotaPLN(wartosciVAT[0]);
                        wn.setKwota(wartosciVAT[2]+wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[0]+wartosciVAT[1]);
                    }
                    
                } else if (wierszpierwszy != null && wartosciVAT[0]==0) {
                    StronaWiersza wn = wierszpierwszy.getStronaWn();
                    StronaWiersza ma = wierszpierwszy.getStronaMa();
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                    if (w.getSymbolwaluty().equals("PLN")) {
                        ma.setKwota(wartosciVAT[1]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[1]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    } else {
                        ma.setKwota(wartosciVAT[3]);
                        ma.setKwotaPLN(wartosciVAT[1]);
                        wn.setKwota(wartosciVAT[3]);
                        wn.setKwotaPLN(wartosciVAT[1]);
                    }
                  }
               if (selected.getListawierszy().size()==2 && wartosciVAT[1] != 0 && wartosciVAT[0] != 0) {
                    Wiersz wierszdrugi = selected.getListawierszy().get(1);
                    nowewiersze.add(wierszdrugi);
                    if (w.getSymbolwaluty().equals("PLN")) {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT[1]);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT[1]);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wierszdrugi.getStronaMa().setKwota(wartosciVAT[3]);
                        wierszdrugi.getStronaMa().setKwotaWaluta(wartosciVAT[3]);
                        wierszdrugi.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    }
               }
                int index = wierszvatdoc.getLp()-1 < 0 ? 0 : wierszvatdoc.getLp()-1;
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":netto");
                RequestContext.getCurrentInstance().update("formwpisdokument:tablicavat:"+index+":brutto");
                RequestContext.getCurrentInstance().update("formwpisdokument:dataList");
            } catch (Exception e1) {
                Msg.msg("w", "Brak zdefiniowanych kont przyporządkowanych do dokumentu. Nie można wygenerować wierszy.");
            }
        }
        return nowewiersze;
    }
    
    public static List<Wiersz> rozliczVatPrzychodRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk){
        List<Wiersz> nowewiersze = new ArrayList<>();
        double nettovat = ewidencjaVatRK.getNetto();
        double kwotavat = ewidencjaVatRK.getVat();
        Wiersz wierszpierwszy = ewidencjaVatRK.getWiersz();
        Konto kontoRozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (kontoRozrachunkowe != null) {
            if (wierszpierwszy != null) {
                StronaWiersza wn = wierszpierwszy.getStronaWn();
                StronaWiersza ma = wierszpierwszy.getStronaMa();
                if (wierszpierwszy.getOpisWiersza().equals("")) {
                    wierszpierwszy.setOpisWiersza(selected.getOpisdokfk());
                }
                String kontrnazwa = ewidencjaVatRK.getKlient().getNskrocona();
                if (kontrnazwa == null) {
                    kontrnazwa = ewidencjaVatRK.getKlient().getNpelna();
                }
                kontrnazwa = kontrnazwa.length() < 18 ? kontrnazwa : kontrnazwa.substring(0, 17);
                wierszpierwszy.setOpisWiersza(wierszpierwszy.getOpisWiersza()+" "+kontrnazwa);
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
                    Konto k = kontoDAOfk.findKonto("221", wpisView);
                    wierszdrugi.getStronaMa().setKonto(k);
                }
                nowewiersze.add(wierszdrugi);
            }
        } else {
            Msg.msg("w", "Brak Zdefiniowanego konta kasy. Nie można generować zapisów VAT.");
        }
        return nowewiersze;
    }
    
    public static List<Wiersz> rozliczEdytujVatPrzychodRK(EVatwpisFK ewidencjaVatRK, double[] wartosciVAT, Dokfk selected, WpisView wpisView, int wierszRKindex, KontoDAOfk kontoDAOfk){
        List<Wiersz> nowewiersze = new ArrayList<>();
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
