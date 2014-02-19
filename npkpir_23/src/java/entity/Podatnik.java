/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Parametr;
import embeddable.Pozycjenafakturzebazadanych;
import embeddable.Straty;
import embeddable.Straty1;
import embeddable.Udzialy;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "podatnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podatnik.findAll", query = "SELECT p FROM Podatnik p"),
    @NamedQuery(name = "Podatnik.findByNip", query = "SELECT p FROM Podatnik p WHERE p.nip = :nip"),
    @NamedQuery(name = "Podatnik.findByPesel", query = "SELECT p FROM Podatnik p WHERE p.pesel = :pesel"),
    @NamedQuery(name = "Podatnik.findByRegon", query = "SELECT p FROM Podatnik p WHERE p.regon = :regon"),
    @NamedQuery(name = "Podatnik.findByDataurodzenia", query = "SELECT p FROM Podatnik p WHERE p.dataurodzenia = :dataurodzenia"),
    @NamedQuery(name = "Podatnik.findByDochokres", query = "SELECT p FROM Podatnik p WHERE p.dochokres = :dochokres"),
    @NamedQuery(name = "Podatnik.findByEmail", query = "SELECT p FROM Podatnik p WHERE p.email = :email"),
    @NamedQuery(name = "Podatnik.findByFax", query = "SELECT p FROM Podatnik p WHERE p.fax = :fax"),
    @NamedQuery(name = "Podatnik.findByGmina", query = "SELECT p FROM Podatnik p WHERE p.gmina = :gmina"),
    @NamedQuery(name = "Podatnik.findByImie", query = "SELECT p FROM Podatnik p WHERE p.imie = :imie"),
    @NamedQuery(name = "Podatnik.findByKodpocztowy", query = "SELECT p FROM Podatnik p WHERE p.kodpocztowy = :kodpocztowy"),
    @NamedQuery(name = "Podatnik.findByMetodakasowa", query = "SELECT p FROM Podatnik p WHERE p.metodakasowa = :metodakasowa"),
    @NamedQuery(name = "Podatnik.findByMiejscowosc", query = "SELECT p FROM Podatnik p WHERE p.miejscowosc = :miejscowosc"),
    @NamedQuery(name = "Podatnik.findByNazwapelna", query = "SELECT p FROM Podatnik p WHERE p.nazwapelna = :nazwapelna"),
    @NamedQuery(name = "Podatnik.findByNazwisko", query = "SELECT p FROM Podatnik p WHERE p.nazwisko = :nazwisko"),
    @NamedQuery(name = "Podatnik.findByNrdomu", query = "SELECT p FROM Podatnik p WHERE p.nrdomu = :nrdomu"),
    @NamedQuery(name = "Podatnik.findByNrlokalu", query = "SELECT p FROM Podatnik p WHERE p.nrlokalu = :nrlokalu"),
    @NamedQuery(name = "Podatnik.findByPoczta", query = "SELECT p FROM Podatnik p WHERE p.poczta = :poczta"),
    @NamedQuery(name = "Podatnik.findByPowiat", query = "SELECT p FROM Podatnik p WHERE p.powiat = :powiat"),
    @NamedQuery(name = "Podatnik.findByTelefonkontaktowy", query = "SELECT p FROM Podatnik p WHERE p.telefonkontaktowy = :telefonkontaktowy"),
    @NamedQuery(name = "Podatnik.findByUlica", query = "SELECT p FROM Podatnik p WHERE p.ulica = :ulica"),
    @NamedQuery(name = "Podatnik.findByUrzadskarbowy", query = "SELECT p FROM Podatnik p WHERE p.urzadskarbowy = :urzadskarbowy"),
    @NamedQuery(name = "Podatnik.findByVatokres", query = "SELECT p FROM Podatnik p WHERE p.vatokres = :vatokres"),
    @NamedQuery(name = "Podatnik.findByWojewodztwo", query = "SELECT p FROM Podatnik p WHERE p.wojewodztwo = :wojewodztwo")})
