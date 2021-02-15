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
@Table(name = "zasilki", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zasilki.findAll", query = "SELECT z FROM Zasilki z"),
    @NamedQuery(name = "Zasilki.findByZaiSerial", query = "SELECT z FROM Zasilki z WHERE z.zaiSerial = :zaiSerial"),
    @NamedQuery(name = "Zasilki.findByZaiRokOd", query = "SELECT z FROM Zasilki z WHERE z.zaiRokOd = :zaiRokOd"),
    @NamedQuery(name = "Zasilki.findByZaiMiesiacOd", query = "SELECT z FROM Zasilki z WHERE z.zaiMiesiacOd = :zaiMiesiacOd"),
    @NamedQuery(name = "Zasilki.findByZaiRokDo", query = "SELECT z FROM Zasilki z WHERE z.zaiRokDo = :zaiRokDo"),
    @NamedQuery(name = "Zasilki.findByZaiMiesiacDo", query = "SELECT z FROM Zasilki z WHERE z.zaiMiesiacDo = :zaiMiesiacDo"),
    @NamedQuery(name = "Zasilki.findByZaiRodz12", query = "SELECT z FROM Zasilki z WHERE z.zaiRodz12 = :zaiRodz12"),
    @NamedQuery(name = "Zasilki.findByZaiRodz3", query = "SELECT z FROM Zasilki z WHERE z.zaiRodz3 = :zaiRodz3"),
    @NamedQuery(name = "Zasilki.findByZaiRodz4", query = "SELECT z FROM Zasilki z WHERE z.zaiRodz4 = :zaiRodz4"),
    @NamedQuery(name = "Zasilki.findByZaiPiel", query = "SELECT z FROM Zasilki z WHERE z.zaiPiel = :zaiPiel"),
    @NamedQuery(name = "Zasilki.findByZaiWych", query = "SELECT z FROM Zasilki z WHERE z.zaiWych = :zaiWych"),
    @NamedQuery(name = "Zasilki.findByZaiWychSam", query = "SELECT z FROM Zasilki z WHERE z.zaiWychSam = :zaiWychSam"),
    @NamedQuery(name = "Zasilki.findByZaiDod1", query = "SELECT z FROM Zasilki z WHERE z.zaiDod1 = :zaiDod1"),
    @NamedQuery(name = "Zasilki.findByZaiDod2", query = "SELECT z FROM Zasilki z WHERE z.zaiDod2 = :zaiDod2"),
    @NamedQuery(name = "Zasilki.findByZaiDod3", query = "SELECT z FROM Zasilki z WHERE z.zaiDod3 = :zaiDod3"),
    @NamedQuery(name = "Zasilki.findByZaiDod4", query = "SELECT z FROM Zasilki z WHERE z.zaiDod4 = :zaiDod4"),
    @NamedQuery(name = "Zasilki.findByZaiDod5", query = "SELECT z FROM Zasilki z WHERE z.zaiDod5 = :zaiDod5"),
    @NamedQuery(name = "Zasilki.findByZaiDod6", query = "SELECT z FROM Zasilki z WHERE z.zaiDod6 = :zaiDod6"),
    @NamedQuery(name = "Zasilki.findByZaiDod7", query = "SELECT z FROM Zasilki z WHERE z.zaiDod7 = :zaiDod7"),
    @NamedQuery(name = "Zasilki.findByZaiDod8", query = "SELECT z FROM Zasilki z WHERE z.zaiDod8 = :zaiDod8"),
    @NamedQuery(name = "Zasilki.findByZaiDod9", query = "SELECT z FROM Zasilki z WHERE z.zaiDod9 = :zaiDod9"),
    @NamedQuery(name = "Zasilki.findByZaiDod10", query = "SELECT z FROM Zasilki z WHERE z.zaiDod10 = :zaiDod10"),
    @NamedQuery(name = "Zasilki.findByZaiTyp", query = "SELECT z FROM Zasilki z WHERE z.zaiTyp = :zaiTyp"),
    @NamedQuery(name = "Zasilki.findByZaiChar1", query = "SELECT z FROM Zasilki z WHERE z.zaiChar1 = :zaiChar1"),
    @NamedQuery(name = "Zasilki.findByZaiChar2", query = "SELECT z FROM Zasilki z WHERE z.zaiChar2 = :zaiChar2"),
    @NamedQuery(name = "Zasilki.findByZaiChar3", query = "SELECT z FROM Zasilki z WHERE z.zaiChar3 = :zaiChar3"),
    @NamedQuery(name = "Zasilki.findByZaiChar4", query = "SELECT z FROM Zasilki z WHERE z.zaiChar4 = :zaiChar4"),
    @NamedQuery(name = "Zasilki.findByZaiChar5", query = "SELECT z FROM Zasilki z WHERE z.zaiChar5 = :zaiChar5"),
    @NamedQuery(name = "Zasilki.findByZaiChar6", query = "SELECT z FROM Zasilki z WHERE z.zaiChar6 = :zaiChar6"),
    @NamedQuery(name = "Zasilki.findByZaiChar7", query = "SELECT z FROM Zasilki z WHERE z.zaiChar7 = :zaiChar7"),
    @NamedQuery(name = "Zasilki.findByZaiChar8", query = "SELECT z FROM Zasilki z WHERE z.zaiChar8 = :zaiChar8"),
    @NamedQuery(name = "Zasilki.findByZaiInt1", query = "SELECT z FROM Zasilki z WHERE z.zaiInt1 = :zaiInt1"),
    @NamedQuery(name = "Zasilki.findByZaiInt2", query = "SELECT z FROM Zasilki z WHERE z.zaiInt2 = :zaiInt2"),
    @NamedQuery(name = "Zasilki.findByZaiInt3", query = "SELECT z FROM Zasilki z WHERE z.zaiInt3 = :zaiInt3"),
    @NamedQuery(name = "Zasilki.findByZaiInt4", query = "SELECT z FROM Zasilki z WHERE z.zaiInt4 = :zaiInt4")})
