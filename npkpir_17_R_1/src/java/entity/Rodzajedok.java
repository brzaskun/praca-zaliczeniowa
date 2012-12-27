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
@Table(name = "rodzajedok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rodzajedok.findAll", query = "SELECT r FROM Rodzajedok r"),
    @NamedQuery(name = "Rodzajedok.findBySkrot", query = "SELECT r FROM Rodzajedok r WHERE r.skrot = :skrot"),
    @NamedQuery(name = "Rodzajedok.findByNazwa", query = "SELECT r FROM Rodzajedok r WHERE r.nazwa = :nazwa"),
    @NamedQuery(name = "Rodzajedok.findByRodzajtransakcji", query = "SELECT r FROM Rodzajedok r WHERE r.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Rodzajedok.findByWzorzec", query = "SELECT r FROM Rodzajedok r WHERE r.wzorzec = :wzorzec")})
public class Rodzajedok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "skrot")
    private String skrot;
    @Size(max = 255)
    @Column(name = "nazwa")
    private String nazwa;
    @Size(max = 255)
    @Column(name = "rodzajtransakcji")
    private String rodzajtransakcji;
    @Size(max = 255)
    @Column(name = "wzorzec")
    private String wzorzec;
    @Column(name = "wybrany")
    private boolean wybrany;

    public Rodzajedok() {
    }

    public Rodzajedok(String skrot) {
        this.skrot = skrot;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getRodzajtransakcji() {
        return rodzajtransakcji;
    }

    public void setRodzajtransakcji(String rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }

    public String getWzorzec() {
        return wzorzec;
    }

    public void setWzorzec(String wzorzec) {
        this.wzorzec = wzorzec;
    }

    public boolean isWybrany() {
        return wybrany;
    }

    public void setWybrany(boolean wybrany) {
        this.wybrany = wybrany;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skrot != null ? skrot.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rodzajedok)) {
            return false;
        }
        Rodzajedok other = (Rodzajedok) object;
        if ((this.skrot == null && other.skrot != null) || (this.skrot != null && !this.skrot.equals(other.skrot))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rodzajedok[ skrot=" + skrot + " ]";
    }
    
}
