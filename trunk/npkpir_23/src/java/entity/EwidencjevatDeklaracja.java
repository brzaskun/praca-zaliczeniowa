/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatViewPola;
import embeddable.EVatwpisSuma;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "ewidencjevatdeklaracja")
@XmlRootElement
public class EwidencjevatDeklaracja implements Serializable {
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
    @ManyToOne
    private Evewidencja ewidencja;
    @Lob
    @Column(name = "wierszeEwidencji")
    private List<EVatViewPola> wierszeEwidencji;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "sumaewidencji")
    private EVatwpisSuma sumaewidencji;
    @JoinColumn(name = "deklaracja")
    @ManyToOne(cascade = CascadeType.ALL)
    private Deklaracjevat deklaracja;
    
    public EwidencjevatDeklaracja() {
    }

    public EwidencjevatDeklaracja(Integer id) {
        this.id = id;
    }

    public EwidencjevatDeklaracja(Integer id, String podatnik, String rok, String miesiac, Evewidencja ewidencja, List<EVatViewPola> wierszeEwidencji, EVatwpisSuma sumaewidencji, Deklaracjevat deklaracja) {
        this.id = id;
        this.podatnik = podatnik;
        this.rok = rok;
        this.miesiac = miesiac;
        this.ewidencja = ewidencja;
        this.wierszeEwidencji = wierszeEwidencji;
        this.sumaewidencji = sumaewidencji;
        this.deklaracja = deklaracja;
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

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
    }

    public List<EVatViewPola> getWierszeEwidencji() {
        return wierszeEwidencji;
    }

    public void setWierszeEwidencji(List<EVatViewPola> wierszeEwidencji) {
        this.wierszeEwidencji = wierszeEwidencji;
    }

   

    public EVatwpisSuma getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(EVatwpisSuma sumaewidencji) {
        this.sumaewidencji = sumaewidencji;
    }


    public Deklaracjevat getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(Deklaracjevat deklaracja) {
        this.deklaracja = deklaracja;
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
        if (!(object instanceof EwidencjevatDeklaracja)) {
            return false;
        }
        EwidencjevatDeklaracja other = (EwidencjevatDeklaracja) object;
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
