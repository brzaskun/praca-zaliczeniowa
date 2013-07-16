/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.FKWiersz;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findById", query = "SELECT d FROM Dokfk d WHERE d.id = :id"),
    @NamedQuery(name = "Dokfk.findByData", query = "SELECT d FROM Dokfk d WHERE d.data = :data"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numer = :numer")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String data;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String numer;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<FKWiersz> konta;

    public Dokfk() {
    }

    public Dokfk(Integer id) {
        this.id = id;
    }

    public Dokfk(Integer id, String data, String numer, List<FKWiersz> konta) {
        this.id = id;
        this.data = data;
        this.numer = numer;
        this.konta = konta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public List<FKWiersz> getKonta() {
        return konta;
    }

    public void setKonta(List<FKWiersz> konta) {
        this.konta = konta;
    }

  

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokfk)) {
            return false;
        }
        Dokfk other = (Dokfk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Dokfk[ id=" + id + " ]";
    }
    
}
