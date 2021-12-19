/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EtatPracFacade;
import entity.EtatPrac;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class EtatView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private EtatPrac selected;
    @Inject
    private EtatPracFacade etatFacade;
    private List<EtatPrac> lista;
    private EtatPrac selectedlista;
    
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null){
            selected.setUmowa(wpisView.getUmowa());
            lista = etatFacade.findByUmowa(wpisView.getUmowa());
        }
    }
    
    public void create() {
      if (selected!=null && wpisView.getUmowa()!=null) {
          try {
            selected.setUmowa(wpisView.getUmowa());
            etatFacade.create(selected);
            lista.add(selected);
            selected = new EtatPrac();
            Msg.msg("Dodano etat");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano etatu");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunEtat(EtatPrac zmienna) {
        if (zmienna!=null) {
            etatFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto etat");
        } else {
            Msg.msg("e","Nie wybrano etatu");
        }
    }

    public EtatPrac getSelected() {
        return selected;
    }

    public void setSelected(EtatPrac selected) {
        this.selected = selected;
    }

    public List<EtatPrac> getLista() {
        return lista;
    }

    public void setLista(List<EtatPrac> lista) {
        this.lista = lista;
    }

    public EtatPrac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(EtatPrac selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
    
    
}
