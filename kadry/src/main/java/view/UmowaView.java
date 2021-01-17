/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KodzawoduBean;
import dao.AngazFacade;
import dao.EtatFacade;
import dao.KodyzawodowFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import entity.Angaz;
import entity.Etat;
import entity.Kodyzawodow;
import entity.Umowa;
import entity.Umowakodzus;
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
public class UmowaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Umowa selected;
    @Inject
    private Umowa selectedlista;
    @Inject
    private Etat etat;
    private List<Umowa> lista;
    private List<Angaz> listaangaz;
    private List<Umowakodzus> listaumowakodzus;
    private List<Kodyzawodow> listakodyzawodow;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    @Inject
    private EtatFacade etatFacade;
    @Inject
    private UmowakodzusFacade rodzajumowyFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    
    @PostConstruct
    public void init() {
        lista  = umowaFacade.findByAngaz(wpisView.getAngaz());
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findAll();
        listakodyzawodow = kodyzawodowFacade.findAll();
       
    }
 
    public void create() {
      if (selected!=null && wpisView.getAngaz()!=null) {
          try {
            selected.setAngaz(wpisView.getAngaz());
            umowaFacade.create(selected);
            lista.add(selected);
            Etat etat = new Etat(selected);
            etatFacade.create(etat);
            wpisView.setUmowa(selected);
            selected = new Umowa();
            Msg.msg("Dodano nową umowę");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    public void edit() {
      if (selected!=null && selected.getId()!=null) {
          try {
            umowaFacade.edit(selected);
            wpisView.setUmowa(selected);
            selected = new Umowa();
            Msg.msg("Edycja umowy zakończona");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    public void aktywuj() {
        if (selectedlista!=null) {
            wpisView.setUmowa(selectedlista);
            wpisView.setFirma(selectedlista.getAngaz().getFirma());
            wpisView.setPracownik(selectedlista.getAngaz().getPracownik());
            Msg.msg("Aktywowano umowę");
        }
    }
    
    public void ustawumowe() {
        if (selected.getUmowakodzus() != null) {
            if (selected.getUmowakodzus().getKod().equals("0110")) {
                selected.setNrkolejny("UP/"+wpisView.getPracownik().getPesel()+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setCzastrwania("umowa na okres próbny");
                selected.setDataod("2020-01-01");
                selected.setDatanfz("2020-01-01");
                selected.setDataspoleczne("2020-01-01");
                selected.setDatazawarcia("2020-01-01");
                selected.setDatazdrowotne("2020-01-01");
                selected.setKosztyuzyskania(250.0);
                selected.setNfz("13");
                selected.setKodzawodu(KodzawoduBean.create());
                selected.setOdliczaculgepodatkowa(true);
            } else if (selected.getUmowakodzus().getKod().equals("0410")) {
                selected.setNrkolejny("UZ/"+wpisView.getPracownik().getPesel()+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setDataod("2020-04-01");
                selected.setDatanfz("2020-04-01");
                selected.setDataspoleczne("2020-04-01");
                selected.setDatazawarcia("2020-04-01");
                selected.setDatazdrowotne("2020-04-01");
                selected.setKosztyuzyskania(0.0);
                selected.setNfz("13");
                selected.setKodzawodu(KodzawoduBean.create());
        }
        }
    }
    
    public void usun(Umowa umowa) {
        if (umowa!=null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            lista.remove(umowa);
            Msg.msg("Usunięto umowę");
        } else {
            Msg.msg("e","Nie wybrano umowy");
        }
    }
    
    public void edytuj(Umowa umowa) {
        if (umowa!=null) {
            selected = umowa;
            Msg.msg("Wybrano umowę do edycji");
        } else {
            Msg.msg("e","Nie wybrano umowy");
        }
    }
    
    public void dodatetat() {
        if (etat!=null&&etat.getDataod()!=null) {
            etatFacade.create(etat);
            selected.getEtatList().add(etat);
            umowaFacade.edit(selected);
        }
    }
    
    public Umowa getSelected() {
        return selected;
    }

    public void setSelected(Umowa selected) {
        this.selected = selected;
    }

    public List<Umowa> getLista() {
        return lista;
    }

    public void setLista(List<Umowa> lista) {
        this.lista = lista;
    }

    public Umowa getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Umowa selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Angaz> getListaangaz() {
        return listaangaz;
    }

    public void setListaangaz(List<Angaz> listaangaz) {
        this.listaangaz = listaangaz;
    }

    public List<Umowakodzus> getListaumowakodzus() {
        return listaumowakodzus;
    }

    public void setListaumowakodzus(List<Umowakodzus> listaumowakodzus) {
        this.listaumowakodzus = listaumowakodzus;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<Kodyzawodow> getListakodyzawodow() {
        return listakodyzawodow;
    }

    public void setListakodyzawodow(List<Kodyzawodow> listakodyzawodow) {
        this.listakodyzawodow = listakodyzawodow;
    }


   
      
    
    
}
