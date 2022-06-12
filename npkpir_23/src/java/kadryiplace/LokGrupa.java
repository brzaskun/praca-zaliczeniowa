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
@Table(name = "lok_grupa", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LokGrupa.findAll", query = "SELECT l FROM LokGrupa l"),
    @NamedQuery(name = "LokGrupa.findByLgrSerial", query = "SELECT l FROM LokGrupa l WHERE l.lgrSerial = :lgrSerial"),
    @NamedQuery(name = "LokGrupa.findByLgrNazwa", query = "SELECT l FROM LokGrupa l WHERE l.lgrNazwa = :lgrNazwa"),
    @NamedQuery(name = "LokGrupa.findByLgrKolejnosc", query = "SELECT l FROM LokGrupa l WHERE l.lgrKolejnosc = :lgrKolejnosc"),
    @NamedQuery(name = "LokGrupa.findByLgrChar1", query = "SELECT l FROM LokGrupa l WHERE l.lgrChar1 = :lgrChar1"),
    @NamedQuery(name = "LokGrupa.findByLgrChar2", query = "SELECT l FROM LokGrupa l WHERE l.lgrChar2 = :lgrChar2"),
    @NamedQuery(name = "LokGrupa.findByLgrChar3", query = "SELECT l FROM LokGrupa l WHERE l.lgrChar3 = :lgrChar3"),
    @NamedQuery(name = "LokGrupa.findByLgrChar4", query = "SELECT l FROM LokGrupa l WHERE l.lgrChar4 = :lgrChar4"),
    @NamedQuery(name = "LokGrupa.findByLgrVchar1", query = "SELECT l FROM LokGrupa l WHERE l.lgrVchar1 = :lgrVchar1"),
    @NamedQuery(name = "LokGrupa.findByLgrVchar2", query = "SELECT l FROM LokGrupa l WHERE l.lgrVchar2 = :lgrVchar2"),
    @NamedQuery(name = "LokGrupa.findByLgrInt1", query = "SELECT l FROM LokGrupa l WHERE l.lgrInt1 = :lgrInt1"),
    @NamedQuery(name = "LokGrupa.findByLgrInt2", query = "SELECT l FROM LokGrupa l WHERE l.lgrInt2 = :lgrInt2"),
    @NamedQuery(name = "LokGrupa.findByLgrData1", query = "SELECT l FROM LokGrupa l WHERE l.lgrData1 = :lgrData1"),
    @NamedQuery(name = "LokGrupa.findByLgrData2", query = "SELECT l FROM LokGrupa l WHERE l.lgrData2 = :lgrData2"),
    @NamedQuery(name = "LokGrupa.findByLgrNum1", query = "SELECT l FROM LokGrupa l WHERE l.lgrNum1 = :lgrNum1"),
    @NamedQuery(name = "LokGrupa.findByLgrNum2", query = "SELECT l FROM LokGrupa l WHERE l.lgrNum2 = :lgrNum2"),
    @NamedQuery(name = "LokGrupa.findByLgrNum3", query = "SELECT l FROM LokGrupa l WHERE l.lgrNum3 = :lgrNum3"),
    @NamedQuery(name = "LokGrupa.findByLgrNum4", query = "SELECT l FROM LokGrupa l WHERE l.lgrNum4 = :lgrNum4")})
