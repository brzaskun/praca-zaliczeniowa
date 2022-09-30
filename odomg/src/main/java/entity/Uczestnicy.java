/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "uczestnicy", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "nazwaszkolenia", "dataustania"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uczestnicy.findAll", query = "SELECT u FROM Uczestnicy u"),
    @NamedQuery(name = "Uczestnicy.findById", query = "SELECT u FROM Uczestnicy u WHERE u.id = :id"),
    @NamedQuery(name = "Uczestnicy.findByEmail", query = "SELECT u FROM Uczestnicy u WHERE u.email = :email"),
    @NamedQuery(name = "Uczestnicy.findByHaslo", query = "SELECT u FROM Uczestnicy u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uczestnicy.findByImienazwisko", query = "SELECT u FROM Uczestnicy u WHERE u.imienazwisko = :imienazwisko"),
    @NamedQuery(name = "Uczestnicy.findByPlec", query = "SELECT u FROM Uczestnicy u WHERE u.plec = :plec"),
    @NamedQuery(name = "Uczestnicy.findByFirma", query = "SELECT u FROM Uczestnicy u WHERE u.firma = :firma"),
    @NamedQuery(name = "Uczestnicy.findByNazwaszkolenia", query = "SELECT u FROM Uczestnicy u WHERE u.nazwaszkolenia = :nazwaszkolenia"),
    @NamedQuery(name = "Uczestnicy.findByUprawnienia", query = "SELECT u FROM Uczestnicy u WHERE u.uprawnienia = :uprawnienia"),
    @NamedQuery(name = "Uczestnicy.findByWyslanymailupr", query = "SELECT u FROM Uczestnicy u WHERE u.wyslanymailupr = :wyslanymailupr"),
    @NamedQuery(name = "Uczestnicy.findBySessionstart", query = "SELECT u FROM Uczestnicy u WHERE u.sessionstart = :sessionstart"),
    @NamedQuery(name = "Uczestnicy.findBySessionend", query = "SELECT u FROM Uczestnicy u WHERE u.sessionend = :sessionend"),
    @NamedQuery(name = "Uczestnicy.findByWyniktestu", query = "SELECT u FROM Uczestnicy u WHERE u.wyniktestu = :wyniktestu"),
    @NamedQuery(name = "Uczestnicy.findByWyslanycert", query = "SELECT u FROM Uczestnicy u WHERE u.wyslanycert = :wyslanycert"),
    @NamedQuery(name = "Uczestnicy.findByIlosclogowan", query = "SELECT u FROM Uczestnicy u WHERE u.ilosclogowan = :ilosclogowan"),
    @NamedQuery(name = "Uczestnicy.findByIloscpoprawnych", query = "SELECT u FROM Uczestnicy u WHERE u.iloscpoprawnych = :iloscpoprawnych"),
    @NamedQuery(name = "Uczestnicy.findByIloscblednych", query = "SELECT u FROM Uczestnicy u WHERE u.iloscblednych = :iloscblednych"),
    @NamedQuery(name = "Uczestnicy.findByIloscodpowiedzi", query = "SELECT u FROM Uczestnicy u WHERE u.iloscodpowiedzi = :iloscodpowiedzi"),
    @NamedQuery(name = "Uczestnicy.findByNrupowaznienia", query = "SELECT u FROM Uczestnicy u WHERE u.nrupowaznienia = :nrupowaznienia"),
    @NamedQuery(name = "Uczestnicy.findByIndetyfikator", query = "SELECT u FROM Uczestnicy u WHERE u.indetyfikator = :indetyfikator"),
    @NamedQuery(name = "Uczestnicy.findByDatanadania", query = "SELECT u FROM Uczestnicy u WHERE u.datanadania = :datanadania"),
    @NamedQuery(name = "Uczestnicy.findByDataustania", query = "SELECT u FROM Uczestnicy u WHERE u.dataustania = :dataustania"),
    @NamedQuery(name = "Uczestnicy.findByWyslaneup", query = "SELECT u FROM Uczestnicy u WHERE u.wyslaneup = :wyslaneup"),
    @NamedQuery(name = "Uczestnicy.findByUtworzony", query = "SELECT u FROM Uczestnicy u WHERE u.utworzony = :utworzony"),
    @NamedQuery(name = "Uczestnicy.findByZmodyfikowany", query = "SELECT u FROM Uczestnicy u WHERE u.zmodyfikowany = :zmodyfikowany"),
    @NamedQuery(name = "Uczestnicy.findByDataostatniegologowania", query = "SELECT u FROM Uczestnicy u WHERE u.dataostatniegologowania = :dataostatniegologowania"),
    @NamedQuery(name = "Uczestnicy.findByIplogowania", query = "SELECT u FROM Uczestnicy u WHERE u.iplogowania = :iplogowania"),
    @NamedQuery(name = "Uczestnicy.findByZaswiadczeniedata", query = "SELECT u FROM Uczestnicy u WHERE u.zaswiadczeniedata = :zaswiadczeniedata"),
    @NamedQuery(name = "Uczestnicy.findByUpowaznieniedata", query = "SELECT u FROM Uczestnicy u WHERE u.upowaznieniedata = :upowaznieniedata"),
    @NamedQuery(name = "Uczestnicy.findByOstatnireset", query = "SELECT u FROM Uczestnicy u WHERE u.ostatnireset = :ostatnireset"),
    @NamedQuery(name = "Uczestnicy.findByWcisnietyklawisz", query = "SELECT u FROM Uczestnicy u WHERE u.wcisnietyklawisz = :wcisnietyklawisz"),
    @NamedQuery(name = "Uczestnicy.findByStacjonarny", query = "SELECT u FROM Uczestnicy u WHERE u.stacjonarny = :stacjonarny"),
    @NamedQuery(name = "Uczestnicy.findByWyslaneupdanewrazliwe", query = "SELECT u FROM Uczestnicy u WHERE u.wyslaneupdanewrazliwe = :wyslaneupdanewrazliwe"),
    @NamedQuery(name = "Uczestnicy.findByUpowaznieniedwdata", query = "SELECT u FROM Uczestnicy u WHERE u.upowaznieniedwdata = :upowaznieniedwdata"),
    @NamedQuery(name = "Uczestnicy.findByDatanadaniadsk", query = "SELECT u FROM Uczestnicy u WHERE u.datanadaniadsk = :datanadaniadsk"),
    @NamedQuery(name = "Uczestnicy.findByFirmaId", query = "SELECT u FROM Uczestnicy u WHERE u.firmaId = :firmaId"),
    @NamedQuery(name = "Uczestnicy.findByWyslanymailuprdata", query = "SELECT u FROM Uczestnicy u WHERE u.wyslanymailuprdata = :wyslanymailuprdata"),
    @NamedQuery(name = "Uczestnicy.findByWyslanymailuprdataprzyp", query = "SELECT u FROM Uczestnicy u WHERE u.wyslanymailuprdataprzyp = :wyslanymailuprdataprzyp")})
