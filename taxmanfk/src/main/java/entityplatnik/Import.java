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
import javax.persistence.Lob;
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
@Table(name = "IMPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Import.findAll", query = "SELECT i FROM Import i"),
    @NamedQuery(name = "Import.findById", query = "SELECT i FROM Import i WHERE i.id = :id"),
    @NamedQuery(name = "Import.findByIdPlatnik", query = "SELECT i FROM Import i WHERE i.idPlatnik = :idPlatnik"),
    @NamedQuery(name = "Import.findByIdUzytkownik", query = "SELECT i FROM Import i WHERE i.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "Import.findByStatus", query = "SELECT i FROM Import i WHERE i.status = :status"),
    @NamedQuery(name = "Import.findByDataRozp", query = "SELECT i FROM Import i WHERE i.dataRozp = :dataRozp"),
    @NamedQuery(name = "Import.findByDataZak", query = "SELECT i FROM Import i WHERE i.dataZak = :dataZak"),
    @NamedQuery(name = "Import.findByRodzaj", query = "SELECT i FROM Import i WHERE i.rodzaj = :rodzaj"),
    @NamedQuery(name = "Import.findByPominieteFiltr", query = "SELECT i FROM Import i WHERE i.pominieteFiltr = :pominieteFiltr"),
    @NamedQuery(name = "Import.findByPominieteFiltrBlw", query = "SELECT i FROM Import i WHERE i.pominieteFiltrBlw = :pominieteFiltrBlw"),
    @NamedQuery(name = "Import.findByPominieteDok", query = "SELECT i FROM Import i WHERE i.pominieteDok = :pominieteDok"),
    @NamedQuery(name = "Import.findByPominieteDokBlw", query = "SELECT i FROM Import i WHERE i.pominieteDokBlw = :pominieteDokBlw"),
    @NamedQuery(name = "Import.findByBezIdPlatnika", query = "SELECT i FROM Import i WHERE i.bezIdPlatnika = :bezIdPlatnika"),
    @NamedQuery(name = "Import.findByBezIdUbezp", query = "SELECT i FROM Import i WHERE i.bezIdUbezp = :bezIdUbezp"),
    @NamedQuery(name = "Import.findByBezIdUbezpBlw", query = "SELECT i FROM Import i WHERE i.bezIdUbezpBlw = :bezIdUbezpBlw"),
    @NamedQuery(name = "Import.findByBledyKryt", query = "SELECT i FROM Import i WHERE i.bledyKryt = :bledyKryt"),
    @NamedQuery(name = "Import.findByBledyKrytBlw", query = "SELECT i FROM Import i WHERE i.bledyKrytBlw = :bledyKrytBlw"),
    @NamedQuery(name = "Import.findByDoWyjasnienia", query = "SELECT i FROM Import i WHERE i.doWyjasnienia = :doWyjasnienia"),
    @NamedQuery(name = "Import.findByDoWyjasnieniaBlw", query = "SELECT i FROM Import i WHERE i.doWyjasnieniaBlw = :doWyjasnieniaBlw"),
    @NamedQuery(name = "Import.findByLiczbaZaimp", query = "SELECT i FROM Import i WHERE i.liczbaZaimp = :liczbaZaimp"),
    @NamedQuery(name = "Import.findByLiczbaZaimpBlw", query = "SELECT i FROM Import i WHERE i.liczbaZaimpBlw = :liczbaZaimpBlw"),
    @NamedQuery(name = "Import.findByNowe", query = "SELECT i FROM Import i WHERE i.nowe = :nowe"),
    @NamedQuery(name = "Import.findByNoweBlw", query = "SELECT i FROM Import i WHERE i.noweBlw = :noweBlw"),
    @NamedQuery(name = "Import.findByNadpisane", query = "SELECT i FROM Import i WHERE i.nadpisane = :nadpisane"),
    @NamedQuery(name = "Import.findByNadpisaneBlw", query = "SELECT i FROM Import i WHERE i.nadpisaneBlw = :nadpisaneBlw"),
    @NamedQuery(name = "Import.findBySumowane", query = "SELECT i FROM Import i WHERE i.sumowane = :sumowane"),
    @NamedQuery(name = "Import.findBySumowaneBlw", query = "SELECT i FROM Import i WHERE i.sumowaneBlw = :sumowaneBlw"),
    @NamedQuery(name = "Import.findByUbezpNowe", query = "SELECT i FROM Import i WHERE i.ubezpNowe = :ubezpNowe"),
    @NamedQuery(name = "Import.findByUbezpZmienione", query = "SELECT i FROM Import i WHERE i.ubezpZmienione = :ubezpZmienione"),
    @NamedQuery(name = "Import.findByZakresZas", query = "SELECT i FROM Import i WHERE i.zakresZas = :zakresZas"),
    @NamedQuery(name = "Import.findByInserttmp", query = "SELECT i FROM Import i WHERE i.inserttmp = :inserttmp")})
