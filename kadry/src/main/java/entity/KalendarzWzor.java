/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kalendarzwzor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KalendarzWzor.findAll", query = "SELECT k FROM KalendarzWzor k"),
    @NamedQuery(name = "KalendarzWzor.findById", query = "SELECT k FROM KalendarzWzor k WHERE k.id = :id"),
    @NamedQuery(name = "KalendarzWzor.findByRok", query = "SELECT k FROM KalendarzWzor k WHERE k.rok = :rok"),
    @NamedQuery(name = "KalendarzWzor.findByMc", query = "SELECT k FROM KalendarzWzor k WHERE k.mc = :mc")
})
public class KalendarzWzor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @OneToMany(mappedBy = "kalendarzmiesiac")
    private List<Dzien> dzienList;

    public KalendarzWzor() {
    }

    public KalendarzWzor(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof KalendarzWzor)) {
            return false;
        }
        KalendarzWzor other = (KalendarzWzor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "entity.KalendarzWzor[ id=" + id + " ]";
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }
   
        
}