public class LokGrupa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "lgr_serial", nullable = false)
    private Integer lgrSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "lgr_nazwa", nullable = false, length = 64)
    private String lgrNazwa;
    @Column(name = "lgr_kolejnosc")
    private Short lgrKolejnosc;
    @Column(name = "lgr_char_1")
    private Character lgrChar1;
    @Column(name = "lgr_char_2")
    private Character lgrChar2;
    @Column(name = "lgr_char_3")
    private Character lgrChar3;
    @Column(name = "lgr_char_4")
    private Character lgrChar4;
    @Size(max = 64)
    @Column(name = "lgr_vchar_1", length = 64)
    private String lgrVchar1;
    @Size(max = 64)
    @Column(name = "lgr_vchar_2", length = 64)
    private String lgrVchar2;
    @Column(name = "lgr_int_1")
    private Integer lgrInt1;
    @Column(name = "lgr_int_2")
    private Integer lgrInt2;
    @Column(name = "lgr_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lgrData1;
    @Column(name = "lgr_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lgrData2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lgr_num_1", precision = 17, scale = 6)
    private BigDecimal lgrNum1;
    @Column(name = "lgr_num_2", precision = 17, scale = 6)
    private BigDecimal lgrNum2;
    @Column(name = "lgr_num_3", precision = 17, scale = 6)
    private BigDecimal lgrNum3;
    @Column(name = "lgr_num_4", precision = 17, scale = 6)
    private BigDecimal lgrNum4;
    @JoinColumn(name = "lgr_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma lgrFirSerial;
    @OneToMany(mappedBy = "magLgrSerial")
    private List<Magazyn> magazynList;
    @OneToMany(mappedBy = "lokLgrSerial")
    private List<Lokalizacja> lokalizacjaList;

    public LokGrupa() {
    }

    public LokGrupa(Integer lgrSerial) {
        this.lgrSerial = lgrSerial;
    }

    public LokGrupa(Integer lgrSerial, String lgrNazwa) {
        this.lgrSerial = lgrSerial;
        this.lgrNazwa = lgrNazwa;
    }

    public Integer getLgrSerial() {
        return lgrSerial;
    }

    public void setLgrSerial(Integer lgrSerial) {
        this.lgrSerial = lgrSerial;
    }

    public String getLgrNazwa() {
        return lgrNazwa;
    }

    public void setLgrNazwa(String lgrNazwa) {
        this.lgrNazwa = lgrNazwa;
    }

    public Short getLgrKolejnosc() {
        return lgrKolejnosc;
    }

    public void setLgrKolejnosc(Short lgrKolejnosc) {
        this.lgrKolejnosc = lgrKolejnosc;
    }

    public Character getLgrChar1() {
        return lgrChar1;
    }

    public void setLgrChar1(Character lgrChar1) {
        this.lgrChar1 = lgrChar1;
    }

    public Character getLgrChar2() {
        return lgrChar2;
    }

    public void setLgrChar2(Character lgrChar2) {
        this.lgrChar2 = lgrChar2;
    }

    public Character getLgrChar3() {
        return lgrChar3;
    }

    public void setLgrChar3(Character lgrChar3) {
        this.lgrChar3 = lgrChar3;
    }

    public Character getLgrChar4() {
        return lgrChar4;
    }

    public void setLgrChar4(Character lgrChar4) {
        this.lgrChar4 = lgrChar4;
    }

    public String getLgrVchar1() {
        return lgrVchar1;
    }

    public void setLgrVchar1(String lgrVchar1) {
        this.lgrVchar1 = lgrVchar1;
    }

    public String getLgrVchar2() {
        return lgrVchar2;
    }

    public void setLgrVchar2(String lgrVchar2) {
        this.lgrVchar2 = lgrVchar2;
    }

    public Integer getLgrInt1() {
        return lgrInt1;
    }

    public void setLgrInt1(Integer lgrInt1) {
        this.lgrInt1 = lgrInt1;
    }

    public Integer getLgrInt2() {
        return lgrInt2;
    }

    public void setLgrInt2(Integer lgrInt2) {
        this.lgrInt2 = lgrInt2;
    }

    public Date getLgrData1() {
        return lgrData1;
    }

    public void setLgrData1(Date lgrData1) {
        this.lgrData1 = lgrData1;
    }

    public Date getLgrData2() {
        return lgrData2;
    }

    public void setLgrData2(Date lgrData2) {
        this.lgrData2 = lgrData2;
    }

    public BigDecimal getLgrNum1() {
        return lgrNum1;
    }

    public void setLgrNum1(BigDecimal lgrNum1) {
        this.lgrNum1 = lgrNum1;
    }

    public BigDecimal getLgrNum2() {
        return lgrNum2;
    }

    public void setLgrNum2(BigDecimal lgrNum2) {
        this.lgrNum2 = lgrNum2;
    }

    public BigDecimal getLgrNum3() {
        return lgrNum3;
    }

    public void setLgrNum3(BigDecimal lgrNum3) {
        this.lgrNum3 = lgrNum3;
    }

    public BigDecimal getLgrNum4() {
        return lgrNum4;
    }

    public void setLgrNum4(BigDecimal lgrNum4) {
        this.lgrNum4 = lgrNum4;
    }

    public Firma getLgrFirSerial() {
        return lgrFirSerial;
    }

    public void setLgrFirSerial(Firma lgrFirSerial) {
        this.lgrFirSerial = lgrFirSerial;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    @XmlTransient
    public List<Lokalizacja> getLokalizacjaList() {
        return lokalizacjaList;
    }

    public void setLokalizacjaList(List<Lokalizacja> lokalizacjaList) {
        this.lokalizacjaList = lokalizacjaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lgrSerial != null ? lgrSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LokGrupa)) {
            return false;
        }
        LokGrupa other = (LokGrupa) object;
        if ((this.lgrSerial == null && other.lgrSerial != null) || (this.lgrSerial != null && !this.lgrSerial.equals(other.lgrSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.LokGrupa[ lgrSerial=" + lgrSerial + " ]";
    }
    
}
