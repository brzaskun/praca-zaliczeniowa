/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zmiennawynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zmiennawynagrodzenia.findAll", query = "SELECT z FROM Zmiennawynagrodzenia z"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findById", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.id = :id"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByNazwa", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.nazwa = :nazwa"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDataod", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.dataod = :dataod"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByDatado", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.datado = :datado"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByKwotastala", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.kwotastala = :kwotastala"),
    @NamedQuery(name = "Zmiennawynagrodzenia.findByStala0zmienna1", query = "SELECT z FROM Zmiennawynagrodzenia z WHERE z.stala0zmienna1 = :stala0zmienna1")})
public class Zmiennawynagrodzenia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 45)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 45)
    @Column(name = "datado")
    private String datado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotastala")
    private Double kwotastala;
    @Column(name = "stala0zmienna1")
    private Boolean stala0zmienna1;
    @OneToMany(mappedBy = "zmiennawynagrodzenia")
    private List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList;

    public Zmiennawynagrodzenia() {
    }

    public Zmiennawynagrodzenia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public Double getKwotastala() {
        return kwotastala;
    }

    public void setKwotastala(Double kwotastala) {
        this.kwotastala = kwotastala;
    }

    public Boolean getStala0zmienna1() {
        return stala0zmienna1;
    }

    public void setStala0zmienna1(Boolean stala0zmienna1) {
        this.stala0zmienna1 = stala0zmienna1;
    }

    @XmlTransient
    public List<Skladnikwynagrodzenia> getSkladnikwynagrodzeniaList() {
        return skladnikwynagrodzeniaList;
    }

    public void setSkladnikwynagrodzeniaList(List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList) {
        this.skladnikwynagrodzeniaList = skladnikwynagrodzeniaList;
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
        if (!(object instanceof Zmiennawynagrodzenia)) {
            return false;
        }
        Zmiennawynagrodzenia other = (Zmiennawynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zmiennawynagrodzenia[ id=" + id + " ]";
    }
    
}
