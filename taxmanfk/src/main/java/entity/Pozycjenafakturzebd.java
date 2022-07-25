/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Pozycjenafakturzebazadanych;
import entityfk.Tabelanbp;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pozycjenafakturzebd")
public class Pozycjenafakturzebd implements Serializable{
        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Basic(optional = false)
        @Column(name = "id")
        private int id;
        @Column(name = "lp")
        private int lp;
        @Column(name = "nazwa")
        private String nazwa = "";
        @Column(name = "nowakolumna")
        private String nowakolumna = "";
        @Column(name = "cenajedn0")
        private double cenajedn0 = 0;
        @Column(name = "cenajedn1")
        private double cenajedn1 = 0;
        @Column(name = "cenajedn2")
        private double cenajedn2 = 0;
        @Column(name = "cenajedn3")
        private double cenajedn3 = 0;
        @Column(name = "cenajedn4")
        private double cenajedn4 = 0;
        @Column(name = "cenajedn5")
        private double cenajedn5 = 0;
        @Column(name = "PKWiU")
        private String PKWiU = "";
        @Column(name = "jednostka")
        private String jednostka = "";
        @Column(name = "ilosc")
        private double ilosc = 0;
        @Column(name = "cena")
        private double cena = 0;
        @Column(name = "netto")
        private double netto = 0;
        @Column(name = "podatek")
        private double podatek = 0;
        @Column(name = "podatekkwota")
        private double podatekkwota = 0;
        @Column(name = "brutto")
        private double brutto = 0;
        @Column(name = "dodatkowapozycja")
        private int dodatkowapozycja = 0;
        @Column(name = "pierwotna0korekta1")
        private int pierwotna0korekta1 = 0;
        @ManyToOne
        @JoinColumn(name = "faktura", referencedColumnName = "id")
        private Faktura faktura;

    public Pozycjenafakturzebd() {
    }

    public Pozycjenafakturzebd(int lp, String nazwa, String PKWiU, String jednostka, double ilosc, double cena, double netto, double podatek, double podatekkwota, double brutto) {
       this.lp = lp;
       this.nazwa = nazwa;
       this.PKWiU = PKWiU;
       this.jednostka = jednostka;
       this.ilosc = ilosc;
       this.cena = cena;
       this.netto = netto;
       this.podatek = podatek;
       this.podatekkwota = podatekkwota;
       this.brutto = brutto;
   }
    
    public Pozycjenafakturzebd(int lp, String nazwa, String nowakolumna, double cenajedn0, double cenajedn1, double cenajedn2, double cenajedn3, double cenajedn4, double cenajedn5, String PKWiU, String jednostka, double ilosc, double cena, double netto, double podatek, double podatekkwota, double brutto, int dodatkowapozycja) {
       this.lp = lp;
       this.nazwa = nazwa;
       this.nowakolumna = nowakolumna;
       this.cenajedn0 = cenajedn0;
       this.cenajedn1 = cenajedn1;
       this.cenajedn2 = cenajedn2;
       this.cenajedn3 = cenajedn3;
       this.cenajedn4 = cenajedn4;
       this.cenajedn5 = cenajedn5;
       this.PKWiU = PKWiU;
       this.jednostka = jednostka;
       this.ilosc = ilosc;
       this.cena = cena;
       this.netto = netto;
       this.podatek = podatek;
       this.podatekkwota = podatekkwota;
       this.brutto = brutto;
       this.dodatkowapozycja = dodatkowapozycja;
   }

    public Pozycjenafakturzebd(Pozycjenafakturzebd p) {
       this.lp = p.lp;
       this.nazwa = p.nazwa;
       this.nowakolumna = p.nowakolumna;
       this.cenajedn0 = p.cenajedn0;
       this.cenajedn1 = p.cenajedn1;
       this.cenajedn2 = p.cenajedn2;
       this.cenajedn3 = p.cenajedn3;
       this.cenajedn4 = p.cenajedn4;
       this.cenajedn5 = p.cenajedn5;
       this.PKWiU = p.PKWiU;
       this.jednostka = p.jednostka;
       this.ilosc = p.ilosc;
       this.cena = p.cena;
       this.netto = p.netto;
       this.podatek = p.podatek;
       this.podatekkwota = p.podatekkwota;
       this.brutto = p.brutto;
       this.dodatkowapozycja = p.dodatkowapozycja;
       this.pierwotna0korekta1 = p.pierwotna0korekta1;
    }
    
