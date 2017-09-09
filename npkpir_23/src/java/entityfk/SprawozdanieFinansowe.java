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
    @NamedQuery(name = "SprawozdanieFinansowe.findByRok", query = "SELECT m FROM SprawozdanieFinansowe m WHERE m.rok = :rok"),
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
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Column(name = "zaksiegowano")
    private String zaksiegowano;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datazaksiegowania")
    private Date datazaksiegowania;
    @Column(name = "cit8")
    private String cit8;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datacit8")
    private Date datacit8;
    @Column(name = "zatwierdzajacy")
    private String zatwierdzajacy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datazatwierdzenia")
    private Date datazatwierdzenia;
    @Temporal(TemporalType.DATE)
    @Column(name = "wyslanedopodatnika")
    private Date wyslanedopodatnika;
    @Column(name = "wyslal")
    private String wyslal;
    @Temporal(TemporalType.DATE)
    @Column(name = "wrociloodpodatnika")
    private Date wrociloodpodatnika;
    @Column(name = "wrocil")
    private String wrocil;
    @Column(name = "zlozonedokrs")
    @Temporal(TemporalType.DATE)
    private Date zlozonedokrs;
    @Column(name = "zlozyl")
    private String zlozyl;
    @Temporal(TemporalType.DATE)
    @Column(name = "zatwierdzonewkrs")
    private Date zatwierdzonewkrs;
    @Column(name = "zatwierdzil")
    private String zatwierdzil;
    @Column(name = "zlozonewurzedzie")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date zlozonewurzedzie;
    @Column(name = "zlozylurzad")
    private String zlozylurzad;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.rok);
        hash = 67 * hash + Objects.hashCode(this.podatnik);
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
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
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

    public String getCit8() {
        return cit8;
    }

    public void setCit8(String cit8) {
        this.cit8 = cit8;
    }

    public Date getDatacit8() {
        return datacit8;
    }

    public void setDatacit8(Date datacit8) {
        this.datacit8 = datacit8;
    }
    
  

    public String getWyslal() {
        return wyslal;
    }

    public void setWyslal(String wyslal) {
        this.wyslal = wyslal;
    }

    public String getWrocil() {
        return wrocil;
    }

    public void setWrocil(String wrocil) {
        this.wrocil = wrocil;
    }

    public String getZlozyl() {
        return zlozyl;
    }

    public void setZlozyl(String zlozyl) {
        this.zlozyl = zlozyl;
    }

    public String getZatwierdzil() {
        return zatwierdzil;
    }

    public void setZatwierdzil(String zatwierdzil) {
        this.zatwierdzil = zatwierdzil;
    }

    public String getZlozylurzad() {
        return zlozylurzad;
    }

    public void setZlozylurzad(String zlozylurzad) {
        this.zlozylurzad = zlozylurzad;
    }
    
  
    
    public Date getDatazaksiegowania() {
        return datazaksiegowania;
    }
    
    public void setDatazaksiegowania(Date datazaksiegowania) {
        this.datazaksiegowania = datazaksiegowania;
    }

    public String getZaksiegowano() {
        return zaksiegowano;
    }

    public void setZaksiegowano(String zaksiegowano) {
        this.zaksiegowano = zaksiegowano;
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
