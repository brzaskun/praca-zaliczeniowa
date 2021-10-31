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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "etatprac", uniqueConstraints = {
    @UniqueConstraint(columnNames={"umowa","dataod"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EtatPrac.findAll", query = "SELECT e FROM EtatPrac e"),
    @NamedQuery(name = "EtatPrac.findById", query = "SELECT e FROM EtatPrac e WHERE e.id = :id"),
    @NamedQuery(name = "EtatPrac.findByUmowa", query = "SELECT e FROM EtatPrac e WHERE e.umowa = :umowa"),
    @NamedQuery(name = "EtatPrac.findByDataod", query = "SELECT e FROM EtatPrac e WHERE e.dataod = :dataod"),
    @NamedQuery(name = "EtatPrac.findByDatado", query = "SELECT e FROM EtatPrac e WHERE e.datado = :datado"),
    @NamedQuery(name = "EtatPrac.findByEtatPrac1", query = "SELECT e FROM EtatPrac e WHERE e.etat1 = :etat1"),
    @NamedQuery(name = "EtatPrac.findByEtatPrac2", query = "SELECT e FROM EtatPrac e WHERE e.etat2 = :etat2")})
public class EtatPrac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 10)
    @Column(name = "datado")
    private String datado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etat1")
    private int etat1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "etat2")
    private int etat2;
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Umowa umowa;

    public EtatPrac() {
    }

    public EtatPrac(Integer id) {
        this.id = id;
    }

    public EtatPrac(Integer id, String dataod, int etat1, int etat2) {
        this.id = id;
        this.dataod = dataod;
        this.etat1 = etat1;
        this.etat2 = etat2;
    }

    public EtatPrac(Umowa selected, Integer etat1, Integer etat2) {
        this.dataod = selected.getDataod();
        this.datado = selected.getDatado();
        this.etat1 = etat1==null?1:etat1;
        this.etat2 = etat2==null?1:etat2;
        this.umowa = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public int getEtat1() {
        return etat1;
    }

    public void setEtat1(int etat1) {
        this.etat1 = etat1;
    }

    public int getEtat2() {
        return etat2;
    }

    public void setEtat2(int etat2) {
        this.etat2 = etat2;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
    }

    public String getEtat() {
        return this.etat1+"/"+this.etat2;
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
        if (!(object instanceof EtatPrac)) {
            return false;
        }
        EtatPrac other = (EtatPrac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EtatPrac[ id=" + id + " ]";
    }
    
}
