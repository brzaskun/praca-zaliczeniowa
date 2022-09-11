/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Osito
 */
public class Okres implements Serializable{
    private static final long serialVersionUID = 1L;
    @NotNull
    private String rok;
    @NotNull
    private String mc;

    public Okres(String rok, String mc) {
        this.rok = rok;
        this.mc = mc;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.rok);
        hash = 59 * hash + Objects.hashCode(this.mc);
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
        final Okres other = (Okres) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.mc, other.mc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rok + mc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
    
    
    
}
