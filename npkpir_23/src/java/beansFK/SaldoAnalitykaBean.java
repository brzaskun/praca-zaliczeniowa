/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import comparator.SaldoKontocomparator;
import dao.StronaWierszaDAO;
import embeddable.Mce;
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
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SaldoAnalitykaBean  {

   public static List<SaldoKonto> przygotowanalistasaldbo(List<Konto> kontaklienta, List<Konto> kontaklientarokpop, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp, StronaWierszaDAO stronaWierszaDAO, Podatnik podatnik, String rok, String mc) {
        List<SaldoKonto> listaSaldoKonto = new ArrayList<>();
        List<StronaWiersza> zapisyRok = pobierzzapisy(stronaWierszaDAO, podatnik, rok);
        Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
        List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
        kontaklienta.parallelStream().map((p) -> {
            if (p.getPelnynumer().equals("202-1-5")) {
            }
            return p;
        }).forEachOrdered((p) -> {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(), saldoKonto);
        });
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, wierszenieuzupelnione, false, mc);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok, wierszenieuzupelnione, true, mc);
        przygotowanalista.values().parallelStream().map((s) -> {
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
            System.out.println("W tym dokumencie nie ma uzupełnionych kont: " + t.getDokfkS());
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
   
   private static List<StronaWiersza> pobierzzapisy(StronaWierszaDAO stronaWierszaDAO, Podatnik podatnik, String rok) {
        List<StronaWiersza> zapisyRok = stronaWierszaDAO.findStronaByPodatnikRok(podatnik, rok);
        return zapisyRok;
    }

    private static void naniesBOnaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyBO) {
        zapisyBO.parallelStream().forEach((r) -> {
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
    
    private static void naniesZapisyNaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyRok, List<StronaWiersza> wierszenieuzupelnione, boolean obroty0zapisy1, String mc) {
        int granicamca = Mce.getMiesiacToNumber().get(mc);
        zapisyRok.parallelStream().forEach(r-> {
            if (obroty0zapisy1 == true && !r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione, mc);
                }
            } else if (obroty0zapisy1 == false && r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione, mc);
                }
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
                            Konto macierzyste = p.getKonto().getKontomacierzyste();
                            if (r.getKonto().getPelnynumer().equals(macierzyste.getPelnynumer())) {
                                sumuj(p,r);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void sumuj(SaldoKonto corka, SaldoKonto macierzyste) {
        macierzyste.setBoWn(macierzyste.getBoWn()+corka.getBoWn());
        macierzyste.setBoMa(macierzyste.getBoMa()+corka.getBoMa());
        macierzyste.setObrotyWn(macierzyste.getObrotyWn()+corka.getObrotyWn());
        macierzyste.setObrotyWn(macierzyste.getObrotyMa()+corka.getObrotyMa());
        macierzyste.setSaldoWn(macierzyste.getSaldoWn()+corka.getSaldoWn());
        macierzyste.setSaldoMa(macierzyste.getSaldoMa()+corka.getSaldoMa());
    }
}
