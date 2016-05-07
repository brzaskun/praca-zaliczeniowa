/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sumypkpir;
import entity.UmorzenieN;
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
public class UmorzenieDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sumypkpirFacade;

    public UmorzenieDAO() {
        super(UmorzenieN.class);
    }
    
    public  List<Sumypkpir> findAll(){
        try {
            return sumypkpirFacade.findAll(UmorzenieN.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
  
}
