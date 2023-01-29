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
    private String rokmc;
    private int nrkolejny;

    public Okres(String rok, String mc) {
        this.rok = rok;
        this.mc = mc;
        this.rokmc = rok+mc;
    }
    
    public Okres(String rok, String mc, int lp) {
        this.rok = rok;
        this.mc = mc;
        this.rokmc = rok+mc;
        this.nrkolejny = lp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.rokmc);
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
        if (!Objects.equals(this.rokmc, other.rokmc)) {
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

    public String getRokmc() {
        return rokmc;
    }

    public void setRokmc(String rokmc) {
        this.rokmc = rokmc;
    }

    public int getNrkolejny() {
        return nrkolejny;
    }

    public void setNrkolejny(int nrkolejny) {
        this.nrkolejny = nrkolejny;
    }
    
    
    
}
