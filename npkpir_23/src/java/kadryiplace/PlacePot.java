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
@Table(name = "place_pot", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlacePot.findAll", query = "SELECT p FROM PlacePot p"),
    @NamedQuery(name = "PlacePot.findByPpoSerial", query = "SELECT p FROM PlacePot p WHERE p.ppoSerial = :ppoSerial"),
    @NamedQuery(name = "PlacePot.findByPpoDataOd", query = "SELECT p FROM PlacePot p WHERE p.ppoDataOd = :ppoDataOd"),
    @NamedQuery(name = "PlacePot.findByPpoDataDo", query = "SELECT p FROM PlacePot p WHERE p.ppoDataDo = :ppoDataDo"),
    @NamedQuery(name = "PlacePot.findByPpoKwota", query = "SELECT p FROM PlacePot p WHERE p.ppoKwota = :ppoKwota"),
    @NamedQuery(name = "PlacePot.findByPpoPodDoch", query = "SELECT p FROM PlacePot p WHERE p.ppoPodDoch = :ppoPodDoch"),
    @NamedQuery(name = "PlacePot.findByPpoZus", query = "SELECT p FROM PlacePot p WHERE p.ppoZus = :ppoZus"),
    @NamedQuery(name = "PlacePot.findByPpoZdrowotne", query = "SELECT p FROM PlacePot p WHERE p.ppoZdrowotne = :ppoZdrowotne"),
    @NamedQuery(name = "PlacePot.findByPpoChorWyp", query = "SELECT p FROM PlacePot p WHERE p.ppoChorWyp = :ppoChorWyp"),
    @NamedQuery(name = "PlacePot.findByPpoWyp", query = "SELECT p FROM PlacePot p WHERE p.ppoWyp = :ppoWyp"),
    @NamedQuery(name = "PlacePot.findByPpoDod1", query = "SELECT p FROM PlacePot p WHERE p.ppoDod1 = :ppoDod1"),
    @NamedQuery(name = "PlacePot.findByPpoDod2", query = "SELECT p FROM PlacePot p WHERE p.ppoDod2 = :ppoDod2"),
    @NamedQuery(name = "PlacePot.findByPpoDod3", query = "SELECT p FROM PlacePot p WHERE p.ppoDod3 = :ppoDod3"),
    @NamedQuery(name = "PlacePot.findByPpoPdstZasChor", query = "SELECT p FROM PlacePot p WHERE p.ppoPdstZasChor = :ppoPdstZasChor"),
    @NamedQuery(name = "PlacePot.findByPpoPpe", query = "SELECT p FROM PlacePot p WHERE p.ppoPpe = :ppoPpe"),
    @NamedQuery(name = "PlacePot.findByPpoDod4", query = "SELECT p FROM PlacePot p WHERE p.ppoDod4 = :ppoDod4"),
    @NamedQuery(name = "PlacePot.findByPpoDod5", query = "SELECT p FROM PlacePot p WHERE p.ppoDod5 = :ppoDod5"),
    @NamedQuery(name = "PlacePot.findByPpoDod6", query = "SELECT p FROM PlacePot p WHERE p.ppoDod6 = :ppoDod6"),
    @NamedQuery(name = "PlacePot.findByPpoDod7", query = "SELECT p FROM PlacePot p WHERE p.ppoDod7 = :ppoDod7"),
    @NamedQuery(name = "PlacePot.findByPpoDod8", query = "SELECT p FROM PlacePot p WHERE p.ppoDod8 = :ppoDod8"),
    @NamedQuery(name = "PlacePot.findByPpoNum1", query = "SELECT p FROM PlacePot p WHERE p.ppoNum1 = :ppoNum1"),
    @NamedQuery(name = "PlacePot.findByPpoNum2", query = "SELECT p FROM PlacePot p WHERE p.ppoNum2 = :ppoNum2"),
    @NamedQuery(name = "PlacePot.findByPpoDate1", query = "SELECT p FROM PlacePot p WHERE p.ppoDate1 = :ppoDate1"),
    @NamedQuery(name = "PlacePot.findByPpoDate2", query = "SELECT p FROM PlacePot p WHERE p.ppoDate2 = :ppoDate2"),
    @NamedQuery(name = "PlacePot.findByPpoTyp", query = "SELECT p FROM PlacePot p WHERE p.ppoTyp = :ppoTyp"),
    @NamedQuery(name = "PlacePot.findByPpoVchar1", query = "SELECT p FROM PlacePot p WHERE p.ppoVchar1 = :ppoVchar1"),
    @NamedQuery(name = "PlacePot.findByPpoInt1", query = "SELECT p FROM PlacePot p WHERE p.ppoInt1 = :ppoInt1"),
    @NamedQuery(name = "PlacePot.findByPpoInt2", query = "SELECT p FROM PlacePot p WHERE p.ppoInt2 = :ppoInt2"),
    @NamedQuery(name = "PlacePot.findByPpoInt3", query = "SELECT p FROM PlacePot p WHERE p.ppoInt3 = :ppoInt3"),
    @NamedQuery(name = "PlacePot.findByPpoInt4", query = "SELECT p FROM PlacePot p WHERE p.ppoInt4 = :ppoInt4")})