public class Import implements Serializable {

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
    @Column(name = "ID_UZYTKOWNIK")
    private Integer idUzytkownik;
    @Size(max = 1)
    @Column(name = "STATUS", length = 1)
    private String status;
    @Column(name = "DATA_ROZP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRozp;
    @Column(name = "DATA_ZAK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataZak;
    @Size(max = 5)
    @Column(name = "RODZAJ", length = 5)
    private String rodzaj;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "LISTA_PLIKOW", length = 2147483647)
    private String listaPlikow;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "LISTA_BLAD_STR", length = 2147483647)
    private String listaBladStr;
    @Column(name = "POMINIETE_FILTR")
    private Integer pominieteFiltr;
    @Column(name = "POMINIETE_FILTR_BLW")
    private Integer pominieteFiltrBlw;
    @Column(name = "POMINIETE_DOK")
    private Integer pominieteDok;
    @Column(name = "POMINIETE_DOK_BLW")
    private Integer pominieteDokBlw;
    @Column(name = "BEZ_ID_PLATNIKA")
    private Integer bezIdPlatnika;
    @Column(name = "BEZ_ID_UBEZP")
    private Integer bezIdUbezp;
    @Column(name = "BEZ_ID_UBEZP_BLW")
    private Integer bezIdUbezpBlw;
    @Column(name = "BLEDY_KRYT")
    private Integer bledyKryt;
    @Column(name = "BLEDY_KRYT_BLW")
    private Integer bledyKrytBlw;
    @Column(name = "DO_WYJASNIENIA")
    private Integer doWyjasnienia;
    @Column(name = "DO_WYJASNIENIA_BLW")
    private Integer doWyjasnieniaBlw;
    @Column(name = "LICZBA_ZAIMP")
    private Integer liczbaZaimp;
    @Column(name = "LICZBA_ZAIMP_BLW")
    private Integer liczbaZaimpBlw;
    @Column(name = "NOWE")
    private Integer nowe;
    @Column(name = "NOWE_BLW")
    private Integer noweBlw;
    @Column(name = "NADPISANE")
    private Integer nadpisane;
    @Column(name = "NADPISANE_BLW")
    private Integer nadpisaneBlw;
    @Column(name = "SUMOWANE")
    private Integer sumowane;
    @Column(name = "SUMOWANE_BLW")
    private Integer sumowaneBlw;
    @Column(name = "UBEZP_NOWE")
    private Integer ubezpNowe;
    @Column(name = "UBEZP_ZMIENIONE")
    private Integer ubezpZmienione;
    @Column(name = "ZAKRES_ZAS")
    private Integer zakresZas;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public Import() {
    }

    public Import(Integer id) {
        this.id = id;
    }

