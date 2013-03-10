/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
@Named
public class UjeciePkpir implements Serializable {
    @Column(name = "mcujecia")
    private String mcujecia;
    @Column(name = "rokujecia")
    private Double rokujecia;
    @Column(name = "typoperacji")
    private Double typoperacji;
    @Column(name = "kwota")
    private Double kwota;
    @Column(name = "dorozliczenia")
    private Double dorozliczenia;

    public UjeciePkpir() {
    }

    public UjeciePkpir(String mcujecia, Double rokujecia, Double typoperacji, Double kwota, Double dorozliczenia) {
        this.mcujecia = mcujecia;
        this.rokujecia = rokujecia;
        this.typoperacji = typoperacji;
        this.kwota = kwota;
        this.dorozliczenia = dorozliczenia;
    }

   
}
