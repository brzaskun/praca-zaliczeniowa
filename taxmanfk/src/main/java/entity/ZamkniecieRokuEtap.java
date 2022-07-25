/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zamkniecieRokuEtap", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis", "rok"})
})
@NamedQueries({
    @NamedQuery(name = "ZamkniecieRokuEtap.findAll", query = "SELECT e FROM ZamkniecieRokuEtap e"),
    @NamedQuery(name = "ZamkniecieRokuEtap.findByRok", query = "SELECT e FROM ZamkniecieRokuEtap e WHERE e.rok = :rok")
})
public class ZamkniecieRokuEtap implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "kolejnosc")
    private Integer kolejnosc;
    @Column(name = "opis")
    private String opis;
    @Column(name = "rok")
    private String rok;
    @Column(name = "pkpir")
    private boolean pkpir;
    @Column(name = "ryczalt")
    private boolean ryczalt;
    @Column(name = "ksiegi")
    private boolean ksiegi;
    @Column(name = "vat")
    private boolean vat;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.opis);
        hash = 29 * hash + Objects.hashCode(this.rok);
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
        final ZamkniecieRokuEtap other = (ZamkniecieRokuEtap) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZamkniecieRokuEtap{" + "opis=" + opis + ", rok=" + rok + ", pkpir=" + pkpir + ", ryczalt=" + ryczalt + ", ksiegi=" + ksiegi + ", vat=" + vat + '}';
    }

  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isPkpir() {
        return pkpir;
    }

    public void setPkpir(boolean pkpir) {
        this.pkpir = pkpir;
    }

    public boolean isRyczalt() {
        return ryczalt;
    }

    public void setRyczalt(boolean ryczalt) {
        this.ryczalt = ryczalt;
    }

    public boolean isKsiegi() {
        return ksiegi;
    }

    public void setKsiegi(boolean ksiegi) {
        this.ksiegi = ksiegi;
    }

    public boolean isVat() {
        return vat;
    }

    public void setVat(boolean vat) {
        this.vat = vat;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }
    
    
    
}
