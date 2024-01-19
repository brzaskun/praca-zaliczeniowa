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
    //emeryt
    @Column(name = "pracaemeryt43przychod")
    private double pracaemeryt43przychod;
    @Column(name = "pracaemeryt44koszt")
    private double pracaemeryt44koszt;
    @Column(name = "pracaemeryt45dochod")
    private double pracaemeryt45dochod;
    @Column(name = "praca46dochodzwolniony")
    private double pracaemeryt46dochodzwolniony;
    @Column(name = "pracaemeryt47zaliczka")
    private double pracaemeryt47zaliczka;
    @Column(name = "pracaemeryt48przychodwyzszekoszty")
    private double pracaemeryt48przychodwyzszekoszty;
    @Column(name = "pracaemeryt49wyzszekoszty")
    private double pracaemeryt49wyzszekoszty;
    
    @Column(name = "funkcja54przychod")
    private double funkcja54przychod;
    @Column(name = "funkcja55koszt")
    private double funkcja55koszt;
    @Column(name = "funkcja56dochod")
    private double funkcja56dochod;
    @Column(name = "funkcja57zaliczka")
    private double funkcja57zaliczka;
    @Column(name = "zlecenie58przychod")
    private double zlecenie58przychod;
    @Column(name = "zlecenie59koszt")
    private double zlecenie59koszt;
    @Column(name = "zlecenie60dochod")
    private double zlecenie60dochod;
    @Column(name = "zlecenie61zaliczka")
    private double zlecenie61zaliczka;
    @Column(name = "zlecenie62przychod26")
    private double zlecenie62przychod26;
    @Column(name = "zlecenie63koszt26")
    private double zlecenie63koszt26;
    @Column(name = "zlecenie64dochod26")
    private double zlecenie64dochod26;
    @Column(name = "zlecenie65zaliczka26")
    private double zlecenie65zaliczka26;
    @Column(name = "zus51pole75")
    private double zus51pole95;
    @Column(name = "zus51pole96")
    private double zus51pole96;
    @Column(name = "zus51pole77")
    private double zus51pole97;
    @Column(name = "zus52pole78")
    private double zus52pole122;
