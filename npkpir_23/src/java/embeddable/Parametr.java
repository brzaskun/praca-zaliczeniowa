/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.ParamSuper;
import error.E;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Parametr extends ParamSuper implements Serializable {
    private static final long serialVersionUID = -3861404301478825762L;
    
    private String mcOd;
    private String rokOd;
    private String mcDo;
    private String rokDo;
    private String parametr;

    public Parametr() {
    }

    public Parametr(String mcOd, String rokOd, String mcDo, String rokDo, String parametr) {
        this.mcOd = mcOd;
        this.rokOd = rokOd;
        this.mcDo = mcDo;
        this.rokDo = rokDo;
        this.parametr = parametr;
    }

    
    
    @Override
    public String toString() {
        return "Parametr{" + "mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", parametr=" + parametr + '}';
    }

   
   
//<editor-fold defaultstate="collapsed" desc="comment">

    public String getMcOd() {
        return mcOd;
    }

    public void setMcOd(String mcOd) {
        this.mcOd = mcOd;
    }

    public String getRokOd() {
        return rokOd;
    }

    public void setRokOd(String rokOd) {
        this.rokOd = rokOd;
    }

    public String getMcDo() {
        return mcDo;
    }

    public void setMcDo(String mcDo) {
        this.mcDo = mcDo;
    }

    public String getRokDo() {
        return rokDo;
    }

    public void setRokDo(String rokDo) {
        this.rokDo = rokDo;
    }

    public String getParametr() {
        return parametr;
    }
    
    public Double getParamentrNumb() {
        double zwrot = 0;
        try {
            parametr = parametr.replace(",",".");
            zwrot = Double.parseDouble(parametr);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public int getParamentrInt() {
        int zwrot = 0;
        try {
            zwrot = Integer.parseInt(parametr);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }

    public void setParametr(String parametr) {
        this.parametr = parametr;
    }

//</editor-fold>
}
