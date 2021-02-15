/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dzial_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DzialHist.findAll", query = "SELECT d FROM DzialHist d"),
    @NamedQuery(name = "DzialHist.findByDzhSerial", query = "SELECT d FROM DzialHist d WHERE d.dzhSerial = :dzhSerial"),
    @NamedQuery(name = "DzialHist.findByDzhDataOd", query = "SELECT d FROM DzialHist d WHERE d.dzhDataOd = :dzhDataOd"),
    @NamedQuery(name = "DzialHist.findByDzhDataDo", query = "SELECT d FROM DzialHist d WHERE d.dzhDataDo = :dzhDataDo"),
    @NamedQuery(name = "DzialHist.findByDzhStatus", query = "SELECT d FROM DzialHist d WHERE d.dzhStatus = :dzhStatus"),
    @NamedQuery(name = "DzialHist.findByDzhDodChar1", query = "SELECT d FROM DzialHist d WHERE d.dzhDodChar1 = :dzhDodChar1"),
    @NamedQuery(name = "DzialHist.findByDzhTyp", query = "SELECT d FROM DzialHist d WHERE d.dzhTyp = :dzhTyp")})
public class DzialHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dzh_serial", nullable = false)
    private Integer dzhSerial;
    @Column(name = "dzh_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dzhDataOd;
    @Column(name = "dzh_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dzhDataDo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dzh_status", nullable = false)
    private Character dzhStatus;
    @Column(name = "dzh_dod_char_1")
    private Character dzhDodChar1;
    @Column(name = "dzh_typ")
    private Character dzhTyp;
    @JoinColumn(name = "dzh_dep_serial", referencedColumnName = "dep_serial", nullable = false)
    @ManyToOne(optional = false)
    private Dep dzhDepSerial;
    @JoinColumn(name = "dzh_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba dzhOsoSerial;

    public DzialHist() {
    }

    public DzialHist(Integer dzhSerial) {
        this.dzhSerial = dzhSerial;
    }

    public DzialHist(Integer dzhSerial, Character dzhStatus) {
        this.dzhSerial = dzhSerial;
        this.dzhStatus = dzhStatus;
    }

    public Integer getDzhSerial() {
        return dzhSerial;
    }

    public void setDzhSerial(Integer dzhSerial) {
        this.dzhSerial = dzhSerial;
    }

    public Date getDzhDataOd() {
        return dzhDataOd;
    }

    public void setDzhDataOd(Date dzhDataOd) {
        this.dzhDataOd = dzhDataOd;
    }

    public Date getDzhDataDo() {
        return dzhDataDo;
    }

    public void setDzhDataDo(Date dzhDataDo) {
        this.dzhDataDo = dzhDataDo;
    }

    public Character getDzhStatus() {
        return dzhStatus;
    }

    public void setDzhStatus(Character dzhStatus) {
        this.dzhStatus = dzhStatus;
    }

    public Character getDzhDodChar1() {
        return dzhDodChar1;
    }

    public void setDzhDodChar1(Character dzhDodChar1) {
        this.dzhDodChar1 = dzhDodChar1;
    }

    public Character getDzhTyp() {
        return dzhTyp;
    }

    public void setDzhTyp(Character dzhTyp) {
        this.dzhTyp = dzhTyp;
    }

    public Dep getDzhDepSerial() {
        return dzhDepSerial;
    }

    public void setDzhDepSerial(Dep dzhDepSerial) {
        this.dzhDepSerial = dzhDepSerial;
    }

    public Osoba getDzhOsoSerial() {
        return dzhOsoSerial;
    }

    public void setDzhOsoSerial(Osoba dzhOsoSerial) {
        this.dzhOsoSerial = dzhOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dzhSerial != null ? dzhSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DzialHist)) {
            return false;
        }
        DzialHist other = (DzialHist) object;
        if ((this.dzhSerial == null && other.dzhSerial != null) || (this.dzhSerial != null && !this.dzhSerial.equals(other.dzhSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DzialHist[ dzhSerial=" + dzhSerial + " ]";
    }
    
}
