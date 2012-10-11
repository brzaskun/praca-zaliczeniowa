/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
public class Umorzenie implements Serializable{
    private Integer nrUmorzenia;
    private Integer rokUmorzenia;
    private Integer mcUmorzenia;
    private BigDecimal kwota;

    public Integer getNrUmorzenia() {
        return nrUmorzenia;
    }

    public void setNrUmorzenia(Integer nrUmorzenia) {
        this.nrUmorzenia = nrUmorzenia;
    }

    public Integer getRokUmorzenia() {
        return rokUmorzenia;
    }

    public void setRokUmorzenia(Integer rokUmorzenia) {
        this.rokUmorzenia = rokUmorzenia;
    }

    public Integer getMcUmorzenia() {
        return mcUmorzenia;
    }

    public void setMcUmorzenia(Integer mcUmorzenia) {
        this.mcUmorzenia = mcUmorzenia;
    }

    public BigDecimal getKwota() {
        return kwota;
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }
    
}
