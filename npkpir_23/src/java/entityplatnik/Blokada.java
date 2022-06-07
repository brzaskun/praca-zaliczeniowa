/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "BLOKADA", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ID_OBIEKT", "ID_PLATNIK", "ID_UBEZPIECZONY", "POZIOM", "TYP", "ID_BLOKADA", "KONTEKST"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blokada.findAll", query = "SELECT b FROM Blokada b"),
    @NamedQuery(name = "Blokada.findById", query = "SELECT b FROM Blokada b WHERE b.id = :id"),
    @NamedQuery(name = "Blokada.findByIdProgram", query = "SELECT b FROM Blokada b WHERE b.idProgram = :idProgram"),
    @NamedQuery(name = "Blokada.findByIdBlokada", query = "SELECT b FROM Blokada b WHERE b.idBlokada = :idBlokada"),
    @NamedQuery(name = "Blokada.findByIdUzytkownik", query = "SELECT b FROM Blokada b WHERE b.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Blokada.findByKomputer", query = "SELECT b FROM Blokada b WHERE b.komputer = :komputer"),
    @NamedQuery(name = "Blokada.findByTyp", query = "SELECT b FROM Blokada b WHERE b.typ = :typ"),
    @NamedQuery(name = "Blokada.findByPoziom", query = "SELECT b FROM Blokada b WHERE b.poziom = :poziom"),
    @NamedQuery(name = "Blokada.findByIdObiekt", query = "SELECT b FROM Blokada b WHERE b.idObiekt = :idObiekt"),
    @NamedQuery(name = "Blokada.findByIdPlatnik", query = "SELECT b FROM Blokada b WHERE b.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Blokada.findByIdUbezpieczony", query = "SELECT b FROM Blokada b WHERE b.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "Blokada.findByIdZestaw", query = "SELECT b FROM Blokada b WHERE b.idZestaw = :idZestaw"),
    @NamedQuery(name = "Blokada.findByCzas", query = "SELECT b FROM Blokada b WHERE b.czas = :czas"),
    @NamedQuery(name = "Blokada.findByKontekst", query = "SELECT b FROM Blokada b WHERE b.kontekst = :kontekst")})
public class Blokada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PROGRAM", nullable = false)
    private int idProgram;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BLOKADA", nullable = false)
    private int idBlokada;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UZYTKOWNIK", nullable = false)
    private int idUzytkownik;
    @Size(max = 64)
    @Column(name = "KOMPUTER", length = 64)
    private String komputer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYP", nullable = false)
    private int typ;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POZIOM", nullable = false)
    private int poziom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OBIEKT", nullable = false)
    private int idObiekt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
    @Column(name = "ID_ZESTAW")
    private Integer idZestaw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CZAS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date czas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KONTEKST", nullable = false)
    private int kontekst;

    public Blokada() {
    }

    public Blokada(Integer id) {
        this.id = id;
    }

    public Blokada(Integer id, int idProgram, int idBlokada, int idUzytkownik, int typ, int poziom, int idObiekt, int idPlatnik, int idUbezpieczony, Date czas, int kontekst) {
        this.id = id;
        this.idProgram = idProgram;
        this.idBlokada = idBlokada;
        this.idUzytkownik = idUzytkownik;
        this.typ = typ;
        this.poziom = poziom;
        this.idObiekt = idObiekt;
        this.idPlatnik = idPlatnik;
        this.idUbezpieczony = idUbezpieczony;
        this.czas = czas;
        this.kontekst = kontekst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdProgram() {
        return idProgram;
    }

    public void setIdProgram(int idProgram) {
        this.idProgram = idProgram;
    }

    public int getIdBlokada() {
        return idBlokada;
    }

    public void setIdBlokada(int idBlokada) {
        this.idBlokada = idBlokada;
    }

    public int getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(int idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public String getKomputer() {
        return komputer;
    }

    public void setKomputer(String komputer) {
        this.komputer = komputer;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    public int getPoziom() {
        return poziom;
    }

    public void setPoziom(int poziom) {
        this.poziom = poziom;
    }

    public int getIdObiekt() {
        return idObiekt;
    }

    public void setIdObiekt(int idObiekt) {
        this.idObiekt = idObiekt;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getIdZestaw() {
        return idZestaw;
    }

    public void setIdZestaw(Integer idZestaw) {
        this.idZestaw = idZestaw;
    }

    public Date getCzas() {
        return czas;
    }

    public void setCzas(Date czas) {
        this.czas = czas;
    }

    public int getKontekst() {
        return kontekst;
    }

    public void setKontekst(int kontekst) {
        this.kontekst = kontekst;
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
        if (!(object instanceof Blokada)) {
            return false;
        }
        Blokada other = (Blokada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Blokada[ id=" + id + " ]";
    }
    
}
