/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatwpis;
import embeddable.PozycjeSzczegoloweVAT;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "vatpoz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vatpoz.findAll", query = "SELECT v FROM Vatpoz v"),
    @NamedQuery(name = "Vatpoz.findById", query = "SELECT v FROM Vatpoz v WHERE v.id = :id"),
    @NamedQuery(name = "Vatpoz.findByPodatnik", query = "SELECT v FROM Vatpoz v WHERE v.podatnik = :podatnik"),
    @NamedQuery(name = "Vatpoz.findByRok", query = "SELECT v FROM Vatpoz v WHERE v.rok = :rok"),
    @NamedQuery(name = "Vatpoz.findByMiesiac", query = "SELECT v FROM Vatpoz v WHERE v.miesiac = :miesiac"),
    @NamedQuery(name = "Vatpoz.findByCelzlozenia", query = "SELECT v FROM Vatpoz v WHERE v.celzlozenia = :celzlozenia"),
    @NamedQuery(name = "Vatpoz.findByKodurzedu", query = "SELECT v FROM Vatpoz v WHERE v.kodurzedu = :kodurzedu"),
    @NamedQuery(name = "Vatpoz.findByNazwaurzedu", query = "SELECT v FROM Vatpoz v WHERE v.nazwaurzedu = :nazwaurzedu"),
    @NamedQuery(name = "Vatpoz.findByKwotaautoryzacja", query = "SELECT v FROM Vatpoz v WHERE v.kwotaautoryzacja = :kwotaautoryzacja")})
public class Vatpoz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "miesiac")
    private String miesiac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "celzlozenia")
    private String celzlozenia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "kodurzedu")
    private String kodurzedu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwaurzedu")
    private String nazwaurzedu;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "pozycjeszczegolowe")
    private PozycjeSzczegoloweVAT pozycjeszczegolowe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "kwotaautoryzacja")
    private String kwotaautoryzacja;
    @Basic(optional = true)
    @Lob
    @Column(name = "deklaracjakorygowana")
    private Vatpoz deklaracjakorygowana;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sumaewidencji")
    private HashMap<String, EVatwpis> sumaewidencji;

    public Vatpoz() {
    }

    public Vatpoz(Integer id) {
        this.id = id;
    }

    public Vatpoz(Integer id, String podatnik, String rok, String miesiac, String celzlozenia, String kodurzedu, String nazwaurzedu, PozycjeSzczegoloweVAT pozycjeszczegolowe, String kwotaautoryzacja, Vatpoz deklaracjakorygowana) {
        this.id = id;
        this.podatnik = podatnik;
        this.rok = rok;
        this.miesiac = miesiac;
        this.celzlozenia = celzlozenia;
        this.kodurzedu = kodurzedu;
        this.nazwaurzedu = nazwaurzedu;
        this.pozycjeszczegolowe = pozycjeszczegolowe;
        this.kwotaautoryzacja = kwotaautoryzacja;
        this.deklaracjakorygowana = deklaracjakorygowana;
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

    public String getCelzlozenia() {
        return celzlozenia;
    }

    public void setCelzlozenia(String celzlozenia) {
        this.celzlozenia = celzlozenia;
    }

    public String getKodurzedu() {
        return kodurzedu;
    }

    public void setKodurzedu(String kodurzedu) {
        this.kodurzedu = kodurzedu;
    }

    public String getNazwaurzedu() {
        return nazwaurzedu;
    }

    public void setNazwaurzedu(String nazwaurzedu) {
        this.nazwaurzedu = nazwaurzedu;
    }

    public PozycjeSzczegoloweVAT getPozycjeszczegolowe() {
        return pozycjeszczegolowe;
    }

    public void setPozycjeszczegolowe(PozycjeSzczegoloweVAT pozycjeszczegolowe) {
        this.pozycjeszczegolowe = pozycjeszczegolowe;
    }

    
    public String getKwotaautoryzacja() {
        return kwotaautoryzacja;
    }

    public void setKwotaautoryzacja(String kwotaautoryzacja) {
        this.kwotaautoryzacja = kwotaautoryzacja;
    }

    public Vatpoz getDeklaracjakorygowana() {
        return deklaracjakorygowana;
    }

    public void setDeklaracjakorygowana(Vatpoz deklaracjakorygowana) {
        this.deklaracjakorygowana = deklaracjakorygowana;
    }

    public HashMap<String, EVatwpis> getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(HashMap<String, EVatwpis> sumaewidencji) {
        this.sumaewidencji = sumaewidencji;
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
        if (!(object instanceof Vatpoz)) {
            return false;
        }
        Vatpoz other = (Vatpoz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Vatpoz[ id=" + id + " ]";
    }
    
}
