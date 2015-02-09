/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Podatnik;
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
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnikObj", "opismiejsca"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MiejsceKosztow.findAll", query = "SELECT m FROM MiejsceKosztow m"),
    @NamedQuery(name = "MiejsceKosztow.findById", query = "SELECT m FROM MiejsceKosztow m WHERE m.id = :id"),
    @NamedQuery(name = "MiejsceKosztow.findByAktywny", query = "SELECT m FROM MiejsceKosztow m WHERE m.aktywny = :aktywny"),
    @NamedQuery(name = "MiejsceKosztow.findByOpismiejsca", query = "SELECT m FROM MiejsceKosztow m WHERE m.opismiejsca = :opismiejsca"),
    @NamedQuery(name = "MiejsceKosztow.findByPodatnik", query = "SELECT m FROM MiejsceKosztow m WHERE m.podatnikObj = :podatnik ORDER BY m.opismiejsca"),
    @NamedQuery(name = "MiejsceKosztow.countByPodatnik", query = "SELECT COUNT(d) FROM MiejsceKosztow d WHERE d.podatnikObj = :podatnik")
})
public class MiejsceKosztow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny", nullable = false)
    private boolean aktywny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opismiejsca", nullable = false, length = 255)
    private String opismiejsca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opisskrocony", nullable = false, length = 255)
    private String opisskrocony;
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta", nullable = false, length = 5)
    private String nrkonta;

    public MiejsceKosztow() {
    }

    public MiejsceKosztow(Integer id) {
        this.id = id;
    }

    public MiejsceKosztow(Integer id, boolean aktywny, String opismiejsca) {
        this.id = id;
        this.aktywny = aktywny;
        this.opismiejsca = opismiejsca;
    }
    
    public void uzupelnij(Podatnik podatnik, String numer) {
        this.podatnikObj = podatnik;
        this.nrkonta = numer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public String getOpismiejsca() {
        return opismiejsca;
    }

    public void setOpismiejsca(String opismiejsca) {
        this.opismiejsca = opismiejsca;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
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
        if (!(object instanceof MiejsceKosztow)) {
            return false;
        }
        MiejsceKosztow other = (MiejsceKosztow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return opismiejsca;
    }

  
    
}
