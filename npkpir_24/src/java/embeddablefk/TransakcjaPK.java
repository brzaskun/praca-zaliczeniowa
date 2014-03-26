/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Osito
 */
public class TransakcjaPK implements Serializable {
    private Rozrachunekfk rozliczany;
    private Rozrachunekfk sparowany;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.rozliczany);
        hash = 29 * hash + Objects.hashCode(this.sparowany);
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
        if (!Objects.equals(this.rozliczany, other.rozliczany)) {
            return false;
        }
        if (!Objects.equals(this.sparowany, other.sparowany)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczany=" + rozliczany + ", sparowany=" + sparowany + '}';
    }

    
    
    public Rozrachunekfk getRozliczany() {
        return rozliczany;
    }

    public void setRozliczany(Rozrachunekfk rozliczany) {
        this.rozliczany = rozliczany;
    }

    public Rozrachunekfk getSparowany() {
        return sparowany;
    }

    public void setSparowany(Rozrachunekfk sparowany) {
        this.sparowany = sparowany;
    }
    
    
}
