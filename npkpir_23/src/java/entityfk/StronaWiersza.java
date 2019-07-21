/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddable.Mce;
import embeddablefk.ListaSum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@NamedEntityGraph(name = "graph.Order.items", 
      attributeNodes = @NamedAttributeNode("items"))
@Table(name = "stronawiersza", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"idwiersza", "strona_key"})
})

@NamedQueries({
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wnma = :wnma AND t.typStronaWiersza = '1'"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKonto", query = "SELECT t FROM StronaWiersza t  WHERE t.konto = :konto AND t.wnma = :wnma AND t.typStronaWiersza = '1'"),
    @NamedQuery(name = "StronaWiersza.findById", query = "SELECT t FROM StronaWiersza t WHERE t.id = :id"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoOnly", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWalutaKorekta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wnma = :wnma AND t.kwota < 0 AND t.typStronaWiersza = '1'"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoKorekta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wnma = :wnma AND t.kwota < 0 AND t.typStronaWiersza = '1'"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoWalutaBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.symbolWalutyBO = :symbolwaluty AND t.wnma = :wnma AND t.typStronaWiersza = '9'"),
    @NamedQuery(name = "StronaWiersza.findByStronaWierszaKontoBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wnma = :wnma AND t.typStronaWiersza = '9'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoMacierzysteRokWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto.kontomacierzyste = :konto AND t.konto.nazwapelna = :nazwapelna AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoMacierzysteRok", query = "SELECT t FROM StronaWiersza t WHERE t.konto.kontomacierzyste = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoSyntetyczneRok", query = "SELECT t FROM StronaWiersza t WHERE (t.konto.syntetycznenumer = :kontonumer OR t.konto = :konto) AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto.kontomacierzyste = :konto AND t.konto.nazwapelna = :nazwapelna AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoBOWaluta", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.symbolWalutyBO = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoBOWalutaWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutaWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokMcWalutaWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWszystkieNT", query = "SELECT t  FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.nowatransakcja = '1'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.wiersz.tabelanbp.waluta.symbolwaluty = :wybranaWalutaDlaKonta AND t.nowatransakcja = '1'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWszystkieR", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.nowatransakcja = '0'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.wiersz.tabelanbp.waluta.symbolwaluty = :wybranaWalutaDlaKonta AND t.nowatransakcja = '0'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto.pelnynumer LIKE :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.miesiac >= :mcod AND t.wiersz.dokfk.miesiac <= :mcdo AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokWalutyWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza != 9"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokMcVAT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.vatM = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikKontoRokVAT", query = "SELECT t FROM StronaWiersza t WHERE t.konto = :konto AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaWynik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWynik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWynikBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza = '9'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikWynikCecha", query = "SELECT t FROM StronaWiersza  t JOIN t.wiersz.dokfk s WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(s.cechadokumentuLista) > 0)"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikWynikCechaRokMc", query = "SELECT t FROM StronaWiersza  t JOIN t.wiersz.dokfk s WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND (SIZE(t.cechazapisuLista) > 0 OR SIZE(s.cechadokumentuLista) > 0) AND s.rok = :rok AND s.miesiac = :mc"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokMcWynik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokMcWynikSlownik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.miesiac = :mc AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.konto.slownikowe = '1'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWynikSlownik", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'wynikowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.konto.slownikowe = '1'"),
    @NamedQuery(name = "StronaWiersza.findStronaByPodatnikRokKontoDist", query = "SELECT DISTINCT(t.konto) FROM StronaWiersza t JOIN t.wiersz.dokfk s WHERE s.rok = :rok AND s.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRok", query = "SELECT t FROM StronaWiersza t JOIN t.wiersz.dokfk s WHERE s.rok = :rok AND s.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaBilans", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokBilans", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj"),
    @NamedQuery(name = "StronaWiersza.findByKontoDistinctPodatnikRokBilans", query = "SELECT DISTINCT t.konto FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.konto.zwyklerozrachszczegolne = 'rozrachunkowe'"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokWalutaBilansBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.tabelanbp.waluta.symbolwaluty = :symbolwaluty AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza = 9"),
    @NamedQuery(name = "StronaWiersza.findByPodatnikRokBilansBO", query = "SELECT t FROM StronaWiersza t WHERE t.konto.bilansowewynikowe = 'bilansowe' AND t.wiersz.dokfk.rok = :rok AND t.wiersz.dokfk.podatnikObj = :podatnikObj AND t.typStronaWiersza = 9")
})
@Cacheable
public class StronaWiersza implements Serializable {
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
    @Column(name = "rozliczono")
    private double rozliczono;
    @Transient
    private double rozliczeniebiezace;
    @Column(name = "pozostalo")
    private double pozostalo;
    @Column(name = "typStronaWiersza")
    //0-nowy, 1-nowatransakcja, 2- rozliczajacy, inne do wykorzystania
    //11-nowa transakcja Wn, 12 - nowa tansakcja Ma
    //21- platnosc Wn, 22- platnosc Ma
    //9- BO
    private int typStronaWiersza;
    @Column(name = "nowatransakcja")
    private boolean nowatransakcja;
    @JoinColumn(name = "idkonto", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Konto konto;
    @Column(name = "wnma")
    private String wnma;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "rozliczajacy", fetch = FetchType.LAZY)
    private List<Transakcja> nowetransakcje;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "nowaTransakcja", fetch = FetchType.LAZY)
    private List<Transakcja> platnosci;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "StronaWiersza_Cechazapisu",
            joinColumns = {
                @JoinColumn(name = "id_StronaWiersza", referencedColumnName = "id"),},
            inverseJoinColumns = {
                @JoinColumn(name = "idcecha", referencedColumnName = "id"),
            })
    private List<Cechazapisu> cechazapisuLista;
    private String symbolWalutyBO;
    private double kursBO;
    private String opisBO;
    @JoinColumn(name = "wierszbo_id", referencedColumnName = "id")
    @ManyToOne
    private WierszBO wierszbo;
    @Transient
    private Set<String> opis;
    @Transient
    private String kontr;
    
   

    public StronaWiersza(Wiersz nowywiersz, String wnma) {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
    }

    public StronaWiersza(Wiersz nowywiersz, String wnma, double kwota) {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
        this.kwota = Z.z(kwota);
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = Z.z(kwota);
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
    }

    public StronaWiersza(Wiersz nowywiersz, String wnma, double kwota, Konto konto) {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
        this.kwota = Z.z(kwota);
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = Z.z(kwota);
        this.wiersz = nowywiersz;
        this.wnma = wnma;
        this.typStronaWiersza = 0;
        this.konto = konto;
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
    }

    public StronaWiersza(WierszBO w, String wnma, String zapisy) {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
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
        this.wiersz.setOpisWiersza(w.getOpis() != null ? w.getOpis() : "zapis BO");
        this.wiersz.setDokfk(new Dokfk("zapis z BO", w.getRok()));
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
        this.symbolWalutyBO = w.getWaluta().getSymbolwaluty();
    }

    public StronaWiersza(WierszBO w, String wnma) {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
        this.konto = w.getKonto();
        this.typStronaWiersza = 9;
        this.symbolWalutyBO = w.getWaluta().getSymbolwaluty();
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
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
        this.opisBO = w.getOpis();

    }

    public StronaWiersza() {
        this.nowetransakcje = Collections.synchronizedList(new ArrayList<>());
        this.platnosci = Collections.synchronizedList(new ArrayList<>());
        this.kwota = 0.0;
        this.kwotaPLN = 0.0;
        this.kwotaWaluta = 0.0;
        this.typStronaWiersza = 0;
        this.cechazapisuLista = Collections.synchronizedList(new ArrayList<>());
    }

    public StronaWiersza(List<ListaSum> listasum, int saldo1sWn2sMa3, boolean nierenderujkolumnnywalut) {
        if (listasum != null) {
            ListaSum l = listasum.get(0);
            double saldown = nierenderujkolumnnywalut ? l.getSaldoWnPLN() : l.getSaldoWn();
            double saldoma = nierenderujkolumnnywalut ? l.getSaldoMaPLN() : l.getSaldoMa();
            double sumawn = nierenderujkolumnnywalut ? l.getSumaWnPLN() : l.getSumaWn();
            double sumama = nierenderujkolumnnywalut ? l.getSumaMaPLN() : l.getSumaMa();
            if (saldo1sWn2sMa3 == 1) {
                this.opisBO = nierenderujkolumnnywalut ? "saldo koncowe w pln": "saldo koncowe w walucie";
                if (saldown > 0.0) {
                    this.setWnma("Wn");
                    this.kwota = Z.z(saldown);
                } else {
                    this.setWnma("Ma");
                    this.kwota = Z.z(saldoma);
                }
            } else {
                if (saldo1sWn2sMa3 == 2) {
                    this.opisBO = nierenderujkolumnnywalut ? "suma strony Wn w pln" : "suma strony Wn w walucie";
                    this.setWnma("Wn");
                    this.kwota = Z.z(sumawn);
                }
                if (saldo1sWn2sMa3 == 3) {
                    this.opisBO = nierenderujkolumnnywalut ? "suma strony Ma w pln" : "suma strony Ma w walucie";
                    this.setWnma("Ma");
                    this.kwota = Z.z(sumama);
                }
            }
        }
    }


    
    public double getKursBO() {
        return kursBO;
    }

    public void setKursBO(double kursBO) {
        this.kursBO = kursBO;
    }

    public Set<String> getOpis() {
        return opis;
    }

    public void setOpis(Set<String> opis) {
        this.opis = opis;
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

    public String getKontr() {
        return kontr;
    }

    public void setKontr(String kontr) {
        this.kontr = kontr;
    }

    public double getRozliczeniebiezace() {
        return rozliczeniebiezace;
    }

    public void setRozliczeniebiezace(double rozliczeniebiezace) {
        this.rozliczeniebiezace = rozliczeniebiezace;
    }
    

    public double getRozliczonoViewInneDok(List<Transakcja> biezacetransakcje) {
        this.rozliczono = 0.0;
        if (biezacetransakcje!=null) {
            if (this.nowatransakcja) {
                for (Transakcja p : this.platnosci) {
                    if (!biezacetransakcje.contains(p)) {
                        if (p.getKwotawwalucierachunku() != 0.0) {
                            this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                        } else {
                            this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                        }
                    }
                }
                this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
            } else {
                  for (Transakcja p : this.nowetransakcje) {
                      if (!biezacetransakcje.contains(p)) {
                        this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                      }
                    }
                  //bo jak tak jest to jak platnosc  jest w PLN a rachunek w NOK to pokazuje kwote rozliczona w nokach
    //            for (Transakcja p : this.nowetransakcje) {
    //                if (p.getKwotawwalucierachunku() > 0.0) {
    //                    this.rozliczono += p.getKwotawwalucierachunku();
    //                } else {
    //                    this.rozliczono += p.getKwotatransakcji();
    //                }
    //            }
                this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
            }
        }
        return Z.z(this.rozliczono);
    }
    
    public double getRozliczonoViewTenDok() {
        this.rozliczono = 0.0;
        if (this.nowatransakcja) {
            for (Transakcja p : this.platnosci) {
                if (p.getId()==null) {
                    if (p.getKwotawwalucierachunku() != 0.0) {
                        this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                    } else {
                        this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                    }
                }
            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        } else {
              for (Transakcja p : this.nowetransakcje) {
                  if (p.getId()==null) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                  }
                }
              //bo jak tak jest to jak platnosc  jest w PLN a rachunek w NOK to pokazuje kwote rozliczona w nokach
//            for (Transakcja p : this.nowetransakcje) {
//                if (p.getKwotawwalucierachunku() > 0.0) {
//                    this.rozliczono += p.getKwotawwalucierachunku();
//                } else {
//                    this.rozliczono += p.getKwotatransakcji();
//                }
//            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        }
        return Z.z(this.rozliczono);
    }
  

    

    public double getRozliczono() {
        this.rozliczono = 0.0;
        if (this.nowatransakcja) {
            for (Transakcja p : this.platnosci) {
                if (p.getKwotawwalucierachunku() != 0.0) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                } else {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                }
            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        } else {
              for (Transakcja p : this.nowetransakcje) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                }
              //bo jak tak jest to jak platnosc  jest w PLN a rachunek w NOK to pokazuje kwote rozliczona w nokach
//            for (Transakcja p : this.nowetransakcje) {
//                if (p.getKwotawwalucierachunku() > 0.0) {
//                    this.rozliczono += p.getKwotawwalucierachunku();
//                } else {
//                    this.rozliczono += p.getKwotatransakcji();
//                }
//            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        }
        return Z.z(this.rozliczono);
    }
    
    //dzieki temu mozna bez problemu zmieniac wartosci rozliczen w walucie, nie trzeba ich wczesnie zerowac
    public double getRozliczono(Transakcja loop) {
        this.rozliczono = 0.0;
        for (Transakcja p : this.platnosci) {
            if (p != loop) {
                if (p.getKwotawwalucierachunku() != 0.0) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                } else {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                }
            }
        }
        this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        return Z.z(this.rozliczono);
    }
   

    public double getRozliczono(WpisView wpisView) {
        this.rozliczono = 0.0;
        int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
        int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
        if (this.nowatransakcja) {
            for (Iterator<Transakcja> it = this.platnosci.iterator(); it.hasNext();) {
                Transakcja pr = it.next();
                int mc = Mce.getMiesiacToNumber().get(pr.getDatarozrachunku().split("-")[1]);
                if (mc < granicaDolna || mc > granicaGorna) {
                    it.remove();
                }
            }
            for (Transakcja p : this.platnosci) {
                if (p.getKwotawwalucierachunku() != 0.0) {
                    this.rozliczono += p.getKwotawwalucierachunku();
                } else {
                    this.rozliczono += p.getKwotatransakcji();
                }
            }
            this.pozostalo = this.getKwotaR() - this.rozliczono;
        } else {
            for (Iterator<Transakcja> it = this.nowetransakcje.iterator(); it.hasNext();) {
                Transakcja pr = it.next();
                int mc = Mce.getMiesiacToNumber().get(pr.getDatarozrachunku().split("-")[1]);
                if (mc < granicaDolna || mc > granicaGorna) {
                    it.remove();
                }
            }
            for (Transakcja p : this.nowetransakcje) {
                    this.rozliczono += p.getKwotatransakcji();
            }
            //bo jak tak jest to jak platnosc  jest w PLN a rachunek w NOK to pokazuje kwote rozliczona w nokach
//            for (Transakcja p : this.nowetransakcje) {
//                if (p.getKwotawwalucierachunku() > 0.0) {
//                    this.rozliczono += p.getKwotawwalucierachunku();
//                } else {
//                    this.rozliczono += p.getKwotatransakcji();
//                }
//            }
            this.pozostalo = this.getKwotaR() - this.rozliczono;
        }
        return Z.z(this.rozliczono);
    }

    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }

    public double getPozostalo() {
        this.rozliczono = 0.0;
        if (this.platnosci != null) {
            for (Transakcja p : this.platnosci) {
                if (p.getKwotawwalucierachunku() != 0.0) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                } else {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                }
            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        }
        if (this.nowetransakcje != null) {
            for (Transakcja p : this.nowetransakcje) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        }
        return Z.z(this.pozostalo);
    }

    public double getPozostalo(WpisView wpisView) {
        this.rozliczono = 0.0;
        int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
        int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
        if (this.nowatransakcja) {
            for (Iterator<Transakcja> it = this.platnosci.iterator(); it.hasNext();) {
                Transakcja pr = it.next();
                int mc = Mce.getMiesiacToNumber().get(pr.getDatarozrachunku().split("-")[1]);
                if (mc < granicaDolna || mc > granicaGorna) {
                    it.remove();
                }
            }
            for (Transakcja p : this.platnosci) {
                if (p.getKwotawwalucierachunku() != 0.0) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotawwalucierachunku());
                } else {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
                }
            }
            this.pozostalo = Z.z(this.getKwotaR() - this.rozliczono);
        } else {
            for (Iterator<Transakcja> it = this.nowetransakcje.iterator(); it.hasNext();) {
                Transakcja pr = it.next();
                int mc = Mce.getMiesiacToNumber().get(pr.getDatarozrachunku().split("-")[1]);
                if (mc < granicaDolna || mc > granicaGorna) {
                    it.remove();
                }
            }
            for (Transakcja p : this.nowetransakcje) {
                    this.rozliczono = Z.z(this.rozliczono+p.getKwotatransakcji());
            }
            this.pozostalo = this.getKwotaR() - this.rozliczono;
        }
        return Z.z(this.pozostalo);
    }

    public double getPozostaloPLN() {
        double kwotaprzeliczenia = this.pozostalo;
        if (this.getWiersz().getTabelanbp() != null) {
            kwotaprzeliczenia = Z.z(this.pozostalo * this.getWiersz().getTabelanbp().getKurssredniPrzelicznik());
        } else if (this.getKursBO() != 0.0) {
            kwotaprzeliczenia = Z.z(this.pozostalo * kursBO);
        }
        return Z.z(kwotaprzeliczenia);
    }

    public String getNazwaWaluty() {
        if (this.getWiersz().getTabelanbp() != null) {
            return this.wiersz.getTabelanbp().getWaluta().getSymbolwaluty();
        } else {
            return this.symbolWalutyBO;
        }
    }

    public double getKursWalutyBOSW() {
        double zwrot = 1.0;
        if (this.getWiersz().getTabelanbp() != null) {
            zwrot = this.wiersz.getTabelanbp().getKurssredni();
        } else if (this.kursBO != 0.0) {
            zwrot = this.kursBO;
        }
        return zwrot;
    }
    
    public String getStylopisrozrachunki() {
        String zwrot = null;
        if (this.isNowatransakcja()) {
            zwrot = "color: darkred";
        }
        return zwrot;
    }
    
    public String getOpisSW() {
        String zwrot = this.wiersz.getOpisWiersza();
        if (this.opisBO != null) {
            zwrot = this.opisBO;
        } else if (!this.wiersz.getDokfk().getOpisdokfk().equals(this.wiersz.getOpisWiersza())) {
            zwrot = zwrot + "/" + this.wiersz.getDokfk().getOpisdokfk();
        }
        return zwrot;
    }
    
    public String getKontrahent() {
        return this.getDokfk().getKontr().getNpelna();
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

    public double getKwotaR() {
        return Math.abs(kwota);
    }

    public void setKwota(double kwota) {
        this.kwota = Z.z(kwota);
    }

    public double getKwotaPLN() {
        return kwotaPLN;
    }

    public double getKwotaPLNR() {
        return Math.abs(kwotaPLN);
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

    public Dokfk getDokfk() {
        return wiersz.getDokfk();
    }

   
    public List<Cechazapisu> getListaCechazapisu() {
        return wiersz.getDokfk().getCechadokumentuLista();
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

    public WierszBO getWierszbo() {
        return wierszbo;
    }

    public void setWierszbo(WierszBO wierszbo) {
        this.wierszbo = wierszbo;
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

    public String getSymbolWaluty() {
        return this.wiersz.getTabelanbp().getWaluta().getSymbolwaluty();
    }

    public String getSymbolWalutBOiSW() {
        String symbol = "PLN";
        if (this.wiersz.getTabelanbp() != null) {
            symbol = this.wiersz.getTabelanbp().getWaluta().getSymbolwaluty();
        } else if (this.symbolWalutyBO != null) {
            symbol = this.symbolWalutyBO;
        }
        return symbol;
    }
    
    
    public String getSymbolInnychWalutBOiSW() {
        String symbol = "";
        if (this.wiersz.getTabelanbp() != null) {
            symbol = this.wiersz.getTabelanbp().getWaluta().getSymbolwaluty();
        } else if (this.symbolWalutyBO != null) {
            symbol = this.symbolWalutyBO;
        }
        if (symbol.equals("PLN")) {
            symbol = "";
        }
        return symbol;
    }
    
    public String getSkrotSymbolWalutBOiSW() {
        String symbol = "PLN";
        if (this.wiersz.getTabelanbp() != null) {
            symbol = this.wiersz.getTabelanbp().getWaluta().getSkrotsymbolu();
        } else if (this.symbolWalutyBO != null) {
            symbol = this.symbolWalutyBO;
        }
        return symbol;
    }
    
   
    public StronaWiersza getDrugaStrona() {
        Wiersz w = this.getWiersz();
        if (this.wnma.equals("Wn")) {
            return w.getStronaMa();
        } else {
            return w.getStronaWn();
        }
    }

    public Konto getKontoPrzeciwstawne() {
        Wiersz w = this.getWiersz();
        Konto t = null;
        if (this.wnma.equals("Wn")) {
            if (w.getStronaMa() != null) {
                t = w.getStronaMa().getKonto();
            }
        } else if (w.getStronaWn() != null) {
            t = w.getStronaWn().getKonto();
        }
        return t;
    }

    public boolean getToNieJestRRK() {
        boolean zwrot = true;
        if (this.wiersz != null && this.wiersz.getDokfk() != null && this.wiersz.getDokfk().getRodzajedok() != null) {
            if (this.wiersz.getDokfk().getRodzajedok().getSkrot().equals("RRK")) {
                zwrot = false;
            }
        }
        return zwrot;
    }

    public boolean isWn() {
        return this.wnma.equals("Wn");
    }
    
    public String getRokMc() {
        return this.getDokfk().getMcRok();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.wiersz);
        hash = 83 * hash + Objects.hashCode(this.wnma);
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
        final StronaWiersza other = (StronaWiersza) obj;
        if (!Objects.equals(this.wnma, other.wnma)) {
            return false;
        }
        if (!Objects.equals(this.wiersz, other.wiersz)) {
            return false;
        }
        return true;
    }
    
        
    
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 71 * hash + Objects.hashCode(this.id);
//        hash = 71 * hash + Objects.hashCode(this.wiersz);
//        hash = 71 * hash + (int) (Double.doubleToLongBits(this.kwota) ^ (Double.doubleToLongBits(this.kwota) >>> 32));
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final StronaWiersza other = (StronaWiersza) obj;
//        if (!Objects.equals(this.id, other.id)) {
//            return false;
//        }
//        if (!Objects.equals(this.wiersz, other.wiersz)) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.kwota) != Double.doubleToLongBits(other.kwota)) {
//            return false;
//        }
//        return true;
//    }
//    
    
   

    @Override
    public String toString() {
        if (this.id != null) {
            return "SW" + "id=" + id + ", konto=" + konto.getPelnynumer() + "," + " "+this.getRokMc()+" "+ wnma + ", wiersz=" + wiersz.getIdporzadkowy() + ", kwota=" + kwota+ ", kwotapln=" + kwotaPLN + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo+ "wal: "+ this.getSymbolWalutBOiSW() + ", nowatransakcja=" + typStronaWiersza + '}';
        } else if (this.id == null && this.konto != null) {
            return "SW" + "id=null," + wnma + ", konto= " + konto.getPelnynumer() + ", wiersz= " + wiersz.getIdporzadkowy() + ", kwota=" + kwota + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + typStronaWiersza + '}';
        } else {
            return "SW" + "id=null," + wnma + ", wiersz= " + wiersz.getIdporzadkowy() + ", kwota=" + kwota + ", rozliczono=" + rozliczono + ", pozostalo=" + pozostalo + ", nowatransakcja=" + typStronaWiersza + '}';
        }
    }
    
    //do druku i dla podsumowania
    public String getDataOperacji() {
        String zwrot = "";
        if (this.wiersz != null) {
            zwrot = this.wiersz.getDokfk().getDataoperacji();
        }
        return zwrot;
    }
    
    //do druku i dla podsumowania
    public String getDataDokumentuShort() {
        String zwrot = "";
        if (this.wiersz.getDokfk() != null) {
            zwrot = this.wiersz.getDokfk().getDatadokumentu().substring(2,10);
        }
        return zwrot;
    }
    
    public String getDokfkS() {
        String zwrot = "";
        if (this.wiersz != null) {
            zwrot = this.wiersz.getDokfkS();
        }
        return zwrot;
    }
    
    public int getIdporzadkowy() {
        int zwrot = 0;
        if (this.wiersz != null) {
            zwrot = this.wiersz.getIdporzadkowy();
        }
        return zwrot;
    }
     public String getNumerwlasnydokfk() {
        String zwrot = "";
        if (this.wiersz != null) {
            zwrot = this.wiersz.getDokfk().getNumerwlasnydokfk();
        }
        return zwrot;
    }
     public String getOpisWiersza(int l) {
        String zwrot = "";
        if (this.wiersz != null) {
            zwrot = this.wiersz.getOpisWiersza(l);
        } else if (opisBO != null) {
            zwrot = opisBO;
        }
        return zwrot;
    }
  
    public String getKontoPrzeciwstawneNumer() {
        String zwrot = "";
        if (this.wiersz != null) {
            Wiersz w = this.getWiersz();
            if (this.wnma.equals("Wn")) {
                if (w.getStronaMa() != null) {
                    zwrot = w.getStronaMa().getKonto().getPelnynumer();
                }
            } else if (w.getStronaWn() != null) {
                zwrot = w.getStronaWn().getKonto().getPelnynumer();
            }
        }
        return zwrot;
    }
    
    public String getSymbolWalutPrint() {
        String zwrot = "";
        if (this.wiersz != null) {
            zwrot = getSymbolWalutBOiSW();
        }
        return zwrot;
    }
    
    public boolean getSaRozrachunki (){
        boolean zwrot = false;
        if (this.nowetransakcje != null && !this.nowetransakcje.isEmpty()) {
            zwrot = true;
        }
        if (this.platnosci != null && !this.platnosci.isEmpty()) {
            zwrot = true;
        }
        return zwrot;
    }
    
    public String getDataWiersza() {
        String zwrot = this.getDataOperacji();
        if (this.wiersz.getDataWalutyWiersza() != null) {
            zwrot = this.wiersz.getDataWalutyWiersza();
        }
        return zwrot;
    }
    
    public String getDataWierszaPelna() {
        String zwrot = this.getDataOperacji();
        if (this.wiersz.getDataWalutyWiersza() != null) {
            zwrot = this.getDokfk().getRok()+"-"+this.getDokfk().getMiesiac()+"-"+this.wiersz.getDataWalutyWiersza();
        }
        return zwrot;
    }

    public boolean isRoznicaKursowa() {
        boolean zwrot = false;
        if (this.getKontoPrzeciwstawne() !=null && this.getKontoPrzeciwstawne().getPelnynumer().equals("755")) {
            zwrot = true;
        }
        return zwrot;
    }
    
    public String getKontrahetZapisy(String podatniknip) {
        String zwrot = this.getKontrahent();
        if (this.getDokfk().getKontr().getNip()!=null) {
            zwrot = this.getDokfk().getKontr().getNip().equals(podatniknip) ? "" : this.getKontrahent();
        }
        if (this.wiersz.geteVatwpisFK() != null) {
            zwrot = this.wiersz.geteVatwpisFK().getKontr().getNpelna();
        }
        return zwrot;
    }
    
    
}

