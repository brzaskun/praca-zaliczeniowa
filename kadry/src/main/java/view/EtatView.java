/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EtatFacade;
import entity.Etat;
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
public class EtatView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Etat selected;
    @Inject
    private EtatFacade etatFacade;
    private List<Etat> lista;
    private Etat selectedlista;
    
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        selected.setUmowa(wpisView.getUmowa());
        lista = etatFacade.findByUmowa(wpisView.getUmowa());
    }
    
    public void create() {
      if (selected!=null && wpisView.getUmowa()!=null) {
          try {
            selected.setUmowa(wpisView.getUmowa());
            etatFacade.create(selected);
            lista.add(selected);
            selected = new Etat();
            Msg.msg("Dodano etat");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano etatu");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunEtat(Etat zmienna) {
        if (zmienna!=null) {
            etatFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto etat");
        } else {
            Msg.msg("e","Nie wybrano etatu");
        }
    }

    public Etat getSelected() {
        return selected;
    }

    public void setSelected(Etat selected) {
        this.selected = selected;
    }

    public List<Etat> getLista() {
        return lista;
    }

    public void setLista(List<Etat> lista) {
        this.lista = lista;
    }

    public Etat getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Etat selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
    
    
}
