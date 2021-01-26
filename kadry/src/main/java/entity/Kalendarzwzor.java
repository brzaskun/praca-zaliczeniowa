/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kalendarzwzor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarzwzor.findAll", query = "SELECT k FROM Kalendarzwzor k"),
    @NamedQuery(name = "Kalendarzwzor.findById", query = "SELECT k FROM Kalendarzwzor k WHERE k.id = :id"),
    @NamedQuery(name = "Kalendarzwzor.findByRok", query = "SELECT k FROM Kalendarzwzor k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kalendarzwzor.findByMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kalendarzwzor.findByFirmaRok", query = "SELECT k FROM Kalendarzwzor k WHERE k.firma=:firma AND k.rok=:rok"),
    @NamedQuery(name = "Kalendarzwzor.findByFirmaRokMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.firma=:firma AND k.rok=:rok AND k.mc = :mc")
})
public class Kalendarzwzor implements Serializable {
    @NotNull
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @NotNull
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "kalendarzwzor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Dzien> dzienList;
    @NotNull
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne
    private Firma firma;

    public Kalendarzwzor() {
    }

    public Kalendarzwzor(int id) {
        this.id = id;
    }

    public Kalendarzwzor(Firma firma, String rok) {
        this.firma = firma;
        this.rok = rok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Kalendarzwzor)) {
            return false;
        }
        Kalendarzwzor other = (Kalendarzwzor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "entity.Kalendarzwzor[ id=" + id + " ]";
    }

     public void generujdnizglobalnego(Kalendarzwzor kalendarzwzor) {
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < kalendarzwzor.getDzienList().size(); i++) {
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i);
            Dzien dzien = new Dzien(dzienwzor, this);
            nowedni.add(dzien);
        }
        this.dzienList = nowedni;
    }
    
    
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }


    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
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
   
        
}
