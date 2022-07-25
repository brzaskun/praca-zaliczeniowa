/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zusstawki",uniqueConstraints = {@UniqueConstraint(columnNames={"rok","miesiac","rodzajzus"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusstawki.findAll", query = "SELECT z FROM Zusstawki z"),
    @NamedQuery(name = "Zusstawki.findZUS", query = "SELECT z FROM Zusstawki z WHERE z.rodzajzus = :rodzajzus"),
    @NamedQuery(name = "Zusstawki.findByRok", query = "SELECT z FROM Zusstawki z WHERE z.rok = :rok"),
    @NamedQuery(name = "Zusstawki.findByMiesiac", query = "SELECT z FROM Zusstawki z WHERE z.miesiac = :miesiac"),
    @NamedQuery(name = "Zusstawki.findByZus51", query = "SELECT z FROM Zusstawki z WHERE z.zus51ch = :zus51ch"),
    @NamedQuery(name = "Zusstawki.findByZus52", query = "SELECT z FROM Zusstawki z WHERE z.zus52 = :zus52"),
    @NamedQuery(name = "Zusstawki.findByZus53", query = "SELECT z FROM Zusstawki z WHERE z.zus53 = :zus53")})
public class Zusstawki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "miesiac")
    private String miesiac;
    @Column(name = "rodzajzus")
    private int rodzajzus;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "zus51ch")
    private double zus51ch;
    @Column(name = "zus51bch")
    private double zus51bch;
    @Column(name = "zus52")
    private double zus52;
    @Column(name = "zus52odl")
    private double zus52odl;
    @Column(name = "zus53")
    private double zus53;
    @Column(name = "pit4")
    private double pit4;
    @Column(name = "pit8")
    private double pit8;


   

    public Zusstawki() {
        
    }

    public Zusstawki(Zusstawki stary) {
        this.rok = stary.rok;
        this.miesiac = stary.miesiac;
        this.rodzajzus = stary.rodzajzus;
        this.zus51ch = stary.zus51ch;
        this.zus51bch = stary.zus51bch;
        this.zus52 = stary.zus52;
        this.zus52odl = stary.zus52odl;
        this.zus53 = stary.zus53;
        this.pit4 = stary.pit4;
    }

    public Zusstawki(String rok, String miesiac, int rodzajzus, double zus51ch, double zus51bch, double zus52, double zus52odl, double zus53, double pit4, double pit8) {
        this.rok = rok;
        this.miesiac = miesiac;
        this.rodzajzus = rodzajzus;
        this.zus51ch = zus51ch;
        this.zus51bch = zus51bch;
        this.zus52 = zus52;
        this.zus52odl = zus52odl;
        this.zus53 = zus53;
        this.pit4 = pit4;
        this.pit8 = pit8;
    }
    
    

    public double getZus51ch() {
        return zus51ch;
    }

    public void setZus51ch(double zus51ch) {
        this.zus51ch = zus51ch;
    }

    public double getZus51bch() {
        return zus51bch;
    }

    public void setZus51bch(double zus51bch) {
        this.zus51bch = zus51bch;
    }

   

    public double getZus52() {
        return zus52;
    }

    public void setZus52(double zus52) {
        this.zus52 = zus52;
    }

    public double getZus52odl() {
        return zus52odl;
    }

    public void setZus52odl(double zus52odl) {
        this.zus52odl = zus52odl;
    }

    
    public double getZus53() {
        return zus53;
    }

    public void setZus53(double zus53) {
        this.zus53 = zus53;
    }

    public double getPit4() {
        return pit4;
    }

    public void setPit4(double pit4) {
        this.pit4 = pit4;
    }

    public double getPit8() {
        return pit8;
    }

    public void setPit8(double pit8) {
        this.pit8 = pit8;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public int getRodzajzus() {
        return rodzajzus;
    }

    public void setRodzajzus(int rodzajzus) {
        this.rodzajzus = rodzajzus;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.rok);
        hash = 79 * hash + Objects.hashCode(this.miesiac);
        hash = 79 * hash + this.rodzajzus;
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
        final Zusstawki other = (Zusstawki) obj;
        if (this.rodzajzus != other.rodzajzus) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.miesiac, other.miesiac)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zusstawki{" + "rok=" + rok + ", miesiac=" + miesiac + ", rodzajzus=" + rodzajzus + ", zus51ch=" + zus51ch + ", zus52=" + zus52 + ", zus53=" + zus53 + ", pit4=" + pit4 + '}';
    }


    
    
   

    
}
