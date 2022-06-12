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
@Table(name = "rok", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rok.findAll", query = "SELECT r FROM Rok r"),
    @NamedQuery(name = "Rok.findByRokSerial", query = "SELECT r FROM Rok r WHERE r.rokSerial = :rokSerial"),
    @NamedQuery(name = "Rok.findByRokFirma", query = "SELECT r FROM Rok r WHERE r.rokFirSerial = :firma AND r.rokNumer=:rok"),
    @NamedQuery(name = "Rok.findByRokDataOd", query = "SELECT r FROM Rok r WHERE r.rokDataOd = :rokDataOd"),
    @NamedQuery(name = "Rok.findByRokDataDo", query = "SELECT r FROM Rok r WHERE r.rokDataDo = :rokDataDo"),
    @NamedQuery(name = "Rok.findByRokOpis", query = "SELECT r FROM Rok r WHERE r.rokOpis = :rokOpis"),
    @NamedQuery(name = "Rok.findByRokNumer", query = "SELECT r FROM Rok r WHERE r.rokNumer = :rokNumer"),
    @NamedQuery(name = "Rok.findByRokKryteria", query = "SELECT r FROM Rok r WHERE r.rokKryteria = :rokKryteria"),
    @NamedQuery(name = "Rok.findByRokDodChar1", query = "SELECT r FROM Rok r WHERE r.rokDodChar1 = :rokDodChar1"),
    @NamedQuery(name = "Rok.findByRokDodChar2", query = "SELECT r FROM Rok r WHERE r.rokDodChar2 = :rokDodChar2"),
    @NamedQuery(name = "Rok.findByRokDodChar3", query = "SELECT r FROM Rok r WHERE r.rokDodChar3 = :rokDodChar3"),
    @NamedQuery(name = "Rok.findByRokDodChar4", query = "SELECT r FROM Rok r WHERE r.rokDodChar4 = :rokDodChar4"),
    @NamedQuery(name = "Rok.findByRokDodVchar1", query = "SELECT r FROM Rok r WHERE r.rokDodVchar1 = :rokDodVchar1"),
    @NamedQuery(name = "Rok.findByRokDodVchar2", query = "SELECT r FROM Rok r WHERE r.rokDodVchar2 = :rokDodVchar2"),
    @NamedQuery(name = "Rok.findByRokDodInt1", query = "SELECT r FROM Rok r WHERE r.rokDodInt1 = :rokDodInt1"),
    @NamedQuery(name = "Rok.findByRokDodInt2", query = "SELECT r FROM Rok r WHERE r.rokDodInt2 = :rokDodInt2"),
    @NamedQuery(name = "Rok.findByRokDodNum1", query = "SELECT r FROM Rok r WHERE r.rokDodNum1 = :rokDodNum1"),
    @NamedQuery(name = "Rok.findByRokDodNum2", query = "SELECT r FROM Rok r WHERE r.rokDodNum2 = :rokDodNum2")})
