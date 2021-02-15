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
import javax.persistence.CascadeType;
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
@Table(name = "spisy", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spisy.findAll", query = "SELECT s FROM Spisy s"),
    @NamedQuery(name = "Spisy.findBySpiSerial", query = "SELECT s FROM Spisy s WHERE s.spiSerial = :spiSerial"),
    @NamedQuery(name = "Spisy.findBySpiOpis", query = "SELECT s FROM Spisy s WHERE s.spiOpis = :spiOpis"),
    @NamedQuery(name = "Spisy.findBySpiRodzajInw", query = "SELECT s FROM Spisy s WHERE s.spiRodzajInw = :spiRodzajInw"),
    @NamedQuery(name = "Spisy.findBySpiSposob", query = "SELECT s FROM Spisy s WHERE s.spiSposob = :spiSposob"),
    @NamedQuery(name = "Spisy.findBySpiOsobaOdp", query = "SELECT s FROM Spisy s WHERE s.spiOsobaOdp = :spiOsobaOdp"),
    @NamedQuery(name = "Spisy.findBySpiKomisja1", query = "SELECT s FROM Spisy s WHERE s.spiKomisja1 = :spiKomisja1"),
    @NamedQuery(name = "Spisy.findBySpiKomisja2", query = "SELECT s FROM Spisy s WHERE s.spiKomisja2 = :spiKomisja2"),
    @NamedQuery(name = "Spisy.findBySpiKomisja3", query = "SELECT s FROM Spisy s WHERE s.spiKomisja3 = :spiKomisja3"),
    @NamedQuery(name = "Spisy.findBySpiInne1", query = "SELECT s FROM Spisy s WHERE s.spiInne1 = :spiInne1"),
    @NamedQuery(name = "Spisy.findBySpiInne2", query = "SELECT s FROM Spisy s WHERE s.spiInne2 = :spiInne2"),
    @NamedQuery(name = "Spisy.findBySpiInne3", query = "SELECT s FROM Spisy s WHERE s.spiInne3 = :spiInne3"),
    @NamedQuery(name = "Spisy.findBySpiRozp", query = "SELECT s FROM Spisy s WHERE s.spiRozp = :spiRozp"),
    @NamedQuery(name = "Spisy.findBySpiZak", query = "SELECT s FROM Spisy s WHERE s.spiZak = :spiZak"),
    @NamedQuery(name = "Spisy.findBySpiWycenil", query = "SELECT s FROM Spisy s WHERE s.spiWycenil = :spiWycenil"),
    @NamedQuery(name = "Spisy.findBySpiSprawdzil", query = "SELECT s FROM Spisy s WHERE s.spiSprawdzil = :spiSprawdzil"),
    @NamedQuery(name = "Spisy.findBySpiChar1", query = "SELECT s FROM Spisy s WHERE s.spiChar1 = :spiChar1"),
    @NamedQuery(name = "Spisy.findBySpiChar2", query = "SELECT s FROM Spisy s WHERE s.spiChar2 = :spiChar2"),
    @NamedQuery(name = "Spisy.findBySpiChar3", query = "SELECT s FROM Spisy s WHERE s.spiChar3 = :spiChar3"),
    @NamedQuery(name = "Spisy.findBySpiChar4", query = "SELECT s FROM Spisy s WHERE s.spiChar4 = :spiChar4"),
    @NamedQuery(name = "Spisy.findBySpiData1", query = "SELECT s FROM Spisy s WHERE s.spiData1 = :spiData1"),
    @NamedQuery(name = "Spisy.findBySpiData2", query = "SELECT s FROM Spisy s WHERE s.spiData2 = :spiData2"),
    @NamedQuery(name = "Spisy.findBySpiData3", query = "SELECT s FROM Spisy s WHERE s.spiData3 = :spiData3"),
    @NamedQuery(name = "Spisy.findBySpiData4", query = "SELECT s FROM Spisy s WHERE s.spiData4 = :spiData4"),
    @NamedQuery(name = "Spisy.findBySpiVchar1", query = "SELECT s FROM Spisy s WHERE s.spiVchar1 = :spiVchar1"),
    @NamedQuery(name = "Spisy.findBySpiVchar2", query = "SELECT s FROM Spisy s WHERE s.spiVchar2 = :spiVchar2"),
    @NamedQuery(name = "Spisy.findBySpiVchar3", query = "SELECT s FROM Spisy s WHERE s.spiVchar3 = :spiVchar3"),
    @NamedQuery(name = "Spisy.findBySpiVchar4", query = "SELECT s FROM Spisy s WHERE s.spiVchar4 = :spiVchar4"),
    @NamedQuery(name = "Spisy.findBySpiInt1", query = "SELECT s FROM Spisy s WHERE s.spiInt1 = :spiInt1"),
    @NamedQuery(name = "Spisy.findBySpiInt2", query = "SELECT s FROM Spisy s WHERE s.spiInt2 = :spiInt2"),
    @NamedQuery(name = "Spisy.findBySpiNum1", query = "SELECT s FROM Spisy s WHERE s.spiNum1 = :spiNum1"),
    @NamedQuery(name = "Spisy.findBySpiNum2", query = "SELECT s FROM Spisy s WHERE s.spiNum2 = :spiNum2"),
    @NamedQuery(name = "Spisy.findBySpiTyp", query = "SELECT s FROM Spisy s WHERE s.spiTyp = :spiTyp")})
