/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Faktura;
import entity.FakturaRozrachunki;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class FakturaRozliczenie implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int lp;
    private Faktura faktura;
    private FakturaRozrachunki rozliczenie;
    private boolean faktura0rozliczenie1;
    private String rok;
    private String mc;
    private boolean nowy0rozliczony1;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.faktura);
        hash = 17 * hash + Objects.hashCode(this.rozliczenie);
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
        final FakturaRozliczenie other = (FakturaRozliczenie) obj;
        if (!Objects.equals(this.faktura, other.faktura)) {
            return false;
        }
        if (!Objects.equals(this.rozliczenie, other.rozliczenie)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaRozliczenie{" + "lp=" + lp + ", faktura=" + faktura.getFakturaPK().getNumerkolejny() + ", rozliczenie=" + rozliczenie.getNrdokumentu() + ", faktura0rozliczenie1=" + faktura0rozliczenie1 + ", rok=" + rok + ", mc=" + mc + ", nowy0rozliczony1=" + nowy0rozliczony1 + '}';
    }

   
    
    
}
