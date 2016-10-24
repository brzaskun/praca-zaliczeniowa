/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "adminmail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminmail.findAll", query = "SELECT a FROM Adminmail a"),
    @NamedQuery(name = "Adminmail.findById", query = "SELECT a FROM Adminmail a WHERE a.id = :id"),
    @NamedQuery(name = "Adminmail.findByDatawysylki", query = "SELECT a FROM Adminmail a WHERE a.datawysylki = :datawysylki"),
    @NamedQuery(name = "Adminmail.findByTresc", query = "SELECT a FROM Adminmail a WHERE a.tresc = :tresc"),
    @NamedQuery(name = "Adminmail.findByTytul", query = "SELECT a FROM Adminmail a WHERE a.tytul = :tytul")})
public class Adminmail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false, name = "id")
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datawysylki")
    private Date datawysylki;
    @Lob
    @Column(name = "maile")
    private List<String> maile;
    @Lob
    @Column(name = "podatnicy")
    private List<String> podatnicy;
    @Lob
    @Size(max = 65535)
    @Column(length = 65535)
    private String tresc;
    @Size(max = 255)
    @Column(length = 255)
    private String tytul;

    public Adminmail() {
    }

    public Adminmail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(Date datawysylki) {
        this.datawysylki = datawysylki;
    }

    public List<String> getMaile() {
        return maile;
    }

    public void setMaile(List<String> maile) {
        this.maile = maile;
    }

    public List<String> getPodatnicy() {
        return podatnicy;
    }

    public void setPodatnicy(List<String> podatnicy) {
        this.podatnicy = podatnicy;
    }


    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
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
        if (!(object instanceof Adminmail)) {
            return false;
        }
        Adminmail other = (Adminmail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Adminmail[ id=" + id + " ]";
    }
    
}
