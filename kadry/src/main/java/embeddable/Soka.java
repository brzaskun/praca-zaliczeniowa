/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import data.Data;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class Soka implements Serializable {
     private static final long serialVersionUID = 1L;
     private String nazwiskoiimie;
     private String pesel;
     private String kod;
     private String dataod;
     private String datado;
     private int dni;
     private double godziny;
     private double kwota;
     private String waluta;

    public Soka(Naliczenienieobecnosc nal) {
        this.nazwiskoiimie = nal.getNazwiskoiImie();
        this.pesel = nal.getPasekwynagrodzen().getPesel();
        this.kod = nal.getNieobecnosc().getRodzajnieobecnosci().getKod();
        this.dataod = nal.getDataod();
        this.datado = nal.getDatado();
        this.dni = (int) nal.getLiczbadniNieobecnosci();
        this.kwota = nal.getKwotawaluta();
        this.waluta = nal.getWaluta();
        this.godziny = nal.getLiczbagodzinNieobecnosci();
    }

    public Soka(Nieobecnosc nieobecnoscaccu, String dataod, String datado, Naliczenieskladnikawynagrodzenia oddelegowanie) {
        this.nazwiskoiimie = nieobecnoscaccu.getAngaz().getNazwiskoiImie();
        this.pesel = nieobecnoscaccu.getAngaz().getPracownik().getPesel();
        this.kod = nieobecnoscaccu.getRodzajnieobecnosci().getKod();
        this.dataod = dataod;
        this.datado = datado;
        this.dni = Data.iletodniKalendarzowych(dataod, datado);
        this.kwota = oddelegowanie.getKwotadolistyplacwaluta();
        this.waluta = oddelegowanie.getWaluta();
        this.godziny = nieobecnoscaccu.getGodzinyroboczenieobecnosc();
    }

    public String getNazwiskoiimie() {
        return nazwiskoiimie;
    }

    public void setNazwiskoiimie(String nazwiskoiimie) {
        this.nazwiskoiimie = nazwiskoiimie;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

   
    public String getDataod() {
        return Data.getDzien(dataod);
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return Data.getDzien(datado);
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public double getGodziny() {
        return godziny;
    }

    public void setGodziny(double godziny) {
        this.godziny = godziny;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.nazwiskoiimie);
        hash = 71 * hash + Objects.hashCode(this.pesel);
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
        final Soka other = (Soka) obj;
        if (!Objects.equals(this.nazwiskoiimie, other.nazwiskoiimie)) {
            return false;
        }
        if (!Objects.equals(this.pesel, other.pesel)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Soka{" + "nazwiskoiimie=" + nazwiskoiimie + ", pesel=" + pesel + ", kod=" + kod + ", dataod=" + dataod + ", datado=" + datado + ", dni=" + dni + ", kwota=" + kwota + ", waluta=" + waluta + '}';
    }
     
     
    
}
