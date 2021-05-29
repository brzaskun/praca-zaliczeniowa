/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
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
        if (wpisView.getAngaz() != null) {
            listaskladnikiwynagrodzenia = skladnikWynagrodzeniaFacade.findByPracownik(wpisView.getAngaz().getPracownik());
            if (listaskladnikiwynagrodzenia != null && !listaskladnikiwynagrodzenia.isEmpty()) {
                lista = zmiennaWynagrodzeniaFacade.findBySkladnik(listaskladnikiwynagrodzenia.get(0));
            }
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
            if (lista!=null && lista.size()>0) {
                zakonczokrespoprzedni(lista,selected);
            }
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
    
     public void edytuj(Zmiennawynagrodzenia selected) {
      if (selected!=null && selected.getSkladnikwynagrodzenia()!=null) {
          try {
            zmiennaWynagrodzeniaFacade.edit(selected);
            Msg.msg("Zmieniono zmienną wyn");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji zmiennej wyn");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    
    private void zakonczokrespoprzedni(List<Zmiennawynagrodzenia> lista, Zmiennawynagrodzenia selected) {
        try {
            Zmiennawynagrodzenia ostatnia = lista.get(lista.size()-1);
            String nowadataod = selected.getDataod();
            String wyliczonadatado = Data.odejmijdni(nowadataod, 1);
            ostatnia.setDatado(wyliczonadatado);
            zmiennaWynagrodzeniaFacade.edit(ostatnia);
        } catch (Exception e) {}
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
