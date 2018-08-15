/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import embeddablefk.MiejsceZest;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.StronaWiersza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Named;
import view.WpisView;
import viewfk.MiejsceKosztowView;

/**
 *
 * @author Osito
 */
@Named

public class MiejsceKosztowBean {

    public static void zsumujkwotyzkont(List<MiejsceKosztow> miejscakosztow, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, LinkedHashSet<MiejsceKosztowView.TabelaMiejsceKosztow> listasummiejsckosztow, List<StronaWiersza> stronywiersza) {
        int i = 1;
        for (MiejsceKosztow p : miejscakosztow) {
            double total = 0;
            List<MiejsceZest> l = Collections.synchronizedList(new ArrayList<>());
            MiejsceKosztowView.TabelaMiejsceKosztow m = new MiejsceKosztowView.TabelaMiejsceKosztow();
            for (Konto r : kontaslownikowe) {
                if (stronywiersza.size() > 0) {
                    double suma = 0;
                    List<StronaWiersza> listastron = Collections.synchronizedList(new ArrayList<>());
                    for (Iterator<StronaWiersza> it = stronywiersza.iterator(); it.hasNext();) {
                        StronaWiersza s = it.next();
                        if (s.getKonto().getNazwapelna().equals(p.getOpismiejsca()) && s.getKonto().getKontomacierzyste()!=null && s.getKonto().getKontomacierzyste().equals(r)) {
                            if (s.getDokfk().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                                suma += sumuj(s);
                                listastron.add(s);
                                it.remove();
                            }
                        }
                    }
                    total += suma;
                    if (suma != 0.0) {
                        l.add(new MiejsceZest(r.getNazwapelna(), r.getPelnynumer(), suma, total, listastron));
                    }
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
