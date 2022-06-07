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
@Table(name = "DZIENNIK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dziennik.findAll", query = "SELECT d FROM Dziennik d"),
    @NamedQuery(name = "Dziennik.findById", query = "SELECT d FROM Dziennik d WHERE d.id = :id"),
    @NamedQuery(name = "Dziennik.findByIdPlatnik", query = "SELECT d FROM Dziennik d WHERE d.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Dziennik.findByIdUzytkownik", query = "SELECT d FROM Dziennik d WHERE d.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Dziennik.findByCzas", query = "SELECT d FROM Dziennik d WHERE d.czas = :czas"),
    @NamedQuery(name = "Dziennik.findByIdZdarzenia", query = "SELECT d FROM Dziennik d WHERE d.idZdarzenia = :idZdarzenia"),
    @NamedQuery(name = "Dziennik.findByIdPomocniczy", query = "SELECT d FROM Dziennik d WHERE d.idPomocniczy = :idPomocniczy"),
    @NamedQuery(name = "Dziennik.findByOpis", query = "SELECT d FROM Dziennik d WHERE d.opis = :opis"),
    @NamedQuery(name = "Dziennik.findByInserttmp", query = "SELECT d FROM Dziennik d WHERE d.inserttmp = :inserttmp")})
public class Dziennik implements Serializable {

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

    public Dziennik() {
    }

    public Dziennik(Integer id) {
        this.id = id;
    }

    public Dziennik(Integer id, Date czas, int idZdarzenia) {
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
        if (!(object instanceof Dziennik)) {
            return false;
        }
        Dziennik other = (Dziennik) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Dziennik[ id=" + id + " ]";
    }
    
}
