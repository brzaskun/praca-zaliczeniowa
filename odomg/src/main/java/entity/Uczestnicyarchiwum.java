/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "uczestnicyarchiwum", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_uzytkownik", "sessionstart"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uczestnicyarchiwum.findAll", query = "SELECT u FROM Uczestnicyarchiwum u"),
    @NamedQuery(name = "Uczestnicyarchiwum.findById", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.id = :id"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByWyslanymailupr", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.wyslanymailupr = :wyslanymailupr"),
    @NamedQuery(name = "Uczestnicyarchiwum.findBySessionstart", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.sessionstart = :sessionstart"),
    @NamedQuery(name = "Uczestnicyarchiwum.findBySessionend", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.sessionend = :sessionend"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByWyniktestu", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.wyniktestu = :wyniktestu"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByWyslanycert", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.wyslanycert = :wyslanycert"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByIlosclogowan", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.ilosclogowan = :ilosclogowan"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByIloscpoprawnych", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.iloscpoprawnych = :iloscpoprawnych"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByIloscblednych", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.iloscblednych = :iloscblednych"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByIloscodpowiedzi", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.iloscodpowiedzi = :iloscodpowiedzi"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByNrupowaznienia", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.nrupowaznienia = :nrupowaznienia"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByIndetyfikator", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.indetyfikator = :indetyfikator"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByDatanadania", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.datanadania = :datanadania"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByDataustania", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.dataustania = :dataustania"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByWyslaneup", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.wyslaneup = :wyslaneup"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByData", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.data = :data"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByNazwaszkolenia", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.nazwaszkolenia = :nazwaszkolenia"),
    @NamedQuery(name = "Uczestnicyarchiwum.findByStacjonarny", query = "SELECT u FROM Uczestnicyarchiwum u WHERE u.stacjonarny = :stacjonarny")})
public class Uczestnicyarchiwum implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "wyslanymailupr")
    private Integer wyslanymailupr;
    @Size(max = 25)
    @Column(name = "sessionstart", length = 25)
    private String sessionstart;
    @Size(max = 25)
    @Column(name = "sessionend", length = 25)
    private String sessionend;
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
    @Column(name = "wyslaneup")
    private Boolean wyslaneup;
    @Column(name = "data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Size(max = 50)
    @Column(name = "nazwaszkolenia", length = 50)
    private String nazwaszkolenia;
    @Column(name = "stacjonarny")
    private Boolean stacjonarny;
    @JoinColumn(name = "id_uzytkownik", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Uczestnicy idUzytkownik;

    public Uczestnicyarchiwum() {
    }

    public Uczestnicyarchiwum(Integer id) {
        this.id = id;
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

    public String getSessionstart() {
        return sessionstart;
    }

    public void setSessionstart(String sessionstart) {
        this.sessionstart = sessionstart;
    }

    public String getSessionend() {
        return sessionend;
    }

    public void setSessionend(String sessionend) {
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

    public Boolean getWyslaneup() {
        return wyslaneup;
    }

    public void setWyslaneup(Boolean wyslaneup) {
        this.wyslaneup = wyslaneup;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNazwaszkolenia() {
        return nazwaszkolenia;
    }

    public void setNazwaszkolenia(String nazwaszkolenia) {
        this.nazwaszkolenia = nazwaszkolenia;
    }

    public Boolean getStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(Boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public Uczestnicy getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Uczestnicy idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
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
        if (!(object instanceof Uczestnicyarchiwum)) {
            return false;
        }
        Uczestnicyarchiwum other = (Uczestnicyarchiwum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Uczestnicyarchiwum[ id=" + id + " ]";
    }
    
}
