/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.WynPotraceniaFacade;
import dao.RodzajpotraceniaFacade;
import dao.SkladnikPotraceniaFacade;
import dao.UmowaFacade;
import entity.Rodzajpotracenia;
import entity.Skladnikpotracenia;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.WynPotracenia;
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
    private List<Rodzajpotracenia> listapotracen;
    private List<Umowa> listaumow;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private RodzajpotraceniaFacade rodzajpotraceniaFacade;
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
        listapotracen = rodzajpotraceniaFacade.findAll();

    }
    

    public void create() {
      if (selected!=null && selected.getAngaz()!=null) {
          try {
            skladnikPotraceniaFacade.create(selected);
            lista.add(selected);
            selected = new Skladnikpotracenia();
            Msg.msg("Dodano nowy składnik potrąceń");
          } catch (Exception e) {
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


    public List<Rodzajpotracenia> getListapotracen() {
        return listapotracen;
    }

    public void setListapotracen(List<Rodzajpotracenia> listapotracen) {
        this.listapotracen = listapotracen;
    }

    

     @Inject
    private WynPotraceniaFacade wynPotraceniaFacade;
    public void generujtabele() {
        Msg.msg("Start");
        List<WynPotracenia> findAll = wynPotraceniaFacade.findAll();
        for (WynPotracenia p : findAll) {
            Rodzajpotracenia s  = new Rodzajpotracenia();
            s.setOpis(p.getWpoOpis());
            s.setWpo_serial(p.getWpoSerial());
            s.setPod_doch(p.getWpoPodDoch().equals('T'));
            s.setZus(p.getWpoZus().equals('T'));
            s.setZdrowotne(p.getWpoZdrowotne().equals('T'));
            s.setNumer(p.getWpoNumer());
            rodzajpotraceniaFacade.create(s);
        }
        Msg.dP();
    }
    
    
}
