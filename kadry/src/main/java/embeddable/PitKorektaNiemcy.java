/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddable;

import entity.Angaz;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.Objects;
import z.Z;

/**
 *
 * @author Osito
 */
public class PitKorektaNiemcy implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Angaz angaz;
    private double staryprzychod;
    private double staryprzychodniemiecki;
    private double staryspoleczne;
    private double starypodatek;
    private double starypodatekniemiecki;
    private double korektaprzychod;
    private double korektaprzychodniemiecki;
    private double korektaspoleczne;
    private double korektapodatek;
    private double korektapodatekniemiecki;
    private double nowyprzychod;
    private double nowyprzychodniemiecki;
    private double nowyspoleczne;
    private double nowypodatek;
    private double nowypodatekniemiecki;
    
    public void dodajstare(Pasekwynagrodzen pasek) {
        this.staryprzychod = Z.z(this.staryprzychod + pasek.getBruttozuskraj());
        this.staryprzychodniemiecki = Z.z(this.staryprzychodniemiecki + pasek.getPodstawaopodatkowaniazagranicawaluta());
        this.staryspoleczne = Z.z(this.staryspoleczne + pasek.getRazemspolecznepracownik());
        this.starypodatek = Z.z(this.starypodatek + pasek.getPodatekdochodowy());
        this.starypodatekniemiecki = Z.z(this.starypodatekniemiecki + pasek.getPodatekdochodowyzagranicawaluta());
    }

    
    public void dodajnowe(Pasekwynagrodzen pasek) {
        this.korektaprzychod = Z.z(this.korektaprzychod - pasek.getOddelegowaniepln());
        this.korektaprzychodniemiecki = Z.z(this.korektaprzychodniemiecki + pasek.getPrzekroczeniepodstawaniemiecka());
        this.korektaspoleczne = Z.z(this.korektaspoleczne - (pasek.getRazemspolecznepracownik()-pasek.getSpoleczneudzialpolska()));
        this.korektapodatek = Z.z(this.korektapodatek - (pasek.getPodatekdochodowy()-pasek.getPrzekroczenienowypodatek()));
        this.korektapodatekniemiecki = Z.z(this.korektapodatekniemiecki + pasek.getPrzekroczeniepodatekniemiecki());
    }
    
     public void roznica() {
        this.nowyprzychod = Z.z(this.staryprzychod+this.korektaprzychod);
        this.nowyprzychodniemiecki = Z.z(this.staryprzychodniemiecki+this.korektaprzychodniemiecki);
        this.nowyspoleczne = Z.z(this.staryspoleczne+this.korektaspoleczne);
        this.nowypodatek = Z.z(this.starypodatek+this.korektapodatek);
        this.nowypodatekniemiecki = Z.z(this.starypodatekniemiecki+this.korektapodatekniemiecki);
    }
    
     public void sumuj(PitKorektaNiemcy pitKorektaNiemcy) {
        this.korektaprzychod = Z.z(this.korektaprzychod + pitKorektaNiemcy.korektaprzychod);
        this.korektaprzychodniemiecki = Z.z(this.korektaprzychodniemiecki + pitKorektaNiemcy.getKorektapodatekniemiecki());
        this.korektaspoleczne = Z.z(this.korektaspoleczne + pitKorektaNiemcy.getKorektaspoleczne());
        this.korektapodatek = Z.z(this.korektapodatek + pitKorektaNiemcy.getKorektapodatek());
        this.korektapodatekniemiecki = Z.z(this.korektapodatekniemiecki + pitKorektaNiemcy.getKorektapodatekniemiecki());
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.angaz);
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
        final PitKorektaNiemcy other = (PitKorektaNiemcy) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.angaz, other.angaz);
    }

    @Override
    public String toString() {
        return "PitKorektaNiemcy{" + "angaz=" + angaz.getNazwiskoiImie() + ", staryprzychod=" + staryprzychod + ", staryspoleczne=" + staryspoleczne + ", starypodatek=" + starypodatek + ", nowyprzychod=" + nowyprzychod + ", nowyspoleczne=" + nowyspoleczne + ", nowypodatek=" + nowypodatek + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
    }

    public double getStaryprzychod() {
        return staryprzychod;
    }

    public void setStaryprzychod(double staryprzychod) {
        this.staryprzychod = staryprzychod;
    }

    public double getStaryspoleczne() {
        return staryspoleczne;
    }

    public void setStaryspoleczne(double staryspoleczne) {
        this.staryspoleczne = staryspoleczne;
    }

    public double getStarypodatek() {
        return starypodatek;
    }

    public void setStarypodatek(double starypodatek) {
        this.starypodatek = starypodatek;
    }

    public double getNowyprzychod() {
        return nowyprzychod;
    }

    public void setNowyprzychod(double nowyprzychod) {
        this.nowyprzychod = nowyprzychod;
    }

    public double getNowyspoleczne() {
        return nowyspoleczne;
    }

    public void setNowyspoleczne(double nowyspoleczne) {
        this.nowyspoleczne = nowyspoleczne;
    }

    public double getNowypodatek() {
        return nowypodatek;
    }

    public void setNowypodatek(double nowypodatek) {
        this.nowypodatek = nowypodatek;
    }

    public double getStarypodatekniemiecki() {
        return starypodatekniemiecki;
    }

    public void setStarypodatekniemiecki(double starypodatekniemiecki) {
        this.starypodatekniemiecki = starypodatekniemiecki;
    }

    public double getNowypodatekniemiecki() {
        return nowypodatekniemiecki;
    }

    public void setNowypodatekniemiecki(double nowypodatekniemiecki) {
        this.nowypodatekniemiecki = nowypodatekniemiecki;
    }

    public double getStaryprzychodniemiecki() {
        return staryprzychodniemiecki;
    }

    public void setStaryprzychodniemiecki(double staryprzychodniemiecki) {
        this.staryprzychodniemiecki = staryprzychodniemiecki;
    }

    public double getNowyprzychodniemiecki() {
        return nowyprzychodniemiecki;
    }

    public void setNowyprzychodniemiecki(double nowyprzychodniemiecki) {
        this.nowyprzychodniemiecki = nowyprzychodniemiecki;
    }

    public double getKorektaprzychod() {
        return korektaprzychod;
    }

    public void setKorektaprzychod(double korektaprzychod) {
        this.korektaprzychod = korektaprzychod;
    }

    public double getKorektaprzychodniemiecki() {
        return korektaprzychodniemiecki;
    }

    public void setKorektaprzychodniemiecki(double korektaprzychodniemiecki) {
        this.korektaprzychodniemiecki = korektaprzychodniemiecki;
    }

    public double getKorektaspoleczne() {
        return korektaspoleczne;
    }

    public void setKorektaspoleczne(double korektaspoleczne) {
        this.korektaspoleczne = korektaspoleczne;
    }

    public double getKorektapodatek() {
        return korektapodatek;
    }

    public void setKorektapodatek(double korektapodatek) {
        this.korektapodatek = korektapodatek;
    }

    public double getKorektapodatekniemiecki() {
        return korektapodatekniemiecki;
    }

    public void setKorektapodatekniemiecki(double korektapodatekniemiecki) {
        this.korektapodatekniemiecki = korektapodatekniemiecki;
    }

    

    

    
    
    
    
    
    
}
