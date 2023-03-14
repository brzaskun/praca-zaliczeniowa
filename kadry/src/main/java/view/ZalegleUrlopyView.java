/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.PracownikFacade;
import entity.Angaz;
import entity.Pracownik;
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
public class ZalegleUrlopyView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Angaz> lista;
    private Angaz selectedlista;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WpisView wpisView;
      
    
    @PostConstruct
    public void init() {
        if (wpisView.getFirma()!=null) {
            lista = angazFacade.findByFirma(wpisView.getFirma());
        }
    }
    
    public void edytuj(Angaz angaz) {
         if (angaz!=null) {
            angazFacade.edit(angaz);
            Msg.msg("Zapisano zmiany");
        } else {
            Msg.msg("e", "Błąd nie wybrano angażu");
        }
     }
    
    public void zapisz(Pracownik pracownik) {
        if (pracownik!=null) {
            pracownikFacade.edit(pracownik);
            Msg.msg("Zaktualizowano danepracownika");
        }
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
    
    
    
}
