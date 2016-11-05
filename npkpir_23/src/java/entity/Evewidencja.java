/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "evewidencja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evewidencja.findAll", query = "SELECT e FROM Evewidencja e"),
    @NamedQuery(name = "Evewidencja.findByNazwa", query = "SELECT e FROM Evewidencja e WHERE e.nazwa = :nazwa"),
    @NamedQuery(name = "Evewidencja.findByPole", query = "SELECT e FROM Evewidencja e WHERE e.pole = :pole"),
    @NamedQuery(name = "Evewidencja.findByNrpolanetto", query = "SELECT e FROM Evewidencja e WHERE e.nrpolanetto = :nrpolanetto"),
    @NamedQuery(name = "Evewidencja.findByNrpolavat", query = "SELECT e FROM Evewidencja e WHERE e.nrpolavat = :nrpolavat"),
    @NamedQuery(name = "Evewidencja.findByRodzajzakupu", query = "SELECT e FROM Evewidencja e WHERE e.rodzajzakupu = :rodzajzakupu"),
    @NamedQuery(name = "Evewidencja.findByTransakcja", query = "SELECT e FROM Evewidencja e WHERE e.transakcja = :transakcja"),
    @NamedQuery(name = "Evewidencja.findByTypEwidencji", query = "SELECT e FROM Evewidencja e WHERE e.typewidencji = :typewidencji"),
@NamedQuery(name = "Evewidencja.findByTylkoNetto", query = "SELECT e FROM Evewidencja e WHERE e.tylkoNetto = :tylkoNetto")})
public class Evewidencja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "pole")
    private String pole;
    @Size(max = 2)
    @Column(name = "nrpolanetto")
    private String nrpolanetto;
    @Size(max = 2)
    @Column(name = "nrpolavat")
    private String nrpolavat;
    @Size(max = 255)
    @Column(name = "rodzajzakupu")
    private String rodzajzakupu;
    @Size(max = 255)
    @Column(name = "transakcja")
    private String transakcja;
    @Column(name = "tylkoNetto")
    private boolean tylkoNetto;
    //s,z,sz
    @Column(name = "typewidencji")
    private String typewidencji;
    @Column(name = "de")
    private String de;
    @Column(name = "stawkavat")
    private double stawkavat;
//    @OneToMany(mappedBy = "ewidencja", cascade = CascadeType.ALL,  orphanRemoval=true)
//    private List<EVatwpis1> eVatwpisList;

    public Evewidencja() {
    }

    public Evewidencja(String nazwa, String pole, String nrpolanetto, String nrpolavat, String rodzajzakupu, String transakcja, boolean tylkoNetto) {
        this.nazwa = nazwa;
        this.pole = pole;
        this.nrpolanetto = nrpolanetto;
        this.nrpolavat = nrpolavat;
        this.rodzajzakupu = rodzajzakupu;
        this.transakcja = transakcja;
        this.tylkoNetto = tylkoNetto;
    }
    
    
    public Evewidencja(String nazwa, String pole, String nrpolanetto, String nrpolavat, String rodzajzakupu, String transakcja, boolean tylkoNetto, double stawkavat) {
        this.nazwa = nazwa;
        this.pole = pole;
        this.nrpolanetto = nrpolanetto;
        this.nrpolavat = nrpolavat;
        this.rodzajzakupu = rodzajzakupu;
        this.transakcja = transakcja;
        this.tylkoNetto = tylkoNetto;
        this.stawkavat = stawkavat;
    }

       

    public Evewidencja(String nazwa) {
        this.nazwa = nazwa;
    }

    public Evewidencja(String nazwa, boolean tylkoNetto) {
        this.nazwa = nazwa;
        this.tylkoNetto = tylkoNetto;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public String getNrpolanetto() {
        return nrpolanetto;
    }

    public void setNrpolanetto(String nrpolanetto) {
        this.nrpolanetto = nrpolanetto;
    }

    public String getNrpolavat() {
        return nrpolavat;
    }

    public void setNrpolavat(String nrpolavat) {
        this.nrpolavat = nrpolavat;
    }

    public String getRodzajzakupu() {
        return rodzajzakupu;
    }

    public void setRodzajzakupu(String rodzajzakupu) {
        this.rodzajzakupu = rodzajzakupu;
    }

    public String getTransakcja() {
        return transakcja;
    }

    public void setTransakcja(String transakcja) {
        this.transakcja = transakcja;
    }

    public boolean isTylkoNetto() {
        return tylkoNetto;
    }

    public void setTylkoNetto(boolean tylkoNetto) {
        this.tylkoNetto = tylkoNetto;
    }

    public String getTypewidencji() {
        return typewidencji;
    }

    public void setTypewidencji(String typewidencji) {
        this.typewidencji = typewidencji;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public double getStawkavat() {
        return stawkavat;
    }

    public void setStawkavat(double stawkavat) {
        this.stawkavat = stawkavat;
    }

     
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nazwa != null ? nazwa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evewidencja)) {
            return false;
        }
        Evewidencja other = (Evewidencja) object;
        if ((this.nazwa == null && other.nazwa != null) || (this.nazwa != null && !this.nazwa.equals(other.nazwa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evewidencja{" + "nazwa=" + nazwa + ", pole=" + pole + ", nrpolanetto=" + nrpolanetto + ", nrpolavat=" + nrpolavat + ", rodzajzakupu=" + rodzajzakupu + ", transakcja=" + transakcja + ", tylkoNetto=" + tylkoNetto + ", typewidencji=" + typewidencji + ", de=" + de + ", stawkavat=" + stawkavat + '}';
    }

   
    
   
    
}
