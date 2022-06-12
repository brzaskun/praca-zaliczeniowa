/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "plan_grup", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanGrup.findAll", query = "SELECT p FROM PlanGrup p"),
    @NamedQuery(name = "PlanGrup.findByPlgSerial", query = "SELECT p FROM PlanGrup p WHERE p.plgSerial = :plgSerial"),
    @NamedQuery(name = "PlanGrup.findByPlgNazwa", query = "SELECT p FROM PlanGrup p WHERE p.plgNazwa = :plgNazwa"),
    @NamedQuery(name = "PlanGrup.findByPlgStBazowa", query = "SELECT p FROM PlanGrup p WHERE p.plgStBazowa = :plgStBazowa"),
    @NamedQuery(name = "PlanGrup.findByPlgProcWAkc", query = "SELECT p FROM PlanGrup p WHERE p.plgProcWAkc = :plgProcWAkc"),
    @NamedQuery(name = "PlanGrup.findByPlgTyp", query = "SELECT p FROM PlanGrup p WHERE p.plgTyp = :plgTyp"),
    @NamedQuery(name = "PlanGrup.findByPlgDodChar1", query = "SELECT p FROM PlanGrup p WHERE p.plgDodChar1 = :plgDodChar1"),
    @NamedQuery(name = "PlanGrup.findByPlgDodChar2", query = "SELECT p FROM PlanGrup p WHERE p.plgDodChar2 = :plgDodChar2"),
    @NamedQuery(name = "PlanGrup.findByPlgDodChar3", query = "SELECT p FROM PlanGrup p WHERE p.plgDodChar3 = :plgDodChar3"),
    @NamedQuery(name = "PlanGrup.findByPlgDodChar4", query = "SELECT p FROM PlanGrup p WHERE p.plgDodChar4 = :plgDodChar4"),
    @NamedQuery(name = "PlanGrup.findByPlgDodVchar1", query = "SELECT p FROM PlanGrup p WHERE p.plgDodVchar1 = :plgDodVchar1"),
    @NamedQuery(name = "PlanGrup.findByPlgDodVchar2", query = "SELECT p FROM PlanGrup p WHERE p.plgDodVchar2 = :plgDodVchar2"),
    @NamedQuery(name = "PlanGrup.findByPlgDodInt1", query = "SELECT p FROM PlanGrup p WHERE p.plgDodInt1 = :plgDodInt1"),
    @NamedQuery(name = "PlanGrup.findByPlgDodInt2", query = "SELECT p FROM PlanGrup p WHERE p.plgDodInt2 = :plgDodInt2"),
    @NamedQuery(name = "PlanGrup.findByPlgDodNum1", query = "SELECT p FROM PlanGrup p WHERE p.plgDodNum1 = :plgDodNum1"),
    @NamedQuery(name = "PlanGrup.findByPlgDodNum2", query = "SELECT p FROM PlanGrup p WHERE p.plgDodNum2 = :plgDodNum2")})
