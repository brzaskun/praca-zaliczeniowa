/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "definicjalistaplac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Definicjalistaplac.findAll", query = "SELECT d FROM Definicjalistaplac d"),
    @NamedQuery(name = "Definicjalistaplac.findById", query = "SELECT d FROM Definicjalistaplac d WHERE d.id = :id"),
    @NamedQuery(name = "Definicjalistaplac.findByOpis", query = "SELECT d FROM Definicjalistaplac d WHERE d.opis = :opis"),
    @NamedQuery(name = "Definicjalistaplac.findByDatasporzadzenia", query = "SELECT d FROM Definicjalistaplac d WHERE d.datasporzadzenia = :datasporzadzenia"),
    @NamedQuery(name = "Definicjalistaplac.findByDatapodatek", query = "SELECT d FROM Definicjalistaplac d WHERE d.datapodatek = :datapodatek"),
    @NamedQuery(name = "Definicjalistaplac.findByDatazus", query = "SELECT d FROM Definicjalistaplac d WHERE d.datazus = :datazus"),
    @NamedQuery(name = "Definicjalistaplac.findByNrkolejny", query = "SELECT d FROM Definicjalistaplac d WHERE d.nrkolejny = :nrkolejny")})
public class Definicjalistaplac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "opis")
    private String opis;
    @Size(max = 45)
    @Column(name = "datasporzadzenia")
    private String datasporzadzenia;
    @Size(max = 45)
    @Column(name = "datapodatek")
    private String datapodatek;
    @Size(max = 45)
    @Column(name = "datazus")
    private String datazus;
    @Size(max = 45)
    @Column(name = "nrkolejny")
    private String nrkolejny;
    @JoinColumn(name = "rodzajlistyplac", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajlistyplac rodzajlistyplac;
    @OneToMany(mappedBy = "definicjalistaplac")
    private List<Naliczeniepotracenie> naliczeniepotracenieList;
    @OneToMany(mappedBy = "definicjalistaplac")
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "definicjalistaplac")
    private List<Pasekwynagrodzen> pasekwynagrodzenList;

    public Definicjalistaplac() {
    }

    public Definicjalistaplac(Integer id) {
        this.id = id;
    }

    public Definicjalistaplac(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getDatasporzadzenia() {
        return datasporzadzenia;
    }

    public void setDatasporzadzenia(String datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public String getDatapodatek() {
        return datapodatek;
    }

    public void setDatapodatek(String datapodatek) {
        this.datapodatek = datapodatek;
    }

    public String getDatazus() {
        return datazus;
    }

    public void setDatazus(String datazus) {
        this.datazus = datazus;
    }

    public String getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(String nrkolejny) {
        this.nrkolejny = nrkolejny;
    }

    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }

    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }

    @XmlTransient
    public List<Naliczeniepotracenie> getNaliczeniepotracenieList() {
        return naliczeniepotracenieList;
    }

    public void setNaliczeniepotracenieList(List<Naliczeniepotracenie> naliczeniepotracenieList) {
        this.naliczeniepotracenieList = naliczeniepotracenieList;
    }

    @XmlTransient
    public List<Naliczenieskladnikawynagrodzenia> getNaliczenieskladnikawynagrodzeniaList() {
        return naliczenieskladnikawynagrodzeniaList;
    }

    public void setNaliczenieskladnikawynagrodzeniaList(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        this.naliczenieskladnikawynagrodzeniaList = naliczenieskladnikawynagrodzeniaList;
    }

    @XmlTransient
    public List<Pasekwynagrodzen> getPasekwynagrodzenList() {
        return pasekwynagrodzenList;
    }

    public void setPasekwynagrodzenList(List<Pasekwynagrodzen> pasekwynagrodzenList) {
        this.pasekwynagrodzenList = pasekwynagrodzenList;
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
        if (!(object instanceof Definicjalistaplac)) {
            return false;
        }
        Definicjalistaplac other = (Definicjalistaplac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Definicjalistaplac[ id=" + id + " ]";
    }
    
}
