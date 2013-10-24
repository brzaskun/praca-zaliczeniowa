/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
    @NamedQuery(name = "Dokfk.findBySeriadokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk"),
    @NamedQuery(name = "Dokfk.findByNrkolejny", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.nrkolejny = :nrkolejny"),
    @NamedQuery(name = "Dokfk.findByPodatnik", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik"),
    @NamedQuery(name = "Dokfk.findByDatawystawienia", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Dokfk.findByDatawystawieniaNumer", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia AND d.numer = :numer"),
    @NamedQuery(name = "Dokfk.findByNaniesionezapisy", query = "SELECT d FROM Dokfk d WHERE d.naniesionezapisy = :naniesionezapisy"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numer = :numer")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DokfkPK dokfkPK = new DokfkPK();
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "datawystawienia", nullable = false, length = 10)
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "naniesionezapisy", nullable = false)
    private boolean naniesionezapisy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "numer", nullable = false, length = 255)
    private String numer = "1/2013";
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dokfk", cascade = CascadeType.ALL,  orphanRemoval=true)
    @OrderBy("idporzadkowy")
    private List<Wiersze> konta;
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dokfk", cascade = CascadeType.ALL,  orphanRemoval=true)
//    @OrderBy("id")
//    private List<Kontozapisy> zapisynakoncie;
  
    
    
    public Dokfk() {
    }

    public Dokfk(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
    }

    public Dokfk(DokfkPK dokfkPK, String datawystawienia, boolean naniesionezapisy, String numer) {
        this.dokfkPK = dokfkPK;
        this.datawystawienia = datawystawienia;
        this.naniesionezapisy = naniesionezapisy;
        this.numer = numer;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Dokfk(String seriadokfk, int nrkolejny, String podatnik, String rok) {
        this.dokfkPK = new DokfkPK(seriadokfk, nrkolejny, podatnik, rok);
    }
    
    public DokfkPK getDokfkPK() {
        return dokfkPK;
    }
    
    public void setDokfkPK(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
    }
    
    public String getDatawystawienia() {
        return datawystawienia;
    }
    
    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }
    
    public boolean getNaniesionezapisy() {
        return naniesionezapisy;
    }
    
    public void setNaniesionezapisy(boolean naniesionezapisy) {
        this.naniesionezapisy = naniesionezapisy;
    }
    
    public String getNumer() {
        return numer;
    }
    
    public void setNumer(String numer) {
        this.numer = numer;
    }
    
    @XmlTransient
    public List<Wiersze> getKonta() {
        return konta;
    }
    
    public void setKonta(List<Wiersze> konta) {
        this.konta = konta;
    }

//    @XmlTransient
//    public List<Kontozapisy> getZapisynakoncie() {
//        return zapisynakoncie;
//    }
//
//    public void setZapisynakoncie(List<Kontozapisy> zapisynakoncie) {
//        this.zapisynakoncie = zapisynakoncie;
//    }
//    
    
    
    //</editor-fold>

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
        return "entityfk.Dokfk[ dokfkPK=" + dokfkPK + " ]";
    }

   
    
}
