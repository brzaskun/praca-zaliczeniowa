    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    private Integer rozliczajacyPK;
    private Integer nowaTransakcjaPK;

    public TransakcjaPK() {
        this.id = -1;
    }
    
    

    public TransakcjaPK(Integer rozliczajacyId, Integer rozliczanyId) {
        this.id = -1;
        this.rozliczajacyPK = rozliczajacyId;
        this.nowaTransakcjaPK = rozliczanyId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    public Integer getRozliczajacyPK() {
        return rozliczajacyPK;
    }

    public void setRozliczajacyPK(Integer rozliczajacyPK) {
        this.rozliczajacyPK = rozliczajacyPK;
    }

    public Integer getNowaTransakcjaPK() {
        return nowaTransakcjaPK;
    }

    public void setNowaTransakcjaPK(Integer nowaTransakcjaPK) {
        this.nowaTransakcjaPK = nowaTransakcjaPK;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rozliczajacyPK);
        hash = 97 * hash + Objects.hashCode(this.nowaTransakcjaPK);
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
        if (!Objects.equals(this.rozliczajacyPK, other.rozliczajacyPK)) {
            return false;
        }
        if (!Objects.equals(this.nowaTransakcjaPK, other.nowaTransakcjaPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + rozliczajacyPK + ", rozliczanyId=" + nowaTransakcjaPK + '}';
    }

    
}
