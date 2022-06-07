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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "LISTA_ODDZIAL", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NAZWAODDZIAL"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaOddzial.findAll", query = "SELECT l FROM ListaOddzial l"),
    @NamedQuery(name = "ListaOddzial.findById", query = "SELECT l FROM ListaOddzial l WHERE l.id = :id"),
    @NamedQuery(name = "ListaOddzial.findByNazwaoddzial", query = "SELECT l FROM ListaOddzial l WHERE l.nazwaoddzial = :nazwaoddzial"),
    @NamedQuery(name = "ListaOddzial.findByInserttmp", query = "SELECT l FROM ListaOddzial l WHERE l.inserttmp = :inserttmp")})
public class ListaOddzial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "NAZWAODDZIAL", nullable = false, length = 31)
    private String nazwaoddzial;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public ListaOddzial() {
    }

    public ListaOddzial(Integer id) {
        this.id = id;
    }

    public ListaOddzial(Integer id, String nazwaoddzial) {
        this.id = id;
        this.nazwaoddzial = nazwaoddzial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwaoddzial() {
        return nazwaoddzial;
    }

    public void setNazwaoddzial(String nazwaoddzial) {
        this.nazwaoddzial = nazwaoddzial;
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
        if (!(object instanceof ListaOddzial)) {
            return false;
        }
        ListaOddzial other = (ListaOddzial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.ListaOddzial[ id=" + id + " ]";
    }
    
}
