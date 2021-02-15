/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "paragon_k", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParagonK.findAll", query = "SELECT p FROM ParagonK p"),
    @NamedQuery(name = "ParagonK.findByPakSerial", query = "SELECT p FROM ParagonK p WHERE p.pakSerial = :pakSerial"),
    @NamedQuery(name = "ParagonK.findByPakSysSerial", query = "SELECT p FROM ParagonK p WHERE p.pakSysSerial = :pakSysSerial"),
    @NamedQuery(name = "ParagonK.findByPakMiaSerial", query = "SELECT p FROM ParagonK p WHERE p.pakMiaSerial = :pakMiaSerial"),
    @NamedQuery(name = "ParagonK.findByPakMiaNazwa", query = "SELECT p FROM ParagonK p WHERE p.pakMiaNazwa = :pakMiaNazwa"),
    @NamedQuery(name = "ParagonK.findByPakKonNazwa", query = "SELECT p FROM ParagonK p WHERE p.pakKonNazwa = :pakKonNazwa"),
    @NamedQuery(name = "ParagonK.findByPakKonAdres", query = "SELECT p FROM ParagonK p WHERE p.pakKonAdres = :pakKonAdres"),
    @NamedQuery(name = "ParagonK.findByPakKonRegon", query = "SELECT p FROM ParagonK p WHERE p.pakKonRegon = :pakKonRegon"),
    @NamedQuery(name = "ParagonK.findByPakKonNip", query = "SELECT p FROM ParagonK p WHERE p.pakKonNip = :pakKonNip"),
    @NamedQuery(name = "ParagonK.findByPakKonPesel", query = "SELECT p FROM ParagonK p WHERE p.pakKonPesel = :pakKonPesel"),
    @NamedQuery(name = "ParagonK.findByPakKonNazwaSkr", query = "SELECT p FROM ParagonK p WHERE p.pakKonNazwaSkr = :pakKonNazwaSkr"),
    @NamedQuery(name = "ParagonK.findByPakKonNrId", query = "SELECT p FROM ParagonK p WHERE p.pakKonNrId = :pakKonNrId"),
    @NamedQuery(name = "ParagonK.findByPakForSerial", query = "SELECT p FROM ParagonK p WHERE p.pakForSerial = :pakForSerial"),
    @NamedQuery(name = "ParagonK.findByPakForOpis", query = "SELECT p FROM ParagonK p WHERE p.pakForOpis = :pakForOpis"),
    @NamedQuery(name = "ParagonK.findByPakChar1", query = "SELECT p FROM ParagonK p WHERE p.pakChar1 = :pakChar1"),
    @NamedQuery(name = "ParagonK.findByPakChar2", query = "SELECT p FROM ParagonK p WHERE p.pakChar2 = :pakChar2"),
    @NamedQuery(name = "ParagonK.findByPakVchar1", query = "SELECT p FROM ParagonK p WHERE p.pakVchar1 = :pakVchar1"),
    @NamedQuery(name = "ParagonK.findByPakVchar2", query = "SELECT p FROM ParagonK p WHERE p.pakVchar2 = :pakVchar2"),
    @NamedQuery(name = "ParagonK.findByPakDate1", query = "SELECT p FROM ParagonK p WHERE p.pakDate1 = :pakDate1"),
    @NamedQuery(name = "ParagonK.findByPakInt1", query = "SELECT p FROM ParagonK p WHERE p.pakInt1 = :pakInt1"),
    @NamedQuery(name = "ParagonK.findByPakNum1", query = "SELECT p FROM ParagonK p WHERE p.pakNum1 = :pakNum1")})
