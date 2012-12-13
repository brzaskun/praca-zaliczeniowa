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
public class Rozrachunek implements Serializable {
    @Column(name = "dataplatnosci")
    private String dataplatnosci;
    @Column(name = "kwotawplacona")
    private Double kwotawplacona;
    @Column(name = "dorozliczenia")
    private Double dorozliczenia;

    public Rozrachunek() {
    }

    
    public Rozrachunek(String dataplatnosci, Double kwotawplacona, Double dorozliczenia) {
        this.dataplatnosci = dataplatnosci;
        this.kwotawplacona = kwotawplacona;
        this.dorozliczenia = dorozliczenia;
    }

    
    public String getDataplatnosci() {
        return dataplatnosci;
    }

    public void setDataplatnosci(String dataplatnosci) {
        this.dataplatnosci = dataplatnosci;
    }

    public Double getKwotawplacona() {
        return kwotawplacona;
    }

    public void setKwotawplacona(Double kwotawplacona) {
        this.kwotawplacona = kwotawplacona;
    }

    public Double getDorozliczenia() {
        return dorozliczenia;
    }

    public void setDorozliczenia(Double dorozliczenia) {
        this.dorozliczenia = dorozliczenia;
    }
   
    
}
