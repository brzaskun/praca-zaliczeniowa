/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import data.Data;
import entity.Nieobecnosc;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class Soka1 implements Serializable {
     private int id;
     private static final long serialVersionUID = 1L;
     private String kod;
     private String dataod;
     private String datado;
     private int dni;
     private double godziny;
     private double kwota;
     private String waluta;

     
    public Soka1(int id, Nieobecnosc nieobecnoscaccu, String dataod, String datado, String waluta, double kwota) {
        this.id = id;
        this.kod = nieobecnoscaccu.getRodzajnieobecnosci().getKod();
        this.dataod = dataod;
        this.datado = datado;
        this.dni = Data.iletodniKalendarzowych(dataod, datado);
        this.kwota = kwota;
        this.waluta = waluta;
        this.godziny = nieobecnoscaccu.getGodzinyroboczenieobecnosc();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
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
        final Soka1 other = (Soka1) obj;
        return this.id == other.id;
    }
     
    

    

    @Override
    public String toString() {
        return "Soka{, kod=" + kod + ", dataod=" + dataod + ", datado=" + datado + ", dni=" + dni + ", kwota=" + kwota + ", waluta=" + waluta + '}';
    }
     
     
    
}
