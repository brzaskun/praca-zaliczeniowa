/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class PanstwaMap implements Serializable {

private static Map<String,String> wykazPanstwSX;

//    public static void main(String[] args) {
//        Map<String,String> wykazPanstwS = new ConcurrentHashMap<>();
//        List<String> panstwa = Collections.synchronizedList(new ArrayList<>());
//        List<String> symbole = Collections.synchronizedList(new ArrayList<>());
//        panstwa.addAll(Panstwa.getWykazPanstw());
//        symbole.addAll(PanstwaSymb.getWykazPanstwS());
//        Iterator it;
//        it = panstwa.iterator();
//        Iterator itX;
//        itX = symbole.iterator();
//        while(it.hasNext()&&itX.hasNext()){
//            
//            try {
//                wykazPanstwS.put(itX.next().toString(),it.next().toString());
//            } catch (Exception e){
//            }
//        }
//        
//    }

public PanstwaMap(){
    wykazPanstwSX = new ConcurrentHashMap<>();
}


@PostConstruct
    public void init(){
        List<String> panstwa = Collections.synchronizedList(new ArrayList<>());
        List<String> symbole = Collections.synchronizedList(new ArrayList<>());
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
        PanstwaMap.wykazPanstwSX = wykazPanstwSX;
    }

    
    
}
