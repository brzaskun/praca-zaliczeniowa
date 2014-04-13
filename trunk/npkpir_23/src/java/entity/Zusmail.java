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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusmail.findAll", query = "SELECT z FROM Zusmail z"),
    @NamedQuery(name = "Zusmail.findByPK", query = "SELECT z FROM Zusmail z WHERE z.zusmailPK.podatnik = :podatnik AND z.zusmailPK.rok = :rok AND z.zusmailPK.mc = :mc"),
    @NamedQuery(name = "Zusmail.findByPodatnik", query = "SELECT z FROM Zusmail z WHERE z.zusmailPK.podatnik = :podatnik"),
    @NamedQuery(name = "Zusmail.findByRok", query = "SELECT z FROM Zusmail z WHERE z.zusmailPK.rok = :rok"),
    @NamedQuery(name = "Zusmail.findByMc", query = "SELECT z FROM Zusmail z WHERE z.zusmailPK.mc = :mc"),
    @NamedQuery(name = "Zusmail.findByRokMc", query = "SELECT z FROM Zusmail z WHERE z.zusmailPK.rok = :rok AND z.zusmailPK.mc = :mc"),
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
    @EmbeddedId
    protected ZusmailPK zusmailPK;
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
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawysylki;
    @Basic(optional = false)
    @NotNull
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

    public Zusmail(ZusmailPK zusmailPK) {
        this.zusmailPK = zusmailPK;
    }

    public Zusmail(ZusmailPK zusmailPK, Date datawysylki, int nrwysylki, String adresmail, String tytul, String tresc, String wysylajacy) {
        this.zusmailPK = zusmailPK;
        this.datawysylki = datawysylki;
        this.nrwysylki = nrwysylki;
        this.adresmail = adresmail;
        this.tytul = tytul;
        this.tresc = tresc;
        this.wysylajacy = wysylajacy;
    }

    public Zusmail(String podatnik, String rok, String mc) {
        this.zusmailPK = new ZusmailPK(podatnik, rok, mc);
    }

    public ZusmailPK getZusmailPK() {
        return zusmailPK;
    }

    public void setZusmailPK(ZusmailPK zusmailPK) {
        this.zusmailPK = zusmailPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zusmailPK != null ? zusmailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zusmail)) {
            return false;
        }
        Zusmail other = (Zusmail) object;
        if ((this.zusmailPK == null && other.zusmailPK != null) || (this.zusmailPK != null && !this.zusmailPK.equals(other.zusmailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zusmail[ zusmailPK=" + zusmailPK + " ]";
    }
    
}
