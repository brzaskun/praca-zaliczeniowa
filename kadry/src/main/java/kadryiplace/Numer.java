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
@Table(name = "numer", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Numer.findAll", query = "SELECT n FROM Numer n"),
    @NamedQuery(name = "Numer.findByNmrSerial", query = "SELECT n FROM Numer n WHERE n.nmrSerial = :nmrSerial"),
    @NamedQuery(name = "Numer.findByNmrNrDokAuto", query = "SELECT n FROM Numer n WHERE n.nmrNrDokAuto = :nmrNrDokAuto"),
    @NamedQuery(name = "Numer.findByNmrNrDokD", query = "SELECT n FROM Numer n WHERE n.nmrNrDokD = :nmrNrDokD"),
    @NamedQuery(name = "Numer.findByNmrNrDokDP", query = "SELECT n FROM Numer n WHERE n.nmrNrDokDP = :nmrNrDokDP"),
    @NamedQuery(name = "Numer.findByNmrNrDokL", query = "SELECT n FROM Numer n WHERE n.nmrNrDokL = :nmrNrDokL"),
    @NamedQuery(name = "Numer.findByNmrNrDokLP", query = "SELECT n FROM Numer n WHERE n.nmrNrDokLP = :nmrNrDokLP"),
    @NamedQuery(name = "Numer.findByNmrNrDokS", query = "SELECT n FROM Numer n WHERE n.nmrNrDokS = :nmrNrDokS"),
    @NamedQuery(name = "Numer.findByNmrNrDokSP", query = "SELECT n FROM Numer n WHERE n.nmrNrDokSP = :nmrNrDokSP"),
    @NamedQuery(name = "Numer.findByNmrRodzaj", query = "SELECT n FROM Numer n WHERE n.nmrRodzaj = :nmrRodzaj"),
    @NamedQuery(name = "Numer.findByNmrDokLogo", query = "SELECT n FROM Numer n WHERE n.nmrDokLogo = :nmrDokLogo"),
    @NamedQuery(name = "Numer.findByNmrChar1", query = "SELECT n FROM Numer n WHERE n.nmrChar1 = :nmrChar1"),
    @NamedQuery(name = "Numer.findByNmrChar2", query = "SELECT n FROM Numer n WHERE n.nmrChar2 = :nmrChar2"),
    @NamedQuery(name = "Numer.findByNmrChar3", query = "SELECT n FROM Numer n WHERE n.nmrChar3 = :nmrChar3"),
    @NamedQuery(name = "Numer.findByNmrChar4", query = "SELECT n FROM Numer n WHERE n.nmrChar4 = :nmrChar4"),
    @NamedQuery(name = "Numer.findByNmrChar5", query = "SELECT n FROM Numer n WHERE n.nmrChar5 = :nmrChar5"),
    @NamedQuery(name = "Numer.findByNmrChar6", query = "SELECT n FROM Numer n WHERE n.nmrChar6 = :nmrChar6"),
    @NamedQuery(name = "Numer.findByNmrChar7", query = "SELECT n FROM Numer n WHERE n.nmrChar7 = :nmrChar7"),
    @NamedQuery(name = "Numer.findByNmrChar8", query = "SELECT n FROM Numer n WHERE n.nmrChar8 = :nmrChar8"),
    @NamedQuery(name = "Numer.findByNmrNum1", query = "SELECT n FROM Numer n WHERE n.nmrNum1 = :nmrNum1"),
    @NamedQuery(name = "Numer.findByNmrNum2", query = "SELECT n FROM Numer n WHERE n.nmrNum2 = :nmrNum2"),
    @NamedQuery(name = "Numer.findByNmrData1", query = "SELECT n FROM Numer n WHERE n.nmrData1 = :nmrData1"),
    @NamedQuery(name = "Numer.findByNmrData2", query = "SELECT n FROM Numer n WHERE n.nmrData2 = :nmrData2"),
    @NamedQuery(name = "Numer.findByNmrVchar1", query = "SELECT n FROM Numer n WHERE n.nmrVchar1 = :nmrVchar1"),
    @NamedQuery(name = "Numer.findByNmrVchar2", query = "SELECT n FROM Numer n WHERE n.nmrVchar2 = :nmrVchar2"),
    @NamedQuery(name = "Numer.findByNmrVchar3", query = "SELECT n FROM Numer n WHERE n.nmrVchar3 = :nmrVchar3"),
    @NamedQuery(name = "Numer.findByNmrVchar4", query = "SELECT n FROM Numer n WHERE n.nmrVchar4 = :nmrVchar4"),
    @NamedQuery(name = "Numer.findByNmrInt1", query = "SELECT n FROM Numer n WHERE n.nmrInt1 = :nmrInt1"),
    @NamedQuery(name = "Numer.findByNmrInt2", query = "SELECT n FROM Numer n WHERE n.nmrInt2 = :nmrInt2"),
    @NamedQuery(name = "Numer.findByNmrInt3", query = "SELECT n FROM Numer n WHERE n.nmrInt3 = :nmrInt3"),
    @NamedQuery(name = "Numer.findByNmrInt4", query = "SELECT n FROM Numer n WHERE n.nmrInt4 = :nmrInt4")})
