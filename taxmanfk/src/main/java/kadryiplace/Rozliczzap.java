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
@Table(name = "rozliczzap", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozliczzap.findAll", query = "SELECT r FROM Rozliczzap r"),
    @NamedQuery(name = "Rozliczzap.findByRzzSerial", query = "SELECT r FROM Rozliczzap r WHERE r.rzzSerial = :rzzSerial"),
    @NamedQuery(name = "Rozliczzap.findByRzzData", query = "SELECT r FROM Rozliczzap r WHERE r.rzzData = :rzzData"),
    @NamedQuery(name = "Rozliczzap.findByRzzOpis", query = "SELECT r FROM Rozliczzap r WHERE r.rzzOpis = :rzzOpis"),
    @NamedQuery(name = "Rozliczzap.findByRzzKwota", query = "SELECT r FROM Rozliczzap r WHERE r.rzzKwota = :rzzKwota"),
    @NamedQuery(name = "Rozliczzap.findByRzzNumerDok", query = "SELECT r FROM Rozliczzap r WHERE r.rzzNumerDok = :rzzNumerDok"),
    @NamedQuery(name = "Rozliczzap.findByRzzUwagi", query = "SELECT r FROM Rozliczzap r WHERE r.rzzUwagi = :rzzUwagi"),
    @NamedQuery(name = "Rozliczzap.findByRzzTytul", query = "SELECT r FROM Rozliczzap r WHERE r.rzzTytul = :rzzTytul"),
    @NamedQuery(name = "Rozliczzap.findByRzzForSerial", query = "SELECT r FROM Rozliczzap r WHERE r.rzzForSerial = :rzzForSerial"),
    @NamedQuery(name = "Rozliczzap.findByRzzChar1", query = "SELECT r FROM Rozliczzap r WHERE r.rzzChar1 = :rzzChar1"),
    @NamedQuery(name = "Rozliczzap.findByRzzChar2", query = "SELECT r FROM Rozliczzap r WHERE r.rzzChar2 = :rzzChar2"),
    @NamedQuery(name = "Rozliczzap.findByRzzChar3", query = "SELECT r FROM Rozliczzap r WHERE r.rzzChar3 = :rzzChar3"),
    @NamedQuery(name = "Rozliczzap.findByRzzChar4", query = "SELECT r FROM Rozliczzap r WHERE r.rzzChar4 = :rzzChar4"),
    @NamedQuery(name = "Rozliczzap.findByRzzNum1", query = "SELECT r FROM Rozliczzap r WHERE r.rzzNum1 = :rzzNum1"),
    @NamedQuery(name = "Rozliczzap.findByRzzNum2", query = "SELECT r FROM Rozliczzap r WHERE r.rzzNum2 = :rzzNum2"),
    @NamedQuery(name = "Rozliczzap.findByRzzFormaWpl", query = "SELECT r FROM Rozliczzap r WHERE r.rzzFormaWpl = :rzzFormaWpl"),
    @NamedQuery(name = "Rozliczzap.findByRzzVchar1", query = "SELECT r FROM Rozliczzap r WHERE r.rzzVchar1 = :rzzVchar1"),
    @NamedQuery(name = "Rozliczzap.findByRzzData1", query = "SELECT r FROM Rozliczzap r WHERE r.rzzData1 = :rzzData1"),
    @NamedQuery(name = "Rozliczzap.findByRzzData2", query = "SELECT r FROM Rozliczzap r WHERE r.rzzData2 = :rzzData2"),
    @NamedQuery(name = "Rozliczzap.findByRzzTyp", query = "SELECT r FROM Rozliczzap r WHERE r.rzzTyp = :rzzTyp")})
