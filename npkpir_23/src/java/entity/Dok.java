/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.AmazonCSV;
import embeddable.FakturaCis;
import embeddable.FakturaEbay;
import embeddable.Stornodoch;
import entityfk.Cechazapisu;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.StringUtils;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dok", uniqueConstraints = {
    @UniqueConstraint(columnNames={"nr_wl_dk,podid,kontr1,pkpir_r"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dok.findByIdDok", query = "SELECT d FROM Dok d WHERE d.idDok = :idDok"),
    @NamedQuery(name = "Dok.findByIdDokOdDo", query = "SELECT d FROM Dok d WHERE d.idDok > :odd AND D.idDok < :dod"),
    @NamedQuery(name = "Dok.findByTypDokumentu", query = "SELECT d FROM Dok d WHERE d.rodzajedok.skrot = :typdokumentu"),
    @NamedQuery(name = "Dok.findByKontr", query = "SELECT d FROM Dok d WHERE d.kontr1.id = :kontr"),
    @NamedQuery(name = "Dok.findByKontr1Null", query = "SELECT d FROM Dok d WHERE d.kontr1 IS NULL"),
    @NamedQuery(name = "Dok.findByDataWyst", query = "SELECT d FROM Dok d WHERE d.dataWyst = :dataWyst"),
    @NamedQuery(name = "Dok.findByTermin30", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin30"),
    @NamedQuery(name = "Dok.findByTermin90", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin90"),
    @NamedQuery(name = "Dok.findByTermin150", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin150"),
    @NamedQuery(name = "Dok.findByTerminPlatnosci", query = "SELECT d FROM Dok d WHERE d.terminPlatnosci = :terminPlatnosci"),
    @NamedQuery(name = "Dok.findByNrWlDk", query = "SELECT d FROM Dok d WHERE d.nrWlDk = :nrWlDk"),
    @NamedQuery(name = "Dok.findByRodzTrans", query = "SELECT d FROM Dok d WHERE d.rodzajedok.rodzajtransakcji = :rodzTrans"),
    @NamedQuery(name = "Dok.findByOpis", query = "SELECT d FROM Dok d WHERE d.opis = :opis"),
    @NamedQuery(name = "Dok.findByUwagi", query = "SELECT d FROM Dok d WHERE d.uwagi = :uwagi"),
    @NamedQuery(name = "Dok.findByPkpirM", query = "SELECT d FROM Dok d WHERE d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByPkpirRMCount", query = "SELECT COUNT(d) FROM Dok d WHERE d.podatnik = :podatnik AND d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByPkpirR", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR"),
    @NamedQuery(name = "Dok.findByVatM", query = "SELECT d FROM Dok d WHERE d.vatM = :vatM"),
    @NamedQuery(name = "Dok.findByVatR", query = "SELECT d FROM Dok d WHERE d.vatR = :vatR"),
    @NamedQuery(name = "Dok.znajdzInwestycja", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.dataWyst = :data AND d.netto = :netto AND d.nrWlDk = :numer"),
    @NamedQuery(name = "Dok.findDuplicate", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.kontr1.nip = :nip AND d.nrWlDk = :nrWlDk AND d.netto = :netto AND d.pkpirR = :pkpirR"),
    @NamedQuery(name = "Dok.findDuplicateAMO", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.nrWlDk = :nrWlDk AND d.pkpirR = :pkpirR AND d.rodzajedok.skrot = 'AMO'"),
    @NamedQuery(name = "Dok.findDuplicatewTrakcie", query = "SELECT d FROM Dok d WHERE d.kontr1.nip = :nip AND d.nrWlDk = :nrWlDk AND d.podatnik = :podatnik AND d.rodzajedok.skrot = :typdokumentu AND d.pkpirR = :rok"),
    @NamedQuery(name = "Dok.findStornoDok", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.opis = :opis"),
    @NamedQuery(name = "Dok.findPoprzednik", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.opis = :opis"),
    @NamedQuery(name = "Dok.findByRozliczony", query = "SELECT d FROM Dok d WHERE d.rozliczony = :rozliczony"),
    @NamedQuery(name = "Dok.findByPodatnik", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByfindByLastofaTypeKontrahent", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.kontr1 = :kontr AND d.pkpirR = :pkpirR  ORDER BY d.idDok DESC"),
    @NamedQuery(name = "Dok.findByFakturaWystawiona", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.kontr1 = :kontr AND d.nrWlDk = :nrWlDk AND d.brutto = :brutto"),
    @NamedQuery(name = "Dok.findByBK", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByBKodMca", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM>=:mc AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByBKodMcadoMca", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM>=:mcod AND d.pkpirM<=:mcdo AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByInwestycje", query = "SELECT d FROM Dok d WHERE d.symbolinwestycji IS NOT NULL AND d.symbolinwestycji != '' AND d.symbolinwestycji != 'wybierz'"),
    @NamedQuery(name = "Dok.findByBKPrzychody", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik AND (d.rodzajedok.skrot = 'SPRY' OR d.rodzajedok.skrot = 'UPTK' OR d.rodzajedok.skrot = 'UPTK100' OR d.rodzajedok.skrot = 'RVCS')"),
    @NamedQuery(name = "Dok.findByBKMCPrzychody", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND (d.rodzajedok.skrot = 'SPRY' OR d.rodzajedok.skrot = 'UPTK' OR d.rodzajedok.skrot = 'UPTK100' OR d.rodzajedok.skrot = 'RVCS')"),
    @NamedQuery(name = "Dok.findByBKVAT", query = "SELECT d FROM Dok d WHERE d.vatR = :vatR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByTPR", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik AND d.rodzajedok.skrot = :typdokumentu ORDER BY d.idDok DESC"),
    @NamedQuery(name = "Dok.findByBKM", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByBKMVAT", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.vatM = :vatM AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByBKMWaluta", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.tabelanbp.waluta.symbolwaluty != 'PLN' "),
    @NamedQuery(name = "Dok.findByRokKW", query = "SELECT d FROM Dok d WHERE d.vatR = :pkpirR AND d.podatnik = :podatnik AND (d.vatM = :mc1 OR d.vatM = :mc2 OR d.vatM = :mc3)"),
    @NamedQuery(name = "Dok.findByDuplikat", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByRMPT", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.rodzajedok.skrot = :typdokumentu"),
    @NamedQuery(name = "Dok.findByRokMc", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByRokMcDataKsiegowania", query = "SELECT d FROM Dok d WHERE d.dataK >= :startDate AND d.dataK < :endDate"),
    @NamedQuery(name = "Dok.znajdzDokumentPodatnikWpr", query = "SELECT DISTINCT d.podatnik FROM Dok d WHERE d.wprowadzil = :wprowadzil")
})
@Cacheable 
public class Dok extends DokSuper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dok")
    private Long idDok;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 255)
//    @Column(name = "typdokumentu")
//    private String typdokumentu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rodzajedok", referencedColumnName = "id")
    private Rodzajedok rodzajedok;
    @Size(max = 10)
    @Column(name = "nrWpkpir")
    private int nrWpkpir;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nr_wl_dk")
    private String nrWlDk;
//    @Lob
//    @Column(name = "kontr")
//    private Klienci kontr;
    @JoinColumn(name = "kontr1", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontr1;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 50)
//    @Column(name = "podatnik")
//    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpr")
    private String wprowadzil;
    @Column(name = "data_k", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataK;
    @Column(name = "dataedycji")
    private Date dataedycji;
    @Column(name = "edytowal")
    private String edytowal;
    @Size(max = 10)
    @Column(name = "data_wyst")
//    @Temporal(TemporalType.DATE)
    private String dataWyst;
    @Size(max = 10)
    @Column(name = "data_sprzedazy")
//    @Temporal(TemporalType.DATE)
    private String dataSprz;
//    @Size(max = 45)
//    @Column(name = "rodz_trans")
//    private String rodzTrans;
    @Size(max = 145)
    @Column(name = "opis")
    private String opis;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dok", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<KwotaKolumna1> listakwot1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "netto")
    private Double netto;
    @Column(name = "brutto")
    private Double brutto;
    @Size(max = 45)
    @Column(name = "uwagi")
    private String uwagi;
    @Size(max = 45)
    @Column(name = "pkpir_m")
    private String pkpirM;
    @Size(max = 45)
    @Column(name = "pkpir_r")
    private String pkpirR;
    @Size(max = 2)
    @Column(name = "vat_m")
    private String vatM;
    @Size(max = 4)
    @Column(name = "vat_r")
    private String vatR;
    @Size(max = 65)
    @Column(name = "status")
    private String status;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "evat")
//    private List<EVatwpis> ewidencjaVAT;
//    @JoinColumn(name = "ewidencjaVAT1")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dok", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<EVatwpis1> ewidencjaVAT1;
    @Column(name = "dokprosty")
    boolean dokumentProsty;
    @Column(name = "dodkolumna")
    boolean dodatkowaKolumna;
    @Column(name = "rozliczony")
    private Boolean rozliczony;
    @Size(max = 10)
    @Column(name = "termin_platnosci")
//    @Temporal(TemporalType.DATE)
    private String terminPlatnosci;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dok", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<Rozrachunek1> rozrachunki1;
    @Size(max = 10)
    @Column(name = "termin_30")
    private String termin30;
    @Size(max = 10)
    @Column(name = "termin_90")
    private String termin90;
    @Size(max = 10)
    @Column(name = "termin_150")
    private String termin150;
    @Lob
    @Column(name = "storno")
    private List<Stornodoch> storno;
    @Column(name = "usunpozornie")
    private Boolean usunpozornie;
    @Size(max = 50)
    @Column(name = "symbolinwestycji", nullable = true)
    private String symbolinwestycji;
    @JoinColumn(name = "walutadokumentu", referencedColumnName = "idwaluty")
    @ManyToOne
    private Waluty walutadokumentu;
    @JoinColumn(name = "tabelanbp", referencedColumnName = "idtabelanbp")
    @ManyToOne
    private Tabelanbp tabelanbp;
    @Column(name = "sprawdzony")
    private int sprawdzony;
    @JoinColumn(name = "inwestycja", referencedColumnName = "id")
    @ManyToOne
    private Inwestycje inwestycja;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dokument", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<PlatnoscWaluta> platnosciwaluta;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Dok_Cechazapisu",
            joinColumns = {
                @JoinColumn(name = "id_dok", referencedColumnName = "id_dok"),
            },
            inverseJoinColumns = {
                 @JoinColumn(name = "idcecha", referencedColumnName = "id"),
            })
    private List<Cechazapisu> cechadokumentuLista;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @JoinColumn(name = "vatue", referencedColumnName = "id")
    @OneToOne
    private VatUe vatUe;
//    @JoinColumn(name = "vat27", referencedColumnName = "id")
//    @OneToOne
//    private Vat27 vat27;
    @JoinColumn(name = "wniosekVATZDEntity", referencedColumnName = "id")
    @OneToOne
    private WniosekVATZDEntity wniosekVATZDEntity;
    @Transient
    private AmazonCSV amazonCSV;
    @Transient
    private FakturaCis fakturaCis;
    @Transient
    private FakturaEbay fakturaEbay;
    @JoinColumn(name = "faktura", referencedColumnName = "id")
    @OneToOne
    private Faktura faktura;
    @JoinColumn(name = "fakturakontrahent", referencedColumnName = "id")
    @OneToOne
    private Faktura fakturakontrahent;
     @Column(name = "ulganazledlugidatapierwsza")
    private String ulganazledlugidatapierwsza;
    @Column(name = "ulganazledlugidatapierwszaplus90")
    private String ulganazledlugidatapierwszaplus90;
    @Column(name = "ulganazledlugidatadruga")
    private String ulganazledlugidatadruga;
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dok", cascade = CascadeType.ALL,  orphanRemoval=true)
//    private List<UmorzenieN> listaumorzen;
    
    
    public Dok() {
        this.listakwot1 = Collections.synchronizedList(new ArrayList<>());
        this.cechadokumentuLista = Collections.synchronizedList(new ArrayList<>());
        this.getListakwot1().add(new KwotaKolumna1());
    }

    public Dok(Long idDok) {
        this.listakwot1 = Collections.synchronizedList(new ArrayList<>());
        this.cechadokumentuLista = Collections.synchronizedList(new ArrayList<>());
        this.getListakwot1().add(new KwotaKolumna1());
        this.idDok = idDok;
    }

     public double[] pobierzwartosci() {
        double netto = 0.0;
        double nettowaluta = 0.0;
        for (EVatwpis1 p : this.ewidencjaVAT1) {
            netto += p.getNetto();
            nettowaluta += p.getNetto();
        }
        return new double[]{Z.z(netto),Z.z(nettowaluta)};
    }
    public String getDokSN() {
        StringBuilder s = new StringBuilder();
        s.append(this.getRodzajedok().getSkrot());
        s.append("/");
        s.append(this.getNrWlDk());
        s.append("/");
        s.append(this.getPkpirR());
        return s.toString();
    }
    public Long getIdDok() {
        return idDok;
    }

    public void setIdDok(Long idDok) {
        this.idDok = idDok;
    }

    public int getNrWpkpir() {
        return nrWpkpir;
    }

    public void setNrWpkpir(int nrWpkpir) {
        this.nrWpkpir = nrWpkpir;
    }

    public List<Cechazapisu> getCechadokumentuLista() {
        return cechadokumentuLista;
    }

    public void setCechadokumentuLista(List<Cechazapisu> cechadokumentuLista) {
        this.cechadokumentuLista = cechadokumentuLista;
    }

    public Klienci getKontr() {
        return kontr1;
    }

    public void setKontr(Klienci kontr) {
        this.kontr1 = kontr;
    }

    public Klienci getKontr1() {
        return kontr1;
    }

    public void setKontr1(Klienci kontr1) {
        this.kontr1 = kontr1;
    }

    public List<KwotaKolumna1> getListakwot1() {
        return listakwot1;
    }

    public void setListakwot1(List<KwotaKolumna1> listakwot1) {
        this.listakwot1 = listakwot1;
    }
    
    public String getDataWyst() {
        return dataWyst;
    }

    public void setDataWyst(String dataWyst) {
            this.dataWyst = dataWyst;
    }

    public String getNrWlDk() {
        return nrWlDk;
    }

    public void setNrWlDk(String nrWlDk) {
        this.nrWlDk = nrWlDk;
    }

    
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

      
    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public String getPkpirM() {
        return pkpirM;
    }

    public void setPkpirM(String pkpirM) {
        this.pkpirM = pkpirM;
    }

    public String getPkpirR() {
        return pkpirR;
    }

    public void setPkpirR(String pkpirR) {
        this.pkpirR = pkpirR;
    }

    public String getVatM() {
        return vatM;
    }

    public AmazonCSV getAmazonCSV() {
        return amazonCSV;
    }

    public void setAmazonCSV(AmazonCSV amazonCSV) {
        this.amazonCSV = amazonCSV;
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

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

   
   

    public String getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(String wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataK() {
        return dataK;
    }

    public void setDataK(Date dataK) {
        this.dataK = dataK;
    }

    public List<PlatnoscWaluta> getPlatnosciwaluta() {
        return platnosciwaluta;
    }

//    public List<EVatwpis> getEwidencjaVAT() {
//        return ewidencjaVAT;
//    }
//
//    public void setEwidencjaVAT(List<EVatwpis> ewidencjaVAT) {
//        this.ewidencjaVAT = ewidencjaVAT;
//    }
    public void setPlatnosciwaluta(List<PlatnoscWaluta> platnosciwaluta) {
        this.platnosciwaluta = platnosciwaluta;
    }

    public boolean isDokumentProsty() {
        return dokumentProsty;
    }

    public void setDokumentProsty(boolean dokumentProsty) {
        this.dokumentProsty = dokumentProsty;
    }

  
    public boolean isDodatkowaKolumna() {
        return dodatkowaKolumna;
    }

    public void setDodatkowaKolumna(boolean dodatkowaKolumna) {
        this.dodatkowaKolumna = dodatkowaKolumna;
    }

    public String getDataSprz() {
        return dataSprz;
    }

    public void setDataSprz(String dataSprz) {
        this.dataSprz = dataSprz;
    }

    public Boolean getRozliczony() {
        return rozliczony;
    }

    public void setRozliczony(Boolean rozliczony) {
        this.rozliczony = rozliczony;
    }

   
    public String getTerminPlatnosci() {
        return terminPlatnosci;
    }

    public void setTerminPlatnosci(String terminPlatnosci) {
        this.terminPlatnosci = terminPlatnosci;
    }

    public String getTermin30() {
        return termin30;
    }

    public void setTermin30(String termin30) {
        this.termin30 = termin30;
    }

    public String getTermin90() {
        return termin90;
    }

    public void setTermin90(String termin90) {
        this.termin90 = termin90;
    }

    public String getTermin150() {
        return termin150;
    }

    public void setTermin150(String termin150) {
        this.termin150 = termin150;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public List<Stornodoch> getStorno() {
        return storno;
    }

    public void setStorno(List<Stornodoch> storno) {
        this.storno = storno;
    }

    public Double getBrutto() {
        return this.brutto != null ? this.brutto : 0.0;
    }
    
      public double getBruttoDouble() {
        return this.brutto != null ? this.brutto : 0.0;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }

    public Boolean getUsunpozornie() {
        return usunpozornie;
    }

    public void setUsunpozornie(Boolean usunpozornie) {
        this.usunpozornie = usunpozornie;
    }

    public String getSymbolinwestycji() {
        return symbolinwestycji;
    }

    public void setSymbolinwestycji(String symbolinwestycji) {
        this.symbolinwestycji = symbolinwestycji;
    }

    public List<EVatwpis1> getEwidencjaVAT1() {
        return ewidencjaVAT1;
    }

    public void setEwidencjaVAT1(List<EVatwpis1> ewidencjaVAT1) {
        this.ewidencjaVAT1 = ewidencjaVAT1;
    }

    public Inwestycje getInwestycja() {
        return inwestycja;
    }

    public void setInwestycja(Inwestycje inwestycja) {
        this.inwestycja = inwestycja;
    }

    public List<Rozrachunek1> getRozrachunki1() {
        return rozrachunki1;
    }

    public void setRozrachunki1(List<Rozrachunek1> rozrachunki1) {
        this.rozrachunki1 = rozrachunki1;
    }

    public Waluty getWalutadokumentu() {
        return walutadokumentu;
    }

    public Rodzajedok getRodzajedok() {
        return rodzajedok;
    }

    public void setRodzajedok(Rodzajedok rodzajedok) {
        this.rodzajedok = rodzajedok;
    }
    
    public VatUe getVatUe() {
        return vatUe;
    }
    
    public void setVatUe(VatUe vatUe) {
        this.vatUe = vatUe;
    }
    
    public String getSymbolWaluty() {
        String zwrot = "PLN";
        if (this.walutadokumentu != null) {
            zwrot = this.walutadokumentu.getSymbolwaluty();
        }
        return zwrot;
    }

    public void setWalutadokumentu(Waluty walutadokumentu) {
        this.walutadokumentu = walutadokumentu;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    public Vat27 getVat27() {
        return null;
    }

    public void setVat27(Vat27 vat27) {
        //this.vat27 = vat27;
    }

    public Faktura getFaktura() {
        return faktura;
    }

    public void setFaktura(Faktura faktura) {
        this.faktura = faktura;
    }

    public WniosekVATZDEntity getWniosekVATZDEntity() {
        return wniosekVATZDEntity;
    }

    public void setWniosekVATZDEntity(WniosekVATZDEntity wniosekVATZDEntity) {
        this.wniosekVATZDEntity = wniosekVATZDEntity;
    }

    public FakturaEbay getFakturaEbay() {
        return fakturaEbay;
    }

    public void setFakturaEbay(FakturaEbay fakturaEbay) {
        this.fakturaEbay = fakturaEbay;
    }

    public Faktura getFakturakontrahent() {
        return fakturakontrahent;
    }

    public void setFakturakontrahent(Faktura fakturakontrahent) {
        this.fakturakontrahent = fakturakontrahent;
    }

    public boolean jestniemcy() {
        boolean zwrot = false;
        if (this.ewidencjaVAT1!=null) {
            for (EVatwpis1 p : this.ewidencjaVAT1) {
                if (p.ewidencja.isNiemcy()) {
                   zwrot = true;
                }
            }
        }
        return zwrot;
    }
    
    
    public Double getNettoWaluta() {
        double suma = 0.0;
        for (KwotaKolumna1 p : this.listakwot1) {
            if (p.getNettowaluta() != 0.0) {
                suma += p.getNettowaluta();
            }
        }
        return suma;
    }
    
    public Double getVatWaluta() {
        double suma = 0.0;
        try {
            if (this.tabelanbp!=null) {
                for (KwotaKolumna1 p : this.listakwot1) {
                    if (p.getNettowaluta() != 0.0) {
                        suma += p.getVat();
                    }
                }
                double kurs = this.tabelanbp.getKurssredniPrzelicznik();
                suma = Z.z4(suma/kurs);
            } else {
                suma = getVatWalutaCSV();
            }
        } catch (Exception e){}
        return suma;
    }
    
    public Double getBruttoWaluta() {
        return this.getNettoWaluta()+this.getVatWaluta();
    }
    
    public Double getVatWalutaCSV() {
        double suma = 0.0;
        for (KwotaKolumna1 p : this.listakwot1) {
            if (p.getVatwaluta() != 0.0) {
                suma += p.getVatwaluta();
            }
        }
        return Z.z(suma);
    }

    public int getSprawdzony() {
        return sprawdzony;
    }

    public void setSprawdzony(int sprawdzony) {
        this.sprawdzony = sprawdzony;
    }

    public FakturaCis getFakturaCis() {
        return fakturaCis;
    }

    public void setFakturaCis(FakturaCis fakturaCis) {
        this.fakturaCis = fakturaCis;
    }
      
    public Date getDataedycji() {
        return dataedycji;
    }

    public void setDataedycji(Date dataedycji) {
        this.dataedycji = dataedycji;
    }

    public String getEdytowal() {
        return edytowal;
    }

    public String getUlganazledlugidatapierwsza() {
        return ulganazledlugidatapierwsza;
    }

    public void setUlganazledlugidatapierwsza(String ulganazledlugidatapierwsza) {
        this.ulganazledlugidatapierwsza = ulganazledlugidatapierwsza;
    }

    public String getUlganazledlugidatapierwszaplus90() {
        return ulganazledlugidatapierwszaplus90;
    }

    public void setUlganazledlugidatapierwszaplus90(String ulganazledlugidatapierwszaplus90) {
        this.ulganazledlugidatapierwszaplus90 = ulganazledlugidatapierwszaplus90;
    }

    public String getUlganazledlugidatadruga() {
        return ulganazledlugidatadruga;
    }

    public void setUlganazledlugidatadruga(String ulganazledlugidatadruga) {
        this.ulganazledlugidatadruga = ulganazledlugidatadruga;
    }
    
    

//    public List<UmorzenieN> getListaumorzen() {
//        return listaumorzen;
//    }
//
//    public void setListaumorzen(List<UmorzenieN> listaumorzen) {
//        this.listaumorzen = listaumorzen;
//    }
    public void setEdytowal(String edytowal) {      
        this.edytowal = edytowal;
    }

    public double getVat() {
        double br = this.brutto == null ? 0.0 : this.brutto;
        double nt = this.netto == null ? 0.0 : this.netto;
        return this.dokumentProsty ? 0.0 : Z.z(br-nt);
    }
    
    public boolean czyCecha() {
        if (this.cechadokumentuLista != null && this.cechadokumentuLista.size() > 0) {
            return true;
        } 
        return false;
    }
    
    public String getListaCech() {
        StringBuilder sb = new StringBuilder();
        if (czyCecha()) {
            for (Cechazapisu p : this.cechadokumentuLista) {
                sb.append(p.getNazwacechy());
                sb.append(",");
            }
        }
        String zwrot = sb.toString();
        zwrot = StringUtils.removeEnd(zwrot, ",");
        return zwrot;
    }
    
     public boolean isSaPlatnosci() {
        boolean zwrot = false;
        if (!this.platnosciwaluta.isEmpty()) {
            zwrot = true;
        }
        return zwrot;
    }
     public boolean isNiemcy() {
        boolean zwrot = false;
        if (this.rodzajedok!=null&&this.rodzajedok.getRodzajtransakcji().contains("Niemcy")) {
            zwrot = true;
        }
        return zwrot;
    }
      public String pobierzsymbole() {
        String zwrot = null;
        StringBuilder sb = new StringBuilder();
        if (this.getOznaczenie1()!=null || this.getOznaczenie2()!=null || this.getOznaczenie3()!=null || this.getOznaczenie4()!=null ) {
            if (this.getOznaczenie1()!=null) {
                sb.append(this.getOznaczenie1().getSymbol());
                sb.append(", ");
            }
            if (this.getOznaczenie2()!=null) {
                sb.append(this.getOznaczenie2().getSymbol());
                sb.append(", ");
            }
            if (this.getOznaczenie3()!=null) {
                sb.append(this.getOznaczenie3().getSymbol());
                sb.append(", ");
            }
            if (this.getOznaczenie4()!=null) {
                sb.append(this.getOznaczenie4().getSymbol());
            }
            zwrot = sb.toString();
            zwrot = zwrot.substring(0, zwrot.length()-2);
        }
        return zwrot;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nrWlDk);
        hash = 83 * hash + Objects.hashCode(this.kontr1);
        hash = 83 * hash + Objects.hashCode(this.podatnik);
        hash = 83 * hash + Objects.hashCode(this.dataWyst);
        hash = 83 * hash + Objects.hashCode(this.rodzajedok);
        hash = 83 * hash + Objects.hashCode(this.netto);
        return hash;
    }

   

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dok other = (Dok) obj;
        if (!Objects.equals(this.nrWlDk, other.nrWlDk)) {
            return false;
        }
        if (!Objects.equals(this.kontr1, other.kontr1)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.dataWyst, other.dataWyst)) {
            return false;
        }
        if (!Objects.equals(this.rodzajedok, other.rodzajedok)) {
            return false;
        }
        if (!Objects.equals(this.netto, other.netto)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return ". Info dok: nrWlDk=" + nrWlDk + ", kontrahent=" + kontr1.getNpelna() + ", podatnik=" + podatnik.getPrintnazwa() + ", seria=" + rodzajedok.getSkrotNazwyDok() + ", dataWyst=" + dataWyst;
    }

    public String toString2() {
        return "nrWlDk=" + nrWlDk + ", kontrahent=" + kontr1.getNpelna()+ ", dataWyst=" + dataWyst;
    }

    
}
