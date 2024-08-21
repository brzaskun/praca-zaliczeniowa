/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import embeddablefk.MiejsceZest;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.StronaWiersza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Named;
import view.WpisView;
import viewfk.MiejscePrzychodowView;

/**
 *
 * @author Osito
 */
@Named

public class MiejscePrzychodowBean {

    public static void zsumujkwotyzkont(List<MiejscePrzychodow> miejscaprzychodow, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, LinkedHashSet<MiejscePrzychodowView.TabelaMiejscePrzychodow> listasummiejsckosztow, List<StronaWiersza> stronywiersza) {
        int i = 1;
        for (MiejscePrzychodow p : miejscaprzychodow) {
            double total = 0;
            List<MiejsceZest> l = Collections.synchronizedList(new ArrayList<>());
            MiejscePrzychodowView.TabelaMiejscePrzychodow m = new MiejscePrzychodowView.TabelaMiejscePrzychodow();
            for (Konto r : kontaslownikowe) {
                if (stronywiersza.size() > 0) {
                    double suma = 0;
                    List<StronaWiersza> listastron = Collections.synchronizedList(new ArrayList<>());
                    for (StronaWiersza s : stronywiersza) {
                        if (s.getKonto().getNazwapelna().equals(p.getOpismiejsca()) && s.getKonto().getKontomacierzyste()!=null && s.getKonto().getKontomacierzyste().equals(r)) {
                            suma += sumuj(s);
                            listastron.add(s);
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
                m.setMiejscePrzychodow(p);
                m.setMiejscePrzychodowZest(l);
                listasummiejsckosztow.add(m);
            }
        }
    }

    private static double sumuj(StronaWiersza s) {
        if (s.getWnma().equals("Wn")) {
            return -s.getKwota();
        } else {
            return +s.getKwota();
        }
    }

   
    
}
