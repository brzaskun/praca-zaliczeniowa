/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanStatystyka;

import entity.Podatnik;
import java.util.Objects;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class Statystyka {
    private int lp;
    private Podatnik podatnik;
    private String rok;
    private int iloscdokumentow;
    private double obroty;
    private int iloscfaktur;
    private double kwotafaktur;
    private double fakturaNaObroty;
    private double fakturaNaDokumenty;
    private double ranking;

    public Statystyka() {
    }

    public Statystyka(int lp, Podatnik podatnik, String rok, int iloscdokumentow, double obroty, int iloscfaktur, double kwotafaktur) {
        this.lp = lp;
        this.podatnik = podatnik;
        this.rok = rok;
        this.iloscdokumentow = iloscdokumentow;
        this.obroty = obroty;
        this.iloscfaktur = iloscfaktur;
        this.kwotafaktur = kwotafaktur;
        this.fakturaNaObroty = Z.z4(this.kwotafaktur/this.obroty)*100 > 12 ? 12 : Z.z4(this.kwotafaktur/this.obroty)*100;
        this.fakturaNaDokumenty = Z.z4(this.kwotafaktur/this.iloscdokumentow)/10 > 12 ? 12 : Z.z4(this.kwotafaktur/this.iloscdokumentow)/10;
        this.ranking = this.fakturaNaDokumenty+this.fakturaNaObroty;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public int getIloscdokumentow() {
        return iloscdokumentow;
    }

    public void setIloscdokumentow(int iloscdokumentow) {
        this.iloscdokumentow = iloscdokumentow;
    }

    public double getObroty() {
        return obroty;
    }

    public void setObroty(double obroty) {
        this.obroty = obroty;
    }

    public int getIloscfaktur() {
        return iloscfaktur;
    }

    public void setIloscfaktur(int iloscfaktur) {
        this.iloscfaktur = iloscfaktur;
    }

    public double getKwotafaktur() {
        return kwotafaktur;
    }

    public void setKwotafaktur(double kwotafaktur) {
        this.kwotafaktur = kwotafaktur;
    }

    public double getFakturaNaObroty() {
        return fakturaNaObroty;
    }

    public void setFakturaNaObroty(double fakturaNaObroty) {
        this.fakturaNaObroty = fakturaNaObroty;
    }

    public double getFakturaNaDokumenty() {
        return fakturaNaDokumenty;
    }

    public void setFakturaNaDokumenty(double fakturaNaDokumenty) {
        this.fakturaNaDokumenty = fakturaNaDokumenty;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.podatnik);
        hash = 29 * hash + Objects.hashCode(this.rok);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Statystyka other = (Statystyka) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StatystykaBean{" + "podatnik=" + podatnik + ", rok=" + rok + ", iloscdokumentow=" + iloscdokumentow + ", obroty=" + obroty + ", iloscfaktur=" + iloscfaktur + ", kwotafaktur=" + kwotafaktur + '}';
    }
    
    
    
}
