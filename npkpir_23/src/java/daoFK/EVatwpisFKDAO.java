/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.EVatwpisFK;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class EVatwpisFKDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public EVatwpisFKDAO() {
        super(EVatwpisFK.class);
    }
    
    public List<EVatwpisFK> findAll() {
        return sessionFacade.findAll(EVatwpisFK.class);
    }
    
    public List<EVatwpisFK> findPodatnik(Podatnik podatnik) {
        return sessionFacade.findEVatwpisFKByPodatnik(podatnik);
    }
    
    public List<EVatwpisFK> findPodatnikMc(Podatnik podatnik, String mcod, String mcdo) {
        List<EVatwpisFK> l = new ArrayList<>();
        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnik(podatnik);
        if (input != null && !input.isEmpty()) {
            int dg = Integer.parseInt(mcod)-1;
            int gg = Integer.parseInt(mcdo)+1;
            for (EVatwpisFK p : input) {
                int p_mc = Integer.parseInt(p.getMcEw());
                if (p_mc > dg && p_mc < gg) {
                    l.add(p);
                }
            }
        }
        return l;
    }
    
    public List<EVatwpisFK> findPodatnikMcOdDo(Podatnik podatnik, String mcod, String mcdo) {
        List<EVatwpisFK> l = new ArrayList<>();
        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnik(podatnik);
        if (input != null && !input.isEmpty()) {
            int dg = Integer.parseInt(mcod)-1;
            int gg = Integer.parseInt(mcdo)+1;
            for (EVatwpisFK p : input) {
                int mcdok = Integer.parseInt(p.getDokfk().getMiesiac());
                if (mcdok <= dg && p.getVat() != 0.0) {
                    int p_mc = Integer.parseInt(p.getMcEw());
                    if (p_mc > dg && p_mc < gg) {
                        l.add(p);
                    }
                }
            }
        }
        return l;
    }
    
    public List<EVatwpisFK> findPodatnikMcPo(Podatnik podatnik, Integer rok, String mcod, String mcdo) {
        List<EVatwpisFK> l = new ArrayList<>();
        List<EVatwpisFK> input = sessionFacade.findEVatwpisFKByPodatnik(podatnik);
        if (input != null && !input.isEmpty()) {
            int gg = Integer.parseInt(mcdo);
            if (gg == 13) {
                gg = 1;
                rok = rok+1;
            }
            for (EVatwpisFK p : input) {
                int mcdok = Integer.parseInt(p.getDokfk().getMiesiac());
                int p_mc = Integer.parseInt(p.getMcEw());
                if (mcdok != p_mc && p.getVat() != 0.0) {
                    int p_rok = Integer.parseInt(p.getRokEw());
                    if (p_mc > gg && p_rok >= rok) {
                        l.add(p);
                    }
                }
            }
        }
        return l;
    }
    
    public EVatwpisFK znajdzEVatwpisFKPoWierszu(Wiersz wiersz) {
        return sessionFacade.znajdzEVatwpisFKPoWierszu(wiersz);
    }
    
}
