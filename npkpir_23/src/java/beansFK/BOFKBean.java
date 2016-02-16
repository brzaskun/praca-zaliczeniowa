/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.WierszBO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class BOFKBean {
    
    public static List<StronaWiersza> pobierzZapisyBO(WierszBODAO wierszBODAO, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (WierszBO p : wierszeBO) {
            if (p.getKwotaWnPLN() > 0) {
                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
            } else {
                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
            }
        }
        return zapisy;
    }

    public static List<StronaWiersza> pobierzZapisyBO(Konto konto, WierszBODAO wierszBODAO, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto);
        for (WierszBO p : wierszeBO) {
            if (p.getKwotaWnPLN() > 0) {
                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
            } else {
                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
            }
        }
        return zapisy;
    }

    public static List<StronaWiersza> pobierzZapisyBO(Konto konto, String waluta, WierszBODAO wierszBODAO, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKontoWaluta(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto, waluta);
        for (WierszBO p : wierszeBO) {
            if (p.getKwotaWnPLN() > 0) {
                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
            } else {
                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
            }
        }
        return zapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyBOSyntetyka(KontoDAOfk kontoDAOfk, Konto konto, WierszBODAO wierszBODAO, WpisView wpisView) {
        List<StronaWiersza> zapisy = new ArrayList<>();
        List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), konto);
        for (WierszBO p : wierszeBO) {
            if (p.getKwotaWnPLN() > 0) {
                zapisy.add(new StronaWiersza(p, "Wn", "zapisy"));
            } else {
                zapisy.add(new StronaWiersza(p, "Ma", "zapisy"));
            }
        }
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), konto.getPelnynumer());
            for (Konto p : kontapotomne) {
                zapisy.addAll(pobierzZapisyBOSyntetyka(kontoDAOfk, p, wierszBODAO, wpisView));
            }
        }
        return zapisy;
    }
}
