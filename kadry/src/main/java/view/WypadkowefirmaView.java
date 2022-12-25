/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.WypadkowefirmaFacade;
import entity.Wypadkowefirma;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WypadkowefirmaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private WypadkowefirmaFacade wypadkowefirmaFacade;
    private List<Wypadkowefirma> lista;
    @Inject
    private Wypadkowefirma selected;

    @PostConstruct
    public void init() {
        if (wpisView.getFirma()!=null) {
            lista = wypadkowefirmaFacade.findByFirma(wpisView.getFirma());
        }
    }
    
    public void dodaj() {
        if (selected!=null&& selected.getDataod()!=null) {
            selected.setFirma(wpisView.getFirma());
            wypadkowefirmaFacade.create(selected);
            lista.add(selected);
            selected = new Wypadkowefirma();
            Msg.msg("Dodano pozycję");
        } else {
            Msg.msg("e","Wystąpił błąd. Nie dodatno pozycji");
        }
    }
    
    public void usun(Wypadkowefirma wyp) {
        if (wyp!=null&& wyp.getDataod()!=null) {
            wypadkowefirmaFacade.create(wyp);
            lista.remove(wyp);
            Msg.msg("Usunięto pozycję");
        } else {
            Msg.msg("e","Wystąpił błąd. Nie usunięto pozycji");
        }
    }
    
    
    
    public List<Wypadkowefirma> getLista() {
        return lista;
    }

    public void setLista(List<Wypadkowefirma> lista) {
        this.lista = lista;
    }

    public Wypadkowefirma getSelected() {
        return selected;
    }

    public void setSelected(Wypadkowefirma selected) {
        this.selected = selected;
    }
 
    
    
}
