/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import data.Data;
import embeddable.Mce;
import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.primefaces.context.RequestContext;
import view.WpisView;
import viewfk.subroutines.ObslugaWiersza;
 
/**
 *
 * @author Osito
 */
@Entity
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numerwlasnydokfk", "podatnikObj", "rok", "seriadokfk", "kontr"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findBySeriadokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk"),
    @NamedQuery(name = "Dokfk.findBySeriaRokdokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok"),
    @NamedQuery(name = "Dokfk.findBySeriaNumerRokdokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok AND d.podatnikObj = :podatnik AND d.miesiac = :mc"),
    @NamedQuery(name = "Dokfk.findByNrkolejny", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.nrkolejnywserii = :nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByPodatnik", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokMc", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dokfkPK.rok = :rok AND d.miesiac = :mc"),
    @NamedQuery(name = "Dokfk.findByPodatnikRok", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dokfkPK.rok = :rok ORDER BY d.datadokumentu"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokMcKategoria", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dokfkPK.rok = :rok AND d.miesiac = :mc AND d.rodzajedok.skrot = :kategoria"),
    @NamedQuery(name = "Dokfk.findByPodatnikRokKategoria", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dokfkPK.rok = :rok AND d.rodzajedok.skrot = :kategoria"),
    @NamedQuery(name = "Dokfk.findByBKVAT", query = "SELECT d FROM Dokfk d WHERE d.vatR = :vatR AND d.podatnikObj = :podatnik"),
    @NamedQuery(name = "Dokfk.findByPK", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok AND d.podatnikObj = :podatnikObj AND d.dokfkPK.nrkolejnywserii = :nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByDuplikat", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk"),
    @NamedQuery(name = "Dokfk.findByDokEdycjaFK", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk AND d.kontr = :kontrahent"),
    @NamedQuery(name = "Dokfk.findByDuplikatKontrahent", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok AND d.podatnikObj = :podatnikObj AND d.numerwlasnydokfk = :numerwlasnydokfk AND d.kontr = :kontrahent"),
    @NamedQuery(name = "Dokfk.findByDatawystawienia", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Dokfk.findByDatawystawieniaNumer", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia AND d.numerwlasnydokfk = :numer"),
    @NamedQuery(name = "Dokfk.findByLastofaType", query = "SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok ORDER BY d.dokfkPK.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByLastofaTypeKontrahent", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik AND d.dokfkPK.seriadokfk = :seriadokfk AND d.kontr = :kontr AND d.dokfkPK.rok = :rok ORDER BY d.dokfkPK.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numerwlasnydokfk = :numer")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DokfkPK dokfkPK = new DokfkPK();
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "rodzajdokSkrot", referencedColumnName = "skrot"),
        @JoinColumn(name = "rodzajdokPodatnik", referencedColumnName = "podatnikObj")
    })
    private Rodzajedok rodzajedok;
    @MapsId("podatnik")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "numerwlasnydokfk", nullable = false, length = 255)
    private String numerwlasnydokfk;
    @OneToMany(mappedBy = "dokfk", cascade = CascadeType.ALL,  orphanRemoval=true, fetch = FetchType.EAGER)
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
    @Column (name = "wartoscdokumentu")
    private double wartoscdokumentu;
    @Column (name="wtrakcieedycji")
    private boolean wTrakcieEdycji;
    @JoinColumn(name = "kontr", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontr;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dokfk", cascade = CascadeType.ALL,  orphanRemoval=true)
    private List<EVatwpisFK> ewidencjaVAT;
    @Size(max = 2)
    @Column(name = "vat_m")
    private String vatM;
    @Size(max = 4)
    @Column(name = "vat_r")
    private String vatR;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
      name="Dokfk_Cechazapisu",
      joinColumns={
          @JoinColumn(name = "seriadokfk", referencedColumnName = "seriadokfk"),
          @JoinColumn(name = "nrkolejnywserii", referencedColumnName = "nrkolejnywserii"),
          @JoinColumn(name = "podatnikObj", referencedColumnName = "podatnikObj"),
          @JoinColumn(name = "rok", referencedColumnName = "rok")
      },
      inverseJoinColumns={
          @JoinColumn(name = "nazwacechy", referencedColumnName = "nazwacechy"),
          @JoinColumn(name = "rodzajcechy", referencedColumnName = "rodzajcechy")
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
 

    
    
    public Dokfk() {
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
    }
    
    public Dokfk(String opis) {
        this.dokfkPK.setSeriadokfk("BO");
        this.opisdokfk = opis;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
    }

    public Dokfk(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
    }

    public Dokfk(DokfkPK dokfkPK, String datawystawienia, String numer) {
        this.dokfkPK = dokfkPK;
        this.datawystawienia = datawystawienia;
        this.numerwlasnydokfk = numer;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
        this.wTrakcieEdycji = false;
    }

    public Dokfk(String symbolPoprzedniegoDokumentu, Rodzajedok rodzajedok, WpisView wpisView) {
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
        ustawNoweSelected(symbolPoprzedniegoDokumentu, rodzajedok, wpisView);
    }
    
    public Dokfk(String symbolPoprzedniegoDokumentu, Rodzajedok rodzajedok, WpisView wpisView, Klienci klienci) {
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.kontr = klienci;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        this.ewidencjaVAT = new ArrayList<>();
        this.cechadokumentuLista = new ArrayList<>();
        String data = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        this.setDatawystawienia(data);
        this.setDatawplywu(data);
        ustawNoweSelected(symbolPoprzedniegoDokumentu, rodzajedok, wpisView);
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getNrdziennika() {
        return nrdziennika;
    }

    public void setNrdziennika(String nrdziennika) {
        this.nrdziennika = nrdziennika;
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


    public Dokfk(String seriadokfk, int nrkolejny, String podatnik, String rok) {
        this.dokfkPK = new DokfkPK(seriadokfk, nrkolejny, podatnik, rok);
    }
    
    public DokfkPK getDokfkPK() {
        return dokfkPK;
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

    
    public void setOpisdokfk(String opisdokfk) {
        this.opisdokfk = opisdokfk;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }
    
    
    public void setDokfkPK(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
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

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
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
        int hash = 0;
        hash += (dokfkPK != null ? dokfkPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dokfk)) {
            return false;
        }
        Dokfk other = (Dokfk) object;
        if ((this.dokfkPK == null && other.dokfkPK != null) || (this.dokfkPK != null && !this.dokfkPK.equals(other.dokfkPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.Dokfk[ dokfkPK=" + dokfkPK + " ]";
    }
    
    public String getDokfkLP() {
        StringBuilder s = new StringBuilder();
        s.append(this.dokfkPK.getSeriadokfk());
        s.append("/");
        s.append(this.lp);
        s.append("/");
        s.append(this.dokfkPK.getRok());
        return s.toString();
    }
    
    public String getDokfkSN() {
        StringBuilder s = new StringBuilder();
        s.append(this.dokfkPK.getSeriadokfk());
        s.append("/");
        s.append(this.dokfkPK.getNrkolejnywserii());
        s.append("/");
        s.append(this.dokfkPK.getRok());
        return s.toString();
    }

    public void dodajKwotyWierszaDoSumyDokumentu(Wiersz biezacywiersz) {
        try {//robimy to bo sa nowy wiersz jest tez podsumowywany, ale moze byc przeciez pusty wiec wyrzuca blad
            int typwiersza = biezacywiersz.getTypWiersza();
            double wn = biezacywiersz.getStronaWn() != null ? biezacywiersz.getStronaWn().getKwota() : 0.0;
            double ma = biezacywiersz.getStronaMa() != null ? biezacywiersz.getStronaMa().getKwota() : 0.0;
            double suma = 0.0;
            if (typwiersza==1) {
                suma += wn;
            } else if (typwiersza==2) {
                suma += ma;
            } else {
                double kwotaWn = wn;
                double kwotaMa = ma;
                if (kwotaMa>kwotaWn) {
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
        RequestContext.getCurrentInstance().update("formwpisdokument:panelzkwotamidok");
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
    
    public final void ustawNoweSelected(String symbolPoprzedniegoDokumentu,  Rodzajedok rodzajedok, WpisView wpisView) {
        this.dokfkPK = new DokfkPK();
        //chodzi o FVS, FVZ a nie o numerwlasnydokfk :)
        this.dokfkPK.setPodatnik(wpisView.getPodatnikObiekt().getNip());
        this.setPodatnikObj(wpisView.getPodatnikObiekt());
        if (symbolPoprzedniegoDokumentu != null) {
            this.dokfkPK.setSeriadokfk(symbolPoprzedniegoDokumentu);
            this.setRodzajedok(rodzajedok);
        } else {
            this.dokfkPK.setSeriadokfk("ZZ");
        }
        this.dokfkPK.setRok(wpisView.getRokWpisuSt());
        this.setMiesiac(wpisView.getMiesiacWpisu());
        this.setVatR(wpisView.getRokWpisuSt());
        this.setVatM(wpisView.getMiesiacWpisu());
        this.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(this));
        this.setZablokujzmianewaluty(false); 
    }
    
    public Wiersz poprzedniWiersz(Wiersz wiersz) {
        int index = this.listawierszy.indexOf(wiersz);
        try {
            return this.listawierszy.get(index-1);
        } catch (Exception e) {
            
        }
        return null;
    }
    
     
    public Wiersz nastepnyWiersz(Wiersz wiersz) {
         int index = this.listawierszy.indexOf(wiersz);
        try {
            return this.listawierszy.get(index+1);
        } catch (Exception e) {
            
        }
        return null;
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
            symbolPoprzedniegoDokumentu = new String(this.getDokfkPK().getSeriadokfk());
        } catch (Exception e) {
        }
        return symbolPoprzedniegoDokumentu;
    }
     
    public List<StronaWiersza> getStronyWierszy() {
        List<StronaWiersza> lista = new ArrayList<>();
        for (Wiersz p : this.listawierszy) {
            if (p.getStronaWn()!=null) {
                lista.add(p.getStronaWn());
            }
            if (p.getStronaMa()!=null) {
                lista.add(p.getStronaMa());
            }
        }
        return lista;
    }
     
    public int sprawdzczynaniesionorozrachunki() {
        int brakrozrachunkow = 0;
        Iterator it = this.getStronyWierszy().iterator();
        while (it.hasNext()) {
            StronaWiersza p = (StronaWiersza) it.next();
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
    
    public void oznaczewidencjeVAT() {
        for (EVatwpisFK p : this.ewidencjaVAT) {
            if (p.getInnyokres()==0) {
                p.setMcEw(this.getMiesiac());
                p.setRokEw(this.getDokfkPK().getRok());
            } else {
                String[] nowyokres = Mce.zwiekszmiesiac(this.getDokfkPK().getRok(), this.getMiesiac(),p.getInnyokres());
                p.setRokEw(nowyokres[0]);
                p.setMcEw(nowyokres[1]);
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
                    String nowyopis = "zapis BO: "+p.getOpisWiersza();
                    p.setOpisWiersza(nowyopis);
                }
                if (p.getStronaWn().getKonto() != null) {
                    p.setTypWiersza(1);
                    p.getStrona().remove("Ma");
                } else {
                    p.setTypWiersza(2);
                    p.getStrona().remove("Wn");
                }
            }
        }
    }
}