    public Import(Integer id, int idPlatnik) {
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

    public Integer getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(Integer idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataRozp() {
        return dataRozp;
    }

    public void setDataRozp(Date dataRozp) {
        this.dataRozp = dataRozp;
    }

    public Date getDataZak() {
        return dataZak;
    }

    public void setDataZak(Date dataZak) {
        this.dataZak = dataZak;
    }

    public String getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj = rodzaj;
    }

    public String getListaPlikow() {
        return listaPlikow;
    }

    public void setListaPlikow(String listaPlikow) {
        this.listaPlikow = listaPlikow;
    }

    public String getListaBladStr() {
        return listaBladStr;
    }

    public void setListaBladStr(String listaBladStr) {
        this.listaBladStr = listaBladStr;
    }

    public Integer getPominieteFiltr() {
        return pominieteFiltr;
    }

    public void setPominieteFiltr(Integer pominieteFiltr) {
        this.pominieteFiltr = pominieteFiltr;
    }

    public Integer getPominieteFiltrBlw() {
        return pominieteFiltrBlw;
    }

    public void setPominieteFiltrBlw(Integer pominieteFiltrBlw) {
        this.pominieteFiltrBlw = pominieteFiltrBlw;
    }

    public Integer getPominieteDok() {
        return pominieteDok;
    }

    public void setPominieteDok(Integer pominieteDok) {
        this.pominieteDok = pominieteDok;
    }

    public Integer getPominieteDokBlw() {
        return pominieteDokBlw;
    }

    public void setPominieteDokBlw(Integer pominieteDokBlw) {
        this.pominieteDokBlw = pominieteDokBlw;
    }

    public Integer getBezIdPlatnika() {
        return bezIdPlatnika;
    }

    public void setBezIdPlatnika(Integer bezIdPlatnika) {
        this.bezIdPlatnika = bezIdPlatnika;
    }

    public Integer getBezIdUbezp() {
        return bezIdUbezp;
    }

    public void setBezIdUbezp(Integer bezIdUbezp) {
        this.bezIdUbezp = bezIdUbezp;
    }

    public Integer getBezIdUbezpBlw() {
        return bezIdUbezpBlw;
    }

    public void setBezIdUbezpBlw(Integer bezIdUbezpBlw) {
        this.bezIdUbezpBlw = bezIdUbezpBlw;
    }

    public Integer getBledyKryt() {
        return bledyKryt;
    }

    public void setBledyKryt(Integer bledyKryt) {
        this.bledyKryt = bledyKryt;
    }

    public Integer getBledyKrytBlw() {
        return bledyKrytBlw;
    }

    public void setBledyKrytBlw(Integer bledyKrytBlw) {
        this.bledyKrytBlw = bledyKrytBlw;
    }

    public Integer getDoWyjasnienia() {
        return doWyjasnienia;
    }

    public void setDoWyjasnienia(Integer doWyjasnienia) {
        this.doWyjasnienia = doWyjasnienia;
    }

    public Integer getDoWyjasnieniaBlw() {
        return doWyjasnieniaBlw;
    }

    public void setDoWyjasnieniaBlw(Integer doWyjasnieniaBlw) {
        this.doWyjasnieniaBlw = doWyjasnieniaBlw;
    }

    public Integer getLiczbaZaimp() {
        return liczbaZaimp;
    }

    public void setLiczbaZaimp(Integer liczbaZaimp) {
        this.liczbaZaimp = liczbaZaimp;
    }

    public Integer getLiczbaZaimpBlw() {
        return liczbaZaimpBlw;
    }

    public void setLiczbaZaimpBlw(Integer liczbaZaimpBlw) {
        this.liczbaZaimpBlw = liczbaZaimpBlw;
    }

    public Integer getNowe() {
        return nowe;
    }

    public void setNowe(Integer nowe) {
        this.nowe = nowe;
    }

    public Integer getNoweBlw() {
        return noweBlw;
    }

    public void setNoweBlw(Integer noweBlw) {
        this.noweBlw = noweBlw;
    }

    public Integer getNadpisane() {
        return nadpisane;
    }

    public void setNadpisane(Integer nadpisane) {
        this.nadpisane = nadpisane;
    }

    public Integer getNadpisaneBlw() {
        return nadpisaneBlw;
    }

    public void setNadpisaneBlw(Integer nadpisaneBlw) {
        this.nadpisaneBlw = nadpisaneBlw;
    }

    public Integer getSumowane() {
        return sumowane;
    }

    public void setSumowane(Integer sumowane) {
        this.sumowane = sumowane;
    }

    public Integer getSumowaneBlw() {
        return sumowaneBlw;
    }

    public void setSumowaneBlw(Integer sumowaneBlw) {
        this.sumowaneBlw = sumowaneBlw;
    }

    public Integer getUbezpNowe() {
        return ubezpNowe;
    }

    public void setUbezpNowe(Integer ubezpNowe) {
        this.ubezpNowe = ubezpNowe;
    }

    public Integer getUbezpZmienione() {
        return ubezpZmienione;
    }

    public void setUbezpZmienione(Integer ubezpZmienione) {
        this.ubezpZmienione = ubezpZmienione;
    }

    public Integer getZakresZas() {
        return zakresZas;
    }

    public void setZakresZas(Integer zakresZas) {
        this.zakresZas = zakresZas;
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
        if (!(object instanceof Import)) {
            return false;
        }
        Import other = (Import) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.Import[ id=" + id + " ]";
    }
    
}
