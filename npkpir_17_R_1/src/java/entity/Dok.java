/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.EVatwpis;
import embeddable.Rozrachunek;
import embeddable.Stornodoch;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Dok.findByKontr", query = "SELECT d FROM Dok d WHERE d.kontr = :kontr"),
    @NamedQuery(name = "Dok.findByDataWyst", query = "SELECT d FROM Dok d WHERE d.dataWyst = :dataWyst"),
    @NamedQuery(name = "Dok.findByTermin30", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin30"),
    @NamedQuery(name = "Dok.findByTermin90", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin90"),
    @NamedQuery(name = "Dok.findByTermin150", query = "SELECT d FROM Dok d WHERE d.termin30 = :termin150"),
    @NamedQuery(name = "Dok.findByTerminPlatnosci", query = "SELECT d FROM Dok d WHERE d.terminPlatnosci = :terminPlatnosci"),
    @NamedQuery(name = "Dok.findByNrWlDk", query = "SELECT d FROM Dok d WHERE d.nrWlDk = :nrWlDk"),
    @NamedQuery(name = "Dok.findByRodzTrans", query = "SELECT d FROM Dok d WHERE d.rodzTrans = :rodzTrans"),
    @NamedQuery(name = "Dok.findByOpis", query = "SELECT d FROM Dok d WHERE d.opis = :opis"),
    @NamedQuery(name = "Dok.findByKwota", query = "SELECT d FROM Dok d WHERE d.kwota = :kwota"),
    @NamedQuery(name = "Dok.findByUwagi", query = "SELECT d FROM Dok d WHERE d.uwagi = :uwagi"),
    @NamedQuery(name = "Dok.findByPkpirM", query = "SELECT d FROM Dok d WHERE d.pkpirM = :pkpirM"),
    @NamedQuery(name = "Dok.findByPkpirR", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR"),
    @NamedQuery(name = "Dok.findByVatM", query = "SELECT d FROM Dok d WHERE d.vatM = :vatM"),
    @NamedQuery(name = "Dok.findByVatR", query = "SELECT d FROM Dok d WHERE d.vatR = :vatR"),
    @NamedQuery(name = "Dok.findByPkpirKol", query = "SELECT d FROM Dok d WHERE d.pkpirKol = :pkpirKol"),
    @NamedQuery(name = "Dok.findDuplicate", query = "SELECT d FROM Dok d WHERE d.kontr = :kontr AND d.nrWlDk = :nrWlDk AND d.kwota = :kwota"),
    @NamedQuery(name = "Dok.findPoprzednik", query = "SELECT d FROM Dok d WHERE d.pkpirR = :pkpirR AND d.pkpirM = :pkpirM AND d.opis = :opis"),
    @NamedQuery(name = "Dok.findByRozliczony", query = "SELECT d FROM Dok d WHERE d.rozliczony = :rozliczony"),
})
    
public class Dok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_dok")
    private Long idDok;
    @Size(max = 10)
    @Column(name = "nrWpkpir")
    private int nrWpkpir;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nr_wl_dk")
    private String nrWlDk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kontr")
    private Klienci kontr;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kwota")
    private Double kwota;
    @Size(max = 65)
    @Column(name = "pkpir_kol")
    private String pkpirKol;
    @Column(name = "kwotaX")
    private Double kwotaX;
    @Size(max = 65)
    @Column(name = "pkpir_kolX")
    private String pkpirKolX;
    @Column(name = "netto")
    private Double netto;
    @Size(max = 45)
    @Column(name = "uwagi")
    private String uwagi;
    @Size(max = 45)
    @Column(name = "pkpir_m")
    private String pkpirM;
    @Size(max = 45)
    @Column(name = "pkpir_r")
    private String pkpirR;
    @Size(max = 45)
    @Column(name = "vat_m")
    private String vatM;
    @Size(max = 45)
    @Column(name = "vat_r")
    private String vatR;
    @Size(max = 65)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "evat")
    private List<EVatwpis> ewidencjaVAT;
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
    @Lob
    @Column(name = "rozrachunki")
    private ArrayList<Rozrachunek> rozrachunki;
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
    
    
    public Dok() {
    }

    public Dok(Long idDok) {
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
        return kontr;
    }

    public void setKontr(Klienci kontr) {
        this.kontr = kontr;
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

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
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

    public String getPkpirKol() {
        return pkpirKol;
    }

    public void setPkpirKol(String pkpirKol) {
        this.pkpirKol = pkpirKol;
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

    public List<EVatwpis> getEwidencjaVAT() {
        return ewidencjaVAT;
    }

    public void setEwidencjaVAT(List<EVatwpis> ewidencjaVAT) {
        this.ewidencjaVAT = ewidencjaVAT;
    }

    public boolean isDokumentProsty() {
        return dokumentProsty;
    }

    public void setDokumentProsty(boolean dokumentProsty) {
        this.dokumentProsty = dokumentProsty;
    }

    public Double getKwotaX() {
        return kwotaX;
    }

    public void setKwotaX(Double kwotaX) {
        this.kwotaX = kwotaX;
    }

    public String getPkpirKolX() {
        return pkpirKolX;
    }

    public void setPkpirKolX(String pkpirKolX) {
        this.pkpirKolX = pkpirKolX;
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

    public ArrayList<Rozrachunek> getRozrachunki() {
        return rozrachunki;
    }

    public void setRozrachunki(ArrayList<Rozrachunek> rozrachunki) {
        this.rozrachunki = rozrachunki;
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

   

    
   
 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDok != null ? idDok.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dok)) {
            return false;
        }
        Dok other = (Dok) object;
        if ((this.idDok == null && other.idDok != null) || (this.idDok != null && !this.idDok.equals(other.idDok))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dok{" + "idDok=" + idDok + ", nrWlDk=" + nrWlDk + ", kontr=" + kontr + ", podatnik=" + podatnik + ", wprowadzil=" + wprowadzil + ", dataWyst=" + dataWyst + ", kwota=" + kwota + '}';
    }

  
    

    
}
