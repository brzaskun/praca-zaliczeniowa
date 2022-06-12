/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ocena_grup", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OcenaGrup.findAll", query = "SELECT o FROM OcenaGrup o"),
    @NamedQuery(name = "OcenaGrup.findByOcgSerial", query = "SELECT o FROM OcenaGrup o WHERE o.ocgSerial = :ocgSerial"),
    @NamedQuery(name = "OcenaGrup.findByOcgNumOd", query = "SELECT o FROM OcenaGrup o WHERE o.ocgNumOd = :ocgNumOd"),
    @NamedQuery(name = "OcenaGrup.findByOcgNumDo", query = "SELECT o FROM OcenaGrup o WHERE o.ocgNumDo = :ocgNumDo"),
    @NamedQuery(name = "OcenaGrup.findByOcgNazwa", query = "SELECT o FROM OcenaGrup o WHERE o.ocgNazwa = :ocgNazwa"),
    @NamedQuery(name = "OcenaGrup.findByOcgTyp", query = "SELECT o FROM OcenaGrup o WHERE o.ocgTyp = :ocgTyp")})
public class OcenaGrup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocg_serial", nullable = false)
    private Integer ocgSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocg_num_od", nullable = false, precision = 17, scale = 6)
    private BigDecimal ocgNumOd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ocg_num_do", nullable = false, precision = 17, scale = 6)
    private BigDecimal ocgNumDo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "ocg_nazwa", nullable = false, length = 32)
    private String ocgNazwa;
    @Column(name = "ocg_typ")
    private Character ocgTyp;

    public OcenaGrup() {
    }

    public OcenaGrup(Integer ocgSerial) {
        this.ocgSerial = ocgSerial;
    }

    public OcenaGrup(Integer ocgSerial, BigDecimal ocgNumOd, BigDecimal ocgNumDo, String ocgNazwa) {
        this.ocgSerial = ocgSerial;
        this.ocgNumOd = ocgNumOd;
        this.ocgNumDo = ocgNumDo;
        this.ocgNazwa = ocgNazwa;
    }

    public Integer getOcgSerial() {
        return ocgSerial;
    }

    public void setOcgSerial(Integer ocgSerial) {
        this.ocgSerial = ocgSerial;
    }

    public BigDecimal getOcgNumOd() {
        return ocgNumOd;
    }

    public void setOcgNumOd(BigDecimal ocgNumOd) {
        this.ocgNumOd = ocgNumOd;
    }

    public BigDecimal getOcgNumDo() {
        return ocgNumDo;
    }

    public void setOcgNumDo(BigDecimal ocgNumDo) {
        this.ocgNumDo = ocgNumDo;
    }

    public String getOcgNazwa() {
        return ocgNazwa;
    }

    public void setOcgNazwa(String ocgNazwa) {
        this.ocgNazwa = ocgNazwa;
    }

    public Character getOcgTyp() {
        return ocgTyp;
    }

    public void setOcgTyp(Character ocgTyp) {
        this.ocgTyp = ocgTyp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ocgSerial != null ? ocgSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OcenaGrup)) {
            return false;
        }
        OcenaGrup other = (OcenaGrup) object;
        if ((this.ocgSerial == null && other.ocgSerial != null) || (this.ocgSerial != null && !this.ocgSerial.equals(other.ocgSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.OcenaGrup[ ocgSerial=" + ocgSerial + " ]";
    }
    
}
