/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.FKWiersz;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findById", query = "SELECT d FROM Dokfk d WHERE d.id = :id"),
    @NamedQuery(name = "Dokfk.findByDatawystawienia", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numer = :numer"),
    @NamedQuery(name = "Dokfk.findByNaniesionezapisy", query = "SELECT d FROM Dokfk d WHERE d.naniesionezapisy = :naniesionezapisy")})
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
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<FKWiersz> konta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String numer;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean naniesionezapisy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDokfk")
    private List<Kontozapisy> kontozapisyList;
   

    public Dokfk() {
    }

    public Dokfk(Integer id) {
        this.id = id;
    }

    public Dokfk(Integer id, String datawystawienia, List<FKWiersz> konta, String numer, boolean naniesionezapisy) {
        this.id = id;
        this.datawystawienia = datawystawienia;
        this.konta = konta;
        this.numer = numer;
        this.naniesionezapisy = naniesionezapisy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public List<FKWiersz> getKonta() {
        return konta;
    }

    public void setKonta(List<FKWiersz> konta) {
        this.konta = konta;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public boolean getNaniesionezapisy() {
        return naniesionezapisy;
    }

    public void setNaniesionezapisy(boolean naniesionezapisy) {
        this.naniesionezapisy = naniesionezapisy;
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

    @XmlTransient
    public List<Kontozapisy> getKontozapisyList() {
        return kontozapisyList;
    }

    public void setKontozapisyList(List<Kontozapisy> kontozapisyList) {
        this.kontozapisyList = kontozapisyList;
    }

   
}