public class PlacePot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_serial", nullable = false)
    private Integer ppoSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppoDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppoDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal ppoKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_pod_doch", nullable = false)
    private Character ppoPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_zus", nullable = false)
    private Character ppoZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_zdrowotne", nullable = false)
    private Character ppoZdrowotne;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_chor_wyp", nullable = false)
    private Character ppoChorWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_wyp", nullable = false)
    private Character ppoWyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_dod_1", nullable = false)
    private Character ppoDod1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_dod_2", nullable = false)
    private Character ppoDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_dod_3", nullable = false)
    private Character ppoDod3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_pdst_zas_chor", nullable = false)
    private Character ppoPdstZasChor;
    @Column(name = "ppo_ppe")
    private Character ppoPpe;
    @Column(name = "ppo_dod_4")
    private Character ppoDod4;
    @Column(name = "ppo_dod_5")
    private Character ppoDod5;
    @Column(name = "ppo_dod_6")
    private Character ppoDod6;
    @Column(name = "ppo_dod_7")
    private Character ppoDod7;
    @Column(name = "ppo_dod_8")
    private Character ppoDod8;
    @Column(name = "ppo_num_1", precision = 17, scale = 6)
    private BigDecimal ppoNum1;
    @Column(name = "ppo_num_2", precision = 17, scale = 6)
    private BigDecimal ppoNum2;
    @Column(name = "ppo_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppoDate1;
    @Column(name = "ppo_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ppoDate2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ppo_typ", nullable = false)
    private Character ppoTyp;
    @Size(max = 64)
    @Column(name = "ppo_vchar_1", length = 64)
    private String ppoVchar1;
    @Column(name = "ppo_int_1")
    private Integer ppoInt1;
    @Column(name = "ppo_int_2")
    private Integer ppoInt2;
    @Column(name = "ppo_int_3")
    private Integer ppoInt3;
    @Column(name = "ppo_int_4")
    private Integer ppoInt4;
    @JoinColumn(name = "ppo_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy ppoLisSerial;
    @JoinColumn(name = "ppo_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres ppoOkrSerial;
    @JoinColumn(name = "ppo_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ppoOsoSerial;
    @JoinColumn(name = "ppo_lpl_serial", referencedColumnName = "lpl_serial")
    @ManyToOne
    private Place ppoLplSerial;
    @JoinColumn(name = "ppo_wpo_serial", referencedColumnName = "wpo_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynPotracenia ppoWpoSerial;

    public PlacePot() {
    }

    public PlacePot(Integer ppoSerial) {
        this.ppoSerial = ppoSerial;
    }

    public PlacePot(Integer ppoSerial, Date ppoDataOd, Date ppoDataDo, BigDecimal ppoKwota, Character ppoPodDoch, Character ppoZus, Character ppoZdrowotne, Character ppoChorWyp, Character ppoWyp, Character ppoDod1, Character ppoDod2, Character ppoDod3, Character ppoPdstZasChor, Character ppoTyp) {
        this.ppoSerial = ppoSerial;
        this.ppoDataOd = ppoDataOd;
        this.ppoDataDo = ppoDataDo;
        this.ppoKwota = ppoKwota;
        this.ppoPodDoch = ppoPodDoch;
        this.ppoZus = ppoZus;
        this.ppoZdrowotne = ppoZdrowotne;
        this.ppoChorWyp = ppoChorWyp;
        this.ppoWyp = ppoWyp;
        this.ppoDod1 = ppoDod1;
        this.ppoDod2 = ppoDod2;
        this.ppoDod3 = ppoDod3;
        this.ppoPdstZasChor = ppoPdstZasChor;
        this.ppoTyp = ppoTyp;
    }

    public Integer getPpoSerial() {
        return ppoSerial;
    }

    public void setPpoSerial(Integer ppoSerial) {
        this.ppoSerial = ppoSerial;
    }

    public Date getPpoDataOd() {
        return ppoDataOd;
    }

    public void setPpoDataOd(Date ppoDataOd) {
        this.ppoDataOd = ppoDataOd;
    }

    public Date getPpoDataDo() {
        return ppoDataDo;
    }

    public void setPpoDataDo(Date ppoDataDo) {
        this.ppoDataDo = ppoDataDo;
    }

    public BigDecimal getPpoKwota() {
        return ppoKwota;
    }

    public void setPpoKwota(BigDecimal ppoKwota) {
        this.ppoKwota = ppoKwota;
    }

    public Character getPpoPodDoch() {
        return ppoPodDoch;
    }

    public void setPpoPodDoch(Character ppoPodDoch) {
        this.ppoPodDoch = ppoPodDoch;
    }

    public Character getPpoZus() {
        return ppoZus;
    }

    public void setPpoZus(Character ppoZus) {
        this.ppoZus = ppoZus;
    }

    public Character getPpoZdrowotne() {
        return ppoZdrowotne;
    }

    public void setPpoZdrowotne(Character ppoZdrowotne) {
        this.ppoZdrowotne = ppoZdrowotne;
    }

    public Character getPpoChorWyp() {
        return ppoChorWyp;
    }

    public void setPpoChorWyp(Character ppoChorWyp) {
        this.ppoChorWyp = ppoChorWyp;
    }

    public Character getPpoWyp() {
        return ppoWyp;
    }

    public void setPpoWyp(Character ppoWyp) {
        this.ppoWyp = ppoWyp;
    }

    public Character getPpoDod1() {
        return ppoDod1;
    }

    public void setPpoDod1(Character ppoDod1) {
        this.ppoDod1 = ppoDod1;
    }

    public Character getPpoDod2() {
        return ppoDod2;
    }

    public void setPpoDod2(Character ppoDod2) {
        this.ppoDod2 = ppoDod2;
    }

    public Character getPpoDod3() {
        return ppoDod3;
    }

    public void setPpoDod3(Character ppoDod3) {
        this.ppoDod3 = ppoDod3;
    }

    public Character getPpoPdstZasChor() {
        return ppoPdstZasChor;
    }

    public void setPpoPdstZasChor(Character ppoPdstZasChor) {
        this.ppoPdstZasChor = ppoPdstZasChor;
    }

    public Character getPpoPpe() {
        return ppoPpe;
    }

    public void setPpoPpe(Character ppoPpe) {
        this.ppoPpe = ppoPpe;
    }

    public Character getPpoDod4() {
        return ppoDod4;
    }

    public void setPpoDod4(Character ppoDod4) {
        this.ppoDod4 = ppoDod4;
    }

    public Character getPpoDod5() {
        return ppoDod5;
    }

    public void setPpoDod5(Character ppoDod5) {
        this.ppoDod5 = ppoDod5;
    }

    public Character getPpoDod6() {
        return ppoDod6;
    }

    public void setPpoDod6(Character ppoDod6) {
        this.ppoDod6 = ppoDod6;
    }

    public Character getPpoDod7() {
        return ppoDod7;
    }

    public void setPpoDod7(Character ppoDod7) {
        this.ppoDod7 = ppoDod7;
    }

    public Character getPpoDod8() {
        return ppoDod8;
    }

    public void setPpoDod8(Character ppoDod8) {
        this.ppoDod8 = ppoDod8;
    }

    public BigDecimal getPpoNum1() {
        return ppoNum1;
    }

    public void setPpoNum1(BigDecimal ppoNum1) {
        this.ppoNum1 = ppoNum1;
    }

    public BigDecimal getPpoNum2() {
        return ppoNum2;
    }

    public void setPpoNum2(BigDecimal ppoNum2) {
        this.ppoNum2 = ppoNum2;
    }

    public Date getPpoDate1() {
        return ppoDate1;
    }

    public void setPpoDate1(Date ppoDate1) {
        this.ppoDate1 = ppoDate1;
    }

    public Date getPpoDate2() {
        return ppoDate2;
    }

    public void setPpoDate2(Date ppoDate2) {
        this.ppoDate2 = ppoDate2;
    }

    public Character getPpoTyp() {
        return ppoTyp;
    }

    public void setPpoTyp(Character ppoTyp) {
        this.ppoTyp = ppoTyp;
    }

    public String getPpoVchar1() {
        return ppoVchar1;
    }

    public void setPpoVchar1(String ppoVchar1) {
        this.ppoVchar1 = ppoVchar1;
    }

    public Integer getPpoInt1() {
        return ppoInt1;
    }

    public void setPpoInt1(Integer ppoInt1) {
        this.ppoInt1 = ppoInt1;
    }

    public Integer getPpoInt2() {
        return ppoInt2;
    }

    public void setPpoInt2(Integer ppoInt2) {
        this.ppoInt2 = ppoInt2;
    }

    public Integer getPpoInt3() {
        return ppoInt3;
    }

    public void setPpoInt3(Integer ppoInt3) {
        this.ppoInt3 = ppoInt3;
    }

    public Integer getPpoInt4() {
        return ppoInt4;
    }

    public void setPpoInt4(Integer ppoInt4) {
        this.ppoInt4 = ppoInt4;
    }

    public Listy getPpoLisSerial() {
        return ppoLisSerial;
    }

    public void setPpoLisSerial(Listy ppoLisSerial) {
        this.ppoLisSerial = ppoLisSerial;
    }

    public Okres getPpoOkrSerial() {
        return ppoOkrSerial;
    }

    public void setPpoOkrSerial(Okres ppoOkrSerial) {
        this.ppoOkrSerial = ppoOkrSerial;
    }

    public Osoba getPpoOsoSerial() {
        return ppoOsoSerial;
    }

    public void setPpoOsoSerial(Osoba ppoOsoSerial) {
        this.ppoOsoSerial = ppoOsoSerial;
    }

    public Place getPpoLplSerial() {
        return ppoLplSerial;
    }

    public void setPpoLplSerial(Place ppoLplSerial) {
        this.ppoLplSerial = ppoLplSerial;
    }

    public WynPotracenia getPpoWpoSerial() {
        return ppoWpoSerial;
    }

    public void setPpoWpoSerial(WynPotracenia ppoWpoSerial) {
        this.ppoWpoSerial = ppoWpoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ppoSerial != null ? ppoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlacePot)) {
            return false;
        }
        PlacePot other = (PlacePot) object;
        if ((this.ppoSerial == null && other.ppoSerial != null) || (this.ppoSerial != null && !this.ppoSerial.equals(other.ppoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.PlacePot[ ppoSerial=" + ppoSerial + " ]";
    }
    
}
