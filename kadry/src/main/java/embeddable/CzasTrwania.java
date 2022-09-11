/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class CzasTrwania implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final Map<Integer,String> listaczastrwania;
    
    static {
        listaczastrwania = new HashMap<>();
        listaczastrwania.put(0,"czas próbny");
        listaczastrwania.put(1,"czas określony");
        listaczastrwania.put(2,"czas nieokreślony");
        listaczastrwania.put(3,"umowa zlecenia");
    }

    public static Map<Integer, String> getListaczastrwania() {
        return listaczastrwania;
    }

    public static List<String> getListaczastrwaniaS() {
        return new ArrayList<>(listaczastrwania.values());
    }

    public static int find(String czastrwania) {
        int zwrot = 0;
        for (int i : listaczastrwania.keySet()) {
            if (listaczastrwania.get(i).equals(czastrwania)) {
                zwrot = i;
            }
        }
        return zwrot;
    }
    
    
}
