/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */

@Entity
@Table(name = "klientJPK")
@NamedQueries({
    @NamedQuery(name = "KlientJPK.deletePodRokMc", query = "DELETE FROM KlientJPK a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc")
})
public class KlientJPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "KodKrajuNadaniaTIN")
    private String kodKrajuNadaniaTIN;
    @Column(name = "NrKontrahenta")
    private String nrKontrahenta;
    @Column(name = "NazwaKontrahenta")
    private String nazwaKontrahenta;
    @Column(name = "DowodSprzedazy")
    private String dowodSprzedazy;
    @Column(name = "DataWystawienia")
    private String dataWystawienia;
    @Column(name = "DataSprzedazy")
    private String dataSprzedazy;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;

    public KlientJPK() {
    }

    
    public KlientJPK(Dok d, Podatnik podatnik, String rok, String mc) {
        this.dataSprzedazy = d.getDataSprz();
        this.dataWystawienia = d.getDataWyst();
        this.dowodSprzedazy = d.getNrWlDk();
        this.nrKontrahenta = "brak";
        this.nazwaKontrahenta = d.getKontr1().getNpelna();
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodKrajuNadaniaTIN() {
        return kodKrajuNadaniaTIN;
    }

    public void setKodKrajuNadaniaTIN(String kodKrajuNadaniaTIN) {
        this.kodKrajuNadaniaTIN = kodKrajuNadaniaTIN;
    }

    public String getNrKontrahenta() {
        return nrKontrahenta;
    }

    public void setNrKontrahenta(String nrKontrahenta) {
        this.nrKontrahenta = nrKontrahenta;
    }

    public String getNazwaKontrahenta() {
        return nazwaKontrahenta;
    }

    public void setNazwaKontrahenta(String nazwaKontrahenta) {
        this.nazwaKontrahenta = nazwaKontrahenta;
    }

    public String getDowodSprzedazy() {
        return dowodSprzedazy;
    }

    public void setDowodSprzedazy(String dowodSprzedazy) {
        this.dowodSprzedazy = dowodSprzedazy;
    }

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(String dataWystawienia) {
        this.dataWystawienia = dataWystawienia;
    }

    public String getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(String dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.dowodSprzedazy);
        hash = 67 * hash + Objects.hashCode(this.podatnik);
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
        final KlientJPK other = (KlientJPK) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwaKontrahenta, other.nazwaKontrahenta)) {
            return false;
        }
        if (!Objects.equals(this.dowodSprzedazy, other.dowodSprzedazy)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    

   
    
}
