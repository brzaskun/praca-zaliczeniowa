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
@Table(name = "dzien")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dzien.findAll", query = "SELECT d FROM Dzien d"),
    @NamedQuery(name = "Dzien.findById", query = "SELECT d FROM Dzien d WHERE d.id = :id"),
    @NamedQuery(name = "Dzien.findByNrdnia", query = "SELECT d FROM Dzien d WHERE d.nrdnia = :nrdnia"),
    @NamedQuery(name = "Dzien.findByRob1wolny0swieto2", query = "SELECT d FROM Dzien d WHERE d.typdnia = :typdnia"),
    @NamedQuery(name = "Dzien.findByNormagodzin", query = "SELECT d FROM Dzien d WHERE d.normagodzin = :normagodzin"),
    @NamedQuery(name = "Dzien.findByPrzepracowano", query = "SELECT d FROM Dzien d WHERE d.przepracowano = :przepracowano"),
    @NamedQuery(name = "Dzien.findByPiecdziesiatki", query = "SELECT d FROM Dzien d WHERE d.piecdziesiatki = :piecdziesiatki"),
    @NamedQuery(name = "Dzien.findBySetki", query = "SELECT d FROM Dzien d WHERE d.setki = :setki"),
    @NamedQuery(name = "Dzien.findByPoranocna", query = "SELECT d FROM Dzien d WHERE d.poranocna = :poranocna")})
public class Dzien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2)
    @Column(name = "nrdnia")
    private int nrdnia;
    //0roboczy
    //1sobota
    //2niedziela
    //3swieto
    //4ekwiwalentzaswieto
    @Column(name = "typdnia")
    private int typdnia;
    @Size(max = 45)
    @Column(name = "normagodzin")
    private double normagodzin;
    @Size(max = 45)
    @Column(name = "przepracowano")
    private double przepracowano;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "piecdziesiatki")
    private double piecdziesiatki;
    @Column(name = "setki")
    private double setki;
    @Column(name = "poranocna")
    private double poranocna;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzmiesiac kalendarzmiesiac;
    @Size(max = 45)
    @Column(name = "kod")
    private String kod;

    public Dzien() {
    }

    public Dzien(Integer id) {
        this.id = id;
    }

    public Dzien(int nrdnia, int rob1wolny0swieto2, double normagodzin, double przepracowano, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        this.kalendarzmiesiac = kalendarzmiesiac;
    }
    
    public Dzien(int nrdnia, int rob1wolny0swieto2, double normagodzin, double przepracowano, double piecdziesiatki, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        this.kalendarzmiesiac = kalendarzmiesiac;
        this.piecdziesiatki = piecdziesiatki;
    }
    
    public Dzien(int nrdnia, int rob1wolny0swieto2, int normagodzin) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        this.kalendarzmiesiac = kalendarzmiesiac;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNrdnia() {
        return nrdnia;
    }

    public void setNrdnia(int nrdnia) {
        this.nrdnia = nrdnia;
    }

    public int getTypdnia() {
        return typdnia;
    }

    public void setTypdnia(int typdnia) {
        this.typdnia = typdnia;
    }

    public double getNormagodzin() {
        return normagodzin;
    }

    public void setNormagodzin(double normagodzin) {
        this.normagodzin = normagodzin;
    }

    public double getPrzepracowano() {
        return przepracowano;
    }

    public void setPrzepracowano(double przepracowano) {
        this.przepracowano = przepracowano;
    }

   

    public double getPiecdziesiatki() {
        return piecdziesiatki;
    }

    public void setPiecdziesiatki(double piecdziesiatki) {
        this.piecdziesiatki = piecdziesiatki;
    }

    public double getSetki() {
        return setki;
    }

    public void setSetki(double setki) {
        this.setki = setki;
    }

    public double getPoranocna() {
        return poranocna;
    }

    public void setPoranocna(double poranocna) {
        this.poranocna = poranocna;
    }

    public Kalendarzmiesiac getKalendarzmiesiac() {
        return kalendarzmiesiac;
    }

    public void setKalendarzmiesiac(Kalendarzmiesiac kalendarzmiesiac) {
        this.kalendarzmiesiac = kalendarzmiesiac;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
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
        if (!(object instanceof Dzien)) {
            return false;
        }
        Dzien other = (Dzien) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dzien{" + "nrdnia=" + nrdnia + ", typdnia=" + typdnia + ", normagodzin=" + normagodzin + ", przepracowano=" + przepracowano + ", piecdziesiatki=" + piecdziesiatki + ", setki=" + setki + ", poranocna=" + poranocna + ", kalendarzmiesiac=" + kalendarzmiesiac.getMc() + ", kod=" + kod + '}';
    }

  
    
}
