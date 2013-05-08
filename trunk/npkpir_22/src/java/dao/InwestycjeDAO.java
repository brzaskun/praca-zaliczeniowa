/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Inwestycje;
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
    
     public  List<Inwestycje> findInwestycje(String podatnik){
        try {
            System.out.println("Pobieram InwestycjeDAO");
            List<Inwestycje> lista = inwestycjeFacade.findInwestycje(podatnik);
            return lista;
        } catch (Exception e) {
            return null;
        }
   }
    
}
