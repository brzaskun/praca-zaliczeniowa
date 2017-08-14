/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.PodatnikDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DelegacjaDAO;
import daoFK.DokDAOfk;
import daoFK.EVatDeklaracjaPlikDAO;
import daoFK.EVatwpisDedraDAO;
import daoFK.MiejsceKosztowDAO;
import daoFK.MiejscePrzychodowDAO;
import daoFK.PojazdyDAO;
import daoFK.SprawozdanieFinansoweDAO;
import daoFK.WierszBODAO;
import daoFK.WynikFKRokMcDAO;
import entity.Podatnik;
import entityfk.Cechazapisu;
import entityfk.Delegacja;
import entityfk.Dokfk;
import entityfk.EVatDeklaracjaPlik;
import entityfk.EVatwpisDedra;
import entityfk.MiejsceKosztow;
import entityfk.MiejscePrzychodow;
import entityfk.Pojazdy;
import entityfk.SprawozdanieFinansowe;
import entityfk.WierszBO;
import entityfk.WynikFKRokMc;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RewolucjaPodatnikView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject
    private DelegacjaDAO delegacjaDAO;
    @Inject
    private EVatDeklaracjaPlikDAO eVatDeklaracjaPlikDAO;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private MiejsceKosztowDAO miejsceKosztowDAO;
    @Inject
    private PojazdyDAO pojazdyDAO;
    @Inject
    private SprawozdanieFinansoweDAO sprawozdanieFinansoweDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private WynikFKRokMcDAO wynikFKRokMcDAO;
    
    
    public void przenumeruj() {
       proc1();
       System.out.println("Proc 1");
       proc2();
       System.out.println("Proc 2");
       proc3();
       System.out.println("Proc 3");
       proc4();
       System.out.println("Proc 4");
       proc5();
       System.out.println("Proc 5");
       proc6();
       System.out.println("Proc 6");
       proc7();
       System.out.println("Proc 7");
       proc8();
       System.out.println("Proc 8");
       proc9();
       System.out.println("Proc 9");
       proc10();
       System.out.println("Proc 10");
       proc11();
       System.out.println("Proc 11");
       proc12();
       System.out.println("Proc 12");
    }
    
    private void proc1() {
        List<Podatnik> wiersze = podatnikDAO.findAllManager();
        System.out.println("Pobralem proc1");
        int j = 0;
        for (int i = 1; i < wiersze.size()+1; i++) {
            wiersze.get(j++).setId(i);
        }
        System.out.println("Zachowuje proc1");
        podatnikDAO.editList(wiersze);
        System.out.println("Skonczylem proc1");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
    
    private void proc2() {
        List<Cechazapisu> wiersze= cechazapisuDAOfk.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<Cechazapisu> it = wiersze.iterator(); it.hasNext();) {
            Cechazapisu w = it.next();
            if (w.getPodatnik()!= null) {
                w.setPodid(w.getPodatnik().getId());
                printprogres(i);
            }
        }
        System.out.println("Zachowuje");
        cechazapisuDAOfk.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
    
     public void proc3() {
        List<Delegacja> wiersze= delegacjaDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<Delegacja> it = wiersze.iterator(); it.hasNext();) {
            Delegacja w = it.next();
            if (w.getPodatnikObj()!= null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        delegacjaDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
     public void proc4() {
        List<Dokfk> wiersze= dokDAOfk.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<Dokfk> it = wiersze.iterator(); it.hasNext();) {
            Dokfk w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        dokDAOfk.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
    
     public void proc5() {
        List<EVatDeklaracjaPlik> wiersze= eVatDeklaracjaPlikDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<EVatDeklaracjaPlik> it = wiersze.iterator(); it.hasNext();) {
            EVatDeklaracjaPlik w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        eVatDeklaracjaPlikDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
     public void proc6() {
        List<EVatwpisDedra> wiersze= eVatwpisDedraDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<EVatwpisDedra> it = wiersze.iterator(); it.hasNext();) {
            EVatwpisDedra w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        eVatwpisDedraDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
      public void proc7() {
        List<MiejscePrzychodow> wiersze= miejscePrzychodowDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<MiejscePrzychodow> it = wiersze.iterator(); it.hasNext();) {
            MiejscePrzychodow w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        miejscePrzychodowDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
      
      public void proc8() {
        List<MiejsceKosztow> wiersze= miejsceKosztowDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<MiejsceKosztow> it = wiersze.iterator(); it.hasNext();) {
            MiejsceKosztow w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        miejsceKosztowDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
      public void proc9() {
        List<Pojazdy> wiersze= pojazdyDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<Pojazdy> it = wiersze.iterator(); it.hasNext();) {
            Pojazdy w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        pojazdyDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
      
      public void proc10() {
        List<SprawozdanieFinansowe> wiersze= sprawozdanieFinansoweDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<SprawozdanieFinansowe> it = wiersze.iterator(); it.hasNext();) {
            SprawozdanieFinansowe w = it.next();
            if (w.getPodatnik() != null) {
                w.setPodid(w.getPodatnik().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        sprawozdanieFinansoweDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
      
      public void proc11() {
        List<WierszBO> wiersze= wierszBODAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<WierszBO> it = wiersze.iterator(); it.hasNext();) {
            WierszBO w = it.next();
            if (w.getPodatnik() != null) {
                w.setPodid(w.getPodatnik().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wierszBODAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
      
      public void proc12() {
        List<WynikFKRokMc> wiersze= wynikFKRokMcDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<WynikFKRokMc> it = wiersze.iterator(); it.hasNext();) {
            WynikFKRokMc w = it.next();
            if (w.getPodatnikObj() != null) {
                w.setPodid(w.getPodatnikObj().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wynikFKRokMcDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
    private void printprogres(int val) {
        if ( (val % 10000) == 0) {
            System.out.println("zrobiono "+val);
        }
    }
    
    public static void main(String[] args) {
        if ( (10002 % 10000) == 0) {
            System.out.println("even");
        } else {
            System.out.println("not even");
        }
    }
}
