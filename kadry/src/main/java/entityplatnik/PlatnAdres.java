/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityplatnik;

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
@Table(name = "PLATN_ADRES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlatnAdres.findAll", query = "SELECT p FROM PlatnAdres p"),
    @NamedQuery(name = "PlatnAdres.findById", query = "SELECT p FROM PlatnAdres p WHERE p.id = :id"),
    @NamedQuery(name = "PlatnAdres.findByIdPlatnik", query = "SELECT p FROM PlatnAdres p WHERE p.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "PlatnAdres.findByIdDokument", query = "SELECT p FROM PlatnAdres p WHERE p.idDokument = :idDokument"),
    @NamedQuery(name = "PlatnAdres.findByTypAdresu", query = "SELECT p FROM PlatnAdres p WHERE p.typAdresu = :typAdresu"),
    @NamedQuery(name = "PlatnAdres.findByKodpocztowy", query = "SELECT p FROM PlatnAdres p WHERE p.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "PlatnAdres.findByMiejscowosc", query = "SELECT p FROM PlatnAdres p WHERE p.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "PlatnAdres.findByGmina", query = "SELECT p FROM PlatnAdres p WHERE p.gmina = :gmina"),
    @NamedQuery(name = "PlatnAdres.findByUlica", query = "SELECT p FROM PlatnAdres p WHERE p.ulica = :ulica"),
    @NamedQuery(name = "PlatnAdres.findByNumerdomu", query = "SELECT p FROM PlatnAdres p WHERE p.numerdomu = :numerdomu"),
    @NamedQuery(name = "PlatnAdres.findByNumerlokalu", query = "SELECT p FROM PlatnAdres p WHERE p.numerlokalu = :numerlokalu"),
    @NamedQuery(name = "PlatnAdres.findBySkrpocztowa", query = "SELECT p FROM PlatnAdres p WHERE p.skrpocztowa = :skrpocztowa"),
    @NamedQuery(name = "PlatnAdres.findByTelefon", query = "SELECT p FROM PlatnAdres p WHERE p.telefon = :telefon"),
    @NamedQuery(name = "PlatnAdres.findByTeldoteletr", query = "SELECT p FROM PlatnAdres p WHERE p.teldoteletr = :teldoteletr"),
    @NamedQuery(name = "PlatnAdres.findByFaks", query = "SELECT p FROM PlatnAdres p WHERE p.faks = :faks"),
    @NamedQuery(name = "PlatnAdres.findByAdrpocztyel", query = "SELECT p FROM PlatnAdres p WHERE p.adrpocztyel = :adrpocztyel"),
    @NamedQuery(name = "PlatnAdres.findByWojewodztwo", query = "SELECT p FROM PlatnAdres p WHERE p.wojewodztwo = :wojewodztwo"),
    @NamedQuery(name = "PlatnAdres.findByStatusDane", query = "SELECT p FROM PlatnAdres p WHERE p.statusDane = :statusDane"),
    @NamedQuery(name = "PlatnAdres.findByDataod", query = "SELECT p FROM PlatnAdres p WHERE p.dataod = :dataod"),
    @NamedQuery(name = "PlatnAdres.findByInserttmp", query = "SELECT p FROM PlatnAdres p WHERE p.inserttmp = :inserttmp"),
    @NamedQuery(name = "PlatnAdres.findByIdPlZus", query = "SELECT p FROM PlatnAdres p WHERE p.idPlZus = :idPlZus")})
public class PlatnAdres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLATNIK", nullable = false)
    private int idPlatnik;
    @Column(name = "ID_DOKUMENT")
    private Integer idDokument;
    @Column(name = "TYP_ADRESU")
    private Character typAdresu;
    @Size(max = 5)
    @Column(name = "KODPOCZTOWY", length = 5)
    private String kodpocztowy;
    @Size(max = 26)
    @Column(name = "MIEJSCOWOSC", length = 26)
    private String miejscowosc;
    @Size(max = 26)
    @Column(name = "GMINA", length = 26)
    private String gmina;
    @Size(max = 30)
    @Column(name = "ULICA", length = 30)
    private String ulica;
    @Size(max = 7)
    @Column(name = "NUMERDOMU", length = 7)
    private String numerdomu;
    @Size(max = 7)
    @Column(name = "NUMERLOKALU", length = 7)
    private String numerlokalu;
    @Size(max = 5)
    @Column(name = "SKRPOCZTOWA", length = 5)
    private String skrpocztowa;
    @Size(max = 12)
    @Column(name = "TELEFON", length = 12)
    private String telefon;
    @Size(max = 12)
    @Column(name = "TELDOTELETR", length = 12)
    private String teldoteletr;
    @Size(max = 12)
    @Column(name = "FAKS", length = 12)
    private String faks;
    @Size(max = 30)
    @Column(name = "ADRPOCZTYEL", length = 30)
    private String adrpocztyel;
    @Size(max = 26)
    @Column(name = "WOJEWODZTWO", length = 26)
    private String wojewodztwo;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;

    public PlatnAdres() {
    }

    public PlatnAdres(Integer id) {
        this.id = id;
    }

    public PlatnAdres(Integer id, int idPlatnik) {
        this.id = id;
        this.idPlatnik = idPlatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPlatnik() {
        return idPlatnik;
    }

    public void setIdPlatnik(int idPlatnik) {
        this.idPlatnik = idPlatnik;
    }

    public Integer getIdDokument() {
        return idDokument;
    }

    public void setIdDokument(Integer idDokument) {
        this.idDokument = idDokument;
    }

    public Character getTypAdresu() {
        return typAdresu;
    }

    public void setTypAdresu(Character typAdresu) {
        this.typAdresu = typAdresu;
    }

    public String getKodpocztowy() {
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNumerdomu() {
        return numerdomu;
    }

    public void setNumerdomu(String numerdomu) {
        this.numerdomu = numerdomu;
    }

    public String getNumerlokalu() {
        return numerlokalu;
    }

    public void setNumerlokalu(String numerlokalu) {
        this.numerlokalu = numerlokalu;
    }

    public String getSkrpocztowa() {
        return skrpocztowa;
    }

    public void setSkrpocztowa(String skrpocztowa) {
        this.skrpocztowa = skrpocztowa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTeldoteletr() {
        return teldoteletr;
    }

    public void setTeldoteletr(String teldoteletr) {
        this.teldoteletr = teldoteletr;
    }

    public String getFaks() {
        return faks;
    }

    public void setFaks(String faks) {
        this.faks = faks;
    }

    public String getAdrpocztyel() {
        return adrpocztyel;
    }

    public void setAdrpocztyel(String adrpocztyel) {
        this.adrpocztyel = adrpocztyel;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public Character getStatusDane() {
        return statusDane;
    }

    public void setStatusDane(Character statusDane) {
        this.statusDane = statusDane;
    }

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getIdPlZus() {
        return idPlZus;
    }

    public void setIdPlZus(Integer idPlZus) {
        this.idPlZus = idPlZus;
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
        if (!(object instanceof PlatnAdres)) {
            return false;
        }
        PlatnAdres other = (PlatnAdres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.PlatnAdres[ id=" + id + " ]";
    }
    
}
