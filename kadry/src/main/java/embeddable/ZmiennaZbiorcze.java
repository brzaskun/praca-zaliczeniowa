/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Pracownik;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class ZmiennaZbiorcze  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Pracownik pracownik;
    private Zmiennawynagrodzenia zmienna;
    private Zmiennawynagrodzenia ostatniazmienna;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public Zmiennawynagrodzenia getZmienna() {
        return zmienna;
    }

    public void setZmienna(Zmiennawynagrodzenia zmienna) {
        this.zmienna = zmienna;
    }

    public Zmiennawynagrodzenia getOstatniazmienna() {
        return ostatniazmienna;
    }

    public void setOstatniazmienna(Zmiennawynagrodzenia ostatniazmienna) {
        this.ostatniazmienna = ostatniazmienna;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.pracownik);
        hash = 53 * hash + Objects.hashCode(this.zmienna);
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
        final ZmiennaZbiorcze other = (ZmiennaZbiorcze) obj;
        if (!Objects.equals(this.pracownik, other.pracownik)) {
            return false;
        }
        if (!Objects.equals(this.zmienna, other.zmienna)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZmiennaZbiorcze{" + "id=" + id + ", pracownik=" + pracownik.getNazwiskoImie() + ", zmienna=" + zmienna.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny() + '}';
    }
    
    
}
