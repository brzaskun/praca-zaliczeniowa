/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class SrodekTrw implements Serializable{
    private Integer nrkolejnywpisu;
    private String nazwaSrodka;
    private String nrKlasyfikacji;
    private String datazakupu;
    private String dataprzekazania;
    private double wartoscPoczatkowa;
    private double kwotaVat;
    private double stawkaAmortyzacji;
    private double rocznaKwotaOdpisu;
    private double miesiecznaKwotaOdpisu;
    private double dataWyksiegowania;
    private List<Umorzenie> planowaneUmorzenia;
    private List<Umorzenie> naliczoneUmorzenia;

    public SrodekTrw() {
    }

    public SrodekTrw(String nazwaSrodka, double wartoscPoczatkowa) {
        this.nazwaSrodka = nazwaSrodka;
        this.wartoscPoczatkowa = wartoscPoczatkowa;
    }
    
    
    public Integer getNrkolejnywpisu() {
        return nrkolejnywpisu;
    }

    public void setNrkolejnywpisu(Integer nrkolejnywpisu) {
        this.nrkolejnywpisu = nrkolejnywpisu;
    }

    public String getNazwaSrodka() {
        return nazwaSrodka;
    }

    public void setNazwaSrodka(String nazwaSrodka) {
        this.nazwaSrodka = nazwaSrodka;
    }

    public String getNrKlasyfikacji() {
        return nrKlasyfikacji;
    }

    public void setNrKlasyfikacji(String nrKlasyfikacji) {
        this.nrKlasyfikacji = nrKlasyfikacji;
    }

    public String getDatazakupu() {
        return datazakupu;
    }

    public void setDatazakupu(String datazakupu) {
        this.datazakupu = datazakupu;
    }

    public String getDataprzekazania() {
        return dataprzekazania;
    }

    public void setDataprzekazania(String dataprzekazania) {
        this.dataprzekazania = dataprzekazania;
    }

    public double getWartoscPoczatkowa() {
        return wartoscPoczatkowa;
    }

    public void setWartoscPoczatkowa(double wartoscPoczatkowa) {
        this.wartoscPoczatkowa = wartoscPoczatkowa;
    }

    public double getKwotaVat() {
        return kwotaVat;
    }

    public void setKwotaVat(double kwotaVat) {
        this.kwotaVat = kwotaVat;
    }

    public double getStawkaAmortyzacji() {
        return stawkaAmortyzacji;
    }

    public void setStawkaAmortyzacji(double stawkaAmortyzacji) {
        this.stawkaAmortyzacji = stawkaAmortyzacji;
    }

    public double getRocznaKwotaOdpisu() {
        return rocznaKwotaOdpisu;
    }

    public void setRocznaKwotaOdpisu(double rocznaKwotaOdpisu) {
        this.rocznaKwotaOdpisu = rocznaKwotaOdpisu;
    }

    public double getMiesiecznaKwotaOdpisu() {
        return miesiecznaKwotaOdpisu;
    }

    public void setMiesiecznaKwotaOdpisu(double miesiecznaKwotaOdpisu) {
        this.miesiecznaKwotaOdpisu = miesiecznaKwotaOdpisu;
    }

    public double getDataWyksiegowania() {
        return dataWyksiegowania;
    }

    public void setDataWyksiegowania(double dataWyksiegowania) {
        this.dataWyksiegowania = dataWyksiegowania;
    }

    public List<Umorzenie> getPlanowaneUmorzenia() {
        return planowaneUmorzenia;
    }

    public void setPlanowaneUmorzenia(List<Umorzenie> planowaneUmorzenia) {
        this.planowaneUmorzenia = planowaneUmorzenia;
    }

    public List<Umorzenie> getNaliczoneUmorzenia() {
        return naliczoneUmorzenia;
    }

    public void setNaliczoneUmorzenia(List<Umorzenie> naliczoneUmorzenia) {
        this.naliczoneUmorzenia = naliczoneUmorzenia;
    }
    
}
