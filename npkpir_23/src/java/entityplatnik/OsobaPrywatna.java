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
@Table(name = "OSOBA_PRYWATNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OsobaPrywatna.findAll", query = "SELECT o FROM OsobaPrywatna o"),
    @NamedQuery(name = "OsobaPrywatna.findById", query = "SELECT o FROM OsobaPrywatna o WHERE o.id = :id"),
    @NamedQuery(name = "OsobaPrywatna.findByIdUzytkownik", query = "SELECT o FROM OsobaPrywatna o WHERE o.idUzytkownik = :idUzytkownik"),
    @NamedQuery(name = "OsobaPrywatna.findByNip", query = "SELECT o FROM OsobaPrywatna o WHERE o.nip = :nip"),
    @NamedQuery(name = "OsobaPrywatna.findByPesel", query = "SELECT o FROM OsobaPrywatna o WHERE o.pesel = :pesel"),
    @NamedQuery(name = "OsobaPrywatna.findByRodzdok", query = "SELECT o FROM OsobaPrywatna o WHERE o.rodzdok = :rodzdok"),
    @NamedQuery(name = "OsobaPrywatna.findBySerianrdok", query = "SELECT o FROM OsobaPrywatna o WHERE o.serianrdok = :serianrdok"),
    @NamedQuery(name = "OsobaPrywatna.findByWojewodztwo", query = "SELECT o FROM OsobaPrywatna o WHERE o.wojewodztwo = :wojewodztwo"),
    @NamedQuery(name = "OsobaPrywatna.findByMiejscowosc", query = "SELECT o FROM OsobaPrywatna o WHERE o.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "OsobaPrywatna.findByKodpocztowy", query = "SELECT o FROM OsobaPrywatna o WHERE o.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "OsobaPrywatna.findByUlica", query = "SELECT o FROM OsobaPrywatna o WHERE o.ulica = :ulica"),
    @NamedQuery(name = "OsobaPrywatna.findByNumerdomu", query = "SELECT o FROM OsobaPrywatna o WHERE o.numerdomu = :numerdomu"),
    @NamedQuery(name = "OsobaPrywatna.findByNumerlokalu", query = "SELECT o FROM OsobaPrywatna o WHERE o.numerlokalu = :numerlokalu"),
    @NamedQuery(name = "OsobaPrywatna.findByStatuswr", query = "SELECT o FROM OsobaPrywatna o WHERE o.statuswr = :statuswr"),
    @NamedQuery(name = "OsobaPrywatna.findByInserttmp", query = "SELECT o FROM OsobaPrywatna o WHERE o.inserttmp = :inserttmp")})
public class OsobaPrywatna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_UZYTKOWNIK", nullable = false)
    private int idUzytkownik;
    @Size(max = 10)
    @Column(name = "NIP", length = 10)
    private String nip;
    @Size(max = 11)
    @Column(name = "PESEL", length = 11)
    private String pesel;
    @Column(name = "RODZDOK")
    private Character rodzdok;
    @Size(max = 9)
    @Column(name = "SERIANRDOK", length = 9)
    private String serianrdok;
    @Size(max = 26)
    @Column(name = "WOJEWODZTWO", length = 26)
    private String wojewodztwo;
    @Size(max = 26)
    @Column(name = "MIEJSCOWOSC", length = 26)
    private String miejscowosc;
    @Size(max = 5)
    @Column(name = "KODPOCZTOWY", length = 5)
    private String kodpocztowy;
    @Size(max = 30)
    @Column(name = "ULICA", length = 30)
    private String ulica;
    @Size(max = 7)
    @Column(name = "NUMERDOMU", length = 7)
    private String numerdomu;
    @Size(max = 7)
    @Column(name = "NUMERLOKALU", length = 7)
    private String numerlokalu;
    @Column(name = "STATUSWR")
    private Character statuswr;
    @Column(name = "INSERTTMP")
    private Integer inserttmp;

    public OsobaPrywatna() {
    }

    public OsobaPrywatna(Integer id) {
        this.id = id;
    }

    public OsobaPrywatna(Integer id, int idUzytkownik) {
        this.id = id;
        this.idUzytkownik = idUzytkownik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(int idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
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

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getKodpocztowy() {
        return kodpocztowy;
    }

    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
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

    public Character getStatuswr() {
        return statuswr;
    }

    public void setStatuswr(Character statuswr) {
        this.statuswr = statuswr;
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
        if (!(object instanceof OsobaPrywatna)) {
            return false;
        }
        OsobaPrywatna other = (OsobaPrywatna) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityplatnik.OsobaPrywatna[ id=" + id + " ]";
    }
    
}
