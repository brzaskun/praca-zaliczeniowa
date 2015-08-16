/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Parametr implements Serializable {
    private static final long serialVersionUID = -3861404301478825762L;
    
    private String mcOd;
    private String rokOd;
    private String mcDo;
    private String rokDo;
    private String parametr;

    @Override
    public String toString() {
        return "Parametr{" + "mcOd=" + mcOd + ", rokOd=" + rokOd + ", mcDo=" + mcDo + ", rokDo=" + rokDo + ", parametr=" + parametr + '}';
    }

    public static Parametr wyluskajParametr(List<Parametr> lista, String mc, String rok) {
        Parametr par = null;
        for (Parametr p : lista) {
            Integer mcI = Mce.getMiesiacToNumber().get(mc);
            Integer rokI = Integer.parseInt(rok);
            Integer rokOdI = Integer.parseInt(p.getRokOd());
            Integer mcOdI = Integer.parseInt(p.getMcOd());
            Integer mcDoI = 13;
            Integer rokDoI = 9999;
            if (p.getMcDo() != null && p.getRokDo() != null && !p.getMcDo().equals("") && !p.getRokDo().equals("")) {
                mcDoI = Integer.parseInt(p.getMcDo());
                rokDoI = Integer.parseInt(p.getRokDo());
            }
            if (rokOdI < rokI && rokDoI > rokI) {
                if (mcOdI <= mcI && mcDoI >= mcI) {
                    par = p;
                    break;
                }
            }
        }
        return par;
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

    public void setParametr(String parametr) {
        this.parametr = parametr;
    }

//</editor-fold>
}
