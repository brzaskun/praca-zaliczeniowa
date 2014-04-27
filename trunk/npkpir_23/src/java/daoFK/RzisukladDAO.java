/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Rzisuklad;
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
public class RzisukladDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public RzisukladDAO() {
        super(Rzisuklad.class);
    }

    public RzisukladDAO(Class entityClass) {
        super(entityClass);
    }
    
     public  List<Rzisuklad> findAll(){
        try {
            return sessionFacade.findAll(Rzisuklad.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
