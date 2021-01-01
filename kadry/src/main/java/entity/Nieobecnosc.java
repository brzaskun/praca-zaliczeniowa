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
@Table(name = "nieobecnosc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nieobecnosc.findAll", query = "SELECT n FROM Nieobecnosc n"),
    @NamedQuery(name = "Nieobecnosc.findById", query = "SELECT n FROM Nieobecnosc n WHERE n.id = :id"),
    @NamedQuery(name = "Nieobecnosc.findByKod", query = "SELECT n FROM Nieobecnosc n WHERE n.kod = :kod"),
    @NamedQuery(name = "Nieobecnosc.findByNazwa", query = "SELECT n FROM Nieobecnosc n WHERE n.nazwa = :nazwa"),
    @NamedQuery(name = "Nieobecnosc.findByDataod", query = "SELECT n FROM Nieobecnosc n WHERE n.dataod = :dataod"),
    @NamedQuery(name = "Nieobecnosc.findByDatado", query = "SELECT n FROM Nieobecnosc n WHERE n.datado = :datado")})
public class Nieobecnosc implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 45)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 45)
    @Column(name = "datado")
    private String datado;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowa umowa;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "nieobecnosc")
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;

   
    public Nieobecnosc() {
    }

    public Nieobecnosc(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @XmlTransient
    public List<Naliczenienieobecnosc> getNaliczenienieobecnoscList() {
        return naliczenienieobecnoscList;
    }

    public void setNaliczenienieobecnoscList(List<Naliczenienieobecnosc> naliczenienieobecnoscList) {
        this.naliczenienieobecnoscList = naliczenienieobecnoscList;
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
        if (!(object instanceof Nieobecnosc)) {
            return false;
        }
        Nieobecnosc other = (Nieobecnosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Nieobecnosc[ id=" + id + " ]";
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
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

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }
    
}
