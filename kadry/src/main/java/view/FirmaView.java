/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaFacade;
import entity.Firma;
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
public class FirmaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Firma selected;
    @Inject
    private Firma selectedlista;
    private List<Firma> lista;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = firmaFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            firmaFacade.create(selected);
            lista.add(selected);
            selected = new Firma();
            Msg.msg("Dodano nową firmę");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void aktywuj(Firma firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            Msg.msg("Aktywowano firmę");
        }
    }
    
    public Firma getSelected() {
        return selected;
    }

    public void setSelected(Firma selected) {
        this.selected = selected;
    }

    public List<Firma> getLista() {
        return lista;
    }

    public void setLista(List<Firma> lista) {
        this.lista = lista;
    }

    public Firma getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Firma selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
    
}
