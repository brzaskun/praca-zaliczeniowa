/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zbiorczy;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class Dluznik implements Serializable{
    private int id;
    private String nazwa;
    private String adres;

    public Dluznik() {
    }

    public Dluznik(int id, String nazwa, String adres) {
        this.id = id;
        this.nazwa = nazwa;
        this.adres = adres;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nazwa=" + nazwa + ", adres=" + adres;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + Objects.hashCode(this.nazwa);
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
        final Dluznik other = (Dluznik) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }
   
    
    
}
