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
@Table(name = "osoba_pot", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaPot.findAll", query = "SELECT o FROM OsobaPot o"),
    @NamedQuery(name = "OsobaPot.findByOpoSerial", query = "SELECT o FROM OsobaPot o WHERE o.opoSerial = :opoSerial"),
    @NamedQuery(name = "OsobaPot.findByOpoDataOd", query = "SELECT o FROM OsobaPot o WHERE o.opoDataOd = :opoDataOd"),
    @NamedQuery(name = "OsobaPot.findByOpoDataDo", query = "SELECT o FROM OsobaPot o WHERE o.opoDataDo = :opoDataDo"),
    @NamedQuery(name = "OsobaPot.findByOpoKwota", query = "SELECT o FROM OsobaPot o WHERE o.opoKwota = :opoKwota"),
    @NamedQuery(name = "OsobaPot.findByOpoStatus", query = "SELECT o FROM OsobaPot o WHERE o.opoStatus = :opoStatus"),
    @NamedQuery(name = "OsobaPot.findByOpoTyp", query = "SELECT o FROM OsobaPot o WHERE o.opoTyp = :opoTyp"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolcz", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolcz = :opoWspolcz"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolcz2", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolcz2 = :opoWspolcz2"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMin", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMin = :opoWspolczMin"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMax", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMax = :opoWspolczMax"),
    @NamedQuery(name = "OsobaPot.findByOpoAktywna", query = "SELECT o FROM OsobaPot o WHERE o.opoAktywna = :opoAktywna"),
    @NamedQuery(name = "OsobaPot.findByOpoDod1", query = "SELECT o FROM OsobaPot o WHERE o.opoDod1 = :opoDod1"),
    @NamedQuery(name = "OsobaPot.findByOpoDod2", query = "SELECT o FROM OsobaPot o WHERE o.opoDod2 = :opoDod2"),
    @NamedQuery(name = "OsobaPot.findByOpoDod3", query = "SELECT o FROM OsobaPot o WHERE o.opoDod3 = :opoDod3"),
    @NamedQuery(name = "OsobaPot.findByOpoMin", query = "SELECT o FROM OsobaPot o WHERE o.opoMin = :opoMin"),
    @NamedQuery(name = "OsobaPot.findByOpoMax", query = "SELECT o FROM OsobaPot o WHERE o.opoMax = :opoMax"),
    @NamedQuery(name = "OsobaPot.findByOpoKwota2", query = "SELECT o FROM OsobaPot o WHERE o.opoKwota2 = :opoKwota2"),
    @NamedQuery(name = "OsobaPot.findByOpoKwotaMin", query = "SELECT o FROM OsobaPot o WHERE o.opoKwotaMin = :opoKwotaMin"),
    @NamedQuery(name = "OsobaPot.findByOpoKwotaMax", query = "SELECT o FROM OsobaPot o WHERE o.opoKwotaMax = :opoKwotaMax"),
    @NamedQuery(name = "OsobaPot.findByOpoTyp2", query = "SELECT o FROM OsobaPot o WHERE o.opoTyp2 = :opoTyp2"),
    @NamedQuery(name = "OsobaPot.findByOpoTypMin", query = "SELECT o FROM OsobaPot o WHERE o.opoTypMin = :opoTypMin"),
    @NamedQuery(name = "OsobaPot.findByOpoTypMax", query = "SELECT o FROM OsobaPot o WHERE o.opoTypMax = :opoTypMax"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMin2", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMin2 = :opoWspolczMin2"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMax2", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMax2 = :opoWspolczMax2"),
    @NamedQuery(name = "OsobaPot.findByOpoDopisDo", query = "SELECT o FROM OsobaPot o WHERE o.opoDopisDo = :opoDopisDo"),
    @NamedQuery(name = "OsobaPot.findByOpoRodzaj", query = "SELECT o FROM OsobaPot o WHERE o.opoRodzaj = :opoRodzaj"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMin3", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMin3 = :opoWspolczMin3"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolczMax3", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolczMax3 = :opoWspolczMax3"),
    @NamedQuery(name = "OsobaPot.findByOpoWspolcz3", query = "SELECT o FROM OsobaPot o WHERE o.opoWspolcz3 = :opoWspolcz3"),
    @NamedQuery(name = "OsobaPot.findByOpoKwota3", query = "SELECT o FROM OsobaPot o WHERE o.opoKwota3 = :opoKwota3"),
    @NamedQuery(name = "OsobaPot.findByOpoTyp3", query = "SELECT o FROM OsobaPot o WHERE o.opoTyp3 = :opoTyp3"),
    @NamedQuery(name = "OsobaPot.findByOpoInt1", query = "SELECT o FROM OsobaPot o WHERE o.opoInt1 = :opoInt1"),
    @NamedQuery(name = "OsobaPot.findByOpoInt2", query = "SELECT o FROM OsobaPot o WHERE o.opoInt2 = :opoInt2"),
    @NamedQuery(name = "OsobaPot.findByOpoNum1", query = "SELECT o FROM OsobaPot o WHERE o.opoNum1 = :opoNum1"),
    @NamedQuery(name = "OsobaPot.findByOpoNum2", query = "SELECT o FROM OsobaPot o WHERE o.opoNum2 = :opoNum2"),
    @NamedQuery(name = "OsobaPot.findByOpoNum3", query = "SELECT o FROM OsobaPot o WHERE o.opoNum3 = :opoNum3"),
    @NamedQuery(name = "OsobaPot.findByOpoNum4", query = "SELECT o FROM OsobaPot o WHERE o.opoNum4 = :opoNum4"),
    @NamedQuery(name = "OsobaPot.findByOpoDod4", query = "SELECT o FROM OsobaPot o WHERE o.opoDod4 = :opoDod4"),
    @NamedQuery(name = "OsobaPot.findByOpoDod5", query = "SELECT o FROM OsobaPot o WHERE o.opoDod5 = :opoDod5"),
    @NamedQuery(name = "OsobaPot.findByOpoDod6", query = "SELECT o FROM OsobaPot o WHERE o.opoDod6 = :opoDod6"),
    @NamedQuery(name = "OsobaPot.findByOpoDod7", query = "SELECT o FROM OsobaPot o WHERE o.opoDod7 = :opoDod7"),
    @NamedQuery(name = "OsobaPot.findByOpoDod8", query = "SELECT o FROM OsobaPot o WHERE o.opoDod8 = :opoDod8"),
    @NamedQuery(name = "OsobaPot.findByOpoData1", query = "SELECT o FROM OsobaPot o WHERE o.opoData1 = :opoData1"),
    @NamedQuery(name = "OsobaPot.findByOpoData2", query = "SELECT o FROM OsobaPot o WHERE o.opoData2 = :opoData2"),
    @NamedQuery(name = "OsobaPot.findByOpoNazwa", query = "SELECT o FROM OsobaPot o WHERE o.opoNazwa = :opoNazwa"),
    @NamedQuery(name = "OsobaPot.findByOpoWyrazenie", query = "SELECT o FROM OsobaPot o WHERE o.opoWyrazenie = :opoWyrazenie"),
    @NamedQuery(name = "OsobaPot.findByOpoOpoSerial1", query = "SELECT o FROM OsobaPot o WHERE o.opoOpoSerial1 = :opoOpoSerial1"),
    @NamedQuery(name = "OsobaPot.findByOpoOpoSerial2", query = "SELECT o FROM OsobaPot o WHERE o.opoOpoSerial2 = :opoOpoSerial2"),
    @NamedQuery(name = "OsobaPot.findByOpoOpoSerial3", query = "SELECT o FROM OsobaPot o WHERE o.opoOpoSerial3 = :opoOpoSerial3")})
