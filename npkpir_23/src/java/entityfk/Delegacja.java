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
    @UniqueConstraint(columnNames = {"podatnikObj", "opisdlugi", "rok", "krajowa0zagraniczna1"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delegacja.findAll", query = "SELECT m FROM Delegacja m"),
    @NamedQuery(name = "Delegacja.findById", query = "SELECT m FROM Delegacja m WHERE m.id = :id"),
    @NamedQuery(name = "Delegacja.findByAktywny", query = "SELECT m FROM Delegacja m WHERE m.aktywny = :aktywny AND m.rok = :rok AND m.krajowa0zagraniczna1 = :krajowa0zagraniczna1"),
    @NamedQuery(name = "Delegacja.findByOpisdlugi", query = "SELECT m FROM Delegacja m WHERE m.opisdlugi = :opisdlugi AND m.rok = :rok AND m.krajowa0zagraniczna1 = :krajowa0zagraniczna1"),
    @NamedQuery(name = "Delegacja.findByOpisdlugiOnly", query = "SELECT m FROM Delegacja m WHERE m.opisdlugi = :opisdlugi"),
    @NamedQuery(name = "Delegacja.findByPodatnik", query = "SELECT m FROM Delegacja m WHERE m.podatnikObj = :podatnik AND m.rok = :rok AND m.krajowa0zagraniczna1 = :krajowa0zagraniczna1"),
    @NamedQuery(name = "Delegacja.countByPodatnik", query = "SELECT COUNT(m) FROM Delegacja m WHERE m.podatnikObj = :podatnik AND m.rok = :rok AND m.krajowa0zagraniczna1 = :krajowa0zagraniczna1")
})
public class Delegacja implements Serializable {
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
    @Column(name = "opisdlugi", nullable = false, length = 255)
    private String opisdlugi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opiskrotki", nullable = false, length = 255)
    private String opiskrotki;
    @JoinColumn(name = "podatnikObj", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta", nullable = false, length = 5)
    private String nrkonta;
    @Column
    private int rok;
    @Column
    private boolean krajowa0zagraniczna1;


    public Delegacja() {
    }

    public Delegacja(Integer id) {
        this.id = id;
    }

    public Delegacja(Integer id, boolean aktywny, String opisdlugi) {
        this.id = id;
        this.aktywny = aktywny;
        this.opisdlugi = opisdlugi;
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

    public String getOpisdlugi() {
        return opisdlugi;
    }

    public void setOpisdlugi(String opisdlugi) {
        this.opisdlugi = opisdlugi;
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

    public String getOpiskrotki() {
        return opiskrotki;
    }

    public void setOpiskrotki(String opiskrotki) {
        this.opiskrotki = opiskrotki;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public boolean isKrajowa0zagraniczna1() {
        return krajowa0zagraniczna1;
    }

    public void setKrajowa0zagraniczna1(boolean krajowa0zagraniczna1) {
        this.krajowa0zagraniczna1 = krajowa0zagraniczna1;
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
        if (!(object instanceof Delegacja)) {
            return false;
        }
        Delegacja other = (Delegacja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return opisdlugi;
    }

  
    
}
