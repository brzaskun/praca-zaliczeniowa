/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stronawiersza")
@NamedQueries({
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wnma = wnma")
})
public class StronaWiersza implements Serializable{
     private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idwiersza", referencedColumnName = "idwiersza")
    private Wiersz wiersz;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotaPLN")
    private double kwotaPLN;
    @Column(name = "kwotaWaluta")
    private double kwotaWaluta;
    @Column(name="rozliczono")
    private double rozliczono;
    @Column(name="pozostalo")
    private double pozostalo;
    @Column(name="typwiersza")//0-nowy, 1-nowatransakcja, 2- rozliczajacy, inne do wykorzystania
    private int typwiersza;
    @Column(name= "datarozrachunku")
    private String datarozrachunku;
    @Column(name= "konto")
    private Konto konto;
    @Column(name = "wnma")
    private String wnma;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Transakcja> transakcje;

   
    public StronaWiersza(Wiersz nowywiersz) {
        this.transakcje = new ArrayList<>();
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.wiersz = nowywiersz;
        this.typwiersza = nowywiersz.getTypWiersza();
    }
    

    public StronaWiersza() {
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.typwiersza = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getWnma() {
        return wnma;
    }

    public void setWnma(String wnma) {
        this.wnma = wnma;
    }

    public double getRozliczono() {
        this.rozliczono = 0.0;
        for (Transakcja p : this.transakcje) {
            this.rozliczono += p.getKwotatransakcji();
        }
        this.pozostalo = this.kwota - this.rozliczono;
        return rozliczono;
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
        return pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }

    public int getTypwiersza() {
        return typwiersza;
    }

    public void setTypwiersza(int typwiersza) {
        this.typwiersza = typwiersza;
    }


    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotaPLN() {
        return kwotaPLN;
    }

    public void setKwotaPLN(double kwotaPLN) {
        this.kwotaPLN = kwotaPLN;
    }

    public double getKwotaWaluta() {
        return kwotaWaluta;
    }

    public void setKwotaWaluta(double kwotaWaluta) {
        this.kwotaWaluta = kwotaWaluta;
    }

    public String getDatarozrachunku() {
        return datarozrachunku;
    }

    public void setDatarozrachunku(String datarozrachunku) {
        this.datarozrachunku = datarozrachunku;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public void dodajTransakcjeNowe(Transakcja transakcja) {
        if (this.transakcje.contains(transakcja)) {
            this.rozliczono = this.rozliczono - transakcja.getPoprzedniakwota() + transakcja.getKwotatransakcji();
        } else {
            this.rozliczono = this.rozliczono + transakcja.getKwotatransakcji();
            this.transakcje.add(transakcja);
        }
        this.pozostalo = this.kwota - this.rozliczono;
    }
    
    public List<Transakcja> pobierzTransakcje() {
        return this.pobierzTransakcje();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.wiersz);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
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
        final StronaWiersza other = (StronaWiersza) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StronaWiersza{" + "id=" + id + ", wiersz=" + wiersz + ", kwota=" + kwota + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + typwiersza + '}';
    }

    

    
}
