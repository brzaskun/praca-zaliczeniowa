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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "ARCH_DZIENNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchDziennik.findAll", query = "SELECT a FROM ArchDziennik a"),
    @NamedQuery(name = "ArchDziennik.findById", query = "SELECT a FROM ArchDziennik a WHERE a.id = :id"),
    @NamedQuery(name = "ArchDziennik.findByIdPlatnik", query = "SELECT a FROM ArchDziennik a WHERE a.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ArchDziennik.findByIdUzytkownik", query = "SELECT a FROM ArchDziennik a WHERE a.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "ArchDziennik.findByCzas", query = "SELECT a FROM ArchDziennik a WHERE a.czas = :czas"),
    @NamedQuery(name = "ArchDziennik.findByIdZdarzenia", query = "SELECT a FROM ArchDziennik a WHERE a.idZdarzenia = :idZdarzenia"),
    @NamedQuery(name = "ArchDziennik.findByIdPomocniczy", query = "SELECT a FROM ArchDziennik a WHERE a.idPomocniczy = :idPomocniczy"),
    @NamedQuery(name = "ArchDziennik.findByOpis", query = "SELECT a FROM ArchDziennik a WHERE a.opis = :opis"),
    @NamedQuery(name = "ArchDziennik.findByInserttmp", query = "SELECT a FROM ArchDziennik a WHERE a.inserttmp = :inserttmp")})
public class ArchDziennik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "ID_PLATNIK")
    private Integer idPlatnik;
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CZAS", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date czas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ZDARZENIA", nullable = false)
    private int idZdarzenia;
    @Column(name = "ID_POMOCNICZY")
    private Integer idPomocniczy;
    @Size(max = 255)
    @Column(name = "OPIS", length = 255)
    private String opis;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public ArchDziennik() {
    }

    public ArchDziennik(Integer id) {
        this.id = id;
    }

    public ArchDziennik(Integer id, Date czas, int idZdarzenia) {
        this.id = id;
        this.czas = czas;
        this.idZdarzenia = idZdarzenia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(Integer idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public Date getCzas() {
        return czas;
    }

    public void setCzas(Date czas) {
        this.czas = czas;
    }

    public int getIdZdarzenia() {
        return idZdarzenia;
    }

    public void setIdZdarzenia(int idZdarzenia) {
        this.idZdarzenia = idZdarzenia;
    }

    public Integer getIdPomocniczy() {
        return idPomocniczy;
    }

    public void setIdPomocniczy(Integer idPomocniczy) {
        this.idPomocniczy = idPomocniczy;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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
        if (!(object instanceof ArchDziennik)) {
            return false;
        }
        ArchDziennik other = (ArchDziennik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ArchDziennik[ id=" + id + " ]";
    }
    
}
