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
@Table(name = "NIEJED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Niejed.findAll", query = "SELECT n FROM Niejed n"),
    @NamedQuery(name = "Niejed.findById", query = "SELECT n FROM Niejed n WHERE n.id = :id"),
    @NamedQuery(name = "Niejed.findByIdPlatnik", query = "SELECT n FROM Niejed n WHERE n.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Niejed.findByIdDok1", query = "SELECT n FROM Niejed n WHERE n.idDok1 = :idDok1"),
    @NamedQuery(name = "Niejed.findByTyp1", query = "SELECT n FROM Niejed n WHERE n.typ1 = :typ1"),
    @NamedQuery(name = "Niejed.findByIdDok2", query = "SELECT n FROM Niejed n WHERE n.idDok2 = :idDok2"),
    @NamedQuery(name = "Niejed.findByTyp2", query = "SELECT n FROM Niejed n WHERE n.typ2 = :typ2"),
    @NamedQuery(name = "Niejed.findByIdDok3", query = "SELECT n FROM Niejed n WHERE n.idDok3 = :idDok3"),
    @NamedQuery(name = "Niejed.findByStatus", query = "SELECT n FROM Niejed n WHERE n.status = :status"),
    @NamedQuery(name = "Niejed.findByTyp", query = "SELECT n FROM Niejed n WHERE n.typ = :typ"),
    @NamedQuery(name = "Niejed.findByIdProgram", query = "SELECT n FROM Niejed n WHERE n.idProgram = :idProgram")})
public class Niejed implements Serializable {

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
    @Column(name = "ID_DOK1")
    private Integer idDok1;
    @Column(name = "TYP1")
    private Integer typ1;
    @Column(name = "ID_DOK2")
    private Integer idDok2;
    @Column(name = "TYP2")
    private Integer typ2;
    @Column(name = "ID_DOK3")
    private Integer idDok3;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "TYP")
    private Integer typ;
    @Column(name = "ID_PROGRAM")
    private Integer idProgram;

    public Niejed() {
    }

    public Niejed(Integer id) {
        this.id = id;
    }

    public Niejed(Integer id, int idPlatnik) {
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

    public Integer getIdDok1() {
        return idDok1;
    }

    public void setIdDok1(Integer idDok1) {
        this.idDok1 = idDok1;
    }

    public Integer getTyp1() {
        return typ1;
    }

    public void setTyp1(Integer typ1) {
        this.typ1 = typ1;
    }

    public Integer getIdDok2() {
        return idDok2;
    }

    public void setIdDok2(Integer idDok2) {
        this.idDok2 = idDok2;
    }

    public Integer getTyp2() {
        return typ2;
    }

    public void setTyp2(Integer typ2) {
        this.typ2 = typ2;
    }

    public Integer getIdDok3() {
        return idDok3;
    }

    public void setIdDok3(Integer idDok3) {
        this.idDok3 = idDok3;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Integer getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(Integer idProgram) {
        this.idProgram = idProgram;
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
        if (!(object instanceof Niejed)) {
            return false;
        }
        Niejed other = (Niejed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Niejed[ id=" + id + " ]";
    }
    
}
