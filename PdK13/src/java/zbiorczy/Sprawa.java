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
public class Sprawa implements Serializable{
    private int id;
    private Spoldzielnia wierzyciel;
    private Dluznik dluznik;
    private String datautworzenia;
    private String terminzakonczenia;
    private Double kwota;

    public Sprawa(int id, Spoldzielnia wierzyciel, Dluznik dluznik, String datautworzenia, String terminzakonczenia, Double kwota) {
        this.id = id;
        this.wierzyciel = wierzyciel;
        this.dluznik = dluznik;
        this.datautworzenia = datautworzenia;
        this.terminzakonczenia = terminzakonczenia;
        this.kwota = kwota;
    }

    public Sprawa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Spoldzielnia getWierzyciel() {
        return wierzyciel;
    }

    public void setWierzyciel(Spoldzielnia wierzyciel) {
        this.wierzyciel = wierzyciel;
    }

    public Dluznik getDluznik() {
        return dluznik;
    }

    public void setDluznik(Dluznik dluznik) {
        this.dluznik = dluznik;
    }

    public String getDatautworzenia() {
        return datautworzenia;
    }

    public void setDatautworzenia(String datautworzenia) {
        this.datautworzenia = datautworzenia;
    }

    public String getTerminzakonczenia() {
        return terminzakonczenia;
    }

    public void setTerminzakonczenia(String terminzakonczenia) {
        this.terminzakonczenia = terminzakonczenia;
    }

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final Sprawa other = (Sprawa) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.wierzyciel, other.wierzyciel)) {
            return false;
        }
        return true;
    }

  
    
    
}
