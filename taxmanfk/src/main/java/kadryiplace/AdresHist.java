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
@Table(name = "adres_hist", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AdresHist.findAll", query = "SELECT a FROM AdresHist a"),
    @NamedQuery(name = "AdresHist.findByAdhSerial", query = "SELECT a FROM AdresHist a WHERE a.adhSerial = :adhSerial"),
    @NamedQuery(name = "AdresHist.findByAdhTyp", query = "SELECT a FROM AdresHist a WHERE a.adhTyp = :adhTyp"),
    @NamedQuery(name = "AdresHist.findByAdhRodzaj", query = "SELECT a FROM AdresHist a WHERE a.adhRodzaj = :adhRodzaj"),
    @NamedQuery(name = "AdresHist.findByAdhDataOd", query = "SELECT a FROM AdresHist a WHERE a.adhDataOd = :adhDataOd"),
    @NamedQuery(name = "AdresHist.findByAdhDataDo", query = "SELECT a FROM AdresHist a WHERE a.adhDataDo = :adhDataDo"),
    @NamedQuery(name = "AdresHist.findByAdhKod", query = "SELECT a FROM AdresHist a WHERE a.adhKod = :adhKod"),
    @NamedQuery(name = "AdresHist.findByAdhUlica", query = "SELECT a FROM AdresHist a WHERE a.adhUlica = :adhUlica"),
    @NamedQuery(name = "AdresHist.findByAdhDom", query = "SELECT a FROM AdresHist a WHERE a.adhDom = :adhDom"),
    @NamedQuery(name = "AdresHist.findByAdhMieszkanie", query = "SELECT a FROM AdresHist a WHERE a.adhMieszkanie = :adhMieszkanie"),
    @NamedQuery(name = "AdresHist.findByAdhTel", query = "SELECT a FROM AdresHist a WHERE a.adhTel = :adhTel"),
    @NamedQuery(name = "AdresHist.findByAdhFaks", query = "SELECT a FROM AdresHist a WHERE a.adhFaks = :adhFaks"),
    @NamedQuery(name = "AdresHist.findByAdhEmail", query = "SELECT a FROM AdresHist a WHERE a.adhEmail = :adhEmail"),
    @NamedQuery(name = "AdresHist.findByAdhSkrytka", query = "SELECT a FROM AdresHist a WHERE a.adhSkrytka = :adhSkrytka"),
    @NamedQuery(name = "AdresHist.findByAdhTeletrans", query = "SELECT a FROM AdresHist a WHERE a.adhTeletrans = :adhTeletrans"),
    @NamedQuery(name = "AdresHist.findByAdhGmina", query = "SELECT a FROM AdresHist a WHERE a.adhGmina = :adhGmina"),
    @NamedQuery(name = "AdresHist.findByAdhPowiat", query = "SELECT a FROM AdresHist a WHERE a.adhPowiat = :adhPowiat"),
    @NamedQuery(name = "AdresHist.findByAdhPoczta", query = "SELECT a FROM AdresHist a WHERE a.adhPoczta = :adhPoczta"),
    @NamedQuery(name = "AdresHist.findByAdhWojewodztwo", query = "SELECT a FROM AdresHist a WHERE a.adhWojewodztwo = :adhWojewodztwo"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar1", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar1 = :adhDodChar1"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar2", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar2 = :adhDodChar2"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar3", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar3 = :adhDodChar3"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar4", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar4 = :adhDodChar4"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar5", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar5 = :adhDodChar5"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar6", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar6 = :adhDodChar6"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar7", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar7 = :adhDodChar7"),
    @NamedQuery(name = "AdresHist.findByAdhDodChar8", query = "SELECT a FROM AdresHist a WHERE a.adhDodChar8 = :adhDodChar8"),
    @NamedQuery(name = "AdresHist.findByAdhDodDate1", query = "SELECT a FROM AdresHist a WHERE a.adhDodDate1 = :adhDodDate1"),
    @NamedQuery(name = "AdresHist.findByAdhDodDate2", query = "SELECT a FROM AdresHist a WHERE a.adhDodDate2 = :adhDodDate2"),
    @NamedQuery(name = "AdresHist.findByAdhDodInt1", query = "SELECT a FROM AdresHist a WHERE a.adhDodInt1 = :adhDodInt1"),
    @NamedQuery(name = "AdresHist.findByAdhDodInt2", query = "SELECT a FROM AdresHist a WHERE a.adhDodInt2 = :adhDodInt2"),
    @NamedQuery(name = "AdresHist.findByAdhDodNum1", query = "SELECT a FROM AdresHist a WHERE a.adhDodNum1 = :adhDodNum1"),
    @NamedQuery(name = "AdresHist.findByAdhDodNum2", query = "SELECT a FROM AdresHist a WHERE a.adhDodNum2 = :adhDodNum2"),
    @NamedQuery(name = "AdresHist.findByAdhDodVchar1", query = "SELECT a FROM AdresHist a WHERE a.adhDodVchar1 = :adhDodVchar1"),
    @NamedQuery(name = "AdresHist.findByAdhDodVchar2", query = "SELECT a FROM AdresHist a WHERE a.adhDodVchar2 = :adhDodVchar2"),
    @NamedQuery(name = "AdresHist.findByAdhStatus", query = "SELECT a FROM AdresHist a WHERE a.adhStatus = :adhStatus")})
