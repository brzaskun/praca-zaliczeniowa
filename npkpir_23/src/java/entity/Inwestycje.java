/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "inwestycje",uniqueConstraints = {
    @UniqueConstraint(
            columnNames={"podatnik, symbol"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inwestycje.findAll", query = "SELECT i FROM Inwestycje i"),
    @NamedQuery(name = "Inwestycje.findById", query = "SELECT i FROM Inwestycje i WHERE i.id = :id"),
    @NamedQuery(name = "Inwestycje.findByPodatnik", query = "SELECT i FROM Inwestycje i WHERE i.podatnik = :podatnik"),
    @NamedQuery(name = "Inwestycje.findByPodatnikZakonczona", query = "SELECT i FROM Inwestycje i WHERE i.podatnik = :podatnik AND i.zakonczona = :zakonczona"),
    @NamedQuery(name = "Inwestycje.findBySkrot", query = "SELECT i FROM Inwestycje i WHERE i.skrot = :skrot"),
    @NamedQuery(name = "Inwestycje.findBySymbol", query = "SELECT i FROM Inwestycje i WHERE i.symbol = :symbol"),
    @NamedQuery(name = "Inwestycje.findByOpis", query = "SELECT i FROM Inwestycje i WHERE i.opis = :opis"),
    @NamedQuery(name = "Inwestycje.findByRokrozpoczecia", query = "SELECT i FROM Inwestycje i WHERE i.rokrozpoczecia = :rokrozpoczecia"),
    @NamedQuery(name = "Inwestycje.findByMcrozpoczecia", query = "SELECT i FROM Inwestycje i WHERE i.mcrozpoczecia = :mcrozpoczecia"),
    @NamedQuery(name = "Inwestycje.findByRokzakonczenia", query = "SELECT i FROM Inwestycje i WHERE i.rokzakonczenia = :rokzakonczenia"),
    @NamedQuery(name = "Inwestycje.findByMczakonczenia", query = "SELECT i FROM Inwestycje i WHERE i.mczakonczenia = :mczakonczenia"),
    @NamedQuery(name = "Inwestycje.findByTotal", query = "SELECT i FROM Inwestycje i WHERE i.total = :total"),
    @NamedQuery(name = "Inwestycje.findByZakonczona", query = "SELECT i FROM Inwestycje i WHERE i.zakonczona = :zakonczona")})
public class Inwestycje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Size(max = 20)
    @Column(name = "skrot")
    private String skrot;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "symbol")
    private String symbol;
    @Size(max = 255)
    @Column(name = "opis")
    private String opis;
    @Size(max = 4)
    @Column(name = "rokrozpoczecia")
    private String rokrozpoczecia;
    @Size(max = 2)
    @Column(name = "mcrozpoczecia")
    private String mcrozpoczecia;
    @Size(max = 4)
    @Column(name = "rokzakonczenia")
    private String rokzakonczenia;
    @Size(max = 2)
    @Column(name = "mczakonczenia")
    private String mczakonczenia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Lob
    @Column(name = "sumazalata")
    private List<Sumazalata> sumazalata;
    @Column(name = "zakonczona")
    private Boolean zakonczona;
    @JoinColumn(name = "inwestycja")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Dok> doklist;

    

    public Inwestycje(Integer id) {
        this.id = id;
        this.doklist = new ArrayList<>();
    }

    public Inwestycje() {
        this.podatnik = "";
        this.skrot = "";
        this.symbol = "";
        this.opis = "";
        this.rokrozpoczecia = "";
        this.mcrozpoczecia = "";
        this.rokzakonczenia = "";
        this.mczakonczenia = "";
        this.total = 0.0;
        this.sumazalata = new ArrayList<>();
        this.doklist = new ArrayList<>();
        this.zakonczona = false;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRokrozpoczecia() {
        return rokrozpoczecia;
    }

    public void setRokrozpoczecia(String rokrozpoczecia) {
        this.rokrozpoczecia = rokrozpoczecia;
    }

    public String getMcrozpoczecia() {
        return mcrozpoczecia;
    }

    public void setMcrozpoczecia(String mcrozpoczecia) {
        this.mcrozpoczecia = mcrozpoczecia;
    }

    public String getRokzakonczenia() {
        return rokzakonczenia;
    }

    public void setRokzakonczenia(String rokzakonczenia) {
        this.rokzakonczenia = rokzakonczenia;
    }

    public String getMczakonczenia() {
        return mczakonczenia;
    }

    public void setMczakonczenia(String mczakonczenia) {
        this.mczakonczenia = mczakonczenia;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Sumazalata> getSumazalata() {
        return sumazalata;
    }

    public void setSumazalata(List<Sumazalata> sumazalata) {
        this.sumazalata = sumazalata;
    }

    public List<Dok> getDoklist() {
        return doklist;
    }

    public void setDoklist(List<Dok> doklist) {
        this.doklist = doklist;
    }

    

    public Boolean getZakonczona() {
        return zakonczona;
    }

    public void setZakonczona(Boolean zakonczona) {
        this.zakonczona = zakonczona;
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
        if (!(object instanceof Inwestycje)) {
            return false;
        }
        Inwestycje other = (Inwestycje) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inwestycje{" + "podatnik=" + podatnik + ", skrot=" + skrot + ", symbol=" + symbol + ", opis=" + opis + ", rokrozpoczecia=" + rokrozpoczecia + ", mcrozpoczecia=" + mcrozpoczecia + ", rokzakonczenia=" + rokzakonczenia + ", mczakonczenia=" + mczakonczenia + ", total=" + total + ", zakonczona=" + zakonczona + '}';
    }

    

    public class Sumazalata implements Serializable {
        private static final long serialVersionUID = -6730292558506174856L;
        private String rok;
        private Double kwota;

        public String getRok() {
            return rok;
        }

        public Sumazalata(String rok) {
            this.rok = rok;
            this.kwota = 0.0;
        }

        public Sumazalata() {
        }
        
        

        public void setRok(String rok) {
            this.rok = rok;
        }

        public Double getKwota() {
            return kwota;
        }

        public void setKwota(Double kwota) {
            this.kwota = kwota;
        }

        @Override
        public String toString() {
            return "Sumazalata{" + "rok=" + rok + ", kwota=" + kwota + '}';
        }
        
        
    }
    
}
