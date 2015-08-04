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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Fakturadodelementy.findAll", query = "SELECT f FROM Fakturadodelementy f"),
    @NamedQuery(name = "Fakturadodelementy.findByPodatnik", query = "SELECT f FROM Fakturadodelementy f WHERE f.fakturadodelementyPK.podatnik = :podatnik"),
    @NamedQuery(name = "Fakturadodelementy.findByNazwaelementu", query = "SELECT f FROM Fakturadodelementy f WHERE f.fakturadodelementyPK.nazwaelementu = :nazwaelementu"),
    @NamedQuery(name = "Fakturadodelementy.findByNazwaelementuPodatnik", query = "SELECT f FROM Fakturadodelementy f WHERE f.fakturadodelementyPK.nazwaelementu = :nazwaelementu AND f.fakturadodelementyPK.podatnik = :podatnik"),
    @NamedQuery(name = "Fakturadodelementy.findByAktywny", query = "SELECT f FROM Fakturadodelementy f WHERE f.aktywny = :aktywny")})
public class Fakturadodelementy implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FakturadodelementyPK fakturadodelementyPK;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column
    private String trescelementu;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean aktywny;

    public Fakturadodelementy() {
    }

    public Fakturadodelementy(FakturadodelementyPK fakturadodelementyPK) {
        this.fakturadodelementyPK = fakturadodelementyPK;
    }

    public Fakturadodelementy(FakturadodelementyPK fakturadodelementyPK, String trescelementu, boolean aktywny) {
        this.fakturadodelementyPK = fakturadodelementyPK;
        this.trescelementu = trescelementu;
        this.aktywny = aktywny;
    }

    public Fakturadodelementy(String podatnik, String nazwaelementu) {
        this.fakturadodelementyPK = new FakturadodelementyPK(podatnik, nazwaelementu);
    }

    public FakturadodelementyPK getFakturadodelementyPK() {
        return fakturadodelementyPK;
    }

    public void setFakturadodelementyPK(FakturadodelementyPK fakturadodelementyPK) {
        this.fakturadodelementyPK = fakturadodelementyPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fakturadodelementyPK != null ? fakturadodelementyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fakturadodelementy)) {
            return false;
        }
        Fakturadodelementy other = (Fakturadodelementy) object;
        if ((this.fakturadodelementyPK == null && other.fakturadodelementyPK != null) || (this.fakturadodelementyPK != null && !this.fakturadodelementyPK.equals(other.fakturadodelementyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fakturadodelementy[ fakturadodelementyPK=" + fakturadodelementyPK + " ]";
    }
    
}