public class ParagonK implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "pak_serial", nullable = false)
    private Integer pakSerial;
    @Column(name = "pak_sys_serial")
    private Integer pakSysSerial;
    @Column(name = "pak_mia_serial")
    private Integer pakMiaSerial;
    @Size(max = 48)
    @Column(name = "pak_mia_nazwa", length = 48)
    private String pakMiaNazwa;
    @Size(max = 64)
    @Column(name = "pak_kon_nazwa", length = 64)
    private String pakKonNazwa;
    @Size(max = 128)
    @Column(name = "pak_kon_adres", length = 128)
    private String pakKonAdres;
    @Size(max = 14)
    @Column(name = "pak_kon_regon", length = 14)
    private String pakKonRegon;
    @Size(max = 14)
    @Column(name = "pak_kon_nip", length = 14)
    private String pakKonNip;
    @Size(max = 16)
    @Column(name = "pak_kon_pesel", length = 16)
    private String pakKonPesel;
    @Size(max = 32)
    @Column(name = "pak_kon_nazwa_skr", length = 32)
    private String pakKonNazwaSkr;
    @Column(name = "pak_kon_nr_id")
    private Integer pakKonNrId;
    @Column(name = "pak_for_serial")
    private Integer pakForSerial;
    @Size(max = 32)
    @Column(name = "pak_for_opis", length = 32)
    private String pakForOpis;
    @Column(name = "pak_char_1")
    private Character pakChar1;
    @Column(name = "pak_char_2")
    private Character pakChar2;
    @Size(max = 64)
    @Column(name = "pak_vchar_1", length = 64)
    private String pakVchar1;
    @Size(max = 64)
    @Column(name = "pak_vchar_2", length = 64)
    private String pakVchar2;
    @Column(name = "pak_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pakDate1;
    @Column(name = "pak_int_1")
    private Integer pakInt1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pak_num_1", precision = 17, scale = 6)
    private BigDecimal pakNum1;
    @JoinColumn(name = "pak_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent pakKonSerial;
    @JoinColumn(name = "pak_par_serial", referencedColumnName = "par_serial", nullable = false)
    @ManyToOne(optional = false)
    private Paragon pakParSerial;

    public ParagonK() {
    }

    public ParagonK(Integer pakSerial) {
        this.pakSerial = pakSerial;
    }

    public Integer getPakSerial() {
        return pakSerial;
    }

    public void setPakSerial(Integer pakSerial) {
        this.pakSerial = pakSerial;
    }

    public Integer getPakSysSerial() {
        return pakSysSerial;
    }

    public void setPakSysSerial(Integer pakSysSerial) {
        this.pakSysSerial = pakSysSerial;
    }

    public Integer getPakMiaSerial() {
        return pakMiaSerial;
    }

    public void setPakMiaSerial(Integer pakMiaSerial) {
        this.pakMiaSerial = pakMiaSerial;
    }

    public String getPakMiaNazwa() {
        return pakMiaNazwa;
    }

    public void setPakMiaNazwa(String pakMiaNazwa) {
        this.pakMiaNazwa = pakMiaNazwa;
    }

    public String getPakKonNazwa() {
        return pakKonNazwa;
    }

    public void setPakKonNazwa(String pakKonNazwa) {
        this.pakKonNazwa = pakKonNazwa;
    }

    public String getPakKonAdres() {
        return pakKonAdres;
    }

    public void setPakKonAdres(String pakKonAdres) {
        this.pakKonAdres = pakKonAdres;
    }

    public String getPakKonRegon() {
        return pakKonRegon;
    }

    public void setPakKonRegon(String pakKonRegon) {
        this.pakKonRegon = pakKonRegon;
    }

    public String getPakKonNip() {
        return pakKonNip;
    }

    public void setPakKonNip(String pakKonNip) {
        this.pakKonNip = pakKonNip;
    }

    public String getPakKonPesel() {
        return pakKonPesel;
    }

    public void setPakKonPesel(String pakKonPesel) {
        this.pakKonPesel = pakKonPesel;
    }

    public String getPakKonNazwaSkr() {
        return pakKonNazwaSkr;
    }

    public void setPakKonNazwaSkr(String pakKonNazwaSkr) {
        this.pakKonNazwaSkr = pakKonNazwaSkr;
    }

    public Integer getPakKonNrId() {
        return pakKonNrId;
    }

    public void setPakKonNrId(Integer pakKonNrId) {
        this.pakKonNrId = pakKonNrId;
    }

    public Integer getPakForSerial() {
        return pakForSerial;
    }

    public void setPakForSerial(Integer pakForSerial) {
        this.pakForSerial = pakForSerial;
    }

    public String getPakForOpis() {
        return pakForOpis;
    }

    public void setPakForOpis(String pakForOpis) {
        this.pakForOpis = pakForOpis;
    }

    public Character getPakChar1() {
        return pakChar1;
    }

    public void setPakChar1(Character pakChar1) {
        this.pakChar1 = pakChar1;
    }

    public Character getPakChar2() {
        return pakChar2;
    }

    public void setPakChar2(Character pakChar2) {
        this.pakChar2 = pakChar2;
    }

    public String getPakVchar1() {
        return pakVchar1;
    }

    public void setPakVchar1(String pakVchar1) {
        this.pakVchar1 = pakVchar1;
    }

    public String getPakVchar2() {
        return pakVchar2;
    }

    public void setPakVchar2(String pakVchar2) {
        this.pakVchar2 = pakVchar2;
    }

    public Date getPakDate1() {
        return pakDate1;
    }

    public void setPakDate1(Date pakDate1) {
        this.pakDate1 = pakDate1;
    }

    public Integer getPakInt1() {
        return pakInt1;
    }

    public void setPakInt1(Integer pakInt1) {
        this.pakInt1 = pakInt1;
    }

    public BigDecimal getPakNum1() {
        return pakNum1;
    }

    public void setPakNum1(BigDecimal pakNum1) {
        this.pakNum1 = pakNum1;
    }

    public Kontrahent getPakKonSerial() {
        return pakKonSerial;
    }

    public void setPakKonSerial(Kontrahent pakKonSerial) {
        this.pakKonSerial = pakKonSerial;
    }

    public Paragon getPakParSerial() {
        return pakParSerial;
    }

    public void setPakParSerial(Paragon pakParSerial) {
        this.pakParSerial = pakParSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pakSerial != null ? pakSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParagonK)) {
            return false;
        }
        ParagonK other = (ParagonK) object;
        if ((this.pakSerial == null && other.pakSerial != null) || (this.pakSerial != null && !this.pakSerial.equals(other.pakSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ParagonK[ pakSerial=" + pakSerial + " ]";
    }
    
}
