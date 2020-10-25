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
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwa"})
})
@NamedQueries({
    @NamedQuery(name = "JPKVATWersja.findByName", query = "SELECT p FROM JPKVATWersja p WHERE p.nazwa = :nazwa")
})
public class JPKVATWersja implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rokOd")
    private String rokOd;
    @Column(name = "mcOd")
    private String mcOd;
    @Column(name = "nazwa")
    private String nazwa;
    @Column(name = "mc0kw1")
    private boolean mc0kw1;
    

    public JPKVATWersja() {
    }

    public JPKVATWersja(JPKVATWersja dk) {
        this.rokOd = dk.rokOd;
        this.mcOd = dk.mcOd;
        this.nazwa = dk.nazwa;
        this.mc0kw1 = dk.mc0kw1;
    }

        
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nazwa);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JPKVATWersja other = (JPKVATWersja) obj;
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPKVATWersja{" + "rokOd=" + rokOd + ", mcOd=" + mcOd + ", nazwa=" + nazwa + ", mc0kw1=" + mc0kw1 + '}';
    }
  
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getRokOd() {
        return rokOd;
    }
    
    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }
    
    
    public String getMcOd() {
        return mcOd;
    }
    
    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }
    
    
    public String getNazwa() {
        return nazwa;
    }
    
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
       
    public boolean isMc0kw1() {
        return mc0kw1;
    }
    
    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }
    
    


    
    
    //</editor-fold>

    
}