public class Rozliczzap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzz_serial", nullable = false)
    private Integer rzzSerial;
    @Column(name = "rzz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzzData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "rzz_opis", nullable = false, length = 64)
    private String rzzOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzz_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal rzzKwota;
    @Size(max = 32)
    @Column(name = "rzz_numer_dok", length = 32)
    private String rzzNumerDok;
    @Size(max = 128)
    @Column(name = "rzz_uwagi", length = 128)
    private String rzzUwagi;
    @Size(max = 128)
    @Column(name = "rzz_tytul", length = 128)
    private String rzzTytul;
    @Column(name = "rzz_for_serial")
    private Integer rzzForSerial;
    @Column(name = "rzz_char_1")
    private Character rzzChar1;
    @Column(name = "rzz_char_2")
    private Character rzzChar2;
    @Column(name = "rzz_char_3")
    private Character rzzChar3;
    @Column(name = "rzz_char_4")
    private Character rzzChar4;
    @Column(name = "rzz_num_1", precision = 17, scale = 6)
    private BigDecimal rzzNum1;
    @Column(name = "rzz_num_2", precision = 17, scale = 6)
    private BigDecimal rzzNum2;
    @Column(name = "rzz_forma_wpl")
    private Character rzzFormaWpl;
    @Size(max = 128)
    @Column(name = "rzz_vchar_1", length = 128)
    private String rzzVchar1;
    @Column(name = "rzz_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzzData1;
    @Column(name = "rzz_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzzData2;
    @Column(name = "rzz_typ")
    private Character rzzTyp;
    @JoinColumn(name = "rzz_rzu_serial", referencedColumnName = "rzu_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rozliczus rzzRzuSerial;

    public Rozliczzap() {
    }

    public Rozliczzap(Integer rzzSerial) {
        this.rzzSerial = rzzSerial;
    }

    public Rozliczzap(Integer rzzSerial, String rzzOpis, BigDecimal rzzKwota) {
        this.rzzSerial = rzzSerial;
        this.rzzOpis = rzzOpis;
        this.rzzKwota = rzzKwota;
    }

    public Integer getRzzSerial() {
        return rzzSerial;
    }

    public void setRzzSerial(Integer rzzSerial) {
        this.rzzSerial = rzzSerial;
    }

    public Date getRzzData() {
        return rzzData;
    }

    public void setRzzData(Date rzzData) {
        this.rzzData = rzzData;
    }

    public String getRzzOpis() {
        return rzzOpis;
    }

    public void setRzzOpis(String rzzOpis) {
        this.rzzOpis = rzzOpis;
    }

    public BigDecimal getRzzKwota() {
        return rzzKwota;
    }

    public void setRzzKwota(BigDecimal rzzKwota) {
        this.rzzKwota = rzzKwota;
    }

    public String getRzzNumerDok() {
        return rzzNumerDok;
    }

    public void setRzzNumerDok(String rzzNumerDok) {
        this.rzzNumerDok = rzzNumerDok;
    }

    public String getRzzUwagi() {
        return rzzUwagi;
    }

    public void setRzzUwagi(String rzzUwagi) {
        this.rzzUwagi = rzzUwagi;
    }

    public String getRzzTytul() {
        return rzzTytul;
    }

    public void setRzzTytul(String rzzTytul) {
        this.rzzTytul = rzzTytul;
    }

    public Integer getRzzForSerial() {
        return rzzForSerial;
    }

    public void setRzzForSerial(Integer rzzForSerial) {
        this.rzzForSerial = rzzForSerial;
    }

    public Character getRzzChar1() {
        return rzzChar1;
    }

    public void setRzzChar1(Character rzzChar1) {
        this.rzzChar1 = rzzChar1;
    }

    public Character getRzzChar2() {
        return rzzChar2;
    }

    public void setRzzChar2(Character rzzChar2) {
        this.rzzChar2 = rzzChar2;
    }

    public Character getRzzChar3() {
        return rzzChar3;
    }

    public void setRzzChar3(Character rzzChar3) {
        this.rzzChar3 = rzzChar3;
    }

    public Character getRzzChar4() {
        return rzzChar4;
    }

    public void setRzzChar4(Character rzzChar4) {
        this.rzzChar4 = rzzChar4;
    }

    public BigDecimal getRzzNum1() {
        return rzzNum1;
    }

    public void setRzzNum1(BigDecimal rzzNum1) {
        this.rzzNum1 = rzzNum1;
    }

    public BigDecimal getRzzNum2() {
        return rzzNum2;
    }

    public void setRzzNum2(BigDecimal rzzNum2) {
        this.rzzNum2 = rzzNum2;
    }

    public Character getRzzFormaWpl() {
        return rzzFormaWpl;
    }

    public void setRzzFormaWpl(Character rzzFormaWpl) {
        this.rzzFormaWpl = rzzFormaWpl;
    }

    public String getRzzVchar1() {
        return rzzVchar1;
    }

    public void setRzzVchar1(String rzzVchar1) {
        this.rzzVchar1 = rzzVchar1;
    }

    public Date getRzzData1() {
        return rzzData1;
    }

    public void setRzzData1(Date rzzData1) {
        this.rzzData1 = rzzData1;
    }

    public Date getRzzData2() {
        return rzzData2;
    }

    public void setRzzData2(Date rzzData2) {
        this.rzzData2 = rzzData2;
    }

    public Character getRzzTyp() {
        return rzzTyp;
    }

    public void setRzzTyp(Character rzzTyp) {
        this.rzzTyp = rzzTyp;
    }

    public Rozliczus getRzzRzuSerial() {
        return rzzRzuSerial;
    }

    public void setRzzRzuSerial(Rozliczus rzzRzuSerial) {
        this.rzzRzuSerial = rzzRzuSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rzzSerial != null ? rzzSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rozliczzap)) {
            return false;
        }
        Rozliczzap other = (Rozliczzap) object;
        if ((this.rzzSerial == null && other.rzzSerial != null) || (this.rzzSerial != null && !this.rzzSerial.equals(other.rzzSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Rozliczzap[ rzzSerial=" + rzzSerial + " ]";
    }
    
}
