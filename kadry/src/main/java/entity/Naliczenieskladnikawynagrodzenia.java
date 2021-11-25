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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Naliczenieskladnikawynagrodzenia.findByKwota", query = "SELECT n FROM Naliczenieskladnikawynagrodzenia n WHERE n.kwotadolistyplac = :kwota")
})
public class Naliczenieskladnikawynagrodzenia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @NotNull
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 10)
    @NotNull
    @Column(name = "datado")
    private String datado;
    @Column(name = "kwotaumownazacalymc")
    private double kwotaumownazacalymc;
    @Column(name = "kwotyredukujacesuma")
    private double kwotyredukujacesuma;
    @Column(name = "kwotadolistyplac")
    private double kwotadolistyplac;
    @Column(name = "kwotadolistyplacwaluta")
    private double kwotadolistyplacwaluta;
    @Column(name = "dninalezne")
    private double dninalezne;
    @Column(name = "dnifaktyczne")
    private double dnifaktyczne;
    @Column(name = "godzinynalezne")
    private double godzinynalezne;
    @Column(name = "godzinyfaktyczne")
    private double godzinyfaktyczne;
    @Column(name = "stawkadzienna")
    private double stawkadzienna;
    @Column(name = "stawkagodzinowa")
    private double stawkagodzinowa;
    @Column(name = "stawkadziennawaluta")
    private double stawkadziennawaluta;
    @Column(name = "stawkagodzinowawaluta")
    private double stawkagodzinowawaluta;
    @JoinColumn(name = "pasekwynagrodzen", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pasekwynagrodzen pasekwynagrodzen;
    @JoinColumn(name = "skladnikwynagrodzenia", referencedColumnName = "id")
    @ManyToOne
    private Skladnikwynagrodzenia skladnikwynagrodzenia;
    //kontrola czy nie wynagrodzenie za urlop
    @Column(name = "skl_dod_1")
    private Character skl_dod_1;
    @Column(name = "skl_rodzaj")
    private Character skl_rodzaj;
   

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

    public  boolean isZus0bezzus1() {
        return this.skladnikwynagrodzenia.getRodzajwynagrodzenia().isZus0bezzus1();
    }
    
    public boolean isPodatek0bezpodatek1() {
        return this.skladnikwynagrodzenia.getRodzajwynagrodzenia().isPodatek0bezpodatek1();
    }
    public Skladnikwynagrodzenia getSkladnikwynagrodzenia() {
        return skladnikwynagrodzenia;
    }

    public void setSkladnikwynagrodzenia(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        this.skladnikwynagrodzenia = skladnikwynagrodzenia;
    }

    public Character getSkl_dod_1() {
        return skl_dod_1;
    }

    public void setSkl_dod_1(Character skl_dod_1) {
        this.skl_dod_1 = skl_dod_1;
    }

    public Character getSkl_rodzaj() {
        return skl_rodzaj;
    }

    public void setSkl_rodzaj(Character skl_rodzaj) {
        this.skl_rodzaj = skl_rodzaj;
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
        return "Naliczenieskladnikawynagrodzenia{" + "dataod=" + dataod + ", datado=" + datado + ", kwotaumownazacalymc=" + kwotaumownazacalymc + ", kwotyredukujacesuma=" + kwotyredukujacesuma + ", kwotadolistyplac=" + kwotadolistyplac + ", skladnikwynagrodzenia=" + skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod() + '}';
    }

    public double getKwotadolistyplacwaluta() {
        return kwotadolistyplacwaluta;
    }

    public void setKwotadolistyplacwaluta(double kwotadolistyplacwaluta) {
        this.kwotadolistyplacwaluta = kwotadolistyplacwaluta;
    }

    public double getStawkadziennawaluta() {
        return stawkadziennawaluta;
    }

    public void setStawkadziennawaluta(double stawkadziennawaluta) {
        this.stawkadziennawaluta = stawkadziennawaluta;
    }

    public double getStawkagodzinowawaluta() {
        return stawkagodzinowawaluta;
    }

    public void setStawkagodzinowawaluta(double stawkagodzinowawaluta) {
        this.stawkagodzinowawaluta = stawkagodzinowawaluta;
    }


    
   

    public double getKwotaumownazacalymc() {
        return kwotaumownazacalymc;
    }

    public void setKwotaumownazacalymc(double kwotaumownazacalymc) {
        this.kwotaumownazacalymc = kwotaumownazacalymc;
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

    public double getKwotyredukujacesuma() {
        return kwotyredukujacesuma;
    }

    public void setKwotyredukujacesuma(double kwotyredukujacesuma) {
        this.kwotyredukujacesuma = kwotyredukujacesuma;
    }

    public double getKwotadolistyplac() {
        return kwotadolistyplac;
    }

    public void setKwotadolistyplac(double kwotadolistyplac) {
        this.kwotadolistyplac = kwotadolistyplac;
    }


    public Pasekwynagrodzen getPasekwynagrodzen() {
        return pasekwynagrodzen;
    }

    public void setPasekwynagrodzen(Pasekwynagrodzen pasekwynagrodzen) {
        this.pasekwynagrodzen = pasekwynagrodzen;
    }

    public double getDninalezne() {
        return dninalezne;
    }

    public void setDninalezne(double dninalezne) {
        this.dninalezne = dninalezne;
    }

    public double getDnifaktyczne() {
        return dnifaktyczne;
    }

    public void setDnifaktyczne(double dnifaktyczne) {
        this.dnifaktyczne = dnifaktyczne;
    }

    public double getGodzinynalezne() {
        return godzinynalezne;
    }

    public void setGodzinynalezne(double godzinynalezne) {
        this.godzinynalezne = godzinynalezne;
    }

    public double getGodzinyfaktyczne() {
        return godzinyfaktyczne;
    }

    public void setGodzinyfaktyczne(double godzinyfaktyczne) {
        this.godzinyfaktyczne = godzinyfaktyczne;
    }

    public double getStawkadzienna() {
        return stawkadzienna;
    }

    public void setStawkadzienna(double stawkadzienna) {
        this.stawkadzienna = stawkadzienna;
    }

    public double getStawkagodzinowa() {
        return stawkagodzinowa;
    }

    public void setStawkagodzinowa(double stawkagodzinowa) {
        this.stawkagodzinowa = stawkagodzinowa;
    }

    
}
