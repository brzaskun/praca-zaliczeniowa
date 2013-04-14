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
public class Stornodoch implements Serializable {
    @Column(name = "dataplatnosci")
    private String dataplatnosci;
    @Column(name = "kwotawplacona")
    private Double kwotawplacona;
    @Column(name = "dorozliczenia")
    private Double dorozliczenia;
    @Column(name = "ujetowstorno")
    private boolean ujetowstorno;

    public Stornodoch() {
    }

    
    public Stornodoch(String dataplatnosci, Double kwotawplacona, Double dorozliczenia, boolean ujetowstorno) {
        this.dataplatnosci = dataplatnosci;
        this.kwotawplacona = kwotawplacona;
        this.dorozliczenia = dorozliczenia;
        this.ujetowstorno = ujetowstorno;
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

    public boolean isUjetowstorno() {
        return ujetowstorno;
    }

    public void setUjetowstorno(boolean ujetowstorno) {
        this.ujetowstorno = ujetowstorno;
    }
   
    
}
