/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import deklaracje.vatzd.WniosekVATZDSuper;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wniosekVATZDEntity", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"wniosek"})}
)
@NamedQueries({
    @NamedQuery(name = "WniosekVATZDEntity.findByPodatnikRokMcFK", query = "SELECT d FROM WniosekVATZDEntity d WHERE d.rok = :rok AND d.mc = :mc AND d.podatnik = :podatnik")
})
public class WniosekVATZDEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "wniosek")
    private WniosekVATZDSuper  wniosek;
    @Column(name = "zalacznik")
    private String  zalacznik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;

    public WniosekVATZDEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getWniosek() {
        return wniosek;
    }

    public void setWniosek(WniosekVATZDSuper wniosek) {
        this.wniosek = wniosek;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getZalacznik() {
        return zalacznik;
    }

    public void setZalacznik(String zalacznik) {
        this.zalacznik = zalacznik;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.wniosek);
        hash = 71 * hash + Objects.hashCode(this.rok);
        hash = 71 * hash + Objects.hashCode(this.mc);
        hash = 71 * hash + Objects.hashCode(this.podatnik);
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
        final WniosekVATZDEntity other = (WniosekVATZDEntity) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.wniosek, other.wniosek)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }
    
    

   

    @Override
    public String toString() {
        return "WniosekVATZDEntity{" + "wniosek=" + wniosek + ", rok=" + rok + ", mc=" + mc + ", podatnik=" + podatnik.getPrintnazwa() + '}';
    }
    
    
    
}
