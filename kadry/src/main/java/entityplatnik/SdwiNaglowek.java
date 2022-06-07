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
@Table(name = "SDWI_NAGLOWEK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SdwiNaglowek.findAll", query = "SELECT s FROM SdwiNaglowek s"),
    @NamedQuery(name = "SdwiNaglowek.findById", query = "SELECT s FROM SdwiNaglowek s WHERE s.id = :id"),
    @NamedQuery(name = "SdwiNaglowek.findByIdPlatnik", query = "SELECT s FROM SdwiNaglowek s WHERE s.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "SdwiNaglowek.findByIdPrzesylki", query = "SELECT s FROM SdwiNaglowek s WHERE s.idPrzesylki = :idPrzesylki"),
    @NamedQuery(name = "SdwiNaglowek.findBySkrot", query = "SELECT s FROM SdwiNaglowek s WHERE s.skrot = :skrot"),
    @NamedQuery(name = "SdwiNaglowek.findByOkrrozl", query = "SELECT s FROM SdwiNaglowek s WHERE s.okrrozl = :okrrozl"),
    @NamedQuery(name = "SdwiNaglowek.findByDatawygwyc", query = "SELECT s FROM SdwiNaglowek s WHERE s.datawygwyc = :datawygwyc"),
    @NamedQuery(name = "SdwiNaglowek.findByNip", query = "SELECT s FROM SdwiNaglowek s WHERE s.nip = :nip"),
    @NamedQuery(name = "SdwiNaglowek.findByRegon", query = "SELECT s FROM SdwiNaglowek s WHERE s.regon = :regon"),
    @NamedQuery(name = "SdwiNaglowek.findByPesel", query = "SELECT s FROM SdwiNaglowek s WHERE s.pesel = :pesel"),
    @NamedQuery(name = "SdwiNaglowek.findByRodzdok", query = "SELECT s FROM SdwiNaglowek s WHERE s.rodzdok = :rodzdok"),
    @NamedQuery(name = "SdwiNaglowek.findBySerianrdok", query = "SELECT s FROM SdwiNaglowek s WHERE s.serianrdok = :serianrdok"),
    @NamedQuery(name = "SdwiNaglowek.findByNazwaskr", query = "SELECT s FROM SdwiNaglowek s WHERE s.nazwaskr = :nazwaskr"),
    @NamedQuery(name = "SdwiNaglowek.findByNazwisko", query = "SELECT s FROM SdwiNaglowek s WHERE s.nazwisko = :nazwisko"),
    @NamedQuery(name = "SdwiNaglowek.findByImiepierw", query = "SELECT s FROM SdwiNaglowek s WHERE s.imiepierw = :imiepierw"),
    @NamedQuery(name = "SdwiNaglowek.findByStatusSdwi", query = "SELECT s FROM SdwiNaglowek s WHERE s.statusSdwi = :statusSdwi"),
    @NamedQuery(name = "SdwiNaglowek.findByDataurodz", query = "SELECT s FROM SdwiNaglowek s WHERE s.dataurodz = :dataurodz"),
    @NamedQuery(name = "SdwiNaglowek.findByInserttmp", query = "SELECT s FROM SdwiNaglowek s WHERE s.inserttmp = :inserttmp"),
    @NamedQuery(name = "SdwiNaglowek.findByLPozycji", query = "SELECT s FROM SdwiNaglowek s WHERE s.lPozycji = :lPozycji")})
public class SdwiNaglowek implements Serializable {

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
    @Size(max = 30)
    @Column(name = "ID_PRZESYLKI", length = 30)
    private String idPrzesylki;
    @Size(max = 40)
    @Column(name = "SKROT", length = 40)
    private String skrot;
    @Size(max = 6)
    @Column(name = "OKRROZL", length = 6)
    private String okrrozl;
    @Column(name = "DATAWYGWYC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datawygwyc;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 14)
    @Column(name = "REGON", length = 14)
    private String regon;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 31)
    @Column(name = "NAZWASKR", length = 31)
    private String nazwaskr;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Column(name = "STATUS_SDWI")
    private Character statusSdwi;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;
    @Column(name = "L_POZYCJI")
    private Integer lPozycji;

    public SdwiNaglowek() {
    }

    public SdwiNaglowek(Integer id) {
        this.id = id;
    }

    public SdwiNaglowek(Integer id, int idPlatnik) {
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

    public String getIdPrzesylki() {
        return idPrzesylki;
    }

    public void setIdPrzesylki(String idPrzesylki) {
        this.idPrzesylki = idPrzesylki;
    }

    public String getSkrot() {
        return skrot;
    }

    public void setSkrot(String skrot) {
        this.skrot = skrot;
    }

    public String getOkrrozl() {
        return okrrozl;
    }

    public void setOkrrozl(String okrrozl) {
        this.okrrozl = okrrozl;
    }

    public Date getDatawygwyc() {
        return datawygwyc;
    }

    public void setDatawygwyc(Date datawygwyc) {
        this.datawygwyc = datawygwyc;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Character getRodzdok() {
        return rodzdok;
    }

    public void setRodzdok(Character rodzdok) {
        this.rodzdok = rodzdok;
    }

    public String getSerianrdok() {
        return serianrdok;
    }

    public void setSerianrdok(String serianrdok) {
        this.serianrdok = serianrdok;
    }

    public String getNazwaskr() {
        return nazwaskr;
    }

    public void setNazwaskr(String nazwaskr) {
        this.nazwaskr = nazwaskr;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImiepierw() {
        return imiepierw;
    }

    public void setImiepierw(String imiepierw) {
        this.imiepierw = imiepierw;
    }

    public Character getStatusSdwi() {
        return statusSdwi;
    }

    public void setStatusSdwi(Character statusSdwi) {
        this.statusSdwi = statusSdwi;
    }

    public Date getDataurodz() {
        return dataurodz;
    }

    public void setDataurodz(Date dataurodz) {
        this.dataurodz = dataurodz;
    }

    public Integer getInserttmp() {
        return inserttmp;
    }

    public void setInserttmp(Integer inserttmp) {
        this.inserttmp = inserttmp;
    }

    public Integer getLPozycji() {
        return lPozycji;
    }

    public void setLPozycji(Integer lPozycji) {
        this.lPozycji = lPozycji;
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
        if (!(object instanceof SdwiNaglowek)) {
            return false;
        }
        SdwiNaglowek other = (SdwiNaglowek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.SdwiNaglowek[ id=" + id + " ]";
    }
    
}
