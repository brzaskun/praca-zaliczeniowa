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
@Table(name = "kalendarzmiesiac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kalendarzmiesiac.findAll", query = "SELECT k FROM Kalendarzmiesiac k"),
    @NamedQuery(name = "Kalendarzmiesiac.findById", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.id = :id"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRok", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.rok = :rok"),
    @NamedQuery(name = "Kalendarzmiesiac.findByMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc"),
    @NamedQuery(name = "Kalendarzmiesiac.findByRokMcUmowa", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa=:umowa"),
    @NamedQuery(name = "Kalendarzmiesiac.findByFirmaRokMc", query = "SELECT k FROM Kalendarzmiesiac k WHERE k.mc = :mc AND k.rok = :rok AND k.umowa.angaz.firma=:firma")
   })
public class Kalendarzmiesiac implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mc")
    private String mc;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasekwynagrodzen> pasekwynagrodzenList;
   
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "umowa", referencedColumnName = "id")
    @ManyToOne
    private Umowa umowa;
    @OneToMany(mappedBy = "kalendarzmiesiac", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dzien> dzienList;
    

    public Kalendarzmiesiac() {
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Kalendarzmiesiac(int id) {
        this.id = id;
        this.dzienList = new ArrayList<>();
        this.pasekwynagrodzenList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Umowa getUmowa() {
        return umowa;
    }
    public void setUmowa(Umowa umowa) {
        this.umowa = umowa;
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
        if (!(object instanceof Kalendarzmiesiac)) {
            return false;
        }
        Kalendarzmiesiac other = (Kalendarzmiesiac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "entity.Kalendarzmiesiac[ id=" + id + " ]";
    }
   
    @XmlTransient
    public List<Dzien> getDzienList() {
        return dzienList;
    }

    public void setDzienList(List<Dzien> dzienList) {
        this.dzienList = dzienList;
    }

    public int[] robocze() {
        int[] zwrot = new int[2];
        int roboczenalicz = 0;
        int roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0) {
                    roboczenalicz++;
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk++;
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
    public double[] roboczegodz() {
        double[] zwrot = new double[2];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getTypdnia()==0) {
                    roboczenalicz = roboczenalicz+d.getNormagodzin();
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk = roboczenawyk+d.getPrzepracowano();
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }
    
    public double[] roboczenieob(String kod) {
        double[] zwrot = new double[2];
        double roboczenalicz = 0;
        double roboczenawyk = 0;
        if (this.dzienList!=null) {
            for (Dzien d : dzienList) {
                if (d.getKod().equals(kod)) {
                    roboczenalicz = roboczenalicz+d.getNormagodzin();
                    if (d.getPrzepracowano()>0) {
                        roboczenawyk = roboczenawyk+d.getPrzepracowano();
                    }
                }
            }
        }
        zwrot[0] = roboczenalicz;
        zwrot[1] = roboczenawyk;
        return zwrot;
    }


   

    public List<Pasekwynagrodzen> getPasekwynagrodzenList() {
        return pasekwynagrodzenList;
    }

    public void setPasekwynagrodzenList(List<Pasekwynagrodzen> pasekwynagrodzenList) {
        this.pasekwynagrodzenList = pasekwynagrodzenList;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

  
    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getMc() {
        return mc;
    }
    

    public void nanies(Kalendarzwzor kalendarzwzor) {
        int i = 0;
        for (Dzien d : this.dzienList) {
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i++);
            d.nanies(dzienwzor);
        }
    }
    
    public void ganerujdnizwzrocowego(Kalendarzwzor kalendarzwzor) {
        List<Dzien> nowedni = new ArrayList<>();
        for (int i = 0; i < kalendarzwzor.getDzienList().size(); i++) {
            Dzien dzienwzor = kalendarzwzor.getDzienList().get(i);
            nowedni.add(new Dzien(dzienwzor, this));
        }
        this.dzienList = nowedni;
    }

 
        
}
