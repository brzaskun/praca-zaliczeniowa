/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Umorzenie;
import entity.SrodekTrw;
import entity.Sumypkpir;
import entity.UmorzenieN;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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
    private SessionFacade umorzenieFacade;

    public UmorzenieDAO() {
        super(UmorzenieN.class);
    }
    
    public  List<UmorzenieN> findAll(){
        try {
            return umorzenieFacade.findAll(UmorzenieN.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
    public List<UmorzenieN> findBySrodek(SrodekTrw str) {
        List<UmorzenieN> list = umorzenieFacade.findUmorzenieBySrodek(str);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }
  
}
