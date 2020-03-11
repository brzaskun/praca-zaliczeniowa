/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import javax.inject.Named;
import viewfk.CechyzapisuPrzegladView;

/**
 *
 * @author Osito
 */
@Named

public class CechazapisuBean {
    
    public static List<StronaWiersza> pobierzwierszezcecha(List<StronaWiersza> zapisy, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = Collections.synchronizedList(new ArrayList<>());
        listazcecha.addAll(wierszezcecha(zapisy, nazwacechy, mc));
        listazcecha.addAll(dokumentyzcecha(zapisy, nazwacechy, mc));
        return listazcecha;
    }
    
    private static List<StronaWiersza> wierszezcecha(List<StronaWiersza> lista, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = Collections.synchronizedList(new ArrayList<>());
        lista.stream().filter((p) -> (p.getDokfk().getMiesiac().equals(mc))).filter((p) -> (p.getCechazapisuLista() != null && p.getCechazapisuLista().size() > 0)).forEachOrdered((p) -> {
            for (Cechazapisu r : p.getCechazapisuLista()) {
                if (r.getNazwacechy().equals(nazwacechy)) {
                    listazcecha.add(p);
                }
            }
        });
        return listazcecha;
    }
    
    private static List<StronaWiersza> dokumentyzcecha(List<StronaWiersza> lista, String nazwacechy, String mc) {
        List<StronaWiersza> listazcecha = Collections.synchronizedList(new ArrayList<>());
        lista.stream().filter((p) -> (p.getDokfk().getMiesiac().equals(mc))).filter((p) -> (p.getDokfk().getCechadokumentuLista() != null && p.getDokfk().getCechadokumentuLista().size() > 0)).forEachOrdered((p) -> {
            for (Cechazapisu r : p.getDokfk().getCechadokumentuLista()) {
                if (r.getNazwacechy().equals(nazwacechy)) {
                    listazcecha.add(p);
                }
            }
        });
        return listazcecha;
    }

    public static double sumujcecha(List<StronaWiersza> zapisycechakoszt, String nazwacechy, String mc) {
        double suma = 0;
        for (StronaWiersza p : zapisycechakoszt) {
            if (p.getDokfk().getMiesiac().equals(mc)) {
                if (nazwacechy.equals("NKUP") || nazwacechy.equals("KUPMN")) {
                    if (p.getWnma().equals("Wn")) {
                        suma += p.getKwotaPLN();
                    } else {
                        suma -= p.getKwotaPLN();
                    }
                } else {
                    if (p.getWnma().equals("Ma")) {
                        suma -= p.getKwotaPLN();
                    } else {
                        suma += p.getKwotaPLN();
                    }
                }
            }
        }
        return suma;
    }
    
    public static List<CechyzapisuPrzegladView.CechaStronaWiersza> pobierzstrony(List<Dokfk> wykazZaksiegowanychDokumentow) {
        List<CechyzapisuPrzegladView.CechaStronaWiersza> zapisyZCecha = Collections.synchronizedList(new ArrayList<>());
        for (Dokfk p : wykazZaksiegowanychDokumentow) {
               if (p.getCechadokumentuLista() != null && p.getCechadokumentuLista().size() > 0) {
                   for (Cechazapisu r: p.getCechadokumentuLista()) {
                       zapisyZCecha.addAll(CechazapisuBean.pobierzStronyzDokfk(r, p.getListawierszy()));
                   }
               } 
               if (p.getListawierszy() != null) {
                   for (Wiersz r : p.getListawierszy()) {
                       zapisyZCecha.addAll(CechazapisuBean.pobierzpojedynczo(r));
                   }
               }
           }
        return zapisyZCecha;
    }
    
    public static Collection<? extends CechyzapisuPrzegladView.CechaStronaWiersza> pobierzStronyzDokfk(Cechazapisu r, List<Wiersz> listawierszy) {
        List<CechyzapisuPrzegladView.CechaStronaWiersza> lista = Collections.synchronizedList(new ArrayList<>());
        for (Wiersz p : listawierszy) {
            if (p.getStronaWn() != null) {
                if (p.getStronaWn().getKonto().getBilansowewynikowe().equals("wynikowe")) {
                    lista.add(new CechyzapisuPrzegladView.CechaStronaWiersza(r, p.getStronaWn()));
                }
            }
            if (p.getStronaMa() != null) {
                if (p.getStronaMa().getKonto().getBilansowewynikowe().equals("wynikowe")) {
                    lista.add(new CechyzapisuPrzegladView.CechaStronaWiersza(r, p.getStronaMa()));
                }
            }
        }
        return lista;
    }

    public static Collection<? extends CechyzapisuPrzegladView.CechaStronaWiersza> pobierzpojedynczo(Wiersz r) {
        List<CechyzapisuPrzegladView.CechaStronaWiersza> lista = Collections.synchronizedList(new ArrayList<>());
        if (r.getStronaWn() != null) {
            if (r.getStronaWn().getKwota() == 48.27) {
            }
            if (r.getStronaWn().getKonto().getBilansowewynikowe().equals("wynikowe") && r.getStronaWn().getCechazapisuLista() != null) {
                for (Cechazapisu s : r.getStronaWn().getCechazapisuLista()) {
                    lista.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, r.getStronaWn()));
                }
            }
        }
        if (r.getStronaMa() != null) {
            if (r.getStronaMa().getKwota() == 48.27) {
            }
            if (r.getStronaMa().getKonto().getBilansowewynikowe().equals("wynikowe") && r.getStronaMa().getCechazapisuLista() != null) {
                for (Cechazapisu s : r.getStronaMa().getCechazapisuLista()) {
                    lista.add(new CechyzapisuPrzegladView.CechaStronaWiersza(s, r.getStronaMa()));
                }
            }
        }
        return lista;
    }
    
    public static void luskaniezapisowZCechami(String wybranacechadok, List<StronaWiersza> zapisyRok) {
        if (wybranacechadok != null) {
                for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
                StronaWiersza p = it.next();
                if (p.getDokfk().getCechadokumentuLista() != null && p.getDokfk().getCechadokumentuLista().size() > 0) {
                    boolean usun = true;
                    for (Cechazapisu cz : p.getDokfk().getCechadokumentuLista()) {
                        if (cz.getNazwacechy().equals(wybranacechadok)) {
                            usun = false;
                            break;
                        }
                    }
                    if (usun) {
                        it.remove();
                    }
                } else if (p.getCechazapisuLista() != null && p.getCechazapisuLista().size() > 0) {
                    boolean usun = true;
                    for (Cechazapisu cz : p.getCechazapisuLista()) {
                        if (cz.getNazwacechy().equals(wybranacechadok)) {
                            usun = false;
                            break;
                        }
                    }
                    if (usun) {
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
    .stream()
    .filter(s -> {
        System.out.format("filter: %s [%s]\n",
            s, Thread.currentThread().getName());
        return true;
    })
    .map(s -> {
        System.out.format("map: %s [%s]\n",
            s, Thread.currentThread().getName());
        return s.toUpperCase();
    })
    .forEach(s -> System.out.format("forEach: %s [%s]\n",
        s, Thread.currentThread().getName()));
    }
}
