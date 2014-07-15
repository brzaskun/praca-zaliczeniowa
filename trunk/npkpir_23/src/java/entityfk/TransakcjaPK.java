    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class TransakcjaPK implements Serializable{
    
    private String stronaWnId;
    private String stronaMaId;

    public TransakcjaPK() {
    }
    
    

    public TransakcjaPK(String rozliczajacyId, String rozliczanyId) {
        this.stronaWnId = rozliczajacyId;
        this.stronaMaId = rozliczanyId;
    }
    

    public String getStronaWnId() {
        return stronaWnId;
    }

    public void setStronaWnId(String stronaWnId) {
        this.stronaWnId = stronaWnId;
    }

    public String getStronaMaId() {
        return stronaMaId;
    }

    public void setStronaMaId(String stronaMaId) {
        this.stronaMaId = stronaMaId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.stronaWnId);
        hash = 97 * hash + Objects.hashCode(this.stronaMaId);
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
        if (!Objects.equals(this.stronaWnId, other.stronaWnId)) {
            return false;
        }
        if (!Objects.equals(this.stronaMaId, other.stronaMaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + stronaWnId + ", rozliczanyId=" + stronaMaId + '}';
    }

    
    
}
