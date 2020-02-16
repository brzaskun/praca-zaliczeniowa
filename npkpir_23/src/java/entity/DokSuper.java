/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.EVatwpisFK;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class DokSuper  implements Serializable {
    private static final long serialVersionUID = -1401473269477599841L;
    @Transient
    private Klienci kontr1;
    @Transient
    private String typdokumentu;
    @Transient
    private Klienci kontr;
    @Transient
    private Rodzajedok rodzajedok;
    @Transient
    private List<EVatwpis1> ewidencjaVAT1;
    @Transient
    private List<EVatwpisFK> ewidencjaVAT;
    @Transient
    private Waluty walutadokumentu;
    @Transient
    private String terminPlatnosci;
    
    

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public Klienci getKontr() {
        return kontr;
    }

    public void setKontr(Klienci kontr) {
        this.kontr = kontr;
    }
    
    public double[] pobierzwartosci() {
        return new double[2];
    }
   

    public Waluty getWalutadokumentu() {
        return walutadokumentu;
    }

    public Rodzajedok getRodzajedok() {
        return rodzajedok;
    }

    public void setRodzajedok(Rodzajedok rodzajedok) {
        this.rodzajedok = rodzajedok;
    }

    public String getTerminPlatnosci() {
        return terminPlatnosci;
    }

    public void setTerminPlatnosci(String terminPlatnosci) {
        this.terminPlatnosci = terminPlatnosci;
    }
   
    
    
}
