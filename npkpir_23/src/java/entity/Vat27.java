/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Osito
 */

@Entity
public class Vat27 extends VatSuper implements Serializable{
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "deklaracjavat27", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Deklaracjavat27 deklaracjavat27;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vat27")
    private List<Dok> zawiera;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "vat27")
    private List<Dokfk> zawierafk;
    
    

    public Vat27() {
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }


    public Vat27(String transakcja, Klienci kontrahent, Double netto, int liczbadok, List<Dok> zawiera) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = zawiera;
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public Vat27(String transakcja, Klienci kontrahent, double netto, double nettowal) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.nettowaluta = nettowal;
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public Vat27(String transakcja, Klienci kontrahent, Double netto, int liczbadok) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }


    
    public List<Dok> getZawiera() {
        return zawiera;
    }

    public void setZawiera(List<Dok> zawiera) {
        this.zawiera = zawiera;
    }

    public List<Dokfk> getZawierafk() {
        return zawierafk;
    }

    public void setZawierafk(List<Dokfk> zawierafk) {
        this.zawierafk = zawierafk;
    }

    public Deklaracjavat27 getDeklaracjavat27() {
        return deklaracjavat27;
    }

    public void setDeklaracjavat27(Deklaracjavat27 deklaracjavat27) {
        this.deklaracjavat27 = deklaracjavat27;
    }

    
  
    
}
