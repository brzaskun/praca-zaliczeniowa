/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Stornodoch;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "dok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dok.findAll", query = "SELECT d FROM Dok d"),
    @NamedQuery(name = "Dok.findByIdDok", query = "SELECT d FROM Dok d WHERE d.idDok = :idDok"),
    @NamedQuery(name = "Dok.findByIdDokOdDo", query = "SELECT d FROM Dok d WHERE d.idDok > :odd AND D.idDok < :dod"),
    @NamedQuery(name = "Dok.findByTypDokumentu", query = "SELECT d FROM Dok d WHERE d.typdokumentu = :typdokumentu"),
    @NamedQuery(name = "Dok.findByKontr", query = "SELECT d FROM Dok d WHERE d.kontr1 = :kontr"),
    @NamedQuery(name = "Dok.findByKontr1Null", query = "SELECT d FROM Dok d WHERE d.kontr1 IS NULL"),
    @NamedQuery(name = "Dok.findByDataWyst", query = "SELECT d FROM Dok d WHERE d.dataWyst = :dataWyst"),
    @NamedQuery(name = "Dok.findByTermin30", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin30"),
    @NamedQuery(name = "Dok.findByTermin90", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin90"),
    @NamedQuery(name = "Dok.findByTermin150", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin150"),
    @NamedQuery(name = "Dok.findByTerminPlatnosci", query = "SELECT d FROM Dok d WHERE d.terminPlatnosci = :terminPlatnosci"),
    @NamedQuery(name = "Dok.findByNrWlDk", query = "SELECT d FROM Dok d WHERE d.nrWlDk = :nrWlDk"),
    @NamedQuery(name = "Dok.findByRodzTrans", query = "SELECT d FROM Dok d WHERE d.rodzTrans = :rodzTrans"),
    @NamedQuery(name = "Dok.findByOpis", query = "SELECT d FROM Dok d WHERE d.opis = :opis"),
    @NamedQuery(name = "Dok.findByUwagi", query = "SELECT d FROM Dok d WHERE d.uwagi = :uwagi"),
    @NamedQuery(name = "Dok.findByPkpirM", query = "SELECT d FROM Dok d WHERE d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByPkpirRMCount", query = "SELECT COUNT(d) FROM Dok d WHERE d.podatnik = :podatnik AND d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByPkpirR", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR"),
    @NamedQuery(name = "Dok.findByVatM", query = "SELECT d FROM Dok d WHERE d.vatM = :vatM"),
    @NamedQuery(name = "Dok.findByVatR", query = "SELECT d FROM Dok d WHERE d.vatR = :vatR"),
    @NamedQuery(name = "Dok.znajdzInwestycja", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.dataWyst = :data AND d.netto = :netto AND d.nrWlDk = :numer"),
    @NamedQuery(name = "Dok.findDuplicate", query = "SELECT d FROM Dok d WHERE d.kontr1 = :kontr AND d.nrWlDk = :nrWlDk AND d.netto = :netto AND d.pkpirR = :pkpirR"),
    @NamedQuery(name = "Dok.findDuplicateAMO", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.nrWlDk = :nrWlDk AND d.pkpirR = :pkpirR AND d.typdokumentu = 'AMO'"),
    @NamedQuery(name = "Dok.findDuplicatewTrakcie", query = "SELECT d FROM Dok d WHERE d.kontr1 = :kontr AND d.nrWlDk = :nrWlDk AND d.podatnik = :podatnik AND d.typdokumentu = :typdokumentu"),
    @NamedQuery(name = "Dok.findStornoDok", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.opis = :opis"),
    @NamedQuery(name = "Dok.findPoprzednik", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.opis = :opis"),
    @NamedQuery(name = "Dok.findByRozliczony", query = "SELECT d FROM Dok d WHERE d.rozliczony = :rozliczony"),
    @NamedQuery(name = "Dok.findByPodatnik", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByfindByLastofaTypeKontrahent", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.kontr1 = :kontr AND d.pkpirR = :pkpirR  ORDER BY d.idDok DESC"),
    @NamedQuery(name = "Dok.findByFakturaWystawiona", query = "SELECT d FROM Dok d WHERE d.podatnik = :podatnik AND d.kontr1 = :kontr AND d.nrWlDk = :nrWlDk AND d.brutto = :brutto"),
    @NamedQuery(name = "Dok.findByBK", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByInwestycje", query = "SELECT d FROM Dok d WHERE d.symbolinwestycji IS NOT NULL AND d.symbolinwestycji != '' AND d.symbolinwestycji != 'wybierz'"),
    @NamedQuery(name = "Dok.findByBKPrzychody", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik AND d.typdokumentu = 'SPRY'"),
    @NamedQuery(name = "Dok.findByBKMCPrzychody", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.typdokumentu = 'SPRY'"),
    @NamedQuery(name = "Dok.findByBKVAT", query = "SELECT d FROM Dok d WHERE d.vatR = :vatR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByTPR", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik AND d.typdokumentu = :typdokumentu"),
    @NamedQuery(name = "Dok.findByBKM", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByRokKW", query = "SELECT d FROM Dok d WHERE d.vatR = :pkpirR AND d.podatnik = :podatnik AND (d.vatM = :mc1 OR d.vatM = :mc2 OR d.vatM = :mc3)"),
    @NamedQuery(name = "Dok.findByDuplikat", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.podatnik = :podatnik"),
    @NamedQuery(name = "Dok.findByRMPT", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.podatnik = :podatnik AND d.typdokumentu = :typdokumentu"),
    @NamedQuery(name = "Dok.znajdzDokumentPodatnikWpr", query = "SELECT DISTINCT d.podatnik FROM Dok d WHERE d.wprowadzil = :wprowadzil")
})
@Cacheable 
public class Dok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dok")
    private Long idDok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "typdokumentu")
    private String typdokumentu;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "podatnik")
    private String podatnik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "wpr")
    private String wprowadzil;
    @Column(name = "data_k", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataK;
    @Size(max = 10)
    @Column(name = "data_wyst")
//    @Temporal(TemporalType.DATE)
    private String dataWyst;
    @Size(max = 10)
    @Column(name = "data_sprzedazy")
//    @Temporal(TemporalType.DATE)
    private String dataSprz;
    @Size(max = 45)
    @Column(name = "rodz_trans")
    private String rodzTrans;
    @Size(max = 45)
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
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dok", cascade = CascadeType.ALL,  orphanRemoval=true)
    private ArrayList<Rozrachunek1> rozrachunki1;
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
    private ArrayList<Stornodoch> storno;
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
    
    public Dok() {
        this.listakwot1 = new ArrayList<>();
        this.typdokumentu = "";
        this.getListakwot1().add(new KwotaKolumna1());
    }

    public Dok(Long idDok) {
        this.listakwot1 = new ArrayList<>();
        this.typdokumentu = "";
        this.getListakwot1().add(new KwotaKolumna1());
        this.idDok = idDok;
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

    public String getRodzTrans() {
        return rodzTrans;
    }

    public void setRodzTrans(String rodzTrans) {
        this.rodzTrans = rodzTrans;
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

    public void setVatM(String vatM) {
        this.vatM = vatM;
    }

    public String getVatR() {
        return vatR;
    }

    public void setVatR(String vatR) {
        this.vatR = vatR;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
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

//    public List<EVatwpis> getEwidencjaVAT() {
//        return ewidencjaVAT;
//    }
//
//    public void setEwidencjaVAT(List<EVatwpis> ewidencjaVAT) {
//        this.ewidencjaVAT = ewidencjaVAT;
//    }

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

    public ArrayList<Stornodoch> getStorno() {
        return storno;
    }

    public void setStorno(ArrayList<Stornodoch> storno) {
        this.storno = storno;
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public Double getBrutto() {
        return brutto;
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

    public ArrayList<Rozrachunek1> getRozrachunki1() {
        return rozrachunki1;
    }

    public void setRozrachunki1(ArrayList<Rozrachunek1> rozrachunki1) {
        this.rozrachunki1 = rozrachunki1;
    }

    public Waluty getWalutadokumentu() {
        return walutadokumentu;
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

    
    public Double getNettoWaluta() {
        double suma = 0.0;
        for (KwotaKolumna1 p : this.listakwot1) {
            if (p.getNettowaluta() != null) {
                suma += p.getNettowaluta();
            }
        }
        return suma;
    }

    public int getSprawdzony() {
        return sprawdzony;
    }

    public void setSprawdzony(int sprawdzony) {
        this.sprawdzony = sprawdzony;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.nrWlDk);
        hash = 83 * hash + Objects.hashCode(this.kontr1);
        hash = 83 * hash + Objects.hashCode(this.podatnik);
        hash = 83 * hash + Objects.hashCode(this.dataWyst);
        hash = 83 * hash + Objects.hashCode(this.rodzTrans);
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
        if (!Objects.equals(this.rodzTrans, other.rodzTrans)) {
            return false;
        }
        if (!Objects.equals(this.netto, other.netto)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return ". Info dok: nrWlDk=" + nrWlDk + ", kontrahent=" + kontr1.getNpelna() + ", podatnik=" + podatnik + ", wprowadzil=" + wprowadzil + ", dataWyst=" + dataWyst;
    }

 

    
}
