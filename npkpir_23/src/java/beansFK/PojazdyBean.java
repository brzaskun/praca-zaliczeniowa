/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.StronaWierszaDAO;
import embeddablefk.PojazdyZest;
import entityfk.Konto;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.inject.Named;
import view.WpisView;
import viewfk.PojazdyView;

/**
 *
 * @author Osito
 */
@Named

public class PojazdyBean {

    public static void zsumujkwotyzkont(List<Pojazdy> listapojazdy, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, LinkedHashSet<PojazdyView.TabelaPojazdy> listasumpojazdy, List<StronaWiersza> stronywiersza) {
        int i = 1;
        for (Pojazdy p : listapojazdy) {
            double total = 0;
            List<PojazdyZest> l = Collections.synchronizedList(new ArrayList<>());
            PojazdyView.TabelaPojazdy m = new PojazdyView.TabelaPojazdy();
            for (Konto r : kontaslownikowe) {
                if (stronywiersza.size() > 0) {
                    double suma = 0;
                    List<StronaWiersza> listastron = Collections.synchronizedList(new ArrayList<>());
                    for (StronaWiersza s : stronywiersza) {
                        if (s.getKonto().getNazwapelna().equals("ZS228CG") && r.getPelnynumer().equals("403-2")) {
                        }
                        if (s.getKonto().getNazwapelna().equals(p.getNrrejestracyjny()) && s.getKonto().getKontomacierzyste()!=null && s.getKonto().getKontomacierzyste().equals(r)) {
                            if (s.getDokfk().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                                suma += sumuj(s);
                                listastron.add(s);
                            }
                        }
                    }
                    total += suma;
                    if (suma != 0.0) {
                        l.add(stworzmiejscekosztzest(r, suma, total, listastron));
                    }
                }
            }
            if (l.size() > 0) {
                m.setId(i++);
                m.setPojazd(p);
                m.setPojazdyZest(l);
                listasumpojazdy.add(m);
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

    private static PojazdyZest stworzmiejscekosztzest(Konto r, double suma, double total, List<StronaWiersza> kontozapisy) {
        PojazdyZest t = new PojazdyZest();
        t.setKontonazwa(r.getNazwapelna());
        t.setKontonumer(r.getPelnynumer());
        t.setSumaokres(suma);
        t.setSumanarastajaco(total);
        t.setStronywiersza(kontozapisy);
        return t;
    }

}
