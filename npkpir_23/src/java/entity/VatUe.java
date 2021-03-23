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
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import vies.Vies;

/**
 *
 * @author Osito
 */

@Entity
public class VatUe extends VatSuper implements Serializable{
    private static final long serialVersionUID = 1L;
    @JoinColumn(name = "deklaracjavatUE", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private DeklaracjavatUE deklaracjavatUE;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vatUe")
    private List<Dok> zawiera;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vatUe")
    private List<Dokfk> zawierafk;
    @JoinColumn(name = "vies", referencedColumnName = "lp")
    @OneToOne(mappedBy = "vatue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    protected Vies vies;
    @Transient
    private String poprzedninip;

    

    public VatUe() {
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }


    public VatUe(String transakcja, Klienci kontrahent, Double netto, int liczbadok, List<Dok> zawiera) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.liczbadok = liczbadok;
        this.zawiera = zawiera;
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public VatUe(String transakcja, Klienci kontrahent, double netto, double nettowal) {
        this.transakcja = transakcja;
        this.kontrahent = kontrahent;
        this.netto = netto;
        this.nettowaluta = nettowal;
        this.zawiera = Collections.synchronizedList(new ArrayList<>());
        this.zawierafk = Collections.synchronizedList(new ArrayList<>());
    }
    
    public VatUe(String transakcja, Klienci kontrahent, Double netto, int liczbadok) {
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

    public DeklaracjavatUE getDeklaracjavatUE() {
        return deklaracjavatUE;
    }

    public void setDeklaracjavatUE(DeklaracjavatUE deklaracjavatUE) {
        this.deklaracjavatUE = deklaracjavatUE;
    }

    public Vies getVies() {
        return vies;
    }

    public void setVies(Vies vies) {
        this.vies = vies;
    }

    public String getPoprzedninip() {
        return poprzedninip;
    }

    public void setPoprzedninip(String poprzedninip) {
        this.poprzedninip = poprzedninip;
    }

   

    public String getKontrahentwyborNIP() {
        String zwrot = this.kontrahentkraj;
        try {
            zwrot = zwrot+this.kontrahentnip;
        } catch (Exception e){}
        if (this.kontrahent!=null) {
            zwrot = this.kontrahent.getNip();
        }   
        return zwrot;
    }
    
    public String getKontrahentwyborKraj() {
        String zwrot = this.kontrahentkraj;
        try {
            if (this.kontrahent!=null) {
                zwrot = this.kontrahent.getKrajkod();
            }   
        } catch (Exception e){}
        return zwrot;
    }
    
     public String getKontrahentwyborNazwa() {
        String zwrot = this.kontrahentnazwa;
        try {
            if (this.kontrahent!=null) {
                zwrot = this.kontrahent.getNpelna();
            }    
        } catch (Exception e){}
        return zwrot;
    }
    
  
    
}
