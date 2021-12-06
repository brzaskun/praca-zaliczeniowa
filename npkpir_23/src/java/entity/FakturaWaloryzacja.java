/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "FakturaWaloryzacja.findbyPodatnikKontrahentRok", query = "SELECT e FROM FakturaWaloryzacja e WHERE e.podatnik = :podatnik AND e.kontrahent = :kontrahent AND e.rok = :rok")
})
public class FakturaWaloryzacja  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    @ManyToOne
    private Podatnik podatnik;
    @JoinColumn(name = "kontrahent", referencedColumnName = "id")
    @ManyToOne
    private Klienci kontrahent;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Size(max = 45)
    @Column(name = "mc")
    private String mc;
    @Column(name = "kwotabiezacanetto")
    private double kwotabiezacanetto;
    @Column(name = "sprawozdanieroczne")
    private double sprawozdanieroczne;
    @Column(name = "obsluganiemcy")
    private double obsluganiemcy;
    @Column(name = "umowaoprace")
    private double umowaoprace;
    @Column(name = "umowazlecenie")
    private double umowazlecenie;
    @Column(name = "oddelegowanie")
    private double oddelegowanie;
    @Column(name = "kwotabiezacanettoN")
    private double kwotabiezacanettoN;
    @Column(name = "umowaopraceN")
    private double umowaopraceN;
    @Column(name = "umowazlecenieN")
    private double umowazlecenieN;
    @Column(name = "oddelegowanieN")
    private double oddelegowanieN;
    @Column(name = "sprawozdanieroczneN")
    private double sprawozdanieroczneN;
    @Column(name = "obsluganiemcyN")
    private double obsluganiemcyN;
    @Column(name = "datamaila")
    private String datamaila;
    @Column(name = "klientzaakceptowal")
    private String klientzaakceptowal;
    @Column(name = "procentzmian")
    private double procentzmian;
    @Column(name = "dawnoniezmieniona")
    private boolean  dawnoniezmieniona;
    @Column(name = "zmianaopodatkowania")
    private boolean  zmianaopodatkowania;
    @Column(name = "wiekszailosc")
    private boolean  wiekszailosc;
    @Column(name = "minimum")
    private boolean  minimum;
    @Column(name = "klientakceptacja")
    private boolean  klientakceptacja;
    @Column(name = "niewysylac")
    private boolean  niewysylac;
    

    public FakturaWaloryzacja() {
    }

    
    public FakturaWaloryzacja(Podatnik podatnikObiekt, Klienci szukanyklient, String rokWpisuSt) {
        this.podatnik = podatnikObiekt;
        this.kontrahent = szukanyklient;
        this.rok = rokWpisuSt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public Klienci getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(Klienci kontrahent) {
        this.kontrahent = kontrahent;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public boolean isNiewysylac() {
        return niewysylac;
    }

    public void setNiewysylac(boolean niewysylac) {
        this.niewysylac = niewysylac;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public double getKwotabiezacanetto() {
        return kwotabiezacanetto;
    }

    public void setKwotabiezacanetto(double kwotabiezacanetto) {
        this.kwotabiezacanetto = kwotabiezacanetto;
    }

    public double getSprawozdanieroczne() {
        return sprawozdanieroczne;
    }

    public void setSprawozdanieroczne(double sprawozdanieroczne) {
        this.sprawozdanieroczne = sprawozdanieroczne;
    }

    public double getObsluganiemcy() {
        return obsluganiemcy;
    }

    public void setObsluganiemcy(double obsluganiemcy) {
        this.obsluganiemcy = obsluganiemcy;
    }

    public double getUmowaoprace() {
        return umowaoprace;
    }

    public void setUmowaoprace(double umowaoprace) {
        this.umowaoprace = umowaoprace;
    }

    public double getUmowazlecenie() {
        return umowazlecenie;
    }

    public void setUmowazlecenie(double umowazlecenie) {
        this.umowazlecenie = umowazlecenie;
    }

    public double getOddelegowanie() {
        return oddelegowanie;
    }

    public void setOddelegowanie(double oddelegowanie) {
        this.oddelegowanie = oddelegowanie;
    }

    public double getKwotabiezacanettoN() {
        return kwotabiezacanettoN;
    }

    public void setKwotabiezacanettoN(double kwotabiezacanettoN) {
        this.kwotabiezacanettoN = kwotabiezacanettoN;
    }

    public double getUmowaopraceN() {
        return umowaopraceN;
    }

    public void setUmowaopraceN(double umowaopraceN) {
        this.umowaopraceN = umowaopraceN;
    }

    public double getUmowazlecenieN() {
        return umowazlecenieN;
    }

    public void setUmowazlecenieN(double umowazlecenieN) {
        this.umowazlecenieN = umowazlecenieN;
    }

    public double getOddelegowanieN() {
        return oddelegowanieN;
    }

    public void setOddelegowanieN(double oddelegowanieN) {
        this.oddelegowanieN = oddelegowanieN;
    }

    public double getSprawozdanieroczneN() {
        return sprawozdanieroczneN;
    }

    public void setSprawozdanieroczneN(double sprawozdanieroczneN) {
        this.sprawozdanieroczneN = sprawozdanieroczneN;
    }

    public double getObsluganiemcyN() {
        return obsluganiemcyN;
    }

    public void setObsluganiemcyN(double obsluganiemcyN) {
        this.obsluganiemcyN = obsluganiemcyN;
    }

    
    public String getDatamaila() {
        return datamaila;
    }

    public void setDatamaila(String datamaila) {
        this.datamaila = datamaila;
    }

    public String getKlientzaakceptowal() {
        return klientzaakceptowal;
    }

    public void setKlientzaakceptowal(String klientzaakceptowal) {
        this.klientzaakceptowal = klientzaakceptowal;
    }

    public double getProcentzmian() {
        return procentzmian;
    }

    public void setProcentzmian(double procentzmian) {
        this.procentzmian = procentzmian;
    }

    public boolean isDawnoniezmieniona() {
        return dawnoniezmieniona;
    }

    public void setDawnoniezmieniona(boolean dawnoniezmieniona) {
        this.dawnoniezmieniona = dawnoniezmieniona;
    }

    public boolean isZmianaopodatkowania() {
        return zmianaopodatkowania;
    }

    public void setZmianaopodatkowania(boolean zmianaopodatkowania) {
        this.zmianaopodatkowania = zmianaopodatkowania;
    }

    public boolean isWiekszailosc() {
        return wiekszailosc;
    }

    public void setWiekszailosc(boolean wiekszailosc) {
        this.wiekszailosc = wiekszailosc;
    }

    public boolean isMinimum() {
        return minimum;
    }

    public void setMinimum(boolean minimum) {
        this.minimum = minimum;
    }

    public boolean isKlientakceptacja() {
        return klientakceptacja;
    }

    public void setKlientakceptacja(boolean klientakceptacja) {
        this.klientakceptacja = klientakceptacja;
    }

   

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.kontrahent);
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + Objects.hashCode(this.mc);
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
        final FakturaWaloryzacja other = (FakturaWaloryzacja) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        if (!Objects.equals(this.kontrahent, other.kontrahent)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FakturaWaloryzacja{" + "podatnik=" + podatnik.getPrintnazwa() + ", kontrahent=" + kontrahent.getNpelna() + ", rok=" + rok + ", mc=" + mc + '}';
    }
    
    
    
    
}

