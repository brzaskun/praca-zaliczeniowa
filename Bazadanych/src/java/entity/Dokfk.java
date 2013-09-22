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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Dokfk.findByIddok", query = "SELECT d FROM Dokfk d WHERE d.iddok = :iddok"),
    @NamedQuery(name = "Dokfk.findByOpisdokumentu", query = "SELECT d FROM Dokfk d WHERE d.opisdokumentu = :opisdokumentu")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer iddok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String opisdokumentu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iddokumentuobcy")
    private List<Wiersz> wierszList;

    public Dokfk() {
    }

    public Dokfk(Integer iddok) {
        this.iddok = iddok;
    }

    public Dokfk(Integer iddok, String opisdokumentu) {
        this.iddok = iddok;
        this.opisdokumentu = opisdokumentu;
    }

    public Integer getIddok() {
        return iddok;
    }

    public void setIddok(Integer iddok) {
        this.iddok = iddok;
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
        hash += (iddok != null ? iddok.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokfk)) {
            return false;
        }
        Dokfk other = (Dokfk) object;
        if ((this.iddok == null && other.iddok != null) || (this.iddok != null && !this.iddok.equals(other.iddok))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Dokfk[ iddok=" + iddok + " ]";
    }
    
}
