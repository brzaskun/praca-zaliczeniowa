/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "BLAD_PRZETW")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BladPrzetw.findAll", query = "SELECT b FROM BladPrzetw b"),
    @NamedQuery(name = "BladPrzetw.findById", query = "SELECT b FROM BladPrzetw b WHERE b.id = :id"),
    @NamedQuery(name = "BladPrzetw.findByIdPrzesylki", query = "SELECT b FROM BladPrzetw b WHERE b.idPrzesylki = :idPrzesylki"),
    @NamedQuery(name = "BladPrzetw.findByIdDokument", query = "SELECT b FROM BladPrzetw b WHERE b.idDokument = :idDokument"),
    @NamedQuery(name = "BladPrzetw.findByKodBledu", query = "SELECT b FROM BladPrzetw b WHERE b.kodBledu = :kodBledu"),
    @NamedQuery(name = "BladPrzetw.findByNrPozycji", query = "SELECT b FROM BladPrzetw b WHERE b.nrPozycji = :nrPozycji"),
    @NamedQuery(name = "BladPrzetw.findByInserttmp", query = "SELECT b FROM BladPrzetw b WHERE b.inserttmp = :inserttmp")})
public class BladPrzetw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ID_PRZESYLKI", nullable = false, length = 40)
    private String idPrzesylki;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private int idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "KOD_BLEDU", nullable = false)
    private int kodBledu;
    @Column(name = "NR_POZYCJI")
    private Integer nrPozycji;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public BladPrzetw() {
    }

    public BladPrzetw(Integer id) {
        this.id = id;
    }

    public BladPrzetw(Integer id, String idPrzesylki, int idDokument, int kodBledu) {
        this.id = id;
        this.idPrzesylki = idPrzesylki;
        this.idDokument = idDokument;
        this.kodBledu = kodBledu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdPrzesylki() {
        return idPrzesylki;
    }

    public void setIdPrzesylki(String idPrzesylki) {
        this.idPrzesylki = idPrzesylki;
    }

    public int getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(int idDokument) {
        this.idDokument = idDokument;
    }

    public int getKodBledu() {
        return kodBledu;
    }

    public void setKodBledu(int kodBledu) {
        this.kodBledu = kodBledu;
    }

    public Integer getNrPozycji() {
        return nrPozycji;
    }

    public void setNrPozycji(Integer nrPozycji) {
        this.nrPozycji = nrPozycji;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
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
        if (!(object instanceof BladPrzetw)) {
            return false;
        }
        BladPrzetw other = (BladPrzetw) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.BladPrzetw[ id=" + id + " ]";
    }
    
}
