/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnik", "rok", "mc"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusmail.findAll", query = "SELECT z FROM Zusmail z"),
    @NamedQuery(name = "Zusmail.findByPK", query = "SELECT z FROM Zusmail z WHERE z.podatnik = :podatnik AND z.rok = :rok AND z.mc = :mc"),
    @NamedQuery(name = "Zusmail.findByPodatnik", query = "SELECT z FROM Zusmail z WHERE z.podatnik = :podatnik"),
    @NamedQuery(name = "Zusmail.findByRok", query = "SELECT z FROM Zusmail z WHERE z.rok = :rok"),
    @NamedQuery(name = "Zusmail.findByMc", query = "SELECT z FROM Zusmail z WHERE z.mc = :mc"),
    @NamedQuery(name = "Zusmail.findByRokMc", query = "SELECT z FROM Zusmail z WHERE z.rok = :rok AND z.mc = :mc"),
    @NamedQuery(name = "Zusmail.findByZus51ch", query = "SELECT z FROM Zusmail z WHERE z.zus51ch = :zus51ch"),
    @NamedQuery(name = "Zusmail.findByZus51bch", query = "SELECT z FROM Zusmail z WHERE z.zus51bch = :zus51bch"),
    @NamedQuery(name = "Zusmail.findByZus52", query = "SELECT z FROM Zusmail z WHERE z.zus52 = :zus52"),
    @NamedQuery(name = "Zusmail.findByZus52odl", query = "SELECT z FROM Zusmail z WHERE z.zus52odl = :zus52odl"),
    @NamedQuery(name = "Zusmail.findByZus53", query = "SELECT z FROM Zusmail z WHERE z.zus53 = :zus53"),
    @NamedQuery(name = "Zusmail.findByPit4", query = "SELECT z FROM Zusmail z WHERE z.pit4 = :pit4"),
    @NamedQuery(name = "Zusmail.findByDatawysylki", query = "SELECT z FROM Zusmail z WHERE z.datawysylki = :datawysylki"),
    @NamedQuery(name = "Zusmail.findByNrwysylki", query = "SELECT z FROM Zusmail z WHERE z.nrwysylki = :nrwysylki"),
    @NamedQuery(name = "Zusmail.findByAdresmail", query = "SELECT z FROM Zusmail z WHERE z.adresmail = :adresmail"),
    @NamedQuery(name = "Zusmail.findByTytul", query = "SELECT z FROM Zusmail z WHERE z.tytul = :tytul"),
    @NamedQuery(name = "Zusmail.findByWysylajacy", query = "SELECT z FROM Zusmail z WHERE z.wysylajacy = :wysylajacy")})
public class Zusmail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    private Podatnik podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(nullable = false, length = 2)
    private String mc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double zus51ch;
    @Column(precision = 22)
    private Double zus51bch;
    @Column(precision = 22)
    private Double zus52;
    @Column(precision = 22)
    private Double zus52odl;
    @Column(precision = 22)
    private Double zus53;
    @Column(precision = 22)
    private Double pit4;
    @Column(precision = 22)
    private Double pit8;
    @Column(precision = 22)
    private Double zus;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datawysylki;
    @Basic(optional = false)
    @Column(nullable = false)
    private int nrwysylki = 0;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String adresmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String tytul;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(nullable = false, length = 65535)
    private String tresc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String wysylajacy;

    public Zusmail() {
    }

    public Zusmail(Podatnik p, String rok, String mc) {
        this.podatnik = p;
        this.rok = rok;
        this.mc = mc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

  
  
  
    public Double getZus51ch() {
        return zus51ch;
    }

    public void setZus51ch(Double zus51ch) {
        this.zus51ch = zus51ch;
    }

    public Double getZus51bch() {
        return zus51bch;
    }

    public void setZus51bch(Double zus51bch) {
        this.zus51bch = zus51bch;
    }

    public Double getZus52() {
        return zus52;
    }

    public void setZus52(Double zus52) {
        this.zus52 = zus52;
    }

    public Double getZus52odl() {
        return zus52odl;
    }

    public void setZus52odl(Double zus52odl) {
        this.zus52odl = zus52odl;
    }

    public Double getZus53() {
        return zus53;
    }

    public void setZus53(Double zus53) {
        this.zus53 = zus53;
    }

    public Double getPit4() {
        return pit4;
    }

    public Double getPit8() {
        return pit8;
    }

    public void setPit8(Double pit8) {
        this.pit8 = pit8;
    }

    public void setPit4(Double pit4) {
        this.pit4 = pit4;
    }

    public Date getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(Date datawysylki) {
        this.datawysylki = datawysylki;
    }

    public int getNrwysylki() {
        return nrwysylki;
    }

    public void setNrwysylki(int nrwysylki) {
        this.nrwysylki = nrwysylki;
    }

    public String getAdresmail() {
        return adresmail;
    }

    public void setAdresmail(String adresmail) {
        this.adresmail = adresmail;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getWysylajacy() {
        return wysylajacy;
    }

    public void setWysylajacy(String wysylajacy) {
        this.wysylajacy = wysylajacy;
    }

    public Double getZus() {
        return zus;
    }

    public void setZus(Double zus) {
        this.zus = zus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.podatnik);
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + Objects.hashCode(this.mc);
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
        final Zusmail other = (Zusmail) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }
    
    

  

    @Override
    public String toString() {
        return "Zusmail{" + "podatnik=" + podatnik + ", rok=" + rok + ", mc=" + mc + ", zus51ch=" + zus51ch + ", zus52=" + zus52 + ", zus53=" + zus53 + ", pit4=" + pit4 + '}';
    }

    

 
    
}
