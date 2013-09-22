/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "bazatest", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findBySymbol", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.symbol = :symbol"),
    @NamedQuery(name = "Dokfk.findByNrkolejny", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.nrkolejny = :nrkolejny"),
    @NamedQuery(name = "Dokfk.findByPodatnik", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik"),
    @NamedQuery(name = "Dokfk.findByOpisdokumentu", query = "SELECT d FROM Dokfk d WHERE d.opisdokumentu = :opisdokumentu")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DokfkPK dokfkPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String opisdokumentu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dokfk")
    private List<Wiersz> wierszList;
    

    public Dokfk() {
    }

    public Dokfk(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
    }

    public Dokfk(DokfkPK dokfkPK, String opisdokumentu) {
        this.dokfkPK = dokfkPK;
        this.opisdokumentu = opisdokumentu;
    }

    public Dokfk(String symbol, int nrkolejny, String podatnik) {
        this.dokfkPK = new DokfkPK(symbol, nrkolejny, podatnik);
    }

    public DokfkPK getDokfkPK() {
        return dokfkPK;
    }

    public void setDokfkPK(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
    }

    public String getOpisdokumentu() {
        return opisdokumentu;
    }

    public void setOpisdokumentu(String opisdokumentu) {
        this.opisdokumentu = opisdokumentu;
    }

    @XmlTransient
    public List<Wiersz> getWierszList() {
        return wierszList;
    }

    public void setWierszList(List<Wiersz> wierszList) {
        this.wierszList = wierszList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dokfkPK != null ? dokfkPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokfk)) {
            return false;
        }
        Dokfk other = (Dokfk) object;
        if ((this.dokfkPK == null && other.dokfkPK != null) || (this.dokfkPK != null && !this.dokfkPK.equals(other.dokfkPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dokfk[ dokfkPK=" + dokfkPK + " ]";
    }
    
}
