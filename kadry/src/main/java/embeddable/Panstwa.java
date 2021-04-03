/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Panstwa implements Serializable {
     private static final long serialVersionUID = 1L;
     
     private Map<String, String> listapanstwa;
    
     @PostConstruct
     private void init() { 
         listapanstwa = new HashMap<>();
         listapanstwa.put("1", "Polska");
         listapanstwa.put("2", "Niemcy");
         listapanstwa.put("3", "Ukraina");
         listapanstwa.put("4", "Tajlandia");
         listapanstwa.put("5", "Białoruś");
         listapanstwa.put("6", "Rosja");
         listapanstwa.put("7", "Rumunia");
     }

    public Map<String, String> getListapanstwa() {
        return listapanstwa;
    }

    
     
     
}
