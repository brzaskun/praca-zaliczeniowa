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
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Opis")
@SessionScoped
public class Opis implements Serializable{

    private List<String> opisList;

    
    public Opis() {
        opisList = new ArrayList<String>();
        opisList.add("lolo");
    }

    public List<String> getOpisList() {
        return opisList;
    }

    public void setOpisList(List<String> opisList) {
        this.opisList = opisList;
    }


    public void dodajOpis(ValueChangeEvent e){
        String ns = e.getNewValue().toString();
        if(opisList.size()>0&&ns.length()>5){
            if(!opisList.contains(ns)){
                opisList.add(ns);
            }
        }
    }
    
     public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();  
        String kl = new String();
         for(String p : getOpisList()) {  
            if(p.startsWith(query)) {
                 results.add(p);
             }
        }  
        return results;  
    }  
}
