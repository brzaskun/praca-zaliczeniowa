/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "intrastatwiersz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intrastatwiersz.findAll", query = "SELECT i FROM Intrastatwiersz i"),
    @NamedQuery(name = "Intrastatwiersz.findById", query = "SELECT i FROM Intrastatwiersz i WHERE i.id = :id"),
    @NamedQuery(name = "Intrastatwiersz.findByRok", query = "SELECT i FROM Intrastatwiersz i WHERE i.rok = :rok"),
    @NamedQuery(name = "Intrastatwiersz.findByMc", query = "SELECT i FROM Intrastatwiersz i WHERE i.mc = :mc"),
    @NamedQuery(name = "Intrastatwiersz.findByKrajprzeznaczenia", query = "SELECT i FROM Intrastatwiersz i WHERE i.krajprzeznaczenia = :krajprzeznaczenia"),
    @NamedQuery(name = "Intrastatwiersz.findByRodzajtransakcji", query = "SELECT i FROM Intrastatwiersz i WHERE i.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Intrastatwiersz.findByKodtowaru", query = "SELECT i FROM Intrastatwiersz i WHERE i.kodtowaru = :kodtowaru"),
    @NamedQuery(name = "Intrastatwiersz.findByOpistowaru", query = "SELECT i FROM Intrastatwiersz i WHERE i.opistowaru = :opistowaru"),
    @NamedQuery(name = "Intrastatwiersz.findByMasanettokg", query = "SELECT i FROM Intrastatwiersz i WHERE i.masanettokg = :masanettokg"),
    @NamedQuery(name = "Intrastatwiersz.findByIlosc", query = "SELECT i FROM Intrastatwiersz i WHERE i.ilosc = :ilosc"),
    @NamedQuery(name = "Intrastatwiersz.findByWartoscfaktury", query = "SELECT i FROM Intrastatwiersz i WHERE i.wartoscfaktury = :wartoscfaktury"),
    @NamedQuery(name = "Intrastatwiersz.findByVatuekontrahenta", query = "SELECT i FROM Intrastatwiersz i WHERE i.vatuekontrahenta = :vatuekontrahenta"),
    @NamedQuery(name = "Intrastatwiersz.deletePodRokMc", query = "DELETE FROM Intrastatwiersz a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc"),
    @NamedQuery(name = "Intrastatwiersz.findByPodRokMc", query = "SELECT a FROM Intrastatwiersz a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc")
})
public class Intrastatwiersz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "rok")
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "mc")
    private String mc;
    @Size(max = 2)
    @Column(name = "krajprzeznaczenia")
    private String krajprzeznaczenia;
    @Column(name = "rodzajtransakcji")
    private Integer rodzajtransakcji;
    @Column(name = "kodtowaru")
    private Integer kodtowaru;
    @Size(max = 128)
    @Column(name = "opistowaru")
    private String opistowaru;
    @Size(max = 45)
    @Column(name = "masanettokg")
    private String masanettokg;
    @Size(max = 45)
    @Column(name = "ilosc")
    private String ilosc;
    @Column(name = "wartoscfaktury")
    private Integer wartoscfaktury;
    @Size(max = 45)
    @Column(name = "vatuekontrahenta")
    private String vatuekontrahenta;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Podatnik podatnik;
    @XmlTransient
    private String numerkolejny;

    public Intrastatwiersz() {
    }

    public Intrastatwiersz(Integer id) {
        this.id = id;
    }

    public Intrastatwiersz(String rokWpisuSt, String miesiacWpisu, Podatnik podatnikObiekt) {
        this.rok = rokWpisuSt;
        this.mc = miesiacWpisu;
        this.podatnik = podatnikObiekt;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getKrajprzeznaczenia() {
        return krajprzeznaczenia;
    }

    public void setKrajprzeznaczenia(String krajprzeznaczenia) {
        this.krajprzeznaczenia = krajprzeznaczenia;
    }

    public Integer getRodzajtransakcji() {
        return rodzajtransakcji;
    }

    public void setRodzajtransakcji(Integer rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }

    public Integer getKodtowaru() {
        return kodtowaru;
    }

    public void setKodtowaru(Integer kodtowaru) {
        this.kodtowaru = kodtowaru;
    }

    public String getOpistowaru() {
        return opistowaru;
    }

    public void setOpistowaru(String opistowaru) {
        this.opistowaru = opistowaru;
    }

    public String getMasanettokg() {
        return masanettokg;
    }

    public void setMasanettokg(String masanettokg) {
        this.masanettokg = masanettokg;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }

    public Integer getWartoscfaktury() {
        return wartoscfaktury;
    }

    public void setWartoscfaktury(Integer wartoscfaktury) {
        this.wartoscfaktury = wartoscfaktury;
    }

    public String getVatuekontrahenta() {
        return vatuekontrahenta;
    }

    public void setVatuekontrahenta(String vatuekontrahenta) {
        this.vatuekontrahenta = vatuekontrahenta;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getNumerkolejny() {
        return numerkolejny;
    }

    public void setNumerkolejny(String numerkolejny) {
        this.numerkolejny = numerkolejny;
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
        if (!(object instanceof Intrastatwiersz)) {
            return false;
        }
        Intrastatwiersz other = (Intrastatwiersz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Intrastatwiersz{" + "rok=" + rok + ", mc=" + mc + ", krajprzeznaczenia=" + krajprzeznaczenia + ", rodzajtransakcji=" + rodzajtransakcji + ", kodtowaru=" + kodtowaru + ", opistowaru=" + opistowaru + ", masanettokg=" + masanettokg + ", ilosc=" + ilosc + ", wartoscfaktury=" + wartoscfaktury + ", vatuekontrahenta=" + vatuekontrahenta + '}';
    }

   
    
}
