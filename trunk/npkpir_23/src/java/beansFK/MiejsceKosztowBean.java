/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import embeddablefk.MiejsceKosztowZest;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.StronaWiersza;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;
import viewfk.MiejsceKosztowView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class MiejsceKosztowBean {

    public static void zsumujkwotyzkont(List<MiejsceKosztow> miejscakosztow, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, LinkedHashSet<MiejsceKosztowView.TabelaMiejsceKosztow> listasummiejsckosztow) {
        int i = 1;
        for (MiejsceKosztow p : miejscakosztow) {
            double total = 0;
            List<MiejsceKosztowZest> l = new ArrayList<>();
            MiejsceKosztowView.TabelaMiejsceKosztow m = new MiejsceKosztowView.TabelaMiejsceKosztow();
            for (Konto r : kontaslownikowe) {
                List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteMcWaluta(wpisView.getPodatnikObiekt(), r, wpisView.getMiesiacWpisu(), "PLN", p);
                if (kontozapisy.size() > 0) {
                    double suma = 0;
                    for (StronaWiersza s : kontozapisy) {
                        suma += sumuj(s);
                    }
                    total += suma;
                    l.add(new MiejsceKosztowZest(r.getNazwapelna(), r.getPelnynumer(), suma, total, kontozapisy));
                }
            }
            if (l.size() > 0) {
                m.setId(i++);
                m.setMiejsceKosztow(p);
                m.setMiejsceKosztowZest(l);
                listasummiejsckosztow.add(m);
            }
        }
    }

    private static double sumuj(StronaWiersza s) {
        if (s.getWnma().equals("Wn")) {
            return s.getKwota();
        } else {
            return -s.getKwota();
        }
    }

   
    
}
