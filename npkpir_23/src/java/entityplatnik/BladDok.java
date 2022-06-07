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
@Table(name = "BLAD_DOK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BladDok.findAll", query = "SELECT b FROM BladDok b"),
    @NamedQuery(name = "BladDok.findById", query = "SELECT b FROM BladDok b WHERE b.id = :id"),
    @NamedQuery(name = "BladDok.findByIdPlatnik", query = "SELECT b FROM BladDok b WHERE b.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "BladDok.findByIdObiekt", query = "SELECT b FROM BladDok b WHERE b.idObiekt = :idObiekt"),
    @NamedQuery(name = "BladDok.findByTyp", query = "SELECT b FROM BladDok b WHERE b.typ = :typ"),
    @NamedQuery(name = "BladDok.findByIdBlokBlw", query = "SELECT b FROM BladDok b WHERE b.idBlokBlw = :idBlokBlw"),
    @NamedQuery(name = "BladDok.findByIdMiejsce", query = "SELECT b FROM BladDok b WHERE b.idMiejsce = :idMiejsce"),
    @NamedQuery(name = "BladDok.findByNumer", query = "SELECT b FROM BladDok b WHERE b.numer = :numer"),
    @NamedQuery(name = "BladDok.findByKlasa", query = "SELECT b FROM BladDok b WHERE b.klasa = :klasa"),
    @NamedQuery(name = "BladDok.findByBlok", query = "SELECT b FROM BladDok b WHERE b.blok = :blok"),
    @NamedQuery(name = "BladDok.findByPole", query = "SELECT b FROM BladDok b WHERE b.pole = :pole"),
    @NamedQuery(name = "BladDok.findBySegment", query = "SELECT b FROM BladDok b WHERE b.segment = :segment"),
    @NamedQuery(name = "BladDok.findByInserttmp", query = "SELECT b FROM BladDok b WHERE b.inserttmp = :inserttmp"),
    @NamedQuery(name = "BladDok.findByIdObiekt2", query = "SELECT b FROM BladDok b WHERE b.idObiekt2 = :idObiekt2")})
public class BladDok implements Serializable {

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
    @Column(name = "ID_OBIEKT", nullable = false)
    private int idObiekt;
    @Column(name = "TYP")
    private Integer typ;
    @Column(name = "ID_BLOK_BLW")
    private Integer idBlokBlw;
    @Column(name = "ID_MIEJSCE")
    private Integer idMiejsce;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMER", nullable = false)
    private int numer;
    @Column(name = "KLASA")
    private Integer klasa;
    @Column(name = "BLOK")
    private Integer blok;
    @Column(name = "POLE")
    private Integer pole;
    @Column(name = "SEGMENT")
    private Integer segment;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_OBIEKT2")
    private Integer idObiekt2;

    public BladDok() {
    }

    public BladDok(Integer id) {
        this.id = id;
    }

    public BladDok(Integer id, int idPlatnik, int idObiekt, int numer) {
        this.id = id;
        this.idPlatnik = idPlatnik;
        this.idObiekt = idObiekt;
        this.numer = numer;
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

    public int getIdObiekt() {
        return idObiekt;
    }

    public void setIdObiekt(int idObiekt) {
        this.idObiekt = idObiekt;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Integer getIdBlokBlw() {
        return idBlokBlw;
    }

    public void setIdBlokBlw(Integer idBlokBlw) {
        this.idBlokBlw = idBlokBlw;
    }

    public Integer getIdMiejsce() {
        return idMiejsce;
    }

    public void setIdMiejsce(Integer idMiejsce) {
        this.idMiejsce = idMiejsce;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public Integer getKlasa() {
        return klasa;
    }

    public void setKlasa(Integer klasa) {
        this.klasa = klasa;
    }

    public Integer getBlok() {
        return blok;
    }

    public void setBlok(Integer blok) {
        this.blok = blok;
    }

    public Integer getPole() {
        return pole;
    }

    public void setPole(Integer pole) {
        this.pole = pole;
    }

    public Integer getSegment() {
        return segment;
    }

    public void setSegment(Integer segment) {
        this.segment = segment;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdObiekt2() {
        return idObiekt2;
    }

    public void setIdObiekt2(Integer idObiekt2) {
        this.idObiekt2 = idObiekt2;
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
        if (!(object instanceof BladDok)) {
            return false;
        }
        BladDok other = (BladDok) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.BladDok[ id=" + id + " ]";
    }
    
}
