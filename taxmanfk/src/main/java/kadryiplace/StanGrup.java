/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "stan_grup", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StanGrup.findAll", query = "SELECT s FROM StanGrup s"),
    @NamedQuery(name = "StanGrup.findByStgSerial", query = "SELECT s FROM StanGrup s WHERE s.stgSerial = :stgSerial"),
    @NamedQuery(name = "StanGrup.findByStgKod", query = "SELECT s FROM StanGrup s WHERE s.stgKod = :stgKod"),
    @NamedQuery(name = "StanGrup.findByStgNazwa", query = "SELECT s FROM StanGrup s WHERE s.stgNazwa = :stgNazwa")})
public class StanGrup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "stg_serial", nullable = false)
    private Integer stgSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stg_kod", nullable = false)
    private short stgKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "stg_nazwa", nullable = false, length = 64)
    private String stgNazwa;
    @JoinColumn(name = "stg_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma stgFirSerial;
    @OneToMany(mappedBy = "stpStgSerial")
    private List<StanPodgrup> stanPodgrupList;

    public StanGrup() {
    }

    public StanGrup(Integer stgSerial) {
        this.stgSerial = stgSerial;
    }

    public StanGrup(Integer stgSerial, short stgKod, String stgNazwa) {
        this.stgSerial = stgSerial;
        this.stgKod = stgKod;
        this.stgNazwa = stgNazwa;
    }

    public Integer getStgSerial() {
        return stgSerial;
    }

    public void setStgSerial(Integer stgSerial) {
        this.stgSerial = stgSerial;
    }

    public short getStgKod() {
        return stgKod;
    }

    public void setStgKod(short stgKod) {
        this.stgKod = stgKod;
    }

    public String getStgNazwa() {
        return stgNazwa;
    }

    public void setStgNazwa(String stgNazwa) {
        this.stgNazwa = stgNazwa;
    }

    public Firma getStgFirSerial() {
        return stgFirSerial;
    }

    public void setStgFirSerial(Firma stgFirSerial) {
        this.stgFirSerial = stgFirSerial;
    }

    @XmlTransient
    public List<StanPodgrup> getStanPodgrupList() {
        return stanPodgrupList;
    }

    public void setStanPodgrupList(List<StanPodgrup> stanPodgrupList) {
        this.stanPodgrupList = stanPodgrupList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stgSerial != null ? stgSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StanGrup)) {
            return false;
        }
        StanGrup other = (StanGrup) object;
        if ((this.stgSerial == null && other.stgSerial != null) || (this.stgSerial != null && !this.stgSerial.equals(other.stgSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.StanGrup[ stgSerial=" + stgSerial + " ]";
    }
    
}
