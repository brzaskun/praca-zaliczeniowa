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
            evatwpis.setVat(Z.z(evatwpis.getNetto()* stawkavat));
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
        } else if (skrotRT.contains("ZZP")) {
            evatwpis.setVat(Z.z((evatwpis.getNetto() * 0.23) / 2));
        } else {
            evatwpis.setVat(Z.z(evatwpis.getNetto() * stawkavat));
        }
    }
            
    public static double[] podsumujwartosciVAT(List<EVatwpisFK> ewidencja) {
        double[] wartosciVAT = new double[8];
        for (EVatwpisFK p : ewidencja) {
            wartosciVAT[0] += p.getNetto();
            wartosciVAT[1] += p.getVat();
            wartosciVAT[2] += p.getNettowwalucie();
            wartosciVAT[3] += p.getVatwwalucie();
            double vatplnpolowa = Z.z(p.getVat()/2);
            double vatplnreszta = Z.z(p.getVat()-vatplnpolowa);
            wartosciVAT[4] = vatplnpolowa;
            wartosciVAT[5] = vatplnreszta;
            double vatpolowa = Z.z(p.getVatwwalucie()/2);
            double vatreszta = Z.z(p.getVatwwalucie()-vatpolowa);
            wartosciVAT[6] = vatpolowa;
            wartosciVAT[7] = vatreszta;
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
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
                                ma.setKwota(wartosciVAT[0]);
                                ma.setKwotaPLN(wartosciVAT[0]);
                            } else {
                                ma.setKwota(wartosciVAT[0] + wartosciVAT[1]);
                                ma.setKwotaPLN(wartosciVAT[0] + wartosciVAT[1]);
                            }
                        } else {
                            wn.setKwota(wartosciVAT[2]);
                            wn.setKwotaPLN(wartosciVAT[0]);
                            if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
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
                if (selected.getRodzajedok().getSkrot().equals("ZZP")) {
                   wartosciVAT[1] = wartosciVAT[4];
                   wartosciVAT[3] = wartosciVAT[6];
                }
                if (selected.getRodzajedok().getSkrot().equals("ZZP")) {
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
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[1]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[1], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[1]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
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
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWNT(selected, lp, wartosciVAT[5], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                        wiersz2_3.getStronaMa().setKwotaPLN(wartosciVAT[5]);
                    } else {
                        wiersz2_3 = ObslugaWiersza.utworzNowyWierszWn(selected, lp, wartosciVAT[5], 1);
                        wiersz2_3.getStronaWn().setKwotaPLN(wartosciVAT[5]);
                    }
                } else {
                    if (selected.getRodzajedok().getRodzajtransakcji().equals("WNT") || selected.getRodzajedok().getRodzajtransakcji().equals("import usług")) {
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
}
