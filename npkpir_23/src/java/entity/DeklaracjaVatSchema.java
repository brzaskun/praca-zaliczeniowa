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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames={"nazwaschemy"})
})
public class DeklaracjaVatSchema implements Serializable {
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
    @Column(name = "nazwaschemy")
    private String nazwaschemy;
    @Column(name = "wstep")
    private String wstep;
    @Column(name = "pouczenie", length = 2048)
    private String pouczenie;
    @Column(name = "mc0kw1")
    private boolean mc0kw1;
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nazwaschemy);
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
        final DeklaracjaVatSchema other = (DeklaracjaVatSchema) obj;
        if (!Objects.equals(this.nazwaschemy, other.nazwaschemy)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DeklaracjaVatSchema{" + "rokOd=" + rokOd + ", mcOd=" + mcOd + ", nazwaschemy=" + nazwaschemy + ", wstep=" + wstep + ", pouczenie=" + pouczenie + ", mc0kw1=" + mc0kw1 + '}';
    }
  
    
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

  
    public String getNazwaschemy() {
        return nazwaschemy;
    }

    public void setNazwaschemy(String nazwaschemy) {
        this.nazwaschemy = nazwaschemy;
    }

    public String getWstep() {
        return wstep;
    }

    public void setWstep(String wstep) {
        this.wstep = wstep;
    }

    public String getPouczenie() {
        return pouczenie;
    }

    public void setPouczenie(String pouczenie) {
        this.pouczenie = pouczenie;
    }

    public boolean isMc0kw1() {
        return mc0kw1;
    }

    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }

  
    
    
}
