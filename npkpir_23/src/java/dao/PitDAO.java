/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import entityfk.Cechazapisu;
import error.E;
import java.io.Serializable;
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
public class PitDAO extends DAO implements Serializable {

    @Inject private SessionFacade pitpozFacade;

    public PitDAO() {
        super(Pitpoz.class);
    }

    public Pitpoz find(String rok, String mc, String pod) {
        return pitpozFacade.findPitpoz(rok, mc, pod);
    }
    
    public List<Pitpoz> findList(String rok, String mc, String pod) {
        return pitpozFacade.findPitpozLista(rok, mc, pod);
    }
    
    public Pitpoz find(String rok, String mc, String pod, String udzialowiec, Cechazapisu cecha) {
        try {
            return pitpozFacade.findPitpoz(rok, mc, pod, udzialowiec, cecha);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Pitpoz> findPitPod(String rok, String pod,Cechazapisu wybranacechadok) {
        List<Pitpoz> tmp = pitpozFacade.getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        if (wybranacechadok!=null) {
            tmp = pitpozFacade.getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik AND p.cechazapisu = :cecha").setParameter("pkpirR", rok).setParameter("podatnik", pod).setParameter("cecha", wybranacechadok).getResultList();
        }
        return Collections.synchronizedList(tmp);
    }

    public  List<Pitpoz> findAll(){
        try {
            List<Pitpoz> lista = pitpozFacade.findPitpozAll()   ;
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
