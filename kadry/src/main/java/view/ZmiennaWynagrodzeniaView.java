/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import entity.Skladnikwynagrodzenia;
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
    private List<Skladnikwynagrodzenia> listaskladnikiwynagrodzenia;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz()!=null) {
            listaskladnikiwynagrodzenia = skladnikWynagrodzeniaFacade.findByPracownik(wpisView.getAngaz().getPracownik());
        }
    }
    
    public void init2(Skladnikwynagrodzenia skladnikwynagrodzenia) {
       if (skladnikwynagrodzenia!=null) {
            lista  = zmiennaWynagrodzeniaFacade.findBySkladnik(skladnikwynagrodzenia);
            selected.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
       }
   }
    

    public void create() {
      if (selected!=null && selected.getSkladnikwynagrodzenia()!=null) {
          try {
            zmiennaWynagrodzeniaFacade.create(selected);
            lista.add(selected);
            selected = new Zmiennawynagrodzenia();
            Msg.msg("Dodano zmienną wyn");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano zmiennej wyn");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    public void usunZmiennaWyn(Zmiennawynagrodzenia zmienna) {
        if (zmienna!=null) {
            zmiennaWynagrodzeniaFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto zmienną");
        } else {
            Msg.msg("e","Nie wybrano zmiennej");
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

    public List<Skladnikwynagrodzenia> getListaskladnikiwynagrodzenia() {
        return listaskladnikiwynagrodzenia;
    }

    public void setListaskladnikiwynagrodzenia(List<Skladnikwynagrodzenia> listaskladnikiwynagrodzenia) {
        this.listaskladnikiwynagrodzenia = listaskladnikiwynagrodzenia;
    }


    
    
    
}
