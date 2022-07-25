/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KontokategoriaDAOfk;
import entityfk.Kontokategoria;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KontokategoriaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Kontokategoria> lista;
    @Inject
    private Kontokategoria selectedDod;
    @Inject
    private KontokategoriaDAOfk kontokategoriaDAOfk;

    public KontokategoriaView() {
         ////E.m(this);
        this.lista = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        this.lista = kontokategoriaDAOfk.findAll();
    }
    
        
    public void zachowaj() {
        if (lista != null) {
            try {
                kontokategoriaDAOfk.editList(lista);
                Msg.msg("Zachowano kategorie");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zachowano kategori");
            }
        }
    }
    
    public void dodaj() {
        if (selectedDod != null) {
            try {
                selectedDod.setSymbol(selectedDod.getSymbol());
                kontokategoriaDAOfk.create(selectedDod);
                lista.add(selectedDod);
                selectedDod = new Kontokategoria();
                Msg.msg("Dodano nową kategorię");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie dodano nowej kategorii");
            }
        }
    }
    
    public void edytuj(Kontokategoria k) {
        if (k != null) {
            try {
                selectedDod.setSymbol(k.getSymbol());
                kontokategoriaDAOfk.edit(k);
                Msg.msg("Zmieniono opis kategorii");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zmieniono opisu kategorii");
            }
        }
    }
    
    public void usun(Kontokategoria k) {
        if (k != null) {
            try {
                kontokategoriaDAOfk.remove(k);
                lista.remove(k);
                Msg.msg("Usunięto kategorię");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie usunięto kategorii");
            }
        }
    }
    
    public void sprawdzduplikat() {
        for (Kontokategoria p : lista) {
            if (p.getSymbol().equals(selectedDod.getSymbol())) {
                selectedDod.setSymbol(null);
                Msg.msg("e", "Taki symbol został już wprowadzony");
            }
        }
    }

    public List<Kontokategoria> getLista() {
        return lista;
    }

    public void setLista(List<Kontokategoria> lista) {
        this.lista = lista;
    }

   
    public Kontokategoria getSelectedDod() {
        return selectedDod;
    }

    public void setSelectedDod(Kontokategoria selectedDod) {
        this.selectedDod = selectedDod;
    }
    
    
 
            
}
