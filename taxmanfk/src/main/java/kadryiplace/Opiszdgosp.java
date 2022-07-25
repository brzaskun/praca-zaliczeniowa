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
@Table(name = "opiszdgosp", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opiszdgosp.findAll", query = "SELECT o FROM Opiszdgosp o"),
    @NamedQuery(name = "Opiszdgosp.findByOzgSerial", query = "SELECT o FROM Opiszdgosp o WHERE o.ozgSerial = :ozgSerial"),
    @NamedQuery(name = "Opiszdgosp.findByOzgOpis", query = "SELECT o FROM Opiszdgosp o WHERE o.ozgOpis = :ozgOpis"),
    @NamedQuery(name = "Opiszdgosp.findByOzgTyp", query = "SELECT o FROM Opiszdgosp o WHERE o.ozgTyp = :ozgTyp")})
public class Opiszdgosp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ozg_serial", nullable = false)
    private Integer ozgSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ozg_opis", nullable = false, length = 64)
    private String ozgOpis;
    @Column(name = "ozg_typ")
    private Character ozgTyp;
    @JoinColumn(name = "ozg_fir_serial", referencedColumnName = "fir_serial")
    @ManyToOne
    private Firma ozgFirSerial;

    public Opiszdgosp() {
    }

    public Opiszdgosp(Integer ozgSerial) {
        this.ozgSerial = ozgSerial;
    }

    public Opiszdgosp(Integer ozgSerial, String ozgOpis) {
        this.ozgSerial = ozgSerial;
        this.ozgOpis = ozgOpis;
    }

    public Integer getOzgSerial() {
        return ozgSerial;
    }

    public void setOzgSerial(Integer ozgSerial) {
        this.ozgSerial = ozgSerial;
    }

    public String getOzgOpis() {
        return ozgOpis;
    }

    public void setOzgOpis(String ozgOpis) {
        this.ozgOpis = ozgOpis;
    }

    public Character getOzgTyp() {
        return ozgTyp;
    }

    public void setOzgTyp(Character ozgTyp) {
        this.ozgTyp = ozgTyp;
    }

    public Firma getOzgFirSerial() {
        return ozgFirSerial;
    }

    public void setOzgFirSerial(Firma ozgFirSerial) {
        this.ozgFirSerial = ozgFirSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ozgSerial != null ? ozgSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opiszdgosp)) {
            return false;
        }
        Opiszdgosp other = (Opiszdgosp) object;
        if ((this.ozgSerial == null && other.ozgSerial != null) || (this.ozgSerial != null && !this.ozgSerial.equals(other.ozgSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Opiszdgosp[ ozgSerial=" + ozgSerial + " ]";
    }
    
}
