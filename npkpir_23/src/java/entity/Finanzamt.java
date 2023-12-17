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

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "finanzamt")
@NamedQueries({
    @NamedQuery(name = "Finanzamt.findAll", query = "SELECT f FROM Finanzamt f"),
    @NamedQuery(name = "Finanzamt.findById", query = "SELECT f FROM Finanzamt f WHERE f.id = :id"),
    @NamedQuery(name = "Finanzamt.findByNazwa", query = "SELECT f FROM Finanzamt f WHERE f.nazwa = :nazwa")})
public class Finanzamt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "literaod")
    private String literaod;
    @Column(name = "literado")
    private String literado;

    public Finanzamt() {
    }

    public Finanzamt(Integer id) {
        this.id = id;
    }

    public Finanzamt(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
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

    public String getLiteraod() {
        return literaod;
    }

    public void setLiteraod(String literaod) {
        this.literaod = literaod;
    }

    public String getLiterado() {
        return literado;
    }

    public void setLiterado(String literado) {
        this.literado = literado;
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
        if (!(object instanceof Finanzamt)) {
            return false;
        }
        Finanzamt other = (Finanzamt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Finanzamt{" + "nazwa=" + nazwa + '}';
    }

  
}
