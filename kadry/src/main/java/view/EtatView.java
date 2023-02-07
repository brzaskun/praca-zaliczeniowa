/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.EtatBean;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import entity.EtatPrac;
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
    private EtatPrac selected;
    @Inject
    private EtatPracFacade etatFacade;
    private List<EtatPrac> lista;
    private EtatPrac selectedlista;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null){
            selected.setAngaz(wpisView.getAngaz());
            lista = etatFacade.findByAngaz(wpisView.getAngaz());
        }
    }
    
    public void create() {
      if (selected!=null && wpisView.getUmowa()!=null) {
          if (selected.getId()==null) {
            try {
              selected.setAngaz(wpisView.getAngaz());
              etatFacade.create(selected);
              lista.add(selected);
              EtatBean.edytujkalendarz(selected, kalendarzmiesiacFacade);
              selected = new EtatPrac();
              Msg.msg("Dodano etat");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano etatu");
            }
          } else {
              try {
                etatFacade.edit(selected);
                EtatBean.edytujkalendarz(selected, kalendarzmiesiacFacade);
                selected = new EtatPrac();
                Msg.msg("Edytowano etat");
              } catch (Exception e) {
                  Msg.msg("e", "Błąd - nie zmieniono etatu");
              }
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

    public void wybierzdoedycji(EtatPrac selected) {
      if (selected!=null && selected.getAngaz()!=null) {
          try {
            this.selected  = selected;
            Msg.msg("Zmieniono etat do edycji");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji etatu");
          }
      } else {
          Msg.msg("e", "Nie wybrano etatu");
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
