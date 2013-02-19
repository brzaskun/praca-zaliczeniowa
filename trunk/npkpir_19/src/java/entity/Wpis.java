/*
 * To change this template, choose Tools | Templates
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
@Table(name = "wpis")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wpis.findAll", query = "SELECT w FROM Wpis w"),
    @NamedQuery(name = "Wpis.findById", query = "SELECT w FROM Wpis w WHERE w.id = :id"),
    @NamedQuery(name = "Wpis.findByPodatnikWpisu", query = "SELECT w FROM Wpis w WHERE w.podatnikWpisu = :podatnikWpisu"),
    @NamedQuery(name = "Wpis.findByRokWpisu", query = "SELECT w FROM Wpis w WHERE w.rokWpisu = :rokWpisu"),
    @NamedQuery(name = "Wpis.findByMiesiacWpisu", query = "SELECT w FROM Wpis w WHERE w.miesiacWpisu = :miesiacWpisu"),
    @NamedQuery(name = "Wpis.findByWprowadzil", query = "SELECT w FROM Wpis w WHERE w.wprowadzil = :wprowadzil"),
    @NamedQuery(name = "Wpis.findByMiesiacOd", query = "SELECT w FROM Wpis w WHERE w.miesiacOd = :miesiacOd"),
    @NamedQuery(name = "Wpis.findByMiesiacDo", query = "SELECT w FROM Wpis w WHERE w.miesiacDo = :miesiacDo"),
    @NamedQuery(name = "Wpis.findBySrodkTrw", query = "SELECT w FROM Wpis w WHERE w.srodkTrw = :srodkTrw")})
public class Wpis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "podatnikWpisu")
    private String podatnikWpisu;
    @Column(name = "rokWpisu")
    private Integer rokWpisu;
    @Size(max = 2)
    @Column(name = "miesiacWpisu")
    private String miesiacWpisu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "wprowadzil")
    private String wprowadzil;
    @Size(max = 2)
    @Column(name = "miesiacOd")
    private String miesiacOd;
    @Size(max = 2)
    @Column(name = "miesiacDo")
    private String miesiacDo;
    @Column(name = "srodkTrw")
    private Boolean srodkTrw;
    @Size(max = 255)
    @Column(name = "biezacasesja")
    private String biezacasesja;

    public Wpis() {
    }

    public Wpis(Integer id) {
        this.id = id;
    }

    public Wpis(Integer id, String wprowadzil) {
        this.id = id;
        this.wprowadzil = wprowadzil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public void setPodatnikWpisu(String podatnikWpisu) {
        this.podatnikWpisu = podatnikWpisu;
    }

    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        this.rokWpisu = rokWpisu;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public String getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        this.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        this.miesiacDo = miesiacDo;
    }

    public Boolean getSrodkTrw() {
        return srodkTrw;
    }

    public void setSrodkTrw(Boolean srodkTrw) {
        this.srodkTrw = srodkTrw;
    }

    public String getBiezacasesja() {
        return biezacasesja;
    }

    public void setBiezacasesja(String biezacasesja) {
        this.biezacasesja = biezacasesja;
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
        if (!(object instanceof Wpis)) {
            return false;
        }
        Wpis other = (Wpis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wpis[ id=" + id + " ]";
    }
    
}
