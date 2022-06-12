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
@Table(name = "sc_mag", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScMag.findAll", query = "SELECT s FROM ScMag s"),
    @NamedQuery(name = "ScMag.findByScmSerial", query = "SELECT s FROM ScMag s WHERE s.scmSerial = :scmSerial"),
    @NamedQuery(name = "ScMag.findByScmTyp", query = "SELECT s FROM ScMag s WHERE s.scmTyp = :scmTyp"),
    @NamedQuery(name = "ScMag.findByScmCenaSpr", query = "SELECT s FROM ScMag s WHERE s.scmCenaSpr = :scmCenaSpr"),
    @NamedQuery(name = "ScMag.findByScmCenaSpr2", query = "SELECT s FROM ScMag s WHERE s.scmCenaSpr2 = :scmCenaSpr2"),
    @NamedQuery(name = "ScMag.findByScmCenaSpr3", query = "SELECT s FROM ScMag s WHERE s.scmCenaSpr3 = :scmCenaSpr3"),
    @NamedQuery(name = "ScMag.findByScmMarza1", query = "SELECT s FROM ScMag s WHERE s.scmMarza1 = :scmMarza1"),
    @NamedQuery(name = "ScMag.findByScmMarza2", query = "SELECT s FROM ScMag s WHERE s.scmMarza2 = :scmMarza2"),
    @NamedQuery(name = "ScMag.findByScmMarza3", query = "SELECT s FROM ScMag s WHERE s.scmMarza3 = :scmMarza3"),
    @NamedQuery(name = "ScMag.findByScmCenaSprBr1", query = "SELECT s FROM ScMag s WHERE s.scmCenaSprBr1 = :scmCenaSprBr1"),
    @NamedQuery(name = "ScMag.findByScmCenaSprBr2", query = "SELECT s FROM ScMag s WHERE s.scmCenaSprBr2 = :scmCenaSprBr2"),
    @NamedQuery(name = "ScMag.findByScmCenaSprBr3", query = "SELECT s FROM ScMag s WHERE s.scmCenaSprBr3 = :scmCenaSprBr3"),
    @NamedQuery(name = "ScMag.findByScmChar1", query = "SELECT s FROM ScMag s WHERE s.scmChar1 = :scmChar1"),
    @NamedQuery(name = "ScMag.findByScmChar2", query = "SELECT s FROM ScMag s WHERE s.scmChar2 = :scmChar2"),
    @NamedQuery(name = "ScMag.findByScmChar3", query = "SELECT s FROM ScMag s WHERE s.scmChar3 = :scmChar3"),
    @NamedQuery(name = "ScMag.findByScmChar4", query = "SELECT s FROM ScMag s WHERE s.scmChar4 = :scmChar4"),
    @NamedQuery(name = "ScMag.findByScmChar5", query = "SELECT s FROM ScMag s WHERE s.scmChar5 = :scmChar5"),
    @NamedQuery(name = "ScMag.findByScmChar6", query = "SELECT s FROM ScMag s WHERE s.scmChar6 = :scmChar6"),
    @NamedQuery(name = "ScMag.findByScmVchar1", query = "SELECT s FROM ScMag s WHERE s.scmVchar1 = :scmVchar1"),
    @NamedQuery(name = "ScMag.findByScmVchar2", query = "SELECT s FROM ScMag s WHERE s.scmVchar2 = :scmVchar2"),
    @NamedQuery(name = "ScMag.findByScmVchar3", query = "SELECT s FROM ScMag s WHERE s.scmVchar3 = :scmVchar3"),
    @NamedQuery(name = "ScMag.findByScmVchar4", query = "SELECT s FROM ScMag s WHERE s.scmVchar4 = :scmVchar4"),
    @NamedQuery(name = "ScMag.findByScmNum1", query = "SELECT s FROM ScMag s WHERE s.scmNum1 = :scmNum1"),
    @NamedQuery(name = "ScMag.findByScmNum2", query = "SELECT s FROM ScMag s WHERE s.scmNum2 = :scmNum2"),
    @NamedQuery(name = "ScMag.findByScmNum3", query = "SELECT s FROM ScMag s WHERE s.scmNum3 = :scmNum3"),
    @NamedQuery(name = "ScMag.findByScmNum4", query = "SELECT s FROM ScMag s WHERE s.scmNum4 = :scmNum4"),
    @NamedQuery(name = "ScMag.findByScmNum5", query = "SELECT s FROM ScMag s WHERE s.scmNum5 = :scmNum5"),
    @NamedQuery(name = "ScMag.findByScmNum6", query = "SELECT s FROM ScMag s WHERE s.scmNum6 = :scmNum6"),
    @NamedQuery(name = "ScMag.findByScmNum7", query = "SELECT s FROM ScMag s WHERE s.scmNum7 = :scmNum7"),
    @NamedQuery(name = "ScMag.findByScmNum8", query = "SELECT s FROM ScMag s WHERE s.scmNum8 = :scmNum8"),
    @NamedQuery(name = "ScMag.findByScmNum9", query = "SELECT s FROM ScMag s WHERE s.scmNum9 = :scmNum9"),
    @NamedQuery(name = "ScMag.findByScmNum10", query = "SELECT s FROM ScMag s WHERE s.scmNum10 = :scmNum10"),
    @NamedQuery(name = "ScMag.findByScmNum11", query = "SELECT s FROM ScMag s WHERE s.scmNum11 = :scmNum11"),
    @NamedQuery(name = "ScMag.findByScmNum12", query = "SELECT s FROM ScMag s WHERE s.scmNum12 = :scmNum12"),
    @NamedQuery(name = "ScMag.findByScmNum13", query = "SELECT s FROM ScMag s WHERE s.scmNum13 = :scmNum13"),
    @NamedQuery(name = "ScMag.findByScmNum14", query = "SELECT s FROM ScMag s WHERE s.scmNum14 = :scmNum14"),
    @NamedQuery(name = "ScMag.findByScmInt1", query = "SELECT s FROM ScMag s WHERE s.scmInt1 = :scmInt1"),
    @NamedQuery(name = "ScMag.findByScmInt2", query = "SELECT s FROM ScMag s WHERE s.scmInt2 = :scmInt2"),
    @NamedQuery(name = "ScMag.findByScmInt3", query = "SELECT s FROM ScMag s WHERE s.scmInt3 = :scmInt3"),
    @NamedQuery(name = "ScMag.findByScmInt4", query = "SELECT s FROM ScMag s WHERE s.scmInt4 = :scmInt4")})
