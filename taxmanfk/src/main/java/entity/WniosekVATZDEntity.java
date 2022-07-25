/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import deklaracje.vatzd.WniosekVATZDSuper;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @Column(name = "zalacznik", length = 8192)
    private String  zalacznik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @Column(name = "naliczonyzmniejszenie")
    private double naliczonyzmniejszenie;
    @Column(name = "naliczonyzwiekszenie")
    private double naliczonyzwiekszenie;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wniosekVATZDEntity")
    private List<Dok> zawiera;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wniosekVATZDEntity")
    private List<Dokfk> zawierafk;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wniosekVATZDEntity")
    private List<Deklaracjevat> deklaracjevat;

    public WniosekVATZDEntity() {
        this.deklaracjevat = Collections.synchronizedList(new ArrayList<>());
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

    public List<Dok> getZawiera() {
        return zawiera;
    }

    public void setZawiera(List<Dok> zawiera) {
        this.zawiera = zawiera;
    }

    public List<Dokfk> getZawierafk() {
        return zawierafk;
    }

    public void setZawierafk(List<Dokfk> zawierafk) {
        this.zawierafk = zawierafk;
    }

    public List<Deklaracjevat> getDeklaracjevat() {
        return deklaracjevat;
    }

    public void setDeklaracjevat(List<Deklaracjevat> deklaracjevat) {
        this.deklaracjevat = deklaracjevat;
    }

    public double getNaliczonyzmniejszenie() {
        return naliczonyzmniejszenie;
    }

    public void setNaliczonyzmniejszenie(double naliczonyzmniejszenie) {
        this.naliczonyzmniejszenie = naliczonyzmniejszenie;
    }

    public double getNaliczonyzwiekszenie() {
        return naliczonyzwiekszenie;
    }

    public void setNaliczonyzwiekszenie(double naliczonyzwiekszenie) {
        this.naliczonyzwiekszenie = naliczonyzwiekszenie;
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
    
    public int getNetto() {
        int zwrot = 0;
        if (this.wniosek!=null && this.wniosek.getClass().equals(deklaracje.vatzd.WniosekVATZD.class)) {
            zwrot = ((deklaracje.vatzd.WniosekVATZD)this.wniosek).getPozycjeSzczegolowe().getP10().intValue();
        }
        return zwrot;
    }
    
    public int getVat() {
        int zwrot = 0;
        if (this.wniosek!=null && this.wniosek.getClass().equals(deklaracje.vatzd.WniosekVATZD.class)) {
            zwrot = ((deklaracje.vatzd.WniosekVATZD)this.wniosek).getPozycjeSzczegolowe().getP11().intValue();
        }
        return zwrot;
    }
    
    
}
