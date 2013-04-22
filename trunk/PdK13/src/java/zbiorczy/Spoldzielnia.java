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
public class Spoldzielnia implements Serializable{
    private int id;
    private String nazwa;
    private String adres;
    private int iloscssprawbiezacych;

    public Spoldzielnia() {
    }

    public Spoldzielnia(int id, String nazwa, String adres, int iloscssprawbiezacych) {
        this.id = id;
        this.nazwa = nazwa;
        this.adres = adres;
        this.iloscssprawbiezacych = iloscssprawbiezacych;
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

    public int getIloscssprawbiezacych() {
        return iloscssprawbiezacych;
    }

    public void setIloscssprawbiezacych(int iloscssprawbiezacych) {
        this.iloscssprawbiezacych = iloscssprawbiezacych;
    }

    @Override
    public String toString() {
        return "id=" + id + ", nazwa=" + nazwa + ", adres=" + adres + ", iloscssprawbiezacych=" + iloscssprawbiezacych;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.nazwa);
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
        final Spoldzielnia other = (Spoldzielnia) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
