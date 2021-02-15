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
@Table(name = "pion", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pion.findAll", query = "SELECT p FROM Pion p"),
    @NamedQuery(name = "Pion.findByPioSerial", query = "SELECT p FROM Pion p WHERE p.pioSerial = :pioSerial"),
    @NamedQuery(name = "Pion.findByPioNazwa", query = "SELECT p FROM Pion p WHERE p.pioNazwa = :pioNazwa"),
    @NamedQuery(name = "Pion.findByPioKod1", query = "SELECT p FROM Pion p WHERE p.pioKod1 = :pioKod1"),
    @NamedQuery(name = "Pion.findByPioKod2", query = "SELECT p FROM Pion p WHERE p.pioKod2 = :pioKod2"),
    @NamedQuery(name = "Pion.findByPioKalendarz", query = "SELECT p FROM Pion p WHERE p.pioKalendarz = :pioKalendarz"),
    @NamedQuery(name = "Pion.findByPioTyp", query = "SELECT p FROM Pion p WHERE p.pioTyp = :pioTyp"),
    @NamedQuery(name = "Pion.findByPioChar1", query = "SELECT p FROM Pion p WHERE p.pioChar1 = :pioChar1"),
    @NamedQuery(name = "Pion.findByPioChar2", query = "SELECT p FROM Pion p WHERE p.pioChar2 = :pioChar2"),
    @NamedQuery(name = "Pion.findByPioChar3", query = "SELECT p FROM Pion p WHERE p.pioChar3 = :pioChar3"),
    @NamedQuery(name = "Pion.findByPioChar4", query = "SELECT p FROM Pion p WHERE p.pioChar4 = :pioChar4"),
    @NamedQuery(name = "Pion.findByPioVchar1", query = "SELECT p FROM Pion p WHERE p.pioVchar1 = :pioVchar1"),
    @NamedQuery(name = "Pion.findByPioVchar2", query = "SELECT p FROM Pion p WHERE p.pioVchar2 = :pioVchar2"),
    @NamedQuery(name = "Pion.findByPioNum1", query = "SELECT p FROM Pion p WHERE p.pioNum1 = :pioNum1"),
    @NamedQuery(name = "Pion.findByPioNum2", query = "SELECT p FROM Pion p WHERE p.pioNum2 = :pioNum2")})
public class Pion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pio_serial", nullable = false)
    private Integer pioSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "pio_nazwa", nullable = false, length = 64)
    private String pioNazwa;
    @Size(max = 16)
    @Column(name = "pio_kod_1", length = 16)
    private String pioKod1;
    @Size(max = 16)
    @Column(name = "pio_kod_2", length = 16)
    private String pioKod2;
    @Column(name = "pio_kalendarz")
    private Character pioKalendarz;
    @Column(name = "pio_typ")
    private Character pioTyp;
    @Column(name = "pio_char_1")
    private Character pioChar1;
    @Column(name = "pio_char_2")
    private Character pioChar2;
    @Column(name = "pio_char_3")
    private Character pioChar3;
    @Column(name = "pio_char_4")
    private Character pioChar4;
    @Size(max = 64)
    @Column(name = "pio_vchar_1", length = 64)
    private String pioVchar1;
    @Size(max = 64)
    @Column(name = "pio_vchar_2", length = 64)
    private String pioVchar2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pio_num_1", precision = 17, scale = 6)
    private BigDecimal pioNum1;
    @Column(name = "pio_num_2", precision = 17, scale = 6)
    private BigDecimal pioNum2;
    @OneToMany(mappedBy = "depPioSerial")
    private List<Dep> depList;
    @OneToMany(mappedBy = "kldPioSerial")
    private List<Kalendarz> kalendarzList;
    @JoinColumn(name = "pio_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma pioFirSerial;

    public Pion() {
    }

    public Pion(Integer pioSerial) {
        this.pioSerial = pioSerial;
    }

    public Pion(Integer pioSerial, String pioNazwa) {
        this.pioSerial = pioSerial;
        this.pioNazwa = pioNazwa;
    }

    public Integer getPioSerial() {
        return pioSerial;
    }

    public void setPioSerial(Integer pioSerial) {
        this.pioSerial = pioSerial;
    }

    public String getPioNazwa() {
        return pioNazwa;
    }

    public void setPioNazwa(String pioNazwa) {
        this.pioNazwa = pioNazwa;
    }

    public String getPioKod1() {
        return pioKod1;
    }

    public void setPioKod1(String pioKod1) {
        this.pioKod1 = pioKod1;
    }

    public String getPioKod2() {
        return pioKod2;
    }

    public void setPioKod2(String pioKod2) {
        this.pioKod2 = pioKod2;
    }

    public Character getPioKalendarz() {
        return pioKalendarz;
    }

    public void setPioKalendarz(Character pioKalendarz) {
        this.pioKalendarz = pioKalendarz;
    }

    public Character getPioTyp() {
        return pioTyp;
    }

    public void setPioTyp(Character pioTyp) {
        this.pioTyp = pioTyp;
    }

    public Character getPioChar1() {
        return pioChar1;
    }

    public void setPioChar1(Character pioChar1) {
        this.pioChar1 = pioChar1;
    }

    public Character getPioChar2() {
        return pioChar2;
    }

    public void setPioChar2(Character pioChar2) {
        this.pioChar2 = pioChar2;
    }

    public Character getPioChar3() {
        return pioChar3;
    }

    public void setPioChar3(Character pioChar3) {
        this.pioChar3 = pioChar3;
    }

    public Character getPioChar4() {
        return pioChar4;
    }

    public void setPioChar4(Character pioChar4) {
        this.pioChar4 = pioChar4;
    }

    public String getPioVchar1() {
        return pioVchar1;
    }

    public void setPioVchar1(String pioVchar1) {
        this.pioVchar1 = pioVchar1;
    }

    public String getPioVchar2() {
        return pioVchar2;
    }

    public void setPioVchar2(String pioVchar2) {
        this.pioVchar2 = pioVchar2;
    }

    public BigDecimal getPioNum1() {
        return pioNum1;
    }

    public void setPioNum1(BigDecimal pioNum1) {
        this.pioNum1 = pioNum1;
    }

    public BigDecimal getPioNum2() {
        return pioNum2;
    }

    public void setPioNum2(BigDecimal pioNum2) {
        this.pioNum2 = pioNum2;
    }

    @XmlTransient
    public List<Dep> getDepList() {
        return depList;
    }

    public void setDepList(List<Dep> depList) {
        this.depList = depList;
    }

    @XmlTransient
    public List<Kalendarz> getKalendarzList() {
        return kalendarzList;
    }

    public void setKalendarzList(List<Kalendarz> kalendarzList) {
        this.kalendarzList = kalendarzList;
    }

    public Firma getPioFirSerial() {
        return pioFirSerial;
    }

    public void setPioFirSerial(Firma pioFirSerial) {
        this.pioFirSerial = pioFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pioSerial != null ? pioSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pion)) {
            return false;
        }
        Pion other = (Pion) object;
        if ((this.pioSerial == null && other.pioSerial != null) || (this.pioSerial != null && !this.pioSerial.equals(other.pioSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Pion[ pioSerial=" + pioSerial + " ]";
    }
    
}
