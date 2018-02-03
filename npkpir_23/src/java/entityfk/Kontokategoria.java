/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kontokategoria", catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontokategoria.findAll", query = "SELECT k FROM Kontokategoria k"),
    @NamedQuery(name = "Kontokategoria.findById", query = "SELECT k FROM Kontokategoria k WHERE k.id = :id"),
    @NamedQuery(name = "Kontokategoria.findBySymbol", query = "SELECT k FROM Kontokategoria k WHERE k.symbol = :symbol"),
    @NamedQuery(name = "Kontokategoria.findByOpispelny", query = "SELECT k FROM Kontokategoria k WHERE k.opispelny = :opispelny"),
    @NamedQuery(name = "Kontokategoria.findByOpisskrocony", query = "SELECT k FROM Kontokategoria k WHERE k.opisskrocony = :opisskrocony")})
public class Kontokategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "symbol")
    private String symbol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "opispelny")
    private String opispelny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "opisskrocony")
    private String opisskrocony;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Konto> listakont;

    public Kontokategoria() {
        this.listakont = new ArrayList<>();
    }

    public Kontokategoria(int id) {
        this.listakont = new ArrayList<>();
        this.id = id;
    }

    public Kontokategoria(int id, String symbol, String opispelny, String opisskrocony) {
        this.listakont = new ArrayList<>();
        this.id = id;
        this.symbol = symbol;
        this.opispelny = opispelny;
        this.opisskrocony = opisskrocony;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOpispelny() {
        return opispelny;
    }

    public void setOpispelny(String opispelny) {
        this.opispelny = opispelny;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public List<Konto> getListakont() {
        return listakont;
    }

    public void setListakont(List<Konto> listakont) {
        this.listakont = listakont;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kontokategoria other = (Kontokategoria) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    

   

    @Override
    public String toString() {
        return "Kontokategoria{" + "id=" + id + ", symbol=" + symbol + ", opispelny=" + opispelny + ", opisskrocony=" + opisskrocony + '}';
    }

     
    
}
