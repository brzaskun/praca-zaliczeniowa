/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="WpisSet")
@SessionScoped
public class WpisSet implements Serializable{

    private static Pod podatnikWpisu;
    private static Integer rokWpisu;
    private static Integer miesiacWpisu;

    public Pod getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public void setPodatnikWpisu(Pod podatnikWpisu) {
        WpisSet.podatnikWpisu = podatnikWpisu;
    }
    
    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        WpisSet.rokWpisu = rokWpisu;
    }

    public Integer getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(Integer miesiacWpisu) {
        WpisSet.miesiacWpisu = miesiacWpisu;
    }

    
    
}
