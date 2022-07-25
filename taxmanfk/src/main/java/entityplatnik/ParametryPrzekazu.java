/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "PARAMETRY_PRZEKAZU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametryPrzekazu.findAll", query = "SELECT p FROM ParametryPrzekazu p"),
    @NamedQuery(name = "ParametryPrzekazu.findById", query = "SELECT p FROM ParametryPrzekazu p WHERE p.id = :id"),
    @NamedQuery(name = "ParametryPrzekazu.findByIdPlatnik", query = "SELECT p FROM ParametryPrzekazu p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ParametryPrzekazu.findByNazwa", query = "SELECT p FROM ParametryPrzekazu p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "ParametryPrzekazu.findByWartosc", query = "SELECT p FROM ParametryPrzekazu p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "ParametryPrzekazu.findByInserttmp", query = "SELECT p FROM ParametryPrzekazu p WHERE p.inserttmp = :inserttmp")})
public class ParametryPrzekazu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Size(max = 255)
    @Column(name = "NAZWA", length = 255)
    private String nazwa;
    @Size(max = 255)
    @Column(name = "WARTOSC", length = 255)
    private String wartosc;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public ParametryPrzekazu() {
    }

    public ParametryPrzekazu(Integer id) {
        this.id = id;
    }

    public ParametryPrzekazu(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getWartosc() {
        return wartosc;
    }

    public void setWartosc(String wartosc) {
        this.wartosc = wartosc;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof ParametryPrzekazu)) {
            return false;
        }
        ParametryPrzekazu other = (ParametryPrzekazu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ParametryPrzekazu[ id=" + id + " ]";
    }
    
}
