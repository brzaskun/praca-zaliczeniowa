/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturywystokresoweDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturywystokresoweFacade;

    public FakturywystokresoweDAO() {
        super(Fakturyokresowe.class);
    }
    
    public  List<Fakturywystokresowe> findAll(){
        try {
            return fakturywystokresoweFacade.findAll(Fakturywystokresowe.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Fakturywystokresowe findFakturaOkresowaById(Integer id){
        try {
            return fakturywystokresoweFacade.findFakturaOkresowaById(id);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
//    public List<Fakturywystokresowe> findPodatnik(String podatnik){
//        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
//        try {
//            zwrot = fakturywystokresoweFacade.findPodatnikFaktury(podatnik);
//        } catch (Exception e) { E.e(e); }
//        return zwrot;
//    }
    
//     public List<Fakturywystokresowe> findPodatnik(String podatnik, String rok){
//        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
//        try {
//            zwrot = fakturywystokresoweFacade.findPodatnikRokFaktury(podatnik, rok);
//        } catch (Exception e) { E.e(e); }
//        return zwrot;
//    }
     
     public List<Fakturywystokresowe> findPodatnikBiezace(String podatnik, String rok){
        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
            zwrot = fakturywystokresoweFacade.findPodatnikRokFakturyBiezace(podatnik, rok);
        } catch (Exception e) { E.e(e); }
        return zwrot;
    }

    public Fakturywystokresowe findOkresowa(String rok, String klientnip, String nazwapelna, double brutto) {
        try {
            return fakturywystokresoweFacade.findOkresowa(rok, klientnip, nazwapelna, brutto);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
     public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc, String rok) {
        try {
            return fakturywystokresoweFacade.findOkresoweOstatnie(podatnik, mc, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<Fakturywystokresowe> findFakturaOkresowaByFaktura(Faktura p) {
        try {
            return fakturywystokresoweFacade.findOkresoweOstatnieByfaktura(p);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
}