public class OsobaPot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "opo_serial", nullable = false)
    private Integer opoSerial;
    @Column(name = "opo_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opoDataOd;
    @Column(name = "opo_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opoDataDo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "opo_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal opoKwota;
    @Column(name = "opo_status")
    private Character opoStatus;
    @Column(name = "opo_typ")
    private Character opoTyp;
    @Column(name = "opo_wspolcz", precision = 17, scale = 6)
    private BigDecimal opoWspolcz;
    @Column(name = "opo_wspolcz_2", precision = 17, scale = 6)
    private BigDecimal opoWspolcz2;
    @Column(name = "opo_wspolcz_min", precision = 17, scale = 6)
    private BigDecimal opoWspolczMin;
    @Column(name = "opo_wspolcz_max", precision = 17, scale = 6)
    private BigDecimal opoWspolczMax;
    @Column(name = "opo_aktywna")
    private Character opoAktywna;
    @Column(name = "opo_dod_1")
    private Character opoDod1;
    @Column(name = "opo_dod_2")
    private Character opoDod2;
    @Column(name = "opo_dod_3")
    private Character opoDod3;
    @Column(name = "opo_min")
    private Character opoMin;
    @Column(name = "opo_max")
    private Character opoMax;
    @Column(name = "opo_kwota_2", precision = 13, scale = 2)
    private BigDecimal opoKwota2;
    @Column(name = "opo_kwota_min", precision = 13, scale = 2)
    private BigDecimal opoKwotaMin;
    @Column(name = "opo_kwota_max", precision = 13, scale = 2)
    private BigDecimal opoKwotaMax;
    @Column(name = "opo_typ_2")
    private Character opoTyp2;
    @Column(name = "opo_typ_min")
    private Character opoTypMin;
    @Column(name = "opo_typ_max")
    private Character opoTypMax;
    @Column(name = "opo_wspolcz_min2", precision = 17, scale = 6)
    private BigDecimal opoWspolczMin2;
    @Column(name = "opo_wspolcz_max2", precision = 17, scale = 6)
    private BigDecimal opoWspolczMax2;
    @Column(name = "opo_dopis_do")
    private Character opoDopisDo;
    @Column(name = "opo_rodzaj")
    private Character opoRodzaj;
    @Column(name = "opo_wspolcz_min3", precision = 17, scale = 6)
    private BigDecimal opoWspolczMin3;
    @Column(name = "opo_wspolcz_max3", precision = 17, scale = 6)
    private BigDecimal opoWspolczMax3;
    @Column(name = "opo_wspolcz_3", precision = 17, scale = 6)
    private BigDecimal opoWspolcz3;
    @Column(name = "opo_kwota_3", precision = 13, scale = 2)
    private BigDecimal opoKwota3;
    @Column(name = "opo_typ_3")
    private Character opoTyp3;
    @Column(name = "opo_int_1")
    private Integer opoInt1;
    @Column(name = "opo_int_2")
    private Integer opoInt2;
    @Column(name = "opo_num_1", precision = 17, scale = 6)
    private BigDecimal opoNum1;
    @Column(name = "opo_num_2", precision = 17, scale = 6)
    private BigDecimal opoNum2;
    @Column(name = "opo_num_3", precision = 17, scale = 6)
    private BigDecimal opoNum3;
    @Column(name = "opo_num_4", precision = 17, scale = 6)
    private BigDecimal opoNum4;
    @Column(name = "opo_dod_4")
    private Character opoDod4;
    @Column(name = "opo_dod_5")
    private Character opoDod5;
    @Column(name = "opo_dod_6")
    private Character opoDod6;
    @Column(name = "opo_dod_7")
    private Character opoDod7;
    @Column(name = "opo_dod_8")
    private Character opoDod8;
    @Column(name = "opo_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opoData1;
    @Column(name = "opo_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date opoData2;
    @Size(max = 48)
    @Column(name = "opo_nazwa", length = 48)
    private String opoNazwa;
    @Column(name = "opo_wyrazenie")
    private Character opoWyrazenie;
    @Column(name = "opo_opo_serial_1")
    private Integer opoOpoSerial1;
    @Column(name = "opo_opo_serial_2")
    private Integer opoOpoSerial2;
    @Column(name = "opo_opo_serial_3")
    private Integer opoOpoSerial3;
    @JoinColumn(name = "opo_ssd_serial_1", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerial1;
    @JoinColumn(name = "opo_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba opoOsoSerial;
    @JoinColumn(name = "opo_ssd_serial_min3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMin3;
    @JoinColumn(name = "opo_ssd_serial_max3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMax3;
    @JoinColumn(name = "opo_wpo_serial", referencedColumnName = "wpo_serial", nullable = false)
    @ManyToOne(optional = false)
    private WynPotracenia opoWpoSerial;
    @JoinColumn(name = "opo_ssd_serial_3", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerial3;
    @JoinColumn(name = "opo_ssd_serial_2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerial2;
    @JoinColumn(name = "opo_ssd_serial_min", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMin;
    @JoinColumn(name = "opo_ssd_serial_max", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMax;
    @JoinColumn(name = "opo_ssd_serial_min2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMin2;
    @JoinColumn(name = "opo_ssd_serial_max2", referencedColumnName = "ssd_serial")
    @ManyToOne
    private StSystOpis opoSsdSerialMax2;
    @JoinColumn(name = "opo_tyt_serial", referencedColumnName = "tyt_serial")
    @ManyToOne
    private Tytul opoTytSerial;
    @OneToMany(mappedBy = "dswPpoSerial")
    private List<DaneStatW> daneStatWList;
    @OneToMany(mappedBy = "dssOpoSerial")
    private List<DaneStatS> daneStatSList;

    public OsobaPot() {
    }

    public OsobaPot(Integer opoSerial) {
        this.opoSerial = opoSerial;
    }

    public OsobaPot(Integer opoSerial, BigDecimal opoKwota) {
        this.opoSerial = opoSerial;
        this.opoKwota = opoKwota;
    }

    public Integer getOpoSerial() {
        return opoSerial;
    }

    public void setOpoSerial(Integer opoSerial) {
        this.opoSerial = opoSerial;
    }

    public Date getOpoDataOd() {
        return opoDataOd;
    }

    public void setOpoDataOd(Date opoDataOd) {
        this.opoDataOd = opoDataOd;
    }

    public Date getOpoDataDo() {
        return opoDataDo;
    }

    public void setOpoDataDo(Date opoDataDo) {
        this.opoDataDo = opoDataDo;
    }

    public BigDecimal getOpoKwota() {
        return opoKwota;
    }

    public void setOpoKwota(BigDecimal opoKwota) {
        this.opoKwota = opoKwota;
    }

    public Character getOpoStatus() {
        return opoStatus;
    }

    public void setOpoStatus(Character opoStatus) {
        this.opoStatus = opoStatus;
    }

    public Character getOpoTyp() {
        return opoTyp;
    }

    public void setOpoTyp(Character opoTyp) {
        this.opoTyp = opoTyp;
    }

    public BigDecimal getOpoWspolcz() {
        return opoWspolcz;
    }

    public void setOpoWspolcz(BigDecimal opoWspolcz) {
        this.opoWspolcz = opoWspolcz;
    }

    public BigDecimal getOpoWspolcz2() {
        return opoWspolcz2;
    }

    public void setOpoWspolcz2(BigDecimal opoWspolcz2) {
        this.opoWspolcz2 = opoWspolcz2;
    }

    public BigDecimal getOpoWspolczMin() {
        return opoWspolczMin;
    }

    public void setOpoWspolczMin(BigDecimal opoWspolczMin) {
        this.opoWspolczMin = opoWspolczMin;
    }

    public BigDecimal getOpoWspolczMax() {
        return opoWspolczMax;
    }

    public void setOpoWspolczMax(BigDecimal opoWspolczMax) {
        this.opoWspolczMax = opoWspolczMax;
    }

    public Character getOpoAktywna() {
        return opoAktywna;
    }

    public void setOpoAktywna(Character opoAktywna) {
        this.opoAktywna = opoAktywna;
    }

    public Character getOpoDod1() {
        return opoDod1;
    }

    public void setOpoDod1(Character opoDod1) {
        this.opoDod1 = opoDod1;
    }

    public Character getOpoDod2() {
        return opoDod2;
    }

    public void setOpoDod2(Character opoDod2) {
        this.opoDod2 = opoDod2;
    }

    public Character getOpoDod3() {
        return opoDod3;
    }

    public void setOpoDod3(Character opoDod3) {
        this.opoDod3 = opoDod3;
    }

    public Character getOpoMin() {
        return opoMin;
    }

    public void setOpoMin(Character opoMin) {
        this.opoMin = opoMin;
    }

    public Character getOpoMax() {
        return opoMax;
    }

    public void setOpoMax(Character opoMax) {
        this.opoMax = opoMax;
    }

    public BigDecimal getOpoKwota2() {
        return opoKwota2;
    }

    public void setOpoKwota2(BigDecimal opoKwota2) {
        this.opoKwota2 = opoKwota2;
    }

    public BigDecimal getOpoKwotaMin() {
        return opoKwotaMin;
    }

    public void setOpoKwotaMin(BigDecimal opoKwotaMin) {
        this.opoKwotaMin = opoKwotaMin;
    }

    public BigDecimal getOpoKwotaMax() {
        return opoKwotaMax;
    }

    public void setOpoKwotaMax(BigDecimal opoKwotaMax) {
        this.opoKwotaMax = opoKwotaMax;
    }

    public Character getOpoTyp2() {
        return opoTyp2;
    }

    public void setOpoTyp2(Character opoTyp2) {
        this.opoTyp2 = opoTyp2;
    }

    public Character getOpoTypMin() {
        return opoTypMin;
    }

    public void setOpoTypMin(Character opoTypMin) {
        this.opoTypMin = opoTypMin;
    }

    public Character getOpoTypMax() {
        return opoTypMax;
    }

    public void setOpoTypMax(Character opoTypMax) {
        this.opoTypMax = opoTypMax;
    }

    public BigDecimal getOpoWspolczMin2() {
        return opoWspolczMin2;
    }

    public void setOpoWspolczMin2(BigDecimal opoWspolczMin2) {
        this.opoWspolczMin2 = opoWspolczMin2;
    }

    public BigDecimal getOpoWspolczMax2() {
        return opoWspolczMax2;
    }

    public void setOpoWspolczMax2(BigDecimal opoWspolczMax2) {
        this.opoWspolczMax2 = opoWspolczMax2;
    }

    public Character getOpoDopisDo() {
        return opoDopisDo;
    }

    public void setOpoDopisDo(Character opoDopisDo) {
        this.opoDopisDo = opoDopisDo;
    }

    public Character getOpoRodzaj() {
        return opoRodzaj;
    }

    public void setOpoRodzaj(Character opoRodzaj) {
        this.opoRodzaj = opoRodzaj;
    }

    public BigDecimal getOpoWspolczMin3() {
        return opoWspolczMin3;
    }

    public void setOpoWspolczMin3(BigDecimal opoWspolczMin3) {
        this.opoWspolczMin3 = opoWspolczMin3;
    }

    public BigDecimal getOpoWspolczMax3() {
        return opoWspolczMax3;
    }

    public void setOpoWspolczMax3(BigDecimal opoWspolczMax3) {
        this.opoWspolczMax3 = opoWspolczMax3;
    }

    public BigDecimal getOpoWspolcz3() {
        return opoWspolcz3;
    }

    public void setOpoWspolcz3(BigDecimal opoWspolcz3) {
        this.opoWspolcz3 = opoWspolcz3;
    }

    public BigDecimal getOpoKwota3() {
        return opoKwota3;
    }

    public void setOpoKwota3(BigDecimal opoKwota3) {
        this.opoKwota3 = opoKwota3;
    }

    public Character getOpoTyp3() {
        return opoTyp3;
    }

    public void setOpoTyp3(Character opoTyp3) {
        this.opoTyp3 = opoTyp3;
    }

    public Integer getOpoInt1() {
        return opoInt1;
    }

    public void setOpoInt1(Integer opoInt1) {
        this.opoInt1 = opoInt1;
    }

    public Integer getOpoInt2() {
        return opoInt2;
    }

    public void setOpoInt2(Integer opoInt2) {
        this.opoInt2 = opoInt2;
    }

    public BigDecimal getOpoNum1() {
        return opoNum1;
    }

    public void setOpoNum1(BigDecimal opoNum1) {
        this.opoNum1 = opoNum1;
    }

    public BigDecimal getOpoNum2() {
        return opoNum2;
    }

    public void setOpoNum2(BigDecimal opoNum2) {
        this.opoNum2 = opoNum2;
    }

    public BigDecimal getOpoNum3() {
        return opoNum3;
    }

    public void setOpoNum3(BigDecimal opoNum3) {
        this.opoNum3 = opoNum3;
    }

    public BigDecimal getOpoNum4() {
        return opoNum4;
    }

    public void setOpoNum4(BigDecimal opoNum4) {
        this.opoNum4 = opoNum4;
    }

    public Character getOpoDod4() {
        return opoDod4;
    }

    public void setOpoDod4(Character opoDod4) {
        this.opoDod4 = opoDod4;
    }

    public Character getOpoDod5() {
        return opoDod5;
    }

    public void setOpoDod5(Character opoDod5) {
        this.opoDod5 = opoDod5;
    }

    public Character getOpoDod6() {
        return opoDod6;
    }

    public void setOpoDod6(Character opoDod6) {
        this.opoDod6 = opoDod6;
    }

    public Character getOpoDod7() {
        return opoDod7;
    }

    public void setOpoDod7(Character opoDod7) {
        this.opoDod7 = opoDod7;
    }

    public Character getOpoDod8() {
        return opoDod8;
    }

    public void setOpoDod8(Character opoDod8) {
        this.opoDod8 = opoDod8;
    }

    public Date getOpoData1() {
        return opoData1;
    }

    public void setOpoData1(Date opoData1) {
        this.opoData1 = opoData1;
    }

    public Date getOpoData2() {
        return opoData2;
    }

    public void setOpoData2(Date opoData2) {
        this.opoData2 = opoData2;
    }

    public String getOpoNazwa() {
        return opoNazwa;
    }

    public void setOpoNazwa(String opoNazwa) {
        this.opoNazwa = opoNazwa;
    }

    public Character getOpoWyrazenie() {
        return opoWyrazenie;
    }

    public void setOpoWyrazenie(Character opoWyrazenie) {
        this.opoWyrazenie = opoWyrazenie;
    }

    public Integer getOpoOpoSerial1() {
        return opoOpoSerial1;
    }

    public void setOpoOpoSerial1(Integer opoOpoSerial1) {
        this.opoOpoSerial1 = opoOpoSerial1;
    }

    public Integer getOpoOpoSerial2() {
        return opoOpoSerial2;
    }

    public void setOpoOpoSerial2(Integer opoOpoSerial2) {
        this.opoOpoSerial2 = opoOpoSerial2;
    }

    public Integer getOpoOpoSerial3() {
        return opoOpoSerial3;
    }

    public void setOpoOpoSerial3(Integer opoOpoSerial3) {
        this.opoOpoSerial3 = opoOpoSerial3;
    }

    public StSystOpis getOpoSsdSerial1() {
        return opoSsdSerial1;
    }

    public void setOpoSsdSerial1(StSystOpis opoSsdSerial1) {
        this.opoSsdSerial1 = opoSsdSerial1;
    }

    public Osoba getOpoOsoSerial() {
        return opoOsoSerial;
    }

    public void setOpoOsoSerial(Osoba opoOsoSerial) {
        this.opoOsoSerial = opoOsoSerial;
    }

    public StSystOpis getOpoSsdSerialMin3() {
        return opoSsdSerialMin3;
    }

    public void setOpoSsdSerialMin3(StSystOpis opoSsdSerialMin3) {
        this.opoSsdSerialMin3 = opoSsdSerialMin3;
    }

    public StSystOpis getOpoSsdSerialMax3() {
        return opoSsdSerialMax3;
    }

    public void setOpoSsdSerialMax3(StSystOpis opoSsdSerialMax3) {
        this.opoSsdSerialMax3 = opoSsdSerialMax3;
    }

    public WynPotracenia getOpoWpoSerial() {
        return opoWpoSerial;
    }

    public void setOpoWpoSerial(WynPotracenia opoWpoSerial) {
        this.opoWpoSerial = opoWpoSerial;
    }

    public StSystOpis getOpoSsdSerial3() {
        return opoSsdSerial3;
    }

    public void setOpoSsdSerial3(StSystOpis opoSsdSerial3) {
        this.opoSsdSerial3 = opoSsdSerial3;
    }

    public StSystOpis getOpoSsdSerial2() {
        return opoSsdSerial2;
    }

    public void setOpoSsdSerial2(StSystOpis opoSsdSerial2) {
        this.opoSsdSerial2 = opoSsdSerial2;
    }

    public StSystOpis getOpoSsdSerialMin() {
        return opoSsdSerialMin;
    }

    public void setOpoSsdSerialMin(StSystOpis opoSsdSerialMin) {
        this.opoSsdSerialMin = opoSsdSerialMin;
    }

    public StSystOpis getOpoSsdSerialMax() {
        return opoSsdSerialMax;
    }

    public void setOpoSsdSerialMax(StSystOpis opoSsdSerialMax) {
        this.opoSsdSerialMax = opoSsdSerialMax;
    }

    public StSystOpis getOpoSsdSerialMin2() {
        return opoSsdSerialMin2;
    }

    public void setOpoSsdSerialMin2(StSystOpis opoSsdSerialMin2) {
        this.opoSsdSerialMin2 = opoSsdSerialMin2;
    }

    public StSystOpis getOpoSsdSerialMax2() {
        return opoSsdSerialMax2;
    }

    public void setOpoSsdSerialMax2(StSystOpis opoSsdSerialMax2) {
        this.opoSsdSerialMax2 = opoSsdSerialMax2;
    }

    public Tytul getOpoTytSerial() {
        return opoTytSerial;
    }

    public void setOpoTytSerial(Tytul opoTytSerial) {
        this.opoTytSerial = opoTytSerial;
    }

    @XmlTransient
    public List<DaneStatW> getDaneStatWList() {
        return daneStatWList;
    }

    public void setDaneStatWList(List<DaneStatW> daneStatWList) {
        this.daneStatWList = daneStatWList;
    }

    @XmlTransient
    public List<DaneStatS> getDaneStatSList() {
        return daneStatSList;
    }

    public void setDaneStatSList(List<DaneStatS> daneStatSList) {
        this.daneStatSList = daneStatSList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (opoSerial != null ? opoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OsobaPot)) {
            return false;
        }
        OsobaPot other = (OsobaPot) object;
        if ((this.opoSerial == null && other.opoSerial != null) || (this.opoSerial != null && !this.opoSerial.equals(other.opoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OsobaPot[ opoSerial=" + opoSerial + " ]";
    }
    
}
