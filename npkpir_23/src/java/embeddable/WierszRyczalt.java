/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class WierszRyczalt implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String rok;
    private String mc;
    private String mcnazwa;
    private double kolumna_17i0;
    private double kolumna_15i0;
    private double kolumna_14i0;
    private double kolumna_12i5;
    private double kolumna_12i0;
    private double kolumna_10i0;
    private double kolumna_8i5;
    private double kolumna_5i5;
    private double kolumna_3i0;
    private double kolumna_2i0;
    private double razem;

    public WierszRyczalt() {
    }

    public WierszRyczalt(int id, String rok, String mc, String mcnazwa) {
        this.id = id;
        this.rok = rok;
        this.mc = mc;
        this.mcnazwa = mcnazwa;
    }

    public void dodaj(WierszRyczalt p ) {
        this.kolumna_17i0 = this.kolumna_17i0+p.kolumna_17i0;
        this.kolumna_15i0 = this.kolumna_15i0+p.kolumna_15i0;
        this.kolumna_14i0 = this.kolumna_14i0+p.kolumna_14i0;
        this.kolumna_12i5 = this.kolumna_12i5+p.kolumna_12i5;
        this.kolumna_12i0 = this.kolumna_12i0+p.kolumna_12i0;
        this.kolumna_10i0 = this.kolumna_10i0+p.kolumna_10i0;
        this.kolumna_8i5 = this.kolumna_8i5+p.kolumna_8i5;
        this.kolumna_5i5 = this.kolumna_5i5+p.kolumna_5i5;
        this.kolumna_3i0 = this.kolumna_3i0+p.kolumna_3i0;
        this.kolumna_2i0 = this.kolumna_2i0+p.kolumna_2i0;
        this.razem = this.getRazem()+p.getRazem();
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + Objects.hashCode(this.mc);
        hash = 89 * hash + Objects.hashCode(this.mcnazwa);
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
        final WierszRyczalt other = (WierszRyczalt) obj;
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

    @Override
    public String toString() {
        return "WierszRyczalt{" + "rok=" + rok + ", mc=" + mc + ", mcnazwa=" + mcnazwa + ", kolumna_8i5=" + kolumna_8i5 + ", kolumna_5i5=" + kolumna_5i5 + ", kolumna_3i0=" + kolumna_3i0 + ", razem=" + razem + '}';
    }

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

    public double getKolumna_17i0() {
        return kolumna_17i0;
    }

    public void setKolumna_17i0(double kolumna_17i0) {
        this.kolumna_17i0 = kolumna_17i0;
    }

    public double getKolumna_15i0() {
        return kolumna_15i0;
    }

    public void setKolumna_15i0(double kolumna_15i0) {
        this.kolumna_15i0 = kolumna_15i0;
    }

    public double getKolumna_14i0() {
        return kolumna_14i0;
    }

    public void setKolumna_14i0(double kolumna_14i0) {
        this.kolumna_14i0 = kolumna_14i0;
    }

    public double getKolumna_12i5() {
        return kolumna_12i5;
    }

    public void setKolumna_12i5(double kolumna_12i5) {
        this.kolumna_12i5 = kolumna_12i5;
    }

    public double getKolumna_12i0() {
        return kolumna_12i0;
    }

    public void setKolumna_12i0(double kolumna_12i0) {
        this.kolumna_12i0 = kolumna_12i0;
    }

    public double getKolumna_10i0() {
        return kolumna_10i0;
    }

    public void setKolumna_10i0(double kolumna_10i0) {
        this.kolumna_10i0 = kolumna_10i0;
    }

    public double getKolumna_8i5() {
        return kolumna_8i5;
    }

    public void setKolumna_8i5(double kolumna_8i5) {
        this.kolumna_8i5 = kolumna_8i5;
    }

    public double getKolumna_5i5() {
        return kolumna_5i5;
    }

    public void setKolumna_5i5(double kolumna_5i5) {
        this.kolumna_5i5 = kolumna_5i5;
    }

    public double getKolumna_3i0() {
        return kolumna_3i0;
    }

    public void setKolumna_3i0(double kolumna_3i0) {
        this.kolumna_3i0 = kolumna_3i0;
    }

    public double getKolumna_2i0() {
        return kolumna_2i0;
    }

    public void setKolumna_2i0(double kolumna_2i0) {
        this.kolumna_2i0 = kolumna_2i0;
    }

    public double getRazem() {
        return Z.z(this.kolumna_10i0+this.kolumna_12i0+this.kolumna_12i5+this.kolumna_14i0+this.kolumna_15i0+this.kolumna_17i0+this.kolumna_2i0+this.kolumna_3i0+this.kolumna_5i5+this.kolumna_8i5);
    }

    
    
    
    
    
}
