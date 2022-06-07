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
@Table(name = "UBEZP_ADRES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbezpAdres.findAll", query = "SELECT u FROM UbezpAdres u"),
    @NamedQuery(name = "UbezpAdres.findById", query = "SELECT u FROM UbezpAdres u WHERE u.id = :id"),
    @NamedQuery(name = "UbezpAdres.findByIdUbezpieczony", query = "SELECT u FROM UbezpAdres u WHERE u.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "UbezpAdres.findByIdDokument", query = "SELECT u FROM UbezpAdres u WHERE u.idDokument = :idDokument"),
    @NamedQuery(name = "UbezpAdres.findByTypAdresu", query = "SELECT u FROM UbezpAdres u WHERE u.typAdresu = :typAdresu"),
    @NamedQuery(name = "UbezpAdres.findByKodpocztowy", query = "SELECT u FROM UbezpAdres u WHERE u.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "UbezpAdres.findByMiejscowosc", query = "SELECT u FROM UbezpAdres u WHERE u.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "UbezpAdres.findByGmina", query = "SELECT u FROM UbezpAdres u WHERE u.gmina = :gmina"),
    @NamedQuery(name = "UbezpAdres.findByUlica", query = "SELECT u FROM UbezpAdres u WHERE u.ulica = :ulica"),
    @NamedQuery(name = "UbezpAdres.findByNumerdomu", query = "SELECT u FROM UbezpAdres u WHERE u.numerdomu = :numerdomu"),
    @NamedQuery(name = "UbezpAdres.findByNumerlokalu", query = "SELECT u FROM UbezpAdres u WHERE u.numerlokalu = :numerlokalu"),
    @NamedQuery(name = "UbezpAdres.findBySkrpocztowa", query = "SELECT u FROM UbezpAdres u WHERE u.skrpocztowa = :skrpocztowa"),
    @NamedQuery(name = "UbezpAdres.findByTelefon", query = "SELECT u FROM UbezpAdres u WHERE u.telefon = :telefon"),
    @NamedQuery(name = "UbezpAdres.findByFaks", query = "SELECT u FROM UbezpAdres u WHERE u.faks = :faks"),
    @NamedQuery(name = "UbezpAdres.findByAdrpocztyel", query = "SELECT u FROM UbezpAdres u WHERE u.adrpocztyel = :adrpocztyel"),
    @NamedQuery(name = "UbezpAdres.findByStatusDane", query = "SELECT u FROM UbezpAdres u WHERE u.statusDane = :statusDane"),
    @NamedQuery(name = "UbezpAdres.findByDataod", query = "SELECT u FROM UbezpAdres u WHERE u.dataod = :dataod"),
    @NamedQuery(name = "UbezpAdres.findByInserttmp", query = "SELECT u FROM UbezpAdres u WHERE u.inserttmp = :inserttmp"),
    @NamedQuery(name = "UbezpAdres.findByIdPlZus", query = "SELECT u FROM UbezpAdres u WHERE u.idPlZus = :idPlZus"),
    @NamedQuery(name = "UbezpAdres.findByIdUbZus", query = "SELECT u FROM UbezpAdres u WHERE u.idUbZus = :idUbZus")})
public class UbezpAdres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UBEZPIECZONY", nullable = false)
    private int idUbezpieczony;
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
    @Column(name = "FAKS", length = 12)
    private String faks;
    @Size(max = 30)
    @Column(name = "ADRPOCZTYEL", length = 30)
    private String adrpocztyel;
    @Column(name = "STATUS_DANE")
    private Character statusDane;
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "ID_PL_ZUS")
    private Integer idPlZus;
    @Column(name = "ID_UB_ZUS")
    private Integer idUbZus;

    public UbezpAdres() {
    }

    public UbezpAdres(Integer id) {
        this.id = id;
    }

    public UbezpAdres(Integer id, int idUbezpieczony) {
        this.id = id;
        this.idUbezpieczony = idUbezpieczony;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(int idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
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

    public Integer getIdUbZus() {
        return idUbZus;
    }

    public void setIdUbZus(Integer idUbZus) {
        this.idUbZus = idUbZus;
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
        if (!(object instanceof UbezpAdres)) {
            return false;
        }
        UbezpAdres other = (UbezpAdres) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.UbezpAdres[ id=" + id + " ]";
    }
    
}