public class Uczestnicy implements Serializable {

    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email", length = 255)
private String email;
    @Size(max = 128)
    @Column(name = "haslo", length = 128)
    private String haslo;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "imienazwisko", length = 255)
    private String imienazwisko;
    @Size(max = 1)
    @Column(name = "plec", length = 1)
    private String plec;
    @Size(max = 255)
    @Column(name = "firma", length = 255)
    private String firma;
    @Size(max = 255)
    @Column(name = "nazwaszkolenia", length = 255)
    private String nazwaszkolenia;
    @Size(max = 25)
    @Column(name = "uprawnienia", length = 25)
    private String uprawnienia;
    @Size(max = 255)
    @Column(name = "nrupowaznienia", length = 255)
    private String nrupowaznienia;
    @Size(max = 255)
    @Column(name = "indetyfikator", length = 255)
    private String indetyfikator;
    @Size(max = 10)
    @Column(name = "datanadania", length = 10)
    private String datanadania;
    @Size(max = 10)
    @Column(name = "dataustania", length = 10)
    private String dataustania;
    @Size(max = 45)
    @Column(name = "iplogowania", length = 45)
    private String iplogowania;
    @Size(max = 10)
    @Column(name = "datanadaniadsk", length = 10)
    private String datanadaniadsk;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUzytkownik")
    private List<Uczestnicyarchiwum> uczestnicyarchiwumList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "wyslanymailupr")
    private Integer wyslanymailupr;
    @Column(name = "sessionstart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionstart;
    @Column(name = "sessionend")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionend;
    @Column(name = "wyniktestu")
    private Integer wyniktestu;
    @Column(name = "wyslanycert")
    private Integer wyslanycert;
    @Column(name = "ilosclogowan")
    private Integer ilosclogowan;
    @Column(name = "iloscpoprawnych")
    private Integer iloscpoprawnych;
    @Column(name = "iloscblednych")
    private Integer iloscblednych;
    @Column(name = "iloscodpowiedzi")
    private Integer iloscodpowiedzi;
    @Column(name = "wyslaneup")
    private boolean wyslaneup;
    @Column(name = "utworzony")
    @Temporal(TemporalType.TIMESTAMP)
    private Date utworzony;
    @Column(name = "zmodyfikowany")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zmodyfikowany;
    @Column(name = "dataostatniegologowania")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataostatniegologowania;
    @Column(name = "zaswiadczeniedata")
    @Temporal(TemporalType.TIMESTAMP)
    private Date zaswiadczeniedata;
    @Column(name = "upowaznieniedata")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upowaznieniedata;
    @Column(name = "ostatnireset")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ostatnireset;
    @Column(name = "wcisnietyklawisz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wcisnietyklawisz;
    @Column(name = "stacjonarny")
    private boolean stacjonarny;
    @Column(name = "wyslaneupdanewrazliwe")
    private boolean wyslaneupdanewrazliwe;
    @Column(name = "upowaznieniedwdata")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upowaznieniedwdata;
    @JoinColumn(name = "firma_id", referencedColumnName = "id")
    private Zakladpracy firmaId;
    @Column(name = "wyslanymailuprdata")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wyslanymailuprdata;
    @Column(name = "wyslanymailuprdataprzyp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date wyslanymailuprdataprzyp;
    @Transient
    private List<UczestnikGrupa> uczestnikgrupy;
    @Transient
    private boolean duplikat;
    @Transient
    private boolean braki;
    @Transient
    private int nrwierszaimport;

    public Uczestnicy() {
    }

    public Uczestnicy(Integer id) {
        this.id = id;
    }

    public Uczestnicy(Uczestnicy nowy) {
        this.email = nowy.email;
        this.imienazwisko = nowy.imienazwisko;
        this.plec = nowy.plec;
        this.nazwaszkolenia = nowy.nazwaszkolenia;
        this.uprawnienia = nowy.uprawnienia;
        this.firmaId = nowy.firmaId;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getWyslanymailupr() {
        return wyslanymailupr;
    }

    public void setWyslanymailupr(Integer wyslanymailupr) {
        this.wyslanymailupr = wyslanymailupr;
    }

    public Date getSessionstart() {
        return sessionstart;
    }

    public void setSessionstart(Date sessionstart) {
        this.sessionstart = sessionstart;
    }

    public Date getSessionend() {
        return sessionend;
    }
    
    public Instant getSessionend2() {
        return dateToInsttant(sessionend);
    }

    public Instant dateToInsttant(Date date) {
        Instant instant2 = date.toInstant();
        return instant2.truncatedTo(ChronoUnit.DAYS);
    }
    public void setSessionend(Date sessionend) {
        this.sessionend = sessionend;
    }

    public Integer getWyniktestu() {
        return wyniktestu;
    }

    public void setWyniktestu(Integer wyniktestu) {
        this.wyniktestu = wyniktestu;
    }

    public Integer getWyslanycert() {
        return wyslanycert;
    }

    public void setWyslanycert(Integer wyslanycert) {
        this.wyslanycert = wyslanycert;
    }

    public Integer getIlosclogowan() {
        return ilosclogowan;
    }

    public void setIlosclogowan(Integer ilosclogowan) {
        this.ilosclogowan = ilosclogowan;
    }

    public Integer getIloscpoprawnych() {
        return iloscpoprawnych;
    }

    public void setIloscpoprawnych(Integer iloscpoprawnych) {
        this.iloscpoprawnych = iloscpoprawnych;
    }

    public Integer getIloscblednych() {
        return iloscblednych;
    }

    public void setIloscblednych(Integer iloscblednych) {
        this.iloscblednych = iloscblednych;
    }

    public Integer getIloscodpowiedzi() {
        return iloscodpowiedzi;
    }

    public void setIloscodpowiedzi(Integer iloscodpowiedzi) {
        this.iloscodpowiedzi = iloscodpowiedzi;
    }


    public boolean getWyslaneup() {
        return wyslaneup;
    }

    public void setWyslaneup(boolean wyslaneup) {
        this.wyslaneup = wyslaneup;
    }

    public Date getUtworzony() {
        return utworzony;
    }

    public void setUtworzony(Date utworzony) {
        this.utworzony = utworzony;
    }

    public Date getZmodyfikowany() {
        return zmodyfikowany;
    }

    public void setZmodyfikowany(Date zmodyfikowany) {
        this.zmodyfikowany = zmodyfikowany;
    }

    public Date getDataostatniegologowania() {
        return dataostatniegologowania;
    }

    public void setDataostatniegologowania(Date dataostatniegologowania) {
        this.dataostatniegologowania = dataostatniegologowania;
    }


    public Date getZaswiadczeniedata() {
        return zaswiadczeniedata;
    }

    public void setZaswiadczeniedata(Date zaswiadczeniedata) {
        this.zaswiadczeniedata = zaswiadczeniedata;
    }

    public Date getUpowaznieniedata() {
        return upowaznieniedata;
    }

    public void setUpowaznieniedata(Date upowaznieniedata) {
        this.upowaznieniedata = upowaznieniedata;
    }

    public Date getOstatnireset() {
        return ostatnireset;
    }

    public void setOstatnireset(Date ostatnireset) {
        this.ostatnireset = ostatnireset;
    }

    public Date getWcisnietyklawisz() {
        return wcisnietyklawisz;
    }

    public void setWcisnietyklawisz(Date wcisnietyklawisz) {
        this.wcisnietyklawisz = wcisnietyklawisz;
    }

    public boolean getStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public boolean getWyslaneupdanewrazliwe() {
        return wyslaneupdanewrazliwe;
    }

    public void setWyslaneupdanewrazliwe(boolean wyslaneupdanewrazliwe) {
        this.wyslaneupdanewrazliwe = wyslaneupdanewrazliwe;
    }

    public Date getUpowaznieniedwdata() {
        return upowaznieniedwdata;
    }

    public void setUpowaznieniedwdata(Date upowaznieniedwdata) {
        this.upowaznieniedwdata = upowaznieniedwdata;
    }

    public Zakladpracy getFirmaId() {
        return firmaId;
    }

    public void setFirmaId(Zakladpracy firmaId) {
        this.firmaId = firmaId;
    }


    public Date getWyslanymailuprdata() {
        return wyslanymailuprdata;
    }

    public void setWyslanymailuprdata(Date wyslanymailuprdata) {
        this.wyslanymailuprdata = wyslanymailuprdata;
    }

    public Date getWyslanymailuprdataprzyp() {
        return wyslanymailuprdataprzyp;
    }

    public void setWyslanymailuprdataprzyp(Date wyslanymailuprdataprzyp) {
        this.wyslanymailuprdataprzyp = wyslanymailuprdataprzyp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.email);
        hash = 59 * hash + Objects.hashCode(this.nazwaszkolenia);
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.sessionstart);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Uczestnicy other = (Uczestnicy) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nazwaszkolenia, other.nazwaszkolenia)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sessionstart, other.sessionstart)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "entity.Uczestnicy[ id=" + id + " ]";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImienazwisko() {
        return imienazwisko;
    }

    public void setImienazwisko(String imienazwisko) {
        this.imienazwisko = imienazwisko;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getNazwaszkolenia() {
        return nazwaszkolenia;
    }

    public void setNazwaszkolenia(String nazwaszkolenia) {
        this.nazwaszkolenia = nazwaszkolenia;
    }

    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    public String getNrupowaznienia() {
        return nrupowaznienia;
    }

    public void setNrupowaznienia(String nrupowaznienia) {
        this.nrupowaznienia = nrupowaznienia;
    }

    public String getIndetyfikator() {
        return indetyfikator;
    }

    public void setIndetyfikator(String indetyfikator) {
        this.indetyfikator = indetyfikator;
    }

    public String getDatanadania() {
        return datanadania;
    }

    public void setDatanadania(String datanadania) {
        this.datanadania = datanadania;
    }

    public String getDataustania() {
        return dataustania;
    }

    public void setDataustania(String dataustania) {
        this.dataustania = dataustania;
    }

    public String getIplogowania() {
        return iplogowania;
    }

    public void setIplogowania(String iplogowania) {
        this.iplogowania = iplogowania;
    }

    public String getDatanadaniadsk() {
        return datanadaniadsk;
    }

    public void setDatanadaniadsk(String datanadaniadsk) {
        this.datanadaniadsk = datanadaniadsk;
    }

    @XmlTransient
    public List<Uczestnicyarchiwum> getUczestnicyarchiwumList() {
        return uczestnicyarchiwumList;
    }

    public void setUczestnicyarchiwumList(List<Uczestnicyarchiwum> uczestnicyarchiwumList) {
        this.uczestnicyarchiwumList = uczestnicyarchiwumList;
    }

    public List<UczestnikGrupa> getUczestnikgrupy() {
        return uczestnikgrupy;
    }

    public void setUczestnikgrupy(List<UczestnikGrupa> uczestnikgrupy) {
        this.uczestnikgrupy = uczestnikgrupy;
    }

    public boolean isDuplikat() {
        return duplikat;
    }

    public void setDuplikat(boolean duplikat) {
        this.duplikat = duplikat;
    }

    public boolean isBraki() {
        return braki;
    }

    public void setBraki(boolean braki) {
        this.braki = braki;
    }

    public int getNrwierszaimport() {
        return nrwierszaimport;
    }

    public void setNrwierszaimport(int nrwierszaimport) {
        this.nrwierszaimport = nrwierszaimport;
    }


   
    
}
