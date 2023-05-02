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
import java.util.ArrayList;
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
        if (wpisView.getAngaz()!=null){
            selected.setAngaz(wpisView.getAngaz());
            lista = etatFacade.findByAngaz(wpisView.getAngaz());
            if (lista==null) {
                lista = new ArrayList<>();
            } else {
                selectedlista = lista.get(0);
            }
            lista.add(new EtatPrac(wpisView.getAngaz()));
        }
    }
    
    public void create(EtatPrac selected) {
      if (selected!=null && wpisView.getUmowa()!=null) {
          if (selected.getId()==null) {
            try {
              selected.setAngaz(wpisView.getAngaz());
              etatFacade.create(selected);
              lista.add(new EtatPrac(wpisView.getAngaz()));
              EtatBean.edytujkalendarz(selected, kalendarzmiesiacFacade);
              Msg.msg("Dodano etat");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano etatu");
            }
          } 
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void edit(EtatPrac selected) {
      if (selected!=null && wpisView.getUmowa()!=null) {
          if (selected.getId()!=null) {
            try {
              selected.setAngaz(wpisView.getAngaz());
              etatFacade.edit(selected);
              EtatBean.edytujkalendarz(selected, kalendarzmiesiacFacade);
              Msg.msg("Edytowano etat");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie edytowano etatu");
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
            Msg.msg("Zmieniono etat");
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
