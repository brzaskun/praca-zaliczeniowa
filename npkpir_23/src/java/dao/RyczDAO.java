/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ryczpoz;
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
public class RyczDAO extends DAO implements Serializable {

    @Inject private SessionFacade ryczFacade;

    public RyczDAO() {
        super(Ryczpoz.class);
    }

    public Ryczpoz find(String rok, String mc, String pod) {
        return ryczFacade.findRycz(rok, mc, pod);
    }
    
    public Ryczpoz find(String rok, String mc, String pod, String udzialowiec) {
        return ryczFacade.findRycz(rok, mc, pod, udzialowiec);
    }
    
    public List<Ryczpoz> findRyczPod(String rok, String pod) {
        return ryczFacade.findRyczpodatnik(rok,pod);
    }
    
    public  List<Ryczpoz> findAll(){
        try {
            List<Ryczpoz> lista = ryczFacade.findRyczAll()   ;
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
