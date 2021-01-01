/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikWynagrodzeniaFacade;
import entity.Skladnikwynagrodzenia;
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
public class SkladnikWynagrodzeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Skladnikwynagrodzenia selected;
    @Inject
    private Skladnikwynagrodzenia selectedlista;
    private List<Skladnikwynagrodzenia> lista;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = skladnikWynagrodzeniaFacade.findAll();
    }

    public void create() {
      if (selected!=null && wpisView.getUmowa()!=null) {
          try {
            selected.setUmowa(wpisView.getUmowa());
            skladnikWynagrodzeniaFacade.create(selected);
            lista.add(selected);
            selected = new Skladnikwynagrodzenia();
            Msg.msg("Dodano nowy składnik wynagrodzenia");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego składnika wynagrodzenai");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    

    public Skladnikwynagrodzenia getSelected() {
        return selected;
    }

    public void setSelected(Skladnikwynagrodzenia selected) {
        this.selected = selected;
    }

    public Skladnikwynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Skladnikwynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Skladnikwynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Skladnikwynagrodzenia> lista) {
        this.lista = lista;
    }


    
    
    
}
