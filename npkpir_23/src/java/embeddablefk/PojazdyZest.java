/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddablefk;

import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */

public class PojazdyZest  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String kontonazwa;
    private String kontonumer;
    private double sumaokres;
    private double sumanarastajaco;
    private List<StronaWiersza> stronywiersza;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.kontonazwa);
        hash = 83 * hash + Objects.hashCode(this.kontonumer);
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
        final PojazdyZest other = (PojazdyZest) obj;
        if (!Objects.equals(this.kontonazwa, other.kontonazwa)) {
            return false;
        }
        if (!Objects.equals(this.kontonumer, other.kontonumer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PojazdyZest{" + "kontonazwa=" + kontonazwa + ", kontonumer=" + kontonumer + ", sumaokres=" + sumaokres + ", sumanarastajaco=" + sumanarastajaco + ", stronywiersza=" + stronywiersza + '}';
    }

    public String getKontonazwa() {
        return kontonazwa;
    }

    public void setKontonazwa(String kontonazwa) {
        this.kontonazwa = kontonazwa;
    }

    public String getKontonumer() {
        return kontonumer;
    }

    public void setKontonumer(String kontonumer) {
        this.kontonumer = kontonumer;
    }

    public double getSumaokres() {
        return sumaokres;
    }

    public void setSumaokres(double sumaokres) {
        this.sumaokres = sumaokres;
    }

    public double getSumanarastajaco() {
        return sumanarastajaco;
    }

    public void setSumanarastajaco(double sumanarastajaco) {
        this.sumanarastajaco = sumanarastajaco;
    }

    public List<StronaWiersza> getStronywiersza() {
        return stronywiersza;
    }

    public void setStronywiersza(List<StronaWiersza> stronywiersza) {
        this.stronywiersza = stronywiersza;
    }
    
    
}
