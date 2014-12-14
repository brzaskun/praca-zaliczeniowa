/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Osito
 */
@Cacheable(false)
@Entity
@Table(name = "stronawiersza")
@NamedQueries({
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wnma = :wnma AND t.typStronaWiersza = '1'"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWalutaBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.symbolWalutyBO = :symbolwaluty AND t.wnma = :wnma AND t.typStronaWiersza = '9'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutaWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWszystkieNT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.nowatransakcja = '1'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.wiersz.tabelanbp.waluta.symbolwaluty = :wybranaWalutaDlaKonta AND t.nowatransakcja = '1'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutyWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza != 9"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokMcVAT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.vatM = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaWynik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWynik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaBilans", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokBilans", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaBilansBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.dokfkPK.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza = 9")
})
public class StronaWiersza implements Serializable{
     private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idwiersza", referencedColumnName = "idwiersza")
    private Wiersz wiersz;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotaPLN")
    private double kwotaPLN;
    @Column(name = "kwotaWaluta")
    private double kwotaWaluta;
    @Column(name="rozliczono")
    private double rozliczono;
    @Column(name="pozostalo")
    private double pozostalo;
    @Column(name="typStronaWiersza")
    //0-nowy, 1-nowatransakcja, 2- rozliczajacy, inne do wykorzystania
    //11-nowa transakcja Wn, 12 - nowa tansakcja Ma
    //21- platnosc Wn, 22- platnosc Ma
    //9- BO
    private int typStronaWiersza;
    @Column(name="nowatransakcja")
    private boolean nowatransakcja;
    @JoinColumn(name= "idkonto")
    @ManyToOne
    private Konto konto;
    @Column(name = "wnma")
    private String wnma;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rozliczajacy", fetch = FetchType.EAGER)
    private List<Transakcja> nowetransakcje;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "nowaTransakcja", fetch = FetchType.EAGER)
    private List<Transakcja> platnosci;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
      name="StronaWiersza_Cechazapisu",
      joinColumns={
          @JoinColumn(name = "id_StronaWiersza", referencedColumnName = "id"),
      },
      inverseJoinColumns={
          @JoinColumn(name = "nazwacechy", referencedColumnName = "nazwacechy"),
          @JoinColumn(name = "rodzajcechy", referencedColumnName = "rodzajcechy")
      })
    private List<Cechazapisu> cechazapisuLista;
    private String symbolWalutyBO;
    private double kursBO;
    private String opisBO;
    

   
    public StronaWiersza(Wiersz nowywiersz, String wnma) {
        this.nowetransakcje = new ArrayList<>();
        this.platnosci = new ArrayList<>();
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
    }
    
     public StronaWiersza(Wiersz nowywiersz, String wnma, double kwota) {
        this.nowetransakcje = new ArrayList<>();
        this.platnosci = new ArrayList<>();
        this.kwota = kwota;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
    }
     
     public StronaWiersza(Wiersz nowywiersz, String wnma, double kwota, Konto konto) {
        this.nowetransakcje = new ArrayList<>();
        this.platnosci = new ArrayList<>();
        this.kwota = kwota;
        this.kwotaPLN = kwota;
        this.kwotaWaluta = 0.0;
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
        this.konto = konto;
    }
    
     public StronaWiersza(WierszBO w, String wnma, String zapisy) {
         this.konto = w.getKonto();
         this.typStronaWiersza = 9;
         if (wnma.equals("Wn")) {
             this.wnma = "Wn";
             this.kwota = w.getKwotaWn();
             this.kwotaPLN = w.getKwotaWnPLN();
             this.kwotaWaluta = w.getKwotaWn();
         } else {
             this.wnma = "Ma";
             this.kwota = w.getKwotaMa();
             this.kwotaPLN = w.getKwotaMaPLN();
             this.kwotaWaluta = w.getKwotaMa();
         }
         this.wiersz = new Wiersz();
         this.wiersz.setIdwiersza(0);
         this.wiersz.setOpisWiersza(w.getWierszBOPK().getOpis());
         this.wiersz.setDokfk(new Dokfk("zapis z BO"));
       }
     
      public StronaWiersza(WierszBO w, String wnma) {
         this.konto = w.getKonto();
         this.typStronaWiersza = 9;
         this.symbolWalutyBO = w.getWaluta().getSymbolwaluty();
         if (wnma.equals("Wn")) {
             this.wnma = "Wn";
             this.kwota = w.getKwotaWn();
             this.kwotaWaluta = w.getKwotaWn();
             this.kwotaPLN = w.getKwotaWnPLN();
         } else {
             this.wnma = "Ma";
             this.kwota = w.getKwotaMa();
             this.kwotaWaluta = w.getKwotaMa();
             this.kwotaPLN = w.getKwotaMaPLN();
          }
             this.nowatransakcja = true;
             this.kursBO = w.getKurs();
             this.opisBO = w.getWierszBOPK().getOpis();

       }
    
    

    public StronaWiersza() {
        this.nowetransakcje = new ArrayList<>();
        this.platnosci = new ArrayList<>();
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.typStronaWiersza = 0;
    }

    public double getKursBO() {
        return kursBO;
    }

    public void setKursBO(double kursBO) {
        this.kursBO = kursBO;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getWnma() {
        return wnma;
    }

    public void setWnma(String wnma) {
        this.wnma = wnma;
    }

    public double getRozliczono() {
        this.rozliczono = 0.0;
        if (this.nowatransakcja) {
            for (Transakcja p : this.platnosci) {
                    this.rozliczono += p.getKwotatransakcji();
            }
            this.pozostalo = this.kwota - this.rozliczono;
        } else {
            for (Transakcja p : this.nowetransakcje) {
                this.rozliczono += p.getKwotatransakcji();
            }
            this.pozostalo = this.kwota - this.rozliczono;
        }
        return this.rozliczono;
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
         this.rozliczono = 0.0;
        if (this.nowatransakcja) {
            for (Transakcja p : this.platnosci) {
                this.rozliczono += p.getKwotatransakcji();
            }
            this.pozostalo = this.kwota - this.rozliczono;
        } else {
            for (Transakcja p : this.nowetransakcje) {
                this.rozliczono += p.getKwotatransakcji();
            }
            this.pozostalo = this.kwota - this.rozliczono;
        }
        return this.pozostalo;
    }

    public void setPozostalo(double pozostalo) {
        this.pozostalo = pozostalo;
    }

    public int getTypStronaWiersza() {
        return typStronaWiersza;
    }

    public void setTypStronaWiersza(int typStronaWiersza) {
        this.typStronaWiersza = typStronaWiersza;
    }


    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotaPLN() {
        return kwotaPLN;
    }

    public void setKwotaPLN(double kwotaPLN) {
        this.kwotaPLN = kwotaPLN;
    }

    public double getKwotaWaluta() {
        return kwotaWaluta;
    }

    public void setKwotaWaluta(double kwotaWaluta) {
        this.kwotaWaluta = kwotaWaluta;
    }

    public boolean isNowatransakcja() {
        return nowatransakcja;
    }

    public void setNowatransakcja(boolean nowatransakcja) {
        this.nowatransakcja = nowatransakcja;
    }
    

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public List<Cechazapisu> getCechazapisuLista() {
        return cechazapisuLista;
    }

    public void setCechazapisuLista(List<Cechazapisu> cechazapisuLista) {
        this.cechazapisuLista = cechazapisuLista;
    }

    public String getOpisBO() {
        return opisBO;
    }

    public void setOpisBO(String opisBO) {
        this.opisBO = opisBO;
    }
    
    

//    public void dodajTransakcjeNowe(Transakcja transakcja) {
//        if (this.nowetransakcje.contains(transakcja)) {
//            this.rozliczono = this.rozliczono - transakcja.getPoprzedniakwota() + transakcja.getKwotatransakcji();
//        } else {
//            this.rozliczono = this.rozliczono + transakcja.getKwotatransakcji();
//            this.nowetransakcje.add(transakcja);
//        }
//        this.pozostalo = this.kwota - this.rozliczono;
//    }
//    
//    public void dodajPlatnosci(Transakcja transakcja) {
//        if (this.platnosci.contains(transakcja)) {
//            this.rozliczono = this.rozliczono - transakcja.getPoprzedniakwota() + transakcja.getKwotatransakcji();
//        } else {
//            this.rozliczono = this.rozliczono + transakcja.getKwotatransakcji();
//            this.platnosci.add(transakcja);
//        }
//        this.pozostalo = this.kwota - this.rozliczono;
//    }

    public List<Transakcja> getNowetransakcje() {
        return nowetransakcje;
    }

    public void setNowetransakcje(List<Transakcja> nowetransakcje) {
        this.nowetransakcje = nowetransakcje;
    }

    public List<Transakcja> getPlatnosci() {
        return platnosci;
    }

    public void setPlatnosci(List<Transakcja> platnosci) {
        this.platnosci = platnosci;
    }

    public String getSymbolWalutyBO() {
        return symbolWalutyBO;
    }

    public void setSymbolWalutyBO(String symbolWalutyBO) {
        this.symbolWalutyBO = symbolWalutyBO;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.wiersz);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
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
        final StronaWiersza other = (StronaWiersza) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (this. id != null) {
            return "StronaWiersza{" + "id=" + id + ", wiersz=" + wiersz + ", kwota=" + kwota + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + typStronaWiersza + '}';
        } else {
            return "StronaWiersza{" + "id=null, wiersz= "+ wiersz + ", kwota=" + kwota + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + typStronaWiersza + '}';
        }
    }

    

    
}
