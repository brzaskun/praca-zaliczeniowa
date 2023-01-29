/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class StanZatrudnienia implements Serializable{
    private static final long serialVersionUID = 1L;
    private String dzien;
    private double osobyrazem;
    private double kobiety;
    private double mezczyzni;
    private double etatyrazem;
    private double etatykobiety;
    private double etatymezczyzni;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.dzien);
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
        final StanZatrudnienia other = (StanZatrudnienia) obj;
        if (!Objects.equals(this.dzien, other.dzien)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StanZatrudnienia{" + "dzien=" + dzien + ", osobyrazem=" + osobyrazem + ", etatyrazem=" + etatyrazem + '}';
    }

    public String getDzien() {
        return dzien;
    }

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }

    public double getOsobyrazem() {
        return osobyrazem;
    }

    public void setOsobyrazem(double osobyrazem) {
        this.osobyrazem = osobyrazem;
    }

    public double getKobiety() {
        return kobiety;
    }

    public void setKobiety(double kobiety) {
        this.kobiety = kobiety;
    }

    public double getMezczyzni() {
        return mezczyzni;
    }

    public void setMezczyzni(double mezczyzni) {
        this.mezczyzni = mezczyzni;
    }

    public double getEtatyrazem() {
        return etatyrazem;
    }

    public void setEtatyrazem(double etatyrazem) {
        this.etatyrazem = etatyrazem;
    }

    public double getEtatykobiety() {
        return etatykobiety;
    }

    public void setEtatykobiety(double etatykobiety) {
        this.etatykobiety = etatykobiety;
    }

    public double getEtatymezczyzni() {
        return etatymezczyzni;
    }

    public void setEtatymezczyzni(double etatymezczyzni) {
        this.etatymezczyzni = etatymezczyzni;
    }

    
    
    
    
}
