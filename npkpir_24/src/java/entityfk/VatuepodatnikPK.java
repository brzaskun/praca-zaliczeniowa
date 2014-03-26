/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

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
public class VatuepodatnikPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String rok;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String klient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String symbolokresu;

    public VatuepodatnikPK() {
    }

    public VatuepodatnikPK(String rok, String klient, String symbolokresu) {
        this.rok = rok;
        this.klient = klient;
        this.symbolokresu = symbolokresu;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public String getSymbolokresu() {
        return symbolokresu;
    }

    public void setSymbolokresu(String symbolokresu) {
        this.symbolokresu = symbolokresu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rok != null ? rok.hashCode() : 0);
        hash += (klient != null ? klient.hashCode() : 0);
        hash += (symbolokresu != null ? symbolokresu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VatuepodatnikPK)) {
            return false;
        }
        VatuepodatnikPK other = (VatuepodatnikPK) object;
        if ((this.rok == null && other.rok != null) || (this.rok != null && !this.rok.equals(other.rok))) {
            return false;
        }
        if ((this.klient == null && other.klient != null) || (this.klient != null && !this.klient.equals(other.klient))) {
            return false;
        }
        if ((this.symbolokresu == null && other.symbolokresu != null) || (this.symbolokresu != null && !this.symbolokresu.equals(other.symbolokresu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityfk.VatuepodatnikPK[ rok=" + rok + ", klient=" + klient + ", symbolokresu=" + symbolokresu + " ]";
    }
    
}
