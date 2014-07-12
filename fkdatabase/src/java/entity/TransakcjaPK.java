/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Osito
 */
@Embeddable
public class TransakcjaPK implements Serializable{
    
    private String rozliczajacyId;
    private String rozliczanyId;
    private Integer numertransakcji;

    public String getRozliczajacyId() {
        return rozliczajacyId;
    }

    public void setRozliczajacyId(String rozliczajacyId) {
        this.rozliczajacyId = rozliczajacyId;
    }

    public String getRozliczanyId() {
        return rozliczanyId;
    }

    public void setRozliczanyId(String rozliczanyId) {
        this.rozliczanyId = rozliczanyId;
    }


    public Integer getNumertransakcji() {
        return numertransakcji;
    }

    public void setNumertransakcji(Integer numertransakcji) {
        this.numertransakcji = numertransakcji;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.rozliczajacyId);
        hash = 67 * hash + Objects.hashCode(this.rozliczanyId);
        hash = 67 * hash + Objects.hashCode(this.numertransakcji);
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
        final TransakcjaPK other = (TransakcjaPK) obj;
        if (!Objects.equals(this.rozliczajacyId, other.rozliczajacyId)) {
            return false;
        }
        if (!Objects.equals(this.rozliczanyId, other.rozliczanyId)) {
            return false;
        }
        if (!Objects.equals(this.numertransakcji, other.numertransakcji)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + rozliczajacyId + ", rozliczanyId=" + rozliczanyId + ", numertransakcji=" + numertransakcji + '}';
    }

    
}
