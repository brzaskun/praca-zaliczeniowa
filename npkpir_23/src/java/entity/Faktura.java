/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faktura.findAll", query = "SELECT f FROM Faktura f"),
    @NamedQuery(name = "Faktura.findByWystawcanazwa", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByNumerkolejnyWystawcanazwa", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.numerkolejny = :numerkolejny AND f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRok", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRokMc", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok AND f.mc = :mc"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRokMcNiezaplacone", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok AND f.mc = :mc AND f.datazaplaty IS NULL"),
    @NamedQuery(name = "Faktura.findByWystawcanazwaRokMcZaplacone", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok AND f.mc = :mc AND f.datazaplaty IS NOT NULL"),
    @NamedQuery(name = "Faktura.findByNumerkolejny", query = "SELECT f FROM Faktura f WHERE f.fakturaPK.numerkolejny = :numerkolejny"),
    @NamedQuery(name = "Faktura.findByRodzajdokumentu", query = "SELECT f FROM Faktura f WHERE f.rodzajdokumentu = :rodzajdokumentu"),
    @NamedQuery(name = "Faktura.findByRodzajtransakcji", query = "SELECT f FROM Faktura f WHERE f.rodzajtransakcji = :rodzajtransakcji"),
    @NamedQuery(name = "Faktura.findByKontrahent_nip", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip"),
    @NamedQuery(name = "Faktura.findByKontrahent", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip AND f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByKontrahentRok", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip AND f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok = :rok ORDER BY f.datawystawienia"),
    @NamedQuery(name = "Faktura.findByKontrahentRokPo2015", query = "SELECT f FROM Faktura f WHERE f.kontrahent_nip = :kontrahent_nip AND f.fakturaPK.wystawcanazwa = :wystawcanazwa AND f.rok > 2015 ORDER BY f.datawystawienia"),
    @NamedQuery(name = "Faktura.findByRok", query = "SELECT f FROM Faktura f WHERE f.rok = :rok"),
    @NamedQuery(name = "Faktura.findByRokPodatnik", query = "SELECT f FROM Faktura f WHERE f.rok = :rok AND f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findByKonrahentPodatnik", query = "SELECT DISTINCT f.kontrahent FROM Faktura f WHERE f.wystawca = :podatnik"),
    @NamedQuery(name = "Faktura.liczByRokPodatnik", query = "SELECT COUNT(f) FROM Faktura f WHERE f.rok = :rok AND f.fakturaPK.wystawcanazwa = :wystawcanazwa"),
    @NamedQuery(name = "Faktura.findOstatniaFakturaByRokPodatnik", query = "SELECT f FROM Faktura f WHERE f.rok = :rok AND f.fakturaPK.wystawcanazwa = :wystawcanazwa ORDER BY f.lp DESC"),
    @NamedQuery(name = "Faktura.findByDatawystawienia", query = "SELECT f FROM Faktura f WHERE f.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Faktura.findByDatasprzedazy", query = "SELECT f FROM Faktura f WHERE f.datasprzedazy = :datasprzedazy"),
    @NamedQuery(name = "Faktura.findByMiejscewystawienia", query = "SELECT f FROM Faktura f WHERE f.miejscewystawienia = :miejscewystawienia"),
    @NamedQuery(name = "Faktura.findByTerminzaplaty", query = "SELECT f FROM Faktura f WHERE f.terminzaplaty = :terminzaplaty"),
    @NamedQuery(name = "Faktura.findBySposobzaplaty", query = "SELECT f FROM Faktura f WHERE f.sposobzaplaty = :sposobzaplaty"),
    @NamedQuery(name = "Faktura.findByNrkontabankowego", query = "SELECT f FROM Faktura f WHERE f.nrkontabankowego = :nrkontabankowego"),
    @NamedQuery(name = "Faktura.findByWalutafaktury", query = "SELECT f FROM Faktura f WHERE f.walutafaktury = :walutafaktury"),
    @NamedQuery(name = "Faktura.findByPodpis", query = "SELECT f FROM Faktura f WHERE f.podpis = :podpis"),
    @NamedQuery(name = "Faktura.findByZatwierdzona", query = "SELECT f FROM Faktura f WHERE f.zatwierdzona = :zatwierdzona"),
    @NamedQuery(name = "Faktura.findByWyslana", query = "SELECT f FROM Faktura f WHERE f.wyslana = :wyslana"),
    @NamedQuery(name = "Faktura.findByZaksiegowana", query = "SELECT f FROM Faktura f WHERE f.zaksiegowana = :zaksiegowana"),
    @NamedQuery(name = "Faktura.findByAutor", query = "SELECT f FROM Faktura f WHERE f.autor = :autor"),
    @NamedQuery(name = "Faktura.findBySchemat", query = "SELECT f FROM Faktura f WHERE f.schemat = :schemat"),
    @NamedQuery(name = "Faktura.findByNetto", query = "SELECT f FROM Faktura f WHERE f.netto = :netto"),
    @NamedQuery(name = "Faktura.findByVat", query = "SELECT f FROM Faktura f WHERE f.vat = :vat"),
    @NamedQuery(name = "Faktura.findByBrutto", query = "SELECT f FROM Faktura f WHERE f.brutto = :brutto")})
