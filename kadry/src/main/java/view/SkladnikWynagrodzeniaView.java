/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.UmowaFacade;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
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
    private List<Rodzajwynagrodzenia> listarodzajwynagrodzenia;
    private List<Umowa> listaumow;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null) {
            lista  = skladnikWynagrodzeniaFacade.findByUmowa(wpisView.getUmowa());
        }
        listaumow = umowaFacade.findPracownikFirma(wpisView.getPracownik(), wpisView.getFirma());
        if (listaumow.size()==1) {
            selected.setUmowa(listaumow.get(0));
        }
        listarodzajwynagrodzenia = rodzajwynagrodzeniaFacade.findAktywne();

    }
    

    public void create() {
      if (selected!=null && selected.getUmowa()!=null) {
          try {
            if (selected.getId()!=null) {
                skladnikWynagrodzeniaFacade.edit(selected);
                selected = new Skladnikwynagrodzenia();
                Msg.msg("Udana edycja składnika wyn");
            } else {
                skladnikWynagrodzeniaFacade.create(selected);
                lista.add(selected);
                selected = new Skladnikwynagrodzenia();
                Msg.msg("Dodano nowy składnik wynagrodzenia");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego składnika wynagrodzenai");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunSkladnikWyn(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        if (skladnikwynagrodzenia!=null) {
            skladnikWynagrodzeniaFacade.remove(skladnikwynagrodzenia);
            lista.remove(skladnikwynagrodzenia);
            Msg.msg("Usunięto składnik wynagrodzeani");
        } else {
            Msg.msg("e","Nie wybrano składnika wynagrodzenia");
        }
    }
    
    public void wybierzdoedycji(Skladnikwynagrodzenia selected) {
      if (selected!=null) {
          try {
            this.selected  = selected;
            Msg.msg("Zmieniono składnik do edycji");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji składnika wyn");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
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

    public List<Umowa> getListaumow() {
        return listaumow;
    }

    public void setListaumow(List<Umowa> listaumow) {
        this.listaumow = listaumow;
    }

    public List<Rodzajwynagrodzenia> getListarodzajwynagrodzenia() {
        return listarodzajwynagrodzenia;
    }

    public void setListarodzajwynagrodzenia(List<Rodzajwynagrodzenia> listarodzajwynagrodzenia) {
        this.listarodzajwynagrodzenia = listarodzajwynagrodzenia;
    }

    

    
    
    
}
