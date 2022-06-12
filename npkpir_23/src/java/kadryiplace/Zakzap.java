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
@Table(name = "zakzap", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakzap.findAll", query = "SELECT z FROM Zakzap z"),
    @NamedQuery(name = "Zakzap.findByZazSerial", query = "SELECT z FROM Zakzap z WHERE z.zazSerial = :zazSerial"),
    @NamedQuery(name = "Zakzap.findByZazData", query = "SELECT z FROM Zakzap z WHERE z.zazData = :zazData"),
    @NamedQuery(name = "Zakzap.findByZazOpis", query = "SELECT z FROM Zakzap z WHERE z.zazOpis = :zazOpis"),
    @NamedQuery(name = "Zakzap.findByZazKwota", query = "SELECT z FROM Zakzap z WHERE z.zazKwota = :zazKwota"),
    @NamedQuery(name = "Zakzap.findByZazNumerDok", query = "SELECT z FROM Zakzap z WHERE z.zazNumerDok = :zazNumerDok"),
    @NamedQuery(name = "Zakzap.findByZazUwagi", query = "SELECT z FROM Zakzap z WHERE z.zazUwagi = :zazUwagi"),
    @NamedQuery(name = "Zakzap.findByZazTytul", query = "SELECT z FROM Zakzap z WHERE z.zazTytul = :zazTytul"),
    @NamedQuery(name = "Zakzap.findByZazTyp", query = "SELECT z FROM Zakzap z WHERE z.zazTyp = :zazTyp"),
    @NamedQuery(name = "Zakzap.findByZazChar1", query = "SELECT z FROM Zakzap z WHERE z.zazChar1 = :zazChar1"),
    @NamedQuery(name = "Zakzap.findByZazChar2", query = "SELECT z FROM Zakzap z WHERE z.zazChar2 = :zazChar2"),
    @NamedQuery(name = "Zakzap.findByZazChar3", query = "SELECT z FROM Zakzap z WHERE z.zazChar3 = :zazChar3"),
    @NamedQuery(name = "Zakzap.findByZazChar4", query = "SELECT z FROM Zakzap z WHERE z.zazChar4 = :zazChar4"),
    @NamedQuery(name = "Zakzap.findByZazNum1", query = "SELECT z FROM Zakzap z WHERE z.zazNum1 = :zazNum1"),
    @NamedQuery(name = "Zakzap.findByZazNum2", query = "SELECT z FROM Zakzap z WHERE z.zazNum2 = :zazNum2"),
    @NamedQuery(name = "Zakzap.findByZazDate1", query = "SELECT z FROM Zakzap z WHERE z.zazDate1 = :zazDate1"),
    @NamedQuery(name = "Zakzap.findByZazDate2", query = "SELECT z FROM Zakzap z WHERE z.zazDate2 = :zazDate2"),
    @NamedQuery(name = "Zakzap.findByZazVchar1", query = "SELECT z FROM Zakzap z WHERE z.zazVchar1 = :zazVchar1"),
    @NamedQuery(name = "Zakzap.findByZazInt1", query = "SELECT z FROM Zakzap z WHERE z.zazInt1 = :zazInt1")})
