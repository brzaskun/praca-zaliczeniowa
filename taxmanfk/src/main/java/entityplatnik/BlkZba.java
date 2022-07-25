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
@Table(name = "BLK_ZBA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlkZba.findAll", query = "SELECT b FROM BlkZba b"),
    @NamedQuery(name = "BlkZba.findByIdDokument", query = "SELECT b FROM BlkZba b WHERE b.idDokument = :idDokument"),
    @NamedQuery(name = "BlkZba.findByIdPlatnik", query = "SELECT b FROM BlkZba b WHERE b.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "BlkZba.findByIdDokNad", query = "SELECT b FROM BlkZba b WHERE b.idDokNad = :idDokNad"),
    @NamedQuery(name = "BlkZba.findByIii1Kodrodzzgl", query = "SELECT b FROM BlkZba b WHERE b.iii1Kodrodzzgl = :iii1Kodrodzzgl"),
    @NamedQuery(name = "BlkZba.findByIii2Nrrachunku", query = "SELECT b FROM BlkZba b WHERE b.iii2Nrrachunku = :iii2Nrrachunku"),
    @NamedQuery(name = "BlkZba.findByStatuswr", query = "SELECT b FROM BlkZba b WHERE b.statuswr = :statuswr"),
    @NamedQuery(name = "BlkZba.findByStatuspt", query = "SELECT b FROM BlkZba b WHERE b.statuspt = :statuspt"),
    @NamedQuery(name = "BlkZba.findByInserttmp", query = "SELECT b FROM BlkZba b WHERE b.inserttmp = :inserttmp"),
    @NamedQuery(name = "BlkZba.findByStatusKontroli", query = "SELECT b FROM BlkZba b WHERE b.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "BlkZba.findByNrBlok", query = "SELECT b FROM BlkZba b WHERE b.nrBlok = :nrBlok")})
public class BlkZba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOKUMENT", nullable = false)
    private Integer idDokument;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_DOK_NAD", nullable = false)
    private int idDokNad;
    @Column(name = "III_1_KODRODZZGL")
    private Character iii1Kodrodzzgl;
    @Size(max = 36)
    @Column(name = "III_2_NRRACHUNKU", length = 36)
    private String iii2Nrrachunku;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUSPT")
    private Character statuspt;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "STATUS_KONTROLI")
    private Character statusKontroli;
    @Column(name = "NR_BLOK")
    private Integer nrBlok;

    public BlkZba() {
    }

    public BlkZba(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public BlkZba(Integer idDokument, int idPlatnik, int idDokNad) {
        this.idDokument = idDokument;
        this.idPlatnik = idPlatnik;
        this.idDokNad = idDokNad;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public int getIdDokNad() {
        return idDokNad;
    }

    public void setIdDokNad(int idDokNad) {
        this.idDokNad = idDokNad;
    }

    public Character getIii1Kodrodzzgl() {
        return iii1Kodrodzzgl;
    }

    public void setIii1Kodrodzzgl(Character iii1Kodrodzzgl) {
        this.iii1Kodrodzzgl = iii1Kodrodzzgl;
    }

    public String getIii2Nrrachunku() {
        return iii2Nrrachunku;
    }

    public void setIii2Nrrachunku(String iii2Nrrachunku) {
        this.iii2Nrrachunku = iii2Nrrachunku;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatuspt() {
        return statuspt;
    }

    public void setStatuspt(Character statuspt) {
        this.statuspt = statuspt;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Character getStatusKontroli() {
        return statusKontroli;
    }

    public void setStatusKontroli(Character statusKontroli) {
        this.statusKontroli = statusKontroli;
    }

    public Integer getNrBlok() {
        return nrBlok;
    }

    public void setNrBlok(Integer nrBlok) {
        this.nrBlok = nrBlok;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDokument != null ? idDokument.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlkZba)) {
            return false;
        }
        BlkZba other = (BlkZba) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.BlkZba[ idDokument=" + idDokument + " ]";
    }
    
}
