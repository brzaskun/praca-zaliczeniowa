/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import entity.Dok;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@XmlRootElement
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nazwacechy", "rodzajcechy"})}
)
@NamedQueries({
    @NamedQuery(name = "Cechazapisu.findAll", query = "SELECT c FROM Cechazapisu c"),
    @NamedQuery(name = "Cechazapisu.findByPodatnikOnly", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik = :podatnik"),
    @NamedQuery(name = "Cechazapisu.findByPodatnikOnlyStatystyczne", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik = :podatnik AND c.charaktercechy='1'"),
    @NamedQuery(name = "Cechazapisu.findByPodatnik", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik IS NULL OR c.podatnik = :podatnik"),
    @NamedQuery(name = "Cechazapisu.findByPodatnikNKUP", query = "SELECT c FROM Cechazapisu c WHERE c.podatnik IS NULL AND c.nazwacechy = :nazwacechy"),
    @NamedQuery(name = "Cechazapisu.findByNazwacechy", query = "SELECT c FROM Cechazapisu c WHERE c.nazwacechy = :nazwacechy"),
    @NamedQuery(name = "Cechazapisu.findByRodzajcechy", query = "SELECT c FROM Cechazapisu c WHERE c.rodzajcechy = :rodzajcechy")})
public class Cechazapisu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String nazwacechy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(nullable = false, length = 150)
    private String rodzajcechy;
    @ManyToMany(mappedBy = "cechadokumentuLista")
    private List<Dokfk> dokfkLista;
    @ManyToMany(mappedBy = "cechadokumentuLista")
    private List<Dok> dokLista;
    @ManyToMany(mappedBy = "cechazapisuLista")
    private List<StronaWiersza> stronaWierszaLista;
    // 1 statystyczna 2 pzychody 3 koszty
    @Column(name = "charaktercechy")
    private int charaktercechy;
    @Column(name = "przesuniecie")
    private int przesuniecie;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @Transient
    private double sumaprzychodow;
    @Transient
    private double sumakosztow;
    
    

    
    
            
    
    public Cechazapisu() {
        this.nazwacechy = nazwacechy;
        this.rodzajcechy = rodzajcechy;
        this.dokfkLista = new ArrayList<>();
        this.dokLista = new ArrayList<>();
        this.stronaWierszaLista = new ArrayList<>();
        this.charaktercechy = -1;
    }


    public Cechazapisu(String nazwacechy, String rodzajcechy) {
        this.nazwacechy = nazwacechy;
        this.rodzajcechy = rodzajcechy;
        this.dokfkLista = new ArrayList<>();
        this.dokLista = new ArrayList<>();
        this.stronaWierszaLista = new ArrayList<>();
        this.charaktercechy = -1;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">

    public List<Dok> getDokLista() {
        return dokLista;
    }

    public void setDokLista(List<Dok> dokLista) {
        this.dokLista = dokLista;
    }

    public double getSumaprzychodow() {
        return sumaprzychodow;
    }

    public void setSumaprzychodow(double sumaprzychodow) {
        this.sumaprzychodow = sumaprzychodow;
    }

    public double getSumakosztow() {
        return sumakosztow;
    }

    public void setSumakosztow(double sumakosztow) {
        this.sumakosztow = sumakosztow;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }
    
    public List<Dokfk> getDokfkLista() {
        return dokfkLista;
    }
    
    public void setDokfkLista(List<Dokfk> dokfkLista) {
        this.dokfkLista = dokfkLista;
    }

    public int getCharaktercechy() {
        return charaktercechy;
    }

    public void setCharaktercechy(int charaktercechy) {
        this.charaktercechy = charaktercechy;
    }

    public int getPrzesuniecie() {
        return przesuniecie;
    }

    public void setPrzesuniecie(int przesuniecie) {
        this.przesuniecie = przesuniecie;
    }
    
    public List<StronaWiersza> getStronaWierszaLista() {
        return stronaWierszaLista;
    }
    
    public void setStronaWierszaLista(List<StronaWiersza> stronaWierszaLista) {
        this.stronaWierszaLista = stronaWierszaLista;
    }

    public String getNazwacechy() {
        return nazwacechy;
    }

    public void setNazwacechy(String nazwacechy) {
        this.nazwacechy = nazwacechy;
    }

    public String getRodzajcechy() {
        return rodzajcechy;
    }

    public void setRodzajcechy(String rodzajcechy) {
        this.rodzajcechy = rodzajcechy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public double getWynik() {
        return Z.z(this.sumaprzychodow-this.sumakosztow);
    }
    
//</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.nazwacechy);
        hash = 37 * hash + Objects.hashCode(this.rodzajcechy);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cechazapisu other = (Cechazapisu) obj;
        if (!Objects.equals(this.nazwacechy, other.nazwacechy)) {
            return false;
        }
        if (!Objects.equals(this.rodzajcechy, other.rodzajcechy)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cechazapisu{" + "nazwacechy=" + nazwacechy + ", rodzajcechy=" + rodzajcechy + '}';
    }

    
    
}