public class Rok implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "rok_serial", nullable = false)
    private Integer rokSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rok_data_od", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rokDataOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rok_data_do", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rokDataDo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "rok_opis", nullable = false, length = 64)
    private String rokOpis;
    @Column(name = "rok_numer")
    private Short rokNumer;
    @Column(name = "rok_kryteria")
    private Character rokKryteria;
    @Column(name = "rok_dod_char_1")
    private Character rokDodChar1;
    @Column(name = "rok_dod_char_2")
    private Character rokDodChar2;
    @Column(name = "rok_dod_char_3")
    private Character rokDodChar3;
    @Column(name = "rok_dod_char_4")
    private Character rokDodChar4;
    @Size(max = 64)
    @Column(name = "rok_dod_vchar_1", length = 64)
    private String rokDodVchar1;
    @Size(max = 64)
    @Column(name = "rok_dod_vchar_2", length = 64)
    private String rokDodVchar2;
    @Column(name = "rok_dod_int_1")
    private Integer rokDodInt1;
    @Column(name = "rok_dod_int_2")
    private Integer rokDodInt2;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rok_dod_num_1", precision = 17, scale = 6)
    private BigDecimal rokDodNum1;
    @Column(name = "rok_dod_num_2", precision = 17, scale = 6)
    private BigDecimal rokDodNum2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "okrRokSerial")
    private List<Okres> okresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parRokSerial")
    private List<Paragon> paragonList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ksiRokSerial")
    private List<Ksiegapir> ksiegapirList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zakRokSerial")
    private List<Zakupy> zakupyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zdoRokSerial")
    private List<Zakdok> zakdokList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plgRokSerial")
    private List<PlanGrup> planGrupList;
    @OneToMany(mappedBy = "amoRokSerial")
    private List<Amorty> amortyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rzuRokSerial")
    private List<Rozliczus> rozliczusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "epzRokSerial")
    private List<EppZest> eppZestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdoRokSerial")
    private List<Magdok> magdokList;
    @OneToMany(mappedBy = "spiRokSerial")
    private List<Spisy> spisyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eppRokSerial")
    private List<Przebieg> przebiegList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sprRokSerial")
    private List<Sprzedaz> sprzedazList;
    @JoinColumn(name = "rok_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma rokFirSerial;

    public Rok() {
    }

    public Rok(Integer rokSerial) {
        this.rokSerial = rokSerial;
    }

    public Rok(Integer rokSerial, Date rokDataOd, Date rokDataDo, String rokOpis) {
        this.rokSerial = rokSerial;
        this.rokDataOd = rokDataOd;
        this.rokDataDo = rokDataDo;
        this.rokOpis = rokOpis;
    }

    public Integer getRokSerial() {
        return rokSerial;
    }

    public void setRokSerial(Integer rokSerial) {
        this.rokSerial = rokSerial;
    }

    public Date getRokDataOd() {
        return rokDataOd;
    }

    public void setRokDataOd(Date rokDataOd) {
        this.rokDataOd = rokDataOd;
    }

    public Date getRokDataDo() {
        return rokDataDo;
    }

    public void setRokDataDo(Date rokDataDo) {
        this.rokDataDo = rokDataDo;
    }

    public String getRokOpis() {
        return rokOpis;
    }

    public void setRokOpis(String rokOpis) {
        this.rokOpis = rokOpis;
    }

    public Short getRokNumer() {
        return rokNumer;
    }

    public void setRokNumer(Short rokNumer) {
        this.rokNumer = rokNumer;
    }

    public Character getRokKryteria() {
        return rokKryteria;
    }

    public void setRokKryteria(Character rokKryteria) {
        this.rokKryteria = rokKryteria;
    }

    public Character getRokDodChar1() {
        return rokDodChar1;
    }

    public void setRokDodChar1(Character rokDodChar1) {
        this.rokDodChar1 = rokDodChar1;
    }

    public Character getRokDodChar2() {
        return rokDodChar2;
    }

    public void setRokDodChar2(Character rokDodChar2) {
        this.rokDodChar2 = rokDodChar2;
    }

    public Character getRokDodChar3() {
        return rokDodChar3;
    }

    public void setRokDodChar3(Character rokDodChar3) {
        this.rokDodChar3 = rokDodChar3;
    }

    public Character getRokDodChar4() {
        return rokDodChar4;
    }

    public void setRokDodChar4(Character rokDodChar4) {
        this.rokDodChar4 = rokDodChar4;
    }

    public String getRokDodVchar1() {
        return rokDodVchar1;
    }

    public void setRokDodVchar1(String rokDodVchar1) {
        this.rokDodVchar1 = rokDodVchar1;
    }

    public String getRokDodVchar2() {
        return rokDodVchar2;
    }

    public void setRokDodVchar2(String rokDodVchar2) {
        this.rokDodVchar2 = rokDodVchar2;
    }

    public Integer getRokDodInt1() {
        return rokDodInt1;
    }

    public void setRokDodInt1(Integer rokDodInt1) {
        this.rokDodInt1 = rokDodInt1;
    }

    public Integer getRokDodInt2() {
        return rokDodInt2;
    }

    public void setRokDodInt2(Integer rokDodInt2) {
        this.rokDodInt2 = rokDodInt2;
    }

    public BigDecimal getRokDodNum1() {
        return rokDodNum1;
    }

    public void setRokDodNum1(BigDecimal rokDodNum1) {
        this.rokDodNum1 = rokDodNum1;
    }

    public BigDecimal getRokDodNum2() {
        return rokDodNum2;
    }

    public void setRokDodNum2(BigDecimal rokDodNum2) {
        this.rokDodNum2 = rokDodNum2;
    }

    @XmlTransient
    public List<Okres> getOkresList() {
        return okresList;
    }

    public void setOkresList(List<Okres> okresList) {
        this.okresList = okresList;
    }

    @XmlTransient
    public List<Paragon> getParagonList() {
        return paragonList;
    }

    public void setParagonList(List<Paragon> paragonList) {
        this.paragonList = paragonList;
    }

    @XmlTransient
    public List<Ksiegapir> getKsiegapirList() {
        return ksiegapirList;
    }

    public void setKsiegapirList(List<Ksiegapir> ksiegapirList) {
        this.ksiegapirList = ksiegapirList;
    }

    @XmlTransient
    public List<Zakupy> getZakupyList() {
        return zakupyList;
    }

    public void setZakupyList(List<Zakupy> zakupyList) {
        this.zakupyList = zakupyList;
    }

    @XmlTransient
    public List<Zakdok> getZakdokList() {
        return zakdokList;
    }

    public void setZakdokList(List<Zakdok> zakdokList) {
        this.zakdokList = zakdokList;
    }

    @XmlTransient
    public List<PlanGrup> getPlanGrupList() {
        return planGrupList;
    }

    public void setPlanGrupList(List<PlanGrup> planGrupList) {
        this.planGrupList = planGrupList;
    }

    @XmlTransient
    public List<Amorty> getAmortyList() {
        return amortyList;
    }

    public void setAmortyList(List<Amorty> amortyList) {
        this.amortyList = amortyList;
    }

    @XmlTransient
    public List<Rozliczus> getRozliczusList() {
        return rozliczusList;
    }

    public void setRozliczusList(List<Rozliczus> rozliczusList) {
        this.rozliczusList = rozliczusList;
    }

    @XmlTransient
    public List<EppZest> getEppZestList() {
        return eppZestList;
    }

    public void setEppZestList(List<EppZest> eppZestList) {
        this.eppZestList = eppZestList;
    }

    @XmlTransient
    public List<Magdok> getMagdokList() {
        return magdokList;
    }

    public void setMagdokList(List<Magdok> magdokList) {
        this.magdokList = magdokList;
    }

    @XmlTransient
    public List<Spisy> getSpisyList() {
        return spisyList;
    }

    public void setSpisyList(List<Spisy> spisyList) {
        this.spisyList = spisyList;
    }

    @XmlTransient
    public List<Przebieg> getPrzebiegList() {
        return przebiegList;
    }

    public void setPrzebiegList(List<Przebieg> przebiegList) {
        this.przebiegList = przebiegList;
    }

    @XmlTransient
    public List<Sprzedaz> getSprzedazList() {
        return sprzedazList;
    }

    public void setSprzedazList(List<Sprzedaz> sprzedazList) {
        this.sprzedazList = sprzedazList;
    }

    public Firma getRokFirSerial() {
        return rokFirSerial;
    }

    public void setRokFirSerial(Firma rokFirSerial) {
        this.rokFirSerial = rokFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rokSerial != null ? rokSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rok)) {
            return false;
        }
        Rok other = (Rok) object;
        if ((this.rokSerial == null && other.rokSerial != null) || (this.rokSerial != null && !this.rokSerial.equals(other.rokSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Rok[ rokSerial=" + rokSerial + " ]";
    }
    
}
