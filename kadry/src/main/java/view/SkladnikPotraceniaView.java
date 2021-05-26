/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikPotraceniaFacade;
import dao.SlownikpotraceniaFacade;
import dao.UmowaFacade;
import entity.Skladnikpotracenia;
import entity.Slownikpotracenia;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class SkladnikPotraceniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Skladnikpotracenia selected;
    @Inject
    private Skladnikpotracenia selectedlista;
    private List<Skladnikpotracenia> lista;
    private List<Slownikpotracenia> listapotracen;
    private List<Umowa> listaumow;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private SlownikpotraceniaFacade slownikpotraceniaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaPotraceniaView zmiennaPotraceniaView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz()!=null) {
            lista  = skladnikPotraceniaFacade.findByPracownik(wpisView.getAngaz().getPracownik());
        }
        listaumow = umowaFacade.findPracownik(wpisView.getPracownik());
        listapotracen = slownikpotraceniaFacade.findAll();

    }
    

    public void create() {
      if (selected!=null && selected.getUmowa()!=null) {
          try {
            skladnikPotraceniaFacade.create(selected);
            lista.add(selected);
            selected = new Skladnikpotracenia();
            Msg.msg("Dodano nowy składnik potrąceń");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego składnika potrąceń");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunSkladnikPotr(Skladnikpotracenia skladnikwynagrodzenia) {
        if (skladnikwynagrodzenia!=null) {
            skladnikPotraceniaFacade.remove(skladnikwynagrodzenia);
            lista.remove(skladnikwynagrodzenia);
            Msg.msg("Usunięto składnik potrąceń");
        } else {
            Msg.msg("e","Nie wybrano składnika potrąceń");
        }
    }
    public Skladnikpotracenia getSelected() {
        return selected;
    }

    public void setSelected(Skladnikpotracenia selected) {
        this.selected = selected;
    }

    public Skladnikpotracenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Skladnikpotracenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Skladnikpotracenia> getLista() {
        return lista;
    }

    public void setLista(List<Skladnikpotracenia> lista) {
        this.lista = lista;
    }

    public List<Umowa> getListaumow() {
        return listaumow;
    }

    public void setListaumow(List<Umowa> listaumow) {
        this.listaumow = listaumow;
    }


    public List<Slownikpotracenia> getListapotracen() {
        return listapotracen;
    }

    public void setListapotracen(List<Slownikpotracenia> listapotracen) {
        this.listapotracen = listapotracen;
    }

    

    
    
    
}
