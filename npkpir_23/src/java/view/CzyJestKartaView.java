/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import error.E;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class CzyJestKartaView   implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean moznapodpisywac;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String innehaslo;
    private String innypesel;
    private String czyjestczytnik;
    private String czyjestkarta;
    private String czyjestcertyfikat;
    private String czydobrehaslo;
    private String certyfikatdo;
    private String nazwacertyfikatu;
    
    
    public void init() { //E.m(this);
         moznapodpisywac = ObslugaPodpisuBean.moznaPodpisacMute(innehaslo, innypesel);
         czyjestczytnik = ObslugaPodpisuBean.getOdpowiedz().get(0);
         czyjestkarta = ObslugaPodpisuBean.getOdpowiedz().get(1);
         czyjestcertyfikat =  ObslugaPodpisuBean.getOdpowiedz().get(2);
         czydobrehaslo = ObslugaPodpisuBean.getOdpowiedz().get(3);
         certyfikatdo = ObslugaPodpisuBean.getOdpowiedz().get(4);
         nazwacertyfikatu = ObslugaPodpisuBean.getOdpowiedz().get(5);
    }

    public boolean isMoznapodpisywac() {
        return moznapodpisywac;
    }

    public void setMoznapodpisywac(boolean moznapodpisywac) {
        this.moznapodpisywac = moznapodpisywac;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public String getInnehaslo() {
        return innehaslo;
    }

    public void setInnehaslo(String haslo) {
        this.innehaslo = haslo;
    }

    public String getInnypesel() {
        return innypesel;
    }

    public void setInnypesel(String innypesel) {
        this.innypesel = innypesel;
    }

    public String getCzyjestczytnik() {
        return czyjestczytnik;
    }

    public void setCzyjestczytnik(String czyjestczytnik) {
        this.czyjestczytnik = czyjestczytnik;
    }

    public String getCzyjestkarta() {
        return czyjestkarta;
    }

    public void setCzyjestkarta(String czyjestkarta) {
        this.czyjestkarta = czyjestkarta;
    }

    public String getCzyjestcertyfikat() {
        return czyjestcertyfikat;
    }

    public void setCzyjestcertyfikat(String czyjestcertyfikat) {
        this.czyjestcertyfikat = czyjestcertyfikat;
    }

    public String getCzydobrehaslo() {
        return czydobrehaslo;
    }

    public void setCzydobrehaslo(String czydobrehaslo) {
        this.czydobrehaslo = czydobrehaslo;
    }

    public String getCertyfikatdo() {
        return certyfikatdo;
    }

    public void setCertyfikatdo(String certyfikatdo) {
        this.certyfikatdo = certyfikatdo;
    }

    public String getNazwacertyfikatu() {
        return nazwacertyfikatu;
    }

    public void setNazwacertyfikatu(String nazwacertyfikatu) {
        this.nazwacertyfikatu = nazwacertyfikatu;
    }
    
    
}
