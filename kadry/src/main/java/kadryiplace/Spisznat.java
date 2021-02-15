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
@Table(name = "spisznat", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spisznat.findAll", query = "SELECT s FROM Spisznat s"),
    @NamedQuery(name = "Spisznat.findBySznSerial", query = "SELECT s FROM Spisznat s WHERE s.sznSerial = :sznSerial"),
    @NamedQuery(name = "Spisznat.findBySznPrzedmiot", query = "SELECT s FROM Spisznat s WHERE s.sznPrzedmiot = :sznPrzedmiot"),
    @NamedQuery(name = "Spisznat.findBySznCecha", query = "SELECT s FROM Spisznat s WHERE s.sznCecha = :sznCecha"),
    @NamedQuery(name = "Spisznat.findBySznJedSerial", query = "SELECT s FROM Spisznat s WHERE s.sznJedSerial = :sznJedSerial"),
    @NamedQuery(name = "Spisznat.findBySznIlosc", query = "SELECT s FROM Spisznat s WHERE s.sznIlosc = :sznIlosc"),
    @NamedQuery(name = "Spisznat.findBySznCenaJedn", query = "SELECT s FROM Spisznat s WHERE s.sznCenaJedn = :sznCenaJedn"),
    @NamedQuery(name = "Spisznat.findBySznCena", query = "SELECT s FROM Spisznat s WHERE s.sznCena = :sznCena"),
    @NamedQuery(name = "Spisznat.findBySznUwagi", query = "SELECT s FROM Spisznat s WHERE s.sznUwagi = :sznUwagi"),
    @NamedQuery(name = "Spisznat.findBySznCzas", query = "SELECT s FROM Spisznat s WHERE s.sznCzas = :sznCzas"),
    @NamedQuery(name = "Spisznat.findBySznUsrUserid", query = "SELECT s FROM Spisznat s WHERE s.sznUsrUserid = :sznUsrUserid"),
    @NamedQuery(name = "Spisznat.findBySznCzasMod", query = "SELECT s FROM Spisznat s WHERE s.sznCzasMod = :sznCzasMod"),
    @NamedQuery(name = "Spisznat.findBySznUsrUseridMod", query = "SELECT s FROM Spisznat s WHERE s.sznUsrUseridMod = :sznUsrUseridMod"),
    @NamedQuery(name = "Spisznat.findBySznLp", query = "SELECT s FROM Spisznat s WHERE s.sznLp = :sznLp"),
    @NamedQuery(name = "Spisznat.findBySznSkreslony", query = "SELECT s FROM Spisznat s WHERE s.sznSkreslony = :sznSkreslony"),
    @NamedQuery(name = "Spisznat.findBySznNum1", query = "SELECT s FROM Spisznat s WHERE s.sznNum1 = :sznNum1"),
    @NamedQuery(name = "Spisznat.findBySznNum2", query = "SELECT s FROM Spisznat s WHERE s.sznNum2 = :sznNum2"),
    @NamedQuery(name = "Spisznat.findBySznChar1", query = "SELECT s FROM Spisznat s WHERE s.sznChar1 = :sznChar1"),
    @NamedQuery(name = "Spisznat.findBySznChar2", query = "SELECT s FROM Spisznat s WHERE s.sznChar2 = :sznChar2"),
    @NamedQuery(name = "Spisznat.findBySznVchar1", query = "SELECT s FROM Spisznat s WHERE s.sznVchar1 = :sznVchar1"),
    @NamedQuery(name = "Spisznat.findBySznInt1", query = "SELECT s FROM Spisznat s WHERE s.sznInt1 = :sznInt1"),
    @NamedQuery(name = "Spisznat.findBySznTyp", query = "SELECT s FROM Spisznat s WHERE s.sznTyp = :sznTyp")})
