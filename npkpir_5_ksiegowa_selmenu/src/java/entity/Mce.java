/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

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

    private static final List<Integer> mceList;

    static{
        mceList = new ArrayList<Integer>();
        mceList.add(new Integer(1));
        mceList.add(new Integer(2));
        mceList.add(new Integer(3));
        mceList.add(new Integer(4));
        mceList.add(new Integer(5));
        mceList.add(new Integer(6));
        mceList.add(new Integer(7));
        mceList.add(new Integer(8));
        mceList.add(new Integer(9));
        mceList.add(new Integer(10));
        mceList.add(new Integer(11));
        mceList.add(new Integer(12));
    }
    
    public Mce() {
    }

    public List<Integer> getMceList() {
        return mceList;
    }
    
}
