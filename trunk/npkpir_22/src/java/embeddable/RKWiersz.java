/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entityfk.Konto;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class RKWiersz implements Serializable{
    private int id;
    private String opis;
    private String Wn;
    private String Ma;
    private Konto konto;

    public RKWiersz() {
    }

    public RKWiersz(int id) {
        this.id = id;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getWn() {
        return Wn;
    }

    public void setWn(String Wn) {
        this.Wn = Wn;
    }

    public String getMa() {
        return Ma;
    }

    public void setMa(String Ma) {
        this.Ma = Ma;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final RKWiersz other = (RKWiersz) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
