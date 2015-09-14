/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import comparator.Evewidencjacomparator;
import dao.EvewidencjaDAO;
import entity.Evewidencja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class ListaEwidencjiVat implements Serializable{
   private static final long serialVersionUID = 1L;
    private static final List<String> naglowekVList;
    static {
        naglowekVList = new ArrayList<String>();
        naglowekVList.add("rodzaj ewidencji");
        naglowekVList.add("netto");
        naglowekVList.add("vat");
        naglowekVList.add("brutto");
        naglowekVList.add("op/zw");
    }
    @Inject
    private EvewidencjaDAO eVDAO;
    private List<String> sprzedazVList;
    private List<String> zakupVList;
    private List<String> srodkitrwaleVList;
    private List<String> wdtVList;
    private List<String> wntVList;
    private List<String> rvcVList;//reverse charge - odwrotne obciazenie
    private List<String> importuslugList;
    private List<String> uslugiPTK;
    private List<String> eksporttowarow;
    private List<String> listadostepnychewidencji;


    public ListaEwidencjiVat() {
        //kategorie do generowania
        zakupVList = new ArrayList<>();
        srodkitrwaleVList = new ArrayList<>();
        sprzedazVList = new ArrayList<>();
        wdtVList = new ArrayList<>();
        wntVList = new ArrayList<>();
        rvcVList = new ArrayList<>();
        importuslugList = new ArrayList<>();
        uslugiPTK = new ArrayList<>();
        eksporttowarow = new ArrayList<>();
        //pojemnik na wszytskie ewidencje z EVDAO
        listadostepnychewidencji = new ArrayList<>();
    }
    
    
    //po pobraniu ewidencji z EVDAO podkleja je pod trzy kategorie ewidencji w celu ich wygenerowania programowego
    @PostConstruct
    public void init(){
        List<Evewidencja> tmp = new ArrayList<>();
        tmp.addAll(eVDAO.findAll());
        Collections.sort(tmp, new Evewidencjacomparator());
        for (Evewidencja up : tmp){
            listadostepnychewidencji.add(up.getNazwa());
            switch(up.getTransakcja()) {
                case "zakup" : 
                    zakupVList.add(up.getNazwa());
                    break;
                case "srodek trw" : 
                    srodkitrwaleVList.add(up.getNazwa());
                    break;
                case "inwestycja" : 
                    srodkitrwaleVList.add(up.getNazwa());
                    break;
                case "WDT" : 
                    wdtVList.add(up.getNazwa());
                    break;
                case "WNT" : 
                    wntVList.add(up.getNazwa());
                    break;
                case "odwrotne obciążenie" : 
                    rvcVList.add(up.getNazwa());
                    break;
                case "import uslug" : 
                    importuslugList.add(up.getNazwa());
                    break;
                case "import usług" : 
                    importuslugList.add(up.getNazwa());
                    break;
                case "usługi poza ter." :
                    uslugiPTK.add(up.getNazwa());
                    break;
                case "eksport towarów" :
                    eksporttowarow.add(up.getNazwa());
                    break;
                default : 
                    sprzedazVList.add(up.getNazwa());
            }
        }

    }
    
    public List<String> pobierzOpisyEwidencji(String transakcjiRodzaj) {
        switch (transakcjiRodzaj) {
                    case ("zakup"):
                        return zakupVList;
                    case ("srodek trw"):
                        return srodkitrwaleVList;
                    case ("srodek trw sprzedaz"):
                        return sprzedazVList;
                    case ("inwestycja"):
                        return srodkitrwaleVList;
                    case ("WDT"):
                        return wdtVList;
                    case ("odwrotne obciążenie"):
                        return rvcVList;
                    case ("WNT"):
                        return wntVList;
                    case ("import usług"):
                        return importuslugList;
                    case "usługi poza ter.":
                        return uslugiPTK;
                    case "eksport towarów":
                        return eksporttowarow;
                    default:
                        return sprzedazVList;
                }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
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

    public List<String> getRvcVList() {
        return rvcVList;
    }

    public void setRvcVList(List<String> rvcVList) {
        this.rvcVList = rvcVList;
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
    
    public List<String> getUslugiPTK() {
        return uslugiPTK;
    }
    
    public void setUslugiPTK(List<String> uslugiPTK) {
        this.uslugiPTK = uslugiPTK;
    }
    
    public List<String> getEksporttowarow() {
        return eksporttowarow;
    }
    
    public void setEksporttowarow(List<String> eksporttowarow) {
        this.eksporttowarow = eksporttowarow;
    }
    
    
    //</editor-fold>
   
   
}
