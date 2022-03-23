/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenieFacade;
import dao.SzkolenieWykazFacade;
import entity.Szkolenie;
import entity.Szkoleniewykaz;
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
public class SzkolenieEditView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private SzkolenieFacade szkolenieFacade;
    @Inject
    private SzkolenieWykazFacade szkolenieWykazFacade;
    @Inject
    private Szkolenie szkolenie;
    private List<Szkolenie> szkolenielista;
    private List<Szkolenie> szkolenielistaselected;
    private Szkoleniewykaz szkoleniewykaz;
    private List<Szkoleniewykaz> szkoleniewykazlista;
    

    @PostConstruct
    private void init() {
        szkoleniewykazlista = szkolenieWykazFacade.findAll();
    }
    
     public void dodaj() {
        if (szkolenie!=null&&szkolenie.getTresc()!=null) {
            try {
                szkolenieFacade.create(szkolenie);
                szkolenie=new Szkolenie();
                Msg.msg("Dodano nowy slajd");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd, nie dodano nowego slajdu");
            }
        }
     }
    
     public void pobierzslajdy() {
        if (szkoleniewykaz!=null) {
            szkolenielista  = szkolenieFacade.findBynazwa(szkoleniewykaz.getNazwa());
            System.out.println("");
        }
     }
     
    public Szkolenie getSzkolenie() {
        return szkolenie;
    }

    public void setSzkolenie(Szkolenie szkolenie) {
        this.szkolenie = szkolenie;
    }

    public List<Szkoleniewykaz> getSzkoleniewykazlista() {
        return szkoleniewykazlista;
    }

    public void setSzkoleniewykazlista(List<Szkoleniewykaz> szkoleniewykazlista) {
        this.szkoleniewykazlista = szkoleniewykazlista;
    }

    public Szkoleniewykaz getSzkoleniewykaz() {
        return szkoleniewykaz;
    }

    public void setSzkoleniewykaz(Szkoleniewykaz szkoleniewykaz) {
        this.szkoleniewykaz = szkoleniewykaz;
    }

    public List<Szkolenie> getSzkolenielista() {
        return szkolenielista;
    }

    public void setSzkolenielista(List<Szkolenie> szkolenielista) {
        this.szkolenielista = szkolenielista;
    }

    public List<Szkolenie> getSzkolenielistaselected() {
        return szkolenielistaselected;
    }

    public void setSzkolenielistaselected(List<Szkolenie> szkolenielistaselected) {
        this.szkolenielistaselected = szkolenielistaselected;
    }

   
    
    
    
    
}
