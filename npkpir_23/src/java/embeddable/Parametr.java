/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.ParamSuper;
import error.E;
import java.io.Serializable;
import java.util.List;
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

    public static Parametr wyluskajParametr(List<Parametr> lista, String mc, String rok) {
        Parametr par = null;
        if (lista != null && lista.size() > 0) {
            for (Parametr p : lista) {
                Integer mcI = Mce.getMiesiacToNumber().get(mc);
                Integer rokI = Integer.parseInt(rok);
                Integer rokOdI = Integer.parseInt(p.getRokOd());
                Integer mcOdI = Integer.parseInt(p.getMcOd());
                Integer mcDoI = 0;
                Integer rokDoI = 0;
                if (p.getMcDo() != null && p.getRokDo() != null && !p.getMcDo().equals("") && !p.getRokDo().equals("")) {
                    mcDoI = Integer.parseInt(p.getMcDo());
                    rokDoI = Integer.parseInt(p.getRokDo());
                }
                if (rokOdI <= rokI && rokDoI == 0) {
                        par = p;
                        break;
                } else if ((rokOdI <= rokI && rokDoI >= rokI)) {
                    if (mcOdI <= mcI && mcDoI >= mcI) {
                        par = p;
                        break;
                    }
                }
            }
        } else {
            System.out.println("Lista z parametrami nie zawiera zadnych elementów! Parametr.java wyluskajparametr");
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
