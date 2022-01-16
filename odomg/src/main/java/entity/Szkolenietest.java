/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "szkolenietest", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"szkolenie", "test"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szkolenietest.findAll", query = "SELECT s FROM Szkolenietest s"),
    @NamedQuery(name = "Szkolenietest.findById", query = "SELECT s FROM Szkolenietest s WHERE s.id = :id"),
    @NamedQuery(name = "Szkolenietest.findBySzkolenie", query = "SELECT s FROM Szkolenietest s WHERE s.szkolenie = :szkolenie"),
    @NamedQuery(name = "Szkolenietest.findByTest", query = "SELECT s FROM Szkolenietest s WHERE s.test = :test"),
    @NamedQuery(name = "Szkolenietest.findByUwagi", query = "SELECT s FROM Szkolenietest s WHERE s.uwagi = :uwagi")})
public class Szkolenietest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "szkolenie", nullable = false, length = 128)
    private String szkolenie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "test", nullable = false, length = 128)
    private String test;
    @Size(max = 256)
    @Column(name = "uwagi", length = 256)
    private String uwagi;

    public Szkolenietest() {
    }

    public Szkolenietest(Integer id) {
        this.id = id;
    }

    public Szkolenietest(Integer id, String szkolenie, String test) {
        this.id = id;
        this.szkolenie = szkolenie;
        this.test = test;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSzkolenie() {
        return szkolenie;
    }

    public void setSzkolenie(String szkolenie) {
        this.szkolenie = szkolenie;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
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
        if (!(object instanceof Szkolenietest)) {
            return false;
        }
        Szkolenietest other = (Szkolenietest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Szkolenietest[ id=" + id + " ]";
    }
    
}
