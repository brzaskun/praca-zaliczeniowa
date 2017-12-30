/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatViewPola;
import embeddable.EVatwpisSuma;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ewidencjevat", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnik", "rok", "miesiac"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ewidencjevat.findAll", query = "SELECT e FROM Ewidencjevat e"),
    @NamedQuery(name = "Ewidencjevat.findById", query = "SELECT e FROM Ewidencjevat e WHERE e.id = :id"),
    @NamedQuery(name = "Ewidencjevat.findByPodatnik", query = "SELECT e FROM Ewidencjevat e WHERE e.podatnik = :podatnik"),
    @NamedQuery(name = "Ewidencjevat.findByRok", query = "SELECT e FROM Ewidencjevat e WHERE e.rok = :rok"),
    @NamedQuery(name = "Ewidencjevat.findByMiesiac", query = "SELECT e FROM Ewidencjevat e WHERE e.miesiac = :miesiac")})
public class Ewidencjevat implements Serializable {
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
    @Lob
    @Column(name = "ewidencje")
    private HashMap<String, List<EVatViewPola>> ewidencje;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sumaewidencji")
    private HashMap<String, EVatwpisSuma> sumaewidencji;
    
    public Ewidencjevat() {
    }

    public Ewidencjevat(Integer id) {
        this.id = id;
    }

    public Ewidencjevat(Integer id, String podatnik, String rok, String miesiac, HashMap<String, List<EVatViewPola>> ewidencje,HashMap<String, EVatwpisSuma> sumaewidencji) {
        this.id = id;
        this.podatnik = podatnik;
        this.rok = rok;
        this.miesiac = miesiac;
        this.ewidencje = ewidencje;
        this.sumaewidencji = sumaewidencji;
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

    public HashMap<String, List<EVatViewPola>> getEwidencje() {
        return ewidencje;
    }

    public void setEwidencje(HashMap<String, List<EVatViewPola>> ewidencje) {
        this.ewidencje = ewidencje;
    }

   
   
    public HashMap<String, EVatwpisSuma> getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(HashMap<String, EVatwpisSuma> sumaewidencji) {
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
        if (!(object instanceof Ewidencjevat)) {
            return false;
        }
        Ewidencjevat other = (Ewidencjevat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ewidencjevat[ id=" + id + " ]";
    }
    
}
