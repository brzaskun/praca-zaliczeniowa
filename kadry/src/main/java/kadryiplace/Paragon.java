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
@Table(name = "paragon", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Paragon.findAll", query = "SELECT p FROM Paragon p"),
    @NamedQuery(name = "Paragon.findByParSerial", query = "SELECT p FROM Paragon p WHERE p.parSerial = :parSerial"),
    @NamedQuery(name = "Paragon.findByParLp", query = "SELECT p FROM Paragon p WHERE p.parLp = :parLp"),
    @NamedQuery(name = "Paragon.findByParData", query = "SELECT p FROM Paragon p WHERE p.parData = :parData"),
    @NamedQuery(name = "Paragon.findByParCzas", query = "SELECT p FROM Paragon p WHERE p.parCzas = :parCzas"),
    @NamedQuery(name = "Paragon.findByParUsrUserid", query = "SELECT p FROM Paragon p WHERE p.parUsrUserid = :parUsrUserid"),
    @NamedQuery(name = "Paragon.findByParKwota", query = "SELECT p FROM Paragon p WHERE p.parKwota = :parKwota"),
    @NamedQuery(name = "Paragon.findByParUsrKasjer", query = "SELECT p FROM Paragon p WHERE p.parUsrKasjer = :parUsrKasjer"),
    @NamedQuery(name = "Paragon.findByParNumer", query = "SELECT p FROM Paragon p WHERE p.parNumer = :parNumer"),
    @NamedQuery(name = "Paragon.findByParUserName", query = "SELECT p FROM Paragon p WHERE p.parUserName = :parUserName"),
    @NamedQuery(name = "Paragon.findByParDataDod1", query = "SELECT p FROM Paragon p WHERE p.parDataDod1 = :parDataDod1"),
    @NamedQuery(name = "Paragon.findByParCzasDod1", query = "SELECT p FROM Paragon p WHERE p.parCzasDod1 = :parCzasDod1"),
    @NamedQuery(name = "Paragon.findByParChar1", query = "SELECT p FROM Paragon p WHERE p.parChar1 = :parChar1"),
    @NamedQuery(name = "Paragon.findByParChar2", query = "SELECT p FROM Paragon p WHERE p.parChar2 = :parChar2"),
    @NamedQuery(name = "Paragon.findByParIni1", query = "SELECT p FROM Paragon p WHERE p.parIni1 = :parIni1"),
    @NamedQuery(name = "Paragon.findByParNum1", query = "SELECT p FROM Paragon p WHERE p.parNum1 = :parNum1"),
    @NamedQuery(name = "Paragon.findByParNum2", query = "SELECT p FROM Paragon p WHERE p.parNum2 = :parNum2"),
    @NamedQuery(name = "Paragon.findByParVchar1", query = "SELECT p FROM Paragon p WHERE p.parVchar1 = :parVchar1"),
    @NamedQuery(name = "Paragon.findByParDate2", query = "SELECT p FROM Paragon p WHERE p.parDate2 = :parDate2"),
    @NamedQuery(name = "Paragon.findByParTyp", query = "SELECT p FROM Paragon p WHERE p.parTyp = :parTyp"),
    @NamedQuery(name = "Paragon.findByParSposobRej", query = "SELECT p FROM Paragon p WHERE p.parSposobRej = :parSposobRej"),
    @NamedQuery(name = "Paragon.findByParNum3", query = "SELECT p FROM Paragon p WHERE p.parNum3 = :parNum3"),
    @NamedQuery(name = "Paragon.findByParNum4", query = "SELECT p FROM Paragon p WHERE p.parNum4 = :parNum4")})
