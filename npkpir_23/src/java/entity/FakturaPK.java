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
public class FakturaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String wystawcanazwa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 126)
    @Column(nullable = false, length = 126)
    private String numerkolejny;

    public FakturaPK() {
    }

    public FakturaPK(String numerkolejny, String wystawcanazwa) {
        this.wystawcanazwa = wystawcanazwa;
        this.numerkolejny = numerkolejny;
    }

    public String getWystawcanazwa() {
        return wystawcanazwa;
    }

    public void setWystawcanazwa(String wystawcanazwa) {
        this.wystawcanazwa = wystawcanazwa;
    }

    public String getNumerkolejny() {
        return numerkolejny;
    }

    public void setNumerkolejny(String numerkolejny) {
        this.numerkolejny = numerkolejny;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wystawcanazwa != null ? wystawcanazwa.hashCode() : 0);
        hash += (numerkolejny != null ? numerkolejny.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FakturaPK)) {
            return false;
        }
        FakturaPK other = (FakturaPK) object;
        if ((this.wystawcanazwa == null && other.wystawcanazwa != null) || (this.wystawcanazwa != null && !this.wystawcanazwa.equals(other.wystawcanazwa))) {
            return false;
        }
        if ((this.numerkolejny == null && other.numerkolejny != null) || (this.numerkolejny != null && !this.numerkolejny.equals(other.numerkolejny))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FakturaPK[ wystawcanazwa=" + wystawcanazwa + ", numerkolejny=" + numerkolejny + " ]";
    }
    
}
