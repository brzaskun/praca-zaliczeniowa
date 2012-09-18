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
@ManagedBean(name="Kolumna")
@SessionScoped
public class Kolmn implements Serializable{

    private static final List<String> kolumnList;
    private static final List<String> kolumnPrzychody;
    private static final List<String> kolumnKoszty;
    private static final List<String> kolumnST;

    static{
        kolumnList = new ArrayList<String>();
        kolumnList.add("przych. sprz");
        kolumnList.add("pozost. przych.");
        kolumnList.add("zakup tow.imat.");
        kolumnList.add("koszty ub.zak.");
        kolumnList.add("wynagrodzenia");
        kolumnList.add("inwestycje");
        kolumnList.add("uwagi");
        
        kolumnPrzychody = new ArrayList<String>();
        kolumnPrzychody.add("przych. sprz");
        kolumnPrzychody.add("pozost. przych.");
       
        kolumnKoszty = new ArrayList<String>();
        kolumnKoszty.add("zakup tow.imat.");
        kolumnKoszty.add("koszty ub.zak.");
        kolumnKoszty.add("wynagrodzenia");
        kolumnKoszty.add("poz. koszty");
        
        kolumnST = new ArrayList<String>();
        kolumnST.add("inwestycje");
        kolumnKoszty.add("uwagi");
       
    }
    
    public Kolmn() {
    }

    public List<String> getKolumnList() {
        return kolumnList;
    }

    public List<String> getKolumnPrzychody() {
        return kolumnPrzychody;
    }

    public List<String> getKolumnKoszty() {
        return kolumnKoszty;
    }

    public  List<String> getKolumnST() {
        return kolumnST;
    }
    
    
}
