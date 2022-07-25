/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
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

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatnikudzialy")
@NamedQueries({
    @NamedQuery(name = "PodatnikUdzialy.findBypodatnik", query = "SELECT k FROM PodatnikUdzialy k WHERE k.podatnikObj = :podatnik")
})
public class PodatnikUdzialy implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "mcOd")
    private String mcOd;
    @Column(name = "rokOd")
    private String rokOd;
    @Column(name = "mcDo")
    private String mcDo;
    @Column(name = "rokDo")
    private String rokDo;
    @Column(name = "nazwiskoimie")
    private String nazwiskoimie;
    @Column(name = "nip")
    private String nip;
    @Column(name = "pesel")
    private String pesel;
    @Column(name = "udzial")
    private String udzial;
    @Column(name = "liczbaudzialow")
    private int liczbaudzialow;
    @Column(name = "wartoscnominalna")
    private double wartoscnominalna;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnikObj;
    @Column(name = "datarozpoczecia")
    private String datarozpoczecia;
    @Column(name = "datazakonczenia")
    private String datazakonczenia;
    @Column(name = "opodatkowanie")
    private String opodatkowanie;
    @JoinColumn(name = "podmiot", referencedColumnName = "id")
    @ManyToOne
    private Podmiot podmiot;
   

    public PodatnikUdzialy() {
        
    }
    
    public PodatnikUdzialy(Udzialy p, Podatnik podatnikObj) {
        this.rokOd = p.getRokOd();
        this.rokDo = p.getRokDo();
        this.mcOd = p.getMcOd();
        this.mcDo = p.getMcDo();
        this.nazwiskoimie = p.getNazwiskoimie();
        this.nip = p.getNip();
        this.udzial = p.getUdzial();
        this.podatnikObj = podatnikObj;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.nazwiskoimie);
        hash = 29 * hash + Objects.hashCode(this.nip);
        hash = 29 * hash + Objects.hashCode(this.udzial);
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
        final PodatnikUdzialy other = (PodatnikUdzialy) obj;
        if (!Objects.equals(this.mcOd, other.mcOd)) {
            return false;
        }
        if (!Objects.equals(this.rokOd, other.rokOd)) {
            return false;
        }
        if (!Objects.equals(this.mcDo, other.mcDo)) {
            return false;
        }
        if (!Objects.equals(this.rokDo, other.rokDo)) {
            return false;
        }
        if (!Objects.equals(this.nazwiskoimie, other.nazwiskoimie)) {
            return false;
        }
        if (!Objects.equals(this.nip, other.nip)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PodatnikUdzialy{" + "id=" + id + ", mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", nazwiskoimie=" + nazwiskoimie + ", nip=" + nip + ", udzial=" + udzial + '}';
    }

    
    
    public String getMcOd() {
        return mcOd;
    }

    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }

    public String getRokOd() {
        return rokOd;
    }

    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }

    public String getMcDo() {
        return mcDo;
    }

    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }

    public String getRokDo() {
        return rokDo;
    }

    public Podmiot getPodmiot() {
        return podmiot;
    }

    public void setPodmiot(Podmiot podmiot) {
        this.podmiot = podmiot;
    }

    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }

    public String getNazwiskoimie() {
        return nazwiskoimie;
    }

    public void setNazwiskoimie(String nazwiskoimie) {
        this.nazwiskoimie = nazwiskoimie;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getUdzial() {
        return udzial;
    }

    public void setUdzial(String udzial) {
        udzial = udzial.replace(",", ".");
        this.udzial = udzial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getDatarozpoczecia() {
        return datarozpoczecia;
    }

    public void setDatarozpoczecia(String datarozpoczecia) {
        this.datarozpoczecia = datarozpoczecia;
    }

    public String getDatazakonczenia() {
        return datazakonczenia;
    }

    public void setDatazakonczenia(String datazakonczenia) {
        this.datazakonczenia = datazakonczenia;
    }

    public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    public int getLiczbaudzialow() {
        return liczbaudzialow;
    }

    public void setLiczbaudzialow(int liczbaudzialow) {
        this.liczbaudzialow = liczbaudzialow;
    }

    public double getWartoscnominalna() {
        return wartoscnominalna;
    }

    public void setWartoscnominalna(double wartoscnominalna) {
        this.wartoscnominalna = wartoscnominalna;
    }

   
    
}
