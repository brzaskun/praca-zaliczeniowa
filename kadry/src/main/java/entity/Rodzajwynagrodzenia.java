/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rodzajwynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajwynagrodzenia.findAll", query = "SELECT r FROM Rodzajwynagrodzenia r"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findById", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findAktywne", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.aktywne = TRUE"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByKod", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.kod = :kod"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpispelny", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opispelny = :opispelny"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findGodzinowe", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opispelny = :opispelny AND r.kod = :kod"),
    @NamedQuery(name = "Rodzajwynagrodzenia.findByOpisskrocony", query = "SELECT r FROM Rodzajwynagrodzenia r WHERE r.opisskrocony = :opisskrocony")})
public class Rodzajwynagrodzenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
       //skladnik wybagrodzenia
    @Column(name = "wks_serial")
    private Integer wks_serial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "opispelny")
    private String opispelny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "opisskrocony")
    private String opisskrocony;
    @Column(name = "stale0zmienne1")
    private  boolean stale0zmienne1;
    @Column(name = "godzinowe0miesieczne1")
    private  boolean godzinowe0miesieczne1;
    @Column(name = "redukowany")
    private  boolean redukowany;
    //jest zus 0 bo to reguła, a z reguły boolean jest false
    @Column(name = "zus0bezzus1")
    private  boolean zus0bezzus1;
    @Column(name = "podatek0bezpodatek1")
    private  boolean podatek0bezpodatek1;
    @Column(name = "aktywne")
    private  boolean aktywne;
    @Column(name = "tylkosuperplace")
    private  boolean tylkosuperplace;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajwynagrodzenia")
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;
    @Column(name = "sredniaurlopowakraj")
    private  boolean sredniaurlopowakraj;
    @Column(name = "sredniaurlopowaoddelegowanie")
    private  boolean sredniaurlopowaoddelegowanie;
    @Column(name = "podstzasilekchorobowy")
    private  boolean podstzasilekchorobowy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajwynagrodzenia")
    private List<RodzajlistyplacRodzajwynagrodzenia> rodzajllistyplacRodzajwynagrodzeniaList;

;     

    public Rodzajwynagrodzenia() {
    }

    public Rodzajwynagrodzenia(Integer id) {
        this.id = id;
    }

    public Rodzajwynagrodzenia(Integer id, String kod, String opispelny, String opisskrocony) {
        this.id = id;
        this.kod = kod;
        this.opispelny = opispelny;
        this.opisskrocony = opisskrocony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rodzajwynagrodzenia)) {
            return false;
        }
        Rodzajwynagrodzenia other = (Rodzajwynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rodzajwynagrodzenia{" + "kod=" + kod + ", opispelny=" + opispelny + ", opisskrocony=" + opisskrocony + ", stale0zmienne1=" + stale0zmienne1 + ", godzinowe0miesieczne1=" + godzinowe0miesieczne1 + ", redukowany=" + redukowany + ", zus0bezzus1=" + zus0bezzus1 + ", podatek0bezpodatek1=" + podatek0bezpodatek1 + '}';
    }

    public Integer getWks_serial() {
        return wks_serial;
    }

    public void setWks_serial(Integer wks_serial) {
        this.wks_serial = wks_serial;
    }


    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getOpispelny() {
        return opispelny;
    }

    public void setOpispelny(String opispelny) {
        this.opispelny = opispelny;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public  boolean getStale0zmienne1() {
        return stale0zmienne1;
    }

    public void setStale0zmienne1( boolean stale0zmienne1) {
        this.stale0zmienne1 = stale0zmienne1;
    }

    public  boolean getGodzinowe0miesieczne1() {
        return godzinowe0miesieczne1;
    }

    public void setGodzinowe0miesieczne1( boolean godzinowe0miesieczne1) {
        this.godzinowe0miesieczne1 = godzinowe0miesieczne1;
    }

    public  boolean isRedukowany() {
        return redukowany;
    }

    public void setRedukowany( boolean redukowany) {
        this.redukowany = redukowany;
    }

    public  boolean isZus0bezzus1() {
        return zus0bezzus1;
    }

    public void setZus0bezzus1( boolean zus0bezzus1) {
        this.zus0bezzus1 = zus0bezzus1;
    }

    public boolean isPodatek0bezpodatek1() {
        return podatek0bezpodatek1;
    }

    public void setPodatek0bezpodatek1(boolean podatek0bezpodatek1) {
        this.podatek0bezpodatek1 = podatek0bezpodatek1;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public boolean isTylkosuperplace() {
        return tylkosuperplace;
    }

    public void setTylkosuperplace(boolean tylkosuperplace) {
        this.tylkosuperplace = tylkosuperplace;
    }

    public boolean isSredniaurlopowakraj() {
        return sredniaurlopowakraj;
    }

    public void setSredniaurlopowakraj(boolean sredniaurlopowakraj) {
        this.sredniaurlopowakraj = sredniaurlopowakraj;
    }

    public boolean isSredniaurlopowaoddelegowanie() {
        return sredniaurlopowaoddelegowanie;
    }

    public void setSredniaurlopowaoddelegowanie(boolean sredniaurlopowaoddelegowanie) {
        this.sredniaurlopowaoddelegowanie = sredniaurlopowaoddelegowanie;
    }

    public boolean isPodstzasilekchorobowy() {
        return podstzasilekchorobowy;
    }

    public void setPodstzasilekchorobowy(boolean podstzasilekchorobowy) {
        this.podstzasilekchorobowy = podstzasilekchorobowy;
    }

    @XmlTransient
    public List<RodzajlistyplacRodzajwynagrodzenia> getDefinicjalistaplacRodzajwynagrodzeniaList() {
        return rodzajllistyplacRodzajwynagrodzeniaList;
    }

    public void setDefinicjalistaplacRodzajwynagrodzeniaList(List<RodzajlistyplacRodzajwynagrodzenia> definicjalistaplacRodzajwynagrodzeniaList) {
        this.rodzajllistyplacRodzajwynagrodzeniaList = definicjalistaplacRodzajwynagrodzeniaList;
    }  
}
