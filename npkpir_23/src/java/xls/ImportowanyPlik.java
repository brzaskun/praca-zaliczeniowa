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
public class ImportowanyPlik {

    private String opis;
    private String rozszerzenie;
    private String dzielnik;

    public ImportowanyPlik(String opis, String rozszerzenie, String dzielnik) {
        this.opis = opis;
        this.rozszerzenie = rozszerzenie;
        this.dzielnik = dzielnik;
    }

    public ImportowanyPlik(String opis, String rozszerzenie) {
        this.opis = opis;
        this.rozszerzenie = rozszerzenie;
        this.dzielnik = "";
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
    }

    public String getDzielnik() {
        return dzielnik;
    }

    public void setDzielnik(String dzielnik) {
        this.dzielnik = dzielnik;
    }

    @Override
    public String toString() {
        return "ImportowanyPlik{" + "opis=" + opis + ", rozszerzenie=" + rozszerzenie + ", dzielnik=" + dzielnik + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.opis);
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
        final ImportowanyPlik other = (ImportowanyPlik) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        return true;
    }

}
