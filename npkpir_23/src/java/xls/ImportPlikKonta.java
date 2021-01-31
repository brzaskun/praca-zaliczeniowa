/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dao.KontoDAOfk;
import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class ImportPlikKonta implements Serializable{
    private Konto wplyw;
    private Konto zaplata;
    private Konto prowizja;
    private Konto wyplatakarta;
    private Konto platnosckarta;
    private Konto przelewUS;
    private Konto przelewZUS;
    private Konto przelewGmina;
    private Konto przelewBankBank;
    private Konto konto213;
    private boolean sawszystkiekonta;
    
    public void pobierzkonta(KontoDAOfk kontoDAO, Podatnik podatnik, Integer rok) { //E.m(this);
        wplyw = kontoDAO.findKonto("213", podatnik, rok);
        zaplata = kontoDAO.findKonto("213", podatnik, rok);
        prowizja = kontoDAO.findKonto("404-3", podatnik, rok);
        wyplatakarta = kontoDAO.findKonto("149-1", podatnik, rok);
        platnosckarta = kontoDAO.findKonto("149-3", podatnik, rok);
        przelewUS = kontoDAO.findKonto("222", podatnik, rok);
        przelewZUS = kontoDAO.findKonto("231", podatnik, rok);
        przelewGmina = kontoDAO.findKonto("226", podatnik, rok);
        przelewBankBank = kontoDAO.findKonto("149-2", podatnik, rok);
        konto213 = kontoDAO.findKonto("213", podatnik, rok);
        if (wplyw!=null &&
                zaplata!=null &&
                prowizja!=null &&
                wyplatakarta!=null &&
                platnosckarta!=null &&
                przelewUS!=null &&
                przelewZUS!=null &&
                przelewGmina!=null &&
                przelewBankBank!=null &&
                konto213!=null) {
            sawszystkiekonta=true;
        }
    }

    public Konto getWplyw() {
        return wplyw;
    }

    public void setWplyw(Konto wplyw) {
        this.wplyw = wplyw;
    }

    public Konto getZaplata() {
        return zaplata;
    }

    public void setZaplata(Konto zaplata) {
        this.zaplata = zaplata;
    }

    public Konto getProwizja() {
        return prowizja;
    }

    public void setProwizja(Konto prowizja) {
        this.prowizja = prowizja;
    }

    public Konto getWyplatakarta() {
        return wyplatakarta;
    }

    public void setWyplatakarta(Konto wyplatakarta) {
        this.wyplatakarta = wyplatakarta;
    }

    public Konto getPlatnosckarta() {
        return platnosckarta;
    }

    public void setPlatnosckarta(Konto platnosckarta) {
        this.platnosckarta = platnosckarta;
    }

    public Konto getPrzelewUS() {
        return przelewUS;
    }

    public void setPrzelewUS(Konto przelewUS) {
        this.przelewUS = przelewUS;
    }

    public Konto getPrzelewZUS() {
        return przelewZUS;
    }

    public void setPrzelewZUS(Konto przelewZUS) {
        this.przelewZUS = przelewZUS;
    }

    public Konto getPrzelewGmina() {
        return przelewGmina;
    }

    public void setPrzelewGmina(Konto przelewGmina) {
        this.przelewGmina = przelewGmina;
    }

    public Konto getPrzelewBankBank() {
        return przelewBankBank;
    }

    public void setPrzelewBankBank(Konto przelewBankBank) {
        this.przelewBankBank = przelewBankBank;
    }

    public Konto getKonto213() {
        return konto213;
    }

    public void setKonto213(Konto konto213) {
        this.konto213 = konto213;
    }

    public boolean isSawszystkiekonta() {
        return sawszystkiekonta;
    }

    public void setSawszystkiekonta(boolean sawszystkiekonta) {
        this.sawszystkiekonta = sawszystkiekonta;
    }
    
    
}
