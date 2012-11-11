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
    private List<String> listadostepnychewidencji;

    static{
        naglowekVList = new ArrayList<String>();
        naglowekVList.add("rodzaj ewidencji");
        naglowekVList.add("netto");
        naglowekVList.add("vat");
        naglowekVList.add("op/zw");
   }

    public EVatView() {
        //kategorie do generowania
        zakupVList = new ArrayList<>();
        srodkitrwaleVList = new ArrayList<>();
        sprzedazVList = new ArrayList<>();
        //pojemnik na wszytskie ewidencje z EVDAO
        listadostepnychewidencji = new ArrayList<>();
    }
    
    
    //po pobraniu ewidencji z EVDAO podkleja je pod trzy kategorie ewidencji w celu ich wygenerowania programowego
    @PostConstruct
    public void init(){
        ArrayList<EVidencja> tmp = (ArrayList<EVidencja>) EVDAO.getWykazEwidencji();
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()){
            EVidencja up = (EVidencja) it.next();
            listadostepnychewidencji.add(up.getNazwaEwidencji());
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

    public List<String> getListadostepnychewidencji() {
        return listadostepnychewidencji;
    }

    public void setListadostepnychewidencji(List<String> listadostepnychewidencji) {
        this.listadostepnychewidencji = listadostepnychewidencji;
    }
    
   
}
