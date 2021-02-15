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
@Table(name = "rozliczus", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rozliczus.findAll", query = "SELECT r FROM Rozliczus r"),
    @NamedQuery(name = "Rozliczus.findByRzuSerial", query = "SELECT r FROM Rozliczus r WHERE r.rzuSerial = :rzuSerial"),
    @NamedQuery(name = "Rozliczus.findByRzuData", query = "SELECT r FROM Rozliczus r WHERE r.rzuData = :rzuData"),
    @NamedQuery(name = "Rozliczus.findByRzuOpis", query = "SELECT r FROM Rozliczus r WHERE r.rzuOpis = :rzuOpis"),
    @NamedQuery(name = "Rozliczus.findByRzuKwota", query = "SELECT r FROM Rozliczus r WHERE r.rzuKwota = :rzuKwota"),
    @NamedQuery(name = "Rozliczus.findByRzuOsoTyp", query = "SELECT r FROM Rozliczus r WHERE r.rzuOsoTyp = :rzuOsoTyp"),
    @NamedQuery(name = "Rozliczus.findByRzuMieNumer", query = "SELECT r FROM Rozliczus r WHERE r.rzuMieNumer = :rzuMieNumer"),
    @NamedQuery(name = "Rozliczus.findByRzuRodzaj", query = "SELECT r FROM Rozliczus r WHERE r.rzuRodzaj = :rzuRodzaj"),
    @NamedQuery(name = "Rozliczus.findByRzuTytul", query = "SELECT r FROM Rozliczus r WHERE r.rzuTytul = :rzuTytul"),
    @NamedQuery(name = "Rozliczus.findByRzuDod1", query = "SELECT r FROM Rozliczus r WHERE r.rzuDod1 = :rzuDod1"),
    @NamedQuery(name = "Rozliczus.findByRzuDod2", query = "SELECT r FROM Rozliczus r WHERE r.rzuDod2 = :rzuDod2"),
    @NamedQuery(name = "Rozliczus.findByRzuDod3", query = "SELECT r FROM Rozliczus r WHERE r.rzuDod3 = :rzuDod3"),
    @NamedQuery(name = "Rozliczus.findByRzuDod4", query = "SELECT r FROM Rozliczus r WHERE r.rzuDod4 = :rzuDod4"),
    @NamedQuery(name = "Rozliczus.findByRzuNum1", query = "SELECT r FROM Rozliczus r WHERE r.rzuNum1 = :rzuNum1"),
    @NamedQuery(name = "Rozliczus.findByRzuNum2", query = "SELECT r FROM Rozliczus r WHERE r.rzuNum2 = :rzuNum2"),
    @NamedQuery(name = "Rozliczus.findByRzuVchar1", query = "SELECT r FROM Rozliczus r WHERE r.rzuVchar1 = :rzuVchar1"),
    @NamedQuery(name = "Rozliczus.findByRzuData1", query = "SELECT r FROM Rozliczus r WHERE r.rzuData1 = :rzuData1")})
