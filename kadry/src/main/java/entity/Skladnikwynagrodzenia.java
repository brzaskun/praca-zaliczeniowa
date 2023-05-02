/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import comparator.ZmiennaWynagrodzeniacomparator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@Table(name = "skladnikwynagrodzenia", uniqueConstraints = {
    @UniqueConstraint(columnNames={"angaz,rodzajwynagrodzenia"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Skladnikwynagrodzenia.findAll", query = "SELECT s FROM Skladnikwynagrodzenia s"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findById", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.id = :id"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByPracownik", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.angaz.pracownik = :pracownik"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByAngazRodzaj", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.angaz = :angaz AND s.rodzajwynagrodzenia = :rodzajwynagrodzenia"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByAngaz", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.angaz = :angaz"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByKodAktywny", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.rodzajwynagrodzenia.kod = :kod AND s.rodzajwynagrodzenia.aktywne = TRUE"),
    @NamedQuery(name = "Skladnikwynagrodzenia.findByFirma", query = "SELECT s FROM Skladnikwynagrodzenia s WHERE s.angaz.firma = :firma")
})
public class Skladnikwynagrodzenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @NotNull
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne()
    private Angaz angaz;
     //skladnik chorobowy
    @Column(name = "wks_serial")
    private Integer wks_serial;
    @JoinColumn(name = "rodzajwynagrodzenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rodzajwynagrodzenia rodzajwynagrodzenia;
    @Column(name = "oddelegowanie")
    private  boolean oddelegowanie;
    @Column(name = "historyczny")
    private  boolean historyczny;
    @Size(max = 255)
    @Column(name = "uwagi")
    private String uwagi;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Naliczenienieobecnosc> naliczenienieobecnoscList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skladnikwynagrodzenia", orphanRemoval = true)
    private List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList;
    

    public Skladnikwynagrodzenia() {
        this.zmiennawynagrodzeniaList = new ArrayList<>();
    }

    public Skladnikwynagrodzenia(int id) {
        this.id = id;
        this.zmiennawynagrodzeniaList = new ArrayList<>();
    }
    
    public Skladnikwynagrodzenia(Angaz angaz) {
        this.angaz = angaz;
        this.rodzajwynagrodzenia = new Rodzajwynagrodzenia();
        this.rodzajwynagrodzenia.setId(-1);
        this.rodzajwynagrodzenia.setOpispelny("dodaj nowy składnik");
        this.rodzajwynagrodzenia.setOpisskrocony("dodaj nowy skadnik");
        this.zmiennawynagrodzeniaList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public  boolean isZus0bezzus1() {
        return this.rodzajwynagrodzenia.isZus0bezzus1();
    }
    
     public boolean isPodatek0bezpodatek1() {
        return this.rodzajwynagrodzenia.isPodatek0bezpodatek1();
    }

    @XmlTransient
    public List<Naliczenieskladnikawynagrodzenia> getNaliczenieskladnikawynagrodzeniaList() {
        return naliczenieskladnikawynagrodzeniaList;
    }

    public void setNaliczenieskladnikawynagrodzeniaList(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        this.naliczenieskladnikawynagrodzeniaList = naliczenieskladnikawynagrodzeniaList;
    }

    public Integer getWks_serial() {
        return wks_serial;
    }

    public void setWks_serial(Integer wks_serial) {
        this.wks_serial = wks_serial;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public boolean isHistoryczny() {
        return historyczny;
    }

    public void setHistoryczny(boolean historyczny) {
        this.historyczny = historyczny;
    }

   
    
    public String getKod() {
        return this.rodzajwynagrodzenia.getKod();
    }

    @XmlTransient
    public List<Naliczenienieobecnosc> getNaliczenienieobecnoscList() {
        return naliczenienieobecnoscList;
    }

    public void setNaliczenienieobecnoscList(List<Naliczenienieobecnosc> naliczenienieobecnoscList) {
        this.naliczenienieobecnoscList = naliczenienieobecnoscList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.angaz);
        hash = 71 * hash + Objects.hashCode(this.rodzajwynagrodzenia);
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
        final Skladnikwynagrodzenia other = (Skladnikwynagrodzenia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.angaz, other.angaz)) {
            return false;
        }
        return Objects.equals(this.rodzajwynagrodzenia, other.rodzajwynagrodzenia);
    }

    

   

    @Override
    public String toString() {
        return "Skladnikwynagrodzenia kod:" + rodzajwynagrodzenia.getKod() + " rodzajwynagrodzenia=" + rodzajwynagrodzenia.getOpisskrocony() + ", angaz=" + angaz.getPracownik().getNazwiskoImie() + ", oddelegowanie=" + oddelegowanie + '}';
    }

   
    @XmlTransient
    public List<Zmiennawynagrodzenia> getZmiennawynagrodzeniaList() {
        return zmiennawynagrodzeniaList;
    }
    public void setZmiennawynagrodzeniaList(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList) {
        this.zmiennawynagrodzeniaList = zmiennawynagrodzeniaList;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public Rodzajwynagrodzenia getRodzajwynagrodzenia() {
        return rodzajwynagrodzenia;
    }

    public void setRodzajwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        this.rodzajwynagrodzenia = rodzajwynagrodzenia;
    }

    public boolean isOddelegowanie() {
        return oddelegowanie;
    }

    public void setOddelegowanie(boolean oddelegowanie) {
        this.oddelegowanie = oddelegowanie;
    }

    public Zmiennawynagrodzenia pobierzzmienna(Rachunekdoumowyzlecenia p) {
        Zmiennawynagrodzenia zwrot = null;
        if (this.zmiennawynagrodzeniaList!=null) {
            for (Zmiennawynagrodzenia r : this.zmiennawynagrodzeniaList) {
                if (r.getDataod().equals(p.getDataod())) {
                    zwrot = r;
                    break;
                }
            }
        }
        return zwrot;
    }

    public Zmiennawynagrodzenia getOstatniaZmienna() {
        Zmiennawynagrodzenia zwrot = null;
        if (this.zmiennawynagrodzeniaList!=null && this.zmiennawynagrodzeniaList.size()>0) {
            for (Zmiennawynagrodzenia r : this.zmiennawynagrodzeniaList) {
                if (r.getDatado()==null||r.getDatado().equals("")) { 
                    zwrot = r;
                    break;
                }
            }
            if (zwrot == null) {
                Collections.sort(this.zmiennawynagrodzeniaList, new ZmiennaWynagrodzeniacomparator());
                zwrot = this.zmiennawynagrodzeniaList.get(0);
            }
        }
        return zwrot;
    }


    
    
}
