/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class RodzajedokPK implements Serializable {
    private static final long serialVersionUID = 766671343308317524L;
    private String skrotNazwyDok;
    private String podatnik;

    public RodzajedokPK() {
    }

    public RodzajedokPK(String skrot) {
        this.skrotNazwyDok = skrot;
    }

    public RodzajedokPK(String skrot, String podatnik) {
        this.skrotNazwyDok = skrot;
        this.podatnik = podatnik;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.skrotNazwyDok);
        hash = 53 * hash + Objects.hashCode(this.podatnik);
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
        final RodzajedokPK other = (RodzajedokPK) obj;
        if (!Objects.equals(this.skrotNazwyDok, other.skrotNazwyDok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RodzajedokPK{" + "skrot=" + skrotNazwyDok + ", podatnik=" + podatnik + '}';
    }
    
    

    public String getSkrotNazwyDok() {
        return skrotNazwyDok;
    }

    public void setSkrotNazwyDok(String skrotNazwyDok) {
        this.skrotNazwyDok = skrotNazwyDok;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }
    
    
}
