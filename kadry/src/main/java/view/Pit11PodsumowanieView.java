/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaPIT11SchowekFacade;
import dao.FirmaKadryFacade;
import embeddable.Infopit11;
import entity.DeklaracjaPIT11Schowek;
import entity.FirmaKadry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Pit11PodsumowanieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DeklaracjaPIT11SchowekFacade deklaracjaSchowekFacade;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    @Inject
    private WpisView wpisView;
    private List<Infopit11> firmapitilosclist;
    private List<Infopit11> firmapitilosclistfilter;
    int angaze;
    int pity;
    List<FirmaKadry> listafirm;
    
    @PostConstruct
    private void init() {
        listafirm = firmaKadryFacade.findByBezglobal();
    }
    
    public void pobierz() {
        List<DeklaracjaPIT11Schowek> listapit = deklaracjaSchowekFacade.findByRok(wpisView.getRokWpisu());
        firmapitilosclist = new ArrayList<>();
        firmapitilosclistfilter = null;
        angaze = 0;
        pity = 0;
        int id = 1;
        for (FirmaKadry firma : listafirm) {
            Infopit11 pozycja = new Infopit11(id, firma);
            int angazesize = firma.getAngazListAktywne().size();
            pozycja.setIloscangazy(angazesize);
            angaze = angaze + angazesize;
            int zrobionepit = (int) listapit.stream().filter(p->p.getFirma().equals(firma)).count();
            pozycja.setIloscpit(zrobionepit);
            pity = pity + zrobionepit;
            firmapitilosclist.add(pozycja);
            id++;
        }
    }
    
     public int getPozostalo() {
            return this.angaze-this.pity;
        }

    public List<Infopit11> getFirmapitilosclist() {
        return firmapitilosclist;
    }

    public void setFirmapitilosclist(List<Infopit11> firmapitilosclist) {
        this.firmapitilosclist = firmapitilosclist;
    }

    public List<Infopit11> getFirmapitilosclistfilter() {
        return firmapitilosclistfilter;
    }

    public void setFirmapitilosclistfilter(List<Infopit11> firmapitilosclistfilter) {
        this.firmapitilosclistfilter = firmapitilosclistfilter;
    }

    public int getAngaze() {
        return angaze;
    }

    public void setAngaze(int angaze) {
        this.angaze = angaze;
    }

    public int getPity() {
        return pity;
    }

    public void setPity(int pity) {
        this.pity = pity;
    }
    
    

    
    
}
