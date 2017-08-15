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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podid", "nrrejestracyjny"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pojazdy.findAll", query = "SELECT m FROM Pojazdy m"),
    @NamedQuery(name = "Pojazdy.findById", query = "SELECT m FROM Pojazdy m WHERE m.id = :id"),
    @NamedQuery(name = "Pojazdy.findByAktywny", query = "SELECT m FROM Pojazdy m WHERE m.aktywny = :aktywny"),
    @NamedQuery(name = "Pojazdy.findByOpismiejsca", query = "SELECT m FROM Pojazdy m WHERE m.nrrejestracyjny = :nrrejestracyjny"),
    @NamedQuery(name = "Pojazdy.findByPodatnik", query = "SELECT m FROM Pojazdy m WHERE m.podatnikObj = :podatnik"),
    @NamedQuery(name = "Pojazdy.countByPodatnik", query = "SELECT COUNT(d) FROM Pojazdy d WHERE d.podatnikObj = :podatnik")
})
public class Pojazdy implements Serializable {
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
    @Column(name = "nrrejestracyjny", nullable = false, length = 255)
    private String nrrejestracyjny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwapojazdu", nullable = false, length = 255)
    private String nazwapojazdu;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta", nullable = false, length = 5)
    private String nrkonta;
    @Column(name = "rok")
    private int rok;
    @Basic(optional = true)
    @Column(name = "pokaz0chowaj1", nullable = true)
    protected boolean pokaz0chowaj1;


    public Pojazdy() {
    }

    public Pojazdy(Integer id) {
        this.id = id;
    }

    public Pojazdy(Integer id, boolean aktywny, String opismiejsca) {
        this.id = id;
        this.aktywny = aktywny;
        this.nrrejestracyjny = opismiejsca;
    }
    
    public void uzupelnij(Podatnik podatnik, String numer, int rok) {
        this.podatnikObj = podatnik;
        this.nrkonta = numer;
        this.rok = rok;
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

    public String getNrrejestracyjny() {
        return nrrejestracyjny;
    }

    public void setNrrejestracyjny(String nrrejestracyjny) {
        this.nrrejestracyjny = nrrejestracyjny;
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

    public String getNazwapojazdu() {
        return nazwapojazdu;
    }

    public void setNazwapojazdu(String nazwapojazdu) {
        this.nazwapojazdu = nazwapojazdu;
    }
    
    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public boolean isPokaz0chowaj1() {
        return pokaz0chowaj1;
    }

    public void setPokaz0chowaj1(boolean pokaz0chowaj1) {
        this.pokaz0chowaj1 = pokaz0chowaj1;
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
        if (!(object instanceof Pojazdy)) {
            return false;
        }
        Pojazdy other = (Pojazdy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nrrejestracyjny+" "+nazwapojazdu;
    }

  
    
}
