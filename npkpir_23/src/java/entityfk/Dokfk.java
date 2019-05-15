/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import data.Data;
import embeddable.Kwartaly;
import embeddable.Mce;
import entity.DokSuper;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.Vat27;
import entity.VatUe;
import entity.WniosekVATZDEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import view.WpisView; import org.primefaces.PrimeFaces;
import viewfk.subroutines.ObslugaWiersza;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numerwlasnydokfk", "podid", "rok", "seriadokfk", "kontr"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findById", query = "SELECT d FROM Dokfk d WHERE d.id = :id"),
    @NamedQuery(name = "Dokfk.findBySeriadokfk", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk"),
    @NamedQuery(name = "Dokfk.findBySeriaRokdokfk", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok"),
    @NamedQuery(name = "Dokfk.findBySeriaNumerRokdokfk", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.podatnikObj = :podatnik AND d.miesiac = :mc"),
    @NamedQuery(name = "Dokfk.findByNrkolejny", query = "SELECT d FROM Dokfk d WHERE d.nrkolejnywserii = :nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokMc", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.miesiac = :mc"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokMcVAT", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.vatR = :rok AND d.vatM = :mc"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokKw", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND (d.miesiac = :mc1 OR d.miesiac = :mc2 OR d.miesiac = :mc3)"),
    @NamedQuery(name = "Dokfk.findByPodatnikRok", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok"),
    @NamedQuery(name = "Dokfk.findByRok", query = "SELECT d FROM Dokfk d WHERE d.rok = :rok"),
    @NamedQuery(name = "Dokfk.findByPodatnik", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokSrodkiTrwale", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.zawierasrodkitrw = 1"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokRMK", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.zawierarmk = 1"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokMcKategoria", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.miesiac = :mc AND d.rodzajedok.skrot = :kategoria"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokKategoria", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.rodzajedok.skrot = :kategoria"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokKategoriaOrderByNo", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.rok = :rok AND d.rodzajedok.skrot = :kategoria ORDER BY d.nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByBKVAT", query = "SELECT d FROM Dokfk d WHERE d.vatR = :vatR AND d.podatnikObj = :podatnik"),
    @NamedQuery(name = "Dokfk.findByDokfkPK", query = "SELECT d FROM Dokfk d WHERE d = :dokfkPK"),
    @NamedQuery(name = "Dokfk.findByPK", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.podatnikObj = :podatnikObj AND d.nrkolejnywserii = :nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByDuplikat", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk"),
    @NamedQuery(name = "Dokfk.findByDokEdycjaFK", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk AND d.kontr = :kontrahent"),
    @NamedQuery(name = "Dokfk.findByDuplikatKontrahent", query = "SELECT d FROM Dokfk d WHERE d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk AND d.kontr = :kontrahent"),
    @NamedQuery(name = "Dokfk.findByDatawystawienia", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Dokfk.findByDatawystawieniaNumer", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia AND d.numerwlasnydokfk = :numer"),
    @NamedQuery(name = "Dokfk.findByLastofaType", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.seriadokfk = :seriadokfk AND d.rok = :rok ORDER BY d.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByLastofaTypeMc", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.seriadokfk = :seriadokfk AND d.rok = :rok AND d.miesiac = :mc ORDER BY d.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByLastofaTypeKontrahent", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.seriadokfk = :seriadokfk AND d.kontr = :kontr AND d.rok = :rok ORDER BY d.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numerwlasnydokfk = :numer"),
    @NamedQuery(name = "Dokfk.znajdzDokumentPodatnikWpr", query = "SELECT DISTINCT d.podatnikObj.nazwapelna FROM Dokfk d WHERE d.wprowadzil = :wprowadzil"),
    @NamedQuery(name = "Dokfk.znajdzSeriePodatnik", query = "SELECT DISTINCT d.seriadokfk FROM Dokfk d WHERE d.rok = :rok AND d.podatnikObj = :podatnik")
})
@Cacheable(false)
public class Dokfk extends DokSuper implements Serializable {

    private static final long serialVersionUID = 1L; //dd
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "seriadokfk", nullable = false, length = 15)
    private String seriadokfk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nrkolejnywserii", nullable = false)
    private int nrkolejnywserii;
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "rok", nullable = false, length = 4)
    private String rok;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rodzajdok", referencedColumnName = "id")
    private Rodzajedok rodzajedok;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datawystawienia", length = 10)
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datadokumentu", length = 10)
    private String datadokumentu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataoperacji", length = 10)
    private String dataoperacji;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datawplywu", length = 10)
    private String datawplywu;
    @Size(max = 10)
    @Column(name = "termin_platnosci")
    private String terminPlatnosci;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numerwlasnydokfk", nullable = false, length = 255)
    private String numerwlasnydokfk;
    @OneToMany(mappedBy = "dokfk", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("idporzadkowy")
    private List<Wiersz> listawierszy;
    @Column(name = "miesiac")
    private String miesiac;
    @Column(name = "opisdokfk")
    private String opisdokfk;
    @JoinColumn(name = "walutadokumentu", referencedColumnName = "idwaluty")
    @ManyToOne
    private Waluty walutadokumentu;
    @Column(name = "zablokujzmianewaluty")
    private boolean zablokujzmianewaluty;
    @Column(name = "liczbarozliczonych")
    private int liczbarozliczonych;
    @JoinColumn(name = "TABELANBP_idtabelanbp", referencedColumnName = "idtabelanbp")
    @ManyToOne
    private Tabelanbp tabelanbp;
    @Column(name = "wartoscdokumentu")
    private double wartoscdokumentu;
    @Column(name = "wtrakcieedycji")
    private boolean wTrakcieEdycji;
    @JoinColumn(name = "kontr", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Klienci kontr;
    @OneToMany(mappedBy = "dokfk", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EVatwpisFK> ewidencjaVAT;
    @Size(max = 2)
    @Column(name = "vat_m")
    private String vatM;
    @Size(max = 4)
    @Column(name = "vat_r")
    private String vatR;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Dokfk_Cechazapisu",
            joinColumns = {
                @JoinColumn(name = "dokid", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "idcecha", referencedColumnName = "id"),
            })
    private List<Cechazapisu> cechadokumentuLista;
    @Column(name = "nrdziennika")
    private String nrdziennika;
    @Column(name = "importowany")
    private boolean importowany;
    @Column(name = "lp")
    private int lp;
    @Column(name = "wzorzec")
    private boolean wzorzec;
    @Column(name = "saldopoczatkowe")
    private double saldopoczatkowe;
    @Column(name = "saldokoncowe")
    private double saldokoncowe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wprowadzil")
    private String wprowadzil;
    @Column(name = "zawierasrodkitrw")
    private boolean zawierasrodkitrw;
    @Column(name = "zawierarmk")
    private boolean zawierarmk;
    @Column(name = "zawierarozkurs")
    private boolean zawierarozkurs;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataksiegowania")
    private Date dataksiegowania;
    @Column(name = "dataujecia")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataujecia;
    @JoinColumn(name = "vatue", referencedColumnName = "id")
    @OneToOne
    private VatUe vatUe;
    @JoinColumn(name = "vat27", referencedColumnName = "id")
    @OneToOne
    private Vat27 vat27;
    @JoinColumn(name = "wniosekVATZDEntity", referencedColumnName = "id")
    @OneToOne
    private WniosekVATZDEntity wniosekVATZDEntity;



    public Dokfk() {
        this.saldopoczatkowe = 0.0;
        this.saldokoncowe = 0.0;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
    }

    public Dokfk(String opis, String rok) {
        this.saldopoczatkowe = 0.0;
        this.saldokoncowe = 0.0;
        this.seriadokfk = "BO";
        this.rok = rok;
        this.nrkolejnywserii = 1;
        this.datadokumentu = rok + "-01-31";
        this.dataoperacji = rok + "-01-31";
        this.datawplywu = rok + "-01-31";
        this.datawystawienia = rok + "-01-31";
        this.numerwlasnydokfk = "BO/" + rok + "/01";
        this.opisdokfk = opis;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
    }


    public Dokfk(String symbolPoprzedniegoDokumentu, Rodzajedok rodzajedok, WpisView wpisView) {
        this.saldopoczatkowe = 0.0;
        this.saldokoncowe = 0.0;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
        ustawNoweSelected(symbolPoprzedniegoDokumentu, rodzajedok, wpisView);
    }

    public Dokfk(String symbolPoprzedniegoDokumentu, Rodzajedok rodzajedok, WpisView wpisView, Klienci klienci) {
        this.saldopoczatkowe = 0.0;
        this.saldokoncowe = 0.0;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.kontr = klienci;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
        String mc = wpisView.getMiesiacWpisu().equals("CR") ? wpisView.getMiesiacWpisuArchiwum() : wpisView.getMiesiacWpisu();
        String data = Data.ostatniDzien(wpisView.getRokWpisuSt(), mc);
        this.setDatawystawienia(data);
        this.setDatawplywu(data);
        ustawNoweSelected(symbolPoprzedniegoDokumentu, rodzajedok, wpisView);
    }


    public Dokfk(int nrkolejny, String rokWpisuSt) {
        this.nrkolejnywserii = nrkolejny;
        this.rok = rokWpisuSt;
    }

    
  
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getNrdziennika() {
        return nrdziennika;
    }

    public void setNrdziennika(String nrdziennika) {
        this.nrdziennika = nrdziennika;
    }

    public String getTerminPlatnosci() {
        return terminPlatnosci;
    }

    public void setTerminPlatnosci(String terminPlatnosci) {
        this.terminPlatnosci = terminPlatnosci;
    }

    public WniosekVATZDEntity getWniosekVATZDEntity() {
        return wniosekVATZDEntity;
    }

    public void setWniosekVATZDEntity(WniosekVATZDEntity wniosekVATZDEntity) {
        this.wniosekVATZDEntity = wniosekVATZDEntity;
    }

    public Vat27 getVat27() {
        return vat27;
    }

    public void setVat27(Vat27 vat27) {
        this.vat27 = vat27;
    }

    public VatUe getVatUe() {
        return vatUe;
    }

    public void setVatUe(VatUe vatUe) {
        this.vatUe = vatUe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataksiegowania() {
        return dataksiegowania;
    }

    public Date getDataujecia() {
        return dataujecia;
    }

    public void setDataujecia(Date dataujecia) {
        this.dataujecia = dataujecia;
    }

    public void setDataksiegowania(Date dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }

    public boolean isZawierasrodkitrw() {
        return zawierasrodkitrw;
    }

    public void setZawierasrodkitrw(boolean zawierasrodkitrw) {
        this.zawierasrodkitrw = zawierasrodkitrw;
    }

    public String getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public double getSaldopoczatkowe() {
        return saldopoczatkowe;
    }

    public void setSaldopoczatkowe(double saldopoczatkowe) {
        this.saldopoczatkowe = saldopoczatkowe;
    }

    public double getSaldokoncowe() {
        return saldokoncowe;
    }

    public void setSaldokoncowe(double saldokoncowe) {
        this.saldokoncowe = saldokoncowe;
    }

    public boolean isWzorzec() {
        return wzorzec;
    }

    public void setWzorzec(boolean wzorzec) {
        this.wzorzec = wzorzec;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public boolean isImportowany() {
        return importowany;
    }

    public void setImportowany(boolean importowany) {
        this.importowany = importowany;
    }

    public List<Cechazapisu> getCechadokumentuLista() {
        return cechadokumentuLista;
    }

    public void setCechadokumentuLista(List<Cechazapisu> cechadokumentuLista) {
        this.cechadokumentuLista = cechadokumentuLista;
    }

    public String getVatM() {
        return vatM;
    }

    public void setVatM(String vatM) {
        this.vatM = vatM;
    }

    public String getVatR() {
        return vatR;
    }

    public void setVatR(String vatR) {
        this.vatR = vatR;
    }

    public List<EVatwpisFK> getEwidencjaVAT() {
        return ewidencjaVAT;
    }

    public void setEwidencjaVAT(List<EVatwpisFK> ewidencjaVAT) {
        this.ewidencjaVAT = ewidencjaVAT;
    }

    public Rodzajedok getRodzajedok() {
        return rodzajedok;
    }

    public void setRodzajedok(Rodzajedok rodzajedok) {
        this.rodzajedok = rodzajedok;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public Klienci getKontr() {
        return kontr;
    }

    public void setKontr(Klienci kontr) {
        this.kontr = kontr;
    }

    public String getDatadokumentu() {
        return datadokumentu;
    }

    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public String getDataoperacji() {
        return dataoperacji;
    }

    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }

    public String getDatawplywu() {
        return datawplywu;
    }

    public void setDatawplywu(String datawplywu) {
        this.datawplywu = datawplywu;
    }

    public boolean iswTrakcieEdycji() {
        return wTrakcieEdycji;
    }

    public void setwTrakcieEdycji(boolean wTrakcieEdycji) {
        this.wTrakcieEdycji = wTrakcieEdycji;
    }


    public String getOpisdokfk() {
        return opisdokfk;
    }

    public Waluty getWalutadokumentu() {
        return walutadokumentu;
    }

    public void setWalutadokumentu(Waluty walutadokumentu) {
        this.walutadokumentu = walutadokumentu;
    }

    public String getSeriadokfk() {
        return seriadokfk;
    }

    public void setSeriadokfk(String seriadokfk) {
        this.seriadokfk = seriadokfk;
    }

    public int getNrkolejnywserii() {
        return nrkolejnywserii;
    }

    public void setNrkolejnywserii(int nrkolejnywserii) {
        this.nrkolejnywserii = nrkolejnywserii;
    }

    public void setOpisdokfk(String opisdokfk) {
        this.opisdokfk = opisdokfk;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public String getNumerwlasnydokfk() {
        return numerwlasnydokfk;
    }

    public void setNumerwlasnydokfk(String numerwlasnydokfk) {
        this.numerwlasnydokfk = numerwlasnydokfk;
    }

    public String getRok() {
        return rok;
    }
    
    public int getRokInt() {
        return Integer.parseInt(rok);
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isZablokujzmianewaluty() {
        return zablokujzmianewaluty;
    }

    public void setZablokujzmianewaluty(boolean zablokujzmianewaluty) {
        this.zablokujzmianewaluty = zablokujzmianewaluty;
    }

    public int getLiczbarozliczonych() {
        return liczbarozliczonych;
    }

    public void setLiczbarozliczonych(int liczbarozliczonych) {
        this.liczbarozliczonych = liczbarozliczonych;
    }

    public double getWartoscdokumentu() {
        return wartoscdokumentu;
    }

    public void setWartoscdokumentu(double wartoscdokumentu) {
        this.wartoscdokumentu = wartoscdokumentu;
    }

    @XmlTransient
    public List<Wiersz> getListawierszy() {
        return listawierszy;
    }

    public void setListawierszy(List<Wiersz> listawierszy) {
        this.listawierszy = listawierszy;
    }

    public boolean isZawierarmk() {
        return zawierarmk;
    }

    public void setZawierarmk(boolean zawierarmk) {
        this.zawierarmk = zawierarmk;
    }

    public boolean isZawierarozkurs() {
        return zawierarozkurs;
    }

    public void setZawierarozkurs(boolean zawierarozkurs) {
        this.zawierarozkurs = zawierarozkurs;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }
    
    @Override
        public String getTypdokumentu() {
        return rodzajedok.getSkrot();
    }


//    @XmlTransient
//    public List<Kontozapisy> getZapisynakoncie() {
//        return zapisynakoncie;
//    }
//
//    public void setZapisynakoncie(List<Kontozapisy> zapisynakoncie) {
//        this.zapisynakoncie = zapisynakoncie;
//    }
//
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.seriadokfk);
        hash = 97 * hash + this.nrkolejnywserii;
        hash = 97 * hash + Objects.hashCode(this.rok);
        hash = 97 * hash + Objects.hashCode(this.podatnikObj);
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
        final Dokfk other = (Dokfk) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.nrkolejnywserii != other.nrkolejnywserii) {
            return false;
        }
        if (!Objects.equals(this.seriadokfk, other.seriadokfk)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.podatnikObj, other.podatnikObj)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dokfk{" + "seriadokfk=" + seriadokfk + ", nrkolejnywserii=" + nrkolejnywserii + ", rok=" + rok + ", podatnikObj=" + podatnikObj + ", datawystawienia=" + datawystawienia + '}';
    }
    

    

    public String getDokfkLP() {
        StringBuilder s = new StringBuilder();
        s.append(this.getSeriadokfk());
        s.append("/");
        s.append(this.lp);
        s.append("/");
        s.append(this.getRok());
        return s.toString();
    }

    public String getDokfkSN() {
        StringBuilder s = new StringBuilder();
        s.append(this.getSeriadokfk());
        s.append("/");
        s.append(this.getNrkolejnywserii());
        s.append("/");
        s.append(this.getRok());
        return s.toString();
    }

    public EVatwpisFK getVATRK(Wiersz w) {
        EVatwpisFK ew = null;
        for (EVatwpisFK p : this.getEwidencjaVAT()) {
            if (p.getWiersz() == w) {
                ew = p;
                break;
            }
        }
        return ew;
    }
    
    public double getNiezaplacone() {
        double suma = 0.0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            if (p.isNowatransakcja()) {
                suma = Z.z(p.getPozostalo());
            }
        }
        return suma;
    }
    
    public String getOpisDokfkUsun() {
        return this.toString2()+" "+this.numerwlasnydokfk+" "+this.kontr.getNpelna();
    }
    
    public String toString3() {
        return seriadokfk + "/" + nrkolejnywserii + "/" + rok + ", firma: " + podatnikObj.getNazwapelna();
    }

    public String toString2() {
        return seriadokfk + "/" + nrkolejnywserii + "/" + rok;
    }
    
    public String stringdlavatzd() {
        return seriadokfk + "/" + nrkolejnywserii + "/" + rok+" "+numerwlasnydokfk+" "+kontr.getNpelna();
    }

    public String getMcRok() {
        return this.miesiac + "/" + this.getRok();
    }
    
   

    public void dodajKwotyWierszaDoSumyDokumentu(Wiersz biezacywiersz) {
        try {//robimy to bo sa nowy wiersz jest tez podsumowywany, ale moze byc przeciez pusty wiec wyrzuca blad
            int typwiersza = biezacywiersz.getTypWiersza();
            double wn = biezacywiersz.getStronaWn() != null ? biezacywiersz.getStronaWn().getKwota() : 0.0;
            double ma = biezacywiersz.getStronaMa() != null ? biezacywiersz.getStronaMa().getKwota() : 0.0;
            double suma = 0.0;
            if (typwiersza == 1) {
                suma += wn;
            } else if (typwiersza == 2) {
                suma += ma;
            } else {
                double kwotaWn = wn;
                double kwotaMa = ma;
                if (kwotaMa > kwotaWn) {
                    suma += wn;
                } else {
                    suma += ma;
                }
            }
            this.wartoscdokumentu += suma;
        } catch (Exception e) {

        }
    }

    public void przeliczKwotyWierszaDoSumyDokumentu() {
        this.wartoscdokumentu = 0.0;
        for (Wiersz p : this.listawierszy) {
            dodajKwotyWierszaDoSumyDokumentu(p);
        }
        PrimeFaces.current().ajax().update("formwpisdokument:panelzkwotamidok");
    }

//    public void uzupelnijwierszeodane() {
//        //ladnie uzupelnia informacje o wierszu pk
//        List<Wiersz> wierszewdokumencie = this.listawierszy;
//        try {
//            for (Wiersz p : wierszewdokumencie) {
//                String opis = p.getOpisWiersza();
//                if (opis.contains("kontown")) {
//                    p.setDataksiegowania(this.datawystawienia);
//                    p.setDokfk(this);
//                } else if (opis.contains("kontoma")) {
//                    p.setDataksiegowania(this.datawystawienia);
//                    p.setDokfk(this);
//                } else {
//                    p.setDataksiegowania(this.datawystawienia);
//                    p.setDokfk(this);
//                }
//            }
//        } catch (Exception e) {
//        }
//    }
    public void ustawNoweSelected(String symbolPoprzedniegoDokumentu, Rodzajedok rodzajedok, WpisView wpisView) {
        //chodzi o FVS, FVZ a nie o numerwlasnydokfk :)
        this.setPodatnikObj(wpisView.getPodatnikObiekt());
        if (symbolPoprzedniegoDokumentu != null) {
            this.setSeriadokfk(symbolPoprzedniegoDokumentu);
            this.setRodzajedok(rodzajedok);
        } else {
            this.setSeriadokfk("ZZ");
        }
        this.setRok(wpisView.getRokWpisuSt());
        String mc = wpisView.getMiesiacWpisu().equals("CR") ? wpisView.getMiesiacWpisuArchiwum() : wpisView.getMiesiacWpisu();
        this.setMiesiac(mc);
        this.setVatR(wpisView.getRokWpisuSt());
        this.setVatM(mc);
        this.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(this));
        this.setZablokujzmianewaluty(false);
    }

    public Wiersz poprzedniWiersz(Wiersz wiersz) {
        int index = this.listawierszy.indexOf(wiersz);
        try {
            return this.listawierszy.get(index - 1);
        } catch (Exception e) {

        }
        return null;
    }

    public Wiersz nastepnyWiersz(Wiersz wiersz) {
        Wiersz zwrot = null;
        int index = this.listawierszy.indexOf(wiersz);
        try {
            zwrot = this.listawierszy.get(index + 1);
        } catch (Exception e) {
        }
        if (wiersz==zwrot) {
            zwrot = null;
        }
        return zwrot;
    }

    public void usunpuste() {
        try {
            List<Wiersz> wierszedokumentu = this.getListawierszy();
            if (wierszedokumentu.size() > 1) {
                for (Iterator<Wiersz> it = wierszedokumentu.iterator(); it.hasNext();) {
                    Wiersz p = it.next();
                    if (p.getOpisWiersza() == null || p.getOpisWiersza().equals("")) {
                        it.remove();
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public String pobierzSymbolPoprzedniegoDokfk() {
        String symbolPoprzedniegoDokumentu = "";
        try {
            symbolPoprzedniegoDokumentu = new String(this.getSeriadokfk());
        } catch (Exception e) {
        }
        return symbolPoprzedniegoDokumentu;
    }

    public List<StronaWiersza> getStronyWierszy() {
        this.getListawierszy().size();
        List<StronaWiersza> lista = new ArrayList<>();
        for (Wiersz p : this.listawierszy) {
            if (p.getStronaWn() != null) {
                lista.add(p.getStronaWn());
            }
            if (p.getStronaMa() != null) {
                lista.add(p.getStronaMa());
            }
        }
        return lista;
    }

    public int sprawdzczynaniesionorozrachunki() {
        int brakrozrachunkow = 0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            boolean jestrozrachunkowe = p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            boolean jestnowatransakcja = p.isNowatransakcja();
            boolean saplatnosci = p.getRozliczono() > 0;
            if (jestrozrachunkowe == true) {
                if (jestnowatransakcja == false && saplatnosci == false) {
                    brakrozrachunkow = 1;
                }
            }
        }
        return brakrozrachunkow;
    }

    public void oznaczVATdokument(String sprawdzjakiokresvat) {
        if (!sprawdzjakiokresvat.equals("blad") && this.ewidencjaVAT !=null && !this.ewidencjaVAT.isEmpty()) {
            for (EVatwpisFK p : this.ewidencjaVAT) {
                if (!p.isNieduplikuj()) {
                    if (p.getNetto() != 0.0 || p.getVat() != 0.0) {
                        if (p.getInnyokres() == 0) {
                            p.setMcEw(this.getMiesiac());
                            p.setRokEw(this.getRok());
                            this.setVatR(this.getRok());
                            this.setVatM(this.getMiesiac());
                        } else if (sprawdzjakiokresvat.equals("kwartalne")) {
                            String[] nowyokres = Kwartaly.zwiekszkwartal(this.getRok(), this.getMiesiac(), p.getInnyokres());
                            p.setRokEw(nowyokres[0]);
                            p.setMcEw(nowyokres[1]);
                            this.setVatR(nowyokres[0]);
                            this.setVatM(nowyokres[1]);
                        } else {
                            String[] nowyokres = Mce.zwiekszmiesiac(this.getRok(), this.getMiesiac(), p.getInnyokres());
                            p.setRokEw(nowyokres[0]);
                            p.setMcEw(nowyokres[1]);
                            this.setVatR(nowyokres[0]);
                            this.setVatM(nowyokres[1]);
                        }
                    }
                }
            }
        }
    }

    public void dodajTabeleWalut(Tabelanbp tabelanbp) {
        this.setTabelanbp(tabelanbp);
        List<Wiersz> wiersze = this.getListawierszy();
        for (Wiersz p : wiersze) {
            p.setTabelanbp(tabelanbp);
        }
    }

    public void przepiszWierszeBO() {
        List<Wiersz> wiersze = this.getListawierszy();
        for (Wiersz p : wiersze) {
            if (p.getTypWiersza() == 0) {
                if (!p.getOpisWiersza().contains("zapis BO:")) {
                    String nowyopis = "zapis BO: " + p.getOpisWiersza();
                    p.setOpisWiersza(nowyopis);
                }
                if (p.getStronaWn().getKonto() != null) {
                    p.setTypWiersza(1);
                    p.setStronaMa(null);
                } else {
                    p.setTypWiersza(2);
                    p.setStronaWn(null);
                }
            }
        }
    }
    
    public void przenumeruj() {
        List<Wiersz> wiersze = this.getListawierszy();
        int lp = 1;
        for (Wiersz p : wiersze) {
            p.setIdporzadkowy(lp++);
        }
    }

    public boolean czyCecha() {
        if (this.cechadokumentuLista != null && this.cechadokumentuLista.size() > 0) {
            return true;
        } else {
            for (StronaWiersza p : this.getStronyWierszy()) {
                if (p.getCechazapisuLista().size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public double getWartoscdokumentuPLN() {
        return Z.z(this.wartoscdokumentu*this.getTabelanbp().getKurssredniPrzelicznik());
    }
    
    public String getWartoswPLN() {
        double w = 0.0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            w += p.getKwotaPLN();
        }
        w = Z.z(w/2);
        return beansPdf.PdfFont.formatujLiczba(w);
    }
    
    public String getNettoPLNWNT() {
        this.getStronyWierszy().size();
        double w = 0.0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            if (p.getKonto().getBilansowewynikowe().equals("wynikowe") || p.getKonto().getPelnynumer().startsWith("3")) {
                w += p.getKwotaPLN();
            }
        }
        return beansPdf.PdfFont.formatujLiczba(w);
    }
    
     public String getVATPLNWNT() {
        this.getStronyWierszy().size();
        double w = 0.0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            if (p.getKonto().getPelnynumer().startsWith("221")) {
                w += p.getKwotaPLN();
            }
        }
        return beansPdf.PdfFont.formatujLiczba(w);
    }
    
     public String getNettoWALWNT() {
        this.getStronyWierszy().size();
        double w = 0.0;
        for (StronaWiersza p : this.getStronyWierszy()) {
            if (p.getKonto().getBilansowewynikowe().equals("wynikowe") || p.getKonto().getPelnynumer().startsWith("3")) {
                w += p.getKwota();
            }
        }
        return beansPdf.PdfFont.formatujLiczba(w);
    }
     
     public String getKontoRozrachunkowe() {
         String zwrot = "b/k";
         if (this.getRodzajedok()!=null && this.getRodzajedok().getKontorozrachunkowe() != null) {
             zwrot = this.getRodzajedok().getKontorozrachunkowe().getPelnynumer();
         }
         return zwrot;
     }
     
    public double[] pobierzwartosci() {
        double netto = 0.0;
        double nettowaluta = 0.0;
        for (EVatwpisFK p : ewidencjaVAT) {
            netto += p.getNetto();
            nettowaluta += p.getNettowwalucie();
        }
        return new double[]{Z.z(netto),Z.z(nettowaluta)};
    }
    
    public boolean isDwarejestry() {
        boolean zwrot = false;
        if (!this.getRodzajedok().isTylkovatnalezny() && this.getRodzajedok().getSkrot().equals("WNT") || this.getRodzajedok().getSkrot().equals("IU") || this.getRodzajedok().getSkrot().equals("RVC")) {
            zwrot = true;
        }
        return zwrot;
    }

     public void sortujwierszeData() {
        Object[] wiersze =  this.getListawierszy().toArray();
        int n = wiersze.length - 1;
        while (n > 0) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                Wiersz o1 = (Wiersz) wiersze[j];
                Wiersz o2 = (Wiersz) wiersze[j+1];
                int wieksza = Integer.parseInt(o1.getDataWalutyWiersza());
                int mniejsza = Integer.parseInt(o2.getDataWalutyWiersza());
                if (wieksza > mniejsza) {
                    swap(wiersze, j, j + 1);
                    last = j;
                }
            }
            n = last;
        }
        List<Wiersz> nowe = new ArrayList<>();
        int licz = 1;
        int wiersztyp0 = 0;
        for (Object wiersze1 : wiersze) {
            Wiersz w = (Wiersz) wiersze1;
            w.setIdporzadkowy(licz++);
            if (w.getTypWiersza()==0) {
                wiersztyp0 = w.getIdporzadkowy();
                w.setLpmacierzystego(w.getIdporzadkowy());
            } else {
                w.setLpmacierzystego(wiersztyp0);
            }
            nowe.add(w);
        }
        this.setListawierszy(nowe);
        
    }
    
    public static void sortujwierszeData(Popo popo) {
        Object[] wiersze =  popo.lista.toArray();
        int n = wiersze.length - 1;
        while (n > 0) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                Popo o1 = (Popo) wiersze[j];
                Popo o2 = (Popo) wiersze[j+1];
                int wieksza = o1.data;
                int mniejsza = o2.data;
                if (wieksza > mniejsza) {
                    swap(wiersze, j, j + 1);
                    last = j;
                }
            }
            n = last;
        }
        List<Popo> nowe = new ArrayList<>();
        for (Object wiersze1 : wiersze) {
            nowe.add((Popo) wiersze1);
        }
        popo.setLista(nowe);
        
    }

    public double getNettoVAT() {
        double zwrot = 0.0;
        if (this.ewidencjaVAT!=null) {
            for (EVatwpisFK p : this.ewidencjaVAT) {
                zwrot += p.getNetto();
            }
        }
        return zwrot;
    }

    public double getVATVAT() {
        double zwrot = 0.0;
        if (this.ewidencjaVAT!=null) {
            for (EVatwpisFK p : this.ewidencjaVAT) {
                zwrot += p.getVat();
            }
        }
        return zwrot;
    }
    
    private static class Popo {
        int lp;
        int id; 
        int data;
        int rwiersza;
        
        public List<Popo> lista;

        public Popo() {
            lista = new ArrayList<>();
            lista.add(new Popo(1,1,3,0));
            lista.add(new Popo(2,2,8,0));
            lista.add(new Popo(3,3,15,0));
            lista.add(new Popo(4,4,1,0));
            lista.add(new Popo(5,5,8,0));
            lista.add(new Popo(6,6,8,1));
        }

        private Popo(int i, int i0, int i1, int i2) {
            this.lp = i;
            this.id = i0;
            this.data = i1;
            this.rwiersza = i2;
        }

        @Override
        public String toString() {
            return "Popo{" + "lp=" + lp + ", id=" + id + ", data=" + data + ", rwiersza=" + rwiersza + '}';
        }

        

        public void setLista(List<Popo> lista) {
            this.lista = lista;
        }
        
        
        
    }
    
    public static void sortujwierszeID() {
        Popo popo = new Popo();
        Object[] wiersze =  popo.lista.toArray();
        int n = wiersze.length - 1;
        while (n > 0) {
            int last = 0;
            for (int j = 0; j < n; j++) {
                Popo o1 = (Popo) wiersze[j];
                Popo o2 = (Popo) wiersze[j+1];
                int wieksza = o1.data;
                int mniejsza = o2.data;
                int wiekszeid = o1.id;
                int mniejszeid = o2.id;
                if (wieksza == mniejsza && wiekszeid < mniejszeid) {
                    swap(wiersze, j, j + 1);
                    last = j;
                }
            }
            n = last;
        }
        List<Popo> nowe = new ArrayList<>();
        for (Object wiersze1 : wiersze) {
            nowe.add((Popo) wiersze1);
        }
        popo.setLista(nowe);
        //this.setListawierszy(nowe);
    }
    
    private static void swap(Object[] tablica, int i, int j) {
        Object temp = tablica[i];
        tablica[i] = tablica[j];
        tablica[j] = temp;
    }
    
    public static void main(String[] args) {
        Popo popo = new Popo();
        sortujwierszeData(popo);
    }
}
