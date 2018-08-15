/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.WniosekVATZDEntity;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class WniosekVATZDEntityDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
    
    public WniosekVATZDEntityDAO() {
        super(WniosekVATZDEntityDAO.class);
    }
    
    public  List<WniosekVATZDEntity> findAll(){
        try {
            return wierszeFacade.findAll(WniosekVATZDEntity.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<WniosekVATZDEntity> findByPodatnikRokMcFK(WpisView wpisView){
        try {
            return wierszeFacade.findWniosekZDByPodatnikRokMcFK(wpisView);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<WniosekVATZDEntity> findByPodatnikRokMcFK(Podatnik podatnik, String rok, String mc){
        try {
            return wierszeFacade.findWniosekZDByPodatnikRokMcFK(podatnik, rok, mc);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    
   
}
