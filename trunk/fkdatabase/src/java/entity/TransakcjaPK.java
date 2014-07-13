/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class TransakcjaPK implements Serializable{
    
    private String rozliczajacyId;
    private String rozliczanyId;

    public TransakcjaPK() {
    }
    
    

    public TransakcjaPK(String rozliczajacyId, String rozliczanyId) {
        this.rozliczajacyId = rozliczajacyId;
        this.rozliczanyId = rozliczanyId;
    }
    

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rozliczajacyId);
        hash = 97 * hash + Objects.hashCode(this.rozliczanyId);
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
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + rozliczajacyId + ", rozliczanyId=" + rozliczanyId + '}';
    }

    
    
}
