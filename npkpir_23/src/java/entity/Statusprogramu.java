/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "statusprogramu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Statusprogramu.findAll", query = "SELECT e FROM Statusprogramu e"),
})
public class Statusprogramu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lp")
    private int lp;
    @Size(max = 10)
    @Column(name = "dataod", nullable = true)
    private String dataod;
    @Size(max = 10)
    @Column(name = "datado", nullable = true)
    private String datado;
    @Column(name = "tekst")
    private String tekst;
    @Column(name = "aktywny")
    private boolean aktywny;
    @Column(name = "rodzajuzera")
    @NotNull(message = "Nie wprowadzono rodzaju użytkownika")
    private String rodzajuzera;

    public Statusprogramu() {
        this.dataod = null;
        this.datado = null;
    }


    public int getLp() {
        return lp;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public void setLp(int lp) {   
        this.lp = lp;
    }

    public String getRodzajuzera() {
        return rodzajuzera;
    }

    public void setRodzajuzera(String rodzajuzera) {
        this.rodzajuzera = rodzajuzera;
    }
    
    public String getDataod() {
        return dataod;
    }
    
    public void setDataod(String dataod) {
        this.dataod = dataod;
    }
    
    public String getDatado() {
        return datado;
    }
    
    public void setDatado(String datado) {
        this.datado = datado;
    }
    
    public String getTekst() {
        return tekst;
    }
    
    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
    
    public boolean isAktywny() {
        return aktywny;
    }
    
    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }
    
    
//</editor-fold>
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.lp);
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
        final Statusprogramu other = (Statusprogramu) obj;
        if (!Objects.equals(this.lp, other.lp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Statusprogramu{" + "dataod=" + dataod + ", datado=" + datado + ", tekst=" + tekst + ", aktywny=" + aktywny + '}';
    }
    
    
    
    
}
