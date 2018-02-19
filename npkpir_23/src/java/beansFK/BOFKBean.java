/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.WierszBO;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class BOFKBean {
    
    public static List<StronaWiersza> pobierzZapisyBO(DokDAOfk dokDAOfk, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<Dokfk> dokfk = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, "BO");
        dokfk.parallelStream().filter((p) -> (p.getOpisdokfk().contains("bilans otwarcia roku:"))).forEachOrdered((p) -> {
            zapisy.addAll(p.getStronyWierszy());
        });
        return zapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyObrotyRozp(DokDAOfk dokDAOfk, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<Dokfk> dokfk = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, "BO");
        dokfk.parallelStream().filter((p) -> (!p.getOpisdokfk().contains("bilans otwarcia roku:"))).forEachOrdered((p) -> {
            zapisy.addAll(p.getStronyWierszy());
        });
        return zapisy;
    }
    
//    public static List<StronaWiersza> pobierzZapisyBO(WierszBODAO wierszBODAO, WpisView wpisView) {
//        List<StronaWiersza> zapisy = new ArrayList<>();
//        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//        for (WierszBO p : wierszeBO) {
//            if (p.getKwotaWnPLN() != 0.0) {
//                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
//            } else {
//                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
//            }
//        }
//        return zapisy;
//    }

//    public static List<StronaWiersza> pobierzZapisyBO(Konto konto, WierszBODAO wierszBODAO, WpisView wpisView) {
//        List<StronaWiersza> zapisy = new ArrayList<>();
//        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto);
//        for (WierszBO p : wierszeBO) {
//            if (p.getKwotaWnPLN() != 0.0) {
//                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
//            } else {
//                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
//            }
//        }
//        return zapisy;
//    }

    public static List<StronaWiersza> pobierzZapisyBO(Konto konto, String waluta, WierszBODAO wierszBODAO, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKontoWaluta(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto, waluta);
        wierszeBO.forEach((p) -> {
            if (p.getKwotaWnPLN() != 0.0) {
                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
            } else {
                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
            }
        });
        return zapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyBOSyntetyka(KontoDAOfk kontoDAOfk, Konto konto, DokDAOfk dokDAOfk, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<Dokfk> dokfk = dokDAOfk.findDokfkPodatnikRokKategoria(wpisView, "BO");
        dokfk.parallelStream().filter((p) -> (p.getNrkolejnywserii()==1)).forEachOrdered((p) -> {
            for (StronaWiersza r : p.getStronyWierszy()) {
                if (r.getKonto().equals(konto)) {
                    zapisy.add(r);
                }
            }
        });
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), konto.getPelnynumer());
            kontapotomne.forEach((p) -> {
                zapisy.addAll(pobierzZapisyBOSyntetyka(kontoDAOfk, p, dokDAOfk, wpisView));
            });
        }
        return zapisy;
    }
}
