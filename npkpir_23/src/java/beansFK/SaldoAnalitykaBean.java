/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.SaldoKontocomparator;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SaldoAnalitykaBean  {

   public static List<SaldoKonto> przygotowanalistasaldbo(List<Konto> kontaklienta, List<Konto> kontaklientarokpop, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp, List<StronaWiersza> zapisyRok, Podatnik podatnik) {
        List<SaldoKonto> listaSaldoKonto = new ArrayList<>();
        
        Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
        List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
        kontaklienta.stream().map((p) -> {
            if (p.getPelnynumer().equals("202-1-5")) {
            }
            return p;
        }).forEachOrdered((p) -> {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(), saldoKonto);
        });
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, wierszenieuzupelnione, false);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok, wierszenieuzupelnione, true);
        przygotowanalista.values().stream().map((s) -> {
            s.sumujBOZapisy();
            return s;
        }).forEachOrdered((s) -> {
            s.wyliczSaldo();
        });
        for (SaldoKonto k : przygotowanalista.values()) {
            for (Konto l : kontaklientarokpop) {
                if (k.getKonto().equals(l)) {
                    k.setBoWn(Z.z(l.getSaldoWnksiegi()));
                    k.setBoMa(Z.z(l.getSaldoMaksiegi()));
                    break;
                }
            }
        }
        for (StronaWiersza t : wierszenieuzupelnione) {
            error.E.s("W tym dokumencie nie ma uzupełnionych kont: " + t.getDokfkS());
        }
        listaSaldoKonto.addAll(przygotowanalista.values());
        for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
            SaldoKonto skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0 && skn.getKonto().isMapotomkow()==false) {
                it.remove();
            }
        }
        podsumujanalityke(listaSaldoKonto);
        return listaSaldoKonto;
    }
   

    private static void naniesBOnaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyBO) {
        zapisyBO.stream().forEach((r) -> {
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getWnma().equals("Wn")) {
                    p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
                } else {
                    p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
                }
                p.getZapisy().add(r);
            }
        });
    }
    
    private static void naniesZapisyNaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyRok, List<StronaWiersza> wierszenieuzupelnione, boolean obroty0zapisy1) {
        zapisyRok.stream().forEach(r-> {
            if (obroty0zapisy1 == true && !r.getDokfk().getSeriadokfk().equals("BO")) {
                nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione, "12");
            } else if (obroty0zapisy1 == false && r.getDokfk().getSeriadokfk().equals("BO")) {
                nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione, "12");
            }
        });
    }
    
    private static void nanieskonkretnyzapis(StronaWiersza r, Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> wierszenieuzupelnione, String mc) {
        try {
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getKonto().equals(p.getKonto())) {
                        if (r.getWnma().equals("Wn")) {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + r.getKwotaPLN()));
                            }
                            p.setObrotyWn(Z.z(p.getObrotyWn() + r.getKwotaPLN()));
                        } else {
                            if (r.getDokfk().getMiesiac().equals(mc)) {
                                p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + r.getKwotaPLN()));
                            }
                            p.setObrotyMa(Z.z(p.getObrotyMa() + r.getKwotaPLN()));
                        }
                        p.getZapisy().add(r);
                }
            }
        } catch (Exception e) {
            if (r.getKonto() == null) {
            }
            if (r.getWiersz().getDokfk().getMiesiac() == null) {
            }
            E.e(e);
            if (wierszenieuzupelnione.size() > 0) {
                boolean jest = false;
                for (StronaWiersza t : wierszenieuzupelnione) {
                    if (t.getDokfkS().equals(r.getDokfkS())) {
                        jest = true;
                    }
                }
                if (jest == false) {
                    wierszenieuzupelnione.add(r);
                }
            } else {
                wierszenieuzupelnione.add(r);
            }
        }
    }

    private static void podsumujanalityke(List<SaldoKonto> listaSaldoKonto) {
        Collections.sort(listaSaldoKonto, new SaldoKontocomparator());
        for (int level = 5; level>0; level--) {
            List<SaldoKonto> lista2 = new ArrayList<>();
            lista2.addAll(listaSaldoKonto);
            for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
                SaldoKonto p = it.next();
                if (p.getKonto().getLevel()==level) {
                    for (SaldoKonto r : lista2) {
                        if (r.getKonto().getLevel()==level-1) {
                            try {
                                Konto macierzyste = p.getKonto().getKontomacierzyste();
                                if (r.getKonto().getPelnynumer().equals(macierzyste.getPelnynumer())) {
                                    sumuj(p,r);
                                }
                            } catch (Exception e) {
                                System.out.println("brak konta macierzystego dla konto "+p.getKonto().getPelnynumer());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void sumuj(SaldoKonto corka, SaldoKonto macierzyste) {
        macierzyste.setBoWn(Z.z(macierzyste.getBoWn()+corka.getBoWn()));
        macierzyste.setBoMa(Z.z(macierzyste.getBoMa()+corka.getBoMa()));
        macierzyste.setObrotyWn(Z.z(macierzyste.getObrotyWn()+corka.getObrotyWn()));
        macierzyste.setObrotyWn(Z.z(macierzyste.getObrotyMa()+corka.getObrotyMa()));
        macierzyste.setSaldoWn(Z.z(macierzyste.getSaldoWn()+corka.getSaldoWn()));
        macierzyste.setSaldoMa(Z.z(macierzyste.getSaldoMa()+corka.getSaldoMa()));
    }

    public static boolean sprawdzzaksiegowanie(List<Konto> kontaklienta, List<Konto> kontaklientarokpop) {
        boolean wszystkook = true;
        for (Iterator<Konto> it = kontaklienta.iterator(); it.hasNext();) {
            Konto kontobiezace = it.next();
            for (Iterator<Konto> it1 = kontaklientarokpop.iterator(); it1.hasNext();) {
                Konto kontopoprzednie = it1.next();
                if (kontopoprzednie.isWynik0bilans1()&&!kontopoprzednie.getPelnynumer().equals("860")) {
                    if (kontobiezace.getPelnynumer().equals(kontopoprzednie.getPelnynumer())) {
                        double bzKWn = kontopoprzednie.getSaldoWnksiegi();
                        double bzKMa = kontopoprzednie.getSaldoMaksiegi();
                        double boWn = kontobiezace.getBoWn();
                        double boMa = kontobiezace.getBoMa();
                        if (Z.z(bzKWn)!=Z.z(boWn)||Z.z(bzKMa)!=Z.z(boMa)) {
                            wszystkook = false;
                            break;
                        }
                    }
                }
            }
            if (!wszystkook) {
                break;
            }
        }
        return wszystkook;
    }
}
