/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Okresrozliczeniowy implements Serializable {
    private String rok;
    private String miesiac;
    private boolean zamkniety;
    private boolean edytuj;


    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getRok() {
        return rok;
    }
    
    public void setRok(String rok) {
        this.rok = rok;
    }
    
    public String getMiesiac() {
        return miesiac;
    }
    
    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }
    
    public boolean isZamkniety() {
        return zamkniety;
    }
    
    public void setZamkniety(boolean zamkniety) {
        this.zamkniety = zamkniety;
    }
    
    public boolean isEdytuj() {
        return edytuj;
    }
    
    public void setEdytuj(boolean edytuj) {
        this.edytuj = edytuj;
    }
    
    //</editor-fold>
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Okresrozliczeniowy other = (Okresrozliczeniowy) obj;
        if (!Objects.equals(this.rok, other.rok)) {
            return false;
        }
        if (!Objects.equals(this.miesiac, other.miesiac)) {
            return false;
        }
        return true;
    }
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.rok);
        hash = 89 * hash + Objects.hashCode(this.miesiac);
        hash = 89 * hash + (this.zamkniety ? 1 : 0);
        hash = 89 * hash + (this.edytuj ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Okresrozliczeniowy{" + "rok=" + rok + ", miesiac=" + miesiac + ", zamkniety=" + zamkniety + '}';
    }
   
    
    
}
