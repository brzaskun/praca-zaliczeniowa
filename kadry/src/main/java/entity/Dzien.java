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

    @Size(max = 5)
    @Column(name = "kod")
    private String kod;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nrdnia")
    private Integer nrdnia;
    @Column(name = "typdnia")
    private Integer typdnia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "normagodzin")
    private Double normagodzin;
    //0roboczy
    //1sobota
    //2niedziela
    //3swieto
    //4ekwiwalentzaswieto
    @Column(name = "przepracowano")
    private Double przepracowano;
    @Column(name = "piecdziesiatki")
    private Double piecdziesiatki;
    @Column(name = "setki")
    private Double setki;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "poranocna")
    private Double poranocna;
    @JoinColumn(name = "kalendarzwzor", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzwzor kalendarzwzor;
    @JoinColumn(name = "kalendarzmiesiac", referencedColumnName = "id")
    @ManyToOne
    private Kalendarzmiesiac kalendarzmiesiac;

    public Dzien() {
    }

    public Dzien(Integer id) {
        this.id = id;
    }

    public Dzien(int nrdnia, int rob1wolny0swieto2, double normagodzin, double przepracowano, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.przepracowano = przepracowano;
        this.normagodzin = normagodzin;
        this.kalendarzmiesiac = kalendarzmiesiac;
    }
    
    public Dzien(int nrdnia, int rob1wolny0swieto2, double normagodzin, double przepracowano, double piecdziesiatki, Kalendarzmiesiac kalendarzmiesiac) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        this.przepracowano = przepracowano;
        this.kalendarzmiesiac = kalendarzmiesiac;
        this.piecdziesiatki = piecdziesiatki;
    }
    
    public Dzien(int nrdnia, int rob1wolny0swieto2, double normagodzin, Kalendarzwzor kalendarzWzor) {
        this.nrdnia = nrdnia;
        this.typdnia = rob1wolny0swieto2;
        this.normagodzin = normagodzin;
        this.kalendarzwzor = kalendarzWzor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Kalendarzmiesiac getKalendarzmiesiac() {
        return kalendarzmiesiac;
    }
    public void setKalendarzmiesiac(Kalendarzmiesiac kalendarzmiesiac) {
        this.kalendarzmiesiac = kalendarzmiesiac;
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
        return "Dzien{" + "nrdnia=" + nrdnia + ", typdnia=" + typdnia + ", normagodzin=" + normagodzin + ", przepracowano=" + przepracowano + ", piecdziesiatki=" + piecdziesiatki + ", setki=" + setki + ", poranocna=" + poranocna + ", kod=" + kod + '}';
    }

    public Integer getNrdnia() {
        return nrdnia;
    }

    public void setNrdnia(Integer nrdnia) {
        this.nrdnia = nrdnia;
    }

    public Integer getTypdnia() {
        return typdnia;
    }



    public void setTypdnia(Integer typdnia) {
        this.typdnia = typdnia;
    }

    public Double getNormagodzin() {
        return normagodzin;
    }

    public void setNormagodzin(Double normagodzin) {
        this.normagodzin = normagodzin;
    }

    public Double getPrzepracowano() {
        return przepracowano;
    }

    public void setPrzepracowano(Double przepracowano) {
        this.przepracowano = przepracowano;
    }

    public Double getPiecdziesiatki() {
        return piecdziesiatki;
    }

    public void setPiecdziesiatki(Double piecdziesiatki) {
        this.piecdziesiatki = piecdziesiatki;
    }

    public Double getSetki() {
        return setki;
    }

    public void setSetki(Double setki) {
        this.setki = setki;
    }

    public Double getPoranocna() {
        return poranocna;
    }


    public void setPoranocna(Double poranocna) {
        this.poranocna = poranocna;
    }


    public Kalendarzwzor getKalendarzwzor() {
        return kalendarzwzor;
    }

    public void setKalendarzwzor(Kalendarzwzor kalendarzwzor) {
        this.kalendarzwzor = kalendarzwzor;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

  
    
}
