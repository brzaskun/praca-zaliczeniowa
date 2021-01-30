/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaFacade;
import dao.UprawnieniaFacade;
import dao.UzFacade;
import entity.Firma;
import entity.Uprawnienia;
import entity.Uz;
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
    private Firma selectedlista;
    private Firma selectedeast;
    private List<Firma> lista;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private UprawnieniaFacade uprawnieniaFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private WpisView wpisView;
    private PracownikView pracownikView;
    
    @PostConstruct
    private void init() {
        lista  = firmaFacade.findAll();
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
    }

    public void create() {
      if (selected!=null) {
          try {
            firmaFacade.create(selected);
            lista.add(selected);
            wpisView.setFirma(selected);
            Msg.msg("Dodano nową firmę");
            Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
            Uz uzer = new Uz(selected, uprawnienia);
            selected = new Firma();
            Msg.msg("Dodano nowy angaż");
            uzFacade.create(uzer);
            Msg.msg("Dodano nowego użytkownika");
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
    
    public void edytuj(Firma firma) {
        if (firma!=null && firma.getEmail()!=null) {
            firmaFacade.edit(firma);
            Uz uz = uzFacade.findUzByPesel(firma.getNip());
            if (uz!=null) {
                uz.setEmail(firma.getEmail());
                uz.setNrtelefonu(firma.getTelefon());
                uzFacade.edit(uz);
            } else {
                Uprawnienia uprawnienia = uprawnieniaFacade.findByNazwa("Pracodawca");
                Uz uzer = new Uz(firma, uprawnienia);
                uzFacade.create(uzer);
            }
            Msg.msg("Edytowano firmę");
        } else {
            Msg.msg("e","Nie wybrano firmy");
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
