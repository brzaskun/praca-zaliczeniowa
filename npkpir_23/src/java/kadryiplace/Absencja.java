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
@Table(name = "absencja", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Absencja.findAll", query = "SELECT a FROM Absencja a"),
    @NamedQuery(name = "Absencja.findByAbsSerial", query = "SELECT a FROM Absencja a WHERE a.absSerial = :absSerial"),
    @NamedQuery(name = "Absencja.findByAbsOpis", query = "SELECT a FROM Absencja a WHERE a.absOpis = :absOpis"),
    @NamedQuery(name = "Absencja.findByAbsSystem", query = "SELECT a FROM Absencja a WHERE a.absSystem = :absSystem"),
    @NamedQuery(name = "Absencja.findByAbsKolejnosc", query = "SELECT a FROM Absencja a WHERE a.absKolejnosc = :absKolejnosc"),
    @NamedQuery(name = "Absencja.findByAbsKod", query = "SELECT a FROM Absencja a WHERE a.absKod = :absKod"),
    @NamedQuery(name = "Absencja.findByAbsChar1", query = "SELECT a FROM Absencja a WHERE a.absChar1 = :absChar1"),
    @NamedQuery(name = "Absencja.findByAbsChar2", query = "SELECT a FROM Absencja a WHERE a.absChar2 = :absChar2"),
    @NamedQuery(name = "Absencja.findByAbsChar3", query = "SELECT a FROM Absencja a WHERE a.absChar3 = :absChar3"),
    @NamedQuery(name = "Absencja.findByAbsChar4", query = "SELECT a FROM Absencja a WHERE a.absChar4 = :absChar4"),
    @NamedQuery(name = "Absencja.findByAbsInt1", query = "SELECT a FROM Absencja a WHERE a.absInt1 = :absInt1"),
    @NamedQuery(name = "Absencja.findByAbsInt2", query = "SELECT a FROM Absencja a WHERE a.absInt2 = :absInt2"),
    @NamedQuery(name = "Absencja.findByAbsNum1", query = "SELECT a FROM Absencja a WHERE a.absNum1 = :absNum1"),
    @NamedQuery(name = "Absencja.findByAbsNum2", query = "SELECT a FROM Absencja a WHERE a.absNum2 = :absNum2"),
    @NamedQuery(name = "Absencja.findByAbsVchar1", query = "SELECT a FROM Absencja a WHERE a.absVchar1 = :absVchar1"),
    @NamedQuery(name = "Absencja.findByAbsVchar2", query = "SELECT a FROM Absencja a WHERE a.absVchar2 = :absVchar2"),
    @NamedQuery(name = "Absencja.findByAbsRedWyn", query = "SELECT a FROM Absencja a WHERE a.absRedWyn = :absRedWyn"),
    @NamedQuery(name = "Absencja.findByAbsChar5", query = "SELECT a FROM Absencja a WHERE a.absChar5 = :absChar5"),
    @NamedQuery(name = "Absencja.findByAbsChar6", query = "SELECT a FROM Absencja a WHERE a.absChar6 = :absChar6"),
    @NamedQuery(name = "Absencja.findByAbsChar7", query = "SELECT a FROM Absencja a WHERE a.absChar7 = :absChar7"),
    @NamedQuery(name = "Absencja.findByAbsChar8", query = "SELECT a FROM Absencja a WHERE a.absChar8 = :absChar8")})
