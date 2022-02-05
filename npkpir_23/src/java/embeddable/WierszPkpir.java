/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */

public class WierszPkpir implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String rok;
    private String mc;
    private String mcnazwa;
    //"przych. sprz"
    private double kolumna7;
    //"pozost. przych."
    private double kolumna8;
    // razem przychody 7+8
    private double kolumna9;
    //"zakup tow. i mat."
    private double kolumna10;
    //"koszty ub.zak."
    private double kolumna11;
    //"wynagrodzenia"
    private double kolumna12;
    //"poz. koszty"
    private double kolumna13;
    //razem wydatki 12+13
    private double kolumna14;
    //kolumna dowolna
    private double kolumna15;
    //opis wydatku b+r
    private String kolumna160;
    //"koszt b+r"
    private double kolumna161;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMcnazwa() {
        return mcnazwa;
    }

    public void setMcnazwa(String mcnazwa) {
        this.mcnazwa = mcnazwa;
    }

    public double getKolumna7() {
        return kolumna7;
    }

    public void setKolumna7(double kolumna7) {
        this.kolumna7 = kolumna7;
    }

    public double getKolumna8() {
        return kolumna8;
    }

    public void setKolumna8(double kolumna8) {
        this.kolumna8 = kolumna8;
    }

    public double getKolumna9() {
        return kolumna9;
    }

    public void setKolumna9(double kolumna9) {
        this.kolumna9 = kolumna9;
    }

    public double getKolumna10() {
        return kolumna10;
    }

    public void setKolumna10(double kolumna10) {
        this.kolumna10 = kolumna10;
    }

    public double getKolumna11() {
        return kolumna11;
    }

    public void setKolumna11(double kolumna11) {
        this.kolumna11 = kolumna11;
    }

    public double getKolumna12() {
        return kolumna12;
    }

    public void setKolumna12(double kolumna12) {
        this.kolumna12 = kolumna12;
    }

    public double getKolumna13() {
        return kolumna13;
    }

    public void setKolumna13(double kolumna13) {
        this.kolumna13 = kolumna13;
    }

    public double getKolumna14() {
        return kolumna14;
    }

    public void setKolumna14(double kolumna14) {
        this.kolumna14 = kolumna14;
    }

    public double getKolumna15() {
        return kolumna15;
    }

    public void setKolumna15(double kolumna15) {
        this.kolumna15 = kolumna15;
    }

    public String getKolumna160() {
        return kolumna160;
    }

    public void setKolumna160(String kolumna160) {
        this.kolumna160 = kolumna160;
    }

    public double getKolumna161() {
        return kolumna161;
    }

    public void setKolumna161(double kolumna161) {
        this.kolumna161 = kolumna161;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.rok);
        hash = 47 * hash + Objects.hashCode(this.mc);
        hash = 47 * hash + Objects.hashCode(this.mcnazwa);
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
        final WierszPkpir other = (WierszPkpir) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.mcnazwa, other.mcnazwa)) {
            return false;
        }
        return true;
    }
    
                  
                          
    
}