public class ScMag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "scm_serial", nullable = false)
    private Integer scmSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "scm_typ", nullable = false)
    private Character scmTyp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "scm_cena_spr", precision = 13, scale = 4)
    private BigDecimal scmCenaSpr;
    @Column(name = "scm_cena_spr_2", precision = 13, scale = 4)
    private BigDecimal scmCenaSpr2;
    @Column(name = "scm_cena_spr_3", precision = 13, scale = 4)
    private BigDecimal scmCenaSpr3;
    @Column(name = "scm_marza_1", precision = 5, scale = 2)
    private BigDecimal scmMarza1;
    @Column(name = "scm_marza_2", precision = 5, scale = 2)
    private BigDecimal scmMarza2;
    @Column(name = "scm_marza_3", precision = 5, scale = 2)
    private BigDecimal scmMarza3;
    @Column(name = "scm_cena_spr_br_1", precision = 13, scale = 4)
    private BigDecimal scmCenaSprBr1;
    @Column(name = "scm_cena_spr_br_2", precision = 13, scale = 4)
    private BigDecimal scmCenaSprBr2;
    @Column(name = "scm_cena_spr_br_3", precision = 13, scale = 4)
    private BigDecimal scmCenaSprBr3;
    @Column(name = "scm_char_1")
    private Character scmChar1;
    @Column(name = "scm_char_2")
    private Character scmChar2;
    @Column(name = "scm_char_3")
    private Character scmChar3;
    @Column(name = "scm_char_4")
    private Character scmChar4;
    @Column(name = "scm_char_5")
    private Character scmChar5;
    @Column(name = "scm_char_6")
    private Character scmChar6;
    @Size(max = 64)
    @Column(name = "scm_vchar_1", length = 64)
    private String scmVchar1;
    @Size(max = 64)
    @Column(name = "scm_vchar_2", length = 64)
    private String scmVchar2;
    @Size(max = 64)
    @Column(name = "scm_vchar_3", length = 64)
    private String scmVchar3;
    @Size(max = 64)
    @Column(name = "scm_vchar_4", length = 64)
    private String scmVchar4;
    @Column(name = "scm_num_1", precision = 17, scale = 6)
    private BigDecimal scmNum1;
    @Column(name = "scm_num_2", precision = 17, scale = 6)
    private BigDecimal scmNum2;
    @Column(name = "scm_num_3", precision = 13, scale = 4)
    private BigDecimal scmNum3;
    @Column(name = "scm_num_4", precision = 13, scale = 4)
    private BigDecimal scmNum4;
    @Column(name = "scm_num_5", precision = 13, scale = 4)
    private BigDecimal scmNum5;
    @Column(name = "scm_num_6", precision = 13, scale = 4)
    private BigDecimal scmNum6;
    @Column(name = "scm_num_7", precision = 13, scale = 4)
    private BigDecimal scmNum7;
    @Column(name = "scm_num_8", precision = 13, scale = 4)
    private BigDecimal scmNum8;
    @Column(name = "scm_num_9", precision = 13, scale = 4)
    private BigDecimal scmNum9;
    @Column(name = "scm_num_10", precision = 13, scale = 4)
    private BigDecimal scmNum10;
    @Column(name = "scm_num_11", precision = 5, scale = 2)
    private BigDecimal scmNum11;
    @Column(name = "scm_num_12", precision = 5, scale = 2)
    private BigDecimal scmNum12;
    @Column(name = "scm_num_13", precision = 5, scale = 2)
    private BigDecimal scmNum13;
    @Column(name = "scm_num_14", precision = 5, scale = 2)
    private BigDecimal scmNum14;
    @Column(name = "scm_int_1")
    private Integer scmInt1;
    @Column(name = "scm_int_2")
    private Integer scmInt2;
    @Column(name = "scm_int_3")
    private Integer scmInt3;
    @Column(name = "scm_int_4")
    private Integer scmInt4;
    @JoinColumn(name = "scm_mag_serial", referencedColumnName = "mag_serial", nullable = false)
    @ManyToOne(optional = false)
    private Magazyn scmMagSerial;
    @JoinColumn(name = "scm_scp_serial", referencedColumnName = "scp_serial", nullable = false)
    @ManyToOne(optional = false)
    private ScPromo scmScpSerial;
    @OneToMany(mappedBy = "dscScmSerial")
    private List<DaneStatSc> daneStatScList;

    public ScMag() {
    }

    public ScMag(Integer scmSerial) {
        this.scmSerial = scmSerial;
    }

    public ScMag(Integer scmSerial, Character scmTyp) {
        this.scmSerial = scmSerial;
        this.scmTyp = scmTyp;
    }

    public Integer getScmSerial() {
        return scmSerial;
    }

    public void setScmSerial(Integer scmSerial) {
        this.scmSerial = scmSerial;
    }

    public Character getScmTyp() {
        return scmTyp;
    }

    public void setScmTyp(Character scmTyp) {
        this.scmTyp = scmTyp;
    }

    public BigDecimal getScmCenaSpr() {
        return scmCenaSpr;
    }

    public void setScmCenaSpr(BigDecimal scmCenaSpr) {
        this.scmCenaSpr = scmCenaSpr;
    }

    public BigDecimal getScmCenaSpr2() {
        return scmCenaSpr2;
    }

    public void setScmCenaSpr2(BigDecimal scmCenaSpr2) {
        this.scmCenaSpr2 = scmCenaSpr2;
    }

    public BigDecimal getScmCenaSpr3() {
        return scmCenaSpr3;
    }

    public void setScmCenaSpr3(BigDecimal scmCenaSpr3) {
        this.scmCenaSpr3 = scmCenaSpr3;
    }

    public BigDecimal getScmMarza1() {
        return scmMarza1;
    }

    public void setScmMarza1(BigDecimal scmMarza1) {
        this.scmMarza1 = scmMarza1;
    }

    public BigDecimal getScmMarza2() {
        return scmMarza2;
    }

    public void setScmMarza2(BigDecimal scmMarza2) {
        this.scmMarza2 = scmMarza2;
    }

    public BigDecimal getScmMarza3() {
        return scmMarza3;
    }

    public void setScmMarza3(BigDecimal scmMarza3) {
        this.scmMarza3 = scmMarza3;
    }

    public BigDecimal getScmCenaSprBr1() {
        return scmCenaSprBr1;
    }

    public void setScmCenaSprBr1(BigDecimal scmCenaSprBr1) {
        this.scmCenaSprBr1 = scmCenaSprBr1;
    }

    public BigDecimal getScmCenaSprBr2() {
        return scmCenaSprBr2;
    }

    public void setScmCenaSprBr2(BigDecimal scmCenaSprBr2) {
        this.scmCenaSprBr2 = scmCenaSprBr2;
    }

    public BigDecimal getScmCenaSprBr3() {
        return scmCenaSprBr3;
    }

    public void setScmCenaSprBr3(BigDecimal scmCenaSprBr3) {
        this.scmCenaSprBr3 = scmCenaSprBr3;
    }

    public Character getScmChar1() {
        return scmChar1;
    }

    public void setScmChar1(Character scmChar1) {
        this.scmChar1 = scmChar1;
    }

    public Character getScmChar2() {
        return scmChar2;
    }

    public void setScmChar2(Character scmChar2) {
        this.scmChar2 = scmChar2;
    }

    public Character getScmChar3() {
        return scmChar3;
    }

    public void setScmChar3(Character scmChar3) {
        this.scmChar3 = scmChar3;
    }

    public Character getScmChar4() {
        return scmChar4;
    }

    public void setScmChar4(Character scmChar4) {
        this.scmChar4 = scmChar4;
    }

    public Character getScmChar5() {
        return scmChar5;
    }

    public void setScmChar5(Character scmChar5) {
        this.scmChar5 = scmChar5;
    }

    public Character getScmChar6() {
        return scmChar6;
    }

    public void setScmChar6(Character scmChar6) {
        this.scmChar6 = scmChar6;
    }

    public String getScmVchar1() {
        return scmVchar1;
    }

    public void setScmVchar1(String scmVchar1) {
        this.scmVchar1 = scmVchar1;
    }

    public String getScmVchar2() {
        return scmVchar2;
    }

    public void setScmVchar2(String scmVchar2) {
        this.scmVchar2 = scmVchar2;
    }

    public String getScmVchar3() {
        return scmVchar3;
    }

    public void setScmVchar3(String scmVchar3) {
        this.scmVchar3 = scmVchar3;
    }

    public String getScmVchar4() {
        return scmVchar4;
    }

    public void setScmVchar4(String scmVchar4) {
        this.scmVchar4 = scmVchar4;
    }

    public BigDecimal getScmNum1() {
        return scmNum1;
    }

    public void setScmNum1(BigDecimal scmNum1) {
        this.scmNum1 = scmNum1;
    }

    public BigDecimal getScmNum2() {
        return scmNum2;
    }

    public void setScmNum2(BigDecimal scmNum2) {
        this.scmNum2 = scmNum2;
    }

    public BigDecimal getScmNum3() {
        return scmNum3;
    }

    public void setScmNum3(BigDecimal scmNum3) {
        this.scmNum3 = scmNum3;
    }

    public BigDecimal getScmNum4() {
        return scmNum4;
    }

    public void setScmNum4(BigDecimal scmNum4) {
        this.scmNum4 = scmNum4;
    }

    public BigDecimal getScmNum5() {
        return scmNum5;
    }

    public void setScmNum5(BigDecimal scmNum5) {
        this.scmNum5 = scmNum5;
    }

    public BigDecimal getScmNum6() {
        return scmNum6;
    }

    public void setScmNum6(BigDecimal scmNum6) {
        this.scmNum6 = scmNum6;
    }

    public BigDecimal getScmNum7() {
        return scmNum7;
    }

    public void setScmNum7(BigDecimal scmNum7) {
        this.scmNum7 = scmNum7;
    }

    public BigDecimal getScmNum8() {
        return scmNum8;
    }

    public void setScmNum8(BigDecimal scmNum8) {
        this.scmNum8 = scmNum8;
    }

    public BigDecimal getScmNum9() {
        return scmNum9;
    }

    public void setScmNum9(BigDecimal scmNum9) {
        this.scmNum9 = scmNum9;
    }

    public BigDecimal getScmNum10() {
        return scmNum10;
    }

    public void setScmNum10(BigDecimal scmNum10) {
        this.scmNum10 = scmNum10;
    }

    public BigDecimal getScmNum11() {
        return scmNum11;
    }

    public void setScmNum11(BigDecimal scmNum11) {
        this.scmNum11 = scmNum11;
    }

    public BigDecimal getScmNum12() {
        return scmNum12;
    }

    public void setScmNum12(BigDecimal scmNum12) {
        this.scmNum12 = scmNum12;
    }

    public BigDecimal getScmNum13() {
        return scmNum13;
    }

    public void setScmNum13(BigDecimal scmNum13) {
        this.scmNum13 = scmNum13;
    }

    public BigDecimal getScmNum14() {
        return scmNum14;
    }

    public void setScmNum14(BigDecimal scmNum14) {
        this.scmNum14 = scmNum14;
    }

    public Integer getScmInt1() {
        return scmInt1;
    }

    public void setScmInt1(Integer scmInt1) {
        this.scmInt1 = scmInt1;
    }

    public Integer getScmInt2() {
        return scmInt2;
    }

    public void setScmInt2(Integer scmInt2) {
        this.scmInt2 = scmInt2;
    }

    public Integer getScmInt3() {
        return scmInt3;
    }

    public void setScmInt3(Integer scmInt3) {
        this.scmInt3 = scmInt3;
    }

    public Integer getScmInt4() {
        return scmInt4;
    }

    public void setScmInt4(Integer scmInt4) {
        this.scmInt4 = scmInt4;
    }

    public Magazyn getScmMagSerial() {
        return scmMagSerial;
    }

    public void setScmMagSerial(Magazyn scmMagSerial) {
        this.scmMagSerial = scmMagSerial;
    }

    public ScPromo getScmScpSerial() {
        return scmScpSerial;
    }

    public void setScmScpSerial(ScPromo scmScpSerial) {
        this.scmScpSerial = scmScpSerial;
    }

    @XmlTransient
    public List<DaneStatSc> getDaneStatScList() {
        return daneStatScList;
    }

    public void setDaneStatScList(List<DaneStatSc> daneStatScList) {
        this.daneStatScList = daneStatScList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scmSerial != null ? scmSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScMag)) {
            return false;
        }
        ScMag other = (ScMag) object;
        if ((this.scmSerial == null && other.scmSerial != null) || (this.scmSerial != null && !this.scmSerial.equals(other.scmSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.ScMag[ scmSerial=" + scmSerial + " ]";
    }
    
}
