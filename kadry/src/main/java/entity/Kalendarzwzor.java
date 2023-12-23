/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import comparator.Dziencomparator;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kalendarzwzor", uniqueConstraints = {
    @UniqueConstraint(columnNames={"firma","rok", "mc"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarzwzor.findAll", query = "SELECT k FROM Kalendarzwzor k"),
    @NamedQuery(name = "Kalendarzwzor.findById", query = "SELECT k FROM Kalendarzwzor k WHERE k.id = :id"),
    @NamedQuery(name = "Kalendarzwzor.findByRok", query = "SELECT k FROM Kalendarzwzor k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kalendarzwzor.findByRokMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.rok = :rok AND k.mc = :mc"),
    @NamedQuery(name = "Kalendarzwzor.findByMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kalendarzwzor.findByFirmaRok", query = "SELECT k FROM Kalendarzwzor k WHERE k.firma=:firma AND k.rok=:rok ORDER BY k.mc"),
    @NamedQuery(name = "Kalendarzwzor.findByFirmaRokMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.firma=:firma AND k.rok=:rok AND k.mc = :mc"),
    @NamedQuery(name = "Kalendarzwzor.findByFirmaGlobalnaRokMc", query = "SELECT k FROM Kalendarzwzor k WHERE k.firma.id = 2 AND k.rok=:rok AND k.mc = :mc")
})
public class Kalendarzwzor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @NotNull
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @Column(name="norma")
    private double norma;
    @OneToMany(mappedBy = "kalendarzwzor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Dzien> dzienList;
    @NotNull
    @JoinColumn(name = "firma", referencedColumnName = "id")
    @ManyToOne
    private FirmaKadry firma;

    public Kalendarzwzor() {
    }

    public Kalendarzwzor(int id) {
        this.id = id;
    }

    public Kalendarzwzor(FirmaKadry firma, String rok) {
        this.firma = firma;
        this.rok = rok;
    }
    
    public Kalendarzwzor(FirmaKadry firma, String rok, String mc) {
        this.firma = firma;
        this.rok = rok;
        this.mc = mc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getNorma() {
        return norma;
    }

    public void setNorma(double norma) {
        this.norma = norma;
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

     public void generujdnizglobalnego(Kalendarzwzor kalendarzglobalny) {
        List<Dzien> dzienListGlobalny = kalendarzglobalny.getDzienList();
        Collections.sort(dzienListGlobalny, new Dziencomparator());
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < dzienListGlobalny.size(); i++) {
            Dzien dzienwzor = dzienListGlobalny.get(i);
            Dzien dzien = new Dzien(dzienwzor, this);
            nowedni.add(dzien);
        }
        this.dzienList = nowedni;
    }
    public void edytujdnizglobalnego(Kalendarzwzor kalendarzglobalny) {
        List<Dzien> dzienListGlobalny = kalendarzglobalny.getDzienList();
        Collections.sort(dzienListGlobalny, new Dziencomparator());
        List<Dzien> dzienListWzor = this.getDzienList();
        if (dzienListWzor!=null&&dzienListWzor.size()==dzienListGlobalny.size()) {
            Collections.sort(dzienListWzor, new Dziencomparator());
            for (int i = 0; i < dzienListGlobalny.size(); i++) {
                Dzien dzienwzor = dzienListWzor.get(i);
                Dzien dzienglobalny = dzienListGlobalny.get(i);
                if (dzienwzor.getTypdnia()!=dzienglobalny.getTypdnia()||dzienwzor.getNormagodzinwzorcowa()!=dzienglobalny.getNormagodzin()) {
                    dzienwzor.nanieswzor(dzienglobalny);
                }
            }
        }
    }
    
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
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
    
    public int getIdhash() {
        int zwrot = 0;
        if (this.id!=null) {
            zwrot = this.id;
        } else {
            zwrot = Integer.parseInt(this.rok)+Integer.parseInt(this.mc);
        }
        return zwrot;
    }

    public void zrobkolejnedni(Kalendarzwzor poprzedni) {
        if (poprzedni!=null) {
            List<Dzien> dzienList = poprzedni.dzienList;
            Collections.sort(dzienList,new Dziencomparator());
            int iloscroboczych = 0;
            for (int i = dzienList.size()-1;i>0;i--) {
                Dzien dzien = dzienList.get(i);
                if (dzien.getTypdnia()==2) {
                    break;
                } else if (dzien.getTypdnia()!=-1){
                    iloscroboczych = iloscroboczych+1;
                }
            }
            while (iloscroboczych>6) {
                iloscroboczych = iloscroboczych-7;
            }
            int licznik = 1;
            String data = this.getRok()+"-"+this.getMc()+"-";
            String data2 = this.getRok()+"-"+this.getMc()+"-0";
            for (Dzien d : this.dzienList) {
                d.setNrdnia(licznik);
                if (licznik<10) {
                    d.setDatastring(data2+licznik++);
                } else {
                    d.setDatastring(data+licznik++);
                }
                try {
                       LocalDate dzienszukany = LocalDate.parse(d.getDatastring());
                       DayOfWeek day = dzienszukany.getDayOfWeek();//numery od 1 poniedzialek do 7 niedziela
                    int numerdnia = day.getValue();
                    if (iloscroboczych<5) {
                        d.setTypdnia(0);
                        d.setNormagodzin(8.0);
                        d.setNormagodzinwzorcowa(8.0);
                        iloscroboczych++;
                    } else if (iloscroboczych==5) {
                        d.setTypdnia(1);
                        d.setNormagodzin(0.0);
                        d.setNormagodzinwzorcowa(0.0);
                        iloscroboczych++;
                    } else if (iloscroboczych==6) {
                        d.setTypdnia(2);
                        d.setNormagodzin(0.0);
                        d.setNormagodzinwzorcowa(0.0);
                        iloscroboczych=0;
                    }
                } catch (Exception e) {
                    d.setTypdnia(-1);
                    d.setNormagodzin(0.0);
                    d.setNormagodzinwzorcowa(0.0);
               }
            }
        }
    }
   
        
}
