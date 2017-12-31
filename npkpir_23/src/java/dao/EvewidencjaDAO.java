/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import comparator.Evewidencjacomparator;
import entity.Evewidencja;
import entity.Evpozycja;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

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
    
    public  Map<String, Evewidencja> findAllMapByPole(){
        try {
            List<Evewidencja> pobraneewidencje = findAll();
            Collections.sort(pobraneewidencje, new Evewidencjacomparator());
            Map<String,Evewidencja> mapaewidencji = new HashMap<>();
            for (Evewidencja p : pobraneewidencje) {
                mapaewidencji.put(p.getPole(), p);
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
    
    public List<Evewidencja> znajdzpotransakcji(String nazwa) {
        try {
            return evewidencjaFacade.findEvewidencjaByTransakcja(nazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Evewidencja znajdzponazwiePola(Evpozycja macierzysty) {
        try {
            return evewidencjaFacade.findEvewidencjaByPole(macierzysty);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    
    
}
