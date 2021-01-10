/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.PracownikFacade;
import entity.Pracownik;
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
public class PracownikView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pracownik selected;
    private Pracownik selectedlista;
    private Pracownik selectedeast;
    private List<Pracownik> lista;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = pracownikFacade.findAll();
    }

    public void initRecznie() {
        init();
    }
    
    public void create() {
      if (selected!=null) {
          try {
            pracownikFacade.create(selected);
            lista.add(selected);
            wpisView.setPracownik(selected);
            selected = new Pracownik();
            Msg.msg("Dodano nowego pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego pracownika");
          }
      }
    }
    
    public void aktywuj(Pracownik pracownik) {
        if (pracownik!=null) {
            wpisView.setPracownik(pracownik);
            Msg.msg("Aktywowano pracownika");
        }
    }
    
    public void usun(Pracownik pracownik) {
        if (pracownik!=null) {
            pracownikFacade.remove(pracownik);
            lista.remove(pracownik);
            if (wpisView.getPracownik().equals(pracownik)) {
                wpisView.setPracownik(null);
            }
            Msg.msg("Usunięto pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika");
        }
    }
    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Pracownik> getLista() {
        return lista;
    }

    public void setLista(List<Pracownik> lista) {
        this.lista = lista;
    }

    public Pracownik getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pracownik selectedlista) {
        this.selectedlista = selectedlista;
    }

    public Pracownik getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Pracownik selectedeast) {
        this.selectedeast = selectedeast;
    }
    
    
    
}
