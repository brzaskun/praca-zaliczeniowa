/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pismoadmin;
import entity.UPO;
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
public class UPODAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public UPODAO() {
        super(UPO.class);
    }
    
    public  List<Pismoadmin> findAll(){
        try {
            List<Pismoadmin> lista = sessionFacade.findAll(UPO.class);
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Pismoadmin> findBiezace(){
        try {
            List<Pismoadmin> lista = sessionFacade.findPismoadminBiezace();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

   
    
    
}