public class Spisznat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "szn_serial", nullable = false)
    private Integer sznSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "szn_przedmiot", nullable = false, length = 64)
    private String sznPrzedmiot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "szn_cecha", nullable = false, length = 32)
    private String sznCecha;
    @Column(name = "szn_jed_serial")
    private Integer sznJedSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "szn_ilosc", precision = 13, scale = 6)
    private BigDecimal sznIlosc;
    @Column(name = "szn_cena_jedn", precision = 13, scale = 2)
    private BigDecimal sznCenaJedn;
    @Column(name = "szn_cena", precision = 13, scale = 2)
    private BigDecimal sznCena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "szn_uwagi", nullable = false, length = 64)
    private String sznUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "szn_czas", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sznCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "szn_usr_userid", nullable = false, length = 32)
    private String sznUsrUserid;
    @Column(name = "szn_czas_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sznCzasMod;
    @Size(max = 32)
    @Column(name = "szn_usr_userid_mod", length = 32)
    private String sznUsrUseridMod;
    @Column(name = "szn_lp")
    private Integer sznLp;
    @Column(name = "szn_skreslony")
    private Character sznSkreslony;
    @Column(name = "szn_num_1", precision = 17, scale = 6)
    private BigDecimal sznNum1;
    @Column(name = "szn_num_2", precision = 17, scale = 6)
    private BigDecimal sznNum2;
    @Column(name = "szn_char_1")
    private Character sznChar1;
    @Column(name = "szn_char_2")
    private Character sznChar2;
    @Size(max = 64)
    @Column(name = "szn_vchar_1", length = 64)
    private String sznVchar1;
    @Column(name = "szn_int_1")
    private Integer sznInt1;
    @Column(name = "szn_typ")
    private Character sznTyp;
    @JoinColumn(name = "szn_spi_serial", referencedColumnName = "spi_serial", nullable = false)
    @ManyToOne(optional = false)
    private Spisy sznSpiSerial;

    public Spisznat() {
    }

    public Spisznat(Integer sznSerial) {
        this.sznSerial = sznSerial;
    }

    public Spisznat(Integer sznSerial, String sznPrzedmiot, String sznCecha, String sznUwagi, Date sznCzas, String sznUsrUserid) {
        this.sznSerial = sznSerial;
        this.sznPrzedmiot = sznPrzedmiot;
        this.sznCecha = sznCecha;
        this.sznUwagi = sznUwagi;
        this.sznCzas = sznCzas;
        this.sznUsrUserid = sznUsrUserid;
    }

    public Integer getSznSerial() {
        return sznSerial;
    }

    public void setSznSerial(Integer sznSerial) {
        this.sznSerial = sznSerial;
    }

    public String getSznPrzedmiot() {
        return sznPrzedmiot;
    }

    public void setSznPrzedmiot(String sznPrzedmiot) {
        this.sznPrzedmiot = sznPrzedmiot;
    }

    public String getSznCecha() {
        return sznCecha;
    }

    public void setSznCecha(String sznCecha) {
        this.sznCecha = sznCecha;
    }

    public Integer getSznJedSerial() {
        return sznJedSerial;
    }

    public void setSznJedSerial(Integer sznJedSerial) {
        this.sznJedSerial = sznJedSerial;
    }

    public BigDecimal getSznIlosc() {
        return sznIlosc;
    }

    public void setSznIlosc(BigDecimal sznIlosc) {
        this.sznIlosc = sznIlosc;
    }

    public BigDecimal getSznCenaJedn() {
        return sznCenaJedn;
    }

    public void setSznCenaJedn(BigDecimal sznCenaJedn) {
        this.sznCenaJedn = sznCenaJedn;
    }

    public BigDecimal getSznCena() {
        return sznCena;
    }

    public void setSznCena(BigDecimal sznCena) {
        this.sznCena = sznCena;
    }

    public String getSznUwagi() {
        return sznUwagi;
    }

    public void setSznUwagi(String sznUwagi) {
        this.sznUwagi = sznUwagi;
    }

    public Date getSznCzas() {
        return sznCzas;
    }

    public void setSznCzas(Date sznCzas) {
        this.sznCzas = sznCzas;
    }

    public String getSznUsrUserid() {
        return sznUsrUserid;
    }

    public void setSznUsrUserid(String sznUsrUserid) {
        this.sznUsrUserid = sznUsrUserid;
    }

    public Date getSznCzasMod() {
        return sznCzasMod;
    }

    public void setSznCzasMod(Date sznCzasMod) {
        this.sznCzasMod = sznCzasMod;
    }

    public String getSznUsrUseridMod() {
        return sznUsrUseridMod;
    }

    public void setSznUsrUseridMod(String sznUsrUseridMod) {
        this.sznUsrUseridMod = sznUsrUseridMod;
    }

    public Integer getSznLp() {
        return sznLp;
    }

    public void setSznLp(Integer sznLp) {
        this.sznLp = sznLp;
    }

    public Character getSznSkreslony() {
        return sznSkreslony;
    }

    public void setSznSkreslony(Character sznSkreslony) {
        this.sznSkreslony = sznSkreslony;
    }

    public BigDecimal getSznNum1() {
        return sznNum1;
    }

    public void setSznNum1(BigDecimal sznNum1) {
        this.sznNum1 = sznNum1;
    }

    public BigDecimal getSznNum2() {
        return sznNum2;
    }

    public void setSznNum2(BigDecimal sznNum2) {
        this.sznNum2 = sznNum2;
    }

    public Character getSznChar1() {
        return sznChar1;
    }

    public void setSznChar1(Character sznChar1) {
        this.sznChar1 = sznChar1;
    }

    public Character getSznChar2() {
        return sznChar2;
    }

    public void setSznChar2(Character sznChar2) {
        this.sznChar2 = sznChar2;
    }

    public String getSznVchar1() {
        return sznVchar1;
    }

    public void setSznVchar1(String sznVchar1) {
        this.sznVchar1 = sznVchar1;
    }

    public Integer getSznInt1() {
        return sznInt1;
    }

    public void setSznInt1(Integer sznInt1) {
        this.sznInt1 = sznInt1;
    }

    public Character getSznTyp() {
        return sznTyp;
    }

    public void setSznTyp(Character sznTyp) {
        this.sznTyp = sznTyp;
    }

    public Spisy getSznSpiSerial() {
        return sznSpiSerial;
    }

    public void setSznSpiSerial(Spisy sznSpiSerial) {
        this.sznSpiSerial = sznSpiSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sznSerial != null ? sznSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spisznat)) {
            return false;
        }
        Spisznat other = (Spisznat) object;
        if ((this.sznSerial == null && other.sznSerial != null) || (this.sznSerial != null && !this.sznSerial.equals(other.sznSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Spisznat[ sznSerial=" + sznSerial + " ]";
    }
    
}
