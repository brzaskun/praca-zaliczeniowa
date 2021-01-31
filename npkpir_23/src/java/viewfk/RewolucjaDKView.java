/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.STRDAO;
import dao.DokDAOfk;
import dao.EVatwpisFKDAO;
import dao.RMKDAO;
import dao.WierszDAO;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@Named
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
    
//    public void przenumeruj() {
//       proc1();
//       error.E.s("Proc 1");
//       proc2();
//       error.E.s("Proc 2");
//       proc3();
//       error.E.s("Proc 3");
//       proc4();
//       error.E.s("Proc 4");
//       proc5();
//       error.E.s("Proc 5");
//    }
//    
//    private void proc1() {
//        List<Dokfk> dok = dokDAOfk.findAll();
//        error.E.s("Pobralem");
//        int j = 0;
//        for (int i = 1; i <dok.size(); i++) {
//            dok.get(j++).setId(i);
//        }
//        error.E.s("Zachowuje");
//        dokDAOfk.editList(dok);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+dok.size());
//    }
//    
//    private void proc2() {
//        List<Wiersz> wiersze= wierszDAO.findAll();
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
//            Wiersz w = it.next();
//            w.setDokid(w.getDokfk().getId());
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        wierszDAO.editList(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc3() {
//        List<EVatwpisFK> wiersze= vatwpisFKDAO.findAll();
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<EVatwpisFK> it = wiersze.iterator(); it.hasNext();) {
//            EVatwpisFK w = it.next();
//            if (w.getDokfk() != null) {
//                w.setDokid(w.getDokfk().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        wierszDAO.editList(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     public void proc4() {
//        List<SrodekTrw> wiersze= strdao.findAll();
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<SrodekTrw> it = wiersze.iterator(); it.hasNext();) {
//            SrodekTrw w = it.next();
//            if (w.getDokfk() != null) {
//                w.setDokid(w.getDokfk().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        wierszDAO.editList(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//    
//     public void proc5() {
//        List<RMK> wiersze= rmkdao.findAll();
//        error.E.s("Pobralem");
//        int i = 1;
//        for (Iterator<RMK> it = wiersze.iterator(); it.hasNext();) {
//            RMK w = it.next();
//            if (w.getDokfk() != null) {
//                w.setDokid(w.getDokfk().getId());
//            }
//            printprogres(i);
//        }
//        error.E.s("Zachowuje");
//        wierszDAO.editList(wiersze);
//        error.E.s("Skonczylem");
//        Msg.msg("Przenumerowane "+wiersze.size());
//    }
//     
//     
//    private void printprogres(int val) {
//        if ( (val % 10000) == 0) {
//            error.E.s("zrobiono "+val);
//        }
//    }
//    
//    public static void main(String[] args) {
//        if ( (10002 % 10000) == 0) {
//            error.E.s("even");
//        } else {
//            error.E.s("not even");
//        }
//    }
}
