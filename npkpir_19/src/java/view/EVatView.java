/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import entity.Evewidencja;
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
    private EvewidencjaDAO eVDAO;
   
    private static final List<String> naglowekVList;
    private List<String> sprzedazVList;
    private List<String> zakupVList;
    private List<String> srodkitrwaleVList;
    private List<String> wdtVList;
    private List<String> wntVList;
    private List<String> importuslugList;
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
        wdtVList = new ArrayList<>();
        wntVList = new ArrayList<>();
        importuslugList = new ArrayList<>();
        //pojemnik na wszytskie ewidencje z EVDAO
        listadostepnychewidencji = new ArrayList<>();
    }
    
    
    //po pobraniu ewidencji z EVDAO podkleja je pod trzy kategorie ewidencji w celu ich wygenerowania programowego
    @PostConstruct
    public void init(){
        ArrayList<Evewidencja> tmp =  (ArrayList<Evewidencja>) eVDAO.getDownloaded();
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()){
            Evewidencja up =  (Evewidencja) it.next();
            listadostepnychewidencji.add(up.getNazwa());
            switch(up.getTransakcja()) {
                case "zakup" : 
                    zakupVList.add(up.getNazwa());
                    break;
                case "srodek trw" : 
                    srodkitrwaleVList.add(up.getNazwa());
                    break;
                case "WDT" : 
                    wdtVList.add(up.getNazwa());
                    break;
                case "WNT" : 
                    wntVList.add(up.getNazwa());
                    break;
                case "import uslug" : 
                    importuslugList.add(up.getNazwa());
                    break;
                default : 
                    sprzedazVList.add(up.getNazwa());
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

    public List<String> getWdtVList() {
        return wdtVList;
    }

    public void setWdtVList(List<String> wdtVList) {
        this.wdtVList = wdtVList;
    }

    public List<String> getWntVList() {
        return wntVList;
    }

    public void setWntVList(List<String> wntVList) {
        this.wntVList = wntVList;
    }

    public List<String> getImportuslugList() {
        return importuslugList;
    }

    public void setImportuslugList(List<String> importuslugList) {
        this.importuslugList = importuslugList;
    }

    
   
   
}
