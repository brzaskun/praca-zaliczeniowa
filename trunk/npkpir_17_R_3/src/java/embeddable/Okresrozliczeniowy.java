/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
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
    
    
}
