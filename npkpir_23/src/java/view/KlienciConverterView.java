/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KlienciConverterView implements Serializable{
    
    @Inject
    private KlienciDAO klienciDAO;
    
    
    
     public List<Klienci> completeKL(String query) {
        List<Klienci> results = new ArrayList<>();
        if (query.length() > 3) {
            List<Klienci> listaKlientow = klienciDAO.findAll();
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            pattern = Pattern.compile("[A-Z]{3}\\d+");
            m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny2 = m.matches();
            if (czynipzagraniczny || czynipzagraniczny2) {
                for (Klienci p : listaKlientow) {
                    if (p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                }
            } else {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Klienci p : listaKlientow) {
                        if (p.getNip().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Klienci p : listaKlientow) {
                        if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        }
        return results;
    }

    public List<Klienci> getListaKlientow() {
        return klienciDAO.findAll();
    }

    public static void main(String[] args) {
        System.out.println("lo23".toUpperCase());
    }
       
}
