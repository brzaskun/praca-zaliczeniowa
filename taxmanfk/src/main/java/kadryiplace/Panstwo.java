/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "panstwo", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Panstwo.findAll", query = "SELECT p FROM Panstwo p"),
    @NamedQuery(name = "Panstwo.findByPanSerial", query = "SELECT p FROM Panstwo p WHERE p.panSerial = :panSerial"),
    @NamedQuery(name = "Panstwo.findByPanNazwa", query = "SELECT p FROM Panstwo p WHERE p.panNazwa = :panNazwa"),
    @NamedQuery(name = "Panstwo.findByPanSystem", query = "SELECT p FROM Panstwo p WHERE p.panSystem = :panSystem")})
public class Panstwo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pan_serial", nullable = false)
    private Integer panSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "pan_nazwa", nullable = false, length = 64)
    private String panNazwa;
    @Column(name = "pan_system")
    private Character panSystem;
    @OneToMany(mappedBy = "firPanSerial")
    private List<Firma> firmaList;
    @OneToMany(mappedBy = "konPanSerial")
    private List<Kontrahent> kontrahentList;
    @OneToMany(mappedBy = "adhPanSerial")
    private List<AdresHist> adresHistList;
    @OneToMany(mappedBy = "osoPanSerial")
    private List<Osoba> osobaList;

    public Panstwo() {
    }

    public Panstwo(Integer panSerial) {
        this.panSerial = panSerial;
    }

    public Panstwo(Integer panSerial, String panNazwa) {
        this.panSerial = panSerial;
        this.panNazwa = panNazwa;
    }

    public Integer getPanSerial() {
        return panSerial;
    }

    public void setPanSerial(Integer panSerial) {
        this.panSerial = panSerial;
    }

    public String getPanNazwa() {
        return panNazwa;
    }

    public void setPanNazwa(String panNazwa) {
        this.panNazwa = panNazwa;
    }

    public Character getPanSystem() {
        return panSystem;
    }

    public void setPanSystem(Character panSystem) {
        this.panSystem = panSystem;
    }

    @XmlTransient
    public List<Firma> getFirmaList() {
        return firmaList;
    }

    public void setFirmaList(List<Firma> firmaList) {
        this.firmaList = firmaList;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    @XmlTransient
    public List<AdresHist> getAdresHistList() {
        return adresHistList;
    }

    public void setAdresHistList(List<AdresHist> adresHistList) {
        this.adresHistList = adresHistList;
    }

    @XmlTransient
    public List<Osoba> getOsobaList() {
        return osobaList;
    }

    public void setOsobaList(List<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (panSerial != null ? panSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Panstwo)) {
            return false;
        }
        Panstwo other = (Panstwo) object;
        if ((this.panSerial == null && other.panSerial != null) || (this.panSerial != null && !this.panSerial.equals(other.panSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Panstwo[ panSerial=" + panSerial + " ]";
    }
    
}
