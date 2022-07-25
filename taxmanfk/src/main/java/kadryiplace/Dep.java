/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "dep", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dep.findAll", query = "SELECT d FROM Dep d"),
    @NamedQuery(name = "Dep.findByDepSerial", query = "SELECT d FROM Dep d WHERE d.depSerial = :depSerial"),
    @NamedQuery(name = "Dep.findByDepOpis", query = "SELECT d FROM Dep d WHERE d.depOpis = :depOpis"),
    @NamedQuery(name = "Dep.findByDepTyp", query = "SELECT d FROM Dep d WHERE d.depTyp = :depTyp"),
    @NamedQuery(name = "Dep.findByDepKod1", query = "SELECT d FROM Dep d WHERE d.depKod1 = :depKod1"),
    @NamedQuery(name = "Dep.findByDepKod2", query = "SELECT d FROM Dep d WHERE d.depKod2 = :depKod2"),
    @NamedQuery(name = "Dep.findByDepKalendarz", query = "SELECT d FROM Dep d WHERE d.depKalendarz = :depKalendarz"),
    @NamedQuery(name = "Dep.findByDepChar1", query = "SELECT d FROM Dep d WHERE d.depChar1 = :depChar1"),
    @NamedQuery(name = "Dep.findByDepChar2", query = "SELECT d FROM Dep d WHERE d.depChar2 = :depChar2"),
    @NamedQuery(name = "Dep.findByDepChar3", query = "SELECT d FROM Dep d WHERE d.depChar3 = :depChar3"),
    @NamedQuery(name = "Dep.findByDepChar4", query = "SELECT d FROM Dep d WHERE d.depChar4 = :depChar4"),
    @NamedQuery(name = "Dep.findByDepVchar1", query = "SELECT d FROM Dep d WHERE d.depVchar1 = :depVchar1"),
    @NamedQuery(name = "Dep.findByDepVchar2", query = "SELECT d FROM Dep d WHERE d.depVchar2 = :depVchar2"),
    @NamedQuery(name = "Dep.findByDepNum1", query = "SELECT d FROM Dep d WHERE d.depNum1 = :depNum1"),
    @NamedQuery(name = "Dep.findByDepNum2", query = "SELECT d FROM Dep d WHERE d.depNum2 = :depNum2")})
