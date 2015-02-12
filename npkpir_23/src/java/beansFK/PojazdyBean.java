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
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PojazdyBean {

    public static void zsumujkwotyzkont(Pojazdy p, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, Map<Pojazdy, List<PojazdyZest>> listasummiejsckosztow) {
        double total = 0;
        for (Konto r : kontaslownikowe) {
            List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(wpisView.getPodatnikObiekt(), r, wpisView.getMiesiacWpisu(), "PLN", p);
            if (kontozapisy.size() > 0) {
                double suma = 0;
                for (StronaWiersza s : kontozapisy) {
                    suma += sumuj(s, p);
                }
                total += suma;
                List<PojazdyZest> l = listasummiejsckosztow.get(p);
                if (l == null) {
                    l = new ArrayList<>();
                    l.add(stworzmiejscekosztzest(r, suma, total, kontozapisy));
                    listasummiejsckosztow.put(p, l);
                } else {
                    PojazdyZest m = stworzmiejscekosztzest(r, suma, total, kontozapisy);
                    l.remove(m);
                    l.add(m);
                }
            }
        }
    }

    private static double sumuj(StronaWiersza s, Pojazdy p) {
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
