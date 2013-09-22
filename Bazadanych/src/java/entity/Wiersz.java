/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(catalog = "bazatest", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Wiersz.findAll", query = "SELECT w FROM Wiersz w"),
    @NamedQuery(name = "Wiersz.findByIdwiersza", query = "SELECT w FROM Wiersz w WHERE w.idwiersza = :idwiersza"),
    @NamedQuery(name = "Wiersz.findByOpiswiersza", query = "SELECT w FROM Wiersz w WHERE w.opiswiersza = :opiswiersza")})
public class Wiersz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idwiersza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String opiswiersza;
    @JoinColumn(name = "iddokumentuobcy", referencedColumnName = "iddok", nullable = false)
    @ManyToOne(optional = false)
    private Dokfk iddokumentuobcy;

    public Wiersz() {
    }

    public Wiersz(Integer idwiersza) {
        this.idwiersza = idwiersza;
    }

    public Wiersz(Integer idwiersza, String opiswiersza) {
        this.idwiersza = idwiersza;
        this.opiswiersza = opiswiersza;
    }

    public Integer getIdwiersza() {
        return idwiersza;
    }

    public void setIdwiersza(Integer idwiersza) {
        this.idwiersza = idwiersza;
    }

    public String getOpiswiersza() {
        return opiswiersza;
    }

    public void setOpiswiersza(String opiswiersza) {
        this.opiswiersza = opiswiersza;
    }

    public Dokfk getIddokumentuobcy() {
        return iddokumentuobcy;
    }

    public void setIddokumentuobcy(Dokfk iddokumentuobcy) {
        this.iddokumentuobcy = iddokumentuobcy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idwiersza != null ? idwiersza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wiersz)) {
            return false;
        }
        Wiersz other = (Wiersz) object;
        if ((this.idwiersza == null && other.idwiersza != null) || (this.idwiersza != null && !this.idwiersza.equals(other.idwiersza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Wiersz[ idwiersza=" + idwiersza + " ]";
    }
    
}
