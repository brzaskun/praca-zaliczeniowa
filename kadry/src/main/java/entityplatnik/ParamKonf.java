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
import javax.persistence.Lob;
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
@Table(name = "PARAM_KONF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParamKonf.findAll", query = "SELECT p FROM ParamKonf p"),
    @NamedQuery(name = "ParamKonf.findById", query = "SELECT p FROM ParamKonf p WHERE p.id = :id"),
    @NamedQuery(name = "ParamKonf.findByNazwa", query = "SELECT p FROM ParamKonf p WHERE p.nazwa = :nazwa"),
    @NamedQuery(name = "ParamKonf.findByWartosc1", query = "SELECT p FROM ParamKonf p WHERE p.wartosc1 = :wartosc1"),
    @NamedQuery(name = "ParamKonf.findByWartosc3", query = "SELECT p FROM ParamKonf p WHERE p.wartosc3 = :wartosc3")})
public class ParamKonf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "NAZWA", length = 255)
    private String nazwa;
    @Size(max = 255)
    @Column(name = "WARTOSC1", length = 255)
    private String wartosc1;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "WARTOSC2", length = 2147483647)
    private String wartosc2;
    @Column(name = "WARTOSC3")
    private Integer wartosc3;

    public ParamKonf() {
    }

    public ParamKonf(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getWartosc1() {
        return wartosc1;
    }

    public void setWartosc1(String wartosc1) {
        this.wartosc1 = wartosc1;
    }

    public String getWartosc2() {
        return wartosc2;
    }

    public void setWartosc2(String wartosc2) {
        this.wartosc2 = wartosc2;
    }

    public Integer getWartosc3() {
        return wartosc3;
    }

    public void setWartosc3(Integer wartosc3) {
        this.wartosc3 = wartosc3;
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
        if (!(object instanceof ParamKonf)) {
            return false;
        }
        ParamKonf other = (ParamKonf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ParamKonf[ id=" + id + " ]";
    }
    
}
