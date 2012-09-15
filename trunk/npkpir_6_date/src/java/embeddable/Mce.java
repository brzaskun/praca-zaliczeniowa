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
@ManagedBean(name="Mce")
@SessionScoped
public class Mce implements Serializable{

    private static final List<String> mceList;

    static{
        mceList = new ArrayList<String>();
        mceList.add("01");
        mceList.add("02");
        mceList.add("03");
        mceList.add("04");
        mceList.add("05");
        mceList.add("06");
        mceList.add("07");
        mceList.add("08");
        mceList.add("09");
        mceList.add("10");
        mceList.add("11");
        mceList.add("12");
        
    }
    
    public Mce() {
    }

    public List<String> getMceList() {
        return mceList;
    }
    
}
