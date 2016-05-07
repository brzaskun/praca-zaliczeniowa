/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansSrodkiTrwale;

import comparator.SrodekTrwNowaWartoscComparator;
import embeddable.Mce;
import embeddable.Parametr;

import entity.Podatnik;
import entity.SrodekTrw;
import entity.SrodekTrw_NowaWartosc;
import entity.UmorzenieN;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class SrodkiTrwBean implements Serializable {
    
     public static void main(String[] args) {
        SrodekTrw s = new SrodekTrw();
        s.setZmianawartosci(new ArrayList<SrodekTrw_NowaWartosc>());
        s.setNetto(24000.0);
        s.setStawka(50.0);
        s.setDataprzek("2015-01-01-");
        odpisroczny(s);
        odpismiesieczny(s);
        s.setUmorzPlan(naliczodpisymczne(s));
        SrodekTrw_NowaWartosc t = new SrodekTrw_NowaWartosc();
        t.setSrodekTrw(s);
        t.setMc("12");
        t.setRok("2015");
        t.setKwota(24000);
        s.getZmianawartosci().add(t);
        t = new SrodekTrw_NowaWartosc();
        t.setSrodekTrw(s);
        t.setMc("06");
        t.setRok("2015");
        t.setKwota(24000);
        s.getZmianawartosci().add(t);
        naliczodpisymczneUlepszenie(s);
        int i = 1;
        for (Double p : s.getUmorzPlan()) {
            System.out.println(p.intValue());
            i++;
        }
        
    }
    
    public static void naliczodpisymczneUlepszenie(SrodekTrw dodawanysrodektrwaly) {
        Collections.sort(dodawanysrodektrwaly.getZmianawartosci(), new SrodekTrwNowaWartoscComparator());
        int pierwszymc = Mce.getMiesiacToNumber().get(dodawanysrodektrwaly.getDataprzek().split("-")[1]);
        int pierwszyrok = Integer.parseInt(dodawanysrodektrwaly.getDataprzek().split("-")[0]);
        double kwotapierwotna = dodawanysrodektrwaly.getNetto();
        if (dodawanysrodektrwaly.getZmianawartosci().size() > 0) {
            for (SrodekTrw_NowaWartosc s : dodawanysrodektrwaly.getZmianawartosci()) {
                int mczmiany = Mce.getMiesiacToNumber().get(s.getMc());
                int rokzmiany = Integer.parseInt(s.getRok());
                int poilumcachzmienic = Mce.odlegloscMcy(pierwszymc, pierwszyrok, mczmiany, rokzmiany);
                odpisroczny(dodawanysrodektrwaly);
                odpismiesieczny(dodawanysrodektrwaly);
                Double netto_do_amortyzacji = dodawanysrodektrwaly.getNetto()-dodawanysrodektrwaly.getNiepodlegaamortyzacji()-dodawanysrodektrwaly.getUmorzeniepoczatkowe();
                Double nar = 0.0;
                List<Double> listaplanum = new ArrayList<Double>();
                int licznikzmian = 0;
                while (netto_do_amortyzacji - nar > 0) {
                    if (licznikzmian == poilumcachzmienic) {
                        netto_do_amortyzacji += s.getKwota();
                        dodawanysrodektrwaly.setNetto(dodawanysrodektrwaly.getNetto()+s.getKwota());
                        odpisroczny(dodawanysrodektrwaly);
                        odpismiesieczny(dodawanysrodektrwaly);
                    }
                    if (licznikzmian < poilumcachzmienic) {
                        double odpiszachowany = dodawanysrodektrwaly.getUmorzPlan().get(licznikzmian);
                        nar += odpiszachowany;
                        listaplanum.add(odpiszachowany);
                        licznikzmian++;
                    } else {
                        Double odp = (netto_do_amortyzacji - nar) >= dodawanysrodektrwaly.getOdpismc() ? dodawanysrodektrwaly.getOdpismc() : netto_do_amortyzacji - nar;
                        //sluzy do eliminowania odpisow w kwocie groszy i dodaje je do ostatniej raty (zaokraglenia)
                        double antycypacja = (netto_do_amortyzacji - (nar+odp)) > 1.0 ? 0.0 : Z.z((netto_do_amortyzacji - (nar+odp)));
                        listaplanum.add(Z.z(odp));
                        nar = Z.z(nar + odp + antycypacja);
                        licznikzmian++;
                    }
                }
                dodawanysrodektrwaly.setUmorzPlan(listaplanum);
            }
            dodawanysrodektrwaly.setNetto(kwotapierwotna);
        }
    }
    
    
    public static List<Double> naliczodpisymczne(SrodekTrw dodawanysrodektrwaly) {
        Double odpis_mc = Z.z(dodawanysrodektrwaly.getOdpismc());
        Double netto_do_amortyzacji = Z.z(dodawanysrodektrwaly.getNetto()-dodawanysrodektrwaly.getNiepodlegaamortyzacji()- dodawanysrodektrwaly.getUmorzeniepoczatkowe());
        Double nar = 0.0;
        List<Double> listaplanum = new ArrayList<Double>();
        while (netto_do_amortyzacji - nar > 0) {
            double roznica = Z.z(netto_do_amortyzacji - nar);
            Double odp = roznica >= odpis_mc ? odpis_mc : roznica;
            //sluzy do eliminowania odpisow w kwocie groszy i dodaje je do ostatniej raty (zaokraglenia)
            double antycypacja = (netto_do_amortyzacji - (nar+odp)) > 1.0 ? 0.0 : Z.z((netto_do_amortyzacji - (nar+odp)));
            listaplanum.add(Z.z(odp.doubleValue()+antycypacja));
            nar = Z.z(nar + odp + antycypacja);
        }
        return listaplanum;
    }
    
    public static void odpisroczny(SrodekTrw dodawanysrodektrwaly) {
        double odpisrok = 0.0;
        double netto = dodawanysrodektrwaly.getNetto() - dodawanysrodektrwaly.getNiepodlegaamortyzacji();
        odpisrok = Z.z(netto*dodawanysrodektrwaly.getStawka()/100.0);
        dodawanysrodektrwaly.setOdpisrok(odpisrok);
    }
    
    public static void odpismiesieczny(SrodekTrw dodawanysrodektrwaly) {
        double odpismiesiac = 0.0;
        odpismiesiac = Z.z(dodawanysrodektrwaly.getOdpisrok()/12.0);
        if (dodawanysrodektrwaly.getStawka() == 100.0) {
            dodawanysrodektrwaly.setOdpismc(dodawanysrodektrwaly.getOdpisrok());
        } else {
            dodawanysrodektrwaly.setOdpismc(odpismiesiac);
        }
    }

    public static double sumujumorzenia(List<UmorzenieN> umorzenia) {
        double kwotaumorzenia = 0.0;
        Iterator it = umorzenia.iterator();
        while (it.hasNext()) {
            UmorzenieN tmp = (UmorzenieN) it.next();
            kwotaumorzenia += tmp.getKwota();
        }
        return Z.z(kwotaumorzenia);
    }

    public static List<UmorzenieN> generujumorzeniadlasrodka(SrodekTrw srodek, WpisView wpisView) {
        List<UmorzenieN> umorzenia = new ArrayList<>();
        if (srodek.getZlikwidowany() == 0) {
            List<Double> planowane = new ArrayList<>();
            planowane.addAll(srodek.getUmorzPlan());
            Integer rokOd = Integer.parseInt(srodek.getDataprzek().substring(0, 4));
            Integer mcOd = 0;
            if (srodek.getStawka() == 100) {
                mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7));
            } else {
                String pob = srodek.getDataprzek().substring(5, 7);
                // bo jest od miesiaca nastepnego po miesiacu
                mcOd = Integer.parseInt(pob) + 1;
                if (mcOd == 13) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7)) + 1;
                }
            }

            Iterator itX;
            itX = planowane.iterator();
            int i = 1;
            while (itX.hasNext()) {
                Integer[] mcrok = new Integer[2];
                mcrok[0] = mcOd;
                mcrok[1] = rokOd;
                danymiesiacniejestzawieszenie(mcrok, wpisView);
                mcOd = mcrok[0];
                rokOd = mcrok[1];
                Double kwotaodpisMC = (Double) itX.next();
                UmorzenieN odpisZaDanyOkres = new UmorzenieN();
                odpisZaDanyOkres.setKwota(kwotaodpisMC);
                odpisZaDanyOkres.setRokUmorzenia(rokOd);
                odpisZaDanyOkres.setMcUmorzenia(mcOd);
                odpisZaDanyOkres.setNrUmorzenia(i);
                odpisZaDanyOkres.setSrodekTrw(srodek);
                i++;
                if (mcOd == 12) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd++;
                }
                umorzenia.add(odpisZaDanyOkres);
            }
        }
        return umorzenia;
    }
    private static void danymiesiacniejestzawieszenie(Integer[] mcrok, WpisView wpisView) {
        Integer badanymiesiac = mcrok[0];
        Integer badanyrok = mcrok[1];
        Podatnik pod = wpisView.getPodatnikObiekt();
        List<Parametr> listaparametrow = new ArrayList<>();
        if (pod.getZawieszeniedzialalnosci() != null) {
            listaparametrow.addAll(pod.getZawieszeniedzialalnosci());
            Iterator it = listaparametrow.iterator();
            while (it.hasNext()) {
                Parametr par = (Parametr) it.next();
                if (!par.getRokOd().equals(wpisView.getRokWpisuSt())) {
                    it.remove();
                }
            }
            if (listaparametrow.size() > 0) {
                List<String> miesiacezawieszeniawroku = new ArrayList<>();
                for (Parametr s : listaparametrow) {
                    try {
                        miesiacezawieszeniawroku.addAll(Mce.zakresmiesiecy(s.getMcOd(), s.getMcDo()));
                    } catch (Exception e) {
                       Msg.msg("e", "Miesiąc Od jest późniejszy od miesiąca Do!");
                    }
                }
                String ostatnimiesiaczlisty = miesiacezawieszeniawroku.get(miesiacezawieszeniawroku.size() - 1);
                if (miesiacezawieszeniawroku.contains(Mce.getNumberToMiesiac().get(badanymiesiac))) {
                    if (ostatnimiesiaczlisty.equals("12")) {
                        mcrok[0] = 1;
                        mcrok[1] += 1;
                    } else {
                        int ostatnimiesiacint = Mce.getMiesiacToNumber().get(ostatnimiesiaczlisty) + 1;
                        mcrok[0] = ostatnimiesiacint;
                    }
                }
            }
        }
    }
}
