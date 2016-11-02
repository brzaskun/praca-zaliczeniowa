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
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "WierszBO.findByLista", query = "SELECT w FROM WierszBO w WHERE w.konto.pelnynumer LIKE :grupakonta AND  w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.wierszBOPK.mc = :mc"),
    @NamedQuery(name = "WierszBO.findByDeletePodatnikRok", query = "DELETE FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok"),
    @NamedQuery(name = "WierszBO.findByDeletePodatnikRokMc", query = "DELETE FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.wierszBOPK.mc = :mc"),
    @NamedQuery(name = "WierszBO.findByPodatnikRok", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokRozrachunkowe", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto.zwyklerozrachszczegolne = 'rozrachunkowe'"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKonto", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto = :konto"),
    @NamedQuery(name = "WierszBO.findByPodatnikRokKontoWaluta", query = "SELECT w FROM WierszBO w WHERE w.podatnik = :podatnik AND w.wierszBOPK.rok = :rok AND w.konto = :konto AND w.waluta.symbolwaluty = :waluta")
})
@Cacheable
public class WierszBO implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private WierszBOPK wierszBOPK;
    @MapsId("nippodatnika")
    private Podatnik podatnik;
    @MapsId("idkonta")
    private Konto konto;
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

    public WierszBO() {
        this.wierszBOPK = new WierszBOPK();
    }

    public WierszBO(Podatnik podatnik, String rok, Waluty waluta, String mc) {
        this.wierszBOPK = new WierszBOPK();
        this.wierszBOPK.setRok(rok);
        this.wierszBOPK.setMc(mc);
        this.podatnik = podatnik;
        this.kwotaWn = 0.0;
        this.kwotaWnPLN = 0.0;
        this.kwotaMa = 0.0;
        this.kwotaMaPLN = 0.0;
        this.kurs = 0.0;
        this.waluta = waluta;
        this.rozrachunek = false;
    }

    public WierszBO(Podatnik podatnik, SaldoKonto p, String rok, Konto konto, Waluty waluta, Uz wprowadzil) {
        this.wierszBOPK = new WierszBOPK();
        this.wierszBOPK.setRok(rok);
        if (p.getOpisdlabo() != null) {
            System.out.println("");
        }
        System.out.println("opis: "+p.getOpisdlabo());
        this.wierszBOPK.setOpis(p.getOpisdlabo() != null ? p.getOpisdlabo() : "zapis BO " + p.hashCode());
        this.podatnik = podatnik;
        this.konto = konto;
        if (p.getKonto().getPelnynumer().equals("202-2-13")) {
            System.out.println("s");
        }
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
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.wierszBOPK);
        hash = 53 * hash + Objects.hashCode(this.podatnik);
        hash = 53 * hash + Objects.hashCode(this.konto);
        return hash;
    }

    @Override
    public String toString() {
        return "WierszBO{" + ", konto=" + konto.getPelnynumer() + ", opis=" + wierszBOPK.getOpis() + ", kwotaWn=" + kwotaWn + ", kwotaMa=" + kwotaMa + '}';
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
        if (!Objects.equals(this.wierszBOPK, other.wierszBOPK)) {
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

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    public double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public WierszBOPK getWierszBOPK() {
        return wierszBOPK;
    }

    public void setWierszBOPK(WierszBOPK wierszBOPK) {
        this.wierszBOPK = wierszBOPK;
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
        return kwotaWnPLN;
    }

    public void setKwotaWnPLN(double kwotaWnPLN) {
        this.kwotaWnPLN = kwotaWnPLN;
    }

    public double getKwotaMaPLN() {
        return kwotaMaPLN;
    }

    public void setKwotaMaPLN(double kwotaMaPLN) {
        this.kwotaMaPLN = kwotaMaPLN;
    }
    
    public String getOpis() {
        return this.getWierszBOPK().getOpis();
    }
    
    public String getRok() {
        return this.getWierszBOPK().getRok();
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
    }

}
