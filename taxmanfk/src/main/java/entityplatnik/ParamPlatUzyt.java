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
@Table(name = "PARAM_PLAT_UZYT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamPlatUzyt.findAll", query = "SELECT p FROM ParamPlatUzyt p"),
    @NamedQuery(name = "ParamPlatUzyt.findById", query = "SELECT p FROM ParamPlatUzyt p WHERE p.id = :id"),
    @NamedQuery(name = "ParamPlatUzyt.findByIdPlatnik", query = "SELECT p FROM ParamPlatUzyt p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "ParamPlatUzyt.findByIdUzytkownik", query = "SELECT p FROM ParamPlatUzyt p WHERE p.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "ParamPlatUzyt.findByNazwa", query = "SELECT p FROM ParamPlatUzyt p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "ParamPlatUzyt.findByWartosc", query = "SELECT p FROM ParamPlatUzyt p WHERE p.wartosc = :wartosc"),
    @NamedQuery(name = "ParamPlatUzyt.findByInserttmp", query = "SELECT p FROM ParamPlatUzyt p WHERE p.inserttmp = :inserttmp")})
public class ParamPlatUzyt implements Serializable {

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
    @Size(max = 55)
    @Column(name = "NAZWA", length = 55)
    private String nazwa;
    @Size(max = 10)
    @Column(name = "WARTOSC", length = 10)
    private String wartosc;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public ParamPlatUzyt() {
    }

    public ParamPlatUzyt(Integer id) {
        this.id = id;
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
        if (!(object instanceof ParamPlatUzyt)) {
            return false;
        }
        ParamPlatUzyt other = (ParamPlatUzyt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ParamPlatUzyt[ id=" + id + " ]";
    }
    
}
