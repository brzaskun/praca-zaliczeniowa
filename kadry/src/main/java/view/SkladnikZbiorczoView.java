/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import embeddable.ZmiennaZbiorcze;
import entity.Angaz;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import error.E;
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
public class SkladnikZbiorczoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    private List<Rodzajwynagrodzenia> rodzajewynagrodzen;
    private Rodzajwynagrodzenia wybranyrodzaj;
    private String dataod;
    private String datado;
    private ZmiennaZbiorcze selectedlista;
    private List<Angaz> angazList;
    private List<Angaz> angazListselected;
    private boolean oddelegowanie;
    
    @PostConstruct
    public void init() {
        angazList = wpisView.getFirma().getAngazListAktywne();
        rodzajewynagrodzen = rodzajwynagrodzeniaFacade.findAll();
    }
    
      
     
    
     public void tworzzmienne() {
         if (wybranyrodzaj!=null&&angazListselected!=null) {
             for (Angaz p : angazListselected) {
                 Skladnikwynagrodzenia skladnik = new Skladnikwynagrodzenia();
                 skladnik.setRodzajwynagrodzenia(wybranyrodzaj);
                 skladnik.setAngaz(p);
                 skladnik.setWks_serial(wybranyrodzaj.getWks_serial());
                 if (oddelegowanie) {
                     skladnik.setOddelegowanie(oddelegowanie);
                 }
                 try {
                    skladnikWynagrodzeniaFacade.create(skladnik);
                 } catch (Exception e){
                     E.e(e);
                 }
             }
             Msg.msg("naniesiono składnik wynagrodzeń  dla wybranych pracowników");
         } else {
             Msg.msg("e","Nie wybrano pracowników/składnika");
         }
     }
   
    public Rodzajwynagrodzenia getWybranyrodzaj() {
        return wybranyrodzaj;
    }

    public void setWybranyrodzaj(Rodzajwynagrodzenia wybranyrodzaj) {
        this.wybranyrodzaj = wybranyrodzaj;
    }

    public List<Rodzajwynagrodzenia> getRodzajewynagrodzen() {
        return rodzajewynagrodzen;
    }

    public void setRodzajewynagrodzen(List<Rodzajwynagrodzenia> rodzajewynagrodzen) {
        this.rodzajewynagrodzen = rodzajewynagrodzen;
    }

    public ZmiennaZbiorcze getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(ZmiennaZbiorcze selectedlista) {
        this.selectedlista = selectedlista;
    }

    public boolean isOddelegowanie() {
        return oddelegowanie;
    }

    public void setOddelegowanie(boolean oddelegowanie) {
        this.oddelegowanie = oddelegowanie;
    }

    public List<Angaz> getAngazListselected() {
        return angazListselected;
    }

    public void setAngazListselected(List<Angaz> angazListselected) {
        this.angazListselected = angazListselected;
    }

    public List<Angaz> getAngazList() {
        return angazList;
    }

    public void setAngazList(List<Angaz> angazList) {
        this.angazList = angazList;
    }

    
    
    
}
