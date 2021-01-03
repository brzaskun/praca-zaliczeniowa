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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "naliczenieskladnikawynagrodzenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findAll", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n"),
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findById", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n WHERE n.id = :id"),
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findByKwota", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n WHERE n.kwota = :kwota"),
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findByKwotabezzus", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n WHERE n.kwotabezzus = :kwotabezzus"),
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findByKwotazus", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n WHERE n.kwotazus = :kwotazus")})
public class Naliczenieskladnikawynagrodzenia implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotabezzus")
    private double kwotabezzus;
    @Column(name = "kwotazus")
    private double kwotazus;
    @Column(name = "kwotazredukowana")
    private double kwotazredukowana;
    @Column(name = "ilezredukowano")
    private double ilezredukowano;
    @JoinColumn(name = "pasekwynagrodzen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pasekwynagrodzen pasekwynagrodzen;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "skladnikwynagrodzenia", referencedColumnName = "id")
    @ManyToOne
    private Skladnikwynagrodzenia skladnikwynagrodzenia;

    public Naliczenieskladnikawynagrodzenia() {
    }

    public Naliczenieskladnikawynagrodzenia(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Skladnikwynagrodzenia getSkladnikwynagrodzenia() {
        return skladnikwynagrodzenia;
    }

    public void setSkladnikwynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        this.skladnikwynagrodzenia = skladnikwynagrodzenia;
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
        if (!(object instanceof Naliczenieskladnikawynagrodzenia)) {
            return false;
        }
        Naliczenieskladnikawynagrodzenia other = (Naliczenieskladnikawynagrodzenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Naliczenieskladnikawynagrodzenia[ id=" + id + " ]";
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotabezzus() {
        return kwotabezzus;
    }

    public void setKwotabezzus(double kwotabezzus) {
        this.kwotabezzus = kwotabezzus;
    }

    public double getKwotazus() {
        return kwotazus;
    }

    public void setKwotazus(double kwotazus) {
        this.kwotazus = kwotazus;
    }

    public double getKwotazredukowana() {
        return kwotazredukowana;
    }

    public void setKwotazredukowana(double kwotazredukowana) {
        this.kwotazredukowana = kwotazredukowana;
    }

    public double getIlezredukowano() {
        return ilezredukowano;
    }

    public void setIlezredukowano(double ilezredukowano) {
        this.ilezredukowano = ilezredukowano;
    }

    public Pasekwynagrodzen getPasekwynagrodzen() {
        return pasekwynagrodzen;
    }

    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
        this.pasekwynagrodzen = pasekwynagrodzen;
    }
    
}
