/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontokategoria;
import error.E;
import java.io.Serializable;
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
public class KontokategoriaDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade walutyFacade;

    public KontokategoriaDAOfk() {
        super(Kontokategoria.class);
    }

    public KontokategoriaDAOfk(Class entityClass) {
        super(entityClass);
    }
    
    
    public  List<Kontokategoria> findAll(){
        try {
            return walutyFacade.findAll(Kontokategoria.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

   
      
}
