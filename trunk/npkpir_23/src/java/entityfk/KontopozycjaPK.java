/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
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
public class KontopozycjaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255, name = "uklad")
    private int uklad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "kontoid", nullable = false, length = 255)
    private int kontoId;

    public KontopozycjaPK() {
    }

    public KontopozycjaPK(int uklad, int kontoId) {
        this.uklad = uklad;
        this.kontoId = kontoId;
    }

    
    public int getUklad() {
        return uklad;
    }

    public void setUklad(int uklad) {
        this.uklad = uklad;
    }
  

    public int getKontoId() {
        return kontoId;
    }

    public void setKontoId(int kontoId) {
        this.kontoId = kontoId;
    }

   
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.uklad);
        hash = 71 * hash + this.kontoId;
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
        final KontopozycjaPK other = (KontopozycjaPK) obj;
        if (this.uklad != other.uklad) {
            return false;
        }
        if (this.kontoId != other.kontoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KontopozycjaPK{" + "uklad=" + uklad + ", kontoId=" + kontoId + '}';
    }

  

    
}
