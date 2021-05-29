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
@Table(name = "zmiennapotracenia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zmiennapotracenia.findAll", query = "SELECT z FROM Zmiennapotracenia z"),
    @NamedQuery(name = "Zmiennapotracenia.findById", query = "SELECT z FROM Zmiennapotracenia z WHERE z.id = :id"),
    @NamedQuery(name = "Zmiennapotracenia.findByDatado", query = "SELECT z FROM Zmiennapotracenia z WHERE z.datado = :datado"),
    @NamedQuery(name = "Zmiennapotracenia.findByDataod", query = "SELECT z FROM Zmiennapotracenia z WHERE z.dataod = :dataod"),
    @NamedQuery(name = "Zmiennapotracenia.findByNazwa", query = "SELECT z FROM Zmiennapotracenia z WHERE z.nazwa = :nazwa"),
    @NamedQuery(name = "Zmiennapotracenia.findByUmowa", query = "SELECT z FROM Zmiennapotracenia z WHERE z.skladnikpotracenia.umowa = :umowa"),
    @NamedQuery(name = "Zmiennapotracenia.findBySkladnik", query = "SELECT z FROM Zmiennapotracenia z WHERE z.skladnikpotracenia = :skladnikpotracenia")
    })
public class Zmiennapotracenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "datado")
    private String datado;
    @Size(max = 255)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwotastala")
    private double kwotastala;
    @Column(name = "kwotakomornicza")
    private double kwotakomornicza;
    @Column(name = "kwotakomorniczarozliczona")
    private double kwotakomorniczarozliczona;
    @JoinColumn(name = "skladnikpotracenia", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Skladnikpotracenia skladnikpotracenia;


    public Zmiennapotracenia() {
    }

    public Zmiennapotracenia(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof Zmiennapotracenia)) {
            return false;
        }
        Zmiennapotracenia other = (Zmiennapotracenia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zmiennapotracenia[ id=" + id + " ]";
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getKwotastala() {
        return kwotastala;
    }

    public void setKwotastala(double kwotastala) {
        this.kwotastala = kwotastala;
    }

    public double getKwotakomornicza() {
        return kwotakomornicza;
    }

    public void setKwotakomornicza(double kwotakomornicza) {
        this.kwotakomornicza = kwotakomornicza;
    }

    public double getKwotakomorniczarozliczona() {
        return kwotakomorniczarozliczona;
    }

    public void setKwotakomorniczarozliczona(double kwotakomorniczarozliczona) {
        this.kwotakomorniczarozliczona = kwotakomorniczarozliczona;
    }

    public Skladnikpotracenia getSkladnikpotracenia() {
        return skladnikpotracenia;
    }

    public void setSkladnikpotracenia(Skladnikpotracenia skladnikpotracenia) {
        this.skladnikpotracenia = skladnikpotracenia;
    }
    
}
