/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import embeddable.DokKsiega;
import entity.Dok;
import entityfk.Cechazapisu;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Osito
 */
public class CechaBean  implements Serializable{
    
    public static List znajdzcechy(List<Dok> wykazZaksiegowanychDokumentow) {
        List zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
                Set<String> lista = new HashSet<>();
                wykazZaksiegowanychDokumentow.parallelStream().filter((p) -> (p.getCechadokumentuLista() != null && p.getCechadokumentuLista().size() > 0)).forEachOrdered((p) -> {
                    for (Cechazapisu r : p.getCechadokumentuLista()) {
                        lista.add(r.getNazwacechy());
                    }
            });
                zwrot = new ArrayList<>(lista);
                Collections.sort(zwrot);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public static List znajdzcechyPKPiR(List<DokKsiega> wykazZaksiegowanychDokumentow) {
        List zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
                Set<String> lista = new HashSet<>();
                for (DokKsiega p : wykazZaksiegowanychDokumentow) {
                    if (p.getDokument() != null && p.getDokument().getCechadokumentuLista() != null && p.getDokument().getCechadokumentuLista().size() > 0) {
                        for (Cechazapisu r : p.getDokument().getCechadokumentuLista()) {
                            lista.add(r.getNazwacechy());
                        }
                    }
                }
                zwrot = new ArrayList<>(lista);
                Collections.sort(zwrot);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
}
