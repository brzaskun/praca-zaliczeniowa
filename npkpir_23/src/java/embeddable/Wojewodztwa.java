/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@SessionScoped
public class Wojewodztwa implements Serializable {
    private final static List<String> wykaz;
    
    static{
        wykaz = Collections.synchronizedList(new ArrayList<>());
        wykaz.add("zachodniopomorskie");
        wykaz.add("dolnośląskie");
        wykaz.add("kujawsko-omorskie");
        wykaz.add("lubelskie");
        wykaz.add("lubuskie");
        wykaz.add("łódzkie");
        wykaz.add("małopolskie");
        wykaz.add("mazowieckie");
        wykaz.add("opolskie");
        wykaz.add("podkarpackie");
        wykaz.add("podlaskie");
        wykaz.add("pomorskie");
        wykaz.add("śląskie");
        wykaz.add("świętokrzyskie");
        wykaz.add("warmińsko-mazurskie");
        wykaz.add("wielkopolskie");
    }

    public static List<String> getWykaz() {
        return wykaz;
    }
    
    public  List<String> getWykazS() {
        return wykaz;
    }
    
}
