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
public class FirmaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Firma selected;
    private Firma selectedlista;
    private Firma selectedeast;
    private List<Firma> lista;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    private PracownikView pracownikView;
    
    @PostConstruct
    private void init() {
        lista  = firmaFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            firmaFacade.create(selected);
            lista.add(selected);
            wpisView.setFirma(selected);
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
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            }
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    public void usun(Firma firma) {
        if (firma!=null) {
            if (wpisView.getFirma()!=null && wpisView.getFirma().equals(firma)) {
                wpisView.setFirma(null);
            }
            firmaFacade.remove(firma);
            lista.remove(firma);
            Msg.msg("Usunięto firmę");
        } else {
            Msg.msg("e","Nie usunięto firmy");
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

    public Firma getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Firma selectedeast) {
        this.selectedeast = selectedeast;
    }
    
    
    
}
