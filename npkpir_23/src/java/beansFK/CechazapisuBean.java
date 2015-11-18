/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.CechazapisuDAOfk;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
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
@Stateless
public class CechazapisuBean {
    
    public static List<StronaWiersza> pobierzwierszezcecha(List<StronaWiersza> zapisy, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        listazcecha.addAll(wierszezcecha(zapisy, nazwacechy, mc));
        listazcecha.addAll(dokumentyzcecha(zapisy, nazwacechy, mc));
        return listazcecha;
    }
    
    private static List<StronaWiersza> wierszezcecha(List<StronaWiersza> lista, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        for (StronaWiersza p : lista) {
            if (p.getDokfk().getMiesiac().equals(mc)) {
                if (p.getCechazapisuLista() != null && p.getCechazapisuLista().size() > 0) {
                    for (Cechazapisu r : p.getCechazapisuLista()) {
                        if (r.getCechazapisuPK().getNazwacechy().equals(nazwacechy)) {
                            listazcecha.add(p);
                        }
                    }
                }
            }
        }
        return listazcecha;
    }
    
    private static List<StronaWiersza> dokumentyzcecha(List<StronaWiersza> lista, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        for (StronaWiersza p : lista) {
            Dokfk d = p.getDokfk();
            if (d.getMiesiac().equals(mc)) {
                if (d.getCechadokumentuLista() != null && d.getCechadokumentuLista().size() > 0) {
                    for (Cechazapisu r : d.getCechadokumentuLista()) {
                        if (r.getCechazapisuPK().getNazwacechy().equals(nazwacechy)) {
                            listazcecha.add(p);
                        }
                    }
                }
            }
        }
        return listazcecha;
    }

    public static double sumujcecha(List<StronaWiersza> zapisycechakoszt, String nazwacechy, String mc) {
        double suma = 0;
        for (StronaWiersza p : zapisycechakoszt) {
            if (p.getDokfk().getMiesiac().equals(mc)) {
                if (nazwacechy.equals("NKUP") || nazwacechy.equals("PMN")) {
                    if (p.getWnma().equals("Wn")) {
                        suma += p.getKwotaPLN();
                    } else {
                        suma -= p.getKwotaPLN();
                    }
                } else {
                    if (p.getWnma().equals("Wn")) {
                        suma += p.getKwotaPLN();
                    } else {
                        suma -= p.getKwotaPLN();
                    }
                }
            }
        }
        return suma;
    }
    
    
}
