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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "x")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "X.findAll", query = "SELECT x FROM X x"),
    @NamedQuery(name = "X.findById", query = "SELECT x FROM X x WHERE x.id = :id"),
    @NamedQuery(name = "X.findByHost", query = "SELECT x FROM X x WHERE x.host = :host"),
    @NamedQuery(name = "X.findByPort", query = "SELECT x FROM X x WHERE x.port = :port"),
    @NamedQuery(name = "X.findByTryb", query = "SELECT x FROM X x WHERE x.tryb = :tryb"),
    @NamedQuery(name = "X.findByAdres", query = "SELECT x FROM X x WHERE x.adres = :adres"),
    @NamedQuery(name = "X.findByPas", query = "SELECT x FROM X x WHERE x.pas = :pas")})
public class X implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 45)
    @Column(name = "host", length = 45)
    private String host;
    @Size(max = 45)
    @Column(name = "port", length = 45)
    private String port;
    @Size(max = 45)
    @Column(name = "tryb", length = 45)
    private String tryb;
    @Size(max = 90)
    @Column(name = "adres", length = 90)
    private String adres;
    @Size(max = 45)
    @Column(name = "pas", length = 45)
    private String pas;

    public X() {
    }

    public X(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTryb() {
        return tryb;
    }

    public void setTryb(String tryb) {
        this.tryb = tryb;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
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
        if (!(object instanceof X)) {
            return false;
        }
        X other = (X) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.X[ id=" + id + " ]";
    }
    
}
