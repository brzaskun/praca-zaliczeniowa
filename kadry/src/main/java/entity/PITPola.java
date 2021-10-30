/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "pitpola")
public class PITPola {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "praca29przychod")
    private double praca29przychod;
    @Column(name = "praca30koszt")
    private double praca30koszt;
    @Column(name = "praca34przychodwyzszekoszty")
    private double praca34przychodwyzszekoszty;
    @Column(name = "praca35wyzszekoszty")
    private double praca35wyzszekoszty;
    @Column(name = "praca31dochod")
    private double praca31dochod;
    @Column(name = "praca32dochodzwolniony")
    private double praca32dochodzwolniony;
    @Column(name = "praca33zaliczka")
    private double praca33zaliczka;
    @Column(name = "funkcja47przychod")
    private double funkcja47przychod;
    @Column(name = "funkcja48koszt")
    private double funkcja48koszt;
    @Column(name = "funkcja49dochod")
    private double funkcja49dochod;
    @Column(name = "funkcja50zaliczka")
    private double funkcja50zaliczka;
    @Column(name = "zlecenie51przychod")
    private double zlecenie51przychod;
    @Column(name = "zlecenie52koszt")
    private double zlecenie52koszt;
    @Column(name = "zlecenie53dochod")
    private double zlecenie53dochod;
    @Column(name = "zlecenie54zaliczka")
    private double zlecenie54zaliczka;
    @Column(name = "zus51pole75")
    private double zus51pole75;
    @Column(name = "zus51pole77")
    private double zus51pole77;
    @Column(name = "zus52pole78")
    private double zus52pole78;
    @Column(name = "zus52pole80")
    private double zus52pole80;
    @Column(name = "przychody92sumado26lat")
    private double przychody92sumado26lat;
    @Column(name = "praca93do26lat")
    private double praca93do26lat;
    @Column(name = "zlecenie94do26lat")
    private double zlecenie94do26lat;
    @JoinColumn(name = "pracownik", referencedColumnName = "id")
    @NotNull
    @ManyToOne
    private Pracownik pracownik;
    @Size(max = 4)
    @Column(name = "rok")
    private String rok;
    @Column(name = "kosztyuzyskania")
    private byte kosztyzuzyskania;

    
     public void dodajprace(Kartawynagrodzen karta) {
        this.praca29przychod = karta.getBrutto();
        this.praca30koszt = karta.getKosztyuzyskania();
        this.praca31dochod = this.praca31dochod+this.praca29przychod-this.praca30koszt;
        this.praca33zaliczka = this.praca33zaliczka+karta.getPodatekdochodowy();
        this.zus51pole75 = this.zus51pole75+karta.getRazemspolecznepracownik();
        this.zus52pole78 = this.zus52pole78+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajprace26zwolnione(Kartawynagrodzen karta) {
        this.praca93do26lat = karta.getBrutto();
        this.przychody92sumado26lat= this.przychody92sumado26lat+karta.getBrutto();
        this.zus51pole77 = this.zus51pole77+karta.getRazemspolecznepracownik();
        this.zus52pole80 = this.zus52pole80+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajpracekosztywysokie(Kartawynagrodzen karta) {
        this.praca34przychodwyzszekoszty = karta.getBrutto();
        this.praca35wyzszekoszty = karta.getKosztyuzyskania();
        this.praca31dochod = this.praca31dochod+this.praca34przychodwyzszekoszty-this.praca35wyzszekoszty;
        this.praca33zaliczka = this.praca33zaliczka+karta.getPodatekdochodowy();
        this.zus51pole75 = this.zus51pole75+karta.getRazemspolecznepracownik();
        this.zus52pole78 = this.zus52pole78+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajpelnieniefunkcji(Kartawynagrodzen karta) {
        this.funkcja47przychod = karta.getBrutto();
        this.funkcja48koszt = karta.getKosztyuzyskania();
        this.funkcja49dochod = this.funkcja47przychod-this.funkcja48koszt;
        this.funkcja50zaliczka = karta.getPodatekdochodowy();
        this.zus51pole75 = this.zus51pole75+karta.getRazemspolecznepracownik();
        this.zus52pole78 = this.zus52pole78+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajzlecenie(Kartawynagrodzen karta) {
        this.zlecenie51przychod = karta.getBrutto();
        this.zlecenie52koszt = karta.getKosztyuzyskania();
        this.zlecenie53dochod = this.zlecenie51przychod-this.zlecenie52koszt;
        this.zlecenie54zaliczka = karta.getPodatekdochodowy();
        this.zus51pole75 = this.zus51pole75+karta.getRazemspolecznepracownik();
        this.zus52pole78 = this.zus52pole78+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajzlecenie26zwolnione(Kartawynagrodzen karta) {
        this.zlecenie94do26lat = karta.getBrutto();
        this.przychody92sumado26lat= this.przychody92sumado26lat+karta.getBrutto();
        this.zus51pole77 = this.zus51pole77+karta.getRazemspolecznepracownik();
        this.zus52pole80 = this.zus52pole80+karta.getPraczdrowotnedopotracenia();
    }

    
    
    public PITPola() {
    }

    
    public PITPola(Pracownik pracownik, String rok) {
        this.pracownik = pracownik;
        this.rok = rok;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.pracownik);
        hash = 17 * hash + Objects.hashCode(this.rok);
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
        final PITPola other = (PITPola) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.pracownik, other.pracownik)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PITPola{" + "praca29przychod=" + praca29przychod + ", praca30koszt=" + praca30koszt + ", praca33zaliczka=" + praca33zaliczka + ", zlecenie51przychod=" + zlecenie51przychod + ", zlecenie52koszt=" + zlecenie52koszt + ", zlecenie54zaliczka=" + zlecenie54zaliczka + ", przychody92sumado26lat=" + przychody92sumado26lat + ", pracownik=" + pracownik.getNazwiskoImie() + ", rok=" + rok + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPraca29przychod() {
        return praca29przychod;
    }

    public void setPraca29przychod(double praca29przychod) {
        this.praca29przychod = praca29przychod;
    }

    public double getPraca30koszt() {
        return praca30koszt;
    }

    public void setPraca30koszt(double praca30koszt) {
        this.praca30koszt = praca30koszt;
    }

    public double getPraca34przychodwyzszekoszty() {
        return praca34przychodwyzszekoszty;
    }

    public void setPraca34przychodwyzszekoszty(double praca34przychodwyzszekoszty) {
        this.praca34przychodwyzszekoszty = praca34przychodwyzszekoszty;
    }

    public double getPraca35wyzszekoszty() {
        return praca35wyzszekoszty;
    }

    public void setPraca35wyzszekoszty(double praca35wyzszekoszty) {
        this.praca35wyzszekoszty = praca35wyzszekoszty;
    }

    public double getPraca31dochod() {
        return praca31dochod;
    }

    public void setPraca31dochod(double praca31dochod) {
        this.praca31dochod = praca31dochod;
    }

    public double getPraca32dochodzwolniony() {
        return praca32dochodzwolniony;
    }

    public void setPraca32dochodzwolniony(double praca32dochodzwolniony) {
        this.praca32dochodzwolniony = praca32dochodzwolniony;
    }

    public double getPraca33zaliczka() {
        return praca33zaliczka;
    }

    public void setPraca33zaliczka(double praca33zaliczka) {
        this.praca33zaliczka = praca33zaliczka;
    }

    public double getFunkcja47przychod() {
        return funkcja47przychod;
    }

    public void setFunkcja47przychod(double funkcja47przychod) {
        this.funkcja47przychod = funkcja47przychod;
    }

    public double getFunkcja48koszt() {
        return funkcja48koszt;
    }

    public void setFunkcja48koszt(double funkcja48koszt) {
        this.funkcja48koszt = funkcja48koszt;
    }

    public double getFunkcja49dochod() {
        return funkcja49dochod;
    }

    public void setFunkcja49dochod(double funkcja49dochod) {
        this.funkcja49dochod = funkcja49dochod;
    }

    public double getFunkcja50zaliczka() {
        return funkcja50zaliczka;
    }

    public void setFunkcja50zaliczka(double funkcja50zaliczka) {
        this.funkcja50zaliczka = funkcja50zaliczka;
    }

    public double getZlecenie51przychod() {
        return zlecenie51przychod;
    }

    public void setZlecenie51przychod(double zlecenie51przychod) {
        this.zlecenie51przychod = zlecenie51przychod;
    }

    public double getZlecenie52koszt() {
        return zlecenie52koszt;
    }

    public void setZlecenie52koszt(double zlecenie52koszt) {
        this.zlecenie52koszt = zlecenie52koszt;
    }

    public double getZlecenie53dochod() {
        return zlecenie53dochod;
    }

    public void setZlecenie53dochod(double zlecenie53dochod) {
        this.zlecenie53dochod = zlecenie53dochod;
    }

    public double getZlecenie54zaliczka() {
        return zlecenie54zaliczka;
    }

    public void setZlecenie54zaliczka(double zlecenie54zaliczka) {
        this.zlecenie54zaliczka = zlecenie54zaliczka;
    }

    public double getZus51pole75() {
        return zus51pole75;
    }

    public void setZus51pole75(double zus51pole75) {
        this.zus51pole75 = zus51pole75;
    }

    public double getZus51pole77() {
        return zus51pole77;
    }

    public void setZus51pole77(double zus51pole77) {
        this.zus51pole77 = zus51pole77;
    }

    public double getZus52pole78() {
        return zus52pole78;
    }

    public void setZus52pole78(double zus52pole78) {
        this.zus52pole78 = zus52pole78;
    }

    public double getZus52pole80() {
        return zus52pole80;
    }

    public void setZus52pole80(double zus52pole80) {
        this.zus52pole80 = zus52pole80;
    }

    public double getPrzychody92sumado26lat() {
        return przychody92sumado26lat;
    }

    public void setPrzychody92sumado26lat(double przychody92sumado26lat) {
        this.przychody92sumado26lat = przychody92sumado26lat;
    }

    public double getPraca93do26lat() {
        return praca93do26lat;
    }

    public void setPraca93do26lat(double praca93do26lat) {
        this.praca93do26lat = praca93do26lat;
    }

    public double getZlecenie94do26lat() {
        return zlecenie94do26lat;
    }

    public void setZlecenie94do26lat(double zlecenie94do26lat) {
        this.zlecenie94do26lat = zlecenie94do26lat;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public byte getKosztyzuzyskania() {
        return kosztyzuzyskania;
    }

    public void setKosztyzuzyskania(byte kosztyzuzyskania) {
        this.kosztyzuzyskania = kosztyzuzyskania;
    }

   
    
    
}