public class Paragon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_serial", nullable = false)
    private Integer parSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_lp", nullable = false)
    private int parLp;
    @Column(name = "par_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parData;
    @Column(name = "par_czas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parCzas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "par_usr_userid", nullable = false, length = 32)
    private String parUsrUserid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "par_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal parKwota;
    @Size(max = 16)
    @Column(name = "par_usr_kasjer", length = 16)
    private String parUsrKasjer;
    @Column(name = "par_numer")
    private Integer parNumer;
    @Size(max = 64)
    @Column(name = "par_user_name", length = 64)
    private String parUserName;
    @Column(name = "par_data_dod_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parDataDod1;
    @Column(name = "par_czas_dod_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parCzasDod1;
    @Column(name = "par_char_1")
    private Character parChar1;
    @Column(name = "par_char_2")
    private Character parChar2;
    @Column(name = "par_ini_1")
    private Integer parIni1;
    @Column(name = "par_num_1", precision = 17, scale = 6)
    private BigDecimal parNum1;
    @Column(name = "par_num_2", precision = 17, scale = 6)
    private BigDecimal parNum2;
    @Size(max = 64)
    @Column(name = "par_vchar_1", length = 64)
    private String parVchar1;
    @Column(name = "par_date_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date parDate2;
    @Column(name = "par_typ")
    private Character parTyp;
    @Column(name = "par_sposob_rej")
    private Character parSposobRej;
    @Column(name = "par_num_3", precision = 5, scale = 2)
    private BigDecimal parNum3;
    @Column(name = "par_num_4", precision = 13, scale = 2)
    private BigDecimal parNum4;
    @JoinColumn(name = "par_for_serial", referencedColumnName = "for_serial")
    @ManyToOne
    private Formyzap parForSerial;
    @JoinColumn(name = "par_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres parOkrSerial;
    @JoinColumn(name = "par_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok parRokSerial;
    @OneToMany(mappedBy = "paeParSerial")
    private List<ParagonE> paragonEList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pakParSerial")
    private List<ParagonK> paragonKList;
    @OneToMany(mappedBy = "dsaParSerial")
    private List<DaneStatA> daneStatAList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pazParSerial")
    private List<Parpoz> parpozList;
    @OneToMany(mappedBy = "fakParSerial")
    private List<Fakrach> fakrachList;

    public Paragon() {
    }

    public Paragon(Integer parSerial) {
        this.parSerial = parSerial;
    }

    public Paragon(Integer parSerial, int parLp, String parUsrUserid, BigDecimal parKwota) {
        this.parSerial = parSerial;
        this.parLp = parLp;
        this.parUsrUserid = parUsrUserid;
        this.parKwota = parKwota;
    }

    public Integer getParSerial() {
        return parSerial;
    }

    public void setParSerial(Integer parSerial) {
        this.parSerial = parSerial;
    }

    public int getParLp() {
        return parLp;
    }

    public void setParLp(int parLp) {
        this.parLp = parLp;
    }

    public Date getParData() {
        return parData;
    }

    public void setParData(Date parData) {
        this.parData = parData;
    }

    public Date getParCzas() {
        return parCzas;
    }

    public void setParCzas(Date parCzas) {
        this.parCzas = parCzas;
    }

    public String getParUsrUserid() {
        return parUsrUserid;
    }

    public void setParUsrUserid(String parUsrUserid) {
        this.parUsrUserid = parUsrUserid;
    }

    public BigDecimal getParKwota() {
        return parKwota;
    }

    public void setParKwota(BigDecimal parKwota) {
        this.parKwota = parKwota;
    }

    public String getParUsrKasjer() {
        return parUsrKasjer;
    }

    public void setParUsrKasjer(String parUsrKasjer) {
        this.parUsrKasjer = parUsrKasjer;
    }

    public Integer getParNumer() {
        return parNumer;
    }

    public void setParNumer(Integer parNumer) {
        this.parNumer = parNumer;
    }

    public String getParUserName() {
        return parUserName;
    }

    public void setParUserName(String parUserName) {
        this.parUserName = parUserName;
    }

    public Date getParDataDod1() {
        return parDataDod1;
    }

    public void setParDataDod1(Date parDataDod1) {
        this.parDataDod1 = parDataDod1;
    }

    public Date getParCzasDod1() {
        return parCzasDod1;
    }

    public void setParCzasDod1(Date parCzasDod1) {
        this.parCzasDod1 = parCzasDod1;
    }

    public Character getParChar1() {
        return parChar1;
    }

    public void setParChar1(Character parChar1) {
        this.parChar1 = parChar1;
    }

    public Character getParChar2() {
        return parChar2;
    }

    public void setParChar2(Character parChar2) {
        this.parChar2 = parChar2;
    }

    public Integer getParIni1() {
        return parIni1;
    }

    public void setParIni1(Integer parIni1) {
        this.parIni1 = parIni1;
    }

    public BigDecimal getParNum1() {
        return parNum1;
    }

    public void setParNum1(BigDecimal parNum1) {
        this.parNum1 = parNum1;
    }

    public BigDecimal getParNum2() {
        return parNum2;
    }

    public void setParNum2(BigDecimal parNum2) {
        this.parNum2 = parNum2;
    }

    public String getParVchar1() {
        return parVchar1;
    }

    public void setParVchar1(String parVchar1) {
        this.parVchar1 = parVchar1;
    }

    public Date getParDate2() {
        return parDate2;
    }

    public void setParDate2(Date parDate2) {
        this.parDate2 = parDate2;
    }

    public Character getParTyp() {
        return parTyp;
    }

    public void setParTyp(Character parTyp) {
        this.parTyp = parTyp;
    }

    public Character getParSposobRej() {
        return parSposobRej;
    }

    public void setParSposobRej(Character parSposobRej) {
        this.parSposobRej = parSposobRej;
    }

    public BigDecimal getParNum3() {
        return parNum3;
    }

    public void setParNum3(BigDecimal parNum3) {
        this.parNum3 = parNum3;
    }

    public BigDecimal getParNum4() {
        return parNum4;
    }

    public void setParNum4(BigDecimal parNum4) {
        this.parNum4 = parNum4;
    }

    public Formyzap getParForSerial() {
        return parForSerial;
    }

    public void setParForSerial(Formyzap parForSerial) {
        this.parForSerial = parForSerial;
    }

    public Okres getParOkrSerial() {
        return parOkrSerial;
    }

    public void setParOkrSerial(Okres parOkrSerial) {
        this.parOkrSerial = parOkrSerial;
    }

    public Rok getParRokSerial() {
        return parRokSerial;
    }

    public void setParRokSerial(Rok parRokSerial) {
        this.parRokSerial = parRokSerial;
    }

    @XmlTransient
    public List<ParagonE> getParagonEList() {
        return paragonEList;
    }

    public void setParagonEList(List<ParagonE> paragonEList) {
        this.paragonEList = paragonEList;
    }

    @XmlTransient
    public List<ParagonK> getParagonKList() {
        return paragonKList;
    }

    public void setParagonKList(List<ParagonK> paragonKList) {
        this.paragonKList = paragonKList;
    }

    @XmlTransient
    public List<DaneStatA> getDaneStatAList() {
        return daneStatAList;
    }

    public void setDaneStatAList(List<DaneStatA> daneStatAList) {
        this.daneStatAList = daneStatAList;
    }

    @XmlTransient
    public List<Parpoz> getParpozList() {
        return parpozList;
    }

    public void setParpozList(List<Parpoz> parpozList) {
        this.parpozList = parpozList;
    }

    @XmlTransient
    public List<Fakrach> getFakrachList() {
        return fakrachList;
    }

    public void setFakrachList(List<Fakrach> fakrachList) {
        this.fakrachList = fakrachList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parSerial != null ? parSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paragon)) {
            return false;
        }
        Paragon other = (Paragon) object;
        if ((this.parSerial == null && other.parSerial != null) || (this.parSerial != null && !this.parSerial.equals(other.parSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Paragon[ parSerial=" + parSerial + " ]";
    }
    
}
