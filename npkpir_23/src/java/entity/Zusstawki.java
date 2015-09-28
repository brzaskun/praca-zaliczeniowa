/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zusstawki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusstawki.findAll", query = "SELECT z FROM Zusstawki z"),
    @NamedQuery(name = "Zusstawki.findZUS", query = "SELECT z FROM Zusstawki z WHERE z.zusstawkiPK.malyzus = :duzy0maly1"),
    @NamedQuery(name = "Zusstawki.findByRok", query = "SELECT z FROM Zusstawki z WHERE z.zusstawkiPK.rok = :rok"),
    @NamedQuery(name = "Zusstawki.findByMiesiac", query = "SELECT z FROM Zusstawki z WHERE z.zusstawkiPK.miesiac = :miesiac"),
    @NamedQuery(name = "Zusstawki.findByZus51", query = "SELECT z FROM Zusstawki z WHERE z.zus51ch = :zus51ch"),
    @NamedQuery(name = "Zusstawki.findByZus52", query = "SELECT z FROM Zusstawki z WHERE z.zus52 = :zus52"),
    @NamedQuery(name = "Zusstawki.findByZus53", query = "SELECT z FROM Zusstawki z WHERE z.zus53 = :zus53")})
public class Zusstawki implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ZusstawkiPK zusstawkiPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zus51ch")
    private Double zus51ch;
    @Column(name = "zus51bch")
    private Double zus51bch;
    @Column(name = "zus52")
    private Double zus52;
    @Column(name = "zus52odl")
    private Double zus52odl;
    @Column(name = "zus53")
    private Double zus53;
    @Column(name = "pit4")
    private Double pit4;
   

    public Zusstawki() {
        zusstawkiPK = new ZusstawkiPK();
    }

    public Zusstawki(Zusstawki stary) {
        this.zusstawkiPK = stary.zusstawkiPK;
        this.zus51ch = stary.zus51ch;
        this.zus51bch = stary.zus51bch;
        this.zus52 = stary.zus52;
        this.zus52odl = stary.zus52odl;
        this.zus53 = stary.zus53;
        this.pit4 = stary.pit4;
    }

    public Zusstawki(ZusstawkiPK zusstawkiPK){
        this.zusstawkiPK = zusstawkiPK;
    }

    public Zusstawki(String rok, String miesiac) {
        this.zusstawkiPK = new ZusstawkiPK(rok, miesiac);
    }

    @PostConstruct
    public void init() {
        zusstawkiPK = new ZusstawkiPK();
    }

    public ZusstawkiPK getZusstawkiPK() {
        return zusstawkiPK;
    }

    public void setZusstawkiPK(ZusstawkiPK zusstawkiPK) {
        this.zusstawkiPK = zusstawkiPK;
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

       
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (zusstawkiPK != null ? zusstawkiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zusstawki other = (Zusstawki) obj;
        if (!Objects.equals(this.zusstawkiPK, other.zusstawkiPK)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "entity.Zusstawki[ zusstawkiPK=" + zusstawkiPK + " ]";
    }
    
}
