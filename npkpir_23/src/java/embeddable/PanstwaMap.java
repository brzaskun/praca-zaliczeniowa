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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.Embeddable;
/**
 *
 * @author Osito
 */
@Named
@Embeddable
@ApplicationScoped
public class PanstwaMap implements Serializable {

//szukanie po nazwie
private static Map<String,String> wykazPanstwSX;
//szukanie po symbolu
private static Map<String,String> wykazPanstwXS;

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
    wykazPanstwXS = new ConcurrentHashMap<>();
}


@PostConstruct
    public void init() { //E.m(this);
        List<String> panstwa = Collections.synchronizedList(new ArrayList<>());
        List<String> symbole = Collections.synchronizedList(new ArrayList<>());
        panstwa.addAll(Panstwa.getWykazPanstw());
        symbole.addAll(PanstwaSymb.getWykazPanstwS());
        Iterator it;
        it = panstwa.iterator();
        Iterator itX;
        itX = symbole.iterator();
        while(it.hasNext()&&itX.hasNext()){
            String panstwo = it.next().toString();
            String symbol = itX.next().toString();
            wykazPanstwSX.put(panstwo,symbol);
            wykazPanstwXS.put(symbol, panstwo);
        }

    }

    public static  Map<String, String> getWykazPanstwSX() {
        return wykazPanstwSX;
    }

    public static  void setWykazPanstwS(Map<String, String> wykazPanstwSX) {
        PanstwaMap.wykazPanstwSX = wykazPanstwSX;
    }

    public static Map<String, String> getWykazPanstwXS() {
        return wykazPanstwXS;
    }

    public static void setWykazPanstwXS(Map<String, String> wykazPanstwXS) {
        PanstwaMap.wykazPanstwXS = wykazPanstwXS;
    }

    
    
}
