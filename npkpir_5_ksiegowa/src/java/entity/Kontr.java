/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author School
 */
@Entity
@Table(name = "kontr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kontr.findAll", query = "SELECT k FROM Kontr k"),
    @NamedQuery(name = "Kontr.findByNip", query = "SELECT k FROM Kontr k WHERE k.nip = :nip"),
    @NamedQuery(name = "Kontr.findByNpelna", query = "SELECT k FROM Kontr k WHERE k.npelna = :npelna"),
    @NamedQuery(name = "Kontr.findBySkrot", query = "SELECT k FROM Kontr k WHERE k.skrot = :skrot"),
    @NamedQuery(name = "Kontr.findByKod", query = "SELECT k FROM Kontr k WHERE k.kod = :kod"),
    @NamedQuery(name = "Kontr.findByMiasto", query = "SELECT k FROM Kontr k WHERE k.miasto = :miasto"),
    @NamedQuery(name = "Kontr.findByUlica", query = "SELECT k FROM Kontr k WHERE k.ulica = :ulica")})
public class Kontr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "nip")
    private String nip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "npelna")
    private String npelna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "skrot")
    private String skrot;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "kod")
    private String kod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "miasto")
    private String miasto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ulica")
    private String ulica;

    public Kontr() {
    }

    public Kontr(String nip) {
        this.nip = nip;
    }

    public Kontr(String nip, String npelna, String skrot, String kod, String miasto, String ulica) {
        this.nip = nip;
        this.npelna = npelna;
        this.skrot = skrot;
        this.kod = kod;
        this.miasto = miasto;
        this.ulica = ulica;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNpelna() {
        return npelna;
    }

    public void setNpelna(String npelna) {
        this.npelna = npelna;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nip != null ? nip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        System.out.println("dostalem object"+object.toString());
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kontr)) {
            return false;
        }
        Kontr other = (Kontr) object;
        System.out.println("porownuje obiekty"+this.nip+" "+other.nip);
        if ((this.nip == null && other.nip != null) || (this.nip != null && !this.nip.equals(other.nip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Kontr[ nip=" + nip + " ]";
    }
    
}
