/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Klienci;
import entity.Podatnik;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.jboss.weld.util.collections.ArraySet;
import viewfk.subroutines.ObslugaWiersza;
 
/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(catalog = "pkpir", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dokfk.findAll", query = "SELECT d FROM Dokfk d"),
    @NamedQuery(name = "Dokfk.findBySeriadokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk"),
    @NamedQuery(name = "Dokfk.findBySeriaRokdokfk", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.seriadokfk = :seriadokfk AND d.dokfkPK.rok = :rok"),
    @NamedQuery(name = "Dokfk.findByNrkolejny", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.nrkolejnywserii = :nrkolejnywserii"),
    @NamedQuery(name = "Dokfk.findByPodatnik", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik"),
    @NamedQuery(name = "Dokfk.findByPodatnikRok", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik AND d.dokfkPK.rok = :rok"),
    @NamedQuery(name = "Dokfk.findByPK", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK = :dokfkPK"),
    @NamedQuery(name = "Dokfk.findByDatawystawienia", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia"),
    @NamedQuery(name = "Dokfk.findByDatawystawieniaNumer", query = "SELECT d FROM Dokfk d WHERE d.datawystawienia = :datawystawienia AND d.numerwlasnydokfk = :numer"),
    @NamedQuery(name = "Dokfk.findByLastofaType", query = "SELECT d FROM Dokfk d WHERE d.dokfkPK.podatnik = :podatnik AND d.dokfkPK.seriadokfk = :seriadokfk ORDER BY d.dokfkPK.nrkolejnywserii DESC"),
    @NamedQuery(name = "Dokfk.findByNumer", query = "SELECT d FROM Dokfk d WHERE d.numerwlasnydokfk = :numer")})
public class Dokfk implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DokfkPK dokfkPK = new DokfkPK();
    @MapsId("seriadokfk")
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "rodzajdokSkrot", referencedColumnName = "skrot"),
        @JoinColumn(name = "rodzajdokPodatnik", referencedColumnName = "podatnikObj")
    })
    private Rodzajedok rodzajedok;
    @MapsId("podatnik")
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "podatnikObj", referencedColumnName = "nip")
    private Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datawystawienia", nullable = false, length = 10)
    private String datawystawienia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datadokumentu", nullable = false, length = 10)
    private String datadokumentu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataoperacji", nullable = false, length = 10)
    private String dataoperacji;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datawplywu", nullable = false, length = 10)
    private String datawplywu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
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
    @ManyToOne
    private Tabelanbp tabelanbp;
    @Column (name = "wartoscdokumentu")
    private double wartoscdokumentu;
    @Column (name="wtrakcieedycji")
    private boolean wTrakcieEdycji;
    @JoinColumn(name = "kontr")
    @ManyToOne
    private Klienci kontr;
 

    
    
    public Dokfk() {
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
    }

    public Dokfk(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
    }

    public Dokfk(DokfkPK dokfkPK, String datawystawienia, String numer) {
        this.dokfkPK = dokfkPK;
        this.datawystawienia = datawystawienia;
        this.numerwlasnydokfk = numer;
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.listawierszy = new ArrayList<>();
        this.wTrakcieEdycji = false;
    }

    public Dokfk(String symbolPoprzedniegoDokumentu, Podatnik podatnik) {
        this.liczbarozliczonych = 0;
        this.wartoscdokumentu = 0.0;
        this.wTrakcieEdycji = false;
        this.listawierszy = new ArrayList<>();
        ustawNoweSelected(symbolPoprzedniegoDokumentu, podatnik);
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
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

    public boolean getwTrakcieEdycji() {
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

    public void dodajKwotyWierszaDoSumyDokumentu(int numerwiersza) {
        try {//robimy to bo sa nowy wiersz jest tez podsumowywany, ale moze byc przeciez pusty wiec wyrzuca blad
            Wiersz biezacywiersz = this.listawierszy.get(numerwiersza);
            int typwiersza = biezacywiersz.getTypWiersza();
            double suma = 0.0;
            if (typwiersza==1) {
                suma += biezacywiersz.getStronaWn().getKwota();
            } else if (typwiersza==2) {
                suma += biezacywiersz.getStronaMa().getKwota();
            } else {
                double kwotaWn = biezacywiersz.getStronaWn().getKwota();
                double kwotaMa = biezacywiersz.getStronaMa().getKwota();
                if (kwotaMa>kwotaWn) {
                    suma += biezacywiersz.getStronaWn().getKwota();
                } else {
                    suma += biezacywiersz.getStronaMa().getKwota();
                }
            }
            this.wartoscdokumentu = this.wartoscdokumentu + suma;
        } catch (Exception e) {
            
        }
    }
    
    public void przeliczKwotyWierszaDoSumyDokumentu() {
        this.wartoscdokumentu = 0.0;
        int liczbawierszy = this.listawierszy.size();
        for (int i = 0; i < liczbawierszy; i++) {
            dodajKwotyWierszaDoSumyDokumentu(i);
        }
    }
    
    public void uzupelnijwierszeodane() {
        //ladnie uzupelnia informacje o wierszu pk
        List<Wiersz> wierszewdokumencie = this.listawierszy;
        try {
            for (Wiersz p : wierszewdokumencie) {
                String opis = p.getOpisWiersza();
                if (opis.contains("kontown")) {
                    p.setDataksiegowania(this.datawystawienia);
                    //p.setTypWiersza(1);
                    p.setDokfk(this);
                } else if (opis.contains("kontoma")) {
                    p.setDataksiegowania(this.datawystawienia);
                    //p.setTypWiersza(2);
                    p.setDokfk(this);
                } else {
                    p.setDataksiegowania(this.datawystawienia);
                    //p.setTypWiersza(0);
                    p.setDokfk(this);
                }
            }
        } catch (Exception e) {
        }
    }
    
    public final void ustawNoweSelected(String symbolPoprzedniegoDokumentu, Podatnik podatnik) {
        this.dokfkPK = new DokfkPK();
        //chodzi o FVS, FVZ a nie o numerwlasnydokfk :)
        this.setPodatnikObj(podatnik);
        this.dokfkPK.setSeriadokfk(symbolPoprzedniegoDokumentu);
        this.getListawierszy().add(ObslugaWiersza.ustawPierwszyWiersz(this));
        this.setZablokujzmianewaluty(false); 
    }
    
    public Wiersz poprzedniWiersz(Wiersz wiersz) {
        int index = wiersz.getIdporzadkowy();
         for (Wiersz p : this.getListawierszy()) {
                if (p.getIdporzadkowy() == index-1) {
                    return p;
                }
            }
         return null;
    }
    
    public Wiersz nastepnyWiersz(Wiersz wiersz) {
        int index = wiersz.getIdporzadkowy();
         for (Wiersz p : this.getListawierszy()) {
                if (p.getIdporzadkowy() == index+1) {
                    return p;
                }
            }
         return null;
    }
}
