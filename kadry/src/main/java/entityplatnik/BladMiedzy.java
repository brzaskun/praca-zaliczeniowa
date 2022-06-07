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
@Table(name = "BLAD_MIEDZY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BladMiedzy.findAll", query = "SELECT b FROM BladMiedzy b"),
    @NamedQuery(name = "BladMiedzy.findById", query = "SELECT b FROM BladMiedzy b WHERE b.id = :id"),
    @NamedQuery(name = "BladMiedzy.findByIdPlatnik", query = "SELECT b FROM BladMiedzy b WHERE b.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "BladMiedzy.findByIdZestaw", query = "SELECT b FROM BladMiedzy b WHERE b.idZestaw = :idZestaw"),
    @NamedQuery(name = "BladMiedzy.findByTyp", query = "SELECT b FROM BladMiedzy b WHERE b.typ = :typ"),
    @NamedQuery(name = "BladMiedzy.findByNumer", query = "SELECT b FROM BladMiedzy b WHERE b.numer = :numer"),
    @NamedQuery(name = "BladMiedzy.findByKlasa", query = "SELECT b FROM BladMiedzy b WHERE b.klasa = :klasa"),
    @NamedQuery(name = "BladMiedzy.findByInsertTmp", query = "SELECT b FROM BladMiedzy b WHERE b.insertTmp = :insertTmp")})
public class BladMiedzy implements Serializable {

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
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ZESTAW", nullable = false)
    private int idZestaw;
    @Column(name = "TYP")
    private Character typ;
    @Column(name = "NUMER")
    private Integer numer;
    @Column(name = "KLASA")
    private Integer klasa;
    @Column(name = "INSERT_TMP")
    private Integer insertTmp;

    public BladMiedzy() {
    }

    public BladMiedzy(Integer id) {
        this.id = id;
    }

    public BladMiedzy(Integer id, int idPlatnik, int idZestaw) {
        this.id = id;
        this.idPlatnik = idPlatnik;
        this.idZestaw = idZestaw;
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

    public int getIdZestaw() {
        return idZestaw;
    }

    public void setIdZestaw(int idZestaw) {
        this.idZestaw = idZestaw;
    }

    public Character getTyp() {
        return typ;
    }

    public void setTyp(Character typ) {
        this.typ = typ;
    }

    public Integer getNumer() {
        return numer;
    }

    public void setNumer(Integer numer) {
        this.numer = numer;
    }

    public Integer getKlasa() {
        return klasa;
    }

    public void setKlasa(Integer klasa) {
        this.klasa = klasa;
    }

    public Integer getInsertTmp() {
        return insertTmp;
    }

    public void setInsertTmp(Integer insertTmp) {
        this.insertTmp = insertTmp;
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
        if (!(object instanceof BladMiedzy)) {
            return false;
        }
        BladMiedzy other = (BladMiedzy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.BladMiedzy[ id=" + id + " ]";
    }
    
}
