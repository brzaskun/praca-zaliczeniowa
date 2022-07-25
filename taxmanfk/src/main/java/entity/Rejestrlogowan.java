/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rejestrlogowan.findAll", query = "SELECT r FROM Rejestrlogowan r"),
    @NamedQuery(name = "Rejestrlogowan.findByIpusera", query = "SELECT r FROM Rejestrlogowan r WHERE r.ipusera = :ipusera"),
    @NamedQuery(name = "Rejestrlogowan.findByDatalogowania", query = "SELECT r FROM Rejestrlogowan r WHERE r.datalogowania = :datalogowania"),
    @NamedQuery(name = "Rejestrlogowan.findByIlosclogowan", query = "SELECT r FROM Rejestrlogowan r WHERE r.ilosclogowan = :ilosclogowan"),
    @NamedQuery(name = "Rejestrlogowan.findByBlokada", query = "SELECT r FROM Rejestrlogowan r WHERE r.blokada = :blokada"),
    @NamedQuery(name = "Rejestrlogowan.findByUstawIloscLogowan", query = "UPDATE Rejestrlogowan r SET r.ilosclogowan = :ilosclogowan WHERE r.ipusera = :ipusera"),
    @NamedQuery(name = "Rejestrlogowan.findByIloscLogowan0", query = "SELECT r FROM Rejestrlogowan r WHERE r.ilosclogowan = 0")
})
public class Rejestrlogowan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(nullable = false, length = 15)
    private String ipusera;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datalogowania;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int ilosclogowan;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean blokada;

    public Rejestrlogowan() {
    }

    public Rejestrlogowan(String ipusera) {
        this.ipusera = ipusera;
    }

    public Rejestrlogowan(String ipusera, Date datalogowania, int ilosclogowan, boolean blokada) {
        this.ipusera = ipusera;
        this.datalogowania = datalogowania;
        this.ilosclogowan = ilosclogowan;
        this.blokada = blokada;
    }

    public String getIpusera() {
        return ipusera;
    }

    public void setIpusera(String ipusera) {
        this.ipusera = ipusera;
    }

    public Date getDatalogowania() {
        return datalogowania;
    }

    public void setDatalogowania(Date datalogowania) {
        this.datalogowania = datalogowania;
    }

    public int getIlosclogowan() {
        return ilosclogowan;
    }

    public void setIlosclogowan(int ilosclogowan) {
        this.ilosclogowan = ilosclogowan;
    }

    public boolean getBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipusera != null ? ipusera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rejestrlogowan)) {
            return false;
        }
        Rejestrlogowan other = (Rejestrlogowan) object;
        if ((this.ipusera == null && other.ipusera != null) || (this.ipusera != null && !this.ipusera.equals(other.ipusera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Rejestrlogowan[ ipusera=" + ipusera + " ]";
    }
    
}
