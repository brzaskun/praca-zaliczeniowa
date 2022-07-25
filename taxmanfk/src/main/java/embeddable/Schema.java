/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddable;

import java.io.Serializable;
import java.util.Objects;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
@Named
public class Schema implements Serializable{
    private static final long serialVersionUID = 1L;
   
    private String okres;
    private String rok;
    private String mc;
    private String kw;
    private String nazwaschemy;
    private String wstep;
    private String pouczenie;

    public Schema() {
    }

    public Schema(String okres, String rok, String mc, String kw, String nazwaschemy, String wstep, String pouczenie) {
        this.okres = okres;
        this.rok = rok;
        this.mc = mc;
        this.kw = kw;
        this.nazwaschemy = nazwaschemy;
        this.wstep = wstep;
        this.pouczenie = pouczenie;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.okres);
        hash = 29 * hash + Objects.hashCode(this.rok);
        hash = 29 * hash + Objects.hashCode(this.nazwaschemy);
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
        final Schema other = (Schema) obj;
        if (this.okres == null ? other.okres != null : !this.okres.equals(other.okres)) {
            return false;
        }
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.nazwaschemy, other.nazwaschemy)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Schema{" + "okres=" + okres + ", rok=" + rok + ", mc=" + mc + ", kw=" + kw + ", nazwaschemy=" + nazwaschemy + ", wstep=" + wstep + '}';
    }

    public String getOkres() {
        return okres;
    }

    public void setOkres(String okres) {
        this.okres = okres;
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

    public String getKw() {
        return kw;
    }

    public void setKw(String kw) {
        this.kw = kw;
    }

    public String getNazwaschemy() {
        return nazwaschemy;
    }

    public void setNazwaschemy(String nazwaschemy) {
        this.nazwaschemy = nazwaschemy;
    }

    public String getWstep() {
        return wstep;
    }

    public void setWstep(String wstep) {
        this.wstep = wstep;
    }

    public String getPouczenie() {
        return pouczenie;
    }

    public void setPouczenie(String pouczenie) {
        this.pouczenie = pouczenie;
    }

}
