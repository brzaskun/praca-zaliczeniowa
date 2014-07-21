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
    
    private Integer rozliczajacy;
    private Integer nowaTransakcja;

    public TransakcjaPK() {
    }
    
    

    public TransakcjaPK(Integer rozliczajacyId, Integer rozliczanyId) {
        this.rozliczajacy = rozliczajacyId;
        this.nowaTransakcja = rozliczanyId;
    }
    

    public Integer getRozliczajacy() {
        return rozliczajacy;
    }

    public void setRozliczajacy(Integer rozliczajacy) {
        this.rozliczajacy = rozliczajacy;
    }

    public Integer getNowaTransakcja() {
        return nowaTransakcja;
    }

    public void setNowaTransakcja(Integer nowaTransakcja) {
        this.nowaTransakcja = nowaTransakcja;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rozliczajacy);
        hash = 97 * hash + Objects.hashCode(this.nowaTransakcja);
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
        if (!Objects.equals(this.rozliczajacy, other.rozliczajacy)) {
            return false;
        }
        if (!Objects.equals(this.nowaTransakcja, other.nowaTransakcja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + rozliczajacy + ", rozliczanyId=" + nowaTransakcja + '}';
    }

    
}
