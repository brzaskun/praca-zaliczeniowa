/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Evewidencja;
import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Osito
 */

@Entity
public class EVatViewPola implements Serializable{
    private static final long serialVersionUID = -7659459927104523420L;
    private static final List<String> opispol;
    static {
        opispol = new ArrayList<>();
        opispol.add("id");
        opispol.add("dataWyst");
        opispol.add("dataSprz");
        opispol.add("nrWlDk");
        opispol.add("kontr");
        opispol.add("opis");
        opispol.add("netto");
        opispol.add("vat");
        opispol.add("opizw");
        opispol.add("innyMc");
    }

    public static List<String> getOpispol() {
        return opispol;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    private String dataWyst;
    private String dataSprz;
    private String nrWlDk;
    private String nrKolejny;
    private Klienci kontr;
    private String opis;
    private double netto;
    private double vat;
    private String opizw;
    private String innymc;
    @OneToOne
    @JoinColumn(name = "nazwaewidencji", referencedColumnName = "nazwa")
    private Evewidencja nazwaewidencji;
    private String nrpolanetto;
    private String nrpolavat;

    
    
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

    public Klienci getKontr() {
        return kontr;
    }

    public void setKontr(Klienci kontr) {
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

    public Evewidencja getNazwaewidencji() {
        return nazwaewidencji;
    }

    public void setNazwaewidencji(Evewidencja nazwaewidencji) {
        this.nazwaewidencji = nazwaewidencji;
    }


    public String getNrpolanetto() {
        return nrpolanetto;
    }

    public void setNrpolanetto(String nrpolanetto) {
        this.nrpolanetto = nrpolanetto;
    }

    public String getNrpolavat() {
        return nrpolavat;
    }

    public void setNrpolavat(String nrpolavat) {
        this.nrpolavat = nrpolavat;
    }

    public String getInnymc() {
        return innymc;
    }

    public void setInnymc(String innymc) {
        this.innymc = innymc;
    }

    public String getNrKolejny() {
        return nrKolejny;
    }

    public void setNrKolejny(String nrKolejny) {
        this.nrKolejny = nrKolejny;
    }

    
   

    
    
}
