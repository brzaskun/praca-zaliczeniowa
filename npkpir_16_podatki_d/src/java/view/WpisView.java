/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.*;
import entity.Uz;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="WpisView")
@RequestScoped
public class WpisView implements Serializable{

    private static String podatnikWpisu;
    private static Integer rokWpisu;
    private static String miesiacWpisu;
    private static Uz wprowadzil;
    private static String miesiacOd;
    private static String miesiacDo;
    private boolean srodkTrw;

    public boolean isSrodkTrw() {
        return srodkTrw;
    }

    public void setSrodkTrw(boolean srodkTrw) {
        this.srodkTrw = srodkTrw;
    }
   
   
    public WpisView() {
    }

    public String getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public void setPodatnikWpisu(String podatnikWpisu) {
        WpisView.podatnikWpisu = podatnikWpisu;
    }

    public static void setPodatnikWpisuS(String podatnikWpisu) {
        WpisView.podatnikWpisu = podatnikWpisu;
    }
   
    
    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        WpisView.rokWpisu = rokWpisu;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        WpisView.miesiacWpisu = miesiacWpisu;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        WpisView.wprowadzil = wprowadzil;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        WpisView.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        WpisView.miesiacDo = miesiacDo;
    }

   
}
