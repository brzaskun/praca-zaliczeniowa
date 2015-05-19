/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zusstawki;
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
public class ZUSDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade zusstawkiFacade;
   
    public ZUSDAO(){
        super(Zusstawki.class);
    }
    
    public  List<Zusstawki> findAll(){
        try {
            return zusstawkiFacade.findAll(Zusstawki.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
