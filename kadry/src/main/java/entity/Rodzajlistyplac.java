/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "rodzajlistyplac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajlistyplac.findAll", query = "SELECT r FROM Rodzajlistyplac r"),
    @NamedQuery(name = "Rodzajlistyplac.findAktywne", query = "SELECT r FROM Rodzajlistyplac r WHERE r.aktywna = TRUE"),
    @NamedQuery(name = "Rodzajlistyplac.findById", query = "SELECT r FROM Rodzajlistyplac r WHERE r.id = :id"),
    @NamedQuery(name = "Rodzajlistyplac.findByNazwa", query = "SELECT r FROM Rodzajlistyplac r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajlistyplac.findByTyt_serial", query = "SELECT r FROM Rodzajlistyplac r WHERE r.tyt_serial = :tyt_serial"),
    @NamedQuery(name = "Rodzajlistyplac.findByUmowaoPrace", query = "SELECT r FROM Rodzajlistyplac r WHERE r.tyt_serial = 1"),
    @NamedQuery(name = "Rodzajlistyplac.findByUmowaZlecenia", query = "SELECT r FROM Rodzajlistyplac r WHERE r.tyt_serial = 2"),
    @NamedQuery(name = "Rodzajlistyplac.findByTyp", query = "SELECT r FROM Rodzajlistyplac r WHERE r.typ = :typ")})
public class Rodzajlistyplac implements Serializable {
 private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
//    id,nazwa,typ
//    1,"umowa o pracę",1
//    2,"umowa zlecenia i o dzieło",2
//    3,"pełnienie obowiązków",3
//    4,zasiłki,4

    @Column(name = "typ")
    private Integer typ;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 2)
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "tyt_serial")
    private Integer tyt_serial;
    @Column(name = "tyt_kolejnosc")
    private Short tyt_kolejnosc;
    @Column(name = "tyt_okres")
    private Character tyt_okres;
    @Column(name = "aktywna")
    private boolean aktywna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajlistyplac")
    private List<Definicjalistaplac> definicjalistaplacList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodzajlistyplac")
    private List<RodzajlistyplacRodzajwynagrodzenia> rodzajlistyplacRodzajwynagrodzeniaList;

   

    public Rodzajlistyplac() {
    }

    public Rodzajlistyplac(Integer id) {
        this.id = id;
    }

    public Rodzajlistyplac(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getTyt_serial() {
        return tyt_serial;
    }

    public void setTyt_serial(Integer tyt_serial) {
        this.tyt_serial = tyt_serial;
    }

    public Short getTyt_kolejnosc() {
        return tyt_kolejnosc;
    }

    public void setTyt_kolejnosc(Short tyt_kolejnosc) {
        this.tyt_kolejnosc = tyt_kolejnosc;
    }


    public Character getTyt_okres() {
        return tyt_okres;
    }

    public void setTyt_okres(Character tyt_okres) {
        this.tyt_okres = tyt_okres;
    }

    public boolean isAktywna() {
        return aktywna;
    }

    public void setAktywna(boolean aktywna) {
        this.aktywna = aktywna;
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
        if (!(object instanceof Rodzajlistyplac)) {
            return false;
        }
        Rodzajlistyplac other = (Rodzajlistyplac) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rodzajlistyplac{" + "typ=" + typ + ", nazwa=" + nazwa + ", symbol=" + symbol + ", aktywna=" + aktywna + '}';
    }

   

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlTransient
    public List<Definicjalistaplac> getDefinicjalistaplacList() {
        return definicjalistaplacList;
    }

    public void setDefinicjalistaplacList(List<Definicjalistaplac> definicjalistaplacList) {
        this.definicjalistaplacList = definicjalistaplacList;
    }
    
    @XmlTransient
    public List<RodzajlistyplacRodzajwynagrodzenia> getDefinicjalistaplacRodzajwynagrodzeniaList() {
        return rodzajlistyplacRodzajwynagrodzeniaList;
    }

    public void setDefinicjalistaplacRodzajwynagrodzeniaList(List<RodzajlistyplacRodzajwynagrodzenia> rodzajlistyplacRodzajwynagrodzeniaList) {
        this.rodzajlistyplacRodzajwynagrodzeniaList = rodzajlistyplacRodzajwynagrodzeniaList;
    }
}
