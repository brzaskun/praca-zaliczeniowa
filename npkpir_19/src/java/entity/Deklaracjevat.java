/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatwpis;
import embeddable.EVatwpisSuma;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.Vatpoz;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "deklaracjevat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deklaracjevat.findAll", query = "SELECT d FROM Deklaracjevat d"),
    @NamedQuery(name = "Deklaracjevat.findById", query = "SELECT d FROM Deklaracjevat d WHERE d.id = :id"),
    @NamedQuery(name = "Deklaracjevat.findByIdentyfikator", query = "SELECT d FROM Deklaracjevat d WHERE d.identyfikator = :identyfikator"),
    @NamedQuery(name = "Deklaracjevat.findByKodurzedu", query = "SELECT d FROM Deklaracjevat d WHERE d.kodurzedu = :kodurzedu"),
    @NamedQuery(name = "Deklaracjevat.findByMiesiac", query = "SELECT d FROM Deklaracjevat d WHERE d.miesiac = :miesiac"),
    @NamedQuery(name = "Deklaracjevat.findByNrkolejny", query = "SELECT d FROM Deklaracjevat d WHERE d.nrkolejny = :nrkolejny"),
    @NamedQuery(name = "Deklaracjevat.findByPodatnik", query = "SELECT d FROM Deklaracjevat d WHERE d.podatnik = :podatnik"),
    @NamedQuery(name = "Deklaracjevat.findByRok", query = "SELECT d FROM Deklaracjevat d WHERE d.rok = :rok"),
    @NamedQuery(name = "Deklaracjevat.findByRokMcPod", query = "SELECT d FROM Deklaracjevat d WHERE d.rok = :rok AND d.miesiac = :miesiac AND d.podatnik = :podatnik")})
public class Deklaracjevat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "deklaracja")
    private String deklaracja;
    @Lob
    @Column(name = "ewidencje")
    private HashMap<String, ArrayList>  ewidencje;
    @Size(max = 255)
    @Column(name = "identyfikator")
    private String identyfikator;
    @Size(max = 255)
    @Column(name = "kodurzedu")
    private String kodurzedu;
    @Size(max = 255)
    @Column(name = "miesiac")
    private String miesiac;
    @Column(name = "nrkolejny")
    private int nrkolejny;
    @Size(max = 255)
    @Column(name = "podatnik")
    private String podatnik;
    @Lob
    @Column(name = "podsumowanieewidencji")
    private HashMap<String, EVatwpisSuma>  podsumowanieewidencji;
    @Lob
    @Column(name = "pozycjeszczegolowe")
    private PozycjeSzczegoloweVAT pozycjeszczegolowe;
    @Size(max = 255)
    @Column(name = "rok")
    private String rok;
    @Lob
    @Column(name = "selected")
    private Vatpoz selected;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "upo")
    private String upo;
    @Size(max = 255)
    @Column(name = "status")
    private String status;
    @Size(max = 255)
    @Column(name = "opis")
    private String opis;

    public Deklaracjevat() {
    }

    public Deklaracjevat(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(String deklaracja) {
        this.deklaracja = deklaracja;
    }

    public HashMap<String, ArrayList> getEwidencje() {
        return ewidencje;
    }

    public void setEwidencje(HashMap<String, ArrayList> ewidencje) {
        this.ewidencje = ewidencje;
    }

   
    public String getIdentyfikator() {
        return identyfikator;
    }

    public void setIdentyfikator(String identyfikator) {
        this.identyfikator = identyfikator;
    }

    public String getKodurzedu() {
        return kodurzedu;
    }

    public void setKodurzedu(String kodurzedu) {
        this.kodurzedu = kodurzedu;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public int getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(int nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public HashMap<String, EVatwpisSuma> getPodsumowanieewidencji() {
        return podsumowanieewidencji;
    }

    public void setPodsumowanieewidencji(HashMap<String, EVatwpisSuma> podsumowanieewidencji) {
        this.podsumowanieewidencji = podsumowanieewidencji;
    }

    public PozycjeSzczegoloweVAT getPozycjeszczegolowe() {
        return pozycjeszczegolowe;
    }

    public void setPozycjeszczegolowe(PozycjeSzczegoloweVAT pozycjeszczegolowe) {
        this.pozycjeszczegolowe = pozycjeszczegolowe;
    }

  
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

   

    public String getUpo() {
        return upo;
    }

    public void setUpo(String upo) {
        this.upo = upo;
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
        if (!(object instanceof Deklaracjevat)) {
            return false;
        }
        Deklaracjevat other = (Deklaracjevat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Deklaracjevat[ id=" + id + " ]";
    }
    
}
