/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "inwestycje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inwestycje.findAll", query = "SELECT i FROM Inwestycje i"),
    @NamedQuery(name = "Inwestycje.findByPodatnik", query = "SELECT i FROM Inwestycje i WHERE i.inwestycjePK.podatnik = :podatnik"),
    @NamedQuery(name = "Inwestycje.findBySkrot", query = "SELECT i FROM Inwestycje i WHERE i.skrot = :skrot"),
    @NamedQuery(name = "Inwestycje.findBySymbol", query = "SELECT i FROM Inwestycje i WHERE i.inwestycjePK.symbol = :symbol"),
    @NamedQuery(name = "Inwestycje.findByOpis", query = "SELECT i FROM Inwestycje i WHERE i.opis = :opis"),
    @NamedQuery(name = "Inwestycje.findByRokrozpoczecia", query = "SELECT i FROM Inwestycje i WHERE i.rokrozpoczecia = :rokrozpoczecia"),
    @NamedQuery(name = "Inwestycje.findByMcrozpoczecia", query = "SELECT i FROM Inwestycje i WHERE i.mcrozpoczecia = :mcrozpoczecia"),
    @NamedQuery(name = "Inwestycje.findByRokzakonczenia", query = "SELECT i FROM Inwestycje i WHERE i.rokzakonczenia = :rokzakonczenia"),
    @NamedQuery(name = "Inwestycje.findByMczakonczenia", query = "SELECT i FROM Inwestycje i WHERE i.mczakonczenia = :mczakonczenia"),
    @NamedQuery(name = "Inwestycje.findByTotal", query = "SELECT i FROM Inwestycje i WHERE i.total = :total"),
    @NamedQuery(name = "Inwestycje.findByZakonczona", query = "SELECT i FROM Inwestycje i WHERE i.zakonczona = :zakonczona")})
public class Inwestycje implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InwestycjePK inwestycjePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "skrot")
    private String skrot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rokrozpoczecia")
    private String rokrozpoczecia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mcrozpoczecia")
    private String mcrozpoczecia;
    @Basic(optional = false)
    @Size(min = 1, max = 4)
    @Column(name = "rokzakonczenia")
    private String rokzakonczenia;
    @Basic(optional = false)
    @Size(min = 1, max = 2)
    @Column(name = "mczakonczenia")
    private String mczakonczenia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private float total;
    @Basic(optional = false)
    @Lob
    @Column(name = "sumazalata")
    private List<Sumazalata> sumazalata;
    @Basic(optional = false)
    @Lob
    @Column(name = "dokumenty")
    private List<Dok> dokumenty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "zakonczona")
    private boolean zakonczona;
    
    class Sumazalata {
        private String rok;
        private Double kwota;

        public String getRok() {
            return rok;
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
    }

    public Inwestycje() {
        this.inwestycjePK = new InwestycjePK();
        this.skrot = "";
        this.opis = "";
        this.rokrozpoczecia = "";
        this.mcrozpoczecia = "";
        this.rokzakonczenia = "";
        this.mczakonczenia = "";
        this.total = 0;
        this.zakonczona = false;
    }

    public Inwestycje(InwestycjePK inwestycjePK) {
        this.inwestycjePK = inwestycjePK;
        this.skrot = "";
        this.opis = "";
        this.rokrozpoczecia = "";
        this.mcrozpoczecia = "";
        this.rokzakonczenia = "";
        this.mczakonczenia = "";
        this.total = 0;
        this.zakonczona = false;
    }

    public Inwestycje(InwestycjePK inwestycjePK, String skrot, String opis, String rokrozpoczecia, String mcrozpoczecia, String rokzakonczenia, String mczakonczenia, float total, List<Sumazalata> sumazalata, List<Dok> dokumenty, boolean zakonczona) {
        this.inwestycjePK = inwestycjePK;
        this.skrot = skrot;
        this.opis = opis;
        this.rokrozpoczecia = rokrozpoczecia;
        this.mcrozpoczecia = mcrozpoczecia;
        this.rokzakonczenia = rokzakonczenia;
        this.mczakonczenia = mczakonczenia;
        this.total = total;
        this.sumazalata = sumazalata;
        this.dokumenty = dokumenty;
        this.zakonczona = zakonczona;
    }

    

    public Inwestycje(String podatnik, String symbol) {
        this.inwestycjePK = new InwestycjePK(podatnik, symbol);
    }

    public InwestycjePK getInwestycjePK() {
        return inwestycjePK;
    }

    public void setInwestycjePK(InwestycjePK inwestycjePK) {
        this.inwestycjePK = inwestycjePK;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
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

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<Sumazalata> getSumazalata() {
        return sumazalata;
    }

    public void setSumazalata(List<Sumazalata> sumazalata) {
        this.sumazalata = sumazalata;
    }

    public List<Dok> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dok> dokumenty) {
        this.dokumenty = dokumenty;
    }
   
    public boolean getZakonczona() {
        return zakonczona;
    }

    public void setZakonczona(boolean zakonczona) {
        this.zakonczona = zakonczona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inwestycjePK != null ? inwestycjePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inwestycje)) {
            return false;
        }
        Inwestycje other = (Inwestycje) object;
        if ((this.inwestycjePK == null && other.inwestycjePK != null) || (this.inwestycjePK != null && !this.inwestycjePK.equals(other.inwestycjePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Inwestycje[ inwestycjePK=" + inwestycjePK + " ]";
    }
    
}
