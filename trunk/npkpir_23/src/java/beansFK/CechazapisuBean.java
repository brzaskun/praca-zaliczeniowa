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
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class CechazapisuBean {
    
    public static List<StronaWiersza> pobierzwierszezcecha(List<StronaWiersza> zapisy, String nazwacechy) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        listazcecha.addAll(wierszezcecha(zapisy, nazwacechy));
        listazcecha.addAll(dokumentyzcecha(zapisy, nazwacechy));
        return listazcecha;
    }
    
    private static List<StronaWiersza> wierszezcecha(List<StronaWiersza> lista, String nazwacechy) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        for (StronaWiersza p : lista) {
            if (p.getCechazapisuLista() != null && p.getCechazapisuLista().size() > 0) {
                for (Cechazapisu r : p.getCechazapisuLista()) {
                    if (r.getCechazapisuPK().getNazwacechy().equals(nazwacechy)) {
                        listazcecha.add(p);
                    }
                }
            }
        }
        return listazcecha;
    }
    
    private static List<StronaWiersza> dokumentyzcecha(List<StronaWiersza> lista, String nazwacechy) {
        List<StronaWiersza> listazcecha = new ArrayList<>();
        for (StronaWiersza p : lista) {
            Dokfk d = p.getDokfk();
            if (d.getCechadokumentuLista() != null && d.getCechadokumentuLista().size() > 0) {
                for (Cechazapisu r : d.getCechadokumentuLista()) {
                    if (r.getCechazapisuPK().getNazwacechy().equals(nazwacechy)) {
                        listazcecha.add(p);
                    }
                }
            }
        }
        return listazcecha;
    }

    public static double sumujcecha(List<StronaWiersza> zapisycechakoszt, String nazwacechy) {
        double suma = 0;
        for (StronaWiersza p : zapisycechakoszt) {
            if (nazwacechy.equals("NKUP")) {
                if (p.getWnma().equals("Wn")) {
                    suma -= p.getKwotaPLN();
                } else {
                    suma += p.getKwotaPLN();
                }
            } else {
                if (p.getWnma().equals("Wn")) {
                    suma += p.getKwotaPLN();
                } else {
                    suma -= p.getKwotaPLN();
                }
            }
        }
        return suma;
    }
    
    
}
