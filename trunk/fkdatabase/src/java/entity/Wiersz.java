/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(catalog = "fktest", schema = "")
@XmlRootElement
public class Wiersz implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idwiersza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String wiersznazwa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokument dokument;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wiersz", orphanRemoval = true)
    private Set<Rozrachunek> rozrachunki;
    

    public Wiersz() {
    }

    public Wiersz(String wiersznazwa) {
        this.rozrachunki = new HashSet<>();
        this.wiersznazwa = wiersznazwa;
        
    }

    public Integer getIdwiersza() {
        return idwiersza;
    }

    public void setIdwiersza(Integer idwiersza) {
        this.idwiersza = idwiersza;
    }

    public Rozrachunek getRozrachunekWn() {
        for (Rozrachunek p : this.rozrachunki) {
            if (p.getNazwarozrachunku().contains("Wn")) {
                return p;
            }
        }
        return null;
    }

    public Rozrachunek getRozrachunekMa() {
        for (Rozrachunek p : this.rozrachunki) {
            if (p.getNazwarozrachunku().contains("Ma")) {
                return p;
            }
        }
        return null;
    }

    public Set<Rozrachunek> getRozrachunki() {
        return rozrachunki;
    }

    public void setRozrachunki(Set<Rozrachunek> rozrachunki) {
        this.rozrachunki = rozrachunki;
    }
        
    public String getWiersznazwa() {
        return wiersznazwa;
    }

    public void setWiersznazwa(String wiersznazwa) {
        this.wiersznazwa = wiersznazwa;
    }

    public Dokument getDokument() {
        return dokument;
    }

    public void setDokument(Dokument dokument) {
        this.dokument = dokument;
    }

    @Override
    public String toString() {
        return "Wiersz{" + "idwiersza=" + idwiersza + ", wiersznazwa=" + wiersznazwa + '}';
    }

    
}
