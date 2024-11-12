/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import embeddablefk.ImportJPKSprzedaz;
import embeddablefk.InterpaperXLS;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */

@Entity
@Table(name = "klientJPK")
@NamedQueries({
    @NamedQuery(name = "KlientJPK.deletePodRokMc", query = "DELETE FROM KlientJPK a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc"),
    @NamedQuery(name = "KlientJPK.deletePodRokMcAmazon", query = "DELETE FROM KlientJPK a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc AND a.amazontax0additional1 = :amazontax0additional1"),
    @NamedQuery(name = "KlientJPK.findByPodRokMc", query = "SELECT a FROM KlientJPK a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc"),
    @NamedQuery(name = "KlientJPK.findByPodRokMcAmazon", query = "SELECT a FROM KlientJPK a WHERE a.podatnik = :podatnik AND a.rok = :rok AND a.mc = :mc AND a.amazontax0additional1 = :amazontax0additional1")
})
public class KlientJPK implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "serial")
    private String serial;
    @Column(name = "kodkrajunadania")
    private String kodKrajuNadania;
    @Column(name = "kodkrajudoreczenia")
    private String kodKrajuDoreczenia;
    @Column(name = "jurysdykcja")
    private String jurysdykcja;
    @Column(name = "nrkontrahenta")
    private String nrKontrahenta;
    @Column(name = "nazwakontrahenta")
    private String nazwaKontrahenta;
    @Column(name = "dowodsprzedazy")
    private String dowodSprzedazy;
    @Column(name = "datawystawienia")
    private String dataWystawienia;
    @Column(name = "datadprzedazy")
    private String dataSprzedazy;
    @Column(name = "netto")
    private double netto;
    @Column(name = "vat")
    private double vat;
     @Column(name = "nettowaluta")
    private double nettowaluta;
    @Column(name = "vatwaluta")
    private double vatwaluta;
    @Column(name = "stawkavat")
    private double stawkavat;
    @Column(name = "kurs")
    private double kurs;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 2)
    @Column(name = "mc")
    private String mc;
    @JoinColumn(name = "evewidencja", referencedColumnName = "id")
    @ManyToOne
    protected Evewidencja ewidencja;
    @OneToMany(mappedBy = "klientJPK", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EVatwpisKJPK> ewidencjaVAT;
    @Column(name = "waluta")
    private String waluta;
    @Column(name = "wdt")
    private boolean wdt;
    @Column(name = "wnt")
    private boolean wnt;
    @Column(name = "eksport")
    private boolean eksport;
    @Column(name = "importt")
    private boolean importt;
    @Column(name = "rodzajtransakcji")
    private String rodzajtransakcji;
    @Column(name = "amazontax0additional1")
    private int amazontax0additional1;
    @Column(name="opissprzedaz")
    private String opissprzedaz;

    public KlientJPK() {
        this.ewidencjaVAT = new ArrayList<>();
    }

    
    public KlientJPK(Dok d, Podatnik podatnik, String rok, String mc) {
        this.dataSprzedazy = d.getDataSprz();
        this.dataWystawienia = d.getDataWyst();
        this.dowodSprzedazy = d.getNrWlDk();
        this.nrKontrahenta = "brak";
        this.nazwaKontrahenta = d.getKontr1().getNpelna()==null||d.getKontr1().getNpelna().equals("")?"BRAK":d.getKontr1().getNpelna();
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
        if (d.getEwidencjaVAT1()==null) {
            System.out.println("");
        }
        this.ewidencja = d.getEwidencjaVAT1().get(0).getEwidencja();
        this.netto = d.getNetto();
        this.vat = d.getVat();
        this.waluta = d.getSymbolWaluty();
        this.ewidencjaVAT = new ArrayList<>();
        this.opissprzedaz = "FP";
    }
    
    public KlientJPK(Dokfk d, Podatnik podatnik, String rok, String mc) {
        this.dataSprzedazy = d.getDataoperacji();
        this.dataWystawienia = d.getDatawystawienia();
        this.dowodSprzedazy = d.getNumerwlasnydokfk();
        this.nrKontrahenta = d.getKontr().getNip()==null||d.getKontr().getNip().equals("")?"brak":d.getKontr().getNip();
        this.nazwaKontrahenta = d.getKontr().getNpelna()==null||d.getKontr().getNpelna().equals("")?"brak":d.getKontr().getNpelna();
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
        this.ewidencja = d.getEwidencjaVAT().get(0).getEwidencja();
        this.netto = d.getNettoVAT();
        this.vat = d.getVATVAT();
        this.waluta = d.getWalutadokumentu().getSymbolwaluty();
        this.ewidencjaVAT = new ArrayList<>();
        this.opissprzedaz = "FP";
    }

    public KlientJPK(InterpaperXLS d, Podatnik podatnik, String rok, String mc) {
        this.dataSprzedazy = Data.data_yyyyMMdd(d.getDatasprzedaży());
        this.dataWystawienia = Data.data_yyyyMMdd(d.getDatawystawienia());
        this.dowodSprzedazy = d.getNrfaktury();
        this.nrKontrahenta = "brak";
        this.nazwaKontrahenta = d.getKontrahent()==null||d.getKontrahent().equals("")?"BRAK":d.getKontrahent();
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
        this.ewidencja = d.getEvewidencja();
        this.netto = d.getNettoPLN();
        this.vat = d.getVatPLN();
        this.waluta = d.getWalutaplatnosci();
        this.ewidencjaVAT = new ArrayList<>();
        this.opissprzedaz = "FP";
    }

    public KlientJPK(KlientJPK a) {
        this.serial = a.serial;
        this.kodKrajuNadania = a.kodKrajuNadania;
        this.kodKrajuDoreczenia = a.kodKrajuDoreczenia;
        this.jurysdykcja = a.jurysdykcja;
        this.nrKontrahenta = a.nrKontrahenta;
        this.nazwaKontrahenta = a.nazwaKontrahenta;
        this.dowodSprzedazy = a.dowodSprzedazy;
        this.dataWystawienia = a.dataWystawienia;
        this.dataSprzedazy = a.dataSprzedazy;
        this.netto = a.netto;
        this.vat = a.vat;
        this.nettowaluta = a.nettowaluta;
        this.vatwaluta = a.vatwaluta;
        this.stawkavat = a.stawkavat;
        this.kurs = a.kurs;
        this.podatnik = a.podatnik;
        this.rok = a.rok;
        this.mc = a.mc;
        this.ewidencja = a.ewidencja;
        this.ewidencjaVAT = new ArrayList<>();
        this.waluta = a.waluta;
        this.wdt = a.wdt;
        this.wnt = a.wnt;
        this.eksport = a.eksport;
        this.importt = a.importt;
    }

    public KlientJPK(ImportJPKSprzedaz d, Podatnik podatnik, String rok, String mc) {
        this.dataSprzedazy = Data.calendarToString(d.getSprzedazWiersz().getDataSprzedazy());
        this.dataWystawienia = Data.calendarToString(d.getSprzedazWiersz().getDataWystawienia());
        this.dowodSprzedazy = d.getSprzedazWiersz().getDowodSprzedazy();
        this.nrKontrahenta = "brak";
        this.nazwaKontrahenta = d.getKlient()==null||d.getKlient().equals("")?"BRAK":d.getKlient().getNazwapodatnika();
        this.podatnik = podatnik;
        this.rok = rok;
        this.mc = mc;
        this.netto = d.getSprzedazWiersz().getNetto();
        this.vat = d.getSprzedazWiersz().getVat();
        this.waluta = "PLN";
        this.ewidencjaVAT = new ArrayList<>();
        this.stawkavat = (double) d.getSprzedazWiersz().getStawka();
        this.opissprzedaz = "FP";
    }

  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getKodKrajuNadania() {
        return kodKrajuNadania;
    }

    public void setKodKrajuNadania(String kodKrajuNadania) {
        this.kodKrajuNadania = kodKrajuNadania;
    }

    public String getNrKontrahenta() {
        return nrKontrahenta;
    }

    public String getKodKrajuDoreczenia() {
        return kodKrajuDoreczenia;
    }

    public void setKodKrajuDoreczenia(String kodKrajuDoreczenia) {
        this.kodKrajuDoreczenia = kodKrajuDoreczenia;
    }

    public void setNrKontrahenta(String nrKontrahenta) {
        this.nrKontrahenta = nrKontrahenta;
    }

    public String getNazwaKontrahenta() {
        return nazwaKontrahenta;
    }

    public void setNazwaKontrahenta(String nazwaKontrahenta) {
        this.nazwaKontrahenta = nazwaKontrahenta;
    }

    public int getAmazontax0additional1() {
        return amazontax0additional1;
    }

    public void setAmazontax0additional1(int amazontax0additional1) {
        this.amazontax0additional1 = amazontax0additional1;
    }

    public String getDowodSprzedazy() {
        return dowodSprzedazy;
    }

    public void setDowodSprzedazy(String dowodSprzedazy) {
        this.dowodSprzedazy = dowodSprzedazy;
    }

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(String dataWystawienia) {
        this.dataWystawienia = dataWystawienia;
    }

    public String getJurysdykcja() {
        return jurysdykcja;
    }

    public void setJurysdykcja(String jurysdykcja) {
        this.jurysdykcja = jurysdykcja;
    }

    public String getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(String dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
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

    public Evewidencja getEwidencja() {
        return ewidencja;
    }

    public void setEwidencja(Evewidencja ewidencja) {
        this.ewidencja = ewidencja;
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

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public double getVatwaluta() {
        return vatwaluta;
    }

    public void setVatwaluta(double vatwaluta) {
        this.vatwaluta = vatwaluta;
    }

    public double getNettowaluta() {
        return nettowaluta;
    }

    public void setNettowaluta(double nettowaluta) {
        this.nettowaluta = nettowaluta;
    }

    public double getStawkavat() {
        return stawkavat;
    }

    public void setStawkavat(double stawkavat) {
        this.stawkavat = stawkavat;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public boolean isWdt() {
        return wdt;
    }

    public void setWdt(boolean wdt) {
        this.wdt = wdt;
    }

    public boolean isWnt() {
        return wnt;
    }

    public void setWnt(boolean wnt) {
        this.wnt = wnt;
    }

    public List<EVatwpisKJPK> getEwidencjaVAT() {
        return ewidencjaVAT;
    }

    public void setEwidencjaVAT(List<EVatwpisKJPK> ewidencjaVAT) {
        this.ewidencjaVAT = ewidencjaVAT;
    }

    public boolean isEksport() {
        return eksport;
    }

    public void setEksport(boolean eksport) {
        this.eksport = eksport;
    }

    public boolean isImportt() {
        return importt;
    }

    public void setImportt(boolean importt) {
        this.importt = importt;
    }

    public String getOpissprzedaz() {
        return opissprzedaz;
    }

    public void setOpissprzedaz(String opissprzedaz) {
        this.opissprzedaz = opissprzedaz;
    }

    public String getRodzajtransakcji() {
        return rodzajtransakcji;
    }

    public void setRodzajtransakcji(String rodzajtransakcji) {
        this.rodzajtransakcji = rodzajtransakcji;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.dowodSprzedazy);
        hash = 67 * hash + Objects.hashCode(this.podatnik);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KlientJPK other = (KlientJPK) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nazwaKontrahenta, other.nazwaKontrahenta)) {
            return false;
        }
        if (!Objects.equals(this.dowodSprzedazy, other.dowodSprzedazy)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (ewidencja!=null) {
            return "KlientJPK{" + "serial=" + serial + ", kodKrajuNadania=" + kodKrajuNadania + ", kodKrajuDoreczenia=" + kodKrajuDoreczenia + ", jurysdykcja=" + jurysdykcja + ", nrKontrahenta=" + nrKontrahenta + ", nazwaKontrahenta=" + nazwaKontrahenta + ", dowodSprzedazy=" + dowodSprzedazy + ", dataWystawienia=" + dataWystawienia + ", dataSprzedazy=" + dataSprzedazy + ", netto=" + netto + ", vat=" + vat + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + ", stawkavat=" + stawkavat + ", rok=" + rok + ", mc=" + mc + ", ewidencja=" + ewidencja .getNazwa() + ", waluta=" + waluta + ", wdt=" + wdt + ", wnt=" + wnt + ", eksport=" + eksport + ", importt=" + importt + '}';
        }  else {
             return "KlientJPK{" + "serial=" + serial + ", kodKrajuNadania=" + kodKrajuNadania + ", kodKrajuDoreczenia=" + kodKrajuDoreczenia + ", jurysdykcja=" + jurysdykcja + ", nrKontrahenta=" + nrKontrahenta + ", nazwaKontrahenta=" + nazwaKontrahenta + ", dowodSprzedazy=" + dowodSprzedazy + ", dataWystawienia=" + dataWystawienia + ", dataSprzedazy=" + dataSprzedazy + ", netto=" + netto + ", vat=" + vat + ", nettowaluta=" + nettowaluta + ", vatwaluta=" + vatwaluta + ", stawkavat=" + stawkavat + ", rok=" + rok + ", mc=" + mc + ", waluta=" + waluta + ", wdt=" + wdt + ", wnt=" + wnt + ", eksport=" + eksport + ", importt=" + importt + '}';
        }
}
    }

    

    

   
    

