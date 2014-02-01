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

    @Inject
    private SessionFacade sessionFacade;

    public RzisukladDAO() {
        super(Rzisuklad.class);
    }
    
     public  List<Rzisuklad> findAll(){
        try {
            System.out.println("Pobieram RzisukladDAO");
            return sessionFacade.findAll(Rzisuklad.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
