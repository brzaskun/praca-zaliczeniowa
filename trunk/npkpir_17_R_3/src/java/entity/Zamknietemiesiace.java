/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Okresrozliczeniowy;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zamknietemiesiace")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zamknietemiesiace.findAll", query = "SELECT z FROM Zamknietemiesiace z"),
    @NamedQuery(name = "Zamknietemiesiace.findById", query = "SELECT z FROM Zamknietemiesiace z WHERE z.id = :id"),
    @NamedQuery(name = "Zamknietemiesiace.findByPodatnik", query = "SELECT z FROM Zamknietemiesiace z WHERE z.podatnik = :podatnik")})
public class Zamknietemiesiace implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "zamkniete")
    private ArrayList<Okresrozliczeniowy> okres;
    

    public Zamknietemiesiace() {
    }

    public Zamknietemiesiace(Integer id) {
        this.id = id;
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

   

    public ArrayList<Okresrozliczeniowy> getOkres() {
        return okres;
    }

    public void setOkres(ArrayList<Okresrozliczeniowy> okres) {
        this.okres = okres;
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
        if (!(object instanceof Zamknietemiesiace)) {
            return false;
        }
        Zamknietemiesiace other = (Zamknietemiesiace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zamknietemiesiace[ id=" + id + " ]";
    }
    
}