public class Absencja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "abs_serial", nullable = false)
    private Integer absSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "abs_opis", nullable = false, length = 64)
    private String absOpis;
    @Column(name = "abs_system")
    private Character absSystem;
    @Column(name = "abs_kolejnosc")
    private Short absKolejnosc;
    @Column(name = "abs_kod")
    private Character absKod;
    @Column(name = "abs_char_1")
    private Character absChar1;
    @Column(name = "abs_char_2")
    private Character absChar2;
    @Column(name = "abs_char_3")
    private Character absChar3;
    @Column(name = "abs_char_4")
    private Character absChar4;
    @Column(name = "abs_int_1")
    private Integer absInt1;
    @Column(name = "abs_int_2")
    private Integer absInt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "abs_num_1", precision = 17, scale = 6)
    private BigDecimal absNum1;
    @Column(name = "abs_num_2", precision = 17, scale = 6)
    private BigDecimal absNum2;
    @Size(max = 64)
    @Column(name = "abs_vchar_1", length = 64)
    private String absVchar1;
    @Size(max = 64)
    @Column(name = "abs_vchar_2", length = 64)
    private String absVchar2;
    @Column(name = "abs_red_wyn")
    private Character absRedWyn;
    @Column(name = "abs_char_5")
    private Character absChar5;
    @Column(name = "abs_char_6")
    private Character absChar6;
    @Column(name = "abs_char_7")
    private Character absChar7;
    @Column(name = "abs_char_8")
    private Character absChar8;
    @OneToMany(mappedBy = "ospAbsSerial")
    private List<OsobaPrz> osobaPrzList;

    public Absencja() {
    }

    public Absencja(Integer absSerial) {
        this.absSerial = absSerial;
    }

    public Absencja(Integer absSerial, String absOpis) {
        this.absSerial = absSerial;
        this.absOpis = absOpis;
    }

    public Integer getAbsSerial() {
        return absSerial;
    }

    public void setAbsSerial(Integer absSerial) {
        this.absSerial = absSerial;
    }

    public String getAbsOpis() {
        return absOpis;
    }

    public void setAbsOpis(String absOpis) {
        this.absOpis = absOpis;
    }

    public Character getAbsSystem() {
        return absSystem;
    }

    public void setAbsSystem(Character absSystem) {
        this.absSystem = absSystem;
    }

    public Short getAbsKolejnosc() {
        return absKolejnosc;
    }

    public void setAbsKolejnosc(Short absKolejnosc) {
        this.absKolejnosc = absKolejnosc;
    }

    public Character getAbsKod() {
        return absKod;
    }

    public void setAbsKod(Character absKod) {
        this.absKod = absKod;
    }

    public Character getAbsChar1() {
        return absChar1;
    }

    public void setAbsChar1(Character absChar1) {
        this.absChar1 = absChar1;
    }

    public Character getAbsChar2() {
        return absChar2;
    }

    public void setAbsChar2(Character absChar2) {
        this.absChar2 = absChar2;
    }

    public Character getAbsChar3() {
        return absChar3;
    }

    public void setAbsChar3(Character absChar3) {
        this.absChar3 = absChar3;
    }

    public Character getAbsChar4() {
        return absChar4;
    }

    public void setAbsChar4(Character absChar4) {
        this.absChar4 = absChar4;
    }

    public Integer getAbsInt1() {
        return absInt1;
    }

    public void setAbsInt1(Integer absInt1) {
        this.absInt1 = absInt1;
    }

    public Integer getAbsInt2() {
        return absInt2;
    }

    public void setAbsInt2(Integer absInt2) {
        this.absInt2 = absInt2;
    }

    public BigDecimal getAbsNum1() {
        return absNum1;
    }

    public void setAbsNum1(BigDecimal absNum1) {
        this.absNum1 = absNum1;
    }

    public BigDecimal getAbsNum2() {
        return absNum2;
    }

    public void setAbsNum2(BigDecimal absNum2) {
        this.absNum2 = absNum2;
    }

    public String getAbsVchar1() {
        return absVchar1;
    }

    public void setAbsVchar1(String absVchar1) {
        this.absVchar1 = absVchar1;
    }

    public String getAbsVchar2() {
        return absVchar2;
    }

    public void setAbsVchar2(String absVchar2) {
        this.absVchar2 = absVchar2;
    }

    public Character getAbsRedWyn() {
        return absRedWyn;
    }

    public void setAbsRedWyn(Character absRedWyn) {
        this.absRedWyn = absRedWyn;
    }

    public Character getAbsChar5() {
        return absChar5;
    }

    public void setAbsChar5(Character absChar5) {
        this.absChar5 = absChar5;
    }

    public Character getAbsChar6() {
        return absChar6;
    }

    public void setAbsChar6(Character absChar6) {
        this.absChar6 = absChar6;
    }

    public Character getAbsChar7() {
        return absChar7;
    }

    public void setAbsChar7(Character absChar7) {
        this.absChar7 = absChar7;
    }

    public Character getAbsChar8() {
        return absChar8;
    }

    public void setAbsChar8(Character absChar8) {
        this.absChar8 = absChar8;
    }

    @XmlTransient
    public List<OsobaPrz> getOsobaPrzList() {
        return osobaPrzList;
    }

    public void setOsobaPrzList(List<OsobaPrz> osobaPrzList) {
        this.osobaPrzList = osobaPrzList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (absSerial != null ? absSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Absencja)) {
            return false;
        }
        Absencja other = (Absencja) object;
        if ((this.absSerial == null && other.absSerial != null) || (this.absSerial != null && !this.absSerial.equals(other.absSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Absencja[ absSerial=" + absSerial + " ]";
    }
    
}