public class Faktura implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FakturaPK fakturaPK;
    @JoinColumn(name = "wystawca", referencedColumnName = "nip")
    @ManyToOne
    private Podatnik wystawca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String rodzajdokumentu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String rodzajtransakcji;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String kontrahent_nip;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontrahent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String datasprzedazy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String miejscewystawienia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String terminzaplaty;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String sposobzaplaty;
    @Column
    private String nrkontabankowego;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(nullable = false, length = 3)
    private String walutafaktury;
    @Basic(optional = true)
    @Column(nullable = true, length = 255)
    private String podpis;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<Pozycjenafakturzebazadanych> pozycjenafakturze;
    @Lob
    @Column
    private List<Pozycjenafakturzebazadanych> pozycjepokorekcie;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean zatwierdzona;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean wyslana;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean zaksiegowana;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String autor;
    @Size(max = 255)
    @Column(length = 255)
    private String schemat;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double netto;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double vat;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double brutto;
    @Column
    private double nettopk;
    @Column
    private double vatpk;
    @Column
    private double bruttopk;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(nullable = false)
    private List<EVatwpis> ewidencjavat;
    @Lob
    @Column
    private List<EVatwpis> ewidencjavatpk;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean wygenerowanaautomatycznie;
    @Column
    private String rok;
    @Column
    private String mc;
    @Column
    private String numerzamowienia;
    @Column
    private int lp;
    @Column
    private String datazaplaty;
    @Column(name = "fakturaxxl")
    private boolean fakturaxxl;
    @Column(name = "fakturaniemiecka13b")
    private boolean fakturaniemiecka13b;
    @Column(name = "fakturavatmarza")
    private boolean fakturavatmarza;
    @Column(length = 512)
    private String przyczynakorekty;
    @Column
    private int dnizaplaty;
    @Column
    private String nazwa;
    @Column(nullable = true)
    private Integer idfakturaokresowa;
    @Column(name = "stopka")
    private String stopka;
    @Column(name = "nowy0archiwum1")
    private boolean nowy0archiwum1;
    @Temporal(TemporalType.DATE)
    private Date datawysylki;
    @Column(name = "dataupomnienia", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataupomnienia;
    @Column(name = "datatelefon", insertable=true, updatable=true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datatelefon;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<FakturaDuplikat> duplikaty;
    @Column(name = "sprzedazsamochoduimportowanego", nullable = true)
    private boolean sprzedazsamochoduimportowanego;
    @Column(name = "markapojazdu", nullable = true)
    private String markapojazdu;
    @Column(name = "VIN", nullable = true)
    private String VIN;
    @Column(name = "datazakupusamochodu", nullable = true, length = 4)
    private String datazakupusamochodu;
    @Column(name = "samochodbeztablic")
    private boolean samochodbeztablic;
    @Column(name = "samochodtablicezwrocone")
    private boolean samochodtablicezwrocone;
    @Column(name = "liczodwartoscibrutto")
    private boolean liczodwartoscibrutto;
    @Column(name = "gutschrift")
    private boolean gutschrift;
    @Column(name = "zaplacona")
    private boolean zaplacona;
    @Column(name = "zaliczkowa")
    private boolean zaliczkowa;

    public Faktura() {
    }

    public Faktura(Faktura stara) {
        this.fakturaPK =  stara.fakturaPK;
        this.wystawca =  stara.wystawca;
        this.rodzajdokumentu =  stara.rodzajdokumentu;
        this.rodzajtransakcji =  stara.rodzajtransakcji;
        this.kontrahent =  stara.kontrahent;
        this.kontrahent_nip =  stara.kontrahent_nip;
        this.datawystawienia =  stara.datawystawienia;
        this.datasprzedazy =  stara.datasprzedazy;
        this.miejscewystawienia =  stara.miejscewystawienia;
        this.terminzaplaty =  stara.terminzaplaty;
        this.sposobzaplaty =  stara.sposobzaplaty;
        this.nrkontabankowego =  stara.nrkontabankowego;
        this.walutafaktury =  stara.walutafaktury;
        this.podpis =  stara.podpis;
        this.pozycjenafakturze =  stara.pozycjenafakturze;
        this.zatwierdzona =  stara.zatwierdzona;
        this.wyslana =  stara.wyslana;
        this.zaksiegowana =  stara.zaksiegowana;
        this.autor =  stara.autor;
        this.schemat =  stara.schemat;
        this.netto =  stara.netto;
        this.vat =  stara.vat;
        this.brutto =  stara.brutto;
        this.ewidencjavat =  stara.ewidencjavat;
        this.rok = stara.rok;
        this.mc = stara.mc;
        this.numerzamowienia = stara.numerzamowienia;
        this.fakturaxxl = stara.fakturaxxl;
        this.fakturaniemiecka13b = stara.fakturaniemiecka13b;
        this.fakturavatmarza = stara.fakturavatmarza;
        this.datawysylki = null;
        this.duplikaty = new ArrayList<>();
    }

    

    public Faktura(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
    }

    public Faktura(FakturaPK fakturaPK, Podatnik wystawca, String rodzajdokumentu, String rodzajtransakcji, Klienci kontrahent, 
            String datawystawienia, String datasprzedazy, String miejscewystawienia, String terminzaplaty, String sposobzaplaty, 
            String nrkontabankowego, String walutafaktury, String podpis, List<Pozycjenafakturzebazadanych> pozycjenafakturze, 
            boolean zatwierdzona, boolean wyslana, boolean zaksiegowana, String autor, double netto, double vat, double brutto, 
            List<EVatwpis> ewidencjavat, String rok, String mc, String numerzamowienia) {
        this.fakturaPK = fakturaPK;
        this.wystawca = wystawca;
        this.rodzajdokumentu = rodzajdokumentu;
        this.rodzajtransakcji = rodzajtransakcji;
        this.kontrahent = kontrahent;
        this.datawystawienia = datawystawienia;
        this.datasprzedazy = datasprzedazy;
        this.miejscewystawienia = miejscewystawienia;
        this.terminzaplaty = terminzaplaty;
        this.sposobzaplaty = sposobzaplaty;
        this.nrkontabankowego = nrkontabankowego;
        this.walutafaktury = walutafaktury;
        this.podpis = podpis;
        this.pozycjenafakturze = pozycjenafakturze;
        this.zatwierdzona = zatwierdzona;
        this.wyslana = wyslana;
        this.zaksiegowana = zaksiegowana;
        this.autor = autor;
        this.netto = netto;
        this.vat = vat;
        this.brutto = brutto;
        this.ewidencjavat = ewidencjavat;
        this.rok = rok;
        this.mc = mc;
        this.numerzamowienia = numerzamowienia;
        this.datawysylki = null;
        this.duplikaty = new ArrayList<>();
    }

    public Faktura(String wystawcanazwa, String numerkolejny) {
        this.fakturaPK = new FakturaPK(wystawcanazwa, numerkolejny);
    }

    //<editor-fold defaultstate="collapsed" desc="comment">

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean isZaplacona() {
        return zaplacona;
    }

    public void setZaplacona(boolean zaplacona) {
        this.zaplacona = zaplacona;
    }

    public boolean isLiczodwartoscibrutto() {
        return liczodwartoscibrutto;
    }

    public void setLiczodwartoscibrutto(boolean liczodwartoscibrutto) {
        this.liczodwartoscibrutto = liczodwartoscibrutto;
    }

    public boolean isGutschrift() {
        return gutschrift;
    }

    public void setGutschrift(boolean gutschrift) {
        this.gutschrift = gutschrift;
    }

    public boolean isSprzedazsamochoduimportowanego() {
        return sprzedazsamochoduimportowanego;
    }

    public void setSprzedazsamochoduimportowanego(boolean sprzedazsamochoduimportowanego) {
        this.sprzedazsamochoduimportowanego = sprzedazsamochoduimportowanego;
    }

    public String getMarkapojazdu() {
        return markapojazdu;
    }

    public void setMarkapojazdu(String markapojazdu) {
        this.markapojazdu = markapojazdu;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getDatazakupusamochodu() {
        return datazakupusamochodu;
    }

    public void setDatazakupusamochodu(String datazakupusamochodu) {
        this.datazakupusamochodu = datazakupusamochodu;
    }

    public boolean isSamochodbeztablic() {
        return samochodbeztablic;
    }

    public void setSamochodbeztablic(boolean samochodbeztablic) {
        this.samochodbeztablic = samochodbeztablic;
    }

    public boolean isSamochodtablicezwrocone() {
        return samochodtablicezwrocone;
    }

    public void setSamochodtablicezwrocone(boolean samochodtablicezwrocone) {
        this.samochodtablicezwrocone = samochodtablicezwrocone;
    }

    public String getOpisFaktury() {
        String zwrot = null;
        if (this.nazwa != null && !this.nazwa.equals("")) {
            zwrot = nazwa;
        } else if (pozycjenafakturze != null && pozycjenafakturze.size() > 0) {
            zwrot = pozycjenafakturze.get(0).getNazwa();
        }
        return zwrot;
    }
    
    public double getNettoFaktura() {
        double zwrot = this.netto;
        if (this.pozycjepokorekcie != null && this.pozycjepokorekcie.size() > 0) {
            zwrot = this.nettopk-this.netto;
        }
        return zwrot;
    }
    
    public double getBruttoFaktura() {
        double zwrot = this.brutto;
        if (this.pozycjepokorekcie != null && this.pozycjepokorekcie.size() > 0) {
            zwrot = this.bruttopk-this.brutto;
        }
        return zwrot;
    }
    
    public boolean isFakturaNormalna() {
        boolean fakturaNormalna = true;
        if (this.fakturaxxl) {
            fakturaNormalna = false;
        }
        if (this.fakturaniemiecka13b) {
            fakturaNormalna = false;
        }
        if (this.fakturavatmarza) {
            fakturaNormalna = false;
        }
        return fakturaNormalna;
    }
    
    public Date getDatatelefon() {
        return datatelefon;
    }

    public void setDatatelefon(Date datatelefon) {
        this.datatelefon = datatelefon;
    }

    public Date getDataupomnienia() {
        return dataupomnienia;
    }

    public void setDataupomnienia(Date dataupomnienia) {
        this.dataupomnienia = dataupomnienia;
    }

    public Date getDatawysylki() {
        return datawysylki;
    }

    public void setDatawysylki(Date datawysylki) {
        this.datawysylki = datawysylki;
    }

    public boolean isNowy0archiwum1() {
        return nowy0archiwum1;
    }

    public void setNowy0archiwum1(boolean nowy0archiwum1) {
        this.nowy0archiwum1 = nowy0archiwum1;
    }

    public String getStopka() {
        return stopka;
    }

    public void setStopka(String stopka) {
        this.stopka = stopka;
    }

    public Integer getIdfakturaokresowa() {
        return idfakturaokresowa;
    }

    public void setIdfakturaokresowa(Integer idfakturaokresowa) {
        this.idfakturaokresowa = idfakturaokresowa;
    }
    

    public int getDnizaplaty() {
        return dnizaplaty;
    }

    public void setDnizaplaty(int dnizaplaty) {
        this.dnizaplaty = dnizaplaty;
    }
    

    public double getNettopk() {
        return nettopk;
    }

    public void setNettopk(double nettopk) {
        this.nettopk = nettopk;
    }

    public double getVatpk() {
        return vatpk;
    }

    public void setVatpk(double vatpk) {
        this.vatpk = vatpk;
    }

    public double getBruttopk() {
        return bruttopk;
    }

    public void setBruttopk(double bruttopk) {
        this.bruttopk = bruttopk;
    }

    public List<EVatwpis> getEwidencjavatpk() {
        return ewidencjavatpk;
    }

    public void setEwidencjavatpk(List<EVatwpis> ewidencjavatpk) {
        this.ewidencjavatpk = ewidencjavatpk;
    }
    

    public List<Pozycjenafakturzebazadanych> getPozycjepokorekcie() {
        return pozycjepokorekcie;
    }

    public void setPozycjepokorekcie(List<Pozycjenafakturzebazadanych> pozycjepokorekcie) {
        this.pozycjepokorekcie = pozycjepokorekcie;
    }

    public List<FakturaDuplikat> getDuplikaty() {
        return duplikaty;
    }

    public void setDuplikaty(List<FakturaDuplikat> duplikaty) {
        this.duplikaty = duplikaty;
    }
    

    public String getDatazaplaty() {
        return datazaplaty;
    }

    public void setDatazaplaty(String datazaplaty) {
        this.datazaplaty = datazaplaty;
    }
    
    
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public boolean isZaliczkowa() {
        return zaliczkowa;
    }

    public void setZaliczkowa(boolean zaliczkowa) {
        this.zaliczkowa = zaliczkowa;
    }

    public FakturaPK getFakturaPK() {
        return fakturaPK;
    }
    
    public void setFakturaPK(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
    }

    public boolean isFakturaniemiecka13b() {
        return fakturaniemiecka13b;
    }

    public void setFakturaniemiecka13b(boolean fakturaniemiecka13b) {
        this.fakturaniemiecka13b = fakturaniemiecka13b;
    }
    
    public Podatnik getWystawca() {
        return wystawca;
    }
    
    public void setWystawca(Podatnik wystawca) {
        this.wystawca = wystawca;
    }
    
    public String getRodzajdokumentu() {
        return rodzajdokumentu;
    }
    
    public void setRodzajdokumentu(String rodzajdokumentu) {
        this.rodzajdokumentu = rodzajdokumentu;
    }
    
    public String getRodzajtransakcji() {
        return rodzajtransakcji;
    }
    
    public void setRodzajtransakcji(String rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }
    
    public Klienci getKontrahent() {
        return kontrahent;
    }
    
    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }
    
    public String getKontrahent_nip() {
        return kontrahent_nip;
    }
    
    public void setKontrahent_nip(String kontrahent_nip) {
        this.kontrahent_nip = kontrahent_nip;
    }
    
    
    public String getDatawystawienia() {
        return datawystawienia;
    }
    
    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }
    
    public String getDatasprzedazy() {
        return datasprzedazy;
    }
    
    public void setDatasprzedazy(String datasprzedazy) {
        this.datasprzedazy = datasprzedazy;
    }
    
    public String getMiejscewystawienia() {
        return miejscewystawienia;
    }
    
    public void setMiejscewystawienia(String miejscewystawienia) {
        this.miejscewystawienia = miejscewystawienia;
    }
    
    public String getTerminzaplaty() {
        return terminzaplaty;
    }
    
    public void setTerminzaplaty(String terminzaplaty) {
        this.terminzaplaty = terminzaplaty;
    }
    
    public String getSposobzaplaty() {
        return sposobzaplaty;
    }
    
    public void setSposobzaplaty(String sposobzaplaty) {
        this.sposobzaplaty = sposobzaplaty;
    }
    
    public String getNrkontabankowego() {
        return nrkontabankowego;
    }
    
    public void setNrkontabankowego(String nrkontabankowego) {
        this.nrkontabankowego = nrkontabankowego;
    }
    
    public String getWalutafaktury() {
        return walutafaktury;
    }
    
    public void setWalutafaktury(String walutafaktury) {
        this.walutafaktury = walutafaktury;
    }
    
    public String getPodpis() {
        return podpis;
    }
    
    public void setPodpis(String podpis) {
        this.podpis = podpis;
    }
    
    public List<Pozycjenafakturzebazadanych> getPozycjenafakturze() {
        return pozycjenafakturze;
    }
    
    public void setPozycjenafakturze(List<Pozycjenafakturzebazadanych> pozycjenafakturze) {
        this.pozycjenafakturze = pozycjenafakturze;
    }

    public boolean getZatwierdzona() {
        return zatwierdzona;
    }
    
    public void setZatwierdzona(boolean zatwierdzona) {
        this.zatwierdzona = zatwierdzona;
    }
    
    public boolean getWyslana() {
        return wyslana;
    }
    
    public void setWyslana(boolean wyslana) {
        this.wyslana = wyslana;
    }
    
    public boolean getZaksiegowana() {
        return zaksiegowana;
    }
    
    public void setZaksiegowana(boolean zaksiegowana) {
        this.zaksiegowana = zaksiegowana;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getSchemat() {
        return schemat;
    }
    
    public void setSchemat(String schemat) {
        this.schemat = schemat;
    }
    
    public double getNetto() {
        return netto;
    }
    
    public void setNetto(double netto) {
        this.netto = netto;
    }
    
    public double getVat() {
        return vat;
    }
    
    public void setVat(double vat) {
        this.vat = vat;
    }
    
    public double getBrutto() {
        return brutto;
    }
    
    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }
    
    public List<EVatwpis> getEwidencjavat() {
        return ewidencjavat;
    }
    
    public void setEwidencjavat(List<EVatwpis> ewidencjavat) {
        this.ewidencjavat = ewidencjavat;
    }

    public boolean isFakturavatmarza() {
        return fakturavatmarza;
    }

    public void setFakturavatmarza(boolean fakturavatmarza) {
        this.fakturavatmarza = fakturavatmarza;
    }
    
    
    
    public boolean isWygenerowanaautomatycznie() {
        return wygenerowanaautomatycznie;
    }
    
    public void setWygenerowanaautomatycznie(boolean wygenerowanaautomatycznie) {
        this.wygenerowanaautomatycznie = wygenerowanaautomatycznie;
    }
    
    public String getRok() {
        return rok;
    }
    
    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public String getMc() {
        return mc;
    }
    
    public void setMc(String mc) {
        this.mc = mc;
    }
    
    public String getNumerzamowienia() {
        return numerzamowienia;
    }
    
    public void setNumerzamowienia(String numerzamowienia) {
        this.numerzamowienia = numerzamowienia;
    }
    
    

    public boolean isFakturaxxl() {
        return fakturaxxl;
    }

    public void setFakturaxxl(boolean fakturaxxl) {
        this.fakturaxxl = fakturaxxl;
    }

    public String getPrzyczynakorekty() {
        return przyczynakorekty;
    }

    public void setPrzyczynakorekty(String przyczynakorekty) {
        this.przyczynakorekty = przyczynakorekty;
    }
    
    public String getOkresCit() {
        return this.rok+" "+this.mc;
    }
    
    public String getOkresVat() {
        return Data.getRok(this.datasprzedazy)+" "+Data.getMc(this.datasprzedazy);
    }
    
    
//</editor-fold>
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fakturaPK != null ? fakturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faktura)) {
            return false;
        }
        Faktura other = (Faktura) object;
        if ((this.fakturaPK == null && other.fakturaPK != null) || (this.fakturaPK != null && !this.fakturaPK.equals(other.fakturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Faktura{" + "fakturaPK=" + fakturaPK + "kontrahent "+ kontrahent.getNpelna() +"}";
    }


//    public Integer getKontrahentID() {
//        return kontrahentID;
//    }
//
//    public void setKontrahentID(Integer kontrahentID) {
//        this.kontrahentID = kontrahentID;
//    }
//
//    public String getWystawcaNIP() {
//        return wystawcaNIP;
//    }
//
//    public void setWystawcaNIP(String wystawcaNIP) {
//        this.wystawcaNIP = wystawcaNIP;
//    }

    
   
    
}
