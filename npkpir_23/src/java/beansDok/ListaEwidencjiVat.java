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
    private List<Evewidencja> sprzedazVList;
    private List<Evewidencja> zakupVList;
    private List<Evewidencja> srodkitrwaleVList;
    private List<Evewidencja> wdtVList;
    private List<Evewidencja> wntVList;
    private List<Evewidencja> rvcVList;//reverse charge - odwrotne obciazenie
    private List<Evewidencja> rvcVListS;//reverse charge - odwrotne obciazenie sprzedawca
    private List<Evewidencja> importuslugList;
    private List<Evewidencja> uslugiPTK;
    private List<Evewidencja> eksporttowarow;
    private List<Evewidencja> listadostepnychewidencji;


    public ListaEwidencjiVat() {
        //kategorie do generowania
        zakupVList = new ArrayList<>();
        srodkitrwaleVList = new ArrayList<>();
        sprzedazVList = new ArrayList<>();
        wdtVList = new ArrayList<>();
        wntVList = new ArrayList<>();
        rvcVList = new ArrayList<>();
        rvcVListS = new ArrayList<>();
        importuslugList = new ArrayList<>();
        uslugiPTK = new ArrayList<>();
        eksporttowarow = new ArrayList<>();
        //pojemnik na wszytskie ewidencje z EVDAO
        listadostepnychewidencji = new ArrayList<>();
    }
    
    
    //po pobraniu ewidencji z EVDAO podkleja je pod trzy kategorie ewidencji w celu ich wygenerowania programowego
    @PostConstruct
    public void init(){
        List<Evewidencja> tmp = eVDAO.findAll();
        Collections.sort(tmp, new Evewidencjacomparator());
        for (Evewidencja up : tmp){
            listadostepnychewidencji.add(up);
            switch(up.getTransakcja()) {
                case "zakup" : 
                    zakupVList.add(up);
                    break;
                case "srodek trw" : 
                    srodkitrwaleVList.add(up);
                    break;
                case "inwestycja" : 
                    srodkitrwaleVList.add(up);
                    break;
                case "WDT" : 
                    wdtVList.add(up);
                    break;
                case "WNT" : 
                    wntVList.add(up);
                    break;
                case "odwrotne obciążenie" : 
                    rvcVList.add(up);
                    break;
                case ("odwrotne obciążenie sprzedawca"):
                    rvcVListS.add(up);
                    break;
                case "import uslug" : 
                    importuslugList.add(up);
                    break;
                case "import usług" : 
                    importuslugList.add(up);
                    break;
                case "usługi poza ter." :
                    uslugiPTK.add(up);
                    break;
                case "eksport towarów" :
                    eksporttowarow.add(up);
                    break;
                default : 
                    sprzedazVList.add(up);
            }
        }

    }
    
    public List<Evewidencja> pobierzEvewidencje(String transakcjiRodzaj) {
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
                    case ("odwrotne obciążenie sprzedawca"):
                        return rvcVListS;
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
    
    public List<Evewidencja> getSprzedazVList() {
        return sprzedazVList;
    }

    public void setSprzedazVList(List<Evewidencja> sprzedazVList) {
        this.sprzedazVList = sprzedazVList;
    }

    public List<Evewidencja> getZakupVList() {
        return zakupVList;
    }

    public void setZakupVList(List<Evewidencja> zakupVList) {
        this.zakupVList = zakupVList;
    }

    public List<Evewidencja> getSrodkitrwaleVList() {
        return srodkitrwaleVList;
    }

    public void setSrodkitrwaleVList(List<Evewidencja> srodkitrwaleVList) {
        this.srodkitrwaleVList = srodkitrwaleVList;
    }

    public List<Evewidencja> getWdtVList() {
        return wdtVList;
    }

    public void setWdtVList(List<Evewidencja> wdtVList) {
        this.wdtVList = wdtVList;
    }

    public List<Evewidencja> getWntVList() {
        return wntVList;
    }

    public void setWntVList(List<Evewidencja> wntVList) {
        this.wntVList = wntVList;
    }

    public List<Evewidencja> getRvcVList() {
        return rvcVList;
    }

    public void setRvcVList(List<Evewidencja> rvcVList) {
        this.rvcVList = rvcVList;
    }

    public List<Evewidencja> getRvcVListS() {
        return rvcVListS;
    }

    public void setRvcVListS(List<Evewidencja> rvcVListS) {
        this.rvcVListS = rvcVListS;
    }

    public List<Evewidencja> getImportuslugList() {
        return importuslugList;
    }

    public void setImportuslugList(List<Evewidencja> importuslugList) {
        this.importuslugList = importuslugList;
    }

    public List<Evewidencja> getUslugiPTK() {
        return uslugiPTK;
    }

    public void setUslugiPTK(List<Evewidencja> uslugiPTK) {
        this.uslugiPTK = uslugiPTK;
    }

    public List<Evewidencja> getEksporttowarow() {
        return eksporttowarow;
    }

    public void setEksporttowarow(List<Evewidencja> eksporttowarow) {
        this.eksporttowarow = eksporttowarow;
    }

    public List<Evewidencja> getListadostepnychewidencji() {
        return listadostepnychewidencji;
    }

    public void setListadostepnychewidencji(List<Evewidencja> listadostepnychewidencji) {
        this.listadostepnychewidencji = listadostepnychewidencji;
    }
   
   //</editor-fold>
}
