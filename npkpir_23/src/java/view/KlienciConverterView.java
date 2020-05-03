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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
    private Klienci klientautomat;
    
    
    
     public List<Klienci> completeKL(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
        if (query.length() > 3) {
            List<Klienci> listaKlientow = klienciDAO.findAllReadOnlyContains(query);
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            pattern = Pattern.compile("[A-Z]{3}\\d+");
            m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny2 = m.matches();
            if (czynipzagraniczny || czynipzagraniczny2) {
                String query2 = query.toUpperCase();
                results = listaKlientow.stream().filter((p)->(p.getNip().startsWith(query2))).collect(Collectors.toList()); 
            } else {
                try {
                    //sluzydosporawdzenia czy chodzi o nip
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    results = listaKlientow.stream().filter((p)->(p.getNip().startsWith(query))).collect(Collectors.toList()); 
                } catch (NumberFormatException e) {
                    String query2 = query.toLowerCase();
                    results = listaKlientow.stream().filter((p)->(p.getNpelna().toLowerCase().contains(query2.toLowerCase()))).collect(Collectors.toList()); 
                }
            }
            pattern = Pattern.compile("[0-9]{10}");
            m = pattern.matcher(query);
            boolean czytopolskinip  = m.matches();
            if (czytopolskinip) {
                klientautomat = new Klienci(-2,"dodaj klienta automatycznie", "", query, "", "", "", "", "");
                results.add(klientautomat);
                
            }
            results.add(new Klienci(-1,"nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        }
        return results;
    }

              
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher  m = pattern.matcher("851100500");
        boolean czytopolskinip  = m.matches();
    }
}
