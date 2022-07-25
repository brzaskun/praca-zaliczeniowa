/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class ZobowiazaniePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mc")
    private String mc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rok")
    private String rok;

    public ZobowiazaniePK() {
    }

    public ZobowiazaniePK(String mc, String rok) {
        this.mc = mc;
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public String getRokMc() {
        return this.rok+this.mc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mc != null ? mc.hashCode() : 0);
        hash += (rok != null ? rok.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZobowiazaniePK)) {
            return false;
        }
        ZobowiazaniePK other = (ZobowiazaniePK) object;
        if ((this.mc == null && other.mc != null) || (this.mc != null && !this.mc.equals(other.mc))) {
            return false;
        }
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ZobowiazaniePK[ mc=" + mc + ", rok=" + rok + " ]";
    }
    
}
