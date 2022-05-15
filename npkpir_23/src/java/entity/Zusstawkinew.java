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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "zusstawkinew",uniqueConstraints = {@UniqueConstraint(columnNames={"rok","miesiac","rodzajzus", "podatnik", "udzialowiec"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zusstawkinew.findAll", query = "SELECT z FROM Zusstawkinew z"),
    @NamedQuery(name = "Zusstawkinew.findZUS", query = "SELECT z FROM Zusstawkinew z WHERE z.rodzajzus = :rodzajzus"),
    @NamedQuery(name = "Zusstawkinew.findByRok", query = "SELECT z FROM Zusstawkinew z WHERE z.rok = :rok"),
    @NamedQuery(name = "Zusstawkinew.findByMiesiac", query = "SELECT z FROM Zusstawkinew z WHERE z.miesiac = :miesiac"),
    @NamedQuery(name = "Zusstawkinew.findByZus51", query = "SELECT z FROM Zusstawkinew z WHERE z.zus51ch = :zus51ch"),
    @NamedQuery(name = "Zusstawkinew.findByZus52", query = "SELECT z FROM Zusstawkinew z WHERE z.zus52 = :zus52"),
    @NamedQuery(name = "Zusstawkinew.findByZus53", query = "SELECT z FROM Zusstawkinew z WHERE z.zus53 = :zus53")})
public class Zusstawkinew implements Serializable {
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
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @JoinColumn(name = "udzialowiec", referencedColumnName = "id")
    @ManyToOne
    private PodatnikUdzialy udzialowiec;
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
    @Column(name = "pracownicy")
    private boolean pracownicy;

    public Zusstawkinew() {
    }
   
    
    public Zusstawkinew(Zusstawki stary) {
        this.rok = stary.getRok();
        this.miesiac = stary.getMiesiac();
        this.rodzajzus = stary.getRodzajzus();
        this.zus51ch = stary.getZus51ch();
        this.zus51bch = stary.getZus51bch();
        this.zus52 = stary.getZus52();
        this.zus52odl = stary.getZus52odl();
        this.zus53 = stary.getZus53();
        this.pit4 = stary.getPit4();
        this.pit8 = stary.getPit8();
        this.rodzajzus = stary.getRodzajzus();
    }

    public Zusstawkinew(Zusstawkinew stary) {
        this.rok = stary.rok;
        this.miesiac = stary.miesiac;
        this.podatnik = stary.podatnik;
        this.udzialowiec = stary.udzialowiec;
        this.rodzajzus = stary.rodzajzus;
        this.zus51ch = stary.zus51ch;
        this.zus51bch = stary.zus51bch;
        this.zus52 = stary.zus52;
        this.zus52odl = stary.zus52odl;
        this.zus53 = stary.zus53;
        this.pit4 = stary.pit4;
        this.pit8 = stary.pit8;
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

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public PodatnikUdzialy getUdzialowiec() {
        return udzialowiec;
    }

    public void setUdzialowiec(PodatnikUdzialy udzialowiec) {
        this.udzialowiec = udzialowiec;
    }

   
    public boolean isPracownicy() {
        return pracownicy;
    }

    public void setPracownicy(boolean pracownicy) {
        this.pracownicy = pracownicy;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.rok);
        hash = 97 * hash + Objects.hashCode(this.miesiac);
        hash = 97 * hash + Objects.hashCode(this.podatnik);
        hash = 97 * hash + Objects.hashCode(this.udzialowiec);
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
        final Zusstawkinew other = (Zusstawkinew) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.miesiac, other.miesiac)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.udzialowiec, other.udzialowiec)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        if (this.udzialowiec!=null) {
            return "Zusstawkinew{" + "rok=" + rok + ", miesiac=" + miesiac + ", rodzajzus=" + rodzajzus + ", podatnik=" + podatnik.getPrintnazwa() + ", nazwiskoimie=" + udzialowiec.getNazwiskoimie() + '}';
        } else {
            return "Zusstawkinew{" + "rok=" + rok + ", miesiac=" + miesiac + ", rodzajzus=" + rodzajzus + ", podatnik=" + podatnik.getPrintnazwa() +'}';
        }
    }

   

        
}