//    @Column(name = "zus52pole80")
//    private double zus52pole80;
    @Column(name = "przychody92sumado26lat")
    private double przychody109sumado26lat;
    @Column(name = "praca93do26lat")
    private double praca110do26lat;
    @Column(name = "zlecenie94do26lat")
    private double zlecenie111do26lat;
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
        this.zus51pole95 = this.zus51pole95+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
        if (karta.getDochodzagranica()>0.0) {
            this.praca32dochodzwolniony = karta.getDochodzagranica();
            this.praca29przychod = this.praca29przychod-this.praca32dochodzwolniony;
            this.praca31dochod = this.praca29przychod-this.praca30koszt;
        }
    }

    public void dodajprace26zwolnione(Kartawynagrodzen karta) {
        if (karta.getDochodzagranica()>0.0&&karta.isPrzekroczeniedni()) {
            this.praca110do26lat = karta.getDochodpolska();
            this.przychody109sumado26lat= this.przychody109sumado26lat+karta.getDochodpolska();
        } else {
            this.praca110do26lat = karta.getBrutto();
            this.przychody109sumado26lat= this.przychody109sumado26lat+karta.getBrutto();
        }
        this.zus51pole97 = this.zus51pole97+karta.getRazemspolecznepracownik();
        //this.zus52pole80 = this.zus52pole80+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajpracekosztywysokie(Kartawynagrodzen karta) {
        this.praca34przychodwyzszekoszty = karta.getBrutto();
        this.praca35wyzszekoszty = karta.getKosztyuzyskania();
        this.praca31dochod = this.praca31dochod+this.praca34przychodwyzszekoszty-this.praca35wyzszekoszty;
        this.praca33zaliczka = this.praca33zaliczka+karta.getPodatekdochodowy();
        this.zus51pole95 = this.zus51pole95+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajpelnieniefunkcji(Kartawynagrodzen karta) {
        this.funkcja54przychod = karta.getBrutto();
        this.funkcja55koszt = karta.getKosztyuzyskania();
        this.funkcja56dochod = this.funkcja54przychod-this.funkcja55koszt;
        this.funkcja57zaliczka = karta.getPodatekdochodowy();
        this.zus51pole95 = this.zus51pole95+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajzlecenie(Kartawynagrodzen karta) {
        this.zlecenie58przychod = karta.getBrutto();
        this.zlecenie59koszt = karta.getKosztyuzyskania();
        this.zlecenie60dochod = this.zlecenie58przychod-this.zlecenie59koszt;
        this.zlecenie61zaliczka = karta.getPodatekdochodowy();
        this.zus51pole95 = this.zus51pole95+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
    }
    
     public void dodajzlecenie26opodatkowane(Kartawynagrodzen karta) {
        this.zlecenie62przychod26 = karta.getBrutto();
        this.zlecenie63koszt26 = karta.getKosztyuzyskania();
        this.zlecenie64dochod26 = this.zlecenie62przychod26-this.zlecenie63koszt26;
        this.zlecenie65zaliczka26 = karta.getPodatekdochodowy();
        this.zus51pole96 = this.zus51pole96+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajzlecenie26zwolnione(Kartawynagrodzen karta) {
        this.zlecenie111do26lat = karta.getBrutto();
        this.przychody109sumado26lat= this.przychody109sumado26lat+karta.getBrutto();
        this.zus51pole97 = this.zus51pole97+karta.getRazemspolecznepracownik();
        //this.zus52pole80 = this.zus52pole80+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajumowaopraceEmeryt(Kartawynagrodzen karta) {
        this.pracaemeryt43przychod = karta.getBrutto();
        this.pracaemeryt44koszt = karta.getKosztyuzyskania();
        this.pracaemeryt45dochod = this.pracaemeryt45dochod+this.pracaemeryt43przychod-this.pracaemeryt44koszt;
        this.pracaemeryt47zaliczka = this.pracaemeryt47zaliczka+karta.getPodatekdochodowy();
        this.zus51pole96 = this.zus51pole96+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
    }

    public void dodajumowaopraceEmerytkosztypodwyzszone(Kartawynagrodzen karta) {
        this.pracaemeryt48przychodwyzszekoszty = karta.getBrutto();
        this.pracaemeryt49wyzszekoszty = karta.getKosztyuzyskania();
        this.pracaemeryt45dochod = this.pracaemeryt45dochod+this.pracaemeryt48przychodwyzszekoszty-this.pracaemeryt49wyzszekoszty;
        this.pracaemeryt47zaliczka = this.pracaemeryt47zaliczka+karta.getPodatekdochodowy();
        this.zus51pole96 = this.zus51pole96+karta.getRazemspolecznepracownik();
        this.zus52pole122 = this.zus52pole122+karta.getPraczdrowotnedopotracenia();
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
        return "PITPola{" + "praca29przychod=" + praca29przychod + ", praca30koszt=" + praca30koszt + ", praca33zaliczka=" + praca33zaliczka + ", zlecenie51przychod=" + zlecenie58przychod + ", zlecenie52koszt=" + zlecenie59koszt + ", zlecenie54zaliczka=" + zlecenie61zaliczka + ", przychody92sumado26lat=" + przychody109sumado26lat + ", pracownik=" + pracownik.getNazwiskoImie() + ", rok=" + rok + '}';
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

    public double getFunkcja54przychod() {
        return funkcja54przychod;
    }

    public void setFunkcja54przychod(double funkcja54przychod) {
        this.funkcja54przychod = funkcja54przychod;
    }

    public double getFunkcja55koszt() {
        return funkcja55koszt;
    }

    public void setFunkcja55koszt(double funkcja55koszt) {
        this.funkcja55koszt = funkcja55koszt;
    }

    public double getFunkcja56dochod() {
        return funkcja56dochod;
    }

    public void setFunkcja56dochod(double funkcja56dochod) {
        this.funkcja56dochod = funkcja56dochod;
    }

    public double getFunkcja57zaliczka() {
        return funkcja57zaliczka;
    }

    public void setFunkcja57zaliczka(double funkcja57zaliczka) {
        this.funkcja57zaliczka = funkcja57zaliczka;
    }

    public double getZlecenie58przychod() {
        return zlecenie58przychod;
    }

    public void setZlecenie58przychod(double zlecenie58przychod) {
        this.zlecenie58przychod = zlecenie58przychod;
    }

    public double getZlecenie59koszt() {
        return zlecenie59koszt;
    }

    public void setZlecenie59koszt(double zlecenie59koszt) {
        this.zlecenie59koszt = zlecenie59koszt;
    }

    public double getZlecenie60dochod() {
        return zlecenie60dochod;
    }

    public void setZlecenie60dochod(double zlecenie60dochod) {
        this.zlecenie60dochod = zlecenie60dochod;
    }

    public double getZlecenie61zaliczka() {
        return zlecenie61zaliczka;
    }

    public void setZlecenie61zaliczka(double zlecenie61zaliczka) {
        this.zlecenie61zaliczka = zlecenie61zaliczka;
    }

    public double getZus51pole95() {
        return zus51pole95;
    }

    public void setZus51pole95(double zus51pole95) {
        this.zus51pole95 = zus51pole95;
    }

    public double getZus51pole97() {
        return zus51pole97;
    }

    public void setZus51pole97(double zus51pole97) {
        this.zus51pole97 = zus51pole97;
    }

    public double getZus52pole122() {
        return zus52pole122;
    }

    public void setZus52pole122(double zus52pole122) {
        this.zus52pole122 = zus52pole122;
    }

//    public double getZus52pole80() {
//        return zus52pole80;
//    }
//
//    public void setZus52pole80(double zus52pole80) {
//        this.zus52pole80 = zus52pole80;
//    }

    public double getPrzychody109sumado26lat() {
        return przychody109sumado26lat;
    }

    public void setPrzychody109sumado26lat(double przychody109sumado26lat) {
        this.przychody109sumado26lat = przychody109sumado26lat;
    }

    public double getPraca110do26lat() {
        return praca110do26lat;
    }

    public void setPraca110do26lat(double praca110do26lat) {
        this.praca110do26lat = praca110do26lat;
    }

    public double getZlecenie111do26lat() {
        return zlecenie111do26lat;
    }

    public void setZlecenie111do26lat(double zlecenie111do26lat) {
        this.zlecenie111do26lat = zlecenie111do26lat;
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

    public double getZlecenie62przychod26() {
        return zlecenie62przychod26;
    }

    public void setZlecenie62przychod26(double zlecenie62przychod26) {
        this.zlecenie62przychod26 = zlecenie62przychod26;
    }

    public double getZlecenie63koszt26() {
        return zlecenie63koszt26;
    }

    public void setZlecenie63koszt26(double zlecenie63koszt26) {
        this.zlecenie63koszt26 = zlecenie63koszt26;
    }

    public double getZlecenie64dochod26() {
        return zlecenie64dochod26;
    }

    public void setZlecenie64dochod26(double zlecenie64dochod26) {
        this.zlecenie64dochod26 = zlecenie64dochod26;
    }

    public double getZlecenie65zaliczka26() {
        return zlecenie65zaliczka26;
    }

    public void setZlecenie65zaliczka26(double zlecenie65zaliczka26) {
        this.zlecenie65zaliczka26 = zlecenie65zaliczka26;
    }

    public double getZus51pole96() {
        return zus51pole96;
    }

    public void setZus51pole96(double zus51pole96) {
        this.zus51pole96 = zus51pole96;
    }

    public double getPracaemeryt43przychod() {
        return pracaemeryt43przychod;
    }

    public void setPracaemeryt43przychod(double pracaemeryt43przychod) {
        this.pracaemeryt43przychod = pracaemeryt43przychod;
    }

    public double getPracaemeryt44koszt() {
        return pracaemeryt44koszt;
    }

    public void setPracaemeryt44koszt(double pracaemeryt44koszt) {
        this.pracaemeryt44koszt = pracaemeryt44koszt;
    }

    public double getPracaemeryt45dochod() {
        return pracaemeryt45dochod;
    }

    public void setPracaemeryt45dochod(double pracaemeryt45dochod) {
        this.pracaemeryt45dochod = pracaemeryt45dochod;
    }

    public double getPracaemeryt46dochodzwolniony() {
        return pracaemeryt46dochodzwolniony;
    }

    public void setPracaemeryt46dochodzwolniony(double pracaemeryt46dochodzwolniony) {
        this.pracaemeryt46dochodzwolniony = pracaemeryt46dochodzwolniony;
    }

    public double getPracaemeryt47zaliczka() {
        return pracaemeryt47zaliczka;
    }

    public void setPracaemeryt47zaliczka(double pracaemeryt47zaliczka) {
        this.pracaemeryt47zaliczka = pracaemeryt47zaliczka;
    }

    public double getPracaemeryt48przychodwyzszekoszty() {
        return pracaemeryt48przychodwyzszekoszty;
    }

    public void setPracaemeryt48przychodwyzszekoszty(double pracaemeryt48przychodwyzszekoszty) {
        this.pracaemeryt48przychodwyzszekoszty = pracaemeryt48przychodwyzszekoszty;
    }

    public double getPracaemeryt49wyzszekoszty() {
        return pracaemeryt49wyzszekoszty;
    }

    public void setPracaemeryt49wyzszekoszty(double pracaemeryt49wyzszekoszty) {
        this.pracaemeryt49wyzszekoszty = pracaemeryt49wyzszekoszty;
    }

    
    
   
    
    
}