    public Pozycjenafakturzebd(Pozycjenafakturzebazadanych p, int pierwotna0korekta1, Faktura f) {
       this.lp = p.getLp();
       this.nazwa = p.getNazwa();
       this.nowakolumna = p.getNowakolumna();
       this.cenajedn0 = p.getCenajedn0();
       this.cenajedn1 = p.getCenajedn1();
       this.cenajedn2 = p.getCenajedn2();
       this.cenajedn3 = p.getCenajedn3();
       this.cenajedn4 = p.getCenajedn4();
       this.cenajedn5 = p.getCenajedn5();
       this.PKWiU = p.getPKWiU();
       this.jednostka = p.getJednostka();
       this.ilosc = p.getIlosc();
       this.cena = p.getCena();
       this.netto = p.getNetto();
       this.podatek = p.getPodatek();
       this.podatekkwota = p.getPodatekkwota();
       this.brutto = p.getBrutto();
       this.dodatkowapozycja = p.getDodatkowapozycja();
       this.pierwotna0korekta1 = pierwotna0korekta1;
       this.faktura = f;
    }
    

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCenajedn0() {
        return cenajedn0;
    }

    public void setCenajedn0(double cenajedn0) {
        this.cenajedn0 = cenajedn0;
    }

    
    public double getCenajedn1() {
        return cenajedn1;
    }

    public void setCenajedn1(double cenajedn1) {
        this.cenajedn1 = cenajedn1;
    }

    public double getCenajedn2() {
        return cenajedn2;
    }

    public void setCenajedn2(double cenajedn2) {
        this.cenajedn2 = cenajedn2;
    }

    public double getCenajedn3() {
        return cenajedn3;
    }

    public void setCenajedn3(double cenajedn3) {
        this.cenajedn3 = cenajedn3;
    }

    public double getCenajedn4() {
        return cenajedn4;
    }

    public void setCenajedn4(double cenajedn4) {
        this.cenajedn4 = cenajedn4;
    }

    public double getCenajedn5() {
        return cenajedn5;
    }

    public void setCenajedn5(double cenajedn5) {
        this.cenajedn5 = cenajedn5;
    }

    public String getPKWiU() {
        return PKWiU;
    }

    public void setPKWiU(String PKWiU) {
        this.PKWiU = PKWiU;
    }

    public String getJednostka() {
        return jednostka;
    }

    public void setJednostka(String jednostka) {
        this.jednostka = jednostka;
    }

    public double getIlosc() {
        return ilosc;
    }

    public void setIlosc(double ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getPodatek() {
        return podatek;
    }

    public void setPodatek(double podatek) {
        this.podatek = podatek;
    }

    public double getPodatekkwota() {
        return podatekkwota;
    }

    public void setPodatekkwota(double podatekkwota) {
        this.podatekkwota = podatekkwota;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public String getNowakolumna() {
        return nowakolumna;
    }

    public void setNowakolumna(String nowakolumna) {
        this.nowakolumna = nowakolumna;
    }

    public int getDodatkowapozycja() {
        return dodatkowapozycja;
    }

    public void setDodatkowapozycja(int dodatkowapozycja) {
        this.dodatkowapozycja = dodatkowapozycja;
    }

    public int getPierwotna0korekta1() {
        return pierwotna0korekta1;
    }

    public void setPierwotna0korekta1(int pierwotna0korekta1) {
        this.pierwotna0korekta1 = pierwotna0korekta1;
    }
     
    
    


    @Override
    public String toString() {
        return "Pozycjenafakturzebazadanych{" + "lp=" + lp + ", nazwa=" + nazwa + ", PKWiU=" + PKWiU + ", jednostka=" + jednostka + ", ilosc=" + ilosc + ", cena=" + cena + ", netto=" + netto + ", podatek=" + podatek + ", podatekkwota=" + podatekkwota + ", brutto=" + brutto + '}';
    }


    public Object getCena(boolean liczodwartoscibrutto, double podatek) {
        double zwrot = this.cena;
        if (liczodwartoscibrutto && Z.z(podatek) != 0.0) {
            zwrot = Z.z(this.cena-Z.z(this.cena*podatek/(100+podatek)));
        }
        return zwrot;
    }

    public double getNetto(Tabelanbp tabelanbp) {
        double zwrot = this.netto;
        if (tabelanbp!=null) {
            zwrot = Z.z(this.netto*tabelanbp.getKurssredniPrzelicznik());
        }
        return zwrot;
    }

    public double getPodatekkwota(Tabelanbp tabelanbp) {
        double zwrot = this.podatekkwota;
        if (tabelanbp!=null) {
            zwrot = Z.z(this.podatekkwota*tabelanbp.getKurssredniPrzelicznik());
        }
        return zwrot;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.nazwa);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.podatek) ^ (Double.doubleToLongBits(this.podatek) >>> 32));
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
        final Pozycjenafakturzebd other = (Pozycjenafakturzebd) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.netto) != Double.doubleToLongBits(other.netto)) {
            return false;
        }
        if (Double.doubleToLongBits(this.podatek) != Double.doubleToLongBits(other.podatek)) {
            return false;
        }
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }
 
        
}
