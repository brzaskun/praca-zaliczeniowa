/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;


import entity.VatUe;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vatuepodatnik.findAll", query = "SELECT v FROM Vatuepodatnik v"),
    @NamedQuery(name = "Vatuepodatnik.findByMc0kw1", query = "SELECT v FROM Vatuepodatnik v WHERE v.mc0kw1 = :mc0kw1"),
    @NamedQuery(name = "Vatuepodatnik.findByRozliczone", query = "SELECT v FROM Vatuepodatnik v WHERE v.rozliczone = :rozliczone"),
    @NamedQuery(name = "Vatuepodatnik.findByRok", query = "SELECT v FROM Vatuepodatnik v WHERE v.vatuepodatnikPK.rok = :rok"),
    @NamedQuery(name = "Vatuepodatnik.findByKlient", query = "SELECT v FROM Vatuepodatnik v WHERE v.vatuepodatnikPK.klient = :klient"),
    @NamedQuery(name = "Vatuepodatnik.findBySymbolokresu", query = "SELECT v FROM Vatuepodatnik v WHERE v.vatuepodatnikPK.symbolokresu = :symbolokresu"),
    @NamedQuery(name = "Vatuepodatnik.findByRokKlientSymbolokresu", query = "SELECT v FROM Vatuepodatnik v WHERE v.vatuepodatnikPK.rok = :rok AND v.vatuepodatnikPK.klient = :klient AND v.vatuepodatnikPK.symbolokresu = :symbolokresu"),
})
public class Vatuepodatnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VatuepodatnikPK vatuepodatnikPK;
    @Lob
    private List klienciwdtwnt;
    private Boolean mc0kw1;
    private Boolean rozliczone;

    public Vatuepodatnik() {
    }

    public Vatuepodatnik(VatuepodatnikPK vatuepodatnikPK) {
        this.vatuepodatnikPK = vatuepodatnikPK;
    }

    public Vatuepodatnik(String rok, String klient, String symbolokresu) {
        this.vatuepodatnikPK = new VatuepodatnikPK(rok, klient, symbolokresu);
    }

    public VatuepodatnikPK getVatuepodatnikPK() {
        return vatuepodatnikPK;
    }

    public void setVatuepodatnikPK(VatuepodatnikPK vatuepodatnikPK) {
        this.vatuepodatnikPK = vatuepodatnikPK;
    }

    public List getKlienciwdtwnt() {
        return klienciwdtwnt;
    }

    public void setKlienciwdtwnt(List klienciwdtwnt) {
        this.klienciwdtwnt = klienciwdtwnt;
    }


    public Boolean getMc0kw1() {
        return mc0kw1;
    }

    public void setMc0kw1(Boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }

    public Boolean getRozliczone() {
        return rozliczone;
    }

    public void setRozliczone(Boolean rozliczone) {
        this.rozliczone = rozliczone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vatuepodatnikPK != null ? vatuepodatnikPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vatuepodatnik)) {
            return false;
        }
        Vatuepodatnik other = (Vatuepodatnik) object;
        if ((this.vatuepodatnikPK == null && other.vatuepodatnikPK != null) || (this.vatuepodatnikPK != null && !this.vatuepodatnikPK.equals(other.vatuepodatnikPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Vatuepodatnik[ vatuepodatnikPK=" + vatuepodatnikPK + " ]";
    }
    
}
