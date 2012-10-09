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
@Table(name = "uz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uz.findAll", query = "SELECT u FROM Uz u"),
    @NamedQuery(name = "Uz.findByNazw", query = "SELECT u FROM Uz u WHERE u.nazw = :nazw"),
    @NamedQuery(name = "Uz.findByImie", query = "SELECT u FROM Uz u WHERE u.imie = :imie"),
    @NamedQuery(name = "Uz.findByLogi", query = "SELECT u FROM Uz u WHERE u.logi = :logi"),
    @NamedQuery(name = "Uz.findByHaslo", query = "SELECT u FROM Uz u WHERE u.haslo = :haslo"),
    @NamedQuery(name = "Uz.findByAdrma", query = "SELECT u FROM Uz u WHERE u.adrma = :adrma"),
    @NamedQuery(name = "Uz.findByLog", query = "SELECT u FROM Uz u WHERE u.log = :log"),
    @NamedQuery(name = "Uz.findByUpr", query = "SELECT u FROM Uz u WHERE u.upr = :upr")})
public class Uz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "imie")
    private String imie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nazw")
    private String nazw;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "logi")
    private String logi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "haslo")
    private String haslo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "adrma")
    private String adrma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "log")
    private String log;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "upr")
    private String upr;

    public Uz() {
    }

  
    public Uz(String imie, String nazw, String logi, String haslo, String adrma, String log, String upr) {
        this.imie = imie;
        this.nazw = nazw;
        this.logi = logi;
        this.haslo = haslo;
        this.adrma = adrma;
        this.log = log;
        this.upr = upr;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazw() {
        return nazw;
    }

    public void setNazw(String nazw) {
        this.nazw = nazw;
    }

    public String getLogi() {
        return logi;
    }

    public void setLogi(String logi) {
        this.logi = logi;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getAdrma() {
        return adrma;
    }

    public void setAdrma(String adrma) {
        this.adrma = adrma;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getUpr() {
        return upr;
    }

    public void setUpr(String upr) {
        this.upr = upr;
    }

   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.logi != null ? this.logi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Uz other = (Uz) obj;
        if ((this.logi == null) ? (other.logi != null) : !this.logi.equals(other.logi)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  logi ;
    }

    

   
    
}
