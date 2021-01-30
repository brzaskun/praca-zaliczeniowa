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
public class PracodawcaDaneView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pracownik> listapracownikow;
    private List<Pracownik> listafiltered;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private Pracownik selected;
    private Pracownik selectedlista;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        listapracownikow = angazFacade.findPracownicyByFirma(wpisView.getFirma());
    }

    
    public void create() {
      if (selected!=null) {
          try {
            pracownikFacade.create(selected);
            listapracownikow.add(selected);
            wpisView.setPracownik(selected);
            selected = new Pracownik();
            Msg.msg("Dodano nowego pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego pracownika");
          }
      }
    }
    
    
    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Pracownik> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Pracownik> listafiltered) {
        this.listafiltered = listafiltered;
    }

    public Pracownik getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pracownik selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
}
