/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Roki implements Serializable{

    private static final List<Integer> rokiList;
    private static final List<String> rokiListS;

    static{
        rokiList = Collections.synchronizedList(new ArrayList<>());
        rokiList.add(2021);
        rokiList.add(2020);
        rokiList.add(2019);
        rokiList.add(2018);
        rokiList.add(2017);
        rokiList.add(2016);
        rokiList.add(2015);
        rokiList.add(2014);
        rokiList.add(2013);
        rokiListS = Collections.synchronizedList(new ArrayList<>());
        rokiListS.add("2021");
        rokiListS.add("2020");
        rokiListS.add("2019");
        rokiListS.add("2018");
        rokiListS.add("2017");
        rokiListS.add("2016");
        rokiListS.add("2015");
        rokiListS.add("2014");
        rokiListS.add("2013");
    }
    
    public static List<Integer> getRokiListS() {
        return rokiList;
    }
    
    public static List<String> getRokiListStr() {
        return rokiListS;
    }

    public Roki() {
    }
    
    public List<Integer> getRokiList() {
        return rokiList;
    }
    
    public List<Integer> getRokiListMinusJeden() {
        List<Integer> rokimniej = Collections.synchronizedList(new ArrayList<>());
        rokimniej.addAll(rokiList);
        if (rokimniej.size() > 0) {
            rokimniej.remove(0);
        }
        return rokimniej;
    }
    
    public List<String> getRokiListString() {
        return rokiListS;
    }
    
    public List<Integer> RokiListM(Integer biezacyrok) {
        List<Integer> listalat = Collections.synchronizedList(new ArrayList<>());
        for (Integer p : rokiList) {
            if (p >= biezacyrok) {
                listalat.add(p);
            }
        }
        return listalat;
    }
    
    public static String rokPoprzedni(String rok) {
        int nowyrok = Integer.parseInt(rok)-1;
        String zwrot = String.valueOf(nowyrok);
        return zwrot;
    }
    
}
