/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
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
@Table(name = "dane_li_sl", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DaneLiSl.findAll", query = "SELECT d FROM DaneLiSl d"),
    @NamedQuery(name = "DaneLiSl.findByDlsSerial", query = "SELECT d FROM DaneLiSl d WHERE d.dlsSerial = :dlsSerial"),
    @NamedQuery(name = "DaneLiSl.findByDlsTyp", query = "SELECT d FROM DaneLiSl d WHERE d.dlsTyp = :dlsTyp"),
    @NamedQuery(name = "DaneLiSl.findByDlsOpis", query = "SELECT d FROM DaneLiSl d WHERE d.dlsOpis = :dlsOpis"),
    @NamedQuery(name = "DaneLiSl.findByDlsRodzaj", query = "SELECT d FROM DaneLiSl d WHERE d.dlsRodzaj = :dlsRodzaj"),
    @NamedQuery(name = "DaneLiSl.findByDlsSystem", query = "SELECT d FROM DaneLiSl d WHERE d.dlsSystem = :dlsSystem"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodChar1", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodChar1 = :dlsDodChar1"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodChar2", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodChar2 = :dlsDodChar2"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodInt1", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodInt1 = :dlsDodInt1"),
    @NamedQuery(name = "DaneLiSl.findByDlsKto", query = "SELECT d FROM DaneLiSl d WHERE d.dlsKto = :dlsKto"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodVchar1", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodVchar1 = :dlsDodVchar1"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodVchar2", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodVchar2 = :dlsDodVchar2"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodInt2", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodInt2 = :dlsDodInt2"),
    @NamedQuery(name = "DaneLiSl.findByDlsDataOd", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDataOd = :dlsDataOd"),
    @NamedQuery(name = "DaneLiSl.findByDlsDataDo", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDataDo = :dlsDataDo"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodData1", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodData1 = :dlsDodData1"),
    @NamedQuery(name = "DaneLiSl.findByDlsDodData2", query = "SELECT d FROM DaneLiSl d WHERE d.dlsDodData2 = :dlsDodData2"),
    @NamedQuery(name = "DaneLiSl.findByDlsRecord", query = "SELECT d FROM DaneLiSl d WHERE d.dlsRecord = :dlsRecord")})
public class DaneLiSl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dls_serial", nullable = false)
    private Integer dlsSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dls_typ", nullable = false)
    private Character dlsTyp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "dls_opis", nullable = false, length = 128)
    private String dlsOpis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dls_rodzaj", nullable = false)
    private Character dlsRodzaj;
    @Column(name = "dls_system")
    private Character dlsSystem;
    @Column(name = "dls_dod_char_1")
    private Character dlsDodChar1;
    @Column(name = "dls_dod_char_2")
    private Character dlsDodChar2;
    @Column(name = "dls_dod_int_1")
    private Integer dlsDodInt1;
    @Column(name = "dls_kto")
    private Character dlsKto;
    @Size(max = 128)
    @Column(name = "dls_dod_vchar_1", length = 128)
    private String dlsDodVchar1;
    @Size(max = 128)
    @Column(name = "dls_dod_vchar_2", length = 128)
    private String dlsDodVchar2;
    @Column(name = "dls_dod_int_2")
    private Integer dlsDodInt2;
    @Column(name = "dls_data_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlsDataOd;
    @Column(name = "dls_data_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlsDataDo;
    @Column(name = "dls_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlsDodData1;
    @Column(name = "dls_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dlsDodData2;
    @Size(max = 4)
    @Column(name = "dls_record", length = 4)
    private String dlsRecord;
    @OneToMany(mappedBy = "dliDlsSerial")
    private List<DaneLiDa> daneLiDaList;
    @OneToMany(mappedBy = "damDlsSerial")
    private List<DaneStDaM> daneStDaMList;
    @OneToMany(mappedBy = "dhmDlsSerial")
    private List<DaneHiDaM> daneHiDaMList;
    @OneToMany(mappedBy = "dhxDlsSerial")
    private List<DaneHiDaX> daneHiDaXList;
    @JoinColumn(name = "dls_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma dlsFirSerial;
    @OneToMany(mappedBy = "dhiDlsSerial")
    private List<DaneHiDa> daneHiDaList;
    @OneToMany(mappedBy = "dlmDlsSerial")
    private List<DaneLiDaM> daneLiDaMList;
    @OneToMany(mappedBy = "dlxDlsSerial")
    private List<DaneLiDaX> daneLiDaXList;
    @OneToMany(mappedBy = "dasDlsSerial")
    private List<DaneStDa> daneStDaList;
    @OneToMany(mappedBy = "daxDlsSerial")
    private List<DaneStDaX> daneStDaXList;

    public DaneLiSl() {
    }

    public DaneLiSl(Integer dlsSerial) {
        this.dlsSerial = dlsSerial;
    }

    public DaneLiSl(Integer dlsSerial, Character dlsTyp, String dlsOpis, Character dlsRodzaj) {
        this.dlsSerial = dlsSerial;
        this.dlsTyp = dlsTyp;
        this.dlsOpis = dlsOpis;
        this.dlsRodzaj = dlsRodzaj;
    }

    public Integer getDlsSerial() {
        return dlsSerial;
    }

    public void setDlsSerial(Integer dlsSerial) {
        this.dlsSerial = dlsSerial;
    }

    public Character getDlsTyp() {
        return dlsTyp;
    }

    public void setDlsTyp(Character dlsTyp) {
        this.dlsTyp = dlsTyp;
    }

    public String getDlsOpis() {
        return dlsOpis;
    }

    public void setDlsOpis(String dlsOpis) {
        this.dlsOpis = dlsOpis;
    }

    public Character getDlsRodzaj() {
        return dlsRodzaj;
    }

    public void setDlsRodzaj(Character dlsRodzaj) {
        this.dlsRodzaj = dlsRodzaj;
    }

    public Character getDlsSystem() {
        return dlsSystem;
    }

    public void setDlsSystem(Character dlsSystem) {
        this.dlsSystem = dlsSystem;
    }

    public Character getDlsDodChar1() {
        return dlsDodChar1;
    }

    public void setDlsDodChar1(Character dlsDodChar1) {
        this.dlsDodChar1 = dlsDodChar1;
    }

    public Character getDlsDodChar2() {
        return dlsDodChar2;
    }

    public void setDlsDodChar2(Character dlsDodChar2) {
        this.dlsDodChar2 = dlsDodChar2;
    }

    public Integer getDlsDodInt1() {
        return dlsDodInt1;
    }

    public void setDlsDodInt1(Integer dlsDodInt1) {
        this.dlsDodInt1 = dlsDodInt1;
    }

    public Character getDlsKto() {
        return dlsKto;
    }

    public void setDlsKto(Character dlsKto) {
        this.dlsKto = dlsKto;
    }

    public String getDlsDodVchar1() {
        return dlsDodVchar1;
    }

    public void setDlsDodVchar1(String dlsDodVchar1) {
        this.dlsDodVchar1 = dlsDodVchar1;
    }

    public String getDlsDodVchar2() {
        return dlsDodVchar2;
    }

    public void setDlsDodVchar2(String dlsDodVchar2) {
        this.dlsDodVchar2 = dlsDodVchar2;
    }

    public Integer getDlsDodInt2() {
        return dlsDodInt2;
    }

    public void setDlsDodInt2(Integer dlsDodInt2) {
        this.dlsDodInt2 = dlsDodInt2;
    }

    public Date getDlsDataOd() {
        return dlsDataOd;
    }

    public void setDlsDataOd(Date dlsDataOd) {
        this.dlsDataOd = dlsDataOd;
    }

    public Date getDlsDataDo() {
        return dlsDataDo;
    }

    public void setDlsDataDo(Date dlsDataDo) {
        this.dlsDataDo = dlsDataDo;
    }

    public Date getDlsDodData1() {
        return dlsDodData1;
    }

    public void setDlsDodData1(Date dlsDodData1) {
        this.dlsDodData1 = dlsDodData1;
    }

    public Date getDlsDodData2() {
        return dlsDodData2;
    }

    public void setDlsDodData2(Date dlsDodData2) {
        this.dlsDodData2 = dlsDodData2;
    }

    public String getDlsRecord() {
        return dlsRecord;
    }

    public void setDlsRecord(String dlsRecord) {
        this.dlsRecord = dlsRecord;
    }

    @XmlTransient
    public List<DaneLiDa> getDaneLiDaList() {
        return daneLiDaList;
    }

    public void setDaneLiDaList(List<DaneLiDa> daneLiDaList) {
        this.daneLiDaList = daneLiDaList;
    }

    @XmlTransient
    public List<DaneStDaM> getDaneStDaMList() {
        return daneStDaMList;
    }

    public void setDaneStDaMList(List<DaneStDaM> daneStDaMList) {
        this.daneStDaMList = daneStDaMList;
    }

    @XmlTransient
    public List<DaneHiDaM> getDaneHiDaMList() {
        return daneHiDaMList;
    }

    public void setDaneHiDaMList(List<DaneHiDaM> daneHiDaMList) {
        this.daneHiDaMList = daneHiDaMList;
    }

    @XmlTransient
    public List<DaneHiDaX> getDaneHiDaXList() {
        return daneHiDaXList;
    }

    public void setDaneHiDaXList(List<DaneHiDaX> daneHiDaXList) {
        this.daneHiDaXList = daneHiDaXList;
    }

    public Firma getDlsFirSerial() {
        return dlsFirSerial;
    }

    public void setDlsFirSerial(Firma dlsFirSerial) {
        this.dlsFirSerial = dlsFirSerial;
    }

    @XmlTransient
    public List<DaneHiDa> getDaneHiDaList() {
        return daneHiDaList;
    }

    public void setDaneHiDaList(List<DaneHiDa> daneHiDaList) {
        this.daneHiDaList = daneHiDaList;
    }

    @XmlTransient
    public List<DaneLiDaM> getDaneLiDaMList() {
        return daneLiDaMList;
    }

    public void setDaneLiDaMList(List<DaneLiDaM> daneLiDaMList) {
        this.daneLiDaMList = daneLiDaMList;
    }

    @XmlTransient
    public List<DaneLiDaX> getDaneLiDaXList() {
        return daneLiDaXList;
    }

    public void setDaneLiDaXList(List<DaneLiDaX> daneLiDaXList) {
        this.daneLiDaXList = daneLiDaXList;
    }

    @XmlTransient
    public List<DaneStDa> getDaneStDaList() {
        return daneStDaList;
    }

    public void setDaneStDaList(List<DaneStDa> daneStDaList) {
        this.daneStDaList = daneStDaList;
    }

    @XmlTransient
    public List<DaneStDaX> getDaneStDaXList() {
        return daneStDaXList;
    }

    public void setDaneStDaXList(List<DaneStDaX> daneStDaXList) {
        this.daneStDaXList = daneStDaXList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dlsSerial != null ? dlsSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DaneLiSl)) {
            return false;
        }
        DaneLiSl other = (DaneLiSl) object;
        if ((this.dlsSerial == null && other.dlsSerial != null) || (this.dlsSerial != null && !this.dlsSerial.equals(other.dlsSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.DaneLiSl[ dlsSerial=" + dlsSerial + " ]";
    }
    
}
