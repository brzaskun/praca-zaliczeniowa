/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EVDAO;
import embeddable.EVidencja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name="EVatView")
@SessionScoped
public class EVatView implements Serializable{
    @Inject
    private EVDAO eVDAO;
    
    private static final List<String> naglowekVList;
    private List<String> sprzedazVList;
    private List<String> zakupVList;
    private List<String> srodkitrwaleVList;

    static{
        naglowekVList = new ArrayList<String>();
        naglowekVList.add("rodzaj ewidencji");
        naglowekVList.add("netto");
        naglowekVList.add("vat");
   }

    public EVatView() {
        zakupVList = new ArrayList<String>();
        srodkitrwaleVList = new ArrayList<String>();
        sprzedazVList = new ArrayList<String>();
    }
    
    
    
    @PostConstruct
    public void init(){
        ArrayList<EVidencja> listadostepnychewidencji = (ArrayList<EVidencja>) EVDAO.getWykazEwidencji();
        Iterator it;
        it = listadostepnychewidencji.iterator();
        while (it.hasNext()){
            EVidencja up = (EVidencja) it.next();
            if(up.getTransAkcja().equals("zakup")){
                zakupVList.add(up.getNazwaEwidencji());
            } else if (up.getTransAkcja().equals("srodek trw")) {
                srodkitrwaleVList.add(up.getNazwaEwidencji());
            } else {
                sprzedazVList.add(up.getNazwaEwidencji());
            }
            
        }
        
    }
    
    public List<String> getNaglowekVList() {
        return naglowekVList;
    }

    public List<String> getSprzedazVList() {
        return sprzedazVList;
    }

    public List<String> getZakupVList() {
        return zakupVList;
    }

    public List<String> getSrodkitrwaleVList() {
        return srodkitrwaleVList;
    }
    
   
}
