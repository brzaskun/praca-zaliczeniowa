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
@Table(name = "SDWI_UBEZPIECZONY")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SdwiUbezpieczony.findAll", query = "SELECT s FROM SdwiUbezpieczony s"),
    @NamedQuery(name = "SdwiUbezpieczony.findById", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.id = :id"),
    @NamedQuery(name = "SdwiUbezpieczony.findByIdSdwinaglowek", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.idSdwinaglowek = :idSdwinaglowek"),
    @NamedQuery(name = "SdwiUbezpieczony.findByIdUbezpieczony", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.idUbezpieczony = :idUbezpieczony"),
    @NamedQuery(name = "SdwiUbezpieczony.findByPesel", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.pesel = :pesel"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNip", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.nip = :nip"),
    @NamedQuery(name = "SdwiUbezpieczony.findByRodzdok", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.rodzdok = :rodzdok"),
    @NamedQuery(name = "SdwiUbezpieczony.findBySerianrdok", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.serianrdok = :serianrdok"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNazwisko", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.nazwisko = :nazwisko"),
    @NamedQuery(name = "SdwiUbezpieczony.findByImiepierw", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.imiepierw = :imiepierw"),
    @NamedQuery(name = "SdwiUbezpieczony.findByDataurodz", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.dataurodz = :dataurodz"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNazwiskorod", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.nazwiskorod = :nazwiskorod"),
    @NamedQuery(name = "SdwiUbezpieczony.findByImiedrugie", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.imiedrugie = :imiedrugie"),
    @NamedQuery(name = "SdwiUbezpieczony.findByKodkasy", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.kodkasy = :kodkasy"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNazwakch", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.nazwakch = :nazwakch"),
    @NamedQuery(name = "SdwiUbezpieczony.findByTypAdresu", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.typAdresu = :typAdresu"),
    @NamedQuery(name = "SdwiUbezpieczony.findByKodpocztowy", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "SdwiUbezpieczony.findByMiejscowosc", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "SdwiUbezpieczony.findByGmina", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.gmina = :gmina"),
    @NamedQuery(name = "SdwiUbezpieczony.findByUlica", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.ulica = :ulica"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNumerdomu", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.numerdomu = :numerdomu"),
    @NamedQuery(name = "SdwiUbezpieczony.findByNumerlokalu", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.numerlokalu = :numerlokalu"),
    @NamedQuery(name = "SdwiUbezpieczony.findBySkrpocztowa", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.skrpocztowa = :skrpocztowa"),
    @NamedQuery(name = "SdwiUbezpieczony.findByTelefon", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.telefon = :telefon"),
    @NamedQuery(name = "SdwiUbezpieczony.findByFaks", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.faks = :faks"),
    @NamedQuery(name = "SdwiUbezpieczony.findByAdrpocztyel", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.adrpocztyel = :adrpocztyel"),
    @NamedQuery(name = "SdwiUbezpieczony.findByDataod", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.dataod = :dataod"),
    @NamedQuery(name = "SdwiUbezpieczony.findByStatuswr", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.statuswr = :statuswr"),
    @NamedQuery(name = "SdwiUbezpieczony.findByStatusSdwi", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.statusSdwi = :statusSdwi"),
    @NamedQuery(name = "SdwiUbezpieczony.findByKodtytub", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.kodtytub = :kodtytub"),
    @NamedQuery(name = "SdwiUbezpieczony.findByInserttmp", query = "SELECT s FROM SdwiUbezpieczony s WHERE s.inserttmp = :inserttmp")})
public class SdwiUbezpieczony implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SDWINAGLOWEK", nullable = false)
    private int idSdwinaglowek;
    @Column(name = "ID_UBEZPIECZONY")
    private Integer idUbezpieczony;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 31)
    @Column(name = "NAZWISKO", length = 31)
    private String nazwisko;
    @Size(max = 22)
    @Column(name = "IMIEPIERW", length = 22)
    private String imiepierw;
    @Column(name = "DATAURODZ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataurodz;
    @Size(max = 31)
    @Column(name = "NAZWISKOROD", length = 31)
    private String nazwiskorod;
    @Size(max = 22)
    @Column(name = "IMIEDRUGIE", length = 22)
    private String imiedrugie;
    @Size(max = 3)
    @Column(name = "KODKASY", length = 3)
    private String kodkasy;
    @Size(max = 23)
    @Column(name = "NAZWAKCH", length = 23)
    private String nazwakch;
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
    @Column(name = "DATAOD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataod;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "STATUS_SDWI")
    private Character statusSdwi;
    @Size(max = 50)
    @Column(name = "KODTYTUB", length = 50)
    private String kodtytub;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public SdwiUbezpieczony() {
    }

    public SdwiUbezpieczony(Integer id) {
        this.id = id;
    }

    public SdwiUbezpieczony(Integer id, int idSdwinaglowek) {
        this.id = id;
        this.idSdwinaglowek = idSdwinaglowek;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdSdwinaglowek() {
        return idSdwinaglowek;
    }

    public void setIdSdwinaglowek(int idSdwinaglowek) {
        this.idSdwinaglowek = idSdwinaglowek;
    }

    public Integer getIdUbezpieczony() {
        return idUbezpieczony;
    }

    public void setIdUbezpieczony(Integer idUbezpieczony) {
        this.idUbezpieczony = idUbezpieczony;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public Date getDataurodz() {
        return dataurodz;
    }

    public void setDataurodz(Date dataurodz) {
        this.dataurodz = dataurodz;
    }

    public String getNazwiskorod() {
        return nazwiskorod;
    }

    public void setNazwiskorod(String nazwiskorod) {
        this.nazwiskorod = nazwiskorod;
    }

    public String getImiedrugie() {
        return imiedrugie;
    }

    public void setImiedrugie(String imiedrugie) {
        this.imiedrugie = imiedrugie;
    }

    public String getKodkasy() {
        return kodkasy;
    }

    public void setKodkasy(String kodkasy) {
        this.kodkasy = kodkasy;
    }

    public String getNazwakch() {
        return nazwakch;
    }

    public void setNazwakch(String nazwakch) {
        this.nazwakch = nazwakch;
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

    public Date getDataod() {
        return dataod;
    }

    public void setDataod(Date dataod) {
        this.dataod = dataod;
    }

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
    }

    public Character getStatusSdwi() {
        return statusSdwi;
    }

    public void setStatusSdwi(Character statusSdwi) {
        this.statusSdwi = statusSdwi;
    }

    public String getKodtytub() {
        return kodtytub;
    }

    public void setKodtytub(String kodtytub) {
        this.kodtytub = kodtytub;
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
        if (!(object instanceof SdwiUbezpieczony)) {
            return false;
        }
        SdwiUbezpieczony other = (SdwiUbezpieczony) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.SdwiUbezpieczony[ id=" + id + " ]";
    }
    
}
