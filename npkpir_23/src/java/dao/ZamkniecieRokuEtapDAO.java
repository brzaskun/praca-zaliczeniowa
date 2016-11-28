/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Wpis;
import entity.ZamkniecieRokuEtap;
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
public class ZamkniecieRokuEtapDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
    
    public ZamkniecieRokuEtapDAO(){
        super(ZamkniecieRokuEtap.class);
    }

    
    public  List<ZamkniecieRokuEtap> findAll(){
        try {
            return wpisFacade.findAll(ZamkniecieRokuEtap.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
