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
@Table(name = "sprzap", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprzap.findAll", query = "SELECT s FROM Sprzap s"),
    @NamedQuery(name = "Sprzap.findBySpzSerial", query = "SELECT s FROM Sprzap s WHERE s.spzSerial = :spzSerial"),
    @NamedQuery(name = "Sprzap.findBySpzData", query = "SELECT s FROM Sprzap s WHERE s.spzData = :spzData"),
    @NamedQuery(name = "Sprzap.findBySpzOpis", query = "SELECT s FROM Sprzap s WHERE s.spzOpis = :spzOpis"),
    @NamedQuery(name = "Sprzap.findBySpzKwota", query = "SELECT s FROM Sprzap s WHERE s.spzKwota = :spzKwota"),
    @NamedQuery(name = "Sprzap.findBySpzNumerDok", query = "SELECT s FROM Sprzap s WHERE s.spzNumerDok = :spzNumerDok"),
    @NamedQuery(name = "Sprzap.findBySpzTytul", query = "SELECT s FROM Sprzap s WHERE s.spzTytul = :spzTytul"),
    @NamedQuery(name = "Sprzap.findBySpzUwagi", query = "SELECT s FROM Sprzap s WHERE s.spzUwagi = :spzUwagi"),
    @NamedQuery(name = "Sprzap.findBySpzTyp", query = "SELECT s FROM Sprzap s WHERE s.spzTyp = :spzTyp"),
    @NamedQuery(name = "Sprzap.findBySpzChar1", query = "SELECT s FROM Sprzap s WHERE s.spzChar1 = :spzChar1"),
    @NamedQuery(name = "Sprzap.findBySpzChar2", query = "SELECT s FROM Sprzap s WHERE s.spzChar2 = :spzChar2"),
    @NamedQuery(name = "Sprzap.findBySpzChar3", query = "SELECT s FROM Sprzap s WHERE s.spzChar3 = :spzChar3"),
    @NamedQuery(name = "Sprzap.findBySpzChar4", query = "SELECT s FROM Sprzap s WHERE s.spzChar4 = :spzChar4"),
    @NamedQuery(name = "Sprzap.findBySpzNum1", query = "SELECT s FROM Sprzap s WHERE s.spzNum1 = :spzNum1"),
    @NamedQuery(name = "Sprzap.findBySpzNum2", query = "SELECT s FROM Sprzap s WHERE s.spzNum2 = :spzNum2"),
    @NamedQuery(name = "Sprzap.findBySpzData1", query = "SELECT s FROM Sprzap s WHERE s.spzData1 = :spzData1"),
    @NamedQuery(name = "Sprzap.findBySpzData2", query = "SELECT s FROM Sprzap s WHERE s.spzData2 = :spzData2"),
    @NamedQuery(name = "Sprzap.findBySpzVchar1", query = "SELECT s FROM Sprzap s WHERE s.spzVchar1 = :spzVchar1"),
    @NamedQuery(name = "Sprzap.findBySpzInt1", query = "SELECT s FROM Sprzap s WHERE s.spzInt1 = :spzInt1")})
