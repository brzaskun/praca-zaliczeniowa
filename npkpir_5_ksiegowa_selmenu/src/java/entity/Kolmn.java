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
@ManagedBean(name="Kolumna")
@SessionScoped
public class Kolmn implements Serializable{

    private static final List<String> kolumnList;

    static{
        kolumnList = new ArrayList<String>();
        kolumnList.add("przych. sprz");
        kolumnList.add("pozost. przych.");
        kolumnList.add("zakup tow.imat.");
        kolumnList.add("koszty ub.zak.");
        kolumnList.add("wynagrodzenia");
        kolumnList.add("inwestycje");
        kolumnList.add("uwagi");
    }
    
    public Kolmn() {
    }

    public List<String> getKolumnList() {
        return kolumnList;
    }
    
}
