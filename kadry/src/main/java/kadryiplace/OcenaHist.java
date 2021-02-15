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
@Table(name = "ocena_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OcenaHist.findAll", query = "SELECT o FROM OcenaHist o"),
    @NamedQuery(name = "OcenaHist.findByOchSerial", query = "SELECT o FROM OcenaHist o WHERE o.ochSerial = :ochSerial"),
    @NamedQuery(name = "OcenaHist.findByOchData", query = "SELECT o FROM OcenaHist o WHERE o.ochData = :ochData"),
    @NamedQuery(name = "OcenaHist.findByOchOceNum", query = "SELECT o FROM OcenaHist o WHERE o.ochOceNum = :ochOceNum"),
    @NamedQuery(name = "OcenaHist.findByOchOcenaTxt", query = "SELECT o FROM OcenaHist o WHERE o.ochOcenaTxt = :ochOcenaTxt"),
    @NamedQuery(name = "OcenaHist.findByOchDataNast", query = "SELECT o FROM OcenaHist o WHERE o.ochDataNast = :ochDataNast"),
    @NamedQuery(name = "OcenaHist.findByOchStatus", query = "SELECT o FROM OcenaHist o WHERE o.ochStatus = :ochStatus"),
    @NamedQuery(name = "OcenaHist.findByOchOrgPr", query = "SELECT o FROM OcenaHist o WHERE o.ochOrgPr = :ochOrgPr"),
    @NamedQuery(name = "OcenaHist.findByOchNasKl", query = "SELECT o FROM OcenaHist o WHERE o.ochNasKl = :ochNasKl"),
    @NamedQuery(name = "OcenaHist.findByOchStoOb", query = "SELECT o FROM OcenaHist o WHERE o.ochStoOb = :ochStoOb"),
    @NamedQuery(name = "OcenaHist.findByOchIniMy", query = "SELECT o FROM OcenaHist o WHERE o.ochIniMy = :ochIniMy"),
    @NamedQuery(name = "OcenaHist.findByOchRozWl", query = "SELECT o FROM OcenaHist o WHERE o.ochRozWl = :ochRozWl"),
    @NamedQuery(name = "OcenaHist.findByOchKomSi", query = "SELECT o FROM OcenaHist o WHERE o.ochKomSi = :ochKomSi"),
    @NamedQuery(name = "OcenaHist.findByOchPodDe", query = "SELECT o FROM OcenaHist o WHERE o.ochPodDe = :ochPodDe"),
    @NamedQuery(name = "OcenaHist.findByOchRelMi", query = "SELECT o FROM OcenaHist o WHERE o.ochRelMi = :ochRelMi"),
    @NamedQuery(name = "OcenaHist.findByOchSpOceZb", query = "SELECT o FROM OcenaHist o WHERE o.ochSpOceZb = :ochSpOceZb"),
    @NamedQuery(name = "OcenaHist.findByOchPrAwaPi", query = "SELECT o FROM OcenaHist o WHERE o.ochPrAwaPi = :ochPrAwaPi"),
    @NamedQuery(name = "OcenaHist.findByOchWpOceZb", query = "SELECT o FROM OcenaHist o WHERE o.ochWpOceZb = :ochWpOceZb"),
    @NamedQuery(name = "OcenaHist.findByOchProPod", query = "SELECT o FROM OcenaHist o WHERE o.ochProPod = :ochProPod"),
    @NamedQuery(name = "OcenaHist.findByOchPrzesz", query = "SELECT o FROM OcenaHist o WHERE o.ochPrzesz = :ochPrzesz"),
    @NamedQuery(name = "OcenaHist.findByOchRegPl", query = "SELECT o FROM OcenaHist o WHERE o.ochRegPl = :ochRegPl"),
    @NamedQuery(name = "OcenaHist.findByOchZwiOd", query = "SELECT o FROM OcenaHist o WHERE o.ochZwiOd = :ochZwiOd"),
    @NamedQuery(name = "OcenaHist.findByOchAwaPo", query = "SELECT o FROM OcenaHist o WHERE o.ochAwaPo = :ochAwaPo"),
    @NamedQuery(name = "OcenaHist.findByOchInn", query = "SELECT o FROM OcenaHist o WHERE o.ochInn = :ochInn"),
    @NamedQuery(name = "OcenaHist.findByOchOsoSerialOc", query = "SELECT o FROM OcenaHist o WHERE o.ochOsoSerialOc = :ochOsoSerialOc"),
    @NamedQuery(name = "OcenaHist.findByOchOsoSerialZ1", query = "SELECT o FROM OcenaHist o WHERE o.ochOsoSerialZ1 = :ochOsoSerialZ1"),
    @NamedQuery(name = "OcenaHist.findByOchOsoSerialZ2", query = "SELECT o FROM OcenaHist o WHERE o.ochOsoSerialZ2 = :ochOsoSerialZ2"),
    @NamedQuery(name = "OcenaHist.findByOchPlanow", query = "SELECT o FROM OcenaHist o WHERE o.ochPlanow = :ochPlanow"),
    @NamedQuery(name = "OcenaHist.findByOchOrgani", query = "SELECT o FROM OcenaHist o WHERE o.ochOrgani = :ochOrgani"),
    @NamedQuery(name = "OcenaHist.findByOchPrzywo", query = "SELECT o FROM OcenaHist o WHERE o.ochPrzywo = :ochPrzywo"),
    @NamedQuery(name = "OcenaHist.findByOchMonWy", query = "SELECT o FROM OcenaHist o WHERE o.ochMonWy = :ochMonWy"),
    @NamedQuery(name = "OcenaHist.findByOchKierownik", query = "SELECT o FROM OcenaHist o WHERE o.ochKierownik = :ochKierownik"),
    @NamedQuery(name = "OcenaHist.findByOchOceOg", query = "SELECT o FROM OcenaHist o WHERE o.ochOceOg = :ochOceOg"),
    @NamedQuery(name = "OcenaHist.findByOchSomooc", query = "SELECT o FROM OcenaHist o WHERE o.ochSomooc = :ochSomooc"),
    @NamedQuery(name = "OcenaHist.findByOchKto", query = "SELECT o FROM OcenaHist o WHERE o.ochKto = :ochKto"),
    @NamedQuery(name = "OcenaHist.findByOchTyp", query = "SELECT o FROM OcenaHist o WHERE o.ochTyp = :ochTyp")})
