/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "amodok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amodok.findAll", query = "SELECT a FROM Amodok a"),
    @NamedQuery(name = "Amodok.findById", query = "SELECT a FROM Amodok a WHERE a.id = :id"),
    @NamedQuery(name = "Amodok.findByMc", query = "SELECT a FROM Amodok a WHERE a.mc = :mc"),
    @NamedQuery(name = "Amodok.findByRok", query = "SELECT a FROM Amodok a WHERE a.rok = :rok"),
    @NamedQuery(name = "Amodok.findByPodatnik", query = "SELECT a FROM Amodok a WHERE a.podatnik = :podatnik"),
    @NamedQuery(name = "Amodok.findByZaksiegowane", query = "SELECT a FROM Amodok a WHERE a.zaksiegowane = :zaksiegowane"),
    })
public class Amodok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mc")
    private Integer mc;
    @Column(name = "rok")
    private Integer rok;
    @Lob
    @Column(name = "umorzenia")
    private List<Umorzenie> umorzenia;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
    @Column(name = "podatnik")
    private String podatnik;

    public Amodok() {
        umorzenia = new ArrayList<Umorzenie>();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMc() {
        return mc;
    }

    public void setMc(Integer mc) {
        this.mc = mc;
    }

    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public List<Umorzenie> getUmorzenia() {
        return umorzenia;
    }

    public void setUmorzenia(List<Umorzenie> umorzenia) {
        this.umorzenia = umorzenia;
    }

  

    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
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
        if (!(object instanceof Amodok)) {
            return false;
        }
        Amodok other = (Amodok) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Amodok[ id=" + id + " ]";
    }
    
}