public class Spisy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "spi_serial", nullable = false)
    private Integer spiSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_opis", nullable = false, length = 64)
    private String spiOpis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_rodzaj_inw", nullable = false, length = 64)
    private String spiRodzajInw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_sposob", nullable = false, length = 64)
    private String spiSposob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_osoba_odp", nullable = false, length = 64)
    private String spiOsobaOdp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_komisja_1", nullable = false, length = 64)
    private String spiKomisja1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_komisja_2", nullable = false, length = 64)
    private String spiKomisja2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_komisja_3", nullable = false, length = 64)
    private String spiKomisja3;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_inne_1", nullable = false, length = 64)
    private String spiInne1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_inne_2", nullable = false, length = 64)
    private String spiInne2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_inne_3", nullable = false, length = 64)
    private String spiInne3;
    @Column(name = "spi_rozp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiRozp;
    @Column(name = "spi_zak")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiZak;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_wycenil", nullable = false, length = 64)
    private String spiWycenil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "spi_sprawdzil", nullable = false, length = 64)
    private String spiSprawdzil;
    @Column(name = "spi_char_1")
    private Character spiChar1;
    @Column(name = "spi_char_2")
    private Character spiChar2;
    @Column(name = "spi_char_3")
    private Character spiChar3;
    @Column(name = "spi_char_4")
    private Character spiChar4;
    @Column(name = "spi_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiData1;
    @Column(name = "spi_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiData2;
    @Column(name = "spi_data_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiData3;
    @Column(name = "spi_data_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date spiData4;
    @Size(max = 64)
    @Column(name = "spi_vchar_1", length = 64)
    private String spiVchar1;
    @Size(max = 64)
    @Column(name = "spi_vchar_2", length = 64)
    private String spiVchar2;
    @Size(max = 64)
    @Column(name = "spi_vchar_3", length = 64)
    private String spiVchar3;
    @Size(max = 64)
    @Column(name = "spi_vchar_4", length = 64)
    private String spiVchar4;
    @Column(name = "spi_int_1")
    private Integer spiInt1;
    @Column(name = "spi_int_2")
    private Integer spiInt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "spi_num_1", precision = 17, scale = 6)
    private BigDecimal spiNum1;
    @Column(name = "spi_num_2", precision = 17, scale = 6)
    private BigDecimal spiNum2;
    @Column(name = "spi_typ")
    private Character spiTyp;
    @JoinColumn(name = "spi_okr_serial", referencedColumnName = "okr_serial")
    @ManyToOne
    private Okres spiOkrSerial;
    @JoinColumn(name = "spi_rok_serial", referencedColumnName = "rok_serial")
    @ManyToOne
    private Rok spiRokSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sznSpiSerial")
    private List<Spisznat> spisznatList;

    public Spisy() {
    }

    public Spisy(Integer spiSerial) {
        this.spiSerial = spiSerial;
    }

    public Spisy(Integer spiSerial, String spiOpis, String spiRodzajInw, String spiSposob, String spiOsobaOdp, String spiKomisja1, String spiKomisja2, String spiKomisja3, String spiInne1, String spiInne2, String spiInne3, String spiWycenil, String spiSprawdzil) {
        this.spiSerial = spiSerial;
        this.spiOpis = spiOpis;
        this.spiRodzajInw = spiRodzajInw;
        this.spiSposob = spiSposob;
        this.spiOsobaOdp = spiOsobaOdp;
        this.spiKomisja1 = spiKomisja1;
        this.spiKomisja2 = spiKomisja2;
        this.spiKomisja3 = spiKomisja3;
        this.spiInne1 = spiInne1;
        this.spiInne2 = spiInne2;
        this.spiInne3 = spiInne3;
        this.spiWycenil = spiWycenil;
        this.spiSprawdzil = spiSprawdzil;
    }

    public Integer getSpiSerial() {
        return spiSerial;
    }

    public void setSpiSerial(Integer spiSerial) {
        this.spiSerial = spiSerial;
    }

    public String getSpiOpis() {
        return spiOpis;
    }

    public void setSpiOpis(String spiOpis) {
        this.spiOpis = spiOpis;
    }

    public String getSpiRodzajInw() {
        return spiRodzajInw;
    }

    public void setSpiRodzajInw(String spiRodzajInw) {
        this.spiRodzajInw = spiRodzajInw;
    }

    public String getSpiSposob() {
        return spiSposob;
    }

    public void setSpiSposob(String spiSposob) {
        this.spiSposob = spiSposob;
    }

    public String getSpiOsobaOdp() {
        return spiOsobaOdp;
    }

    public void setSpiOsobaOdp(String spiOsobaOdp) {
        this.spiOsobaOdp = spiOsobaOdp;
    }

    public String getSpiKomisja1() {
        return spiKomisja1;
    }

    public void setSpiKomisja1(String spiKomisja1) {
        this.spiKomisja1 = spiKomisja1;
    }

    public String getSpiKomisja2() {
        return spiKomisja2;
    }

    public void setSpiKomisja2(String spiKomisja2) {
        this.spiKomisja2 = spiKomisja2;
    }

    public String getSpiKomisja3() {
        return spiKomisja3;
    }

    public void setSpiKomisja3(String spiKomisja3) {
        this.spiKomisja3 = spiKomisja3;
    }

    public String getSpiInne1() {
        return spiInne1;
    }

    public void setSpiInne1(String spiInne1) {
        this.spiInne1 = spiInne1;
    }

    public String getSpiInne2() {
        return spiInne2;
    }

    public void setSpiInne2(String spiInne2) {
        this.spiInne2 = spiInne2;
    }

    public String getSpiInne3() {
        return spiInne3;
    }

    public void setSpiInne3(String spiInne3) {
        this.spiInne3 = spiInne3;
    }

    public Date getSpiRozp() {
        return spiRozp;
    }

    public void setSpiRozp(Date spiRozp) {
        this.spiRozp = spiRozp;
    }

    public Date getSpiZak() {
        return spiZak;
    }

    public void setSpiZak(Date spiZak) {
        this.spiZak = spiZak;
    }

    public String getSpiWycenil() {
        return spiWycenil;
    }

    public void setSpiWycenil(String spiWycenil) {
        this.spiWycenil = spiWycenil;
    }

    public String getSpiSprawdzil() {
        return spiSprawdzil;
    }

    public void setSpiSprawdzil(String spiSprawdzil) {
        this.spiSprawdzil = spiSprawdzil;
    }

    public Character getSpiChar1() {
        return spiChar1;
    }

    public void setSpiChar1(Character spiChar1) {
        this.spiChar1 = spiChar1;
    }

    public Character getSpiChar2() {
        return spiChar2;
    }

    public void setSpiChar2(Character spiChar2) {
        this.spiChar2 = spiChar2;
    }

    public Character getSpiChar3() {
        return spiChar3;
    }

    public void setSpiChar3(Character spiChar3) {
        this.spiChar3 = spiChar3;
    }

    public Character getSpiChar4() {
        return spiChar4;
    }

    public void setSpiChar4(Character spiChar4) {
        this.spiChar4 = spiChar4;
    }

    public Date getSpiData1() {
        return spiData1;
    }

    public void setSpiData1(Date spiData1) {
        this.spiData1 = spiData1;
    }

    public Date getSpiData2() {
        return spiData2;
    }

    public void setSpiData2(Date spiData2) {
        this.spiData2 = spiData2;
    }

    public Date getSpiData3() {
        return spiData3;
    }

    public void setSpiData3(Date spiData3) {
        this.spiData3 = spiData3;
    }

    public Date getSpiData4() {
        return spiData4;
    }

    public void setSpiData4(Date spiData4) {
        this.spiData4 = spiData4;
    }

    public String getSpiVchar1() {
        return spiVchar1;
    }

    public void setSpiVchar1(String spiVchar1) {
        this.spiVchar1 = spiVchar1;
    }

    public String getSpiVchar2() {
        return spiVchar2;
    }

    public void setSpiVchar2(String spiVchar2) {
        this.spiVchar2 = spiVchar2;
    }

    public String getSpiVchar3() {
        return spiVchar3;
    }

    public void setSpiVchar3(String spiVchar3) {
        this.spiVchar3 = spiVchar3;
    }

    public String getSpiVchar4() {
        return spiVchar4;
    }

    public void setSpiVchar4(String spiVchar4) {
        this.spiVchar4 = spiVchar4;
    }

    public Integer getSpiInt1() {
        return spiInt1;
    }

    public void setSpiInt1(Integer spiInt1) {
        this.spiInt1 = spiInt1;
    }

    public Integer getSpiInt2() {
        return spiInt2;
    }

    public void setSpiInt2(Integer spiInt2) {
        this.spiInt2 = spiInt2;
    }

    public BigDecimal getSpiNum1() {
        return spiNum1;
    }

    public void setSpiNum1(BigDecimal spiNum1) {
        this.spiNum1 = spiNum1;
    }

    public BigDecimal getSpiNum2() {
        return spiNum2;
    }

    public void setSpiNum2(BigDecimal spiNum2) {
        this.spiNum2 = spiNum2;
    }

    public Character getSpiTyp() {
        return spiTyp;
    }

    public void setSpiTyp(Character spiTyp) {
        this.spiTyp = spiTyp;
    }

    public Okres getSpiOkrSerial() {
        return spiOkrSerial;
    }

    public void setSpiOkrSerial(Okres spiOkrSerial) {
        this.spiOkrSerial = spiOkrSerial;
    }

    public Rok getSpiRokSerial() {
        return spiRokSerial;
    }

    public void setSpiRokSerial(Rok spiRokSerial) {
        this.spiRokSerial = spiRokSerial;
    }

    @XmlTransient
    public List<Spisznat> getSpisznatList() {
        return spisznatList;
    }

    public void setSpisznatList(List<Spisznat> spisznatList) {
        this.spisznatList = spisznatList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spiSerial != null ? spiSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spisy)) {
            return false;
        }
        Spisy other = (Spisy) object;
        if ((this.spiSerial == null && other.spiSerial != null) || (this.spiSerial != null && !this.spiSerial.equals(other.spiSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Spisy[ spiSerial=" + spiSerial + " ]";
    }
    
}
