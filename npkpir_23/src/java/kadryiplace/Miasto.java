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
@Table(name = "miasto", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miasto.findAll", query = "SELECT m FROM Miasto m"),
    @NamedQuery(name = "Miasto.findByMiaSerial", query = "SELECT m FROM Miasto m WHERE m.miaSerial = :miaSerial"),
    @NamedQuery(name = "Miasto.findByMiaNazwa", query = "SELECT m FROM Miasto m WHERE m.miaNazwa = :miaNazwa")})
public class Miasto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mia_serial", nullable = false)
    private Integer miaSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 48)
    @Column(name = "mia_nazwa", nullable = false, length = 48)
    private String miaNazwa;
    @OneToMany(mappedBy = "firMiaSerial2")
    private List<Firma> firmaList;
    @OneToMany(mappedBy = "firMiaSerialK")
    private List<Firma> firmaList1;
    @OneToMany(mappedBy = "firMiaSerialD")
    private List<Firma> firmaList2;
    @OneToMany(mappedBy = "firMiaSerial")
    private List<Firma> firmaList3;
    @OneToMany(mappedBy = "konMiaSerial")
    private List<Kontrahent> kontrahentList;
    @OneToMany(mappedBy = "urzMiaSerial")
    private List<Urzad> urzadList;
    @OneToMany(mappedBy = "adhMiaSerial")
    private List<AdresHist> adresHistList;
    @OneToMany(mappedBy = "osoMiaSerial2")
    private List<Osoba> osobaList;
    @OneToMany(mappedBy = "osoMiaSerial")
    private List<Osoba> osobaList1;
    @OneToMany(mappedBy = "osrMiaSerial")
    private List<OsobaRod> osobaRodList;

    public Miasto() {
    }

    public Miasto(Integer miaSerial) {
        this.miaSerial = miaSerial;
    }

    public Miasto(Integer miaSerial, String miaNazwa) {
        this.miaSerial = miaSerial;
        this.miaNazwa = miaNazwa;
    }

    public Integer getMiaSerial() {
        return miaSerial;
    }

    public void setMiaSerial(Integer miaSerial) {
        this.miaSerial = miaSerial;
    }

    public String getMiaNazwa() {
        return miaNazwa;
    }

    public void setMiaNazwa(String miaNazwa) {
        this.miaNazwa = miaNazwa;
    }

    @XmlTransient
    public List<Firma> getFirmaList() {
        return firmaList;
    }

    public void setFirmaList(List<Firma> firmaList) {
        this.firmaList = firmaList;
    }

    @XmlTransient
    public List<Firma> getFirmaList1() {
        return firmaList1;
    }

    public void setFirmaList1(List<Firma> firmaList1) {
        this.firmaList1 = firmaList1;
    }

    @XmlTransient
    public List<Firma> getFirmaList2() {
        return firmaList2;
    }

    public void setFirmaList2(List<Firma> firmaList2) {
        this.firmaList2 = firmaList2;
    }

    @XmlTransient
    public List<Firma> getFirmaList3() {
        return firmaList3;
    }

    public void setFirmaList3(List<Firma> firmaList3) {
        this.firmaList3 = firmaList3;
    }

    @XmlTransient
    public List<Kontrahent> getKontrahentList() {
        return kontrahentList;
    }

    public void setKontrahentList(List<Kontrahent> kontrahentList) {
        this.kontrahentList = kontrahentList;
    }

    @XmlTransient
    public List<Urzad> getUrzadList() {
        return urzadList;
    }

    public void setUrzadList(List<Urzad> urzadList) {
        this.urzadList = urzadList;
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

    @XmlTransient
    public List<Osoba> getOsobaList1() {
        return osobaList1;
    }

    public void setOsobaList1(List<Osoba> osobaList1) {
        this.osobaList1 = osobaList1;
    }

    @XmlTransient
    public List<OsobaRod> getOsobaRodList() {
        return osobaRodList;
    }

    public void setOsobaRodList(List<OsobaRod> osobaRodList) {
        this.osobaRodList = osobaRodList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (miaSerial != null ? miaSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Miasto)) {
            return false;
        }
        Miasto other = (Miasto) object;
        if ((this.miaSerial == null && other.miaSerial != null) || (this.miaSerial != null && !this.miaSerial.equals(other.miaSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Miasto[ miaSerial=" + miaSerial + " ]";
    }
    
}