public class Rozliczus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzu_serial", nullable = false)
    private Integer rzuSerial;
    @Column(name = "rzu_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzuData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "rzu_opis", nullable = false, length = 64)
    private String rzuOpis;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzu_kwota", nullable = false, precision = 13, scale = 2)
    private BigDecimal rzuKwota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rzu_oso_typ", nullable = false)
    private Character rzuOsoTyp;
    @Column(name = "rzu_mie_numer")
    private Short rzuMieNumer;
    @Column(name = "rzu_rodzaj")
    private Character rzuRodzaj;
    @Column(name = "rzu_tytul")
    private Character rzuTytul;
    @Column(name = "rzu_dod_1")
    private Character rzuDod1;
    @Column(name = "rzu_dod_2")
    private Character rzuDod2;
    @Column(name = "rzu_dod_3")
    private Character rzuDod3;
    @Column(name = "rzu_dod_4")
    private Character rzuDod4;
    @Column(name = "rzu_num_1", precision = 17, scale = 6)
    private BigDecimal rzuNum1;
    @Column(name = "rzu_num_2", precision = 17, scale = 6)
    private BigDecimal rzuNum2;
    @Size(max = 128)
    @Column(name = "rzu_vchar_1", length = 128)
    private String rzuVchar1;
    @Column(name = "rzu_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rzuData1;
    @JoinColumn(name = "rzu_okr_serial", referencedColumnName = "okr_serial")
    @ManyToOne
    private Okres rzuOkrSerial;
    @JoinColumn(name = "rzu_oso_serial", referencedColumnName = "oso_serial")
    @ManyToOne
    private Osoba rzuOsoSerial;
    @JoinColumn(name = "rzu_rok_serial", referencedColumnName = "rok_serial", nullable = false)
    @ManyToOne(optional = false)
    private Rok rzuRokSerial;
    @JoinColumn(name = "rzu_urz_serial", referencedColumnName = "urz_serial", nullable = false)
    @ManyToOne(optional = false)
    private Urzad rzuUrzSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rzzRzuSerial")
    private List<Rozliczzap> rozliczzapList;

    public Rozliczus() {
    }

    public Rozliczus(Integer rzuSerial) {
        this.rzuSerial = rzuSerial;
    }

    public Rozliczus(Integer rzuSerial, String rzuOpis, BigDecimal rzuKwota, Character rzuOsoTyp) {
        this.rzuSerial = rzuSerial;
        this.rzuOpis = rzuOpis;
        this.rzuKwota = rzuKwota;
        this.rzuOsoTyp = rzuOsoTyp;
    }

    public Integer getRzuSerial() {
        return rzuSerial;
    }

    public void setRzuSerial(Integer rzuSerial) {
        this.rzuSerial = rzuSerial;
    }

    public Date getRzuData() {
        return rzuData;
    }

    public void setRzuData(Date rzuData) {
        this.rzuData = rzuData;
    }

    public String getRzuOpis() {
        return rzuOpis;
    }

    public void setRzuOpis(String rzuOpis) {
        this.rzuOpis = rzuOpis;
    }

    public BigDecimal getRzuKwota() {
        return rzuKwota;
    }

    public void setRzuKwota(BigDecimal rzuKwota) {
        this.rzuKwota = rzuKwota;
    }

    public Character getRzuOsoTyp() {
        return rzuOsoTyp;
    }

    public void setRzuOsoTyp(Character rzuOsoTyp) {
        this.rzuOsoTyp = rzuOsoTyp;
    }

    public Short getRzuMieNumer() {
        return rzuMieNumer;
    }

    public void setRzuMieNumer(Short rzuMieNumer) {
        this.rzuMieNumer = rzuMieNumer;
    }

    public Character getRzuRodzaj() {
        return rzuRodzaj;
    }

    public void setRzuRodzaj(Character rzuRodzaj) {
        this.rzuRodzaj = rzuRodzaj;
    }

    public Character getRzuTytul() {
        return rzuTytul;
    }

    public void setRzuTytul(Character rzuTytul) {
        this.rzuTytul = rzuTytul;
    }

    public Character getRzuDod1() {
        return rzuDod1;
    }

    public void setRzuDod1(Character rzuDod1) {
        this.rzuDod1 = rzuDod1;
    }

    public Character getRzuDod2() {
        return rzuDod2;
    }

    public void setRzuDod2(Character rzuDod2) {
        this.rzuDod2 = rzuDod2;
    }

    public Character getRzuDod3() {
        return rzuDod3;
    }

    public void setRzuDod3(Character rzuDod3) {
        this.rzuDod3 = rzuDod3;
    }

    public Character getRzuDod4() {
        return rzuDod4;
    }

    public void setRzuDod4(Character rzuDod4) {
        this.rzuDod4 = rzuDod4;
    }

    public BigDecimal getRzuNum1() {
        return rzuNum1;
    }

    public void setRzuNum1(BigDecimal rzuNum1) {
        this.rzuNum1 = rzuNum1;
    }

    public BigDecimal getRzuNum2() {
        return rzuNum2;
    }

    public void setRzuNum2(BigDecimal rzuNum2) {
        this.rzuNum2 = rzuNum2;
    }

    public String getRzuVchar1() {
        return rzuVchar1;
    }

    public void setRzuVchar1(String rzuVchar1) {
        this.rzuVchar1 = rzuVchar1;
    }

    public Date getRzuData1() {
        return rzuData1;
    }

    public void setRzuData1(Date rzuData1) {
        this.rzuData1 = rzuData1;
    }

    public Okres getRzuOkrSerial() {
        return rzuOkrSerial;
    }

    public void setRzuOkrSerial(Okres rzuOkrSerial) {
        this.rzuOkrSerial = rzuOkrSerial;
    }

    public Osoba getRzuOsoSerial() {
        return rzuOsoSerial;
    }

    public void setRzuOsoSerial(Osoba rzuOsoSerial) {
        this.rzuOsoSerial = rzuOsoSerial;
    }

    public Rok getRzuRokSerial() {
        return rzuRokSerial;
    }

    public void setRzuRokSerial(Rok rzuRokSerial) {
        this.rzuRokSerial = rzuRokSerial;
    }

    public Urzad getRzuUrzSerial() {
        return rzuUrzSerial;
    }

    public void setRzuUrzSerial(Urzad rzuUrzSerial) {
        this.rzuUrzSerial = rzuUrzSerial;
    }

    @XmlTransient
    public List<Rozliczzap> getRozliczzapList() {
        return rozliczzapList;
    }

    public void setRozliczzapList(List<Rozliczzap> rozliczzapList) {
        this.rozliczzapList = rozliczzapList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rzuSerial != null ? rzuSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rozliczus)) {
            return false;
        }
        Rozliczus other = (Rozliczus) object;
        if ((this.rzuSerial == null && other.rzuSerial != null) || (this.rzuSerial != null && !this.rzuSerial.equals(other.rzuSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Rozliczus[ rzuSerial=" + rzuSerial + " ]";
    }
    
}
