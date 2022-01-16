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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "zakladpracy", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nazwazakladu"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zakladpracy.findAll", query = "SELECT z FROM Zakladpracy z"),
    @NamedQuery(name = "Zakladpracy.findById", query = "SELECT z FROM Zakladpracy z WHERE z.id = :id"),
    @NamedQuery(name = "Zakladpracy.findByNazwazakladu", query = "SELECT z FROM Zakladpracy z WHERE z.nazwazakladu = :nazwazakladu"),
    @NamedQuery(name = "Zakladpracy.findBySymbol", query = "SELECT z FROM Zakladpracy z WHERE z.symbol = :symbol"),
    @NamedQuery(name = "Zakladpracy.findByUlica", query = "SELECT z FROM Zakladpracy z WHERE z.ulica = :ulica"),
    @NamedQuery(name = "Zakladpracy.findByMiejscowosc", query = "SELECT z FROM Zakladpracy z WHERE z.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "Zakladpracy.findByFirmaaktywna", query = "SELECT z FROM Zakladpracy z WHERE z.firmaaktywna = :firmaaktywna"),
    @NamedQuery(name = "Zakladpracy.findByProgzdawalnosci", query = "SELECT z FROM Zakladpracy z WHERE z.progzdawalnosci = :progzdawalnosci"),
    @NamedQuery(name = "Zakladpracy.findByKontakt", query = "SELECT z FROM Zakladpracy z WHERE z.kontakt = :kontakt"),
    @NamedQuery(name = "Zakladpracy.findByMaxpracownikow", query = "SELECT z FROM Zakladpracy z WHERE z.maxpracownikow = :maxpracownikow"),
    @NamedQuery(name = "Zakladpracy.findByManagerlimit", query = "SELECT z FROM Zakladpracy z WHERE z.managerlimit = :managerlimit"),
    @NamedQuery(name = "Zakladpracy.findByEmail", query = "SELECT z FROM Zakladpracy z WHERE z.email = :email")})
public class Zakladpracy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "nazwazakladu", length = 255)
    private String nazwazakladu;
    @Size(max = 90)
    @Column(name = "symbol", length = 90)
    private String symbol;
    @Size(max = 255)
    @Column(name = "ulica", length = 255)
    private String ulica;
    @Size(max = 255)
    @Column(name = "miejscowosc", length = 255)
    private String miejscowosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "firmaaktywna", nullable = false)
    private boolean firmaaktywna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "progzdawalnosci", nullable = false)
    private int progzdawalnosci;
    @Size(max = 255)
    @Column(name = "kontakt", length = 255)
    private String kontakt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxpracownikow", nullable = false)
    private int maxpracownikow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "managerlimit", nullable = false)
    private int managerlimit;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email", length = 255)
    private String email;

    public Zakladpracy() {
    }

    public Zakladpracy(Integer id) {
        this.id = id;
    }

    public Zakladpracy(Integer id, boolean firmaaktywna, int progzdawalnosci, int maxpracownikow, int managerlimit) {
        this.id = id;
        this.firmaaktywna = firmaaktywna;
        this.progzdawalnosci = progzdawalnosci;
        this.maxpracownikow = maxpracownikow;
        this.managerlimit = managerlimit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwazakladu() {
        return nazwazakladu;
    }

    public void setNazwazakladu(String nazwazakladu) {
        this.nazwazakladu = nazwazakladu;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public boolean getFirmaaktywna() {
        return firmaaktywna;
    }

    public void setFirmaaktywna(boolean firmaaktywna) {
        this.firmaaktywna = firmaaktywna;
    }

    public int getProgzdawalnosci() {
        return progzdawalnosci;
    }

    public void setProgzdawalnosci(int progzdawalnosci) {
        this.progzdawalnosci = progzdawalnosci;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public int getMaxpracownikow() {
        return maxpracownikow;
    }

    public void setMaxpracownikow(int maxpracownikow) {
        this.maxpracownikow = maxpracownikow;
    }

    public int getManagerlimit() {
        return managerlimit;
    }

    public void setManagerlimit(int managerlimit) {
        this.managerlimit = managerlimit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(object instanceof Zakladpracy)) {
            return false;
        }
        Zakladpracy other = (Zakladpracy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Zakladpracy[ id=" + id + " ]";
    }
    
}
