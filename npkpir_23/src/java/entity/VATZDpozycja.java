/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Dokfk;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "VATZDpozycja", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dok", "seriadokfk", "nrkolejnywserii","podatnikObj","rok","deklaracjavat"})}
)
public class VATZDpozycja implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "dok", referencedColumnName = "id_dok")
    @OneToOne(cascade = {CascadeType.ALL})
    private Dok dok;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokfk dokfk;
    @JoinColumn(name = "deklaracjevat", referencedColumnName = "id")
    @OneToOne(cascade = {CascadeType.ALL})
    private Deklaracjevat deklaracjavat;
    @Size(max = 4)
    @Column(name = "rokZD")
    private String rokZD;
    @Size(max = 2)
    @Column(name = "mcZD")
    private String mcZD;
    @Size(max = 10)
    @Column(name = "terminplatnosci")//dd-mm-yyyy
    private String terminplatnosci;
    @Column(name = "korektapodstawa")
    private double korektapodstawa;
    @Column(name = "korektapodatek")
    private double korektapodatek;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.dok);
        hash = 53 * hash + Objects.hashCode(this.dokfk);
        hash = 53 * hash + Objects.hashCode(this.deklaracjavat);
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
        final VATZDpozycja other = (VATZDpozycja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dok, other.dok)) {
            return false;
        }
        if (!Objects.equals(this.dokfk, other.dokfk)) {
            return false;
        }
        if (!Objects.equals(this.deklaracjavat, other.deklaracjavat)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VATZDpozycja{" + "dok=" + dok + ", dokfk=" + dokfk + ", deklaracjevat=" + deklaracjavat + '}';
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dok getDok() {
        return dok;
    }

    public void setDok(Dok dok) {
        this.dok = dok;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    public Deklaracjevat getDeklaracjavat() {
        return deklaracjavat;
    }

    public void setDeklaracjavat(Deklaracjevat deklaracjavat) {
        this.deklaracjavat = deklaracjavat;
    }

    public String getRokZD() {
        return rokZD;
    }

    public void setRokZD(String rokZD) {
        this.rokZD = rokZD;
    }

    public String getMcZD() {
        return mcZD;
    }

    public void setMcZD(String mcZD) {
        this.mcZD = mcZD;
    }

    public String getTerminplatnosci() {
        return terminplatnosci;
    }

    public void setTerminplatnosci(String terminplatnosci) {
        this.terminplatnosci = terminplatnosci;
    }

    public double getKorektapodstawa() {
        return korektapodstawa;
    }

    public void setKorektapodstawa(double korektapodstawa) {
        this.korektapodstawa = korektapodstawa;
    }

    public double getKorektapodatek() {
        return korektapodatek;
    }

    public void setKorektapodatek(double korektapodatek) {
        this.korektapodatek = korektapodatek;
    }
    
    
    
}
