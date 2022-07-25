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
@Table(name = "BLK_ZAA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlkZaa.findAll", query = "SELECT b FROM BlkZaa b"),
    @NamedQuery(name = "BlkZaa.findByIdDokument", query = "SELECT b FROM BlkZaa b WHERE b.idDokument = :idDokument"),
    @NamedQuery(name = "BlkZaa.findByIdPlatnik", query = "SELECT b FROM BlkZaa b WHERE b.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "BlkZaa.findByIdDokNad", query = "SELECT b FROM BlkZaa b WHERE b.idDokNad = :idDokNad"),
    @NamedQuery(name = "BlkZaa.findByIii1Kodrodzzgl2", query = "SELECT b FROM BlkZaa b WHERE b.iii1Kodrodzzgl2 = :iii1Kodrodzzgl2"),
    @NamedQuery(name = "BlkZaa.findByIii2Kodpocztowy", query = "SELECT b FROM BlkZaa b WHERE b.iii2Kodpocztowy = :iii2Kodpocztowy"),
    @NamedQuery(name = "BlkZaa.findByIii3Miejscowosc", query = "SELECT b FROM BlkZaa b WHERE b.iii3Miejscowosc = :iii3Miejscowosc"),
    @NamedQuery(name = "BlkZaa.findByIii4Gmina", query = "SELECT b FROM BlkZaa b WHERE b.iii4Gmina = :iii4Gmina"),
    @NamedQuery(name = "BlkZaa.findByIii5Ulica", query = "SELECT b FROM BlkZaa b WHERE b.iii5Ulica = :iii5Ulica"),
    @NamedQuery(name = "BlkZaa.findByIii6Numerdomu", query = "SELECT b FROM BlkZaa b WHERE b.iii6Numerdomu = :iii6Numerdomu"),
    @NamedQuery(name = "BlkZaa.findByIii7Numerlokalu", query = "SELECT b FROM BlkZaa b WHERE b.iii7Numerlokalu = :iii7Numerlokalu"),
    @NamedQuery(name = "BlkZaa.findByIii8Telefon", query = "SELECT b FROM BlkZaa b WHERE b.iii8Telefon = :iii8Telefon"),
    @NamedQuery(name = "BlkZaa.findByIii9Faks", query = "SELECT b FROM BlkZaa b WHERE b.iii9Faks = :iii9Faks"),
    @NamedQuery(name = "BlkZaa.findByStatuswr", query = "SELECT b FROM BlkZaa b WHERE b.statuswr = :statuswr"),
    @NamedQuery(name = "BlkZaa.findByStatuspt", query = "SELECT b FROM BlkZaa b WHERE b.statuspt = :statuspt"),
    @NamedQuery(name = "BlkZaa.findByInserttmp", query = "SELECT b FROM BlkZaa b WHERE b.inserttmp = :inserttmp"),
    @NamedQuery(name = "BlkZaa.findByStatusKontroli", query = "SELECT b FROM BlkZaa b WHERE b.statusKontroli = :statusKontroli"),
    @NamedQuery(name = "BlkZaa.findByNrBlok", query = "SELECT b FROM BlkZaa b WHERE b.nrBlok = :nrBlok")})
public class BlkZaa implements Serializable {

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
    @Column(name = "III_1_KODRODZZGL2")
    private Character iii1Kodrodzzgl2;
    @Size(max = 5)
    @Column(name = "III_2_KODPOCZTOWY", length = 5)
    private String iii2Kodpocztowy;
    @Size(max = 26)
    @Column(name = "III_3_MIEJSCOWOSC", length = 26)
    private String iii3Miejscowosc;
    @Size(max = 26)
    @Column(name = "III_4_GMINA", length = 26)
    private String iii4Gmina;
    @Size(max = 30)
    @Column(name = "III_5_ULICA", length = 30)
    private String iii5Ulica;
    @Size(max = 7)
    @Column(name = "III_6_NUMERDOMU", length = 7)
    private String iii6Numerdomu;
    @Size(max = 7)
    @Column(name = "III_7_NUMERLOKALU", length = 7)
    private String iii7Numerlokalu;
    @Size(max = 12)
    @Column(name = "III_8_TELEFON", length = 12)
    private String iii8Telefon;
    @Size(max = 12)
    @Column(name = "III_9_FAKS", length = 12)
    private String iii9Faks;
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

    public BlkZaa() {
    }

    public BlkZaa(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public BlkZaa(Integer idDokument, int idPlatnik, int idDokNad) {
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

    public Character getIii1Kodrodzzgl2() {
        return iii1Kodrodzzgl2;
    }

    public void setIii1Kodrodzzgl2(Character iii1Kodrodzzgl2) {
        this.iii1Kodrodzzgl2 = iii1Kodrodzzgl2;
    }

    public String getIii2Kodpocztowy() {
        return iii2Kodpocztowy;
    }

    public void setIii2Kodpocztowy(String iii2Kodpocztowy) {
        this.iii2Kodpocztowy = iii2Kodpocztowy;
    }

    public String getIii3Miejscowosc() {
        return iii3Miejscowosc;
    }

    public void setIii3Miejscowosc(String iii3Miejscowosc) {
        this.iii3Miejscowosc = iii3Miejscowosc;
    }

    public String getIii4Gmina() {
        return iii4Gmina;
    }

    public void setIii4Gmina(String iii4Gmina) {
        this.iii4Gmina = iii4Gmina;
    }

    public String getIii5Ulica() {
        return iii5Ulica;
    }

    public void setIii5Ulica(String iii5Ulica) {
        this.iii5Ulica = iii5Ulica;
    }

    public String getIii6Numerdomu() {
        return iii6Numerdomu;
    }

    public void setIii6Numerdomu(String iii6Numerdomu) {
        this.iii6Numerdomu = iii6Numerdomu;
    }

    public String getIii7Numerlokalu() {
        return iii7Numerlokalu;
    }

    public void setIii7Numerlokalu(String iii7Numerlokalu) {
        this.iii7Numerlokalu = iii7Numerlokalu;
    }

    public String getIii8Telefon() {
        return iii8Telefon;
    }

    public void setIii8Telefon(String iii8Telefon) {
        this.iii8Telefon = iii8Telefon;
    }

    public String getIii9Faks() {
        return iii9Faks;
    }

    public void setIii9Faks(String iii9Faks) {
        this.iii9Faks = iii9Faks;
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
        if (!(object instanceof BlkZaa)) {
            return false;
        }
        BlkZaa other = (BlkZaa) object;
        if ((this.idDokument == null && other.idDokument != null) || (this.idDokument != null && !this.idDokument.equals(other.idDokument))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.BlkZaa[ idDokument=" + idDokument + " ]";
    }
    
}
