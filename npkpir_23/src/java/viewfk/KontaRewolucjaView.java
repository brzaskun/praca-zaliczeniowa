/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewfk;

import dao.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KontaRewolucjaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    KontoDAOfk kontoDAOfk;

    public void przenieskonta() {
        List<Konto> kontacalyswiat = kontoDAOfk.findAll();
        int size = kontacalyswiat.size();
        System.out.println("size " + size);
        kontacalyswiat.parallelStream().forEach(konto -> {
            String zwyklerozrachszczegolne = konto.getZwyklerozrachszczegolne();
            if (zwyklerozrachszczegolne != null) {
                switch (zwyklerozrachszczegolne) {
                    case "zwyk\u0142e":
                        konto.setDwasalda(false);
                        konto.setRozrachunkowe(false);
                        konto.setKontovat(false);
                        break;
                    case "szczeg\u00f3lne":
                        konto.setDwasalda(true);
                        konto.setRozrachunkowe(false);
                        konto.setKontovat(false);
                        break;
                    case "rozrachunkowe":
                        konto.setDwasalda(true);
                        konto.setRozrachunkowe(true);
                        konto.setKontovat(false);
                        break;
                    case "vat":
                        konto.setDwasalda(false);
                        konto.setRozrachunkowe(false);
                        konto.setKontovat(true);
                        break;
                }
            }
            String bilansowewynikowe = konto.getBilansowewynikowe();
            if (bilansowewynikowe != null) {
                konto.setBilansowe(bilansowewynikowe.equals("bilansowe"));
            }
        });
        System.out.println("zmiany naniesione. sejf teraz");
        kontoDAOfk.editList(kontacalyswiat);
        System.out.println("KONIEC!!!!!!!!!!");
    }
    
}