public class PlanGrup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "plg_serial", nullable = false)
    private Integer plgSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "plg_nazwa", nullable = false, length = 32)
    private String plgNazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "plg_st_bazowa", precision = 5, scale = 2)
    private BigDecimal plgStBazowa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "plg_proc_w_akc", nullable = false, length = 32)
    private String plgProcWAkc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "plg_typ", nullable = false)
    private Character plgTyp;
    @Column(name = "plg_dod_char_1")
    private Character plgDodChar1;
    @Column(name = "plg_dod_char_2")
    private Character plgDodChar2;
    @Column(name = "plg_dod_char_3")
    private Character plgDodChar3;
    @Column(name = "plg_dod_char_4")
    private Character plgDodChar4;
    @Size(max = 64)
    @Column(name = "plg_dod_vchar_1", length = 64)
    private String plgDodVchar1;
    @Size(max = 64)
    @Column(name = "plg_dod_vchar_2", length = 64)
    private String plgDodVchar2;
    @Column(name = "plg_dod_int_1")
    private Integer plgDodInt1;
    @Column(name = "plg_dod_int_2")
    private Integer plgDodInt2;
    @Column(name = "plg_dod_num_1", precision = 17, scale = 6)
    private BigDecimal plgDodNum1;
    @Column(name = "plg_dod_num_2", precision = 17, scale = 6)
    private BigDecimal plgDodNum2;
    @JoinColumn(name = "plg_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok plgRokSerial;

    public PlanGrup() {
    }

    public PlanGrup(Integer plgSerial) {
        this.plgSerial = plgSerial;
    }

    public PlanGrup(Integer plgSerial, String plgNazwa, String plgProcWAkc, Character plgTyp) {
        this.plgSerial = plgSerial;
        this.plgNazwa = plgNazwa;
        this.plgProcWAkc = plgProcWAkc;
        this.plgTyp = plgTyp;
    }

    public Integer getPlgSerial() {
        return plgSerial;
    }

    public void setPlgSerial(Integer plgSerial) {
        this.plgSerial = plgSerial;
    }

    public String getPlgNazwa() {
        return plgNazwa;
    }

    public void setPlgNazwa(String plgNazwa) {
        this.plgNazwa = plgNazwa;
    }

    public BigDecimal getPlgStBazowa() {
        return plgStBazowa;
    }

    public void setPlgStBazowa(BigDecimal plgStBazowa) {
        this.plgStBazowa = plgStBazowa;
    }

    public String getPlgProcWAkc() {
        return plgProcWAkc;
    }

    public void setPlgProcWAkc(String plgProcWAkc) {
        this.plgProcWAkc = plgProcWAkc;
    }

    public Character getPlgTyp() {
        return plgTyp;
    }

    public void setPlgTyp(Character plgTyp) {
        this.plgTyp = plgTyp;
    }

    public Character getPlgDodChar1() {
        return plgDodChar1;
    }

    public void setPlgDodChar1(Character plgDodChar1) {
        this.plgDodChar1 = plgDodChar1;
    }

    public Character getPlgDodChar2() {
        return plgDodChar2;
    }

    public void setPlgDodChar2(Character plgDodChar2) {
        this.plgDodChar2 = plgDodChar2;
    }

    public Character getPlgDodChar3() {
        return plgDodChar3;
    }

    public void setPlgDodChar3(Character plgDodChar3) {
        this.plgDodChar3 = plgDodChar3;
    }

    public Character getPlgDodChar4() {
        return plgDodChar4;
    }

    public void setPlgDodChar4(Character plgDodChar4) {
        this.plgDodChar4 = plgDodChar4;
    }

    public String getPlgDodVchar1() {
        return plgDodVchar1;
    }

    public void setPlgDodVchar1(String plgDodVchar1) {
        this.plgDodVchar1 = plgDodVchar1;
    }

    public String getPlgDodVchar2() {
        return plgDodVchar2;
    }

    public void setPlgDodVchar2(String plgDodVchar2) {
        this.plgDodVchar2 = plgDodVchar2;
    }

    public Integer getPlgDodInt1() {
        return plgDodInt1;
    }

    public void setPlgDodInt1(Integer plgDodInt1) {
        this.plgDodInt1 = plgDodInt1;
    }

    public Integer getPlgDodInt2() {
        return plgDodInt2;
    }

    public void setPlgDodInt2(Integer plgDodInt2) {
        this.plgDodInt2 = plgDodInt2;
    }

    public BigDecimal getPlgDodNum1() {
        return plgDodNum1;
    }

    public void setPlgDodNum1(BigDecimal plgDodNum1) {
        this.plgDodNum1 = plgDodNum1;
    }

    public BigDecimal getPlgDodNum2() {
        return plgDodNum2;
    }

    public void setPlgDodNum2(BigDecimal plgDodNum2) {
        this.plgDodNum2 = plgDodNum2;
    }

    public Rok getPlgRokSerial() {
        return plgRokSerial;
    }

    public void setPlgRokSerial(Rok plgRokSerial) {
        this.plgRokSerial = plgRokSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plgSerial != null ? plgSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanGrup)) {
            return false;
        }
        PlanGrup other = (PlanGrup) object;
        if ((this.plgSerial == null && other.plgSerial != null) || (this.plgSerial != null && !this.plgSerial.equals(other.plgSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlanGrup[ plgSerial=" + plgSerial + " ]";
    }
    
}
