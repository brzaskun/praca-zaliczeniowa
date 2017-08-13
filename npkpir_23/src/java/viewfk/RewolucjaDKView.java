/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.STRDAO;
import daoFK.DokDAOfk;
import daoFK.EVatwpisFKDAO;
import daoFK.RMKDAO;
import daoFK.WierszDAO;
import entity.SrodekTrw;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.RMK;
import entityfk.Wiersz;
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
public class RewolucjaDKView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private WierszDAO wierszDAO;
    @Inject
    private EVatwpisFKDAO vatwpisFKDAO;
    @Inject
    private STRDAO strdao;
    @Inject
    private RMKDAO rmkdao;
    
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
    }
    
    private void proc1() {
        List<Dokfk> dok = dokDAOfk.findAll();
        System.out.println("Pobralem");
        int j = 0;
        for (int i = 1; i <dok.size(); i++) {
            dok.get(j++).setId(i);
        }
        System.out.println("Zachowuje");
        dokDAOfk.editList(dok);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+dok.size());
    }
    
    private void proc2() {
        List<Wiersz> wiersze= wierszDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
            Wiersz w = it.next();
            w.setDokid(w.getDokfk().getId());
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wierszDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
    
     public void proc3() {
        List<EVatwpisFK> wiersze= vatwpisFKDAO.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<EVatwpisFK> it = wiersze.iterator(); it.hasNext();) {
            EVatwpisFK w = it.next();
            if (w.getDokfk() != null) {
                w.setDokid(w.getDokfk().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wierszDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
     
     public void proc4() {
        List<SrodekTrw> wiersze= strdao.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<SrodekTrw> it = wiersze.iterator(); it.hasNext();) {
            SrodekTrw w = it.next();
            if (w.getDokfk() != null) {
                w.setDokid(w.getDokfk().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wierszDAO.editList(wiersze);
        System.out.println("Skonczylem");
        Msg.msg("Przenumerowane "+wiersze.size());
    }
    
     public void proc5() {
        List<RMK> wiersze= rmkdao.findAll();
        System.out.println("Pobralem");
        int i = 1;
        for (Iterator<RMK> it = wiersze.iterator(); it.hasNext();) {
            RMK w = it.next();
            if (w.getDokfk() != null) {
                w.setDokid(w.getDokfk().getId());
            }
            printprogres(i);
        }
        System.out.println("Zachowuje");
        wierszDAO.editList(wiersze);
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