public class AdresHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "adh_serial", nullable = false)
    private Integer adhSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adh_typ", nullable = false)
    private Character adhTyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adh_rodzaj", nullable = false)
    private Character adhRodzaj;
    @Column(name = "adh_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adhDataOd;
    @Column(name = "adh_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adhDataDo;
    @Size(max = 5)
    @Column(name = "adh_kod", length = 5)
    private String adhKod;
    @Size(max = 30)
    @Column(name = "adh_ulica", length = 30)
    private String adhUlica;
    @Size(max = 7)
    @Column(name = "adh_dom", length = 7)
    private String adhDom;
    @Size(max = 7)
    @Column(name = "adh_mieszkanie", length = 7)
    private String adhMieszkanie;
    @Size(max = 16)
    @Column(name = "adh_tel", length = 16)
    private String adhTel;
    @Size(max = 16)
    @Column(name = "adh_faks", length = 16)
    private String adhFaks;
    @Size(max = 96)
    @Column(name = "adh_email", length = 96)
    private String adhEmail;
    @Size(max = 5)
    @Column(name = "adh_skrytka", length = 5)
    private String adhSkrytka;
    @Size(max = 16)
    @Column(name = "adh_teletrans", length = 16)
    private String adhTeletrans;
    @Size(max = 26)
    @Column(name = "adh_gmina", length = 26)
    private String adhGmina;
    @Size(max = 26)
    @Column(name = "adh_powiat", length = 26)
    private String adhPowiat;
    @Size(max = 26)
    @Column(name = "adh_poczta", length = 26)
    private String adhPoczta;
    @Size(max = 26)
    @Column(name = "adh_wojewodztwo", length = 26)
    private String adhWojewodztwo;
    @Column(name = "adh_dod_char_1")
    private Character adhDodChar1;
    @Column(name = "adh_dod_char_2")
    private Character adhDodChar2;
    @Column(name = "adh_dod_char_3")
    private Character adhDodChar3;
    @Column(name = "adh_dod_char_4")
    private Character adhDodChar4;
    @Column(name = "adh_dod_char_5")
    private Character adhDodChar5;
    @Column(name = "adh_dod_char_6")
    private Character adhDodChar6;
    @Column(name = "adh_dod_char_7")
    private Character adhDodChar7;
    @Column(name = "adh_dod_char_8")
    private Character adhDodChar8;
    @Column(name = "adh_dod_date_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adhDodDate1;
    @Column(name = "adh_dod_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adhDodDate2;
    @Column(name = "adh_dod_int_1")
    private Integer adhDodInt1;
    @Column(name = "adh_dod_int_2")
    private Integer adhDodInt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "adh_dod_num_1", precision = 17, scale = 6)
    private BigDecimal adhDodNum1;
    @Column(name = "adh_dod_num_2", precision = 17, scale = 6)
    private BigDecimal adhDodNum2;
    @Size(max = 64)
    @Column(name = "adh_dod_vchar_1", length = 64)
    private String adhDodVchar1;
    @Size(max = 64)
    @Column(name = "adh_dod_vchar_2", length = 64)
    private String adhDodVchar2;
    @Column(name = "adh_status")
    private Character adhStatus;
    @JoinColumn(name = "adh_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma adhFirSerial;
    @JoinColumn(name = "adh_kon_serial", referencedColumnName = "kon_serial")
    @ManyToOne
    private Kontrahent adhKonSerial;
    @JoinColumn(name = "adh_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto adhMiaSerial;
    @JoinColumn(name = "adh_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba adhOsoSerial;
    @JoinColumn(name = "adh_osr_serial", referencedColumnName = "osr_serial")
    @ManyToOne
    private OsobaRod adhOsrSerial;
    @JoinColumn(name = "adh_pan_serial", referencedColumnName = "pan_serial")
    @ManyToOne
    private Panstwo adhPanSerial;

    public AdresHist() {
    }

    public AdresHist(Integer adhSerial) {
        this.adhSerial = adhSerial;
    }

    public AdresHist(Integer adhSerial, Character adhTyp, Character adhRodzaj) {
        this.adhSerial = adhSerial;
        this.adhTyp = adhTyp;
        this.adhRodzaj = adhRodzaj;
    }

    public Integer getAdhSerial() {
        return adhSerial;
    }

    public void setAdhSerial(Integer adhSerial) {
        this.adhSerial = adhSerial;
    }

    public Character getAdhTyp() {
        return adhTyp;
    }

    public void setAdhTyp(Character adhTyp) {
        this.adhTyp = adhTyp;
    }

    public Character getAdhRodzaj() {
        return adhRodzaj;
    }

    public void setAdhRodzaj(Character adhRodzaj) {
        this.adhRodzaj = adhRodzaj;
    }

    public Date getAdhDataOd() {
        return adhDataOd;
    }

    public void setAdhDataOd(Date adhDataOd) {
        this.adhDataOd = adhDataOd;
    }

    public Date getAdhDataDo() {
        return adhDataDo;
    }

    public void setAdhDataDo(Date adhDataDo) {
        this.adhDataDo = adhDataDo;
    }

    public String getAdhKod() {
        return adhKod;
    }

    public void setAdhKod(String adhKod) {
        this.adhKod = adhKod;
    }

    public String getAdhUlica() {
        return adhUlica;
    }

    public void setAdhUlica(String adhUlica) {
        this.adhUlica = adhUlica;
    }

    public String getAdhDom() {
        return adhDom;
    }

    public void setAdhDom(String adhDom) {
        this.adhDom = adhDom;
    }

    public String getAdhMieszkanie() {
        return adhMieszkanie;
    }

    public void setAdhMieszkanie(String adhMieszkanie) {
        this.adhMieszkanie = adhMieszkanie;
    }

    public String getAdhTel() {
        return adhTel;
    }

    public void setAdhTel(String adhTel) {
        this.adhTel = adhTel;
    }

    public String getAdhFaks() {
        return adhFaks;
    }

    public void setAdhFaks(String adhFaks) {
        this.adhFaks = adhFaks;
    }

    public String getAdhEmail() {
        return adhEmail;
    }

    public void setAdhEmail(String adhEmail) {
        this.adhEmail = adhEmail;
    }

    public String getAdhSkrytka() {
        return adhSkrytka;
    }

    public void setAdhSkrytka(String adhSkrytka) {
        this.adhSkrytka = adhSkrytka;
    }

    public String getAdhTeletrans() {
        return adhTeletrans;
    }

    public void setAdhTeletrans(String adhTeletrans) {
        this.adhTeletrans = adhTeletrans;
    }

    public String getAdhGmina() {
        return adhGmina;
    }

    public void setAdhGmina(String adhGmina) {
        this.adhGmina = adhGmina;
    }

    public String getAdhPowiat() {
        return adhPowiat;
    }

    public void setAdhPowiat(String adhPowiat) {
        this.adhPowiat = adhPowiat;
    }

    public String getAdhPoczta() {
        return adhPoczta;
    }

    public void setAdhPoczta(String adhPoczta) {
        this.adhPoczta = adhPoczta;
    }

    public String getAdhWojewodztwo() {
        return adhWojewodztwo;
    }

    public void setAdhWojewodztwo(String adhWojewodztwo) {
        this.adhWojewodztwo = adhWojewodztwo;
    }

    public Character getAdhDodChar1() {
        return adhDodChar1;
    }

    public void setAdhDodChar1(Character adhDodChar1) {
        this.adhDodChar1 = adhDodChar1;
    }

    public Character getAdhDodChar2() {
        return adhDodChar2;
    }

    public void setAdhDodChar2(Character adhDodChar2) {
        this.adhDodChar2 = adhDodChar2;
    }

    public Character getAdhDodChar3() {
        return adhDodChar3;
    }

    public void setAdhDodChar3(Character adhDodChar3) {
        this.adhDodChar3 = adhDodChar3;
    }

    public Character getAdhDodChar4() {
        return adhDodChar4;
    }

    public void setAdhDodChar4(Character adhDodChar4) {
        this.adhDodChar4 = adhDodChar4;
    }

    public Character getAdhDodChar5() {
        return adhDodChar5;
    }

    public void setAdhDodChar5(Character adhDodChar5) {
        this.adhDodChar5 = adhDodChar5;
    }

    public Character getAdhDodChar6() {
        return adhDodChar6;
    }

    public void setAdhDodChar6(Character adhDodChar6) {
        this.adhDodChar6 = adhDodChar6;
    }

    public Character getAdhDodChar7() {
        return adhDodChar7;
    }

    public void setAdhDodChar7(Character adhDodChar7) {
        this.adhDodChar7 = adhDodChar7;
    }

    public Character getAdhDodChar8() {
        return adhDodChar8;
    }

    public void setAdhDodChar8(Character adhDodChar8) {
        this.adhDodChar8 = adhDodChar8;
    }

    public Date getAdhDodDate1() {
        return adhDodDate1;
    }

    public void setAdhDodDate1(Date adhDodDate1) {
        this.adhDodDate1 = adhDodDate1;
    }

    public Date getAdhDodDate2() {
        return adhDodDate2;
    }

    public void setAdhDodDate2(Date adhDodDate2) {
        this.adhDodDate2 = adhDodDate2;
    }

    public Integer getAdhDodInt1() {
        return adhDodInt1;
    }

    public void setAdhDodInt1(Integer adhDodInt1) {
        this.adhDodInt1 = adhDodInt1;
    }

    public Integer getAdhDodInt2() {
        return adhDodInt2;
    }

    public void setAdhDodInt2(Integer adhDodInt2) {
        this.adhDodInt2 = adhDodInt2;
    }

    public BigDecimal getAdhDodNum1() {
        return adhDodNum1;
    }

    public void setAdhDodNum1(BigDecimal adhDodNum1) {
        this.adhDodNum1 = adhDodNum1;
    }

    public BigDecimal getAdhDodNum2() {
        return adhDodNum2;
    }

    public void setAdhDodNum2(BigDecimal adhDodNum2) {
        this.adhDodNum2 = adhDodNum2;
    }

    public String getAdhDodVchar1() {
        return adhDodVchar1;
    }

    public void setAdhDodVchar1(String adhDodVchar1) {
        this.adhDodVchar1 = adhDodVchar1;
    }

    public String getAdhDodVchar2() {
        return adhDodVchar2;
    }

    public void setAdhDodVchar2(String adhDodVchar2) {
        this.adhDodVchar2 = adhDodVchar2;
    }

    public Character getAdhStatus() {
        return adhStatus;
    }

    public void setAdhStatus(Character adhStatus) {
        this.adhStatus = adhStatus;
    }

    public Firma getAdhFirSerial() {
        return adhFirSerial;
    }

    public void setAdhFirSerial(Firma adhFirSerial) {
        this.adhFirSerial = adhFirSerial;
    }

    public Kontrahent getAdhKonSerial() {
        return adhKonSerial;
    }

    public void setAdhKonSerial(Kontrahent adhKonSerial) {
        this.adhKonSerial = adhKonSerial;
    }

    public Miasto getAdhMiaSerial() {
        return adhMiaSerial;
    }

    public void setAdhMiaSerial(Miasto adhMiaSerial) {
        this.adhMiaSerial = adhMiaSerial;
    }

    public Osoba getAdhOsoSerial() {
        return adhOsoSerial;
    }

    public void setAdhOsoSerial(Osoba adhOsoSerial) {
        this.adhOsoSerial = adhOsoSerial;
    }

    public OsobaRod getAdhOsrSerial() {
        return adhOsrSerial;
    }

    public void setAdhOsrSerial(OsobaRod adhOsrSerial) {
        this.adhOsrSerial = adhOsrSerial;
    }

    public Panstwo getAdhPanSerial() {
        return adhPanSerial;
    }

    public void setAdhPanSerial(Panstwo adhPanSerial) {
        this.adhPanSerial = adhPanSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adhSerial != null ? adhSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdresHist)) {
            return false;
        }
        AdresHist other = (AdresHist) object;
        if ((this.adhSerial == null && other.adhSerial != null) || (this.adhSerial != null && !this.adhSerial.equals(other.adhSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.AdresHist[ adhSerial=" + adhSerial + " ]";
    }
    
}
