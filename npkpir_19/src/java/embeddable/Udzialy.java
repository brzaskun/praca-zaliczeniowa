/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Udzialy implements Serializable{
    private String mcOd;
    private String rokOd;
    private String mcDo;
    private String rokDo;
    private String nazwiskoimie;
    private String nip;
    private String udzial;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.nazwiskoimie);
        hash = 29 * hash + Objects.hashCode(this.nip);
        hash = 29 * hash + Objects.hashCode(this.udzial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Udzialy other = (Udzialy) obj;
        if (!Objects.equals(this.mcOd, other.mcOd)) {
            return false;
        }
        if (!Objects.equals(this.rokOd, other.rokOd)) {
            return false;
        }
        if (!Objects.equals(this.mcDo, other.mcDo)) {
            return false;
        }
        if (!Objects.equals(this.rokDo, other.rokDo)) {
            return false;
        }
        if (!Objects.equals(this.nazwiskoimie, other.nazwiskoimie)) {
            return false;
        }
        if (!Objects.equals(this.nip, other.nip)) {
            return false;
        }
        if (!Objects.equals(this.udzial, other.udzial)) {
            return false;
        }
        return true;
    }

    
    
    public String getMcOd() {
        return mcOd;
    }

    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }

    public String getRokOd() {
        return rokOd;
    }

    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }

    public String getMcDo() {
        return mcDo;
    }

    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }

    public String getRokDo() {
        return rokDo;
    }

    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }

    public String getNazwiskoimie() {
        return nazwiskoimie;
    }

    public void setNazwiskoimie(String nazwiskoimie) {
        this.nazwiskoimie = nazwiskoimie;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getUdzial() {
        return udzial;
    }

    public void setUdzial(String udzial) {
        this.udzial = udzial;
    }

   
    
}
