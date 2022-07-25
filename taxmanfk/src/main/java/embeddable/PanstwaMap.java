/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.SessionScoped;
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
    List<String> panstwa = Panstwa.getWykazPanstw();
    List<String> symbole = PanstwaSymb.getWykazPanstwS();
    Iterator it = panstwa.iterator();
    Iterator itX = symbole.iterator();
    while(it.hasNext()&&itX.hasNext()){
        String panstwo = it.next().toString();
        String symbol = itX.next().toString();
        wykazPanstwSX.put(panstwo,symbol);
        wykazPanstwXS.put(symbol, panstwo);
    }
}
    /**
    * @param  podaj nazwe panstwa
    * @return  zwraca symbol ponastwa
    */
    public static  Map<String, String> getWykazPanstwSX() {
        return wykazPanstwSX;
    }

    public static  void setWykazPanstwS(Map<String, String> wykazPanstwSX) {
        PanstwaMap.wykazPanstwSX = wykazPanstwSX;
    }

    /**
    * @param  podaj symbolp anstwa
    * @return  zwraca nazwe ponastwa
    */
    public static Map<String, String> getWykazPanstwXS() {
        return wykazPanstwXS;
    }

    public static void setWykazPanstwXS(Map<String, String> wykazPanstwXS) {
        PanstwaMap.wykazPanstwXS = wykazPanstwXS;
    }

    
    
}
