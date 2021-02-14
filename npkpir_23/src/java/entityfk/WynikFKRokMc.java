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
import javax.persistence.Cacheable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"podid", "rok", "mc","udzialowiec"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WynikFKRokMc.findAll", query = "SELECT w FROM WynikFKRokMc w"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokMc", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.mc = :mc"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokMcFirma", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.mc = :mc AND w.udzialowiec = 'firma'"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokMcUdzialowiec", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.mc = :mc AND w.udzialowiec = :udzialowiec"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRok", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokFirma", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.udzialowiec = 'firma'"),
    @NamedQuery(name = "WynikFKRokMc.findPodatnikRokUdzialowiec", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnik AND w.rok = :rok AND w.udzialowiec IS NOT NULL"),
    @NamedQuery(name = "WynikFKRokMc.findById", query = "SELECT w FROM WynikFKRokMc w WHERE w.id = :id"),
    @NamedQuery(name = "WynikFKRokMc.findByKoszty", query = "SELECT w FROM WynikFKRokMc w WHERE w.koszty = :koszty"),
    @NamedQuery(name = "WynikFKRokMc.findByMc", query = "SELECT w FROM WynikFKRokMc w WHERE w.mc = :mc"),
    @NamedQuery(name = "WynikFKRokMc.findByNkup", query = "SELECT w FROM WynikFKRokMc w WHERE w.nkup = :nkup"),
    @NamedQuery(name = "WynikFKRokMc.findByNpup", query = "SELECT w FROM WynikFKRokMc w WHERE w.npup = :npup"),
    @NamedQuery(name = "WynikFKRokMc.findByPrzychody", query = "SELECT w FROM WynikFKRokMc w WHERE w.przychody = :przychody"),
    @NamedQuery(name = "WynikFKRokMc.findByRok", query = "SELECT w FROM WynikFKRokMc w WHERE w.rok = :rok"),
    @NamedQuery(name = "WynikFKRokMc.findByWynikfinansowy", query = "SELECT w FROM WynikFKRokMc w WHERE w.wynikfinansowy = :wynikfinansowy"),
    @NamedQuery(name = "WynikFKRokMc.findByWynikpodatkowy", query = "SELECT w FROM WynikFKRokMc w WHERE w.wynikpodatkowy = :wynikpodatkowy"),
    @NamedQuery(name = "WynikFKRokMc.findByPodatnikObj", query = "SELECT w FROM WynikFKRokMc w WHERE w.podatnikObj = :podatnikObj")})
@Cacheable
public class WynikFKRokMc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double koszty;
    @Size(max = 255)
    @Column(length = 255)
    private String mc;
    @Column(precision = 22)
    private Double nkup;
    @Column(precision = 22)
    private Double npup;
    @Column(precision = 22)
    private Double przychody;
    @Size(max = 255)
    @Column(length = 255)
    private String rok;
    @Column(precision = 22)
    private Double wynikfinansowy;
    @Column(name = "wynikfinansowynarastajaco",precision = 22)
    private Double wynikfinansowynarastajaco;
    @Column(precision = 22)
    private Double wynikpodatkowy;
    @Column(precision = 22)
    private Double podatek;
     @Column(precision = 22)
    private Double wynikfinansowynetto;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnikObj;
    @Column(name = "udzialowiec")
    private String udzialowiec;
    @Column(name = "wprowadzil")
    private String wprowadzil;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date data;
    @Column(name = "udzial")
    private double udzial;
    @Column(name = "podatekzaplacono")
    private double podatekzaplacono;
    @Column(name = "podatekdowplaty")
    private double podatekdowplaty;
    @Column(name = "dywidendawyplacono")
    private double dywidendawyplacono;
    @Column(name = "dywidendadowyplaty")
    private double dywidendadowyplaty;
   
