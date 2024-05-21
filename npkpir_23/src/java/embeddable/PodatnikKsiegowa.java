/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class PodatnikKsiegowa implements Serializable{
        private static final long serialVersionUID = 1;
        private String nip;
        private String nazwa;
        private String ksiegowa;

    public PodatnikKsiegowa() {
    }

    public PodatnikKsiegowa(String brak_podatnika) {
        this.nazwa = brak_podatnika;
        this.nip = "";
        this.ksiegowa = "";
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKsiegowa() {
        return ksiegowa;
    }

    public void setKsiegowa(String ksiegowa) {
        this.ksiegowa = ksiegowa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.nip);
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
        final PodatnikKsiegowa other = (PodatnikKsiegowa) obj;
        return Objects.equals(this.nip, other.nip);
    }

    @Override
    public String toString() {
        return "PodatnikKsiegowa{" + "nip=" + nip + ", nazwa=" + nazwa + ", ksiegowa=" + ksiegowa + '}';
    }

        
    
}