public class Zakzap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaz_serial", nullable = false)
    private Integer zazSerial;
    @Column(name = "zaz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zazData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "zaz_opis", nullable = false, length = 64)
    private String zazOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal zazKwota;
    @Size(max = 32)
    @Column(name = "zaz_numer_dok", length = 32)
    private String zazNumerDok;
    @Size(max = 128)
    @Column(name = "zaz_uwagi", length = 128)
    private String zazUwagi;
    @Size(max = 128)
    @Column(name = "zaz_tytul", length = 128)
    private String zazTytul;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zaz_typ", nullable = false)
    private Character zazTyp;
    @Column(name = "zaz_char_1")
    private Character zazChar1;
    @Column(name = "zaz_char_2")
    private Character zazChar2;
    @Column(name = "zaz_char_3")
    private Character zazChar3;
    @Column(name = "zaz_char_4")
    private Character zazChar4;
    @Column(name = "zaz_num_1", precision = 13, scale = 2)
    private BigDecimal zazNum1;
    @Column(name = "zaz_num_2", precision = 13, scale = 2)
    private BigDecimal zazNum2;
    @Column(name = "zaz_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zazDate1;
    @Column(name = "zaz_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zazDate2;
    @Size(max = 64)
    @Column(name = "zaz_vchar_1", length = 64)
    private String zazVchar1;
    @Column(name = "zaz_int_1")
    private Integer zazInt1;
    @JoinColumn(name = "zaz_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap zazForSerial;
    @JoinColumn(name = "zaz_mdo_serial", referencedColumnName = "mdo_serial")
    @ManyToOne
    private Magdok zazMdoSerial;
    @JoinColumn(name = "zaz_zak_serial", referencedColumnName = "zak_serial")
    @ManyToOne
    private Zakupy zazZakSerial;

    public Zakzap() {
    }

    public Zakzap(Integer zazSerial) {
        this.zazSerial = zazSerial;
    }

    public Zakzap(Integer zazSerial, String zazOpis, BigDecimal zazKwota, Character zazTyp) {
        this.zazSerial = zazSerial;
        this.zazOpis = zazOpis;
        this.zazKwota = zazKwota;
        this.zazTyp = zazTyp;
    }

    public Integer getZazSerial() {
        return zazSerial;
    }

    public void setZazSerial(Integer zazSerial) {
        this.zazSerial = zazSerial;
    }

    public Date getZazData() {
        return zazData;
    }

    public void setZazData(Date zazData) {
        this.zazData = zazData;
    }

    public String getZazOpis() {
        return zazOpis;
    }

    public void setZazOpis(String zazOpis) {
        this.zazOpis = zazOpis;
    }

    public BigDecimal getZazKwota() {
        return zazKwota;
    }

    public void setZazKwota(BigDecimal zazKwota) {
        this.zazKwota = zazKwota;
    }

    public String getZazNumerDok() {
        return zazNumerDok;
    }

    public void setZazNumerDok(String zazNumerDok) {
        this.zazNumerDok = zazNumerDok;
    }

    public String getZazUwagi() {
        return zazUwagi;
    }

    public void setZazUwagi(String zazUwagi) {
        this.zazUwagi = zazUwagi;
    }

    public String getZazTytul() {
        return zazTytul;
    }

    public void setZazTytul(String zazTytul) {
        this.zazTytul = zazTytul;
    }

    public Character getZazTyp() {
        return zazTyp;
    }

    public void setZazTyp(Character zazTyp) {
        this.zazTyp = zazTyp;
    }

    public Character getZazChar1() {
        return zazChar1;
    }

    public void setZazChar1(Character zazChar1) {
        this.zazChar1 = zazChar1;
    }

    public Character getZazChar2() {
        return zazChar2;
    }

    public void setZazChar2(Character zazChar2) {
        this.zazChar2 = zazChar2;
    }

    public Character getZazChar3() {
        return zazChar3;
    }

    public void setZazChar3(Character zazChar3) {
        this.zazChar3 = zazChar3;
    }

    public Character getZazChar4() {
        return zazChar4;
    }

    public void setZazChar4(Character zazChar4) {
        this.zazChar4 = zazChar4;
    }

    public BigDecimal getZazNum1() {
        return zazNum1;
    }

    public void setZazNum1(BigDecimal zazNum1) {
        this.zazNum1 = zazNum1;
    }

    public BigDecimal getZazNum2() {
        return zazNum2;
    }

    public void setZazNum2(BigDecimal zazNum2) {
        this.zazNum2 = zazNum2;
    }

    public Date getZazDate1() {
        return zazDate1;
    }

    public void setZazDate1(Date zazDate1) {
        this.zazDate1 = zazDate1;
    }

    public Date getZazDate2() {
        return zazDate2;
    }

    public void setZazDate2(Date zazDate2) {
        this.zazDate2 = zazDate2;
    }

    public String getZazVchar1() {
        return zazVchar1;
    }

    public void setZazVchar1(String zazVchar1) {
        this.zazVchar1 = zazVchar1;
    }

    public Integer getZazInt1() {
        return zazInt1;
    }

    public void setZazInt1(Integer zazInt1) {
        this.zazInt1 = zazInt1;
    }

    public Formyzap getZazForSerial() {
        return zazForSerial;
    }

    public void setZazForSerial(Formyzap zazForSerial) {
        this.zazForSerial = zazForSerial;
    }

    public Magdok getZazMdoSerial() {
        return zazMdoSerial;
    }

    public void setZazMdoSerial(Magdok zazMdoSerial) {
        this.zazMdoSerial = zazMdoSerial;
    }

    public Zakupy getZazZakSerial() {
        return zazZakSerial;
    }

    public void setZazZakSerial(Zakupy zazZakSerial) {
        this.zazZakSerial = zazZakSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zazSerial != null ? zazSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zakzap)) {
            return false;
        }
        Zakzap other = (Zakzap) object;
        if ((this.zazSerial == null && other.zazSerial != null) || (this.zazSerial != null && !this.zazSerial.equals(other.zazSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zakzap[ zazSerial=" + zazSerial + " ]";
    }
    
}
