/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddable;

import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class FakturaZestawienie  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Podatnik podatnik;
    private Klienci kontrahent;
    private boolean zprogramu;
    private List<FZTresc> trescfaktury;
    

    public FakturaZestawienie() {
        trescfaktury = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.podatnik);
        hash = 71 * hash + Objects.hashCode(this.kontrahent);
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
        final FakturaZestawienie other = (FakturaZestawienie) obj;
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String kont = kontrahent!=null ? kontrahent.getNskrocona() : "brak";
        String poda = podatnik!=null ? podatnik.getPrintnazwa() : "brak";
        int sizefakt  = trescfaktury!=null ? trescfaktury.size() : 0;
        return "FakturaZestawienie{" + "podatnik=" + poda + ", kontrahent=" + kont + ", zprogramu=" + zprogramu + ", trescfaktury=" + sizefakt + '}';
    }

    
    
    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }
    
    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public boolean isZprogramu() {
        return zprogramu;
    }

    public void setZprogramu(boolean zprogramu) {
        this.zprogramu = zprogramu;
    }

    public List<FZTresc> getTrescfaktury() {
        return trescfaktury;
    }

    public void setTrescfaktury(List<FZTresc> trescfaktury) {
        this.trescfaktury = trescfaktury;
    }

  

      
    
    
    
}