public class Zasilki implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_serial", nullable = false)
    private Integer zaiSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_rok_od", nullable = false)
    private short zaiRokOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_miesiac_od", nullable = false)
    private short zaiMiesiacOd;
    @Column(name = "zai_rok_do")
    private Short zaiRokDo;
    @Column(name = "zai_miesiac_do")
    private Short zaiMiesiacDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_rodz_1_2", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiRodz12;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_rodz_3", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiRodz3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_rodz_4", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiRodz4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_piel", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiPiel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_wych", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiWych;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_wych_sam", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiWychSam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_1", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_2", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_3", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_4", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_5", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_6", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_7", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_8", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_9", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod9;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_dod_10", nullable = false, precision = 13, scale = 2)
    private BigDecimal zaiDod10;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "zai_typ", nullable = false, length = 2)
    private String zaiTyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_char_1", nullable = false)
    private Character zaiChar1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_char_2", nullable = false)
    private Character zaiChar2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_char_3", nullable = false)
    private Character zaiChar3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zai_char_4", nullable = false)
    private Character zaiChar4;
    @Column(name = "zai_char_5")
    private Character zaiChar5;
    @Column(name = "zai_char_6")
    private Character zaiChar6;
    @Column(name = "zai_char_7")
    private Character zaiChar7;
    @Column(name = "zai_char_8")
    private Character zaiChar8;
    @Column(name = "zai_int_1")
    private Integer zaiInt1;
    @Column(name = "zai_int_2")
    private Integer zaiInt2;
    @Column(name = "zai_int_3")
    private Integer zaiInt3;
    @Column(name = "zai_int_4")
    private Integer zaiInt4;

    public Zasilki() {
    }

    public Zasilki(Integer zaiSerial) {
        this.zaiSerial = zaiSerial;
    }

    public Zasilki(Integer zaiSerial, short zaiRokOd, short zaiMiesiacOd, BigDecimal zaiRodz12, BigDecimal zaiRodz3, BigDecimal zaiRodz4, BigDecimal zaiPiel, BigDecimal zaiWych, BigDecimal zaiWychSam, BigDecimal zaiDod1, BigDecimal zaiDod2, BigDecimal zaiDod3, BigDecimal zaiDod4, BigDecimal zaiDod5, BigDecimal zaiDod6, BigDecimal zaiDod7, BigDecimal zaiDod8, BigDecimal zaiDod9, BigDecimal zaiDod10, String zaiTyp, Character zaiChar1, Character zaiChar2, Character zaiChar3, Character zaiChar4) {
        this.zaiSerial = zaiSerial;
        this.zaiRokOd = zaiRokOd;
        this.zaiMiesiacOd = zaiMiesiacOd;
        this.zaiRodz12 = zaiRodz12;
        this.zaiRodz3 = zaiRodz3;
        this.zaiRodz4 = zaiRodz4;
        this.zaiPiel = zaiPiel;
        this.zaiWych = zaiWych;
        this.zaiWychSam = zaiWychSam;
        this.zaiDod1 = zaiDod1;
        this.zaiDod2 = zaiDod2;
        this.zaiDod3 = zaiDod3;
        this.zaiDod4 = zaiDod4;
        this.zaiDod5 = zaiDod5;
        this.zaiDod6 = zaiDod6;
        this.zaiDod7 = zaiDod7;
        this.zaiDod8 = zaiDod8;
        this.zaiDod9 = zaiDod9;
        this.zaiDod10 = zaiDod10;
        this.zaiTyp = zaiTyp;
        this.zaiChar1 = zaiChar1;
        this.zaiChar2 = zaiChar2;
        this.zaiChar3 = zaiChar3;
        this.zaiChar4 = zaiChar4;
    }

    public Integer getZaiSerial() {
        return zaiSerial;
    }

    public void setZaiSerial(Integer zaiSerial) {
        this.zaiSerial = zaiSerial;
    }

    public short getZaiRokOd() {
        return zaiRokOd;
    }

    public void setZaiRokOd(short zaiRokOd) {
        this.zaiRokOd = zaiRokOd;
    }

    public short getZaiMiesiacOd() {
        return zaiMiesiacOd;
    }

    public void setZaiMiesiacOd(short zaiMiesiacOd) {
        this.zaiMiesiacOd = zaiMiesiacOd;
    }

    public Short getZaiRokDo() {
        return zaiRokDo;
    }

    public void setZaiRokDo(Short zaiRokDo) {
        this.zaiRokDo = zaiRokDo;
    }

    public Short getZaiMiesiacDo() {
        return zaiMiesiacDo;
    }

    public void setZaiMiesiacDo(Short zaiMiesiacDo) {
        this.zaiMiesiacDo = zaiMiesiacDo;
    }

    public BigDecimal getZaiRodz12() {
        return zaiRodz12;
    }

    public void setZaiRodz12(BigDecimal zaiRodz12) {
        this.zaiRodz12 = zaiRodz12;
    }

    public BigDecimal getZaiRodz3() {
        return zaiRodz3;
    }

    public void setZaiRodz3(BigDecimal zaiRodz3) {
        this.zaiRodz3 = zaiRodz3;
    }

    public BigDecimal getZaiRodz4() {
        return zaiRodz4;
    }

    public void setZaiRodz4(BigDecimal zaiRodz4) {
        this.zaiRodz4 = zaiRodz4;
    }

    public BigDecimal getZaiPiel() {
        return zaiPiel;
    }

    public void setZaiPiel(BigDecimal zaiPiel) {
        this.zaiPiel = zaiPiel;
    }

    public BigDecimal getZaiWych() {
        return zaiWych;
    }

    public void setZaiWych(BigDecimal zaiWych) {
        this.zaiWych = zaiWych;
    }

    public BigDecimal getZaiWychSam() {
        return zaiWychSam;
    }

    public void setZaiWychSam(BigDecimal zaiWychSam) {
        this.zaiWychSam = zaiWychSam;
    }

    public BigDecimal getZaiDod1() {
        return zaiDod1;
    }

    public void setZaiDod1(BigDecimal zaiDod1) {
        this.zaiDod1 = zaiDod1;
    }

    public BigDecimal getZaiDod2() {
        return zaiDod2;
    }

    public void setZaiDod2(BigDecimal zaiDod2) {
        this.zaiDod2 = zaiDod2;
    }

    public BigDecimal getZaiDod3() {
        return zaiDod3;
    }

    public void setZaiDod3(BigDecimal zaiDod3) {
        this.zaiDod3 = zaiDod3;
    }

    public BigDecimal getZaiDod4() {
        return zaiDod4;
    }

    public void setZaiDod4(BigDecimal zaiDod4) {
        this.zaiDod4 = zaiDod4;
    }

    public BigDecimal getZaiDod5() {
        return zaiDod5;
    }

    public void setZaiDod5(BigDecimal zaiDod5) {
        this.zaiDod5 = zaiDod5;
    }

    public BigDecimal getZaiDod6() {
        return zaiDod6;
    }

    public void setZaiDod6(BigDecimal zaiDod6) {
        this.zaiDod6 = zaiDod6;
    }

    public BigDecimal getZaiDod7() {
        return zaiDod7;
    }

    public void setZaiDod7(BigDecimal zaiDod7) {
        this.zaiDod7 = zaiDod7;
    }

    public BigDecimal getZaiDod8() {
        return zaiDod8;
    }

    public void setZaiDod8(BigDecimal zaiDod8) {
        this.zaiDod8 = zaiDod8;
    }

    public BigDecimal getZaiDod9() {
        return zaiDod9;
    }

    public void setZaiDod9(BigDecimal zaiDod9) {
        this.zaiDod9 = zaiDod9;
    }

    public BigDecimal getZaiDod10() {
        return zaiDod10;
    }

    public void setZaiDod10(BigDecimal zaiDod10) {
        this.zaiDod10 = zaiDod10;
    }

    public String getZaiTyp() {
        return zaiTyp;
    }

    public void setZaiTyp(String zaiTyp) {
        this.zaiTyp = zaiTyp;
    }

    public Character getZaiChar1() {
        return zaiChar1;
    }

    public void setZaiChar1(Character zaiChar1) {
        this.zaiChar1 = zaiChar1;
    }

    public Character getZaiChar2() {
        return zaiChar2;
    }

    public void setZaiChar2(Character zaiChar2) {
        this.zaiChar2 = zaiChar2;
    }

    public Character getZaiChar3() {
        return zaiChar3;
    }

    public void setZaiChar3(Character zaiChar3) {
        this.zaiChar3 = zaiChar3;
    }

    public Character getZaiChar4() {
        return zaiChar4;
    }

    public void setZaiChar4(Character zaiChar4) {
        this.zaiChar4 = zaiChar4;
    }

    public Character getZaiChar5() {
        return zaiChar5;
    }

    public void setZaiChar5(Character zaiChar5) {
        this.zaiChar5 = zaiChar5;
    }

    public Character getZaiChar6() {
        return zaiChar6;
    }

    public void setZaiChar6(Character zaiChar6) {
        this.zaiChar6 = zaiChar6;
    }

    public Character getZaiChar7() {
        return zaiChar7;
    }

    public void setZaiChar7(Character zaiChar7) {
        this.zaiChar7 = zaiChar7;
    }

    public Character getZaiChar8() {
        return zaiChar8;
    }

    public void setZaiChar8(Character zaiChar8) {
        this.zaiChar8 = zaiChar8;
    }

    public Integer getZaiInt1() {
        return zaiInt1;
    }

    public void setZaiInt1(Integer zaiInt1) {
        this.zaiInt1 = zaiInt1;
    }

    public Integer getZaiInt2() {
        return zaiInt2;
    }

    public void setZaiInt2(Integer zaiInt2) {
        this.zaiInt2 = zaiInt2;
    }

    public Integer getZaiInt3() {
        return zaiInt3;
    }

    public void setZaiInt3(Integer zaiInt3) {
        this.zaiInt3 = zaiInt3;
    }

    public Integer getZaiInt4() {
        return zaiInt4;
    }

    public void setZaiInt4(Integer zaiInt4) {
        this.zaiInt4 = zaiInt4;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zaiSerial != null ? zaiSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zasilki)) {
            return false;
        }
        Zasilki other = (Zasilki) object;
        if ((this.zaiSerial == null && other.zaiSerial != null) || (this.zaiSerial != null && !this.zaiSerial.equals(other.zaiSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Zasilki[ zaiSerial=" + zaiSerial + " ]";
    }
    
}
