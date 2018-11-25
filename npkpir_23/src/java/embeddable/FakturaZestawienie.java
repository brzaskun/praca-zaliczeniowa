/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddable;

import entity.Faktura;
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

      
    
    public class FZTresc {
        private String mc;
        private String nrfakt;
        private String data;
        private double netto;
        private double vat;
        private double brutto;
        private String opis;
        private Faktura faktura;

        public String getMc() {
            return mc;
        }

        public void setMc(String mc) {
            this.mc = mc;
        }

        public String getNrfakt() {
            return nrfakt;
        }

        public void setNrfakt(String nrfakt) {
            this.nrfakt = nrfakt;
        }

        public double getNetto() {
            return netto;
        }

        public void setNetto(double netto) {
            this.netto = netto;
        }

        public String getOpis() {
            return opis;
        }

        public void setOpis(String opis) {
            this.opis = opis;
        }

        public double getVat() {
            return vat;
        }

        public void setVat(double vat) {
            this.vat = vat;
        }

        public double getBrutto() {
            return brutto;
        }

        public void setBrutto(double brutto) {
            this.brutto = brutto;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Faktura getFaktura() {
            return faktura;
        }

        public void setFaktura(Faktura faktura) {
            this.faktura = faktura;
        }

       
        
        
        
    }
    
}
