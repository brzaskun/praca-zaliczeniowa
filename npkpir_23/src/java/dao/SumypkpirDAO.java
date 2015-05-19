/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sumypkpir;
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
public class SumypkpirDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sumypkpirFacade;

    public SumypkpirDAO() {
        super(Sumypkpir.class);
    }
    
    public  List<Sumypkpir> findAll(){
        try {
            return sumypkpirFacade.findAll(Sumypkpir.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
   public  List<Sumypkpir> findS(String podatnik, String rok){
        try {
            return sumypkpirFacade.findSumy(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
