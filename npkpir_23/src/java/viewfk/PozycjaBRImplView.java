/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PozycjaBRImplView  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    
    
     public void implementujRZiS(TreeNodeExtended root, UkladBR uklad) {
        String nazwa = uklad.getUklad();
        String rok = uklad.getRok();
        List<PozycjaRZiS> pozycjewzorowy = pozycjaRZiSDAO.findRzisuklad(nazwa, "Wzorcowy", rok);
        List pozycjedocelowy = new ArrayList();
        root.getChildrenTree(new ArrayList<TreeNodeExtended>(), pozycjedocelowy);
        for (PozycjaRZiS p : pozycjewzorowy) {
            for (Object r : pozycjedocelowy) {
                PozycjaRZiS s = (PozycjaRZiS) r;
                if (s.getNazwa().equals(p.getNazwa()) && s.getPozycjaString().equals(p.getPozycjaString())) {
                    s.setDe(p.getDe());
                    break;
                }
            }
        }
        pozycjaRZiSDAO.editList(pozycjedocelowy);
        Msg.msg("Zaimplementowano wzorcowy Rachunek Zysków i Strat");
    }
    
    public void implementujBilans(TreeNodeExtended root, UkladBR uklad) {
        String nazwa = uklad.getUklad();
        String rok = uklad.getRok();
        List<PozycjaBilans> pozycjewzorowy = pozycjaBilansDAO.findBilansuklad(nazwa, "Wzorcowy", rok);
        List pozycjedocelowy = new ArrayList();
        root.getChildrenTree(new ArrayList<TreeNodeExtended>(), pozycjedocelowy);
        for (PozycjaBilans p : pozycjewzorowy) {
            for (Object r : pozycjedocelowy) {
                PozycjaBilans s = (PozycjaBilans) r;
                if (s.getNazwa().equals(p.getNazwa()) && s.getPozycjaString().equals(p.getPozycjaString())) {
                    s.setDe(p.getDe());
                    break;
                }
            }
        }
        pozycjaRZiSDAO.editList(pozycjedocelowy);
        Msg.msg("Zaimplementowano wzorcowy Bilans");
    }
    
}
