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
        rokiList = new ArrayList<Integer>();
 //       rokiList.add(new Integer(2012));
        rokiList.add(new Integer(2014));
        rokiList.add(new Integer(2013));
//        rokiList.add(new Integer(2015));
//        rokiList.add(new Integer(2016));
//        rokiList.add(new Integer(2017));
//        rokiList.add(new Integer(2018));
//        rokiList.add(new Integer(2019));
//        rokiList.add(new Integer(2020));
//        rokiList.add(new Integer(2021));
//        rokiList.add(new Integer(2022));
//        rokiList.add(new Integer(2023));
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
    
}
