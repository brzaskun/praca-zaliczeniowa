/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podatnik","rok","element"})
})
@NamedQueries({
    @NamedQuery(name = "SprawozdanieFinansowe.findByPodatnikRok", query = "SELECT m FROM SprawozdanieFinansowe m WHERE m.podatnik = :podatnik AND m.rok = :rok"),
    @NamedQuery(name = "SprawozdanieFinansowe.findByPodatnik", query = "SELECT m FROM SprawozdanieFinansowe m WHERE m.podatnik = :podatnik")
})
public class SprawozdanieFinansowe implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "rok")
    private String rok;
    @JoinColumn(name = "podatnik")
    private Podatnik podatnik;
    @Column(name = "element")
    private int element;
    @Column(name = "sporzadzajacy")
    private String sporzadzajacy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datasporzadzenia")
    private Date datasporzadzenia;
    @Column(name = "zatwierdzajacy")
    private String zatwierdzajacy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datazatwierdzenia")
    private Date datazatwierdzenia;
    @Temporal(TemporalType.DATE)
    @Column(name = "wyslanedopodatnika")
    private Date wyslanedopodatnika;
    @Temporal(TemporalType.DATE)
    @Column(name = "wrociloodpodatnika")
    private Date wrociloodpodatnika;
    @Column(name = "zlozonedokrs")
    @Temporal(TemporalType.DATE)
    private Date zlozonedokrs;
    @Temporal(TemporalType.DATE)
    @Column(name = "zatwierdzonewkrs")
    private Date zatwierdzonewkrs;
    @Column(name = "zlozonewurzedzie")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date zlozonewurzedzie;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.rok);
        hash = 67 * hash + Objects.hashCode(this.podatnik);
        hash = 67 * hash + this.element;
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
        final SprawozdanieFinansowe other = (SprawozdanieFinansowe) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.element != other.element) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SprawozdanieFinansowe{" + "rok=" + rok + ", podatnik=" + podatnik + ", element=" + element + ", sporzadzajacy=" + sporzadzajacy + '}';
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getRok() {
        return rok;
    }
    
    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public Podatnik getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }
    
    public int getElement() {
        return element;
    }
    
    public void setElement(int element) {
        this.element = element;
    }
    
  
    
    public Date getDatasporzadzenia() {
        return datasporzadzenia;
    }
    
    public void setDatasporzadzenia(Date datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public String getSporzadzajacy() {
        return sporzadzajacy;
    }

    public void setSporzadzajacy(String sporzadzajacy) {
        this.sporzadzajacy = sporzadzajacy;
    }

    public String getZatwierdzajacy() {
        return zatwierdzajacy;
    }

    public void setZatwierdzajacy(String zatwierdzajacy) {
        this.zatwierdzajacy = zatwierdzajacy;
    }
  
    
    public Date getDatazatwierdzenia() {
        return datazatwierdzenia;
    }
    
    public void setDatazatwierdzenia(Date datazatwierdzenia) {
        this.datazatwierdzenia = datazatwierdzenia;
    }
    
    public Date getWyslanedopodatnika() {
        return wyslanedopodatnika;
    }
    
    public void setWyslanedopodatnika(Date wyslanedopodatnika) {
        this.wyslanedopodatnika = wyslanedopodatnika;
    }
    
    public Date getWrociloodpodatnika() {
        return wrociloodpodatnika;
    }
    
    public void setWrociloodpodatnika(Date wrociloodpodatnika) {
        this.wrociloodpodatnika = wrociloodpodatnika;
    }
    
    public Date getZlozonedokrs() {
        return zlozonedokrs;
    }
    
    public void setZlozonedokrs(Date zlozonedokrs) {
        this.zlozonedokrs = zlozonedokrs;
    }
    
    public Date getZatwierdzonewkrs() {
        return zatwierdzonewkrs;
    }
    
    public void setZatwierdzonewkrs(Date zatwierdzonewkrs) {
        this.zatwierdzonewkrs = zatwierdzonewkrs;
    }
    
    public Date getZlozonewurzedzie() {
        return zlozonewurzedzie;
    }
    
    public void setZlozonewurzedzie(Date zlozonewurzedzie) {
        this.zlozonewurzedzie = zlozonewurzedzie;
    }
    
//</editor-fold>
    
    
}
