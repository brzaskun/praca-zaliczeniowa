/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZmiennaWynagrodzeniaFacade;
import entity.Zmiennawynagrodzenia;
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
public class ZmiennaWynagrodzeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Zmiennawynagrodzenia selected;
    @Inject
    private Zmiennawynagrodzenia selectedlista;
    private List<Zmiennawynagrodzenia> lista;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = zmiennaWynagrodzeniaFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            zmiennaWynagrodzeniaFacade.create(selected);
            lista.add(selected);
            selected = new Zmiennawynagrodzenia();
            Msg.msg("Dodano nową firmę");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    

    public Zmiennawynagrodzenia getSelected() {
        return selected;
    }

    public void setSelected(Zmiennawynagrodzenia selected) {
        this.selected = selected;
    }

    public Zmiennawynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Zmiennawynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Zmiennawynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Zmiennawynagrodzenia> lista) {
        this.lista = lista;
    }


    
    
    
}