public class Numer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nmr_serial", nullable = false)
    private Integer nmrSerial;
    @Column(name = "nmr_nr_dok_auto")
    private Character nmrNrDokAuto;
    @Size(max = 10)
    @Column(name = "nmr_nr_dok_d", length = 10)
    private String nmrNrDokD;
    @Column(name = "nmr_nr_dok_d_p")
    private Short nmrNrDokDP;
    @Size(max = 10)
    @Column(name = "nmr_nr_dok_l", length = 10)
    private String nmrNrDokL;
    @Column(name = "nmr_nr_dok_l_p")
    private Short nmrNrDokLP;
    @Size(max = 10)
    @Column(name = "nmr_nr_dok_s", length = 10)
    private String nmrNrDokS;
    @Column(name = "nmr_nr_dok_s_p")
    private Short nmrNrDokSP;
    @Column(name = "nmr_rodzaj")
    private Character nmrRodzaj;
    @Column(name = "nmr_dok_logo")
    private Character nmrDokLogo;
    @Column(name = "nmr_char_1")
    private Character nmrChar1;
    @Column(name = "nmr_char_2")
    private Character nmrChar2;
    @Column(name = "nmr_char_3")
    private Character nmrChar3;
    @Column(name = "nmr_char_4")
    private Character nmrChar4;
    @Column(name = "nmr_char_5")
    private Character nmrChar5;
    @Column(name = "nmr_char_6")
    private Character nmrChar6;
    @Column(name = "nmr_char_7")
    private Character nmrChar7;
    @Column(name = "nmr_char_8")
    private Character nmrChar8;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nmr_num_1", precision = 17, scale = 6)
    private BigDecimal nmrNum1;
    @Column(name = "nmr_num_2", precision = 17, scale = 6)
    private BigDecimal nmrNum2;
    @Column(name = "nmr_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nmrData1;
    @Column(name = "nmr_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nmrData2;
    @Size(max = 64)
    @Column(name = "nmr_vchar_1", length = 64)
    private String nmrVchar1;
    @Size(max = 64)
    @Column(name = "nmr_vchar_2", length = 64)
    private String nmrVchar2;
    @Size(max = 64)
    @Column(name = "nmr_vchar_3", length = 64)
    private String nmrVchar3;
    @Size(max = 64)
    @Column(name = "nmr_vchar_4", length = 64)
    private String nmrVchar4;
    @Column(name = "nmr_int_1")
    private Integer nmrInt1;
    @Column(name = "nmr_int_2")
    private Integer nmrInt2;
    @Column(name = "nmr_int_3")
    private Integer nmrInt3;
    @Column(name = "nmr_int_4")
    private Integer nmrInt4;
    @JoinColumn(name = "nmr_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma nmrFirSerial;

    public Numer() {
    }

    public Numer(Integer nmrSerial) {
        this.nmrSerial = nmrSerial;
    }

    public Integer getNmrSerial() {
        return nmrSerial;
    }

    public void setNmrSerial(Integer nmrSerial) {
        this.nmrSerial = nmrSerial;
    }

    public Character getNmrNrDokAuto() {
        return nmrNrDokAuto;
    }

    public void setNmrNrDokAuto(Character nmrNrDokAuto) {
        this.nmrNrDokAuto = nmrNrDokAuto;
    }

    public String getNmrNrDokD() {
        return nmrNrDokD;
    }

    public void setNmrNrDokD(String nmrNrDokD) {
        this.nmrNrDokD = nmrNrDokD;
    }

    public Short getNmrNrDokDP() {
        return nmrNrDokDP;
    }

    public void setNmrNrDokDP(Short nmrNrDokDP) {
        this.nmrNrDokDP = nmrNrDokDP;
    }

    public String getNmrNrDokL() {
        return nmrNrDokL;
    }

    public void setNmrNrDokL(String nmrNrDokL) {
        this.nmrNrDokL = nmrNrDokL;
    }

    public Short getNmrNrDokLP() {
        return nmrNrDokLP;
    }

    public void setNmrNrDokLP(Short nmrNrDokLP) {
        this.nmrNrDokLP = nmrNrDokLP;
    }

    public String getNmrNrDokS() {
        return nmrNrDokS;
    }

    public void setNmrNrDokS(String nmrNrDokS) {
        this.nmrNrDokS = nmrNrDokS;
    }

    public Short getNmrNrDokSP() {
        return nmrNrDokSP;
    }

    public void setNmrNrDokSP(Short nmrNrDokSP) {
        this.nmrNrDokSP = nmrNrDokSP;
    }

    public Character getNmrRodzaj() {
        return nmrRodzaj;
    }

    public void setNmrRodzaj(Character nmrRodzaj) {
        this.nmrRodzaj = nmrRodzaj;
    }

    public Character getNmrDokLogo() {
        return nmrDokLogo;
    }

    public void setNmrDokLogo(Character nmrDokLogo) {
        this.nmrDokLogo = nmrDokLogo;
    }

    public Character getNmrChar1() {
        return nmrChar1;
    }

    public void setNmrChar1(Character nmrChar1) {
        this.nmrChar1 = nmrChar1;
    }

    public Character getNmrChar2() {
        return nmrChar2;
    }

    public void setNmrChar2(Character nmrChar2) {
        this.nmrChar2 = nmrChar2;
    }

    public Character getNmrChar3() {
        return nmrChar3;
    }

    public void setNmrChar3(Character nmrChar3) {
        this.nmrChar3 = nmrChar3;
    }

    public Character getNmrChar4() {
        return nmrChar4;
    }

    public void setNmrChar4(Character nmrChar4) {
        this.nmrChar4 = nmrChar4;
    }

    public Character getNmrChar5() {
        return nmrChar5;
    }

    public void setNmrChar5(Character nmrChar5) {
        this.nmrChar5 = nmrChar5;
    }

    public Character getNmrChar6() {
        return nmrChar6;
    }

    public void setNmrChar6(Character nmrChar6) {
        this.nmrChar6 = nmrChar6;
    }

    public Character getNmrChar7() {
        return nmrChar7;
    }

    public void setNmrChar7(Character nmrChar7) {
        this.nmrChar7 = nmrChar7;
    }

    public Character getNmrChar8() {
        return nmrChar8;
    }

    public void setNmrChar8(Character nmrChar8) {
        this.nmrChar8 = nmrChar8;
    }

    public BigDecimal getNmrNum1() {
        return nmrNum1;
    }

    public void setNmrNum1(BigDecimal nmrNum1) {
        this.nmrNum1 = nmrNum1;
    }

    public BigDecimal getNmrNum2() {
        return nmrNum2;
    }

    public void setNmrNum2(BigDecimal nmrNum2) {
        this.nmrNum2 = nmrNum2;
    }

    public Date getNmrData1() {
        return nmrData1;
    }

    public void setNmrData1(Date nmrData1) {
        this.nmrData1 = nmrData1;
    }

    public Date getNmrData2() {
        return nmrData2;
    }

    public void setNmrData2(Date nmrData2) {
        this.nmrData2 = nmrData2;
    }

    public String getNmrVchar1() {
        return nmrVchar1;
    }

    public void setNmrVchar1(String nmrVchar1) {
        this.nmrVchar1 = nmrVchar1;
    }

    public String getNmrVchar2() {
        return nmrVchar2;
    }

    public void setNmrVchar2(String nmrVchar2) {
        this.nmrVchar2 = nmrVchar2;
    }

    public String getNmrVchar3() {
        return nmrVchar3;
    }

    public void setNmrVchar3(String nmrVchar3) {
        this.nmrVchar3 = nmrVchar3;
    }

    public String getNmrVchar4() {
        return nmrVchar4;
    }

    public void setNmrVchar4(String nmrVchar4) {
        this.nmrVchar4 = nmrVchar4;
    }

    public Integer getNmrInt1() {
        return nmrInt1;
    }

    public void setNmrInt1(Integer nmrInt1) {
        this.nmrInt1 = nmrInt1;
    }

    public Integer getNmrInt2() {
        return nmrInt2;
    }

    public void setNmrInt2(Integer nmrInt2) {
        this.nmrInt2 = nmrInt2;
    }

    public Integer getNmrInt3() {
        return nmrInt3;
    }

    public void setNmrInt3(Integer nmrInt3) {
        this.nmrInt3 = nmrInt3;
    }

    public Integer getNmrInt4() {
        return nmrInt4;
    }

    public void setNmrInt4(Integer nmrInt4) {
        this.nmrInt4 = nmrInt4;
    }

    public Firma getNmrFirSerial() {
        return nmrFirSerial;
    }

    public void setNmrFirSerial(Firma nmrFirSerial) {
        this.nmrFirSerial = nmrFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nmrSerial != null ? nmrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Numer)) {
            return false;
        }
        Numer other = (Numer) object;
        if ((this.nmrSerial == null && other.nmrSerial != null) || (this.nmrSerial != null && !this.nmrSerial.equals(other.nmrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Numer[ nmrSerial=" + nmrSerial + " ]";
    }
    
}
