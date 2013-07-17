/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontozapisy;
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
public class KontoZapisyFKDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade kontozapisyFacade;

    public KontoZapisyFKDAO() {
        super(Kontozapisy.class);
    }
    
     public  List<Kontozapisy> findAll(){
        try {
            System.out.println("Pobieram KontoZapisyFKDAO");
            return kontozapisyFacade.findAll(Kontozapisy.class);
        } catch (Exception e) {
            return null;
        }
   }

    
}
