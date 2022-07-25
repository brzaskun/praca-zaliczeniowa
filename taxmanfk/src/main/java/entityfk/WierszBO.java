/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entity.Uz;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "WierszBO.findByLista", query = "SELECT w FROM WierszBO w WHERE w.konto.pelnynumer LIKE :grupakonta AND  w.podatnik = :podatnik AND w.rok = :rok AND w.mc = :mc"),
    @NamedQuery(name = "WierszBO.findByListaLikwidacja", query = "SELECT w FROM WierszBO w WHERE w.konto.pelnynumer LIKE :grupakonta AND  w.podatnik = :podatnik AND w.rok = :rok AND w.mc = :mc AND w.otwarcielikwidacji = true"),
    @NamedQuery(name = "WierszBO.findByListaRokMc", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok AND w.mc = :mc"),
    @NamedQuery(name = "WierszBO.findByDeletePodatnikRok", query = "DELETE FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok"),
    @NamedQuery(name = "WierszBO.findByDeletePodatnikRokMc", query = "DELETE FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok AND w.mc = :mc"),
    @NamedQuery(name = "WierszBO.findByPodatnikRok", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok"),
    @NamedQuery(name = "WierszBO.findById", query = "SELECT w FROM WierszBO w WHERE w.id = :id"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokRozrachunkowe", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok AND w.konto.zwyklerozrachszczegolne = 'rozrachunkowe'"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKonto", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok AND w.konto = :konto"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKontoWaluta", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.rok = :rok AND w.konto = :konto AND w.waluta.symbolwaluty = :waluta")
})
@Cacheable
public class WierszBO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "podid", referencedColumnName = "id")
    private Podatnik podatnik;
    @ManyToOne
    @JoinColumn(name = "KONTO_id", referencedColumnName = "id")
    private Konto konto;
    private String rok;
    private String mc;
    @Column(length = 1024)
    private String opis;
    private double kwotaWn;
    private double kwotaMa;
    private boolean rozrachunek;
    private Waluty waluta;
    private double kurs;
    private double kwotaWnPLN;
    private double kwotaMaPLN;
    @JoinColumn(name = "wprowadzil", referencedColumnName = "login")
    @ManyToOne
    private Uz wprowadzil;
    @Transient
    private Konto kontostare;
    //9 naniesiony
    @Column(name="nowy0edycja1usun2")
    private int nowy0edycja1usun2;
    @Column(name="otwarcielikwidacji")
    private boolean otwarcielikwidacji;
    @Column(name="roznicakursowastatystyczna")
    private boolean roznicakursowastatystyczna;
    @JoinColumn(name = "evatwpisfk", referencedColumnName = "id")
    private EVatwpisFK eVatwpisFK;
    @Column(name = "data_k", insertable=false, updatable=false, columnDefinition="timestamp default current_timestamp")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataK;
    @Column(name="roznicowy")
    private boolean roznicowy;
    

    public WierszBO() {
        
    }

    public WierszBO(Podatnik podatnik, String rok, Waluty waluta, String mc) {
        this.rok = rok;
        this.mc = mc;
        this.podatnik = podatnik;
        this.kwotaWn = 0.0;
        this.kwotaWnPLN = 0.0;
        this.kwotaMa = 0.0;
        this.kwotaMaPLN = 0.0;
        this.kurs = 0.0;
        this.waluta = waluta;
        this.rozrachunek = false;
        this.nowy0edycja1usun2 = 0;
    }
    

    public WierszBO(Podatnik podatnik, SaldoKonto p, String rok, String mc, Konto konto, Waluty waluta, Uz wprowadzil) {
        this.rok = rok;
        this.mc = mc;
        if (p.getOpisdlabo() != null) {
        }
        this.opis = p.getOpisdlabo() != null ? p.getOpisdlabo() : "zapis BO " + p.hashCode();
        this.podatnik = podatnik;
        this.konto = konto;
        if (p.getSaldoWn() > p.getSaldoMa()) {
            this.kwotaWn = Z.z(p.getSaldoWn() - p.getSaldoMa());
            this.kwotaWnPLN = Z.z(p.getSaldoWnPLN() - p.getSaldoMaPLN());
            this.kwotaMa = 0.0;
            this.kwotaMaPLN = 0.0;
        } else {
            this.kwotaWn = 0.0;
            this.kwotaWnPLN = 0.0;
            this.kwotaMa = Z.z(p.getSaldoMa() - p.getSaldoWn());
            this.kwotaMaPLN = Z.z(p.getSaldoMaPLN() - p.getSaldoWnPLN());
        }
        this.kurs = p.getKursdlaBO();
        this.waluta = waluta;
        this.rozrachunek = false;
        this.wprowadzil = wprowadzil;
        this.nowy0edycja1usun2 = 0;
        this.roznicakursowastatystyczna = p.isRoznicakursowastatystyczna();
    }
    
    public WierszBO(Podatnik podatnik, SaldoKonto p, String rok, String mc, Konto konto, Waluty waluta, Uz wprowadzil, double roznicaWn, double roznicaMa, double roznicaWnPLN, double roznicaMaPLN) {
        this.rok = rok;
        this.mc = mc;
        this.opis = p.getOpisdlabo() != null ? "wiersz różnicowy "+p.getOpisdlabo() : "wiersz różnicowy zapis BO " + p.hashCode();
        this.podatnik = podatnik;
        this.konto = konto;
        this.kwotaWn = Z.z(roznicaWn);
        this.kwotaWnPLN = Z.z(roznicaWnPLN);
        this.kwotaMa = Z.z(roznicaMa);
        this.kwotaMaPLN = Z.z(roznicaMaPLN);
        this.kurs =  kwotaWn != 0.0 ? Z.z4(kwotaWnPLN/kwotaWn) : 0.0;
        if (kwotaMa!=0.0) {
            this.kurs =  kwotaMa != 0.0 ? Z.z4(kwotaMaPLN/kwotaMa) : 0.0;
        }
        this.waluta = waluta;
        this.rozrachunek = false;
        this.wprowadzil = wprowadzil;
        this.nowy0edycja1usun2 = 0;
        this.roznicakursowastatystyczna = p.isRoznicakursowastatystyczna();
    }
    
      public WierszBO(Podatnik podatnik, String rok, String mc, Konto konto, double roznicawn, double roznicama, Waluty waluta, Uz wprowadzil) {
        this.rok = rok;
        this.mc = mc;
        this.opis = "wiersz rożnicowy BO " + konto.hashCode();
        this.podatnik = podatnik;
        this.konto = konto;
        if (roznicawn != 0.0) {
            this.kwotaWn = Z.z(roznicawn);
            this.kwotaWnPLN = Z.z(roznicawn);
            this.kwotaMa = 0.0;
            this.kwotaMaPLN = 0.0;
        } else if (roznicama != 0.0) {
            this.kwotaWn = 0.0;
            this.kwotaWnPLN = 0.0;
            this.kwotaMa = Z.z(roznicama);
            this.kwotaMaPLN = Z.z(roznicama);
        }
        this.kurs = 0.0;
        this.waluta = waluta;
        this.rozrachunek = false;
        this.wprowadzil = wprowadzil;
        this.nowy0edycja1usun2 = 0;
        this.roznicowy = true;
    }

    public WierszBO(WierszBO t) {
        this.podatnik = t.podatnik;
        this.konto = t.konto;
        this.rok = t.rok;
        this.mc = t.mc;
        this.opis = t.opis;
        this.kwotaWn = t.kwotaWn!=0.0?-t.kwotaWn:0.0;
        this.kwotaMa = t.kwotaMa!=0.0?-t.kwotaMa:0.0;
        this.rozrachunek = t.rozrachunek;
        this.waluta = t.waluta;
        this.kurs = t.kurs;
        this.kwotaWnPLN = t.kwotaWnPLN!=0.0?-t.kwotaWnPLN:0.0;
        this.kwotaMaPLN = t.kwotaMaPLN!=0.0?-t.kwotaMaPLN:0.0;
        this.wprowadzil = t.wprowadzil;
        this.kontostare = t.kontostare;
        this.otwarcielikwidacji = t.otwarcielikwidacji;
        this.dataK = t.dataK;
        this.roznicowy = t.roznicowy;
    }

    
   
    @Override
    public String toString() {
        if (konto != null ) {
            return "WierszBO{" + ", konto=" + konto.getPelnynumer() + ", opis=" + opis + ", kwotaWn=" + kwotaWn + ", kwotaWnPLN=" + kwotaWnPLN + ", kwotaMa=" + kwotaMa + ", kwotaMaPLN=" + kwotaMaPLN + '}';
        } else {
            return "WierszBO{" + ", konto null, opis=" + opis + ", kwotaWn=" + kwotaWn + ", kwotaMa=" + kwotaMa + '}';
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.podatnik);
        hash = 83 * hash + Objects.hashCode(this.konto);
        hash = 83 * hash + Objects.hashCode(this.rok);
        hash = 83 * hash + Objects.hashCode(this.mc);
        hash = 83 * hash + Objects.hashCode(this.opis);
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
        final WierszBO other = (WierszBO) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.podatnik, other.podatnik)) {
            return false;
        }
        if (!Objects.equals(this.konto, other.konto)) {
            return false;
        }
        return true;
    }

   
    public double getRoznicaSald() {
        double zwrot = 0.0;
        Konto nowe = this.konto;
        if (this.kontostare != null) {
            double roznica1 = Z.z(nowe.getBoWn()-this.kontostare.getSaldoWnksiegi());
            double roznica2 = Z.z(nowe.getBoMa()-this.kontostare.getSaldoMaksiegi());
            zwrot = roznica1+roznica2;
        }
        return zwrot;
    }
    
    public String getStylwiersza() {
        String zwrot = "color: black; text-align: center;";
        switch (this.nowy0edycja1usun2) {
             case 0 : 
                zwrot = "color: green; text-align: center;";
                break;
            case 1 : 
                zwrot = "color: orange; text-align: center;";
                break;
            case 2 : 
                zwrot = "color: red; text-align: center;";
                break;
            case 3 : 
                zwrot = "color: black; text-align: center;";
                break;
        }
        return zwrot;
    }
   
    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    public int getNowy0edycja1usun2Int() {
        return nowy0edycja1usun2;
    }

    public String getNowy0edycja1usun2() {
        String zwrot = "nowy";
        switch (nowy0edycja1usun2) {
            case 0 : 
                zwrot = "nowy";
                break;
            case 1 : 
                zwrot = "edycja";
                break;
            case 2 : 
                zwrot = "usuń";
                break;
            case 3 : 
                zwrot = "księgi";
                break;
        }
        return zwrot;
    }
    

    public void setNowy0edycja1usun2(int nowy0edycja1usun2) {
        this.nowy0edycja1usun2 = nowy0edycja1usun2;
    }

    public double getKwotaWn() {
        return Z.z(kwotaWn);
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = Z.z(kwotaWn);
    }

    public boolean isRoznicakursowastatystyczna() {
        return roznicakursowastatystyczna;
    }

    public void setRoznicakursowastatystyczna(boolean roznicakursowastatystyczna) {
        this.roznicakursowastatystyczna = roznicakursowastatystyczna;
    }

    public double getKwotaMa() {
        return Z.z(kwotaMa);
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = Z.z(kwotaMa);
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public boolean isRozrachunek() {
        return rozrachunek;
    }

    public void setRozrachunek(boolean rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public Waluty getWaluta() {
        return waluta;
    }

    public void setWaluta(Waluty waluta) {
        this.waluta = waluta;
    }

    public double getKurs() {
        return kurs;
    }

    public void setKurs(double kurs) {
        this.kurs = kurs;
    }

    public double getKwotaWnPLN() {
        return Z.z(kwotaWnPLN);
    }

    public void setKwotaWnPLN(double kwotaWnPLN) {
        this.kwotaWnPLN = Z.z(kwotaWnPLN);
    }

    public double getKwotaMaPLN() {
        return Z.z(kwotaMaPLN);
    }

    public void setKwotaMaPLN(double kwotaMaPLN) {
        this.kwotaMaPLN = Z.z(kwotaMaPLN);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

    public boolean isOtwarcielikwidacji() {
        return otwarcielikwidacji;
    }

    public void setOtwarcielikwidacji(boolean otwarcielikwidacji) {
        this.otwarcielikwidacji = otwarcielikwidacji;
    }

    public Konto getKontostare() {
        return kontostare;
    }

    public void setKontostare(Konto kontostare) {
        this.kontostare = kontostare;
    }

    public EVatwpisFK geteVatwpisFK() {
        return eVatwpisFK;
    }

    public void seteVatwpisFK(EVatwpisFK eVatwpisFK) {
        this.eVatwpisFK = eVatwpisFK;
    }

    public boolean isRoznicowy() {
        return roznicowy;
    }

    public void setRoznicowy(boolean roznicowy) {
        this.roznicowy = roznicowy;
    }

    public Date getDataK() {
        return dataK;
    }

    public void setDataK(Date dataK) {
        this.dataK = dataK;
    }
    
    

    public double getKwota() {
        double zwrot = 0.0;
        if (this.kwotaWn != 0.0) {
            zwrot = this.kwotaWn;
        } else {
            zwrot = this.kwotaMa;
        }
        return Z.z(zwrot);
    }

    
}