public class Sprzap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "spz_serial", nullable = false)
    private Integer spzSerial;
    @Column(name = "spz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spzData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spz_opis", nullable = false, length = 64)
    private String spzOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "spz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal spzKwota;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "spz_numer_dok", nullable = false, length = 32)
    private String spzNumerDok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "spz_tytul", nullable = false, length = 128)
    private String spzTytul;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spz_uwagi", nullable = false, length = 64)
    private String spzUwagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spz_typ", nullable = false)
    private Character spzTyp;
    @Column(name = "spz_char_1")
    private Character spzChar1;
    @Column(name = "spz_char_2")
    private Character spzChar2;
    @Column(name = "spz_char_3")
    private Character spzChar3;
    @Column(name = "spz_char_4")
    private Character spzChar4;
    @Column(name = "spz_num_1", precision = 13, scale = 2)
    private BigDecimal spzNum1;
    @Column(name = "spz_num_2", precision = 13, scale = 2)
    private BigDecimal spzNum2;
    @Column(name = "spz_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spzData1;
    @Column(name = "spz_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spzData2;
    @Size(max = 64)
    @Column(name = "spz_vchar_1", length = 64)
    private String spzVchar1;
    @Column(name = "spz_int_1")
    private Integer spzInt1;
    @JoinColumn(name = "spz_fak_serial", referencedColumnName = "fak_serial")
    @ManyToOne
    private Fakrach spzFakSerial;
    @JoinColumn(name = "spz_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap spzForSerial;
    @JoinColumn(name = "spz_spr_serial", referencedColumnName = "spr_serial")
    @ManyToOne
    private Sprzedaz spzSprSerial;

    public Sprzap() {
    }

    public Sprzap(Integer spzSerial) {
        this.spzSerial = spzSerial;
    }

    public Sprzap(Integer spzSerial, String spzOpis, BigDecimal spzKwota, String spzNumerDok, String spzTytul, String spzUwagi, Character spzTyp) {
        this.spzSerial = spzSerial;
        this.spzOpis = spzOpis;
        this.spzKwota = spzKwota;
        this.spzNumerDok = spzNumerDok;
        this.spzTytul = spzTytul;
        this.spzUwagi = spzUwagi;
        this.spzTyp = spzTyp;
    }

    public Integer getSpzSerial() {
        return spzSerial;
    }

    public void setSpzSerial(Integer spzSerial) {
        this.spzSerial = spzSerial;
    }

    public Date getSpzData() {
        return spzData;
    }

    public void setSpzData(Date spzData) {
        this.spzData = spzData;
    }

    public String getSpzOpis() {
        return spzOpis;
    }

    public void setSpzOpis(String spzOpis) {
        this.spzOpis = spzOpis;
    }

    public BigDecimal getSpzKwota() {
        return spzKwota;
    }

    public void setSpzKwota(BigDecimal spzKwota) {
        this.spzKwota = spzKwota;
    }

    public String getSpzNumerDok() {
        return spzNumerDok;
    }

    public void setSpzNumerDok(String spzNumerDok) {
        this.spzNumerDok = spzNumerDok;
    }

    public String getSpzTytul() {
        return spzTytul;
    }

    public void setSpzTytul(String spzTytul) {
        this.spzTytul = spzTytul;
    }

    public String getSpzUwagi() {
        return spzUwagi;
    }

    public void setSpzUwagi(String spzUwagi) {
        this.spzUwagi = spzUwagi;
    }

    public Character getSpzTyp() {
        return spzTyp;
    }

    public void setSpzTyp(Character spzTyp) {
        this.spzTyp = spzTyp;
    }

    public Character getSpzChar1() {
        return spzChar1;
    }

    public void setSpzChar1(Character spzChar1) {
        this.spzChar1 = spzChar1;
    }

    public Character getSpzChar2() {
        return spzChar2;
    }

    public void setSpzChar2(Character spzChar2) {
        this.spzChar2 = spzChar2;
    }

    public Character getSpzChar3() {
        return spzChar3;
    }

    public void setSpzChar3(Character spzChar3) {
        this.spzChar3 = spzChar3;
    }

    public Character getSpzChar4() {
        return spzChar4;
    }

    public void setSpzChar4(Character spzChar4) {
        this.spzChar4 = spzChar4;
    }

    public BigDecimal getSpzNum1() {
        return spzNum1;
    }

    public void setSpzNum1(BigDecimal spzNum1) {
        this.spzNum1 = spzNum1;
    }

    public BigDecimal getSpzNum2() {
        return spzNum2;
    }

    public void setSpzNum2(BigDecimal spzNum2) {
        this.spzNum2 = spzNum2;
    }

    public Date getSpzData1() {
        return spzData1;
    }

    public void setSpzData1(Date spzData1) {
        this.spzData1 = spzData1;
    }

    public Date getSpzData2() {
        return spzData2;
    }

    public void setSpzData2(Date spzData2) {
        this.spzData2 = spzData2;
    }

    public String getSpzVchar1() {
        return spzVchar1;
    }

    public void setSpzVchar1(String spzVchar1) {
        this.spzVchar1 = spzVchar1;
    }

    public Integer getSpzInt1() {
        return spzInt1;
    }

    public void setSpzInt1(Integer spzInt1) {
        this.spzInt1 = spzInt1;
    }

    public Fakrach getSpzFakSerial() {
        return spzFakSerial;
    }

    public void setSpzFakSerial(Fakrach spzFakSerial) {
        this.spzFakSerial = spzFakSerial;
    }

    public Formyzap getSpzForSerial() {
        return spzForSerial;
    }

    public void setSpzForSerial(Formyzap spzForSerial) {
        this.spzForSerial = spzForSerial;
    }

    public Sprzedaz getSpzSprSerial() {
        return spzSprSerial;
    }

    public void setSpzSprSerial(Sprzedaz spzSprSerial) {
        this.spzSprSerial = spzSprSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spzSerial != null ? spzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprzap)) {
            return false;
        }
        Sprzap other = (Sprzap) object;
        if ((this.spzSerial == null && other.spzSerial != null) || (this.spzSerial != null && !this.spzSerial.equals(other.spzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Sprzap[ spzSerial=" + spzSerial + " ]";
    }
    
}
