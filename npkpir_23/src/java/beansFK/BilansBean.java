/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaRZiSDAO;
import dao.UkladBRDAO;
import embeddable.Mce;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;
import msg.Msg;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */
public class BilansBean {
    
    
    public static void zmianaukladprzegladRZiSBO(UkladBR uklad, UkladBRDAO ukladBRDAO, WpisView wpisView, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, PozycjaRZiSDAO pozycjaRZiSDAO) {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        wyczyscKonta("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontoDAO);
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO,kontopozycjaZapisDAO, uklad, "wynikowe");
    }
    
    public static List<PozycjaRZiSBilans> pobierzPoszerzPozycje(UkladBR ukladBR, PozycjaRZiSDAO pozycjaRZiSDAO, String granica) {
        List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(ukladBR));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                    PozycjaRZiS p = (PozycjaRZiS) it.next();
                    p.setPrzyporzadkowanestronywiersza(null);
                    p.setMce(new HashMap<String,Double>());
                    for (String r : Mce.getMceListS()) {
                        p.getMce().put(r, 0.0);
                    }
                }
        } catch (Exception e) {  
            E.e(e);
        }
        return pozycje;
    }
    
    private static void wyczyscKonta(String rb, Podatnik podatnik, String rok, KontoDAOfk kontoDAO) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
    
    public static double[] rozliczzapisy(List<StronaWiersza> strony) {
        DoubleAccumulator obrotywn = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotyma = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotywnwaluta = new DoubleAccumulator(Double::sum, 0.d);
        DoubleAccumulator obrotymawaluta = new DoubleAccumulator(Double::sum, 0.d);
        strony.forEach(p -> {
            if (p.isWn()) {
                obrotywn.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    obrotywnwaluta.accumulate(Z.z(p.getKwota()));
                }
            } else {
                obrotyma.accumulate(Z.z(p.getKwotaPLN()));
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    obrotymawaluta.accumulate(Z.z(p.getKwota()));
                }
            }
        });
        double saldo = obrotywn.doubleValue() - obrotyma.doubleValue();
        double saldowaluta = obrotywnwaluta.doubleValue() - obrotymawaluta.doubleValue();
        double kurs = 0.0;
        if (saldowaluta != 0.0) {
            kurs = Math.abs(saldo / saldowaluta);
        }
        double[] sumy = new double[3];
        sumy[0] = Z.z(saldo);
        sumy[1] = Z.z(saldowaluta);
        sumy[2] = Z.z(kurs);
        return sumy;
    }
}
