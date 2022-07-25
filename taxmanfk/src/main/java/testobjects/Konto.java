/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import java.util.Objects;

/**
 *
 * @author Osito
 */
public class Konto {
    private Integer id;
    private String podatnik;
    private String nrkonta;
    private String syntetyczne;
    private int level;
    private String nazwapelna;
    private String nazwaskrocona;
    private String bilansowewynikowe;
    private String zwyklerozrachszczegolne;
    //private KontopozycjaBiezaca kontopozycjaID;
    private String macierzyste;
    private int macierzysty;
    private String pelnynumer;
    private boolean mapotomkow;
    private boolean rozwin;
    private int rok;
    private double boWn;
    private double boMa;
    private double obrotyWn;
    private double obrotyMa;
    private double saldoWn;
    private double saldoMa;
    private boolean blokada;
    private boolean slownikowe;
    private int idslownika;
    private boolean przychod0koszt1;
    private String syntetycznenumer;
    private String de;

    public Konto(Integer id, String podatnik, String nazwapelna, String pelnynumer, int rok, double saldoWn, double saldoMa, String bilansowewynikowe) {
        this.id = id;
        this.podatnik = podatnik;
        this.nazwapelna = nazwapelna;
        this.pelnynumer = pelnynumer;
        this.rok = rok;
        this.saldoWn = saldoWn;
        this.saldoMa = saldoMa;
        this.bilansowewynikowe = bilansowewynikowe;
    }

    public Konto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public String getSyntetyczne() {
        return syntetyczne;
    }

    public void setSyntetyczne(String syntetyczne) {
        this.syntetyczne = syntetyczne;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNazwapelna() {
        return nazwapelna;
    }

    public void setNazwapelna(String nazwapelna) {
        this.nazwapelna = nazwapelna;
    }

    public String getNazwaskrocona() {
        return nazwaskrocona;
    }

    public void setNazwaskrocona(String nazwaskrocona) {
        this.nazwaskrocona = nazwaskrocona;
    }

    public String getBilansowewynikowe() {
        return bilansowewynikowe;
    }

    public void setBilansowewynikowe(String bilansowewynikowe) {
        this.bilansowewynikowe = bilansowewynikowe;
    }

    public String getZwyklerozrachszczegolne() {
        return zwyklerozrachszczegolne;
    }

    public void setZwyklerozrachszczegolne(String zwyklerozrachszczegolne) {
        this.zwyklerozrachszczegolne = zwyklerozrachszczegolne;
    }

    public String getMacierzyste() {
        return macierzyste;
    }

    public void setMacierzyste(String macierzyste) {
        this.macierzyste = macierzyste;
    }

    public int getMacierzysty() {
        return macierzysty;
    }

    public void setMacierzysty(int macierzysty) {
        this.macierzysty = macierzysty;
    }

    public String getPelnynumer() {
        return pelnynumer;
    }

    public void setPelnynumer(String pelnynumer) {
        this.pelnynumer = pelnynumer;
    }

    public boolean isMapotomkow() {
        return mapotomkow;
    }

    public void setMapotomkow(boolean mapotomkow) {
        this.mapotomkow = mapotomkow;
    }

    public boolean isRozwin() {
        return rozwin;
    }

    public void setRozwin(boolean rozwin) {
        this.rozwin = rozwin;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public double getBoWn() {
        return boWn;
    }

    public void setBoWn(double boWn) {
        this.boWn = boWn;
    }

    public double getBoMa() {
        return boMa;
    }

    public void setBoMa(double boMa) {
        this.boMa = boMa;
    }

    public double getObrotyWn() {
        return obrotyWn;
    }

    public void setObrotyWn(double obrotyWn) {
        this.obrotyWn = obrotyWn;
    }

    public double getObrotyMa() {
        return obrotyMa;
    }

    public void setObrotyMa(double obrotyMa) {
        this.obrotyMa = obrotyMa;
    }

    public double getSaldoWn() {
        return saldoWn;
    }

    public void setSaldoWn(double saldoWn) {
        this.saldoWn = saldoWn;
    }

    public double getSaldoMa() {
        return saldoMa;
    }

    public void setSaldoMa(double saldoMa) {
        this.saldoMa = saldoMa;
    }

    public boolean isBlokada() {
        return blokada;
    }

    public void setBlokada(boolean blokada) {
        this.blokada = blokada;
    }

    public boolean isSlownikowe() {
        return slownikowe;
    }

    public void setSlownikowe(boolean slownikowe) {
        this.slownikowe = slownikowe;
    }

    public int getIdslownika() {
        return idslownika;
    }

    public void setIdslownika(int idslownika) {
        this.idslownika = idslownika;
    }

    public boolean isPrzychod0koszt1() {
        return przychod0koszt1;
    }

    public void setPrzychod0koszt1(boolean przychod0koszt1) {
        this.przychod0koszt1 = przychod0koszt1;
    }

    public String getSyntetycznenumer() {
        return syntetycznenumer;
    }

    public void setSyntetycznenumer(String syntetycznenumer) {
        this.syntetycznenumer = syntetycznenumer;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    @Override
    public String toString() {
        return "Konto{" + "podatnik=" + podatnik + ", nazwapelna=" + nazwapelna + ", pelnynumer=" + pelnynumer + ", rok=" + rok + ", saldoWn=" + saldoWn + ", saldoMa=" + saldoMa + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Konto other = (Konto) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
