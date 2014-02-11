/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import embeddable.VatUe;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author Osito
 */
@Entity
public class Vatuepodatnik implements Serializable{
    @EmbeddedId
    private VatuepodatnikPK vatuepodatnikPK;
    private boolean mc0kw1;
    private List<VatUe> klienciWDTWNT;
    private boolean rozliczone;

    public VatuepodatnikPK getVatuepodatnikPK() {
        return vatuepodatnikPK;
    }

    public void setVatuepodatnikPK(VatuepodatnikPK vatuepodatnikPK) {
        this.vatuepodatnikPK = vatuepodatnikPK;
    }

    public boolean isMc0kw1() {
        return mc0kw1;
    }

    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }

    public List<VatUe> getKlienciWDTWNT() {
        return klienciWDTWNT;
    }

    public void setKlienciWDTWNT(List<VatUe> klienciWDTWNT) {
        this.klienciWDTWNT = klienciWDTWNT;
    }

    public boolean isRozliczone() {
        return rozliczone;
    }

    public void setRozliczone(boolean rozliczone) {
        this.rozliczone = rozliczone;
    }
    
    
}