public class Dep implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dep_serial", nullable = false)
    private Integer depSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "dep_opis", nullable = false, length = 64)
    private String depOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dep_typ", nullable = false)
    private Character depTyp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "dep_kod_1", nullable = false, length = 32)
    private String depKod1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "dep_kod_2", nullable = false, length = 32)
    private String depKod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dep_kalendarz", nullable = false)
    private Character depKalendarz;
    @Column(name = "dep_char_1")
    private Character depChar1;
    @Column(name = "dep_char_2")
    private Character depChar2;
    @Column(name = "dep_char_3")
    private Character depChar3;
    @Column(name = "dep_char_4")
    private Character depChar4;
    @Size(max = 64)
    @Column(name = "dep_vchar_1", length = 64)
    private String depVchar1;
    @Size(max = 64)
    @Column(name = "dep_vchar_2", length = 64)
    private String depVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dep_num_1", precision = 17, scale = 6)
    private BigDecimal depNum1;
    @Column(name = "dep_num_2", precision = 17, scale = 6)
    private BigDecimal depNum2;
    @JoinColumn(name = "dep_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma depFirSerial;
    @JoinColumn(name = "dep_pio_serial", referencedColumnName = "pio_serial")
    @ManyToOne
    private Pion depPioSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dzhDepSerial")
    private List<DzialHist> dzialHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tmpDepSerial")
    private List<TmpDzial> tmpDzialList;
    @OneToMany(mappedBy = "kldDepSerial")
    private List<Kalendarz> kalendarzList;
    @OneToMany(mappedBy = "osoDepSerial")
    private List<Osoba> osobaList;

    public Dep() {
    }

    public Dep(Integer depSerial) {
        this.depSerial = depSerial;
    }

    public Dep(Integer depSerial, String depOpis, Character depTyp, String depKod1, String depKod2, Character depKalendarz) {
        this.depSerial = depSerial;
        this.depOpis = depOpis;
        this.depTyp = depTyp;
        this.depKod1 = depKod1;
        this.depKod2 = depKod2;
        this.depKalendarz = depKalendarz;
    }

    public Integer getDepSerial() {
        return depSerial;
    }

    public void setDepSerial(Integer depSerial) {
        this.depSerial = depSerial;
    }

    public String getDepOpis() {
        return depOpis;
    }

    public void setDepOpis(String depOpis) {
        this.depOpis = depOpis;
    }

    public Character getDepTyp() {
        return depTyp;
    }

    public void setDepTyp(Character depTyp) {
        this.depTyp = depTyp;
    }

    public String getDepKod1() {
        return depKod1;
    }

    public void setDepKod1(String depKod1) {
        this.depKod1 = depKod1;
    }

    public String getDepKod2() {
        return depKod2;
    }

    public void setDepKod2(String depKod2) {
        this.depKod2 = depKod2;
    }

    public Character getDepKalendarz() {
        return depKalendarz;
    }

    public void setDepKalendarz(Character depKalendarz) {
        this.depKalendarz = depKalendarz;
    }

    public Character getDepChar1() {
        return depChar1;
    }

    public void setDepChar1(Character depChar1) {
        this.depChar1 = depChar1;
    }

    public Character getDepChar2() {
        return depChar2;
    }

    public void setDepChar2(Character depChar2) {
        this.depChar2 = depChar2;
    }

    public Character getDepChar3() {
        return depChar3;
    }

    public void setDepChar3(Character depChar3) {
        this.depChar3 = depChar3;
    }

    public Character getDepChar4() {
        return depChar4;
    }

    public void setDepChar4(Character depChar4) {
        this.depChar4 = depChar4;
    }

    public String getDepVchar1() {
        return depVchar1;
    }

    public void setDepVchar1(String depVchar1) {
        this.depVchar1 = depVchar1;
    }

    public String getDepVchar2() {
        return depVchar2;
    }

    public void setDepVchar2(String depVchar2) {
        this.depVchar2 = depVchar2;
    }

    public BigDecimal getDepNum1() {
        return depNum1;
    }

    public void setDepNum1(BigDecimal depNum1) {
        this.depNum1 = depNum1;
    }

    public BigDecimal getDepNum2() {
        return depNum2;
    }

    public void setDepNum2(BigDecimal depNum2) {
        this.depNum2 = depNum2;
    }

    public Firma getDepFirSerial() {
        return depFirSerial;
    }

    public void setDepFirSerial(Firma depFirSerial) {
        this.depFirSerial = depFirSerial;
    }

    public Pion getDepPioSerial() {
        return depPioSerial;
    }

    public void setDepPioSerial(Pion depPioSerial) {
        this.depPioSerial = depPioSerial;
    }

    @XmlTransient
    public List<DzialHist> getDzialHistList() {
        return dzialHistList;
    }

    public void setDzialHistList(List<DzialHist> dzialHistList) {
        this.dzialHistList = dzialHistList;
    }

    @XmlTransient
    public List<TmpDzial> getTmpDzialList() {
        return tmpDzialList;
    }

    public void setTmpDzialList(List<TmpDzial> tmpDzialList) {
        this.tmpDzialList = tmpDzialList;
    }

    @XmlTransient
    public List<Kalendarz> getKalendarzList() {
        return kalendarzList;
    }

    public void setKalendarzList(List<Kalendarz> kalendarzList) {
        this.kalendarzList = kalendarzList;
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
        hash += (depSerial != null ? depSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dep)) {
            return false;
        }
        Dep other = (Dep) object;
        if ((this.depSerial == null && other.depSerial != null) || (this.depSerial != null && !this.depSerial.equals(other.depSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Dep[ depSerial=" + depSerial + " ]";
    }
    
}
