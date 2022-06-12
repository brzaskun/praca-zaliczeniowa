/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "jezyk_list", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JezykList.findAll", query = "SELECT j FROM JezykList j"),
    @NamedQuery(name = "JezykList.findByJelSerial", query = "SELECT j FROM JezykList j WHERE j.jelSerial = :jelSerial"),
    @NamedQuery(name = "JezykList.findByJelNazwa", query = "SELECT j FROM JezykList j WHERE j.jelNazwa = :jelNazwa"),
    @NamedQuery(name = "JezykList.findByJelPoziom", query = "SELECT j FROM JezykList j WHERE j.jelPoziom = :jelPoziom"),
    @NamedQuery(name = "JezykList.findByJelDofin", query = "SELECT j FROM JezykList j WHERE j.jelDofin = :jelDofin"),
    @NamedQuery(name = "JezykList.findByJelDodChar1", query = "SELECT j FROM JezykList j WHERE j.jelDodChar1 = :jelDodChar1"),
    @NamedQuery(name = "JezykList.findByJelDodVchar1", query = "SELECT j FROM JezykList j WHERE j.jelDodVchar1 = :jelDodVchar1"),
    @NamedQuery(name = "JezykList.findByJelTyp", query = "SELECT j FROM JezykList j WHERE j.jelTyp = :jelTyp")})
public class JezykList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "jel_serial", nullable = false)
    private Integer jelSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "jel_nazwa", nullable = false, length = 64)
    private String jelNazwa;
    @Column(name = "jel_poziom")
    private Character jelPoziom;
    @Column(name = "jel_dofin")
    private Character jelDofin;
    @Column(name = "jel_dod_char_1")
    private Character jelDodChar1;
    @Size(max = 64)
    @Column(name = "jel_dod_vchar_1", length = 64)
    private String jelDodVchar1;
    @Column(name = "jel_typ")
    private Character jelTyp;
    @JoinColumn(name = "jel_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba jelOsoSerial;

    public JezykList() {
    }

    public JezykList(Integer jelSerial) {
        this.jelSerial = jelSerial;
    }

    public JezykList(Integer jelSerial, String jelNazwa) {
        this.jelSerial = jelSerial;
        this.jelNazwa = jelNazwa;
    }

    public Integer getJelSerial() {
        return jelSerial;
    }

    public void setJelSerial(Integer jelSerial) {
        this.jelSerial = jelSerial;
    }

    public String getJelNazwa() {
        return jelNazwa;
    }

    public void setJelNazwa(String jelNazwa) {
        this.jelNazwa = jelNazwa;
    }

    public Character getJelPoziom() {
        return jelPoziom;
    }

    public void setJelPoziom(Character jelPoziom) {
        this.jelPoziom = jelPoziom;
    }

    public Character getJelDofin() {
        return jelDofin;
    }

    public void setJelDofin(Character jelDofin) {
        this.jelDofin = jelDofin;
    }

    public Character getJelDodChar1() {
        return jelDodChar1;
    }

    public void setJelDodChar1(Character jelDodChar1) {
        this.jelDodChar1 = jelDodChar1;
    }

    public String getJelDodVchar1() {
        return jelDodVchar1;
    }

    public void setJelDodVchar1(String jelDodVchar1) {
        this.jelDodVchar1 = jelDodVchar1;
    }

    public Character getJelTyp() {
        return jelTyp;
    }

    public void setJelTyp(Character jelTyp) {
        this.jelTyp = jelTyp;
    }

    public Osoba getJelOsoSerial() {
        return jelOsoSerial;
    }

    public void setJelOsoSerial(Osoba jelOsoSerial) {
        this.jelOsoSerial = jelOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jelSerial != null ? jelSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JezykList)) {
            return false;
        }
        JezykList other = (JezykList) object;
        if ((this.jelSerial == null && other.jelSerial != null) || (this.jelSerial != null && !this.jelSerial.equals(other.jelSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.JezykList[ jelSerial=" + jelSerial + " ]";
    }
    
}
