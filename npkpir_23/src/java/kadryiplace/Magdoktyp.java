/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "magdoktyp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magdoktyp.findAll", query = "SELECT m FROM Magdoktyp m"),
    @NamedQuery(name = "Magdoktyp.findByMdtSerial", query = "SELECT m FROM Magdoktyp m WHERE m.mdtSerial = :mdtSerial"),
    @NamedQuery(name = "Magdoktyp.findByMdtOpis", query = "SELECT m FROM Magdoktyp m WHERE m.mdtOpis = :mdtOpis"),
    @NamedQuery(name = "Magdoktyp.findByMdtKolejnosc", query = "SELECT m FROM Magdoktyp m WHERE m.mdtKolejnosc = :mdtKolejnosc"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrDef", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrDef = :mdtNrDef"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrData", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrData = :mdtNrData"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrDataP", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrDataP = :mdtNrDataP"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrLp", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrLp = :mdtNrLp"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrLpP", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrLpP = :mdtNrLpP"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrStr", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrStr = :mdtNrStr"),
    @NamedQuery(name = "Magdoktyp.findByMdtNrStrP", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNrStrP = :mdtNrStrP"),
    @NamedQuery(name = "Magdoktyp.findByMdtTyp", query = "SELECT m FROM Magdoktyp m WHERE m.mdtTyp = :mdtTyp"),
    @NamedQuery(name = "Magdoktyp.findByMdtLogo", query = "SELECT m FROM Magdoktyp m WHERE m.mdtLogo = :mdtLogo"),
    @NamedQuery(name = "Magdoktyp.findByMdtCeny", query = "SELECT m FROM Magdoktyp m WHERE m.mdtCeny = :mdtCeny"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar1", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar1 = :mdtChar1"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar2", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar2 = :mdtChar2"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar3", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar3 = :mdtChar3"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar4", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar4 = :mdtChar4"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar5", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar5 = :mdtChar5"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar6", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar6 = :mdtChar6"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar7", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar7 = :mdtChar7"),
    @NamedQuery(name = "Magdoktyp.findByMdtChar8", query = "SELECT m FROM Magdoktyp m WHERE m.mdtChar8 = :mdtChar8"),
    @NamedQuery(name = "Magdoktyp.findByMdtNum1", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNum1 = :mdtNum1"),
    @NamedQuery(name = "Magdoktyp.findByMdtNum2", query = "SELECT m FROM Magdoktyp m WHERE m.mdtNum2 = :mdtNum2"),
    @NamedQuery(name = "Magdoktyp.findByMdtData1", query = "SELECT m FROM Magdoktyp m WHERE m.mdtData1 = :mdtData1"),
    @NamedQuery(name = "Magdoktyp.findByMdtData2", query = "SELECT m FROM Magdoktyp m WHERE m.mdtData2 = :mdtData2"),
    @NamedQuery(name = "Magdoktyp.findByMdtVchar1", query = "SELECT m FROM Magdoktyp m WHERE m.mdtVchar1 = :mdtVchar1"),
    @NamedQuery(name = "Magdoktyp.findByMdtVchar2", query = "SELECT m FROM Magdoktyp m WHERE m.mdtVchar2 = :mdtVchar2"),
    @NamedQuery(name = "Magdoktyp.findByMdtVchar3", query = "SELECT m FROM Magdoktyp m WHERE m.mdtVchar3 = :mdtVchar3"),
    @NamedQuery(name = "Magdoktyp.findByMdtVchar4", query = "SELECT m FROM Magdoktyp m WHERE m.mdtVchar4 = :mdtVchar4"),
    @NamedQuery(name = "Magdoktyp.findByMdtInt1", query = "SELECT m FROM Magdoktyp m WHERE m.mdtInt1 = :mdtInt1"),
    @NamedQuery(name = "Magdoktyp.findByMdtInt2", query = "SELECT m FROM Magdoktyp m WHERE m.mdtInt2 = :mdtInt2"),
    @NamedQuery(name = "Magdoktyp.findByMdtInt3", query = "SELECT m FROM Magdoktyp m WHERE m.mdtInt3 = :mdtInt3"),
    @NamedQuery(name = "Magdoktyp.findByMdtInt4", query = "SELECT m FROM Magdoktyp m WHERE m.mdtInt4 = :mdtInt4")})
