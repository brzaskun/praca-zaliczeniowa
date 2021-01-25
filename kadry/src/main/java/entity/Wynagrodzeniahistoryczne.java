/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "wynagrodzeniahistoryczne")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findAll", query = "SELECT w FROM Wynagrodzeniahistoryczne w"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findById", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.id = :id"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByRok", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.rok = :rok"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByMc", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.mc = :mc"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByAngaz", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.angaz = :angaz"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByWynagrodzeniestale", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.wynagrodzeniestale = :wynagrodzeniestale"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByWynagrodzeniezmienne", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.wynagrodzeniezmienne = :wynagrodzeniezmienne"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByDniobowiazku", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.dniobowiazku = :dniobowiazku"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByDniprzepracowane", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.dniprzepracowane = :dniprzepracowane"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByWynagrodzenieuzupelnione", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.wynagrodzenieuzupelnione = :wynagrodzenieuzupelnione"),
    @NamedQuery(name = "Wynagrodzeniahistoryczne.findByTrzebapominac", query = "SELECT w FROM Wynagrodzeniahistoryczne w WHERE w.trzebapominac = :trzebapominac")})
public class Wynagrodzeniahistoryczne implements Serializable {

    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "wynagrodzeniestale")
    private double wynagrodzeniestale;
    @Column(name = "wynagrodzeniezmienne")
    private double wynagrodzeniezmienne;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dniobowiazku")
    private Integer dniobowiazku;
    @Column(name = "dniprzepracowane")
    private Integer dniprzepracowane;
    @Column(name = "wynagrodzenieuzupelnione")
    private double wynagrodzenieuzupelnione;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "trzebapominac")
    private boolean trzebapominac;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;

    public Wynagrodzeniahistoryczne() {
    }

    public Wynagrodzeniahistoryczne(int id) {
        this.id = id;
    }

    public Wynagrodzeniahistoryczne(Angaz selectedangaz, String[] poprzedniOkres) {
        this.angaz = selectedangaz;
        this.rok = poprzedniOkres[1];
        this.mc = poprzedniOkres[0];
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public boolean getTrzebapominac() {
        return trzebapominac;
    }

    public void setTrzebapominac(boolean trzebapominac) {
        this.trzebapominac = trzebapominac;
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
        if (!(object instanceof Wynagrodzeniahistoryczne)) {
            return false;
        }
        Wynagrodzeniahistoryczne other = (Wynagrodzeniahistoryczne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wynagrodzeniahistoryczne[ id=" + id + " ]";
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

    public double getWynagrodzeniestale() {
        return wynagrodzeniestale;
    }

    public void setWynagrodzeniestale(double wynagrodzeniestale) {
        this.wynagrodzeniestale = wynagrodzeniestale;
    }

    public double getWynagrodzeniezmienne() {
        return wynagrodzeniezmienne;
    }

    public void setWynagrodzeniezmienne(double wynagrodzeniezmienne) {
        this.wynagrodzeniezmienne = wynagrodzeniezmienne;
    }

    public Integer getDniobowiazku() {
        return dniobowiazku;
    }

    public void setDniobowiazku(Integer dniobowiazku) {
        this.dniobowiazku = dniobowiazku;
    }

    public Integer getDniprzepracowane() {
        return dniprzepracowane;
    }

    public void setDniprzepracowane(Integer dniprzepracowane) {
        this.dniprzepracowane = dniprzepracowane;
    }

    public double getWynagrodzenieuzupelnione() {
        return wynagrodzenieuzupelnione;
    }

    public void setWynagrodzenieuzupelnione(double wynagrodzenieuzupelnione) {
        this.wynagrodzenieuzupelnione = wynagrodzenieuzupelnione;
    }
    
}
