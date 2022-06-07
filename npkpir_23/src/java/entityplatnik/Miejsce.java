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
@Table(name = "MIEJSCE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Miejsce.findAll", query = "SELECT m FROM Miejsce m"),
    @NamedQuery(name = "Miejsce.findById", query = "SELECT m FROM Miejsce m WHERE m.id = :id"),
    @NamedQuery(name = "Miejsce.findByIdBlad", query = "SELECT m FROM Miejsce m WHERE m.idBlad = :idBlad"),
    @NamedQuery(name = "Miejsce.findByIdDokument", query = "SELECT m FROM Miejsce m WHERE m.idDokument = :idDokument"),
    @NamedQuery(name = "Miejsce.findByIdBlok", query = "SELECT m FROM Miejsce m WHERE m.idBlok = :idBlok"),
    @NamedQuery(name = "Miejsce.findByTyp", query = "SELECT m FROM Miejsce m WHERE m.typ = :typ"),
    @NamedQuery(name = "Miejsce.findByInserttmp", query = "SELECT m FROM Miejsce m WHERE m.inserttmp = :inserttmp")})
public class Miejsce implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_BLAD", nullable = false)
    private int idBlad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private int idDokument;
    @Column(name = "ID_BLOK")
    private Integer idBlok;
    @Column(name = "TYP")
    private Integer typ;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Miejsce() {
    }

    public Miejsce(Integer id) {
        this.id = id;
    }

    public Miejsce(Integer id, int idBlad, int idDokument) {
        this.id = id;
        this.idBlad = idBlad;
        this.idDokument = idDokument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdBlad() {
        return idBlad;
    }

    public void setIdBlad(int idBlad) {
        this.idBlad = idBlad;
    }

    public int getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(int idDokument) {
        this.idDokument = idDokument;
    }

    public Integer getIdBlok() {
        return idBlok;
    }

    public void setIdBlok(Integer idBlok) {
        this.idBlok = idBlok;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
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
        if (!(object instanceof Miejsce)) {
            return false;
        }
        Miejsce other = (Miejsce) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Miejsce[ id=" + id + " ]";
    }
    
}
