/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import dao.DelegacjaDAO;
import embeddablefk.DelegacjaZest;
import entityfk.Delegacja;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named

public class DelegacjaBean {

    public static void zsumujkwotyzkont(Delegacja p, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, Map<Delegacja, List<DelegacjaZest>> listasummiejsckosztow) {
        double total = 0;
        for (Konto r : kontaslownikowe) {
            List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(wpisView.getPodatnikObiekt(), r, wpisView.getMiesiacWpisu(), "PLN", p);
            double suma = 0;
            for (StronaWiersza s : kontozapisy) {
                suma += sumuj(s,p);
            }
            total += suma;
            List<DelegacjaZest> l = listasummiejsckosztow.get(p);
            if (l == null) {
                l = Collections.synchronizedList(new ArrayList<>());
                l.add(stworzmiejscekosztzest(r, suma, total, kontozapisy));
                listasummiejsckosztow.put(p, l);
            } else {
                DelegacjaZest m = stworzmiejscekosztzest(r, suma, total, kontozapisy);
                l.remove(m);
                l.add(m);
            }
            
        }
    }

    private static double sumuj(StronaWiersza s, Delegacja p) {
        if (s.getWnma().equals("Wn")) {
            return s.getKwota();
        } else {
            return -s.getKwota();
        }
    }

    private static DelegacjaZest stworzmiejscekosztzest(Konto r, double suma, double total, List<StronaWiersza> kontozapisy) {
        DelegacjaZest t = new DelegacjaZest();
        t.setKonto(r);
        t.setSumaokres(suma);
        t.setSumanarastajaco(total);
        t.setStronywiersza(kontozapisy);
        return t;
    }

    public static int sprawdzczyjestdelegacja(DelegacjaDAO delegacjaDAO, String nrdelegacji) {
        int jest1niema0 = 0;
        try {
            jest1niema0 = delegacjaDAO.findDelegacjaByNr(nrdelegacji);
        } catch (Exception e) {
            E.e(e);
        }
        return jest1niema0;
    }
    
}
