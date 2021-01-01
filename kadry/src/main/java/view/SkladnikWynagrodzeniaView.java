/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.FirmaFacade;
import dao.PracownikFacade;
import entity.Angaz;
import entity.Firma;
import entity.Pracownik;
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
    private Angaz selected;
    @Inject
    private Angaz selectedlista;
    private List<Angaz> lista;
    private List<Firma> listafirm;
    private List<Pracownik> listapracownikow;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = angazFacade.findAll();
        listafirm = firmaFacade.findAll();
        listapracownikow = pracownikFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            angazFacade.create(selected);
            lista.add(selected);
            selected = new Angaz();
            Msg.msg("Dodano nową firmę");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            Msg.msg("Aktywowano firmę");
        }
    }
    
    public Angaz getSelected() {
        return selected;
    }

    public void setSelected(Angaz selected) {
        this.selected = selected;
    }

    public List<Angaz> getLista() {
        return lista;
    }

    public void setLista(List<Angaz> lista) {
        this.lista = lista;
    }

    public Angaz getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Angaz selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Firma> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<Firma> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

   
    
    
}
