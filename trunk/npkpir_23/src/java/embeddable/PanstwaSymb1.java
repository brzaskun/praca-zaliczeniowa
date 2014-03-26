/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
public class PanstwaSymb1 implements Serializable {

private static Map<String,String> wykazPanstwSX;

    public static void main(String[] args) {
        Map<String,String> wykazPanstwS = new HashMap<>();
        List<String> panstwa = new ArrayList<>();
        List<String> symbole = new ArrayList<>();
        panstwa.addAll(Panstwa.getWykazPanstw());
        symbole.addAll(PanstwaSymb.getWykazPanstwS());
        Iterator it;
        it = panstwa.iterator();
        Iterator itX;
        itX = symbole.iterator();
        while(it.hasNext()&&itX.hasNext()){
            
            try {
                wykazPanstwS.put(itX.next().toString(),it.next().toString());
            } catch (Exception e){
            }
        }
        
    }

public PanstwaSymb1(){
    wykazPanstwSX = new HashMap<>();
}


@PostConstruct
    public void init(){
        List<String> panstwa = new ArrayList<>();
        List<String> symbole = new ArrayList<>();
        panstwa.addAll(Panstwa.getWykazPanstw());
        symbole.addAll(PanstwaSymb.getWykazPanstwS());
        Iterator it;
        it = panstwa.iterator();
        Iterator itX;
        itX = symbole.iterator();
        while(it.hasNext()&&itX.hasNext()){
            wykazPanstwSX.put(it.next().toString(),itX.next().toString());
        }

    }

    public  Map<String, String> getWykazPanstwSX() {
        return wykazPanstwSX;
    }

    public  void setWykazPanstwS(Map<String, String> wykazPanstwSX) {
        PanstwaSymb1.wykazPanstwSX = wykazPanstwSX;
    }

    
    
}
