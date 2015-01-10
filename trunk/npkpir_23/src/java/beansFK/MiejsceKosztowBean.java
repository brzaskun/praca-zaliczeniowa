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
public class MiejsceKosztowBean {

    public static void zsumujkwotyzkont(MiejsceKosztow p, List<Konto> kontaslownikowe, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO, Map<MiejsceKosztow, List<MiejsceKosztowZest>> listasummiejsckosztow) {
        double total = 0;
        for (Konto r : kontaslownikowe) {
            List<StronaWiersza> kontozapisy = stronaWierszaDAO.findStronaByPodatnikKontoMacierzysteMcWaluta(wpisView.getPodatnikObiekt(), r, wpisView.getMiesiacWpisu(), "PLN", p);
            double suma = 0;
            for (StronaWiersza s : kontozapisy) {
                suma += sumuj(s,p);
            }
            total += suma;
            List<MiejsceKosztowZest> l = listasummiejsckosztow.get(p);
            if (l == null) {
                l = new ArrayList<>();
                l.add(stworzmiejscekosztzest(r, suma, total, kontozapisy));
                listasummiejsckosztow.put(p, l);
            } else {
                MiejsceKosztowZest m = stworzmiejscekosztzest(r, suma, total, kontozapisy);
                l.remove(m);
                l.add(m);
            }
            
        }
    }

    private static double sumuj(StronaWiersza s, MiejsceKosztow p) {
        if (s.getWnma().equals("Wn")) {
            return s.getKwota();
        } else {
            return -s.getKwota();
        }
    }

    private static MiejsceKosztowZest stworzmiejscekosztzest(Konto r, double suma, double total, List<StronaWiersza> kontozapisy) {
        MiejsceKosztowZest t = new MiejsceKosztowZest();
        t.setKonto(r);
        t.setSumaokres(suma);
        t.setSumanarastajaco(total);
        t.setStronywiersza(kontozapisy);
        return t;
    }

    
}
