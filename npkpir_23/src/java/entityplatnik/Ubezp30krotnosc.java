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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "UBEZP_30KROTNOSC")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ubezp30krotnosc.findAll", query = "SELECT u FROM Ubezp30krotnosc u"),
    @NamedQuery(name = "Ubezp30krotnosc.findById", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.id = :id"),
    @NamedQuery(name = "Ubezp30krotnosc.findByIdUbezpieczony", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Ubezp30krotnosc.findByIdPlZus", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "Ubezp30krotnosc.findByIdUbZus", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.idUbZus = :idUbZus"),
    @NamedQuery(name = "Ubezp30krotnosc.findByRok", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.rok = :rok"),
    @NamedQuery(name = "Ubezp30krotnosc.findByMiesiacPrzekroczenia", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.miesiacPrzekroczenia = :miesiacPrzekroczenia"),
    @NamedQuery(name = "Ubezp30krotnosc.findByInserttmp", query = "SELECT u FROM Ubezp30krotnosc u WHERE u.inserttmp = :inserttmp")})
public class Ubezp30krotnosc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;
    @Column(name = "ROK")
    private Short rok;
    @Column(name = "MIESIAC_PRZEKROCZENIA")
    private Short miesiacPrzekroczenia;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Ubezp30krotnosc() {
    }

    public Ubezp30krotnosc(Integer id) {
        this.id = id;
    }

    public Ubezp30krotnosc(Integer id, int idUbezpieczony) {
        this.id = id;
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
    }

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
    }

    public Short getRok() {
        return rok;
    }

    public void setRok(Short rok) {
        this.rok = rok;
    }

    public Short getMiesiacPrzekroczenia() {
        return miesiacPrzekroczenia;
    }

    public void setMiesiacPrzekroczenia(Short miesiacPrzekroczenia) {
        this.miesiacPrzekroczenia = miesiacPrzekroczenia;
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
        if (!(object instanceof Ubezp30krotnosc)) {
            return false;
        }
        Ubezp30krotnosc other = (Ubezp30krotnosc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Ubezp30krotnosc[ id=" + id + " ]";
    }
    
}