public class Podatnik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nip")
    @Pattern(regexp="\\d+",message="NIP składa się wyłącznie z cyfr")
    private String nip;
    @Size(max = 255)
    @Column(name = "PESEL")
    @Pattern(regexp="\\d+",message="Pesel składa się wyłącznie z cyfr")
    private String pesel;
    @Size(max = 255)
    @Column(name = "REGON")
    @Pattern(regexp="\\d+",message="Regon składa się wyłącznie z cyfr")
    private String regon;
    @Size(max = 255)
    @Column(name = "DATAURODZENIA")
    private String dataurodzenia;
    @Size(max = 255)
    @Column(name = "DOCHOKRES")
    private String dochokres;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Nieprawidłowy adres email")
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 255)
    @Column(name = "GMINA")
    private String gmina;
    @Size(max = 255)
    @Column(name = "IMIE")
    private String imie;
    @Size(max = 255)
    @Column(name = "KODPOCZTOWY")
    private String kodpocztowy;
    @Size(max = 255)
    @Column(name = "METODAKASOWA")
    private String metodakasowa;
    @Size(max = 255)
    @Column(name = "MIEJSCOWOSC")
    private String miejscowosc;
    @Size(max = 255)
    @Column(name = "NAZWAPELNA")
    private String nazwapelna;
    @Size(max = 255)
    @Column(name = "NAZWISKO")
    private String nazwisko;
    @Size(max = 255)
    @Column(name = "NRDOMU")
    private String nrdomu;
    @Size(max = 255)
    @Column(name = "NRLOKALU")
    private String nrlokalu;
    @Size(max = 255)
    @Column(name = "POCZTA")
    private String poczta;
    @Lob
    @Column(name = "PODATEKDOCHODOWY")
    private List<Parametr> podatekdochodowy;
    @Size(max = 255)
    @Column(name = "POWIAT")
    private String powiat;
    @Pattern(regexp="[\\d]{3}[-]{1,1}[\\d]{3}[-]{1,1}[\\d]{3}", message="Nieprawidłowy numer telefonu, winien być: xxx-xxx-xxx")
    @Size(max = 255)
    @Column(name = "TELEFONKONTAKTOWY")
    private String telefonkontaktowy;
    @Size(max = 255)
    @Column(name = "ULICA")
    private String ulica;
    @Size(max = 255)
    @Column(name = "URZADSKARBOWY")
    private String urzadskarbowy;
    @Lob
    @Column(name = "VATOKRES")
    private List<Parametr> vatokres;
    @Size(max = 255)
    @Column(name = "WOJEWODZTWO")
    private String wojewodztwo;
    @Lob
    @Column(name = "zusparametr")
    private List<Zusstawki> zusparametr;
    @Lob
    @Column(name = "remanent")
    private List<Parametr> remanent;
    @Lob
    @Column(name="dokumentyksiegowe")
    private List<Rodzajedok> dokumentyksiegowe;
    @Lob
    @Column(name = "kwotaautoryzujaca")
    private List<Parametr> kwotaautoryzujaca;
    @Column(name = "pole47")
    private String pole47;
    @Lob
    @Column(name = "opisypkpir")
    private List opisypkpir;
    @Column(name = "odliczaczus51")
    private Boolean odliczaczus51;
    @Lob
    @Column(name = "udzialy")
    private List<Udzialy> udzialy;
    @Lob
    @Column(name = "stratyzlatub")
    private List<Straty> stratyzlatub;
    @Lob
    @Column(name = "stratyzlatub1")
    private List<Straty1> stratyzlatub1;
    @Lob
    @Column(name = "numerpkpir")
    private List<Parametr> numerpkpir;
    @Size(max = 26)
    @Column(name = "nrkontabankowego")
    private String nrkontabankowego;
    @Size (max = 3)
    @Column(name = "platnoscwdni")
    private String platnoscwdni;
    @Size (max = 512)
    @Column(name = "nazwadlafaktury")
    private String nazwadlafaktury;
    @Column(name = "adresdlafaktury")
    private String adresdlafaktury;
    @Lob
    @Column(name = "wierszwzorcowy")
    private Pozycjenafakturzebazadanych wierszwzorcowy;
    @Size (max = 512)
    @Column(name = "miejscewystawienia")
    private String miejscewystawienia;
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Podatnik() {
    }
    
    public Podatnik(String nip) {
        this.nip = nip;
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
    
    public String getRegon() {
        return regon;
    }
    
    public void setRegon(String regon) {
        this.regon = regon;
    }
    
    public String getDataurodzenia() {
        return dataurodzenia;
    }
    
    public void setDataurodzenia(String dataurodzenia) {
        this.dataurodzenia = dataurodzenia;
    }
    
    public String getDochokres() {
        return dochokres;
    }
    
    public void setDochokres(String dochokres) {
        this.dochokres = dochokres;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getGmina() {
        return gmina;
    }
    
    public void setGmina(String gmina) {
        this.gmina = gmina;
    }
    
    public String getImie() {
        return imie;
    }
    
    public void setImie(String imie) {
        this.imie = imie;
    }
    
    public String getKodpocztowy() {
        return kodpocztowy;
    }
    
    public void setKodpocztowy(String kodpocztowy) {
        this.kodpocztowy = kodpocztowy;
    }
    
    public String getMetodakasowa() {
        return metodakasowa;
    }
    
    public void setMetodakasowa(String metodakasowa) {
        this.metodakasowa = metodakasowa;
    }
    
    public String getMiejscowosc() {
        return miejscowosc;
    }
    
    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }
    
    public String getNazwapelna() {
        return nazwapelna;
    }
    
    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }
    
    public String getNazwisko() {
        return nazwisko;
    }
    
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    public String getNrdomu() {
        return nrdomu;
    }
    
    public void setNrdomu(String nrdomu) {
        this.nrdomu = nrdomu;
    }
    
    public String getNrlokalu() {
        return nrlokalu;
    }
    
    public void setNrlokalu(String nrlokalu) {
        this.nrlokalu = nrlokalu;
    }
    
    public String getPoczta() {
        return poczta;
    }
    
    public void setPoczta(String poczta) {
        this.poczta = poczta;
    }
    
    public List<Parametr> getPodatekdochodowy() {
        return podatekdochodowy;
    }
    
    public void setPodatekdochodowy(List<Parametr> podatekdochodowy) {
        this.podatekdochodowy = podatekdochodowy;
    }
    
    public String getPowiat() {
        return powiat;
    }
    
    public void setPowiat(String powiat) {
        this.powiat = powiat;
    }
    
    public String getTelefonkontaktowy() {
        return telefonkontaktowy;
    }
    
    public void setTelefonkontaktowy(String telefonkontaktowy) {
        this.telefonkontaktowy = telefonkontaktowy;
    }
    
    public String getUlica() {
        return ulica;
    }
    
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }
    
    public String getUrzadskarbowy() {
        return urzadskarbowy;
    }
    
    public void setUrzadskarbowy(String urzadskarbowy) {
        this.urzadskarbowy = urzadskarbowy;
    }
    
    public List<Parametr> getVatokres() {
        return vatokres;
    }
    
    public void setVatokres(List<Parametr> vatokres) {
        this.vatokres = vatokres;
    }
    
    
    public String getWojewodztwo() {
        return wojewodztwo;
    }
    
    public void setWojewodztwo(String wojewodztwo) {
        this.wojewodztwo = wojewodztwo;
    }
    
    public List<Zusstawki> getZusparametr() {
        return zusparametr;
    }
    
    public void setZusparametr(List<Zusstawki> zusparametr) {
        this.zusparametr = zusparametr;
    }
    
    public List<Parametr> getRemanent() {
        return remanent;
    }
    
    public void setRemanent(List<Parametr> remanent) {
        this.remanent = remanent;
    }
    
    public List<Rodzajedok> getDokumentyksiegowe() {
        return dokumentyksiegowe;
    }
    
    public void setDokumentyksiegowe(List<Rodzajedok> dokumentyksiegowe) {
        this.dokumentyksiegowe = dokumentyksiegowe;
    }
    
    public List<Parametr> getKwotaautoryzujaca() {
        return kwotaautoryzujaca;
    }
    
    public void setKwotaautoryzujaca(List<Parametr> kwotaautoryzujaca) {
        this.kwotaautoryzujaca = kwotaautoryzujaca;
    }
    
    public String getPole47() {
        return pole47;
    }
    
    public void setPole47(String pole47) {
        this.pole47 = pole47;
    }
    
    public List getOpisypkpir() {
        return opisypkpir;
    }
    
    public void setOpisypkpir(List opisypkpir) {
        this.opisypkpir = opisypkpir;
    }
    
    public Boolean getOdliczaczus51() {
        return odliczaczus51;
    }
    
    public void setOdliczaczus51(Boolean odliczaczus51) {
        this.odliczaczus51 = odliczaczus51;
    }
    
    public List<Udzialy> getUdzialy() {
        return udzialy;
    }
    
    public void setUdzialy(List<Udzialy> udzialy) {
        this.udzialy = udzialy;
    }
    
    public List<Straty> getStratyzlatub() {
        return stratyzlatub;
    }
    
    public void setStratyzlatub(List<Straty> stratyzlatub) {
        this.stratyzlatub = stratyzlatub;
    }
    
    public List<Parametr> getNumerpkpir() {
        return numerpkpir;
    }
    
    public void setNumerpkpir(List<Parametr> numerpkpir) {
        this.numerpkpir = numerpkpir;
    }
    
    public String getNrkontabankowego() {
        return nrkontabankowego;
    }
    
    public void setNrkontabankowego(String nrkontabankowego) {
        this.nrkontabankowego = nrkontabankowego;
    }

    public String getPlatnoscwdni() {
        return platnoscwdni;
    }

    public void setPlatnoscwdni(String platnoscwdni) {
        this.platnoscwdni = platnoscwdni;
    }

    public String getNazwadlafaktury() {
        return nazwadlafaktury;
    }

    public void setNazwadlafaktury(String nazwadlafaktury) {
        this.nazwadlafaktury = nazwadlafaktury;
    }

    public String getAdresdlafaktury() {
        return adresdlafaktury;
    }

    public void setAdresdlafaktury(String adresdlafaktury) {
        this.adresdlafaktury = adresdlafaktury;
    }
    
    

    public Pozycjenafakturzebazadanych getWierszwzorcowy() {
        return wierszwzorcowy;
    }

    public void setWierszwzorcowy(Pozycjenafakturzebazadanych wierszwzorcowy) {
        this.wierszwzorcowy = wierszwzorcowy;
    }

    public String getMiejscewystawienia() {
        return miejscewystawienia;
    }

    public void setMiejscewystawienia(String miejscewystawienia) {
        this.miejscewystawienia = miejscewystawienia;
    }

    public List<Straty1> getStratyzlatub1() {
        return stratyzlatub1;
    }

    public void setStratyzlatub1(List<Straty1> stratyzlatub1) {
        this.stratyzlatub1 = stratyzlatub1;
    }
    
    
    
    
    //</editor-fold>
   
    
   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nip != null ? nip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Podatnik)) {
            return false;
        }
        Podatnik other = (Podatnik) object;
        if ((this.nip == null && other.nip != null) || (this.nip != null && !this.nip.equals(other.nip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Podatnik[ nip=" + nip + " ]";
    }
    
}
