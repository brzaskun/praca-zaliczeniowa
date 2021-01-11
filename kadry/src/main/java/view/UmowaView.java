/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.RodzajumowyFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.Rodzajumowy;
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
public class UmowaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Umowa selected;
    @Inject
    private Umowa selectedlista;
    private List<Umowa> lista;
    private List<Angaz> listaangaz;
    private List<Rodzajumowy> listarodzajumowy;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private RodzajumowyFacade rodzajumowyFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    
    @PostConstruct
    private void init() {
        lista  = umowaFacade.findByAngaz(wpisView.getAngaz());
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listarodzajumowy = rodzajumowyFacade.findAll();
    }
 
    public void create() {
      if (selected!=null) {
          try {
            umowaFacade.create(selected);
            lista.add(selected);
            selected = new Umowa();
            wpisView.setUmowa(selected);
            Msg.msg("Dodano nową umowę");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej umowy");
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
        if (selected.getRodzajumowy() != null) {
            if (selected.getRodzajumowy().getNazwa().equals("umowa o pracę")) {
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setDataod("2020-01-01");
                selected.setDatanfz("2020-01-01");
                selected.setDataspoleczne("2020-01-01");
                selected.setDatazawarcia("2020-01-01");
                selected.setDatazdrowotne("2020-01-01");
                selected.setKosztyuzyskania(250.0);
                selected.setNfz("13");
                selected.setKodubezpieczenia("0110");
                selected.setOdliczaculgepodatkowa(true);
            } else if (selected.getRodzajumowy().getNazwa().equals("umowa zlecenia")) {
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
                selected.setKodubezpieczenia("0411");
        }
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

    public List<Rodzajumowy> getListarodzajumowy() {
        return listarodzajumowy;
    }

    public void setListarodzajumowy(List<Rodzajumowy> listarodzajumowy) {
        this.listarodzajumowy = listarodzajumowy;
    }

      
    
    
}
