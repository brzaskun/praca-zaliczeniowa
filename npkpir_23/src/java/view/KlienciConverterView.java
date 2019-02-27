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
import javax.annotation.PostConstruct;
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
    private List<Klienci> listaKlientow;
    
    
    @PostConstruct
    public void init() {
        listaKlientow = Collections.synchronizedList(klienciDAO.findAll());
    }
    
    public List<Klienci> completeKL(String query) {
        List<Klienci> results = Collections.synchronizedList(new ArrayList<>());
        if (query.length() > 3) {
            Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
            Matcher m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny = m.matches();
            pattern = Pattern.compile("[A-Z]{3}\\d+");
            m = pattern.matcher(query.toUpperCase());
            boolean czynipzagraniczny2 = m.matches();
            if (czynipzagraniczny || czynipzagraniczny2) {
                listaKlientow.parallelStream().forEach((p)->{
                    if (p.getNip().startsWith(query.toUpperCase())) {
                            results.add(p);
                    }
                });
            } else {
                try {
                    //sluzydosporawdzenia czy chodzi o nip
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    listaKlientow.parallelStream().forEach((p)->{
                        if (p.getNip().startsWith(query)) {
                            results.add(p);
                        }
                    });
                } catch (NumberFormatException e) {
                    listaKlientow.parallelStream().forEach((p)->{
                        if (p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    });
                }
            }
            pattern = Pattern.compile("[0-9]{10}");
            m = pattern.matcher(query);
            boolean czytopolskinip  = m.matches();
            if (czytopolskinip) {
                klientautomat = new Klienci("dodaj klienta automatycznie", "", query, "", "", "", "", "");
                results.add(klientautomat);
                
            }
            results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1"));
        }
        return results;
    }

    public void dodajdolisty(Klienci nowy) {
        listaKlientow.add(nowy);
    }
    
    public List<Klienci> getListaKlientow() {
        return klienciDAO.findAll();
    }

    public Klienci getKlientautomat() {
        return klientautomat;
    }

              
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("[0-9]{10}");
        Matcher  m = pattern.matcher("851100500");
        boolean czytopolskinip  = m.matches();
    }
}
