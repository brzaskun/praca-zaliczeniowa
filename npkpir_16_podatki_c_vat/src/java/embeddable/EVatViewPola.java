/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class EVatViewPola implements Serializable{
    private int id;
    private String dataWyst;
    private String dataSprz;
    private String nrWlDk;
    private Kl kontr;
    private String opis;
    private double netto;
    private double vat;
    private String opizw;
    private String nazwaewidencji;

    public EVatViewPola() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataWyst() {
        return dataWyst;
    }

    public void setDataWyst(String dataWyst) {
        this.dataWyst = dataWyst;
    }

    public String getDataSprz() {
        return dataSprz;
    }

    public void setDataSprz(String dataSprz) {
        this.dataSprz = dataSprz;
    }

    public String getNrWlDk() {
        return nrWlDk;
    }

    public void setNrWlDk(String nrWlDk) {
        this.nrWlDk = nrWlDk;
    }

    public Kl getKontr() {
        return kontr;
    }

    public void setKontr(Kl kontr) {
        this.kontr = kontr;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getOpizw() {
        return opizw;
    }

    public void setOpizw(String opizw) {
        this.opizw = opizw;
    }

    public String getNazwaewidencji() {
        return nazwaewidencji;
    }

    public void setNazwaewidencji(String nazwaewidencji) {
        this.nazwaewidencji = nazwaewidencji;
    }

   
    
    
}
