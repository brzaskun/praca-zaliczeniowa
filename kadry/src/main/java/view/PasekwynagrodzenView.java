/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beans.PasekwynagrodzenBean;
import dao.DefinicjalistaplacFacade;
import dao.KalendarzmiesiacFacade;
import dao.PasekwynagrodzenFacade;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfListaPlac;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private Definicjalistaplac wybranalistaplac;
    private Kalendarzmiesiac wybranypracownik;
    private List<Pasekwynagrodzen> lista;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    private List<Kalendarzmiesiac> listakalendarzmiesiac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = pasekwynagrodzenFacade.findAll();
        listadefinicjalistaplac = definicjalistaplacFacade.findAll();
        listakalendarzmiesiac = kalendarzmiesiacFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            PasekwynagrodzenBean.usunpasekjeslijest(selected, pasekwynagrodzenFacade);
            kalendarzmiesiacFacade.edit(selected.getKalendarzmiesiac());
            pasekwynagrodzenFacade.create(selected);
            lista.add(selected);
            selected = new Pasekwynagrodzen();
            Msg.msg("Dodano pasek wynagrodzen");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano paska wynagrodzen");
          }
      }
    }
    
    public void przelicz() {
        if (wybranypracownik!=null && wybranalistaplac!=null) {
            selected.setDefinicjalistaplac(wybranalistaplac);
            selected.setKalendarzmiesiac(wybranypracownik);
            Pasekwynagrodzen pasek = PasekwynagrodzenBean.oblicz(selected, wybranypracownik, wybranalistaplac);
            pasek.setId(1);
            lista.add(pasek);
            Msg.msg("Sporządzono listę dla pracownika");
        } else {
            Msg.msg("e","Nie wybrano listy lub pracownika");
        }
    }
    
    public void drukuj(Pasekwynagrodzen p ) {
        if (p!=null) {
            PdfListaPlac.drukuj(p);
            Msg.msg("Wydrukowano listę płac");
        } else {
            Msg.msg("e","Błąd drukowania. Pasek null");
        }
    }
    
    public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            Msg.msg("Aktywowano firmę");
        }
    }
    
    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getLista() {
        return lista;
    }

    public void setLista(List<Pasekwynagrodzen> lista) {
        this.lista = lista;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplac() {
        return listadefinicjalistaplac;
    }

    public void setListadefinicjalistaplac(List<Definicjalistaplac> listadefinicjalistaplac) {
        this.listadefinicjalistaplac = listadefinicjalistaplac;
    }

    public Pasekwynagrodzen getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pasekwynagrodzen selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Kalendarzmiesiac> getListakalendarzmiesiac() {
        return listakalendarzmiesiac;
    }

    public void setListakalendarzmiesiac(List<Kalendarzmiesiac> listakalendarzmiesiac) {
        this.listakalendarzmiesiac = listakalendarzmiesiac;
    }

    public Definicjalistaplac getWybranalistaplac() {
        return wybranalistaplac;
    }

    public void setWybranalistaplac(Definicjalistaplac wybranalistaplac) {
        this.wybranalistaplac = wybranalistaplac;
    }

    public Kalendarzmiesiac getWybranypracownik() {
        return wybranypracownik;
    }

    public void setWybranypracownik(Kalendarzmiesiac wybranypracownik) {
        this.wybranypracownik = wybranypracownik;
    }

 
    
}
