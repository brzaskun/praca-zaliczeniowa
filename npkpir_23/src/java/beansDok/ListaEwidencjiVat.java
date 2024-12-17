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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class ListaEwidencjiVat implements Serializable{
   
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
    private List<Evewidencja> importtowarowList;
    private List<Evewidencja> uslugiPTK;
    private List<Evewidencja> eksporttowarow;
    private List<Evewidencja> listadostepnychewidencji;
    //*****************************
    private List<Evewidencja> sprzedazNiemcyVList;
    private List<Evewidencja> sprzedazNiemcy13bVList;
    private List<Evewidencja> zakupNiemcyVList;
    private List<Evewidencja> wntNiemcyVList;
    private List<Evewidencja> wdtNiemcyVList;
    private List<Evewidencja> rvcNiemcyVList;//reverse charge - odwrotne obciazenie


    public ListaEwidencjiVat() {
        //kategorie do generowania
        zakupVList = Collections.synchronizedList(new ArrayList<>());
        srodkitrwaleVList = Collections.synchronizedList(new ArrayList<>());
        sprzedazVList = Collections.synchronizedList(new ArrayList<>());
        wdtVList = Collections.synchronizedList(new ArrayList<>());
        wntVList = Collections.synchronizedList(new ArrayList<>());
        rvcVList = Collections.synchronizedList(new ArrayList<>());
        rvcVListS = Collections.synchronizedList(new ArrayList<>());
        importuslugList = Collections.synchronizedList(new ArrayList<>());
        importtowarowList = Collections.synchronizedList(new ArrayList<>());
        uslugiPTK = Collections.synchronizedList(new ArrayList<>());
        eksporttowarow = Collections.synchronizedList(new ArrayList<>());
        //pojemnik na wszytskie ewidencje z EVDAO
        listadostepnychewidencji = Collections.synchronizedList(new ArrayList<>());
        sprzedazNiemcyVList = Collections.synchronizedList(new ArrayList<>());
        sprzedazNiemcy13bVList = Collections.synchronizedList(new ArrayList<>());
        zakupNiemcyVList = Collections.synchronizedList(new ArrayList<>());
        wntNiemcyVList = Collections.synchronizedList(new ArrayList<>());
        wdtNiemcyVList = Collections.synchronizedList(new ArrayList<>());
        rvcNiemcyVList = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    //po pobraniu ewidencji z EVDAO podkleja je pod trzy kategorie ewidencji w celu ich wygenerowania programowego
    @PostConstruct
    public void init() { //E.m(this);
        List<Evewidencja> wszystkie = eVDAO.findAll();
        List<Evewidencja> tylkoPolskie = wszystkie.stream().filter(p->p.isNiemcy()==false).collect(Collectors.toList());
        wszystkie.removeAll(tylkoPolskie);
        List<Evewidencja> tylkoNiemieckie = wszystkie;
        if (tylkoPolskie != null) {
            Collections.sort(tylkoPolskie, new Evewidencjacomparator());
            for (Evewidencja up : tylkoPolskie){
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
                    case "import towarów" : 
                        importtowarowList.add(up);
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
        if (tylkoNiemieckie != null) {
            Collections.sort(tylkoNiemieckie, new Evewidencjacomparator());
            for (Evewidencja up : tylkoNiemieckie){
                listadostepnychewidencji.add(up);
                switch(up.getTransakcja()) {
                    case "zakup Niemcy" : 
                        //dokument RACH BEDZIE UZYWANY U NIEVAT I U VAAT    bo to sa niemieckiie zakupowe
                        //nowy dokument rachde bo przeciez sa rachy bez niemiec i wtedytrzbea by bylo zerowac ewidencje albo wprowadzac cczekbnosky
                        //tylko ewidencja niemiecka
                        //dwie ewidencje jedna do odliczenia naliczonego
                        //druga dl 13b
                        //ra
                        zakupNiemcyVList.add(up);
                        break;
                    case "WDT" : 
                        wntNiemcyVList.add(up);
                        wdtNiemcyVList.add(up);
                        break;
                    case "WNT" : 
                        //czy opodczepiac ewidencje wszystkie czy tworzyc dokumen WNTDE
                        wdtNiemcyVList.add(up);
                        wntNiemcyVList.add(up);
                        break;
                    case "usługi poza ter.":
                        //uzupelnic ewidencja dokumemnt UPTk bedzie tylko ten dokument sprzedazy
                        //podlaczyc sprzedaz z estawka 19% oraz 13B
                        sprzedazNiemcy13bVList.add(up);
                        break;
                            
                        //tego njie uzywamy rto sa dokumen RVC RVCS
//                    case "odwrotne obciążenie" : 
//                        rvcNiemcyVList.add(up);
//                        break;
//                    case ("odwrotne obciążenie sprzedawca"):
//                        rvcVListS.add(up);
//                        break;

                    default : 
                        sprzedazNiemcyVList.add(up);
                }
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
                    case "import towarów" : 
                        return importtowarowList;
                    case "usługi poza ter.":
                        return uslugiPTK;
                    case "eksport towarów":
                        return eksporttowarow;
                    case "zakup Niemcy":
                        return new ArrayList<Evewidencja>();
                    default:
                        return sprzedazVList;
                }
    }
    
    public List<Evewidencja> pobierzEvewidencjeNiemcy(String transakcjiRodzaj) {
        switch (transakcjiRodzaj) {
                    case ("zakup Niemcy"):
                        return zakupNiemcyVList;
                    case ("odwrotne obciążenie"):
                        return rvcNiemcyVList;
                    case ("WNT"):
                    case ("WDT"):
                        return wntNiemcyVList;
                    case ("usługi poza ter."):
                        return sprzedazNiemcy13bVList;
                    default:
                        return sprzedazNiemcyVList;
                }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<String> getNaglowekVList() {
        return naglowekVList;
    }
    
    public List<Evewidencja> getSprzedazVList() {
        return sprzedazVList;
    }

    public List<Evewidencja> getSprzedazNiemcy13bVList() {
        return sprzedazNiemcy13bVList;
    }

    public List<Evewidencja> getSprzedazNiemcyVList() {
        return sprzedazNiemcyVList;
    }

    public void setSprzedazNiemcyVList(List<Evewidencja> sprzedazNiemcyVList) {
        this.sprzedazNiemcyVList = sprzedazNiemcyVList;
    }

    public List<Evewidencja> getZakupNiemcyVList() {
        return zakupNiemcyVList;
    }

    public void setZakupNiemcyVList(List<Evewidencja> zakupNiemcyVList) {
        this.zakupNiemcyVList = zakupNiemcyVList;
    }

    public List<Evewidencja> getWntNiemcyVList() {
        return wntNiemcyVList;
    }

    public void setWntNiemcyVList(List<Evewidencja> wntNiemcyVList) {
        this.wntNiemcyVList = wntNiemcyVList;
    }

    public List<Evewidencja> getRvcNiemcyVList() {
        return rvcNiemcyVList;
    }

    public void setRvcNiemcyVList(List<Evewidencja> rvcNiemcyVList) {
        this.rvcNiemcyVList = rvcNiemcyVList;
    }

    public void setSprzedazNiemcy13bVList(List<Evewidencja> sprzedazNiemcy13bVList) {
        this.sprzedazNiemcy13bVList = sprzedazNiemcy13bVList;
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
