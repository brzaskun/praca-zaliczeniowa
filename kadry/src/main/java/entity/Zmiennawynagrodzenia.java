/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zmiennawynagrodzenia", uniqueConstraints = {
    @UniqueConstraint(columnNames={"dataod","datado", "skladnikwynagrodzenia"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zmiennawynagrodzenia.findAll", query = "SELECT z FROM Zmiennawynagrodzenia z"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findById", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.id = :id"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDatado", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.datado = :datado"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataod", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.dataod = :dataod"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByKwotastala", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.kwota = :kwota"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByNazwa", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.nazwa = :nazwa"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findBySkladnik", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia = :skladnikwynagrodzenia"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataSkladnik", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia = :skladnikwynagrodzenia AND z.dataod = :dataod AND z.datado = :datado"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataSkladnikNull", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia = :skladnikwynagrodzenia AND z.dataod = :dataod AND z.datado IS NULL"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataSkladnikRodzajAngaz", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia.rodzajwynagrodzenia = :rodzajwynagrodzenia AND z.skladnikwynagrodzenia.angaz = :angaz AND z.dataod = :dataod AND z.datado = :datado"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataSkladnikRodzajAngazNull", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia.rodzajwynagrodzenia = :rodzajwynagrodzenia AND z.skladnikwynagrodzenia.angaz = :angaz AND z.dataod = :dataod AND z.datado IS NULL"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByRodzajFirma", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.skladnikwynagrodzenia.rodzajwynagrodzenia = :rodzajwynagrodzenia AND z.skladnikwynagrodzenia.angaz.firma = :firma")
})
public class Zmiennawynagrodzenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "datado")
    private String datado;
    @Size(max = 10)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 3)
    @Column(name = "waluta")
    private String waluta;
    @Column(name = "netto0brutto1")
    private boolean netto0brutto1;
    @JoinColumn(name = "skladnikwynagrodzenia", referencedColumnName = "id")
    @ManyToOne
    private Skladnikwynagrodzenia skladnikwynagrodzenia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotadoredukcji")
    private double kwotadoredukcji;
    @Column(name = "aktywna")
    private  boolean aktywna;
    @Column(name = "aneks")
    private  boolean aneks;
    @Column(name = "minimalneustatowe")
    private  boolean minimalneustatowe;
    @Column(name = "nrkolejnyzmiennej")
    private  int nrkolejnyzmiennej;
    @Transient
    private double nowakwota;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datadodania")
    private Date datadodania;
    @Column(name = "utworzyl")
    private String utworzyl;


    public Zmiennawynagrodzenia() {
        this.waluta = "PLN";
        this.netto0brutto1 = true;
    }

    public Zmiennawynagrodzenia(int id) {
        this.id = id;
        this.waluta = "PLN";
        this.netto0brutto1 = true;
    }

    public Zmiennawynagrodzenia(Rachunekdoumowyzlecenia p, Skladnikwynagrodzenia skladnik) {
        this.dataod = p.getDataod();
        this.datado = p.getDatado();
        this.kwota = p.getKwota();
        this.netto0brutto1 = true;
        this.nazwa = "Umowa zlecenia";
        this.skladnikwynagrodzenia = skladnik;
        this.waluta = "PLN";
    }

    //konstruktor tylko dla aneksowanai
    public Zmiennawynagrodzenia(Zmiennawynagrodzenia r, String dataod) {
        this.datado = null;
        this.dataod = dataod;
        this.nazwa = r.getNazwa();
        this.waluta = r.getWaluta();
        this.netto0brutto1 = r.isNetto0brutto1();
        this.skladnikwynagrodzenia = r.getSkladnikwynagrodzenia();
        this.kwota = r.getNowakwota();
        this.aktywna = r.isAktywna();
        this.minimalneustatowe = r.isMinimalneustatowe();
    }

    public Zmiennawynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        this.skladnikwynagrodzenia = skladnikwynagrodzenia;
        this.waluta = "PLN";
        this.netto0brutto1 = true;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.datado);
        hash = 83 * hash + Objects.hashCode(this.dataod);
        hash = 83 * hash + Objects.hashCode(this.skladnikwynagrodzenia);
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
        final Zmiennawynagrodzenia other = (Zmiennawynagrodzenia) obj;
        if (!Objects.equals(this.datado, other.datado)) {
            return false;
        }
        if (!Objects.equals(this.dataod, other.dataod)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.skladnikwynagrodzenia, other.skladnikwynagrodzenia)) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "Zmiennawynagrodzenia{" + "datado=" + datado + ", dataod=" + dataod + ", nazwa=" + nazwa + ", waluta=" + waluta + ", netto0brutto1=" + netto0brutto1 + ", skladnikwynagrodzenia=" + skladnikwynagrodzenia.getRodzajwynagrodzenia().getOpisskrocony() + ", kwota=" + kwota + ", aktywna=" + aktywna + '}';
    }

    public Date getDatadodania() {
        return datadodania;
    }

    public void setDatadodania(Date datadodania) {
        this.datadodania = datadodania;
    }

    public String getUtworzyl() {
        return utworzyl;
    }

    public void setUtworzyl(String utworzyl) {
        this.utworzyl = utworzyl;
    }

  

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotadoredukcji() {
        return kwotadoredukcji;
    }

    public void setKwotadoredukcji(double kwotadoredukcji) {
        this.kwotadoredukcji = kwotadoredukcji;
    }
    

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Skladnikwynagrodzenia getSkladnikwynagrodzenia() {
        return skladnikwynagrodzenia;
    }

    public void setSkladnikwynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        this.skladnikwynagrodzenia = skladnikwynagrodzenia;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public boolean isNetto0brutto1() {
        return netto0brutto1;
    }

    public void setNetto0brutto1(boolean netto0brutto1) {
        this.netto0brutto1 = netto0brutto1;
    }

    public boolean isAktywna() {
        return aktywna;
    }

    public void setAktywna(boolean aktywna) {
        this.aktywna = aktywna;
    }

    public boolean isMinimalneustatowe() {
        return minimalneustatowe;
    }

    public void setMinimalneustatowe(boolean minimalneustatowe) {
        this.minimalneustatowe = minimalneustatowe;
    }

    public int getNrkolejnyzmiennej() {
        return nrkolejnyzmiennej;
    }

    public void setNrkolejnyzmiennej(int nrkolejnyzmiennej) {
        this.nrkolejnyzmiennej = nrkolejnyzmiennej;
    }

    public double getNowakwota() {
        return nowakwota;
    }

    public void setNowakwota(double nowakwota) {
        this.nowakwota = nowakwota;
    }

    public boolean isAneks() {
        return aneks;
    }

    public void setAneks(boolean aneks) {
        this.aneks = aneks;
    }

   

    

    
}
