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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KlienciConverterView implements Serializable{
    
    private List<Klienci> listaKlientow;
    @Inject
    private KlienciDAO klienciDAO;
    
    @PostConstruct
    private void init() {
        listaKlientow = klienciDAO.findAll();
    }
    
    
     public List<Klienci> completeKL(String query) {
        List<Klienci> results = new ArrayList<>();
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
        results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        return results;
    }

    public List<Klienci> getListaKlientow() {
        return listaKlientow;
    }

    public void setListaKlientow(List<Klienci> listaKlientow) {
        this.listaKlientow = listaKlientow;
    }

   
    
     
     
}