public class OcenaHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "och_serial", nullable = false)
    private Integer ochSerial;
    @Column(name = "och_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ochData;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "och_oce_num", precision = 5, scale = 2)
    private BigDecimal ochOceNum;
    @Size(max = 32)
    @Column(name = "och_ocena_txt", length = 32)
    private String ochOcenaTxt;
    @Column(name = "och_data_nast")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ochDataNast;
    @Basic(optional = false)
    @NotNull
    @Column(name = "och_status", nullable = false)
    private Character ochStatus;
    @Column(name = "och_org_pr")
    private Character ochOrgPr;
    @Column(name = "och_nas_kl")
    private Character ochNasKl;
    @Column(name = "och_sto_ob")
    private Character ochStoOb;
    @Column(name = "och_ini_my")
    private Character ochIniMy;
    @Column(name = "och_roz_wl")
    private Character ochRozWl;
    @Column(name = "och_kom_si")
    private Character ochKomSi;
    @Column(name = "och_pod_de")
    private Character ochPodDe;
    @Column(name = "och_rel_mi")
    private Character ochRelMi;
    @Column(name = "och_sp_oce_zb")
    private Character ochSpOceZb;
    @Column(name = "och_pr_awa_pi")
    private Character ochPrAwaPi;
    @Column(name = "och_wp_oce_zb")
    private Character ochWpOceZb;
    @Column(name = "och_pro_pod", precision = 5, scale = 2)
    private BigDecimal ochProPod;
    @Column(name = "och_przesz")
    private Character ochPrzesz;
    @Column(name = "och_reg_pl")
    private Character ochRegPl;
    @Column(name = "och_zwi_od")
    private Character ochZwiOd;
    @Column(name = "och_awa_po")
    private Character ochAwaPo;
    @Size(max = 254)
    @Column(name = "och_inn", length = 254)
    private String ochInn;
    @Column(name = "och_oso_serial_oc")
    private Integer ochOsoSerialOc;
    @Column(name = "och_oso_serial_z1")
    private Integer ochOsoSerialZ1;
    @Column(name = "och_oso_serial_z2")
    private Integer ochOsoSerialZ2;
    @Column(name = "och_planow")
    private Character ochPlanow;
    @Column(name = "och_organi")
    private Character ochOrgani;
    @Column(name = "och_przywo")
    private Character ochPrzywo;
    @Column(name = "och_mon_wy")
    private Character ochMonWy;
    @Column(name = "och_kierownik")
    private Character ochKierownik;
    @Column(name = "och_oce_og")
    private Character ochOceOg;
    @Column(name = "och_somooc")
    private Character ochSomooc;
    @Column(name = "och_kto")
    private Character ochKto;
    @Column(name = "och_typ")
    private Character ochTyp;
    @JoinColumn(name = "och_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba ochOsoSerial;

    public OcenaHist() {
    }

    public OcenaHist(Integer ochSerial) {
        this.ochSerial = ochSerial;
    }

    public OcenaHist(Integer ochSerial, Character ochStatus) {
        this.ochSerial = ochSerial;
        this.ochStatus = ochStatus;
    }

    public Integer getOchSerial() {
        return ochSerial;
    }

    public void setOchSerial(Integer ochSerial) {
        this.ochSerial = ochSerial;
    }

    public Date getOchData() {
        return ochData;
    }

    public void setOchData(Date ochData) {
        this.ochData = ochData;
    }

    public BigDecimal getOchOceNum() {
        return ochOceNum;
    }

    public void setOchOceNum(BigDecimal ochOceNum) {
        this.ochOceNum = ochOceNum;
    }

    public String getOchOcenaTxt() {
        return ochOcenaTxt;
    }

    public void setOchOcenaTxt(String ochOcenaTxt) {
        this.ochOcenaTxt = ochOcenaTxt;
    }

    public Date getOchDataNast() {
        return ochDataNast;
    }

    public void setOchDataNast(Date ochDataNast) {
        this.ochDataNast = ochDataNast;
    }

    public Character getOchStatus() {
        return ochStatus;
    }

    public void setOchStatus(Character ochStatus) {
        this.ochStatus = ochStatus;
    }

    public Character getOchOrgPr() {
        return ochOrgPr;
    }

    public void setOchOrgPr(Character ochOrgPr) {
        this.ochOrgPr = ochOrgPr;
    }

    public Character getOchNasKl() {
        return ochNasKl;
    }

    public void setOchNasKl(Character ochNasKl) {
        this.ochNasKl = ochNasKl;
    }

    public Character getOchStoOb() {
        return ochStoOb;
    }

    public void setOchStoOb(Character ochStoOb) {
        this.ochStoOb = ochStoOb;
    }

    public Character getOchIniMy() {
        return ochIniMy;
    }

    public void setOchIniMy(Character ochIniMy) {
        this.ochIniMy = ochIniMy;
    }

    public Character getOchRozWl() {
        return ochRozWl;
    }

    public void setOchRozWl(Character ochRozWl) {
        this.ochRozWl = ochRozWl;
    }

    public Character getOchKomSi() {
        return ochKomSi;
    }

    public void setOchKomSi(Character ochKomSi) {
        this.ochKomSi = ochKomSi;
    }

    public Character getOchPodDe() {
        return ochPodDe;
    }

    public void setOchPodDe(Character ochPodDe) {
        this.ochPodDe = ochPodDe;
    }

    public Character getOchRelMi() {
        return ochRelMi;
    }

    public void setOchRelMi(Character ochRelMi) {
        this.ochRelMi = ochRelMi;
    }

    public Character getOchSpOceZb() {
        return ochSpOceZb;
    }

    public void setOchSpOceZb(Character ochSpOceZb) {
        this.ochSpOceZb = ochSpOceZb;
    }

    public Character getOchPrAwaPi() {
        return ochPrAwaPi;
    }

    public void setOchPrAwaPi(Character ochPrAwaPi) {
        this.ochPrAwaPi = ochPrAwaPi;
    }

    public Character getOchWpOceZb() {
        return ochWpOceZb;
    }

    public void setOchWpOceZb(Character ochWpOceZb) {
        this.ochWpOceZb = ochWpOceZb;
    }

    public BigDecimal getOchProPod() {
        return ochProPod;
    }

    public void setOchProPod(BigDecimal ochProPod) {
        this.ochProPod = ochProPod;
    }

    public Character getOchPrzesz() {
        return ochPrzesz;
    }

    public void setOchPrzesz(Character ochPrzesz) {
        this.ochPrzesz = ochPrzesz;
    }

    public Character getOchRegPl() {
        return ochRegPl;
    }

    public void setOchRegPl(Character ochRegPl) {
        this.ochRegPl = ochRegPl;
    }

    public Character getOchZwiOd() {
        return ochZwiOd;
    }

    public void setOchZwiOd(Character ochZwiOd) {
        this.ochZwiOd = ochZwiOd;
    }

    public Character getOchAwaPo() {
        return ochAwaPo;
    }

    public void setOchAwaPo(Character ochAwaPo) {
        this.ochAwaPo = ochAwaPo;
    }

    public String getOchInn() {
        return ochInn;
    }

    public void setOchInn(String ochInn) {
        this.ochInn = ochInn;
    }

    public Integer getOchOsoSerialOc() {
        return ochOsoSerialOc;
    }

    public void setOchOsoSerialOc(Integer ochOsoSerialOc) {
        this.ochOsoSerialOc = ochOsoSerialOc;
    }

    public Integer getOchOsoSerialZ1() {
        return ochOsoSerialZ1;
    }

    public void setOchOsoSerialZ1(Integer ochOsoSerialZ1) {
        this.ochOsoSerialZ1 = ochOsoSerialZ1;
    }

    public Integer getOchOsoSerialZ2() {
        return ochOsoSerialZ2;
    }

    public void setOchOsoSerialZ2(Integer ochOsoSerialZ2) {
        this.ochOsoSerialZ2 = ochOsoSerialZ2;
    }

    public Character getOchPlanow() {
        return ochPlanow;
    }

    public void setOchPlanow(Character ochPlanow) {
        this.ochPlanow = ochPlanow;
    }

    public Character getOchOrgani() {
        return ochOrgani;
    }

    public void setOchOrgani(Character ochOrgani) {
        this.ochOrgani = ochOrgani;
    }

    public Character getOchPrzywo() {
        return ochPrzywo;
    }

    public void setOchPrzywo(Character ochPrzywo) {
        this.ochPrzywo = ochPrzywo;
    }

    public Character getOchMonWy() {
        return ochMonWy;
    }

    public void setOchMonWy(Character ochMonWy) {
        this.ochMonWy = ochMonWy;
    }

    public Character getOchKierownik() {
        return ochKierownik;
    }

    public void setOchKierownik(Character ochKierownik) {
        this.ochKierownik = ochKierownik;
    }

    public Character getOchOceOg() {
        return ochOceOg;
    }

    public void setOchOceOg(Character ochOceOg) {
        this.ochOceOg = ochOceOg;
    }

    public Character getOchSomooc() {
        return ochSomooc;
    }

    public void setOchSomooc(Character ochSomooc) {
        this.ochSomooc = ochSomooc;
    }

    public Character getOchKto() {
        return ochKto;
    }

    public void setOchKto(Character ochKto) {
        this.ochKto = ochKto;
    }

    public Character getOchTyp() {
        return ochTyp;
    }

    public void setOchTyp(Character ochTyp) {
        this.ochTyp = ochTyp;
    }

    public Osoba getOchOsoSerial() {
        return ochOsoSerial;
    }

    public void setOchOsoSerial(Osoba ochOsoSerial) {
        this.ochOsoSerial = ochOsoSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ochSerial != null ? ochSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaHist)) {
            return false;
        }
        OcenaHist other = (OcenaHist) object;
        if ((this.ochSerial == null && other.ochSerial != null) || (this.ochSerial != null && !this.ochSerial.equals(other.ochSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OcenaHist[ ochSerial=" + ochSerial + " ]";
    }
    
}
