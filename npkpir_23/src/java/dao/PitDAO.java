/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import error.E;
import java.io.Serializable;
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
    
    public Pitpoz find(String rok, String mc, String pod, String udzialowiec) {
        try {
            return pitpozFacade.findPitpoz(rok, mc, pod, udzialowiec);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<Pitpoz> findPitPod(String rok, String pod) {
        return pitpozFacade.findPitpodatnik(rok,pod);
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
