/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PozycjaObliczenia {
    private int lp;
    private String opis;
    private Object kwota;

    

    public PozycjaObliczenia() {
    }

    public PozycjaObliczenia(int lp, String opis, Object kwota) {
        this.lp = lp;
        this.opis = opis;
        this.kwota = kwota;
    }

    @Override
    public String toString() {
        return "PozycjaObliczenia{" + "lp=" + lp + ", opis=" + opis + ", kwota=" + kwota + '}';
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.opis);
        return hash;
    }

  

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PozycjaObliczenia other = (PozycjaObliczenia) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        return true;
    }

   

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Object getKwota() {
        return kwota;
    }

    public void setKwota(Object kwota) {
        this.kwota = kwota;
    }

   

    
    
    
    
}
