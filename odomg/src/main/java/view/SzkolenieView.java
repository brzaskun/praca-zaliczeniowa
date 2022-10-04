/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenieFacade;
import dao.SzkolenieWykazFacade;
import dao.UczestnicyFacade;
import entity.Szkolenie;
import entity.Szkoleniewykaz;
import entity.Uczestnicy;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.commandbutton.CommandButton;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SzkolenieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private UczestnicyFacade uczestnicyFacade;
    @Inject
    private SzkolenieWykazFacade szkolenieWykazFacade;
    @Inject
    private SzkolenieFacade szkolenieFacade;
    private List<Uczestnicy> uczestnikszkolenie;
    private Szkoleniewykaz szkoleniewykaz;
    private List<Szkolenie> slajdyszkolenie;
    private String naglowekslajdu;
    private String trescslajdu;
    private int nrbiezacyslajdu;
    private CommandButton buttonzakoncz;
    private CommandButton buttonprzod;
    
    
    @PostConstruct
    private void init() {
        uczestnikszkolenie = uczestnicyFacade.findByEmail(wpisView.getUzer());
        String nazwaszkolenia = uczestnikszkolenie.get(0).getNazwaszkolenia();
        szkoleniewykaz = szkolenieWykazFacade.findBynazwa(nazwaszkolenia);
        slajdyszkolenie = szkolenieFacade.findBynazwa(nazwaszkolenia);
        nrbiezacyslajdu = 0;
        pobierzslajd();
        System.out.println("");
    }
    
    private void pobierzslajd() {
        naglowekslajdu = slajdyszkolenie.get(nrbiezacyslajdu).getNaglowek();
        trescslajdu = slajdyszkolenie.get(nrbiezacyslajdu).getTresc();
    }
    
    public void slajddoprzodu() {
        nrbiezacyslajdu = nrbiezacyslajdu+1;
        if (nrbiezacyslajdu>slajdyszkolenie.size()-1) {
            nrbiezacyslajdu = slajdyszkolenie.size()-1;
            buttonzakoncz.setRendered(true);
            buttonprzod.setRendered(false);
        } else {
            buttonzakoncz.setRendered(false);
            buttonprzod.setRendered(true);
        }
        pobierzslajd();
    }
    
    public void slajddotylu() {
        nrbiezacyslajdu = nrbiezacyslajdu-1;
        buttonzakoncz.setRendered(false);
        buttonprzod.setRendered(true);
        if (nrbiezacyslajdu<0) {
            nrbiezacyslajdu = 0;
        }
        pobierzslajd();
    }
    
    public void uruchomtest() {
        System.out.println("test");
    }

    public List<Uczestnicy> getUczestnikszkolenie() {
        return uczestnikszkolenie;
    }

    public void setUczestnikszkolenie(List<Uczestnicy> uczestnikszkolenie) {
        this.uczestnikszkolenie = uczestnikszkolenie;
    }

    public Szkoleniewykaz getSzkoleniewykaz() {
        return szkoleniewykaz;
    }

    public void setSzkoleniewykaz(Szkoleniewykaz szkoleniewykaz) {
        this.szkoleniewykaz = szkoleniewykaz;
    }

    public List<Szkolenie> getSlajdyszkolenie() {
        return slajdyszkolenie;
    }

    public void setSlajdyszkolenie(List<Szkolenie> slajdyszkolenie) {
        this.slajdyszkolenie = slajdyszkolenie;
    }

    public String getNaglowekslajdu() {
        return naglowekslajdu;
    }

    public void setNaglowekslajdu(String naglowekslajdu) {
        this.naglowekslajdu = naglowekslajdu;
    }

    public String getTrescslajdu() {
        return trescslajdu;
    }

    public void setTrescslajdu(String trescslajdu) {
        this.trescslajdu = trescslajdu;
    }

    public CommandButton getButtonzakoncz() {
        return buttonzakoncz;
    }

    public void setButtonzakoncz(CommandButton buttonzakoncz) {
        this.buttonzakoncz = buttonzakoncz;
    }

    public CommandButton getButtonprzod() {
        return buttonprzod;
    }

    public void setButtonprzod(CommandButton buttonprzod) {
        this.buttonprzod = buttonprzod;
    }
    
    
    
}
