/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fakturaelementygraficzne.findAll", query = "SELECT f FROM Fakturaelementygraficzne f"),
    @NamedQuery(name = "Fakturaelementygraficzne.findByPodatnik", query = "SELECT f FROM Fakturaelementygraficzne f WHERE f.fakturaelementygraficznePK.podatnik = :podatnik"),
    @NamedQuery(name = "Fakturaelementygraficzne.findByNazwaelementu", query = "SELECT f FROM Fakturaelementygraficzne f WHERE f.fakturaelementygraficznePK.nazwaelementu = :nazwaelementu"),
    @NamedQuery(name = "Fakturaelementygraficzne.findByAktywny", query = "SELECT f FROM Fakturaelementygraficzne f WHERE f.aktywny = :aktywny")
})
public class Fakturaelementygraficzne implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FakturaelementygraficznePK fakturaelementygraficznePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trescelementu")
    private String trescelementu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny",nullable = false)
    private boolean aktywny;
    @Column(name = "szerokosc")
    private String szerokosc;
    @Column(name = "wysokosc")
    private String wysokosc;

    public Fakturaelementygraficzne() {
        this.setFakturaelementygraficznePK(new FakturaelementygraficznePK());
    }

    public Fakturaelementygraficzne(FakturaelementygraficznePK fakturaelementygraficznePK) {
        this.fakturaelementygraficznePK = fakturaelementygraficznePK;
    }

    public Fakturaelementygraficzne(FakturaelementygraficznePK fakturaelementygraficznePK, String trescelementu, boolean aktywny) {
        this.fakturaelementygraficznePK = fakturaelementygraficznePK;
        this.trescelementu = trescelementu;
        this.aktywny = aktywny;
    }

    public Fakturaelementygraficzne(String podatnik, String nazwaelementu) {
        this.fakturaelementygraficznePK = new FakturaelementygraficznePK(podatnik, nazwaelementu);
    }

    public FakturaelementygraficznePK getFakturaelementygraficznePK() {
        return fakturaelementygraficznePK;
    }

    public void setFakturaelementygraficznePK(FakturaelementygraficznePK fakturaelementygraficznePK) {
        this.fakturaelementygraficznePK = fakturaelementygraficznePK;
    }

    public String getTrescelementu() {
        return trescelementu;
    }

    public void setTrescelementu(String trescelementu) {
        this.trescelementu = trescelementu;
    }

    public boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public String getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(String szerokosc) {
        this.szerokosc = szerokosc;
    }

    public String getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(String wysokosc) {
        this.wysokosc = wysokosc;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fakturaelementygraficznePK != null ? fakturaelementygraficznePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fakturaelementygraficzne)) {
            return false;
        }
        Fakturaelementygraficzne other = (Fakturaelementygraficzne) object;
        if ((this.fakturaelementygraficznePK == null && other.fakturaelementygraficznePK != null) || (this.fakturaelementygraficznePK != null && !this.fakturaelementygraficznePK.equals(other.fakturaelementygraficznePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fakturaelementygraficzne{" + "fakturaelementygraficznePK=" + fakturaelementygraficznePK + ", trescelementu=" + trescelementu + ", aktywny=" + aktywny + '}';
    }

   
    
}
