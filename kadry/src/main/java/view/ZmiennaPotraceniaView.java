/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikPotraceniaFacade;
import dao.ZmiennaPotraceniaFacade;
import entity.Skladnikpotracenia;
import entity.Zmiennapotracenia;
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
public class ZmiennaPotraceniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Zmiennapotracenia selected;
    @Inject
    private Zmiennapotracenia selectedlista;
    private List<Zmiennapotracenia> lista;
    private List<Skladnikpotracenia> listaskladnikipotracenia;
    @Inject
    private ZmiennaPotraceniaFacade zmiennaPotraceniaFacade;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz() != null) {
            listaskladnikipotracenia = skladnikPotraceniaFacade.findByPracownik(wpisView.getAngaz().getPracownik());
            if (listaskladnikipotracenia != null && !listaskladnikipotracenia.isEmpty()) {
                lista = zmiennaPotraceniaFacade.findBySkladnik(listaskladnikipotracenia.get(0));
                lista.add(new Zmiennapotracenia(listaskladnikipotracenia.get(0)));
            } 
        }
    }
    
    public void init2(Skladnikpotracenia skladnipotracenia) {
       if (skladnipotracenia!=null&&skladnipotracenia.getId()!=null) {
            lista  = zmiennaPotraceniaFacade.findBySkladnik(skladnipotracenia);
            selected.setSkladnikpotracenia(skladnipotracenia);
            lista.add(new Zmiennapotracenia(skladnipotracenia));
       } else {
           lista = null;
       }
   }
    

    public void create(Zmiennapotracenia selected) {
      if (selected!=null && selected.getSkladnikpotracenia()!=null) {
          try {
            if (selected.getId()==null) {
                zmiennaPotraceniaFacade.edit(selected);
                lista.add(new Zmiennapotracenia(selected.getSkladnikpotracenia()));
                Msg.msg("Edytowano potrącenie");
            } 
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano potrącenia");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    
     public void edit(Zmiennapotracenia selected) {
      if (selected!=null && selected.getSkladnikpotracenia()!=null) {
          try {
            if (selected.getId()!=null) {
                zmiennaPotraceniaFacade.edit(selected);
                lista.add(selected);
                Msg.msg("Edytowano potrącenie");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie edytowano potrącenia");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    
    
    
//    private void zakonczokrespoprzedni(List<Zmiennapotracenia> lista, Zmiennapotracenia selected) {
//        try {
//            Zmiennapotracenia ostatnia = lista.get(lista.size()-1);
//            String nowadataod = selected.getDataod();
//            String wyliczonadatado = Data.odejmijdni(nowadataod, 1);
//            ostatnia.setDatado(wyliczonadatado);
//            zmiennaPotraceniaFacade.edit(ostatnia);
//        } catch (Exception e) {}
//    }

    
    public void wybierzdoedycji(Zmiennapotracenia selected) {
      if (selected!=null) {
          try {
            this.selected  = selected;
            Msg.msg("Wybrano potrącenie do edycji");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji potrącenia");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    public void usunZmiennaPotr(Zmiennapotracenia zmienna) {
        if (zmienna!=null) {
            zmiennaPotraceniaFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto zmienną");
        } else {
            Msg.msg("e","Nie wybrano zmiennej");
        }
    }

    public Zmiennapotracenia getSelected() {
        return selected;
    }

    public void setSelected(Zmiennapotracenia selected) {
        this.selected = selected;
    }

    public Zmiennapotracenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Zmiennapotracenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Zmiennapotracenia> getLista() {
        return lista;
    }

    public void setLista(List<Zmiennapotracenia> lista) {
        this.lista = lista;
    }

    public List<Skladnikpotracenia> getListaskladnikipotracenia() {
        return listaskladnikipotracenia;
    }

    public void setListaskladnikipotracenia(List<Skladnikpotracenia> listaskladnikipotracenia) {
        this.listaskladnikipotracenia = listaskladnikipotracenia;
    }

    

    
    
    
}
