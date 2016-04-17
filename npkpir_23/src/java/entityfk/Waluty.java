/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Waluty.findAll", query = "SELECT w FROM Waluty w"),
    @NamedQuery(name = "Waluty.findById", query = "SELECT w FROM Waluty w WHERE w.id = :id"),
    @NamedQuery(name = "Waluty.findByNazwawaluty", query = "SELECT w FROM Waluty w WHERE w.nazwawaluty = :nazwawaluty"),
    @NamedQuery(name = "Waluty.findBySymbolwaluty", query = "SELECT w FROM Waluty w WHERE w.symbolwaluty = :symbolwaluty")
})
public class Waluty implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idwaluty", nullable = false)
    private Integer id;
    @Column(name = "symbolwaluty")
    private String symbolwaluty;
    @Column(name = "nazwawaluty")
    private String nazwawaluty;
    @Column(name = "przelicznik")
    private int przelicznik;
    @Column(name = "skrotsymbolu")
    private String skrotsymbolu;
    @OneToMany(mappedBy = "waluta")
    private List<Tabelanbp> tabelanbp;
    @OneToMany(mappedBy = "walutadokumentu")
    private List<Dokfk> dokfk;
            

    public Waluty() {
        this.przelicznik = 1;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Waluty(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return id;
    }

    public int getPrzelicznik() {
        return przelicznik;
    }

    public void setPrzelicznik(int przelicznik) {
        this.przelicznik = przelicznik;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbolwaluty() {
        return symbolwaluty;
    }

    public void setSymbolwaluty(String symbolwaluty) {
        this.symbolwaluty = symbolwaluty;
    }

    public String getNazwawaluty() {
        return nazwawaluty;
    }

    public void setNazwawaluty(String nazwawaluty) {
        this.nazwawaluty = nazwawaluty;
    }

    public String getSkrotsymbolu() {
        return skrotsymbolu;
    }

    public void setSkrotsymbolu(String skrotsymbolu) {
        this.skrotsymbolu = skrotsymbolu;
    }
    
    
    
   
    //</editor-fold>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Waluty)) {
            return false;
        }
        Waluty other = (Waluty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Symbolwaluty " + symbolwaluty;
    }

   
    
}