public class Magdoktyp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_serial", nullable = false)
    private Integer mdtSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "mdt_opis", nullable = false, length = 64)
    private String mdtOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_kolejnosc", nullable = false)
    private short mdtKolejnosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_nr_def", nullable = false)
    private Character mdtNrDef;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "mdt_nr_data", nullable = false, length = 10)
    private String mdtNrData;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_nr_data_p", nullable = false)
    private short mdtNrDataP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "mdt_nr_lp", nullable = false, length = 10)
    private String mdtNrLp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_nr_lp_p", nullable = false)
    private short mdtNrLpP;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "mdt_nr_str", nullable = false, length = 10)
    private String mdtNrStr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mdt_nr_str_p", nullable = false)
    private short mdtNrStrP;
    @Column(name = "mdt_typ")
    private Character mdtTyp;
    @Column(name = "mdt_logo")
    private Character mdtLogo;
    @Column(name = "mdt_ceny")
    private Character mdtCeny;
    @Column(name = "mdt_char_1")
    private Character mdtChar1;
    @Column(name = "mdt_char_2")
    private Character mdtChar2;
    @Column(name = "mdt_char_3")
    private Character mdtChar3;
    @Column(name = "mdt_char_4")
    private Character mdtChar4;
    @Column(name = "mdt_char_5")
    private Character mdtChar5;
    @Column(name = "mdt_char_6")
    private Character mdtChar6;
    @Column(name = "mdt_char_7")
    private Character mdtChar7;
    @Column(name = "mdt_char_8")
    private Character mdtChar8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mdt_num_1", precision = 17, scale = 6)
    private BigDecimal mdtNum1;
    @Column(name = "mdt_num_2", precision = 17, scale = 6)
    private BigDecimal mdtNum2;
    @Column(name = "mdt_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdtData1;
    @Column(name = "mdt_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mdtData2;
    @Size(max = 64)
    @Column(name = "mdt_vchar_1", length = 64)
    private String mdtVchar1;
    @Size(max = 64)
    @Column(name = "mdt_vchar_2", length = 64)
    private String mdtVchar2;
    @Size(max = 64)
    @Column(name = "mdt_vchar_3", length = 64)
    private String mdtVchar3;
    @Size(max = 64)
    @Column(name = "mdt_vchar_4", length = 64)
    private String mdtVchar4;
    @Column(name = "mdt_int_1")
    private Integer mdtInt1;
    @Column(name = "mdt_int_2")
    private Integer mdtInt2;
    @Column(name = "mdt_int_3")
    private Integer mdtInt3;
    @Column(name = "mdt_int_4")
    private Integer mdtInt4;
    @JoinColumn(name = "mdt_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma mdtFirSerial;
    @OneToMany(mappedBy = "mdoMdtSerial")
    private List<Magdok> magdokList;

    public Magdoktyp() {
    }

    public Magdoktyp(Integer mdtSerial) {
        this.mdtSerial = mdtSerial;
    }

    public Magdoktyp(Integer mdtSerial, String mdtOpis, short mdtKolejnosc, Character mdtNrDef, String mdtNrData, short mdtNrDataP, String mdtNrLp, short mdtNrLpP, String mdtNrStr, short mdtNrStrP) {
        this.mdtSerial = mdtSerial;
        this.mdtOpis = mdtOpis;
        this.mdtKolejnosc = mdtKolejnosc;
        this.mdtNrDef = mdtNrDef;
        this.mdtNrData = mdtNrData;
        this.mdtNrDataP = mdtNrDataP;
        this.mdtNrLp = mdtNrLp;
        this.mdtNrLpP = mdtNrLpP;
        this.mdtNrStr = mdtNrStr;
        this.mdtNrStrP = mdtNrStrP;
    }

    public Integer getMdtSerial() {
        return mdtSerial;
    }

    public void setMdtSerial(Integer mdtSerial) {
        this.mdtSerial = mdtSerial;
    }

    public String getMdtOpis() {
        return mdtOpis;
    }

    public void setMdtOpis(String mdtOpis) {
        this.mdtOpis = mdtOpis;
    }

    public short getMdtKolejnosc() {
        return mdtKolejnosc;
    }

    public void setMdtKolejnosc(short mdtKolejnosc) {
        this.mdtKolejnosc = mdtKolejnosc;
    }

    public Character getMdtNrDef() {
        return mdtNrDef;
    }

    public void setMdtNrDef(Character mdtNrDef) {
        this.mdtNrDef = mdtNrDef;
    }

    public String getMdtNrData() {
        return mdtNrData;
    }

    public void setMdtNrData(String mdtNrData) {
        this.mdtNrData = mdtNrData;
    }

    public short getMdtNrDataP() {
        return mdtNrDataP;
    }

    public void setMdtNrDataP(short mdtNrDataP) {
        this.mdtNrDataP = mdtNrDataP;
    }

    public String getMdtNrLp() {
        return mdtNrLp;
    }

    public void setMdtNrLp(String mdtNrLp) {
        this.mdtNrLp = mdtNrLp;
    }

    public short getMdtNrLpP() {
        return mdtNrLpP;
    }

    public void setMdtNrLpP(short mdtNrLpP) {
        this.mdtNrLpP = mdtNrLpP;
    }

    public String getMdtNrStr() {
        return mdtNrStr;
    }

    public void setMdtNrStr(String mdtNrStr) {
        this.mdtNrStr = mdtNrStr;
    }

    public short getMdtNrStrP() {
        return mdtNrStrP;
    }

    public void setMdtNrStrP(short mdtNrStrP) {
        this.mdtNrStrP = mdtNrStrP;
    }

    public Character getMdtTyp() {
        return mdtTyp;
    }

    public void setMdtTyp(Character mdtTyp) {
        this.mdtTyp = mdtTyp;
    }

    public Character getMdtLogo() {
        return mdtLogo;
    }

    public void setMdtLogo(Character mdtLogo) {
        this.mdtLogo = mdtLogo;
    }

    public Character getMdtCeny() {
        return mdtCeny;
    }

    public void setMdtCeny(Character mdtCeny) {
        this.mdtCeny = mdtCeny;
    }

    public Character getMdtChar1() {
        return mdtChar1;
    }

    public void setMdtChar1(Character mdtChar1) {
        this.mdtChar1 = mdtChar1;
    }

    public Character getMdtChar2() {
        return mdtChar2;
    }

    public void setMdtChar2(Character mdtChar2) {
        this.mdtChar2 = mdtChar2;
    }

    public Character getMdtChar3() {
        return mdtChar3;
    }

    public void setMdtChar3(Character mdtChar3) {
        this.mdtChar3 = mdtChar3;
    }

    public Character getMdtChar4() {
        return mdtChar4;
    }

    public void setMdtChar4(Character mdtChar4) {
        this.mdtChar4 = mdtChar4;
    }

    public Character getMdtChar5() {
        return mdtChar5;
    }

    public void setMdtChar5(Character mdtChar5) {
        this.mdtChar5 = mdtChar5;
    }

    public Character getMdtChar6() {
        return mdtChar6;
    }

    public void setMdtChar6(Character mdtChar6) {
        this.mdtChar6 = mdtChar6;
    }

    public Character getMdtChar7() {
        return mdtChar7;
    }

    public void setMdtChar7(Character mdtChar7) {
        this.mdtChar7 = mdtChar7;
    }

    public Character getMdtChar8() {
        return mdtChar8;
    }

    public void setMdtChar8(Character mdtChar8) {
        this.mdtChar8 = mdtChar8;
    }

    public BigDecimal getMdtNum1() {
        return mdtNum1;
    }

    public void setMdtNum1(BigDecimal mdtNum1) {
        this.mdtNum1 = mdtNum1;
    }

    public BigDecimal getMdtNum2() {
        return mdtNum2;
    }

    public void setMdtNum2(BigDecimal mdtNum2) {
        this.mdtNum2 = mdtNum2;
    }

    public Date getMdtData1() {
        return mdtData1;
    }

    public void setMdtData1(Date mdtData1) {
        this.mdtData1 = mdtData1;
    }

    public Date getMdtData2() {
        return mdtData2;
    }

    public void setMdtData2(Date mdtData2) {
        this.mdtData2 = mdtData2;
    }

    public String getMdtVchar1() {
        return mdtVchar1;
    }

    public void setMdtVchar1(String mdtVchar1) {
        this.mdtVchar1 = mdtVchar1;
    }

    public String getMdtVchar2() {
        return mdtVchar2;
    }

    public void setMdtVchar2(String mdtVchar2) {
        this.mdtVchar2 = mdtVchar2;
    }

    public String getMdtVchar3() {
        return mdtVchar3;
    }

    public void setMdtVchar3(String mdtVchar3) {
        this.mdtVchar3 = mdtVchar3;
    }

    public String getMdtVchar4() {
        return mdtVchar4;
    }

    public void setMdtVchar4(String mdtVchar4) {
        this.mdtVchar4 = mdtVchar4;
    }

    public Integer getMdtInt1() {
        return mdtInt1;
    }

    public void setMdtInt1(Integer mdtInt1) {
        this.mdtInt1 = mdtInt1;
    }

    public Integer getMdtInt2() {
        return mdtInt2;
    }

    public void setMdtInt2(Integer mdtInt2) {
        this.mdtInt2 = mdtInt2;
    }

    public Integer getMdtInt3() {
        return mdtInt3;
    }

    public void setMdtInt3(Integer mdtInt3) {
        this.mdtInt3 = mdtInt3;
    }

    public Integer getMdtInt4() {
        return mdtInt4;
    }

    public void setMdtInt4(Integer mdtInt4) {
        this.mdtInt4 = mdtInt4;
    }

    public Firma getMdtFirSerial() {
        return mdtFirSerial;
    }

    public void setMdtFirSerial(Firma mdtFirSerial) {
        this.mdtFirSerial = mdtFirSerial;
    }

    @XmlTransient
    public List<Magdok> getMagdokList() {
        return magdokList;
    }

    public void setMagdokList(List<Magdok> magdokList) {
        this.magdokList = magdokList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mdtSerial != null ? mdtSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magdoktyp)) {
            return false;
        }
        Magdoktyp other = (Magdoktyp) object;
        if ((this.mdtSerial == null && other.mdtSerial != null) || (this.mdtSerial != null && !this.mdtSerial.equals(other.mdtSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Magdoktyp[ mdtSerial=" + mdtSerial + " ]";
    }
    
}