//    @Lob()
//    @Column(name = "listaprzychody")
//    private List<SaldoKonto> listaprzychody;
//    @Lob()
//    @Column(name = "listakoszty")
//    private List<SaldoKonto> listakoszty;
    

    public WynikFKRokMc() {
    }

    public WynikFKRokMc(Integer id) {
        this.id = id;
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWynikfinansowynarastajaco() {
        return wynikfinansowynarastajaco;
    }

    public void setWynikfinansowynarastajaco(Double wynikfinansowynarastajaco) {
        this.wynikfinansowynarastajaco = wynikfinansowynarastajaco;
    }

    public double getUdzial() {
        return udzial;
    }

    public void setUdzial(double udzial) {
        this.udzial = udzial;
    }

    public double getPodatekzaplacono() {
        return podatekzaplacono;
    }

    public void setPodatekzaplacono(double podatekzaplacono) {
        this.podatekzaplacono = podatekzaplacono;
    }

    public double getPodatekdowplaty() {
        return podatekdowplaty;
    }

    public void setPodatekdowplaty(double podatekdowplaty) {
        this.podatekdowplaty = podatekdowplaty;
    }

    public double getDywidendawyplacono() {
        return dywidendawyplacono;
    }

    public void setDywidendawyplacono(double dywidendawyplacono) {
        this.dywidendawyplacono = dywidendawyplacono;
    }

    public double getDywidendadowyplaty() {
        return dywidendadowyplaty;
    }

    public void setDywidendadowyplaty(double dywidendadowyplaty) {
        this.dywidendadowyplaty = dywidendadowyplaty;
    }
    
    public Double getKoszty() {
        return koszty;
    }
    
    public void setKoszty(Double koszty) {
        this.koszty = koszty;
    }
    
    public String getMc() {
        return mc;
    }
    
    public void setMc(String mc) {
        this.mc = mc;
    }
    
    public String getUdzialowiec() {
        return udzialowiec;
    }
    
    public void setUdzialowiec(String udzialowiec) {
        this.udzialowiec = udzialowiec;
    }
    
    public Double getNkup() {
        return nkup;
    }
    
    public void setNkup(Double nkup) {
        this.nkup = nkup;
    }
    
    public Double getNpup() {
        return npup;
    }
    
    public void setNpup(Double npup) {
        this.npup = npup;
    }
    
    public Double getPrzychody() {
        return przychody;
    }
    
    public void setPrzychody(Double przychody) {
        this.przychody = przychody;
    }
    
    public String getRok() {
        return rok;
    }
    
    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public Double getWynikfinansowy() {
        return wynikfinansowy;
    }
    
    public void setWynikfinansowy(Double wynikfinansowy) {
        this.wynikfinansowy = wynikfinansowy;
    }
    
    public Double getWynikpodatkowy() {
        return wynikpodatkowy;
    }
    
    public void setWynikpodatkowy(Double wynikpodatkowy) {
        this.wynikpodatkowy = wynikpodatkowy;
    }
    
    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }
    
    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }
    
    public String getWprowadzil() {
        return wprowadzil;
    }
    
    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }
    
    public Date getData() {
        return data;
    }
    
    public void setData(Date data) {
        this.data = data;
    }
    
    public double getPrzychodyPodatkowe() {
        double p = this.przychody!=null ? this.przychody : 0.0;
        double k = this.npup !=null ? this.npup : 0.0;
        return Z.z(p+k);
    }
    
    public double getKosztyPodatkowe() {
        double p = this.koszty!=null ? this.koszty : 0.0;
        double k = this.nkup !=null ? this.nkup : 0.0;
        return Z.z(p-k);
    }
    
//    public List<SaldoKonto> getListaprzychody() {
//        return listaprzychody;
//    }
//
//    public void setListaprzychody(List<SaldoKonto> listaprzychody) {
//        this.listaprzychody = listaprzychody;
//    }
//
//    public List<SaldoKonto> getListakoszty() {
//        return listakoszty;
//    }
//
//    public void setListakoszty(List<SaldoKonto> listakoszty) {
//        this.listakoszty = listakoszty;
//    }
//
    
    public Double getPodatek() {
        return podatek;
    }
    
    public void setPodatek(Double podatek) {
        this.podatek = podatek;
    }
    
    public Double getWynikfinansowynetto() {
        return wynikfinansowynetto;
    }
    
    public void setWynikfinansowynetto(Double wynikfinansowynetto) {
        this.wynikfinansowynetto = wynikfinansowynetto;
    }
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.mc);
        hash = 13 * hash + Objects.hashCode(this.rok);
        hash = 13 * hash + Objects.hashCode(this.podatnikObj);
        hash = 13 * hash + Objects.hashCode(this.udzialowiec);
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
        final WynikFKRokMc other = (WynikFKRokMc) obj;
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        if (!Objects.equals(this.udzialowiec, other.udzialowiec)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WynikFKRokMc{" + "mc=" + mc + ", rok=" + rok + ", wynikfinansowy=" + wynikfinansowy + ", wynikpodatkowy=" + wynikpodatkowy + ", podatek=" + podatek + ", podatnikObj=" + podatnikObj + ", udzialowiec=" + udzialowiec + '}';
    }


     
    
}
