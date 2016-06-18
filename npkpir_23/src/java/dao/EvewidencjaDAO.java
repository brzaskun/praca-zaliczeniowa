/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import comparator.Evewidencjacomparator;
import entity.Evewidencja;
import entityfk.EVatwpisDedra;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class EvewidencjaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evewidencjaFacade;

    public EvewidencjaDAO() {
        super(Evewidencja.class);
    }

     public  List<Evewidencja> findAll(){
        try {
            return evewidencjaFacade.findAll(Evewidencja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    public  Map<String, Evewidencja> findAllMap(){
        try {
            List<Evewidencja> pobraneewidencje = findAll();
            Collections.sort(pobraneewidencje, new Evewidencjacomparator());
            Map<String,Evewidencja> mapaewidencji = new HashMap<>();
            for (Evewidencja p : pobraneewidencje) {
                mapaewidencji.put(p.getNazwa(), p);
            }
            return mapaewidencji;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Evewidencja znajdzponazwie(String nazwa)  {
        Evewidencja tmp = new Evewidencja();
        try {
            tmp = evewidencjaFacade.findEvewidencjaByName(nazwa);
            return tmp;
        } catch (Exception e) { E.e(e); 
             return null;
        }
    }
    
    public List<Evewidencja> znajdzpotransakcji(String nazwa) throws Exception {
        try {
            return evewidencjaFacade.findEvewidencjaByTransakcja(nazwa);
        } catch (Exception e) { E.e(e); 
            throw new Exception();
        }
    }

    public List<EVatwpisDedra> findWierszePodatnikMc(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMc").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }
}
