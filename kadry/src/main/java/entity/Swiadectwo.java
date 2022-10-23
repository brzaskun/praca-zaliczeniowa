/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "swiadectwo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Swiadectwo.findAll", query = "SELECT s FROM Swiadectwo s"),
    @NamedQuery(name = "Swiadectwo.findById", query = "SELECT s FROM Swiadectwo s WHERE s.id = :id"),
    @NamedQuery(name = "Swiadectwo.findByDatawystawienia", query = "SELECT s FROM Swiadectwo s WHERE s.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Swiadectwo.findByRozwiazanieumowy", query = "SELECT s FROM Swiadectwo s WHERE s.rozwiazanieumowy = :rozwiazanieumowy")})
public class Swiadectwo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "datawystawienia", length = 255)
    private String datawystawienia;
    @NotNull
    @JoinColumn(name = "rozwiazanieumowy", referencedColumnName = "id")
    private Rozwiazanieumowy rozwiazanieumowy;
    @Column(name="infouzupelniajace")
    private String infouzupelniajace;

    public Swiadectwo() {
    }

    public Swiadectwo(Integer id) {
        this.id = id;
    }

    public Swiadectwo(Integer id, Rozwiazanieumowy rozwiazanieumowy) {
        this.id = id;
        this.rozwiazanieumowy = rozwiazanieumowy;
    }

    public Swiadectwo(Rozwiazanieumowy rozwiazanieumowy) {
        this.rozwiazanieumowy = rozwiazanieumowy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public Rozwiazanieumowy getRozwiazanieumowy() {
        return rozwiazanieumowy;
    }

    public void setRozwiazanieumowy(Rozwiazanieumowy rozwiazanieumowy) {
        this.rozwiazanieumowy = rozwiazanieumowy;
    }

    public String getInfouzupelniajace() {
        return infouzupelniajace;
    }

    public void setInfouzupelniajace(String infouzupelniajace) {
        this.infouzupelniajace = infouzupelniajace;
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
        if (!(object instanceof Swiadectwo)) {
            return false;
        }
        Swiadectwo other = (Swiadectwo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Swiadectwo[ id=" + id + " ]";
    }
    
}
