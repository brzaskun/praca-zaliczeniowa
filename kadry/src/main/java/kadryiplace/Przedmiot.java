/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "przedmiot", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Przedmiot.findAll", query = "SELECT p FROM Przedmiot p"),
    @NamedQuery(name = "Przedmiot.findByPrzSerial", query = "SELECT p FROM Przedmiot p WHERE p.przSerial = :przSerial"),
    @NamedQuery(name = "Przedmiot.findByPrzOpis", query = "SELECT p FROM Przedmiot p WHERE p.przOpis = :przOpis"),
    @NamedQuery(name = "Przedmiot.findByPrzEwidencja", query = "SELECT p FROM Przedmiot p WHERE p.przEwidencja = :przEwidencja")})
public class Przedmiot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "prz_serial", nullable = false)
    private Integer przSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "prz_opis", nullable = false, length = 64)
    private String przOpis;
    @Column(name = "prz_ewidencja")
    private Character przEwidencja;
    @JoinColumn(name = "prz_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma przFirSerial;

    public Przedmiot() {
    }

    public Przedmiot(Integer przSerial) {
        this.przSerial = przSerial;
    }

    public Przedmiot(Integer przSerial, String przOpis) {
        this.przSerial = przSerial;
        this.przOpis = przOpis;
    }

    public Integer getPrzSerial() {
        return przSerial;
    }

    public void setPrzSerial(Integer przSerial) {
        this.przSerial = przSerial;
    }

    public String getPrzOpis() {
        return przOpis;
    }

    public void setPrzOpis(String przOpis) {
        this.przOpis = przOpis;
    }

    public Character getPrzEwidencja() {
        return przEwidencja;
    }

    public void setPrzEwidencja(Character przEwidencja) {
        this.przEwidencja = przEwidencja;
    }

    public Firma getPrzFirSerial() {
        return przFirSerial;
    }

    public void setPrzFirSerial(Firma przFirSerial) {
        this.przFirSerial = przFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (przSerial != null ? przSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Przedmiot)) {
            return false;
        }
        Przedmiot other = (Przedmiot) object;
        if ((this.przSerial == null && other.przSerial != null) || (this.przSerial != null && !this.przSerial.equals(other.przSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Przedmiot[ przSerial=" + przSerial + " ]";
    }
    
}
