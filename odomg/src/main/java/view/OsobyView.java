/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZakladpracyFacade;
import entity.Uczestnicy;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OsobyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private Zakladpracy zakladpracy;
    private List<Zakladpracy> lista;
    private List<Uczestnicy> listaosoby;
    @Inject
    private ZakladpracyFacade zakladpracyFacade;
    
    @PostConstruct
    private void init() {
        lista = zakladpracyFacade.findAll();
    }
    
    public List<Zakladpracy> complete(String query) {
        List<Zakladpracy> results = new ArrayList<>();
        try {
            for (Zakladpracy p : lista) {
                if (p.getNazwazakladu().toLowerCase().contains(query.toLowerCase())) {
                    results.add(p);
                }
            }
        } catch (Exception e){}
        return results;
    }
    
    public void pobierzosoby() {
        if (zakladpracy!=null) {
            
        }
    }

    public Zakladpracy getZakladpracy() {
        return zakladpracy;
    }

    public void setZakladpracy(Zakladpracy zakladpracy) {
        this.zakladpracy = zakladpracy;
    }

    public List<Zakladpracy> getLista() {
        return lista;
    }

    public void setLista(List<Zakladpracy> lista) {
        this.lista = lista;
    }

    public List<Uczestnicy> getListaosoby() {
        return listaosoby;
    }

    public void setListaosoby(List<Uczestnicy> listaosoby) {
        this.listaosoby = listaosoby;
    }
    
    
}
