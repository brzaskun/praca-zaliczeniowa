/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Inwestycje;
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
public class InwestycjeDAO  extends DAO implements Serializable {
    @Inject
    private SessionFacade inwestycjeFacade;

    public InwestycjeDAO() {
        super(Inwestycje.class);
    }
    
     public  List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona){
        try {
            List<Inwestycje> lista = inwestycjeFacade.findInwestycje(podatnik, zakonczona);
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<Inwestycje> findAll() {
        return sessionFacade.findAll(Inwestycje.class);
    }
    
}
