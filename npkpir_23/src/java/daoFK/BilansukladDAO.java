/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Bilansuklad;
import entityfk.Rzisuklad;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class BilansukladDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public BilansukladDAO() {
        super(Rzisuklad.class);
    }

    public BilansukladDAO(Class entityClass) {
        super(entityClass);
    }
    
     public  List<Bilansuklad> findAll(){
        try {
            return sessionFacade.findAll(Bilansuklad.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
