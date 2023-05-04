/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.StanowiskopracFacade;
import entity.Stanowiskoprac;
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
public class StanowiskoPracView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Stanowiskoprac selected;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    private List<Stanowiskoprac> lista;
    @Inject
    private Stanowiskoprac selectedlista;
    
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz()!=null) {
            selected.setAngaz(wpisView.getAngaz());
            lista = stanowiskopracFacade.findByAngaz(wpisView.getAngaz());
            if (lista==null) {
                lista = new ArrayList<>();
            } else if (lista.size()>0){
                selectedlista = lista.get(0);
            }
            lista.add(new Stanowiskoprac(wpisView.getAngaz()));
        }
    }
    
    public void create(Stanowiskoprac selected) {
      if (selected!=null && wpisView.getAngaz()!=null) {
          if (selected.getId()==null) {
            try {
              selected.setAngaz(wpisView.getAngaz());
              stanowiskopracFacade.create(selected);
              lista.add(new Stanowiskoprac(wpisView.getAngaz()));
              Msg.msg("Dodano stanowisko");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano stanowiska");
            }
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
     public void edit(Stanowiskoprac selected) {
      if (selected!=null && wpisView.getAngaz()!=null) {
          if (selected.getId()!=null) {
              try {
              stanowiskopracFacade.edit(selected);
              Msg.msg("Edytowano stanowisko");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie edytowano stanowiska");
            }
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usun(Stanowiskoprac zmienna) {
        if (zmienna!=null) {
            stanowiskopracFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto etat");
        } else {
            Msg.msg("e","Nie wybrano etatu");
        }
    }
    
    public void wybierzdoedycji(Stanowiskoprac selected) {
      if (selected!=null && selected.getAngaz()!=null) {
          try {
            this.selected  = selected;
            Msg.msg("Zmieniono stanowisko");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji stanowiska");
          }
      } else {
          Msg.msg("e", "Nie wybrano stanowiska");
      }
    }

    public Stanowiskoprac getSelected() {
        return selected;
    }

    public void setSelected(Stanowiskoprac selected) {
        this.selected = selected;
    }

    public List<Stanowiskoprac> getLista() {
        return lista;
    }

    public void setLista(List<Stanowiskoprac> lista) {
        this.lista = lista;
    }

    public Stanowiskoprac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Stanowiskoprac selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
    
    
}
