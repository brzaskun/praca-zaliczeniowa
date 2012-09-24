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

    private static Pod podatnikWpisu;
    private static Integer rokWpisu;
    private static String miesiacWpisu;
    private static Uz wprowadzil;
   
   
   
    public WpisView() {
    }

    public Pod getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public void setPodatnikWpisu(Pod podatnikWpisu) {
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

   
}
