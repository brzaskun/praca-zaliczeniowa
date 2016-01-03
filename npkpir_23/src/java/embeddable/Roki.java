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
@ManagedBean(name="Roki")
@SessionScoped
public class Roki implements Serializable{

    private static final List<Integer> rokiList;

    static{
        rokiList = new ArrayList<>();
        rokiList.add(2016);
        rokiList.add(2015);
        rokiList.add(2014);
        rokiList.add(2013);
    }
    
    public static List<Integer> getRokiListS() {
        return rokiList;
    }

    public Roki() {
    }
    
    public List<Integer> getRokiList() {
        return rokiList;
    }
    
    public List<Integer> RokiListM(Integer biezacyrok) {
        List<Integer> listalat = new ArrayList<>();
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
