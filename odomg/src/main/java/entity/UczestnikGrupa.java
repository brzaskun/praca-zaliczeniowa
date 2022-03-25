/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class UczestnikGrupa implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nazwa;
    private boolean jest;

    public UczestnikGrupa() {
    }

    
    public UczestnikGrupa(String nazwagrupy, boolean b) {
        this.nazwa = nazwagrupy;
        this.jest = b;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean isJest() {
        return jest;
    }

    public void setJest(boolean jest) {
        this.jest = jest;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.nazwa);
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
        final UczestnikGrupa other = (UczestnikGrupa) obj;
        if (!Objects.equals(this.nazwa, other.nazwa)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "UczestnikGrupa{" + "nazwa=" + nazwa + ", jest=" + jest + '}';
    }
    
    
    
}
